package com.abhi.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderReceiptResponse {

    private BigDecimal subTotal;
    private BigDecimal salesTax;
    private BigDecimal total;

    private Long orderId;
    private LocalDateTime orderTime;

    private List<ReceiptItemDetails> receiptItemDetails;
}
