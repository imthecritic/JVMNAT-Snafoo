package com.nerdery.snafoo.services;

import com.nerdery.snafoo.common.Logging;
import com.nerdery.snafoo.model.domain.jpa.SnackVotesModel;
import com.nerdery.snafoo.model.domain.jpa.UserVoteModel;
import com.nerdery.snafoo.model.domain.rest.SnackRESTModel;
import com.nerdery.snafoo.model.domain.rest.SuggestionModel;
import com.nerdery.snafoo.repository.SnackVotesRepository;
import com.nerdery.snafoo.repository.UserVoteRepository;
import com.nerdery.snafoo.repository.rest.SnackRestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


import javax.inject.Inject;
import java.util.*;

/**
 * A service that handles the methods needed by the controller
 */

@Service
public class SnafooService implements Logging {

    private static final Logger log = LoggerFactory.getLogger(SnafooService.class);


    String restUrl = "https://api-snacks.nerderylabs.com/v1/snacks/?ApiKey=825a0505-2620-4f23-92bc-e2d3cb7cfc14";
    private SnackRestRepository snackRestRepository = new SnackRestRepository(restUrl);

    @Autowired
    private SnackVotesRepository snackVotesRepository;

    @Autowired
    private UserVoteRepository userVoteRepository;

    private String votemessage = "";


    public ArrayList<SnackRESTModel> getSnacks() {
        log.info("Getting an arraylist of SnackRestModels" );
        ArrayList<SnackRESTModel> snacks = new ArrayList<SnackRESTModel>();
        for (SnackRESTModel snackRESTModel: snackRestRepository.getSnacks()){
            snacks.add(snackRESTModel);
        };

        return  snacks;
    }

    public ArrayList<SnackVotesModel> getOptionalSnacks(){
        log.info("Getting optional snacks");

        ArrayList<SnackVotesModel> optional = new ArrayList<SnackVotesModel>();
        for (SnackVotesModel snack: snackVotesRepository.findAll()){
            if (snack.isOptional()){
                optional.add(snack);
            }
        }
        return  optional;
    }

    public ArrayList<SnackVotesModel> getAlwaysPurchasedSnacks(){
        log.info("Getting nonoptional snacks");

        ArrayList<SnackVotesModel> nonOptional = new ArrayList<SnackVotesModel>();
        for (SnackVotesModel snack: snackVotesRepository.findAll()){
            if (!snack.isOptional()){
                nonOptional.add(snack);
            }
        }
        return  nonOptional;
    }

    public ArrayList<SnackVotesModel> getTopTenVotes(){
        log.info("Getting ten snacks to order");

        ArrayList<SnackVotesModel> all = new ArrayList<SnackVotesModel>();
       for (SnackVotesModel nonOptional: this.getAlwaysPurchasedSnacks()){
           all.add(nonOptional);
       }
        ArrayList<SnackVotesModel> top = new ArrayList<SnackVotesModel>();

        for (SnackVotesModel optional: this.getOptionalSnacks()){
            top.add(optional);
        }

        Collections.sort(top, ( a,  b) -> a.compareTo(b));
        Collections.reverse(top);


        for (int i=0; i<top.size(); i++){
            if(all.size()<10) {
                all.add(top.get(i));
            }
            else {
                break;
            }
        }

        return all;
    }

    public String postSuggestion (SuggestionModel suggestion){
        log.info("Attempting to post a suggestion", suggestion);
        return snackRestRepository.addSnackSuggestion(suggestion);
    }

    public void addVote(int restId){
        log.info("adding snack to  snack votes repository");
        SnackVotesModel svm = snackVotesRepository.findByRestId(restId);
        svm.addVote();
        snackVotesRepository.save(svm);
    }

    /**
     * Parses a string snack votes model and extracts the restID to return it as an integer
     * @param snackVotesModel
     * @return
     */
    public int getRestIdFromStringSVM(String snackVotesModel){
        log.info("Parsing a string to get restId", snackVotesModel);

        int indexOfOpenBracket = snackVotesModel.indexOf("[");
        int indexOfLastBracket = snackVotesModel.lastIndexOf("]");
        snackVotesModel = snackVotesModel.substring(indexOfOpenBracket+1, indexOfLastBracket);
        String[] voteArray = snackVotesModel.split(",");
        Map<String, String> voteMap = new HashMap<String, String>();
        for (int i=0; i<voteArray.length; i++){
            String v = voteArray[i];
            String[] keyValue = v.split(":");
            voteMap.put(keyValue[0], keyValue[1]);
        }
        String restidStr = voteMap.get("Id");
        int restId = Integer.parseInt(restidStr.replaceAll(" ", ""));
        return restId;
    }

