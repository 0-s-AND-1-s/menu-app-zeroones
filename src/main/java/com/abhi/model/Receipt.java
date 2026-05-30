package com.abhi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Table(name="OrderReceipts")
@Getter
@Setter
@Entity
@NoArgsConstructor
public class Receipt {

    @Id
    @SequenceGenerator(name="receipt_seq",sequenceName = "receipt_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "receipt_seq")
    private Integer receiptId;

    @Column(precision = 10, scale = 2)
    private BigDecimal subTotal;

    @Column(precision = 10, scale = 2)
    private BigDecimal salesTax;

    @Column(precision = 10, scale = 2)
    private BigDecimal total;

    @OneToOne
    @JoinColumn(referencedColumnName = "orderId",name="order_id",unique = true)
    private Order order;
}
