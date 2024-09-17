package com.makbe.ims.repository;

import com.makbe.ims.collections.Supplier;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SupplierRepository extends MongoRepository<Supplier, String> {
    boolean existsByPhone(String phone);
}