    @Inject
    public void setSnackRestRepository(SnackRestRepository snackRestRepository) {
        this.snackRestRepository = snackRestRepository;
    }

    /**
     * Finds and returns a SnackVotesModel from Repository
     * @param restId
     * @return
     */
    public SnackVotesModel findByRestId(int restId) {
        log.info("Finding SnackVotesModel by restId " +  restId);
        Assert.notNull(snackVotesRepository, "Failed to seed snacks database, due to missing repository object.");

        return snackVotesRepository.findByRestId(restId);
    }

    public void saveSnack(SnackVotesModel snackVotesModel){
        log.info("Saving {} to repository", snackVotesModel.getName());
        snackVotesRepository.save(snackVotesModel);
    }

    @Inject
    public void setSnackVotesRepository(SnackVotesRepository snackVotesRepository) {
        this.snackVotesRepository = snackVotesRepository;
    }

    public UserVoteModel getUserBySessionId(String employeeSession){
        log.info("Getting a user with ipAddress {} ", employeeSession);
        return userVoteRepository.findByEmployeeSession(employeeSession);
    }

    public void saveUserVote(UserVoteModel userVoteModel){
        log.info("Saving a user vote to repository");

        userVoteRepository.save(userVoteModel);
    }

    @Inject
    public void setUserVoteRepository(UserVoteRepository userVoteRepository) {
        this.userVoteRepository = userVoteRepository;
    }

    /**
     * Checks if the current employee can submit a vote
     * @param restId
     * @param employeeSession
     * @return
     */
    public boolean checkIfCanVote(int restId, String employeeSession) {
        log.info("Checking if user {} can vote for {} ", employeeSession, restId);
        UserVoteModel userVoteModel = getUserBySessionId(employeeSession);
        Assert.notNull(userVoteModel,"Can't find user with session Id");
        SnackVotesModel snackVotesModel = this.findByRestId(restId);
        boolean canAddSnack = userVoteModel.canAddSnack(snackVotesModel);
        int numberofVote = userVoteModel.getNumOfVotesLeft();
        boolean canVote = false;
        if (!canAddSnack & numberofVote==0){
            votemessage = "Opps! You have already voted the total allowed times this month.";
            userVoteModel.setCanVote(false);
            canVote = false;
        }
        if (canAddSnack){
            if (numberofVote == 0){
                votemessage = "Opps! You have already voted the total allowed times this month.";
                userVoteModel.setCanVote(false);
                canVote = false;
            }
            else {
                canVote = true;
            }
        }
        else{
            votemessage = "You have already submitted a vote for this snack!";
            canVote = false;
        }
        this.saveUserVote(userVoteModel);

        return canVote;
    }

    /**
     * Keeps track of a user's vote by adding it to the snacklist in repository
     * @param restId
     * @param employeeSession
     * @return
     */
    public String addUserVote(int restId, String employeeSession) {
        log.info("Adding user {} vote for {} to user repository ", employeeSession, restId);
        UserVoteModel userVoteModel = getUserBySessionId(employeeSession);
        Assert.notNull(userVoteModel,"Can't find user with session Id");
        SnackVotesModel snackVotesModel = this.findByRestId(restId);
        userVoteModel.addSnack(snackVotesModel);
        votemessage = "Awesome your vote has been counted!";
        userVoteModel.decrementNumOfVotesLeft();
        this.saveUserVote(userVoteModel);

        return votemessage;
    }

    /**
     * Used to reset the user so they cna vote the following month
     * @param ipAddress
     */
    public void userReset(String ipAddress) {
        log.info("Resetting user " + ipAddress);

        UserVoteModel userVoteModel =  userVoteRepository.findByEmployeeSession(ipAddress);
      if (userVoteModel != null){
          userVoteModel.reset();
          saveUserVote(userVoteModel);
      }
    }
}