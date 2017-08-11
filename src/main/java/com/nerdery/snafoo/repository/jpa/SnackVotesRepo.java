package com.nerdery.snafoo.repository.jpa;

import com.nerdery.snafoo.model.domain.jpa.SnackVotesModel;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;


/**
 * Created by jasminefarley on 7/13/17.
 */

@Repository
public interface SnackVotesRepo extends CrudRepository<SnackVotesModel, Long> {

}
