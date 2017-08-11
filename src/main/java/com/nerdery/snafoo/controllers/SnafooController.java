package com.nerdery.snafoo.controllers;

import com.nerdery.snafoo.common.Logging;
import com.nerdery.snafoo.model.domain.jpa.SnackVotesModel;
import com.nerdery.snafoo.model.domain.jpa.UserVoteModel;
import com.nerdery.snafoo.model.domain.rest.SnackRESTModel;
import com.nerdery.snafoo.model.domain.rest.SuggestionModel;
import com.nerdery.snafoo.model.view.SnackViewModel;
import com.nerdery.snafoo.services.SnafooService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ModelAttribute;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Snafoo MVC controller demonstrating injection and consumption of various services, request handler methods, and
 * a forced exception for testing the exception handling logic. It can be safely deleted once you have implemented your
 * own controller(s).
 */
@Controller
public class SnafooController implements Logging {


    @Autowired
    private SnafooService snafooService = new SnafooService();

    private static final Logger log = LoggerFactory.getLogger(SnafooController.class);

    /**
     * See the database with example data. This method can be re-purposed or deleted when you create your snack food implementation.
     *
     */

    @Autowired
    private void seedDatabase() {
        log.info("Seeding the database.");
        ArrayList<SnackRESTModel> snackRESTModels = snafooService.getSnacks();
        for (SnackRESTModel snackRESTModel: snackRESTModels){
            SnackVotesModel snackVotesModel = snafooService.findByRestId(snackRESTModel.getRestId());
            if (snackVotesModel == null){
                snafooService.saveSnack(new SnackVotesModel(snackRESTModel));
            }
        }

    }

