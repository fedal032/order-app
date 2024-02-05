package com.orders.orderservice.services;

import com.orders.orderservice.dto.OrderDetailsDto;
import com.orders.orderservice.dto.OrdersDto;
import com.orders.orderservice.dto.PurchasedItemsInfoDto;
import com.orders.orderservice.dto.TotalItemsInfoDto;
import com.orders.orderservice.entity.Category;
import com.orders.orderservice.entity.Customer;
import com.orders.orderservice.entity.OrderDetail;
import com.orders.orderservice.entity.OrderStatus;
import com.orders.orderservice.entity.Orders;
import com.orders.orderservice.message.CustomerOrdersMessage;
import com.orders.orderservice.message.OrderActions;
import com.orders.orderservice.message.OrderJmsProducer;
import com.orders.orderservice.repository.CategoryRepository;
import com.orders.orderservice.repository.CustomerRepository;
import com.orders.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class OrderService
{
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final CategoryRepository categoryRepository;
    private final OrderJmsProducer jmsProducer;


    public List<OrdersDto> retrieveAll()
    {
        log.info("Retrieve all orders");
        List<Orders> orders = orderRepository.findAll();

        return orders.stream().map(OrdersDto::fromEntity).collect(Collectors.toList());
    }

    public List<OrdersDto> retrieveAllPlacedOrdersByCustomer(String customerCode)
    {
        log.info("Retrieve orders by customer code = {}", customerCode);

        Customer customer = customerRepository.findByCode(customerCode);
        if (customer == null)
            throw new IllegalArgumentException("Customer with code = " + customerCode + " not found");

        List<Orders> res = orderRepository.findAllByCustomerAndStatus(customer, OrderStatus.PLACED);
        return res.stream().map(OrdersDto::fromEntity).collect(Collectors.toList());
    }

    public OrdersDto createOrder(OrdersDto order)
    {
        log.info("Start creating new order: {} ", order);

        Customer customer = customerRepository.findByCode(order.getCustomerCode());
        if (customer == null)
            throw new IllegalArgumentException("Customer with code = " + order.getCustomerCode() + " not found");

        //cache categories
        Map<String, Category> categoriesCache = categoryRepository.findAll()
                .stream().collect(Collectors.toMap(Category::getCode, Function.identity()));

        Date creationDate = new Date();
        Orders newOrder = new Orders();
        newOrder.setCode(order.getCode());
        newOrder.setStatus(OrderStatus.PLACED);
        newOrder.setCreatedDate(new java.sql.Date(creationDate.getTime()));
        newOrder.setCustomer(customer);

        if (order.getOrderDetails() != null && !order.getOrderDetails().isEmpty())
        {
            List<OrderDetail> orderDetails = new ArrayList<>(order.getOrderDetails().size());
            for (OrderDetailsDto detailsDto : order.getOrderDetails())
            {
                if(!categoriesCache.containsKey(detailsDto.getCategory())) {
                    log.warn("Skip record with code = {} and category = {}", detailsDto.getCode(), detailsDto.getCategory());
                    continue;
                }
                OrderDetail detail = new OrderDetail();
                detail.setOrder(newOrder);
                detail.setCode(detailsDto.getCode());
                detail.setCategory(categoriesCache.get(detailsDto.getCategory()));
                detail.setAmount(detailsDto.getAmount());
                detail.setPrice(detailsDto.getPrice());
                orderDetails.add(detail);
            }
            newOrder.setOrderDetails(orderDetails);
        }
        orderRepository.save(newOrder);
        jmsProducer.sendMessage(new CustomerOrdersMessage(customer.getCode(), getOrderAmount(newOrder), OrderActions.CREATE));

        return OrdersDto.fromEntity(newOrder);
    }

    public void cancelOrder(String code)
    {
        log.info("Cancel order with code = {}", code);
        Orders order = orderRepository.findFirstByCode(code);
        order.setStatus(OrderStatus.CANCELED);
        order.setModifiedDate(new java.sql.Date(new Date().getTime()));
        orderRepository.save(order);
        jmsProducer.sendMessage(new CustomerOrdersMessage(order.getCustomer().getCode(), getOrderAmount(order), OrderActions.CANCEL));
    }

    private BigDecimal getOrderAmount(Orders order)
    {
        BigDecimal res = BigDecimal.ZERO;
        for (OrderDetail orderDetail : order.getOrderDetails())
            res = orderDetail.getPrice().add(res);

        return res;
    }

    public List<TotalItemsInfoDto> retrieveTotalPurchasedItemsByCustomer(String code)
    {
        return orderRepository.retrieveTotalPurchasedItemsByCustomer(code, OrderStatus.PURCHASED);
    }

    public List<PurchasedItemsInfoDto> retrieveTotalAmountPerCategoryByCustomer(String code)
    {
        return orderRepository.retrieveTotalAmountPerCategoryByCustomer(code, OrderStatus.CANCELED);
    }
}
