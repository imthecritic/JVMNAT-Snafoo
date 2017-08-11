package com.nerdery.snafoo.repository;

import com.nerdery.snafoo.model.domain.rest.SnackRESTModel;

/**
 * Snack Repository Interface
 */
public interface SnackRepository {
    SnackRESTModel[] getSnacks();
}
