package com.makbe.ims.repository;

import com.makbe.ims.collections.order.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {
}
