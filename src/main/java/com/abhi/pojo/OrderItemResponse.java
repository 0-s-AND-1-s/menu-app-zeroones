package com.abhi.pojo;

import lombok.Data;

import java.math.BigDecimal;

//Sending this as a response so that we don't need to unnecessarily include OrderItem(entity) at List<OrderItemResponse> orderItemResponse; in OrderResponse
@Data
public class OrderItemResponse {
    private Long orderItemId;
    private String itemName;
    private BigDecimal unitPrice;
    private Integer quantity;
}
