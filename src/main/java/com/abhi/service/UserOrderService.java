package com.abhi.service;

import com.abhi.dto.request.OrderItemRequest;
import com.abhi.dto.response.OrderResponse;

import java.util.List;

public interface UserOrderService {
    String createOrder(List<OrderItemRequest> orderItems);
    OrderResponse getOrderById(Long orderId);
    String deleteOrderById(Long orderId);
    List<OrderResponse> findAll();
}
