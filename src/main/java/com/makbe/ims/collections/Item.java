package com.makbe.ims.collections;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document(collection = "items")
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    private String id;
    private String brand;
    private ObjectId category;
    @CreatedDate
    private LocalDateTime createdAt;
    @CreatedBy
    private ObjectId createdBy;
    private String model;
    private String name;
    private double price;
    private double quantity;
    private String sku;
    private ObjectId supplier;
    private int stockAlert;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @LastModifiedBy
    private ObjectId updatedBy;
}