    /**
     * Calculates the number of days until the end of the month
     * @return the number in seconds to be fed to the cookie
     */
    private int daysUntilEndOfMonth(){

        Date today = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);

        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);

        Date lastDayOfMonth = calendar.getTime();

        TimeUnit timeUnit = TimeUnit.SECONDS;
        long diffInMillies = lastDayOfMonth.getTime() - today.getTime();
        long seconds = timeUnit.convert(diffInMillies,TimeUnit.SECONDS);

        return (int) seconds;
    }

    /**
     * Renders the home page
     * @param model
     * @param response
     * @param request
     * @return index.ftl
     */
    @RequestMapping("/")
    public String renderHomePage(Model model, HttpServletResponse response, HttpServletRequest request) {
        log.info("Rendering homepage");
        seedDatabase();
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        cookieManager(ipAddress, request, response);
        UserVoteModel userVoteModel = snafooService.getUserBySessionId(ipAddress);
        if (userVoteModel == null){
            snafooService.saveUserVote(new UserVoteModel(ipAddress));
            userVoteModel = snafooService.getUserBySessionId(ipAddress);
        }
        ArrayList<SnackVotesModel> alwaysOrdered = snafooService.getAlwaysPurchasedSnacks();
        ArrayList<SnackVotesModel> optional = snafooService.getOptionalSnacks();
        model.addAttribute("alwaysOrdered", alwaysOrdered);
        model.addAttribute("optional", optional);
        model.addAttribute("userVoteModel", userVoteModel);
        return "index";
    }

    /**
     * Renders Suggestion page and call required services
     * @param model
     * @param response
     * @param request
     * @return
     */
    @RequestMapping("/suggestions")
    public String renderSuggestionsPage(Model model, HttpServletResponse response, HttpServletRequest request) {
        log.info("Rendering suggestions page");
        seedDatabase();
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        cookieManager(ipAddress, request, response);
        UserVoteModel userVoteModel = snafooService.getUserBySessionId(ipAddress);
        if (userVoteModel == null){
            snafooService.saveUserVote(new UserVoteModel(ipAddress));
            userVoteModel = snafooService.getUserBySessionId(ipAddress);
        }
        ArrayList<SnackRESTModel> snacks = snafooService.getSnacks();
        ArrayList<SnackVotesModel> suggestions = snafooService.getOptionalSnacks();
        String message = "";
        model.addAttribute("suggestions", suggestions);
        model.addAttribute("message",message);
        model.addAttribute("userVoteModel", userVoteModel);

        return "suggestions";
    }

    /**
     * Renders shopping list page
     * @param model
     * @return
     */
    @RequestMapping("/shoppinglist")
    public String renderShoppingListPage(Model model) {
        log.info("Rendering shopping list page");

        ArrayList<SnackVotesModel> topTenVotes = snafooService.getTopTenVotes();

        model.addAttribute("topTenVotes", topTenVotes);
        return "shoppinglist";
    }

    /**
     * Initializes suggession htm to handle suggestion requests
     * @param model
     * @return
     */
    @RequestMapping(value = "/suggestion.htm", method = RequestMethod.GET)
    public String initSuggestion(Model model){
        log.info("Intializing the suggestion model");

        SuggestionModel sug = new SuggestionModel();
        model.addAttribute("sug", sug);
        return "suggestions";
    }

    /**
     * Reads suggession submit to handle suggestion requests
     * @param model
     * @return
     */
    @RequestMapping(value = "/suggestion.htm", method = RequestMethod.POST)
    public String postSuggestion(@ModelAttribute("sug") SuggestionModel sug,
                                 Model model, BindingResult result, HttpServletResponse response, HttpServletRequest request)
    {
        String voteMessage = "";
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        log.info("Proccessing a suggestion for {}", ipAddress);

        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        cookieManager(ipAddress, request, response);
        UserVoteModel userVoteModel = snafooService.getUserBySessionId(ipAddress);
        if (userVoteModel == null){
            snafooService.saveUserVote(new UserVoteModel(ipAddress));
            userVoteModel = snafooService.getUserBySessionId(ipAddress);
        }
        ArrayList<SnackRESTModel> snacks = snafooService.getSnacks();
        SnackViewModel snackViewModel = new SnackViewModel(snacks);
        ArrayList<SnackVotesModel> suggestions = snafooService.getOptionalSnacks();
        String message = "";
        String ss = sug.getSuggestions();

        if (sug.getName() == "" & sug.getLocation()== "" & ss.equals("default")){
            message = "Oops, you didn't suggest anything. Try again!";
        }

        else if (sug.getName() == "" & sug.getLocation() == "" ){
            if (ss.equals("default")) {
                message = "You must provide a snack name and a location. Try again!";
            }
            else{
                String vote = sug.getSuggestions();
                int restId = snafooService.getRestIdFromStringSVM(vote);
                boolean canVote = snafooService.checkIfCanVote(restId, ipAddress);
                if (canVote) {
                    voteMessage = snafooService.addUserVote(restId, ipAddress);
                    snafooService.addVote(restId);
                }
                else{
                    voteMessage = "Sorry, you have already reached your voting limit or voted for that snack!";
                }
                message = voteMessage;
            }
        }
        else{
            message = snafooService.postSuggestion(sug);
        }
        model.addAttribute("suggestions", suggestions);
        model.addAttribute("message", message);
        model.addAttribute("userVoteModel", userVoteModel);

        return "suggestions";
    }

    /**
     * Intitilizes the vote.htm
     * @param model
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value="/vote.htm", method=RequestMethod.GET)
    public String initVote(Model model, HttpServletResponse response, HttpServletRequest request) {
        log.info("Intializing and getting the vote model");

        String vote = "default";
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        cookieManager(ipAddress, request, response);
        UserVoteModel userVoteModel = snafooService.getUserBySessionId(ipAddress);
        if (userVoteModel == null){
            snafooService.saveUserVote(new UserVoteModel(ipAddress));
            userVoteModel = snafooService.getUserBySessionId(ipAddress);
        }
        ArrayList<SnackVotesModel> alwaysOrdered = snafooService.getAlwaysPurchasedSnacks();
        ArrayList<SnackVotesModel> optional = snafooService.getOptionalSnacks();
        model.addAttribute("alwaysOrdered", alwaysOrdered);
        model.addAttribute("optional", optional);
        model.addAttribute("userVoteModel", userVoteModel);
        model.addAttribute(vote);

        return "index";
    }

    /**
     * Handles vote post
     * @param voteCookie
     * @param vote
     * @param model
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value="/vote.htm", method=RequestMethod.POST)
    public String vote(@CookieValue(value="voteCookie" , defaultValue ="voteCookie") String voteCookie,
                       @ModelAttribute("vote")  String vote, Model model, HttpServletResponse response, HttpServletRequest request) {
        String voteMessage = "";
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        log.info("Processing a vote submission by {}", ipAddress);

        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        cookieManager(ipAddress, request, response);
        UserVoteModel userVoteModel = snafooService.getUserBySessionId(ipAddress);
        if (userVoteModel == null){
            snafooService.saveUserVote(new UserVoteModel(ipAddress));
            userVoteModel = snafooService.getUserBySessionId(ipAddress);
        }
        ArrayList<SnackVotesModel> alwaysOrdered = snafooService.getAlwaysPurchasedSnacks();
        ArrayList<SnackVotesModel> optional = snafooService.getOptionalSnacks();

        int restId = snafooService.getRestIdFromStringSVM(vote);
        boolean canVote = snafooService.checkIfCanVote(restId, ipAddress);
        if (canVote) {
            voteMessage = snafooService.addUserVote(restId, ipAddress);
            snafooService.addVote(restId);
        }

        model.addAttribute("alwaysOrdered", alwaysOrdered);
        model.addAttribute("optional", optional);
        model.addAttribute("userVoteModel", userVoteModel);

        return "index";
    }

    /**
     * Manages the cookies using the IP
     * @param ipAddress
     * @param request
     * @param response
     */
    private void cookieManager (String ipAddress, HttpServletRequest request, HttpServletResponse response){
        log.info("Managing the cookie for {}", ipAddress);

        boolean cookieExists = false;
        Cookie[] cookies = request.getCookies();
        if (cookies == null){
            cookieExists = false;
        }
        for(int i=0;cookies!=null&&i<cookies.length;i++) {
            String name = cookies[i].getName();
            if (name.equals("voteCookie")){
                cookieExists = true;
            }
        }
        if (!cookieExists){
            Cookie cookie = new Cookie("voteCookie", "voteCookie");
            response.addCookie(cookie);
        }
        for(int i=0;cookies!=null&&i<cookies.length;i++){
            String value = cookies[i].getValue();
            String name = cookies[i].getName();
            if (name.equals("voteCookie") && value.equals( "voteCookie")){
                snafooService.userReset(ipAddress);
                cookies[i].setValue(ipAddress);
                cookies[i].setMaxAge(daysUntilEndOfMonth());
                response.addCookie(cookies[i]);
            }

        }
    }


}