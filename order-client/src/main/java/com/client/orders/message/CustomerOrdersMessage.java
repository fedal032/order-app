package com.client.orders.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerOrdersMessage implements Serializable
{
    private String customerCode;
    private BigDecimal orderAmount;
    private OrderActions action;
}
