package com.makbe.ims.collections;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    private String category;
    @CreatedDate
    private LocalDateTime createdAt;
    @CreatedBy
    private String createdBy;
    private String model;
    private String name;
    private double price;
    private double quantity;
    private String sku;
    private String supplier;
    private int stockAlert;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @LastModifiedBy
    private String updatedBy;
}
