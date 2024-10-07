package com.makbe.ims.controller.order;

import com.makbe.ims.dto.item.OrderItemDto;

import java.util.List;

public record CreateOrderRequest(String userId, List<OrderItemDto> orderItems) {
}
