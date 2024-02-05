package com.orders.orderservice.dto;

import java.math.BigDecimal;

public interface PurchasedItemsInfoDto
{
    String getCategory();
    BigDecimal getTotalPrice();
}
