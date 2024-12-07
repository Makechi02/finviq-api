package com.makechi.finviq.controller.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddUpdateItemRequest {
    private String brand;
    private String category;
    private String model;
    private String name;
    private String sku;
    private BigDecimal costPrice;
    private BigDecimal retailPrice;
    private BigDecimal vatInclusivePrice;
    private double quantity;
    private int stockAlert;
}
