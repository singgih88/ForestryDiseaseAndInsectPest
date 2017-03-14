package com.jecyhw.mvc.repository;

import com.jecyhw.mvc.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by jecyhw on 16-11-9.
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByUserName(String userName);
}
