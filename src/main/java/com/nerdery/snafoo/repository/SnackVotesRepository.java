package com.nerdery.snafoo.repository;

import com.nerdery.snafoo.model.domain.jpa.SnackVotesModel;
import com.nerdery.snafoo.repository.jpa.SnackVotesRepo;

/**
 * Base interface for respositories providing access to SnackVotes models. It can be safely deleted once you have implemented your
 * own repository(ies).
 */

public interface SnackVotesRepository extends SnackVotesRepo {
    SnackVotesModel findByRestId(int restId);
    Iterable<SnackVotesModel> findAll();
    SnackVotesModel save(SnackVotesModel snackVotesModel);
}
