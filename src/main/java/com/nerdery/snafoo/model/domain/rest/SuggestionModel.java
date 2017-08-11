package com.nerdery.snafoo.model.domain.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by jasminefarley on 7/14/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class SuggestionModel {


    @JsonProperty("name")
    private String name;

    @JsonProperty("location")
    private String location;

    private String suggestions;

    public SuggestionModel(){}

    public SuggestionModel(String name, String location){
        this.name = name;
        this.location = location;
    }

    public SuggestionModel(String name, String location, String  suggestions){
        this.name = name;
        this.location = location;
        this.suggestions = suggestions;
    }

    public String getName(){return this.name;}

    public String getLocation() {return this.location;}

    public void setName(String name){this.name = name;}

    public void setLocation(String location) {this.location = location;}

    public void setSuggestions(String suggestions){this.suggestions = suggestions;}

    public String getSuggestions(){return this.suggestions;}

    public String toString(){
        return "{\"name\": \"" + this.getName() + "\", \"location\": \"" + this.getLocation() +"\"}";
    }


}
