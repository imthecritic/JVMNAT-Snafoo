package com.nerdery.snafoo.model.domain.jpa;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * UserVoteModel Domain Model
 * Store a UserVoteModel object annotated as a JPA entity.
 */

@Entity
@Table (name="userVotes")
public class UserVoteModel implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private int numOfVotesLeft = 3;

    @ElementCollection
    @Column
    private List<SnackVotesModel> snackList = new ArrayList<SnackVotesModel>();

    @Column
    private String employeeSession; //Cookie

    @Column
    private String month;

    @Column
    private boolean canVote = true;

    public UserVoteModel(){}

    public UserVoteModel(String employeeSession){
        this.employeeSession = employeeSession;
        this.month = new Date().toString();
    }

    public UserVoteModel(String employeeSession, ArrayList<SnackVotesModel> snackList){
        this.employeeSession = employeeSession;
        this.snackList = snackList;
    }

    public  String getEmployeeSession(){return this.employeeSession;}

    public void setEmployeeSession(String employeeSession) {this.employeeSession = employeeSession;}

    public String getMonth() {return this.month;}

    public void setMonth(String month) {
        this.month = month;
    }

    public boolean isCanVote() {
        return canVote;
    }

    public int getNumOfVotesLeft() {
        return this.numOfVotesLeft;
    }

    public String getNumOfVoteLeftStr() {return Integer.toString(this.getNumOfVotesLeft()); }

    public void addSnack(SnackVotesModel snack){this.snackList.add(snack);}

    public void removeSnack(SnackVotesModel snack){this.snackList.remove(this.snackList.indexOf(snack));}


    public void setSnackList(ArrayList<SnackVotesModel> snackList) {
        this.snackList = snackList;
    }

    public List<SnackVotesModel> getSnackList() {
        return snackList;
    }


    public void setCanVote(boolean canVote) {
        if (this.numOfVotesLeft == 0){
            this.canVote = false;
        }
        else {
            this.canVote = canVote;
        }
    }

    public void setNumOfVotesLeft(int numOfVotesLeft) {
        this.numOfVotesLeft = numOfVotesLeft;
    }

    public void decrementNumOfVotesLeft(){
        this.numOfVotesLeft= this.numOfVotesLeft - 1;
    }

    public void incrementNumOfVotesLeft() {this.numOfVotesLeft++;}

    /**
     * Checks if the snack already exists and if it does not it returns that it can be added
     * @param snack
     * @return
     */
    public boolean canAddSnack(SnackVotesModel snack){
        boolean canAdd =false;
        if (this.snackList.size() ==0) {canAdd = true;}
        for (SnackVotesModel snackVotesModel: this.snackList){
            if (snackVotesModel.getName().equals(snack.getName())){
                canAdd = false;
            }
            else{
                canAdd = true;
            }
        }
        return  canAdd;
    }

    public boolean votedForSnack (SnackVotesModel snack){
        boolean voted =false;
        if (this.snackList.size() ==0) {voted = false;}
        for (SnackVotesModel snackVotesModel: this.snackList){
            if (snackVotesModel.getName().equals(snack.getName())){
                voted = true;
            }
            else{
                voted = false;
            }
        }
        return  voted;
    }

    public String toString(){
        return   "[employeeSession: " + this.employeeSession + ", numberOfVotesLeft: " + this.numOfVotesLeft +
                ", canVote: " + this.canVote + ", snacksVotedFor: " +
                this.snackList.toString() + "]";
    }


    public void reset() {
        this.setNumOfVotesLeft(3);
        this.snackList = new ArrayList<SnackVotesModel>();
        this.canVote = true;
    }
}

