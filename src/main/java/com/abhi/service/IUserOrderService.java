package com.abhi.service;

import com.abhi.pojo.OrderItemRequest;
import com.abhi.pojo.OrderResponse;

import java.util.List;

public interface IUserOrderService {
    String createOrder(List<OrderItemRequest> orderItems);
    OrderResponse getOrderById(Long orderId);
    String deleteOrderById(Long orderId);
    List<OrderResponse> findAll();
}
