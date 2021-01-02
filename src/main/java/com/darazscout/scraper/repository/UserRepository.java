package com.darazscout.scraper.repository;

import com.darazscout.scraper.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserRepository extends MongoRepository<User, String> {
    User findUserByUsername(String userName);
    User findUserByEmail(String email);
}
