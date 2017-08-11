package com.nerdery.snafoo.model.domain.rest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;


/**
 * SnackVotesModel Domain Model
 * A domain class to contain the data needed from the RESTful Web Service
 * that represents a single snack
 */


@JsonIgnoreProperties(ignoreUnknown = true)
public class SnackRESTModel {

    @JsonProperty("id")
    private int restId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("optional")
    private  boolean optional;

    @JsonProperty("purchaseLocations")
    private String purchaseLocations;

    @JsonProperty("purchaseCount")
    private int purchaseCount;

    @JsonProperty("lastPurchaseDate")
    private String lastPurchaseDate;

    public SnackRESTModel(){}

    public int getRestId() { return this.restId;}

    public void setRestId(int restid) {this.restId = restid;}

    public String getName(){
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public int getPurchaseCount(){ return this.purchaseCount;}

    public void setPurchaseCount(int purchaseCount){this.purchaseCount = purchaseCount;}

    public String toString(){
        return "[Id: " + this.restId + ", name: " + this.name + ", optional: " + this.optional + ", purchaseLocations: " +
                this.purchaseLocations + ", lastPurchaseDate: " + this.lastPurchaseDate + ", purchaseCount: "
                + this.purchaseCount + "]";
    }
}
