package com.makbe.ims.dto.order;

import com.makbe.ims.collections.order.Order;
import com.makbe.ims.collections.order.OrderItem;
import com.makbe.ims.dto.customer.CustomerDtoMapper;
import com.makbe.ims.dto.customer.ModelCustomerDto;
import com.makbe.ims.dto.supplier.ModelSupplierDto;
import com.makbe.ims.dto.supplier.SupplierDtoMapper;
import com.makbe.ims.dto.user.UserMapper;
import com.makbe.ims.repository.CustomerRepository;
import com.makbe.ims.repository.SupplierRepository;
import com.makbe.ims.repository.UserRepository;
import com.makbe.ims.service.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class OrderDtoMapper implements Function<Order, OrderDto> {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final SupplierRepository supplierRepository;
    private final UserMapper userMapper;
    private final ItemService itemService;
    private final CustomerDtoMapper customerDtoMapper;
    private final SupplierDtoMapper supplierDtoMapper;

    @Override
    public OrderDto apply(Order order) {

        var createdBy = userMapper.toModelUserDto(userRepository.findById(order.getCreatedBy().toHexString()).orElseThrow());
        var updatedBy = userMapper.toModelUserDto(userRepository.findById(order.getUpdatedBy().toHexString()).orElseThrow());

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
                .createdBy(createdBy)
                .createdAt(order.getCreatedAt())
                .updatedBy(updatedBy)
                .updatedAt(order.getUpdatedAt())
                .build();
    }

    private ItemOrderDto mapToItemOrderDto(OrderItem orderItem) {
        var item = itemService.getItemById(orderItem.itemId().toHexString());
        return new ItemOrderDto(item.getId(), item.getName(), orderItem.quantity(), orderItem.price());
    }
}