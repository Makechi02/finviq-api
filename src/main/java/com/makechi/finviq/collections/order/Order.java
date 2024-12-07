package com.makechi.finviq.collections.order;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Document(collection = "orders")
public class Order {
    @Id
    private String id;
    private OrderType orderType;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private ObjectId userId;
    private List<OrderItem> orderItems;
    @CreatedBy
    private ObjectId createdBy;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedBy
    private ObjectId updatedBy;
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
