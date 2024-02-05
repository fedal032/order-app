package com.orders.orderservice.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderJmsProducer
{
    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @Value("${activemq.order-queue}")
    private String orderQueue;

    public void sendMessage(CustomerOrdersMessage message)
    {
        try
        {
            log.info("Attempting send message {} to order client queue: {} ", message, orderQueue);
            jmsTemplate.convertAndSend(orderQueue, objectMapper.writeValueAsString(message));
        }
        catch (Exception e)
        {
            log.error("Recieved exception during send Message: ", e);
        }
    }
}
