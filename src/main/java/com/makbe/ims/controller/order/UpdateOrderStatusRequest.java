package com.makbe.ims.controller.order;

import com.makbe.ims.collections.order.OrderStatus;

public record UpdateOrderStatusRequest(OrderStatus orderStatus) {
}
