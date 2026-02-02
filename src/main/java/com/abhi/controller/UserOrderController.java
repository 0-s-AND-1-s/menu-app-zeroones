package com.abhi.controller;

import com.abhi.pojo.OrderItemRequest;
import com.abhi.pojo.OrderResponse;
import com.abhi.service.UserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/order")
public class UserOrderController {

    private UserOrderService userOrderService;

    @Autowired
    public UserOrderController(UserOrderService userOrderService) {
        this.userOrderService = userOrderService;
    }


    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@RequestBody List<OrderItemRequest> orderItemRequest) {
        System.out.println(orderItemRequest.size());
        String status = userOrderService.createOrder(orderItemRequest);
        return new ResponseEntity<>(status, HttpStatus.CREATED);
    }

    @GetMapping("/getOrderById/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long orderId) {
        OrderResponse orderResponse = userOrderService.getOrderById(orderId);
        System.out.println("Sending OrderResponse DTO" + orderResponse);
        return ResponseEntity.ok(orderResponse);
    }

}
