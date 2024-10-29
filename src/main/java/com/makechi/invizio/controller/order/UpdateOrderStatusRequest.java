package com.makechi.invizio.controller.order;

import com.makechi.invizio.collections.order.OrderStatus;

public record UpdateOrderStatusRequest(OrderStatus orderStatus) {
}
