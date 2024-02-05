package com.client.orders.service;

import com.client.orders.entity.CustomerStatistic;
import com.client.orders.message.CustomerOrdersMessage;
import com.client.orders.message.OrderActions;
import com.client.orders.repository.CustomerStatisticRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerStatisticService
{
    private final CustomerStatisticRepository customerStatisticRepository;

    @Transactional
    public CustomerStatistic updateStatistic(CustomerOrdersMessage customerOrdersMessage)
    {
        log.info("Start updating statistic for input message: {}", customerOrdersMessage);

        CustomerStatistic customerStatistic = customerStatisticRepository.findCustomerStatisticByCustomerCode(customerOrdersMessage.getCustomerCode());
        if (customerStatistic == null) {
            log.info("Create new Customer statistic");
            customerStatistic = new CustomerStatistic();
            customerStatistic.setCustomerCode(customerOrdersMessage.getCustomerCode());
            customerStatistic.setTotalAmount(customerOrdersMessage.getOrderAmount());
        }
        else {
            if (customerOrdersMessage.getAction() == OrderActions.CREATE)
                customerStatistic.setTotalAmount(customerStatistic.getTotalAmount().add(customerOrdersMessage.getOrderAmount()));
            else if (customerOrdersMessage.getAction() == OrderActions.CANCEL)
                customerStatistic.setTotalAmount(customerStatistic.getTotalAmount().subtract(customerOrdersMessage.getOrderAmount()));
            else
                throw new IllegalArgumentException("Unknown action: " + customerOrdersMessage.getAction().name());
        }

        customerStatistic.setModifiedDate(new java.sql.Date(new Date().getTime()));
        return customerStatisticRepository.saveAndFlush(customerStatistic);
    }

    public List<CustomerStatistic> getAllCustomersStatistic()
    {
        return customerStatisticRepository.findAll();
    }
}
