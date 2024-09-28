package com.makbe.ims.repository;

import com.makbe.ims.collections.Supplier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface SupplierRepository extends MongoRepository<Supplier, String> {
    boolean existsByPhone(String phone);

    @Query("{'$or': [{'name': {'$regex': ?0, '$options': 'i'}}, {'address': {'$regex': ?0, '$options': 'i'}}, {'phone': {'$regex': ?0, '$options': 'i'}}]}")
    List<Supplier> searchByKeyword(String query);

    List<Supplier> findTop5ByOrderByAddedAtDesc();
}
