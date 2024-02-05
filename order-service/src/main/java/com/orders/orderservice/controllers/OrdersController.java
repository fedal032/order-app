package com.orders.orderservice.controllers;

import com.orders.orderservice.dto.OrdersDto;
import com.orders.orderservice.dto.PurchasedItemsInfoDto;
import com.orders.orderservice.dto.TotalItemsInfoDto;
import com.orders.orderservice.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Orders", description = "Order management APIs")
@RestController
@RequestMapping("api/orders")
@RequiredArgsConstructor
@CrossOrigin()
public class OrdersController
{
    private final OrderService orderService;

    @Operation(
            summary = "Create a new order",
            description = "Endpoint to create a new order.",
            tags = {"Orders"}
    )
    @PostMapping("new")
    public OrdersDto createOrder(@RequestBody OrdersDto order)
    {
        return orderService.createOrder(order);
    }

    @Operation(
            summary = "Cancel an existing order",
            description = "Endpoint to cancel an existing order.",
            tags = {"Orders"}
    )
    @PutMapping("{code}/cancel")
    public void cancelOrder(
            @Parameter(description = "The code of the order to be canceled", required = true)
            @PathVariable String code)
    {
        orderService.cancelOrder(code);
    }

    @Operation(
            summary = "Retrieve all orders",
            description = "Endpoint to retrieve a list of all orders.",
            tags = {"Orders"}
    )
    @GetMapping("all")
    public List<OrdersDto> retrieveAll()
    {
        return orderService.retrieveAll();
    }


    @Operation(
            summary = "Retrieve all placed orders by customer",
            description = "Endpoint to retrieve a list of all orders placed by a specific customer.",
            tags = {"Orders"}
    )
    @GetMapping("all/placed")
    public List<OrdersDto> retrieveAllPlacedByCustomer(
            @Parameter(description = "The name of the customer", required = true)
            @RequestParam(name = "customer") String customer)
    {
        return orderService.retrieveAllPlacedOrdersByCustomer(customer);
    }


    @Operation(
            summary = "Retrieve total purchased items by customer",
            description = "Endpoint to retrieve a list of total purchased items by a specific customer.",
            tags = {"Orders"}
    )
    @GetMapping("purchased")
    public List<TotalItemsInfoDto> retrieveTotalPurchasedItemsByCustomer(
            @Parameter(description = "The name of the customer", required = true)
            @RequestParam(name = "customer") String customer)
    {
        return orderService.retrieveTotalPurchasedItemsByCustomer(customer);
    }


    @Operation(
            summary = "Retrieve total amount per category by customer",
            description = "Endpoint to retrieve a list of total amount per category by a specific customer.",
            tags = {"Orders"}
    )
    @GetMapping("amount")
    public List<PurchasedItemsInfoDto> retrieveTotalAmountPerCategoryByCustomer(
            @Parameter(description = "The name of the customer", required = true)
            @RequestParam(name = "customer") String customer)
    {
        return orderService.retrieveTotalAmountPerCategoryByCustomer(customer);
    }
}
