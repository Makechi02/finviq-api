package com.makbe.ims.repository;

import com.makbe.ims.collections.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ItemRepository extends MongoRepository<Item, String> {
    Optional<Item> findBySku(String sku);

    boolean existsByName(String name);

    boolean existsByBrand(String brand);

    boolean existsByModel(String model);
}
