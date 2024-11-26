package com.makbe.ims.controller.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddUpdateItemRequest {
    private String brand;
    private String category;
    private String model;
    private String name;
    private double price;
    private double quantity;
    private int stockAlert;
}
