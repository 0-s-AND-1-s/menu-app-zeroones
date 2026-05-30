package com.abhi.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReceiptItemDetails {
    private String itemName;
    private Integer quantity;
    private BigDecimal unitPrice;
}
