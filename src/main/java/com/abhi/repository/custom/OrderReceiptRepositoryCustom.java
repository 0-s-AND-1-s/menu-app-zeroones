package com.abhi.repository.custom;

import com.abhi.dto.projection.ReceiptDetailsProjection;


public interface OrderReceiptRepositoryCustom {
    ReceiptDetailsProjection getReceiptDetails(Integer receiptId);
}
