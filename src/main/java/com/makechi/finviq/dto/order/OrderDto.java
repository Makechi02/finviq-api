package com.makechi.finviq.dto.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.makechi.finviq.collections.order.OrderStatus;
import com.makechi.finviq.collections.order.OrderType;
import com.makechi.finviq.dto.customer.ModelCustomerDto;
import com.makechi.finviq.dto.supplier.ModelSupplierDto;
import com.makechi.finviq.dto.user.ModelUserDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDto {

    private String id;
    private OrderType orderType;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private ModelCustomerDto customer;
    private ModelSupplierDto supplier;
    private List<ItemOrderDto> orderItems;
    private ModelUserDto createdBy;
    private LocalDateTime createdAt;
    private ModelUserDto updatedBy;
    private LocalDateTime updatedAt;

}