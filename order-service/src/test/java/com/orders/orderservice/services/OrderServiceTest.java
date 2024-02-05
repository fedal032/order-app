package com.orders.orderservice.services;

import com.orders.orderservice.dto.OrderDetailsDto;
import com.orders.orderservice.dto.OrdersDto;
import com.orders.orderservice.dto.PurchasedItemsInfoDto;
import com.orders.orderservice.dto.TotalItemsInfoDto;
import com.orders.orderservice.entity.OrderDetail;
import com.orders.orderservice.entity.OrderStatus;
import com.orders.orderservice.entity.Orders;
import com.orders.orderservice.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class OrderServiceTest
{
    @Autowired
    private OrderRepository orderRepository;

    @InjectMocks
    @Autowired
    private OrderService orderService;

    @MockBean
    JmsTemplate jmsTemplate;

    @BeforeEach
    void setUp()
    {
        doNothing().when(jmsTemplate).convertAndSend(any(String.class), any(Object.class));
    }

    @Test
    @Transactional
    public void retrieveAll()
    {
        List<OrdersDto> all = orderService.retrieveAll();
        assertEquals(all.size(), 4);
    }

    @Test
    void retrieveAllPlacedOrdersByCustomer()
    {
        List<OrdersDto> ordersDtos = orderService.retrieveAllPlacedOrdersByCustomer("cust 1");
        assertEquals(ordersDtos.size(), 1);
        for (OrdersDto dto : ordersDtos)
        {
            assertEquals(OrderStatus.PLACED.name(), dto.getStatus());
        }
    }

    @Test
    @Transactional
    void createOrder()
    {
        String newOrderCode = "new order";
        OrderDetailsDto orderDetailsDto = OrderDetailsDto.builder()
                .code("od-new")
                .category("devices")
                .price(BigDecimal.valueOf(1000L))
                .amount(1L)
                .build();
        OrdersDto ordersDto = OrdersDto.builder()
                .code(newOrderCode)
                .status(OrderStatus.PLACED.name())
                .createdDate(new Date())
                .customerCode("cust 4")
                .orderDetails(Collections.singletonList(orderDetailsDto))
                .build();

        orderService.createOrder(ordersDto);
        verify(jmsTemplate, times(1)).convertAndSend(any(String.class), any(Object.class));

        Orders orderFromDb = orderRepository.findFirstByCode(newOrderCode);
        assertNotNull(orderFromDb);
        List<OrderDetail> orderDetailsFromDb = orderFromDb.getOrderDetails();

        assertEquals(1, orderDetailsFromDb.size());
        OrderDetail detail = orderDetailsFromDb.get(0);
        assertEquals("od-new", detail.getCode());

        orderRepository.delete(orderFromDb);
    }

    @Test
    void cancelOrder()
    {
        String orderCodeToCancel = "order 4";
        Orders order = orderRepository.findFirstByCode(orderCodeToCancel);
        assertEquals(OrderStatus.PLACED, order.getStatus());
        orderService.cancelOrder(orderCodeToCancel);
        verify(jmsTemplate, times(1)).convertAndSend(any(String.class), any(Object.class));
        order = orderRepository.findFirstByCode(orderCodeToCancel);
        assertEquals(OrderStatus.CANCELED, order.getStatus());
    }

    @Test
    void retrieveTotalPurchasedItemsByCustomer()
    {
        List<TotalItemsInfoDto> totalItems = orderService.retrieveTotalPurchasedItemsByCustomer("cust 1");
        assertEquals(totalItems.size(), 1);
        Long totalAmount = 0L;
        for (TotalItemsInfoDto dto : totalItems)
        {
            assertEquals("clothes", dto.getCategory());
            totalAmount += dto.getTotalAmount();
        }
        assertEquals(17000L, totalAmount);
    }

    @Test
    void retrieveTotalAmountPerCategoryByCustomer()
    {
        List<PurchasedItemsInfoDto> purchasedItems = orderService.retrieveTotalAmountPerCategoryByCustomer("cust 1");
        assertEquals(2, purchasedItems.size());
        assertEquals(BigDecimal.valueOf(26000L).compareTo(getTotalPrice(purchasedItems)), 0);

        purchasedItems = orderService.retrieveTotalAmountPerCategoryByCustomer("cust 2");
        assertEquals(1, purchasedItems.size());
        assertEquals(BigDecimal.valueOf(1000L).compareTo(getTotalPrice(purchasedItems)), 0);
    }

    private BigDecimal getTotalPrice(List<PurchasedItemsInfoDto> purchasedItems)
    {
        BigDecimal price = BigDecimal.ZERO;
        for (PurchasedItemsInfoDto dto : purchasedItems)
        {
            price = dto.getTotalPrice().add(price);
        }
        return price;
    }
}