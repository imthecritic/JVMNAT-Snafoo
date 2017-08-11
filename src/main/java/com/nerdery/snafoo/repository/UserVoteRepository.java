package com.nerdery.snafoo.repository;

import com.nerdery.snafoo.model.domain.jpa.UserVoteModel;
import com.nerdery.snafoo.repository.jpa.VoteRepository;

/**
 * Created by jasminefarley on 7/21/17.
 */
public interface UserVoteRepository extends VoteRepository {
    UserVoteModel findByEmployeeSession(String employeeSession);
    UserVoteModel save(UserVoteModel userVoteModel);
}
