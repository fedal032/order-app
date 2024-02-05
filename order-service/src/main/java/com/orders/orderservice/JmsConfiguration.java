package com.orders.orderservice;

import jakarta.jms.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class JmsConfiguration
{
    // setSessionTransacted to rollback jms msg before transaction
    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory)
    {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setSessionTransacted(true);
        jmsTemplate.setConnectionFactory(connectionFactory);
        return jmsTemplate;
    }
}
