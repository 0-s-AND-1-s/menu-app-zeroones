package com.abhi.pojo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {
    public Long orderId;
    public List<OrderItemResponse> orderItemResponse;
    public LocalDateTime orderTime;
}
