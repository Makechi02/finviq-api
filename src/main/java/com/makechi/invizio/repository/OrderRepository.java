package com.makechi.invizio.repository;

import com.makechi.invizio.collections.order.Order;
import com.makechi.invizio.collections.order.OrderType;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {

    List<Order> findAllByOrderType(OrderType orderType);
    List<Order> findAllByUserId(ObjectId userId);

}
