package com.makbe.ims.controller.item;

import lombok.Data;

@Data
public class AddUpdateItemRequest {
    private String brand;
    private String category;
    private String model;
    private String name;
    private double price;
    private double quantity;
    private String supplier;
    private int stockAlert;
}
