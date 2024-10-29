package com.makechi.invizio.dto.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.makechi.invizio.collections.order.OrderStatus;
import com.makechi.invizio.collections.order.OrderType;
import com.makechi.invizio.dto.customer.ModelCustomerDto;
import com.makechi.invizio.dto.supplier.ModelSupplierDto;
import com.makechi.invizio.dto.user.ModelUserDto;
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