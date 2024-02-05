package com.client.orders.controllers;

import com.client.orders.entity.CustomerStatistic;
import com.client.orders.service.CustomerStatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/orders-client")
@RequiredArgsConstructor
public class OrderClientController
{
    private final CustomerStatisticService customerStatisticService;

    @GetMapping("statistic/all")
    public List<CustomerStatistic> getAllCustomersStatistic() {
        return customerStatisticService.getAllCustomersStatistic();
    }
}
