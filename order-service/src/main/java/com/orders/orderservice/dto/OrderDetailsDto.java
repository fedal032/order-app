package com.orders.orderservice.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderDetailsDto
{
    private String code;
    private String category;
    private Long amount;
    private BigDecimal price;
}
