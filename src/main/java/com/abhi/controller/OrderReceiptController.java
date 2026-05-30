package com.abhi.controller;

import com.abhi.dto.response.OrderReceiptResponse;
import com.abhi.service.OrderReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class OrderReceiptController {

    private OrderReceiptService orderReceiptService;

    @Autowired
    public OrderReceiptController(OrderReceiptService orderReceiptService) {
        this.orderReceiptService = orderReceiptService;
    }

    @GetMapping("/getReceipt/{receiptId}")
    public ResponseEntity<OrderReceiptResponse> getReceiptDetails(@PathVariable Integer receiptId) {
        OrderReceiptResponse orderReceiptResponse = orderReceiptService.printReceipt(receiptId);
        return ResponseEntity.ok(orderReceiptResponse);
    }
}
