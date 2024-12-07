package com.makechi.finviq.repository;

import com.makechi.finviq.collections.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends MongoRepository<Category, String> {

    Optional<Category> findByName(String name);

    boolean existsByName(String name);

    @Query("{'$or': [{'name': {'$regex': ?0, '$options': 'i'}}]}")
    List<Category> searchByKeyword(String query);

    List<Category> findTop5ByOrderByCreatedAtDesc();
}
