package com.client.orders.message;

import com.client.orders.entity.CustomerStatistic;
import com.client.orders.service.CustomerStatisticService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderJmsConsumer
{
    private final CustomerStatisticService customerStatisticService;
    private final ObjectMapper objectMapper;

    @JmsListener(destination = "${activemq.order-queue}")
    public void receiveMessage(String jsonMessage) throws JsonProcessingException
    {
        log.info("Received message = {} ", jsonMessage);
        CustomerOrdersMessage customerOrdersMessage = objectMapper.readValue(jsonMessage, CustomerOrdersMessage.class);
        CustomerStatistic customerStatistic = customerStatisticService.updateStatistic(customerOrdersMessage);
        log.info("Customer statistic: {}", customerStatistic);
    }
}
