package com.makechi.invizio.service.order;

import com.makechi.invizio.controller.order.CreateOrderRequest;
import com.makechi.invizio.controller.order.UpdateOrderStatusRequest;
import com.makechi.invizio.dto.order.OrderDto;

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
