package com.abhi.service.impl;

import com.abhi.dto.projection.ReceiptDetailsProjection;
import com.abhi.repository.OrderReceiptRepository;
import com.abhi.model.Order;
import com.abhi.model.OrderItem;
import com.abhi.model.Receipt;
import com.abhi.dto.response.OrderReceiptResponse;
import com.abhi.service.OrderReceiptService;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderReceiptServiceImpl implements OrderReceiptService {

    private OrderReceiptRepository orderReceiptRepository;

    @Autowired
    public OrderReceiptServiceImpl(OrderReceiptRepository orderReceiptRepository) {
        this.orderReceiptRepository = orderReceiptRepository;
    }

    @Override
    @Transactional
    public void saveReceiptForOrder(Order order) {
        List<OrderItem> orderReceiptItems = order.getOrderItems();
        Receipt receiptEntity = new Receipt();
        final BigDecimal taxPercent = BigDecimal.valueOf(0.0825);
        BigDecimal subTotal = orderReceiptItems.stream().map((orderReceiptItem)->
                BigDecimal.valueOf(orderReceiptItem.getQuantity()).multiply(orderReceiptItem.getUnitPrice()))
                .reduce(BigDecimal.ZERO,(totalSum,price)-> totalSum.add(price));
        BigDecimal salesTax = subTotal.multiply(taxPercent);
        BigDecimal total = subTotal.add(salesTax);
        receiptEntity.setOrder(order);
        receiptEntity.setSubTotal(subTotal);
        receiptEntity.setSalesTax(salesTax);
        receiptEntity.setTotal(total);
        orderReceiptRepository.save(receiptEntity);
        System.out.println("Receipt saved successfully");
    }

    @Override
    public OrderReceiptResponse printReceipt(Integer receiptId) {
        ReceiptDetailsProjection receiptDetails = orderReceiptRepository.getReceiptDetails(receiptId);
        OrderReceiptResponse receiptResponse = new OrderReceiptResponse();
        BeanUtils.copyProperties(receiptDetails,receiptResponse);
        return receiptResponse;
    }
}


