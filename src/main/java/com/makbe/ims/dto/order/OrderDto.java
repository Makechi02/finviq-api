package com.makbe.ims.dto.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.makbe.ims.collections.order.OrderStatus;
import com.makbe.ims.collections.order.OrderType;
import com.makbe.ims.dto.customer.ModelCustomerDto;
import com.makbe.ims.dto.supplier.ModelSupplierDto;
import com.makbe.ims.dto.user.ModelUserDto;
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