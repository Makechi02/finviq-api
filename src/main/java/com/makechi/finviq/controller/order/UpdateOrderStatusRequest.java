package com.makechi.finviq.controller.order;

import com.makechi.finviq.collections.order.OrderStatus;

public record UpdateOrderStatusRequest(OrderStatus orderStatus) {
}
