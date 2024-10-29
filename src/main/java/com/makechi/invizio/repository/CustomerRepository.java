package com.makechi.invizio.repository;

import com.makechi.invizio.collections.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CustomerRepository extends MongoRepository<Customer, String> {
    List<Customer> findByNameContainingIgnoreCase(String name);

    boolean existsByEmail(String email);
}
