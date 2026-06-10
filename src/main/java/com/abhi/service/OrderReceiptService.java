package com.abhi.service;

import com.abhi.model.Order;
import com.abhi.dto.response.OrderReceiptResponse;

public interface OrderReceiptService {
     Integer saveReceiptForOrder(Order order);
    OrderReceiptResponse printReceipt(Integer receiptId);
}
