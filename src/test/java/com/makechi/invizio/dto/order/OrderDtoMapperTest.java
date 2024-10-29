package com.makechi.invizio.dto.order;

import com.makechi.invizio.collections.Customer;
import com.makechi.invizio.collections.Supplier;
import com.makechi.invizio.collections.User;
import com.makechi.invizio.collections.order.Order;
import com.makechi.invizio.collections.order.OrderItem;
import com.makechi.invizio.collections.order.OrderStatus;
import com.makechi.invizio.collections.order.OrderType;
import com.makechi.invizio.dto.customer.CustomerDtoMapper;
import com.makechi.invizio.dto.customer.ModelCustomerDto;
import com.makechi.invizio.dto.item.ItemDto;
import com.makechi.invizio.dto.supplier.ModelSupplierDto;
import com.makechi.invizio.dto.supplier.SupplierDtoMapper;
import com.makechi.invizio.dto.user.ModelUserDto;
import com.makechi.invizio.dto.user.UserMapper;
import com.makechi.invizio.repository.CustomerRepository;
import com.makechi.invizio.repository.SupplierRepository;
import com.makechi.invizio.repository.UserRepository;
import com.makechi.invizio.service.item.ItemService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderDtoMapperTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private SupplierRepository supplierRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private ItemService itemService;

    @Mock
    private CustomerDtoMapper customerDtoMapper;

    @Mock
    private SupplierDtoMapper supplierDtoMapper;

    @InjectMocks
    private OrderDtoMapper orderDtoMapper;

    private final ModelUserDto createdByDto = new ModelUserDto("670258595df0332b7901a83a", "Makechi Eric");
    private final ModelUserDto updatedByDto = new ModelUserDto("670258595df0332b7901a83a", "Makechi Eric");

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldMapOrderToOrderDtoForSale() {
        ObjectId userId = new ObjectId("6702c5fe3969533b2f0ff595");
        ObjectId createdBy = new ObjectId();
        ObjectId updatedBy = new ObjectId();
        LocalDateTime orderDate = LocalDateTime.now();
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();
        OrderItem orderItem = new OrderItem(new ObjectId("6702c6be3969533b2f0ff597"), 10, BigDecimal.valueOf(2000));

        Order order = Order.builder()
                .id("6702e8c15aa0665e98e28b2c")
                .orderType(OrderType.SALE)
                .orderDate(orderDate)
                .orderStatus(OrderStatus.PENDING)
                .userId(userId)
                .orderItems(List.of(orderItem))
                .createdBy(createdBy)
                .createdAt(createdAt)
                .updatedBy(updatedBy)
                .updatedAt(updatedAt)
                .build();

        Customer customer = mock(Customer.class);
        ModelCustomerDto customerDto = new ModelCustomerDto("6702c5fe3969533b2f0ff598", "Customer Name");

        User user = mock(User.class);
        ItemDto itemDto = ItemDto.builder()
                .id("6702c6be3969533b2f0ff597")
                .name("HP Elitebook 8440p")
                .build();

        when(userRepository.findById(createdBy.toHexString())).thenReturn(Optional.of(user));
        when(userRepository.findById(updatedBy.toHexString())).thenReturn(Optional.of(user));

        when(userMapper.toModelUserDto(user)).thenReturn(createdByDto);
        when(userMapper.toModelUserDto(user)).thenReturn(updatedByDto);

        when(customerRepository.findById(userId.toHexString())).thenReturn(Optional.of(customer));
        when(customerDtoMapper.toModelCustomerDto(customer)).thenReturn(customerDto);

        when(itemService.getItemById(orderItem.itemId().toHexString())).thenReturn(itemDto);

        OrderDto orderDto = orderDtoMapper.apply(order);

        assertNotNull(orderDto);
        assertEquals("6702e8c15aa0665e98e28b2c", orderDto.getId());
        assertEquals(OrderType.SALE, orderDto.getOrderType());
        assertEquals(orderDate, orderDto.getOrderDate());
        assertEquals(OrderStatus.PENDING, orderDto.getOrderStatus());

        assertNotNull(orderDto.getCustomer());
        assertEquals(customerDto.id(), orderDto.getCustomer().id());
        assertEquals(customerDto.name(), orderDto.getCustomer().name());

        assertEquals(1, orderDto.getOrderItems().size());
        ItemOrderDto itemOrderDto = orderDto.getOrderItems().getFirst();
        assertEquals(orderItem.itemId().toHexString(), itemOrderDto.itemId());
        assertEquals("HP Elitebook 8440p", itemOrderDto.name());
        assertEquals(10, itemOrderDto.quantity());
        assertEquals(BigDecimal.valueOf(2000), itemOrderDto.price());

        assertEquals("Makechi Eric", orderDto.getCreatedBy().name());
        assertEquals("Makechi Eric", orderDto.getUpdatedBy().name());
    }

    @Test
    public void shouldMapOrderToOrderDtoForPurchase() {
        ObjectId userId = new ObjectId("6702c5fe3969533b2f0ff595");
        ObjectId createdBy = new ObjectId();
        ObjectId updatedBy = new ObjectId();
        LocalDateTime orderDate = LocalDateTime.now();
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();
        OrderItem orderItem = new OrderItem(new ObjectId("6702c6be3969533b2f0ff597"), 10, BigDecimal.valueOf(2000));

        Order order = Order.builder()
                .id("6702e8c15aa0665e98e28b2c")
                .orderType(OrderType.PURCHASE)
                .orderDate(orderDate)
                .orderStatus(OrderStatus.PENDING)
                .userId(userId)
                .orderItems(List.of(orderItem))
                .createdBy(createdBy)
                .createdAt(createdAt)
                .updatedBy(updatedBy)
                .updatedAt(updatedAt)
                .build();

        Supplier supplier = mock(Supplier.class);
        ModelSupplierDto supplierDto = new ModelSupplierDto("6702c5fe3969533b2f0ff598", "Supplier Name");

        User user = mock(User.class);
        ItemDto itemDto = ItemDto.builder()
                .id("6702c6be3969533b2f0ff597")
                .name("HP Elitebook 8440p")
                .build();

        when(userRepository.findById(createdBy.toHexString())).thenReturn(Optional.of(user));
        when(userRepository.findById(updatedBy.toHexString())).thenReturn(Optional.of(user));

        when(userMapper.toModelUserDto(user)).thenReturn(createdByDto);
        when(userMapper.toModelUserDto(user)).thenReturn(updatedByDto);

        when(supplierRepository.findById(userId.toHexString())).thenReturn(Optional.of(supplier));
        when(supplierDtoMapper.toModelSupplierDto(supplier)).thenReturn(supplierDto);

        when(itemService.getItemById(orderItem.itemId().toHexString())).thenReturn(itemDto);

        OrderDto orderDto = orderDtoMapper.apply(order);

        assertNotNull(orderDto);
        assertEquals("6702e8c15aa0665e98e28b2c", orderDto.getId());
        assertEquals(OrderType.PURCHASE, orderDto.getOrderType());
        assertEquals(orderDate, orderDto.getOrderDate());
        assertEquals(OrderStatus.PENDING, orderDto.getOrderStatus());

        assertNotNull(orderDto.getSupplier());
        assertEquals(supplierDto.id(), orderDto.getSupplier().id());
        assertEquals(supplierDto.name(), orderDto.getSupplier().name());

        assertEquals(1, orderDto.getOrderItems().size());
        ItemOrderDto itemOrderDto = orderDto.getOrderItems().getFirst();
        assertEquals(orderItem.itemId().toHexString(), itemOrderDto.itemId());
        assertEquals("HP Elitebook 8440p", itemOrderDto.name());
        assertEquals(10, itemOrderDto.quantity());
        assertEquals(BigDecimal.valueOf(2000), itemOrderDto.price());

        assertEquals("Makechi Eric", orderDto.getCreatedBy().name());
        assertEquals("Makechi Eric", orderDto.getUpdatedBy().name());
    }
}
