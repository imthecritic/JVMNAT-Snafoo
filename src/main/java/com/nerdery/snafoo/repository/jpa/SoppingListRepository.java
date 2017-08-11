package com.nerdery.snafoo.repository.jpa;

import com.nerdery.snafoo.model.domain.jpa.ShoppingListJPAModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by jasminefarley on 7/20/17.
 */

@Repository
public interface SoppingListRepository extends CrudRepository<ShoppingListJPAModel, Long>{

}
