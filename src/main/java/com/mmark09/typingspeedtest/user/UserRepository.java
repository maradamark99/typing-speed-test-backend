package com.mmark09.typingspeedtest.user;

import com.mmark09.typingspeedtest.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
}