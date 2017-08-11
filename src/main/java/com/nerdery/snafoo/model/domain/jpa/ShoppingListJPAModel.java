package com.nerdery.snafoo.model.domain.jpa;

import com.nerdery.snafoo.model.domain.rest.SnackRESTModel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jasminefarley on 7/14/17.
 */

@Entity
@Table(name="shoppinglist")
public class ShoppingListJPAModel implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String month;

    @Column
    private ArrayList<SnackVotesModel> snackList = new ArrayList<SnackVotesModel>();

    protected ShoppingListJPAModel(){}

    public ShoppingListJPAModel(String month, ArrayList<SnackVotesModel> snackList){
        this.month = month;
        this.snackList = snackList;
    }

    public void addToShoppingList(SnackVotesModel snackVotesModel){
        this.snackList.add(snackVotesModel);
    }

    public void setSnackList(ArrayList<SnackVotesModel> snackList) {
        this.snackList = snackList;
    }

    public List<SnackVotesModel> getSnackList() {
        return snackList;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getMonth(){
        return this.month;
    }
}
