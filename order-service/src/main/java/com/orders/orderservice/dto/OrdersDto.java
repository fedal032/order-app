package com.orders.orderservice.dto;

import com.orders.orderservice.entity.Orders;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class OrdersDto
{
    private long id;
    private String code;
    private String status;
    private String customerCode;
    private Date createdDate;
    private Date modifiedDate;

    private List<OrderDetailsDto> orderDetails;

    public static OrdersDto fromEntity(Orders order)
    {
        OrdersDtoBuilder builder = OrdersDto.builder()
                .id(order.getId())
                .code(order.getCode())
                .status(order.getStatus().name())
                .customerCode(order.getCustomer().getCode())
                .createdDate(order.getCreatedDate())
                .modifiedDate(order.getModifiedDate());

        List<OrderDetailsDto> detailDtoList = new ArrayList<>();
        order.getOrderDetails().forEach(od -> detailDtoList.add(OrderDetailsDto.builder()
                .code(od.getCode())
                .category(od.getCategory().getCode())
                .amount(od.getAmount())
                .price(od.getPrice())
                .build()));
        builder.orderDetails(detailDtoList);

        return builder.build();
    }
}
