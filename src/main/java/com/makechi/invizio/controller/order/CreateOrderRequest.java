package com.makechi.invizio.controller.order;

import com.makechi.invizio.dto.item.OrderItemDto;

import java.util.List;

public record CreateOrderRequest(String userId, List<OrderItemDto> orderItems) {
}
