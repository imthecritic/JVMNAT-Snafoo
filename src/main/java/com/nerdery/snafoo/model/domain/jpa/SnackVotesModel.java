package com.nerdery.snafoo.model.domain.jpa;

import com.nerdery.snafoo.model.domain.rest.SnackRESTModel;

import javax.persistence.*;
import java.io.Serializable;

/**
 * SnackVotesModel Domain Model
 * Store a SnackVotesModel object annotated as a JPA entity.
 */

@Entity
@Table(name="snackVotes")
public class SnackVotesModel implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private int restId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int purchaseCount = 0;

    @Column(nullable = false)
    private  String purchaseLocations;

    @Column(nullable = false)
    private  boolean optional = true;

    @Column(nullable = false)
    private String lastPurchaseDate = "N/A";

    @Column(nullable = false)
    private int votes = 0;



    public SnackVotesModel(){
        /**
         * The default constructor only exists for the sake of JPA
         */
    }

    public SnackVotesModel(String name, String purchaseLocations){
        this.name = name;
        this.purchaseLocations = purchaseLocations;
    }

    public SnackVotesModel(SnackRESTModel snackRESTModel){
        this.restId = snackRESTModel.getRestId();
        this.name = snackRESTModel.getName();
        this.purchaseCount = snackRESTModel.getPurchaseCount();
        this.purchaseLocations = snackRESTModel.getPurchaseLocations();
        this.optional = snackRESTModel.isOptional();
        this.lastPurchaseDate = this.getLastPurchaseDate();

    }

    public SnackVotesModel(int restId, String name, boolean optional, String purchaseLocations, int purchaseCount, String lastPurchaseDate){
        this.restId = restId;
        this.name = name;
        this.purchaseCount = purchaseCount;
        this.purchaseLocations = purchaseLocations;
        this.optional = optional;
        this.lastPurchaseDate = lastPurchaseDate;
    }


    public String getRestId() { return Integer.toString(this.restId);}

    public int getRestIdInt() {return  this.restId;}

    public void setRestidId(int restid) {this.restId = restid;}

    public String getName(){
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPurchaseCount(){return this.purchaseCount;}

    public void setPurchaseCount(int purchaseCount){this.purchaseCount = purchaseCount;}

    public boolean isOptional() {
        return optional;
    }

    public void setOptional( boolean optional )
    {
        this.optional = optional;
    }

    public  String getPurchaseLocations(){
        return  this.purchaseLocations;
    }

    public void setPurchaseLocations(String purchaseLocations){
        this.purchaseLocations = purchaseLocations;
    }

    public String getLastPurchaseDate() {
        return lastPurchaseDate;
    }

    public void setLastPurchasedDate( String lastPurchaseDate )
    {
        this.lastPurchaseDate = lastPurchaseDate;
    }

    public void addVote(){this.votes++;}

    public String getVotes() {
        return String.valueOf(this.votes);
    }

    public int getVotesInt() {return this.votes;}

    public int compareTo(SnackVotesModel that){
        int thatVotes = that.getVotesInt();
        int thisVotes = this.votes;

        if (thisVotes > thatVotes){
            return 1;

        }
        else if (thisVotes < thatVotes) {
            return -1;
        }

        else {
            return 0;
        }
    }

    public String toString(){
        return "[Id: " + this.restId + ", name: " + this.name + ", optional: " + this.optional + ", purchaseLocations: " +
                this.purchaseLocations + ", lastPurchaseDate: " + this.lastPurchaseDate + ", purchaseCount: "
                + this.purchaseCount + ", votes: " + this.votes + "]";
    }
}
