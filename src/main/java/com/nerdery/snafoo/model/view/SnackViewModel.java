package com.nerdery.snafoo.model.view;

import com.nerdery.snafoo.model.domain.jpa.SnackVotesModel;
import com.nerdery.snafoo.model.domain.rest.SnackRESTModel;

import java.util.ArrayList;

/**
 * Snack View  Model
 *
 */


public class SnackViewModel {

    private ArrayList<SnackRESTModel> snackRESTModels;

    private ArrayList<SnackVotesModel> snackVotesModels;

    public SnackViewModel(ArrayList<SnackRESTModel> snackRESTModels){
        this.snackRESTModels = snackRESTModels;
    }

    public ArrayList<String> getAlwaysPurchasedSnackNames(){
        ArrayList<String> nonOptionalSnackNames = new ArrayList<String>();
        for (SnackRESTModel snack: this.snackRESTModels){
            if (snack.isOptional() == false){
                nonOptionalSnackNames.add(snack.getName());
            }
        }
        return nonOptionalSnackNames;
    }


}
