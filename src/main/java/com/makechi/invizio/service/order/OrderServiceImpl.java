package com.makechi.invizio.service.order;

import com.makechi.invizio.collections.order.Order;
import com.makechi.invizio.collections.order.OrderItem;
import com.makechi.invizio.collections.order.OrderStatus;
import com.makechi.invizio.collections.order.OrderType;
import com.makechi.invizio.controller.order.CreateOrderRequest;
import com.makechi.invizio.controller.order.UpdateOrderStatusRequest;
import com.makechi.invizio.dto.item.ItemDto;
import com.makechi.invizio.dto.item.OrderItemDto;
import com.makechi.invizio.dto.order.OrderDto;
import com.makechi.invizio.dto.order.OrderDtoMapper;
import com.makechi.invizio.exception.RequestValidationException;
import com.makechi.invizio.exception.ResourceNotFoundException;
import com.makechi.invizio.repository.CustomerRepository;
import com.makechi.invizio.repository.OrderRepository;
import com.makechi.invizio.repository.SupplierRepository;
import com.makechi.invizio.service.item.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ItemService itemService;
    private final SupplierRepository supplierRepository;
    private final CustomerRepository customerRepository;
    private final OrderDtoMapper orderDtoMapper;

    @Override
    public OrderDto createSalesOrder(CreateOrderRequest request) {
        log.info("Creating sales order for userId: {}", request.userId());

        checkIfCustomerExists(request.userId());

        var order = Order.builder()
                .userId(new ObjectId(request.userId()))
                .orderDate(LocalDateTime.now())
                .orderStatus(OrderStatus.PENDING)
                .orderType(OrderType.SALE)
                .build();

        processOrderItems(request.orderItems(), order);
        log.info("Sales order created successfully for userId: {}", request.userId());
        order = orderRepository.save(order);
        return orderDtoMapper.apply(order);
    }

    @Override
    public OrderDto createPurchasesOrder(CreateOrderRequest request) {
        log.info("Creating purchases order for supplierId: {}", request.userId());

        checkIfSupplierExists(request.userId());

        var order = Order.builder()
                .userId(new ObjectId(request.userId()))
                .orderDate(LocalDateTime.now())
                .orderStatus(OrderStatus.PENDING)
                .orderType(OrderType.PURCHASE)
                .build();

        processOrderItems(request.orderItems(), order);
        log.info("Purchases order created successfully for supplierId: {}", request.userId());
        order = orderRepository.save(order);
        return orderDtoMapper.apply(order);
    }

    @Override
    public OrderDto updateOrderStatus(String orderId, UpdateOrderStatusRequest request) {
        log.info("Updating status of order with id: {} to {}", orderId, request.orderStatus());

        var order = orderRepository.findById(orderId)
                .orElseThrow(() -> {
                    log.error("Order with id {} not found", orderId);
                    return new ResourceNotFoundException("Order with id " + orderId + " not found");
                });

        if (order.getOrderStatus() == OrderStatus.COMPLETED) {
            log.error("Cannot update completed order with id: {}", orderId);
            throw new RequestValidationException("Cannot update completed order");
        }

        if (request.orderStatus() == OrderStatus.COMPLETED) {
            updateStockForOrder(order);
            log.info("Stock updated for completed order with id: {}", orderId);
        }

        order.setOrderStatus(request.orderStatus());
        log.info("Order status updated successfully for orderId: {}", orderId);
        order = orderRepository.save(order);
        return orderDtoMapper.apply(order);
    }

    private void processOrderItems(List<OrderItemDto> orderItems, Order order) {
        List<OrderItem> processedItems = new ArrayList<>();
        for (OrderItemDto orderItemDto : orderItems) {
            ItemDto item = itemService.getItemById(orderItemDto.itemId());

            BigDecimal price = switch (order.getOrderType()) {
                case PURCHASE -> item.getCostPrice().multiply(BigDecimal.valueOf(orderItemDto.quantity()));
                case SALE -> item.getRetailPrice().multiply(BigDecimal.valueOf(orderItemDto.quantity()));
            };

            var orderItem = new OrderItem(new ObjectId(item.getId()), orderItemDto.quantity(), price);

            processedItems.add(orderItem);
            log.debug("Processed order item: {}", orderItem);
        }
        order.setOrderItems(processedItems);
        log.info("Processed order items for order with id: {}", order);
    }

    private void updateStockForOrder(Order order) {
        for (OrderItem orderItem : order.getOrderItems()) {
            switch (order.getOrderType()) {
                case PURCHASE -> {
                    itemService.increaseStock(orderItem.itemId().toHexString(), orderItem.quantity());
                    log.debug("Increased stock for itemId: {} by {}", orderItem.itemId().toHexString(), orderItem.quantity());
                }
                case SALE -> {
                    itemService.decreaseStock(orderItem.itemId().toHexString(), orderItem.quantity());
                    log.debug("Decreased stock for itemId: {} by {}", orderItem.itemId().toHexString(), orderItem.quantity());
                }
            }
        }
    }

    @Override
    public List<OrderDto> getAllOrders() {
        log.info("Fetching all orders");
        return orderRepository.findAll()
                .stream()
                .map(orderDtoMapper)
                .toList();
    }

    @Override
    public OrderDto getOrderById(String id) {
        log.info("Fetching order with id: {}", id);
        return orderRepository.findById(id)
                .map(orderDtoMapper)
                .orElseThrow(() -> {
                    log.error("Order with id {} not found", id);
                    return new ResourceNotFoundException("Order with id " + id + " not found");
                });
    }

    @Override
    public void deleteOrder(String id) {
        log.info("Deleting order with id: {}", id);
        if (!orderRepository.existsById(id)) {
            log.error("Order with id {} not found", id);
            throw new ResourceNotFoundException("Order with id " + id + " not found");
        }
        orderRepository.deleteById(id);
        log.info("Order with id {} deleted successfully", id);
    }

    @Override
    public List<OrderDto> getSalesOrders() {
        log.info("Fetching all sales orders");
        return orderRepository.findAllByOrderType(OrderType.SALE)
                .stream()
                .map(orderDtoMapper)
                .toList();
    }

    @Override
    public List<OrderDto> getPurchasesOrders() {
        log.info("Fetching all purchases orders");
        return orderRepository.findAllByOrderType(OrderType.PURCHASE)
                .stream()
                .map(orderDtoMapper)
                .toList();
    }

    @Override
    public List<OrderDto> getOrdersByCustomer(String customerId) {
        log.info("Fetching all orders by customer {}", customerId);

        checkIfCustomerExists(customerId);

        return orderRepository.findAllByUserId(new ObjectId(customerId))
                .stream()
                .map(orderDtoMapper)
                .toList();
    }

    @Override
    public List<OrderDto> getOrdersBySupplier(String supplierId) {
        log.info("Fetching all orders by supplier {}", supplierId);

        checkIfSupplierExists(supplierId);

        return orderRepository.findAllByUserId(new ObjectId(supplierId))
                .stream()
                .map(orderDtoMapper)
                .toList();
    }

    private void checkIfCustomerExists(String customerId) {
        if (!customerRepository.existsById(customerId)) {
            log.error("Customer with id {} not found", customerId);
            throw new ResourceNotFoundException("Customer with id " + customerId + " not found");
        }
    }

    private void checkIfSupplierExists(String supplierId) {
        if (!supplierRepository.existsById(supplierId)) {
            log.error("Supplier with id {} not found", supplierId);
            throw new ResourceNotFoundException("Supplier with id " + supplierId + " not found");
        }
    }
}
