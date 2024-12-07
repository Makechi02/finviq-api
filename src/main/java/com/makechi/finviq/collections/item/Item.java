package com.makechi.finviq.collections.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
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
    private BigDecimal costPrice;
    @CreatedDate
    private LocalDateTime createdAt;
    @CreatedBy
    private ObjectId createdBy;
    private String model;
    private String name;
    private double quantity;
    private BigDecimal retailPrice;
    private String sku;
    private int stockAlert;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @LastModifiedBy
    private ObjectId updatedBy;
    private BigDecimal vatInclusivePrice;
}
