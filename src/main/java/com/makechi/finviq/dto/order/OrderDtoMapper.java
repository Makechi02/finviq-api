package com.makechi.finviq.dto.order;

import com.makechi.finviq.collections.order.Order;
import com.makechi.finviq.collections.order.OrderItem;
import com.makechi.finviq.dto.customer.CustomerDtoMapper;
import com.makechi.finviq.dto.customer.ModelCustomerDto;
import com.makechi.finviq.dto.supplier.ModelSupplierDto;
import com.makechi.finviq.dto.supplier.SupplierDtoMapper;
import com.makechi.finviq.dto.user.UserMapper;
import com.makechi.finviq.repository.CustomerRepository;
import com.makechi.finviq.repository.SupplierRepository;
import com.makechi.finviq.service.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class OrderDtoMapper implements Function<Order, OrderDto> {

    private final CustomerRepository customerRepository;
    private final SupplierRepository supplierRepository;
    private final UserMapper userMapper;
    private final ItemService itemService;
    private final CustomerDtoMapper customerDtoMapper;
    private final SupplierDtoMapper supplierDtoMapper;

    @Override
    public OrderDto apply(Order order) {

        ModelCustomerDto customer = null;
        ModelSupplierDto supplier = null;

        switch (order.getOrderType()) {
            case SALE -> {
                var customerEntity = customerRepository.findById(order.getUserId().toHexString()).orElseThrow();
                customer = customerDtoMapper.toModelCustomerDto(customerEntity);
            }
            case PURCHASE -> {
                var supplierEntity = supplierRepository.findById(order.getUserId().toHexString()).orElseThrow();
                supplier = supplierDtoMapper.toModelSupplierDto(supplierEntity);
            }
        }

        return OrderDto.builder()
                .id(order.getId())
                .orderType(order.getOrderType())
                .orderDate(order.getOrderDate())
                .orderStatus(order.getOrderStatus())
                .supplier(supplier)
                .customer(customer)
                .orderItems(
                        order.getOrderItems()
                                .stream()
                                .map(this::mapToItemOrderDto)
                                .toList()
                )
                .createdBy(userMapper.toModelUserDto(order.getCreatedBy().toHexString()))
                .createdAt(order.getCreatedAt())
                .updatedBy(userMapper.toModelUserDto(order.getUpdatedBy().toHexString()))
                .updatedAt(order.getUpdatedAt())
                .build();
    }

    private ItemOrderDto mapToItemOrderDto(OrderItem orderItem) {
        var item = itemService.getItemById(orderItem.itemId().toHexString());
        return new ItemOrderDto(item.getId(), item.getName(), orderItem.quantity(), orderItem.price());
    }
}