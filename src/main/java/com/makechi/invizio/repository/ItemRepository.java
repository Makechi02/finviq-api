package com.makechi.invizio.repository;

import com.makechi.invizio.collections.item.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends MongoRepository<Item, String> {
    Optional<Item> findBySku(String sku);

    boolean existsByName(String name);

    boolean existsByBrand(String brand);

    boolean existsByModel(String model);

    @Query("{'$or': [{'brand': {'$regex': ?0, '$options': 'i'}}, {'model': {'$regex': ?0, '$options': 'i'}}, {'name': {'$regex': ?0, '$options': 'i'}}, {'sku': {'$regex': ?0, '$options': 'i'}}]}")
    Page<Item> searchByKeyword(String query, PageRequest pageRequest);

    List<Item> findTop5ByOrderByCreatedAtDesc();

    @Query(value = "{}", fields = "{costPrice: 1, quantity: 1}")
    List<Item> findAllForInventoryValuation();

    @Query(value = "{}", fields = "{name: 1, quantity: 1, stockAlert: 1}")
    List<Item> findAllForStockLevels();
}
