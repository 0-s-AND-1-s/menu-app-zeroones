package com.abhi.controller;

import com.abhi.dto.request.OrderItemRequest;
import com.abhi.dto.response.OrderResponse;
import com.abhi.service.impl.UserOrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/order")
public class UserOrderController {

    private UserOrderServiceImpl userOrderService;

    @Autowired
    public UserOrderController(UserOrderServiceImpl userOrderService) {
        this.userOrderService = userOrderService;
    }

    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER','ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@RequestBody List<OrderItemRequest> orderItemRequest) {
        System.out.println(orderItemRequest.size());
        String status = userOrderService.createOrder(orderItemRequest);
        return new ResponseEntity<>(status, HttpStatus.CREATED);
    }
//fang,fang123
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @GetMapping("/getOrderById/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long orderId) {
        OrderResponse orderResponse = userOrderService.getOrderById(orderId);
        System.out.println("Sending OrderResponse DTO" + orderResponse);
        return ResponseEntity.ok(orderResponse);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/deleteOrderById/{orderId}")
    public ResponseEntity<String> removeOrderById(@PathVariable Long orderId){
        String deleteStatus = userOrderService.deleteOrderById(orderId);
        return ResponseEntity.ok(deleteStatus);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/getAllOrders")
    public ResponseEntity<List<OrderResponse>> findAllOrders(){
        List<OrderResponse> allOrders = userOrderService.findAll();
        return ResponseEntity.ok(allOrders);
    }

}
