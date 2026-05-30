package com.abhi.dto.projection;

import com.abhi.dto.response.ReceiptItemDetails;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/*@Data
public class ReceiptDetailsProjection {
    private BigDecimal subTotal;
    private BigDecimal salesTax;
    private BigDecimal total;

    private Long orderId;
    private LocalDateTime orderTime;

    private List<ReceiptItemDetails> receiptItemDetails;
}*/
public record ReceiptDetailsProjection(
         BigDecimal subTotal,
         BigDecimal salesTax,
         BigDecimal total,

         Long orderId,
         LocalDateTime orderTime,

         List<ReceiptItemDetails> receiptItemDetails
) {}
