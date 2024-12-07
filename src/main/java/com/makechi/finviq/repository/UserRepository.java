package com.makechi.finviq.repository;

import com.makechi.finviq.collections.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("{'$or': [{'name': {'$regex': ?0, '$options': 'i'}}, {'email': {'$regex': ?0, '$options': 'i'}}, {'role': {'$regex': ?0, '$options': 'i'}}]}")
    List<User> searchByKeyword(String keyword);

    List<User> findTop5ByOrderByCreatedAtDesc();
}
