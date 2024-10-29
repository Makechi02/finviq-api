package com.makbe.ims.service.order;

import com.makbe.ims.controller.order.CreateOrderRequest;
import com.makbe.ims.controller.order.UpdateOrderStatusRequest;
import com.makbe.ims.dto.order.OrderDto;

import java.util.List;

public interface OrderService {

    List<OrderDto> getAllOrders();

    OrderDto getOrderById(String id);

    void deleteOrder(String id);

    OrderDto createSalesOrder(CreateOrderRequest request);

    OrderDto createPurchasesOrder(CreateOrderRequest request);

    OrderDto updateOrderStatus(String orderId, UpdateOrderStatusRequest request);

    List<OrderDto> getSalesOrders();

    List<OrderDto> getPurchasesOrders();

    List<OrderDto> getOrdersByCustomer(String customerId);

    List<OrderDto> getOrdersBySupplier(String supplierId);
}
