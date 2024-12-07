package com.makechi.finviq.controller.order;

import com.makechi.finviq.dto.item.OrderItemDto;

import java.util.List;

public record CreateOrderRequest(String userId, List<OrderItemDto> orderItems) {
}
