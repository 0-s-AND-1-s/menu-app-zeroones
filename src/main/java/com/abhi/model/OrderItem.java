package com.abhi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

//BiDirectional association
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(
        indexes = {
                @Index(name = "idx_order_item_order_id", columnList = "order_id")
        }
)
public class OrderItem {

    @Id
    @SequenceGenerator(
            name = "order_item_seq",
            sequenceName = "order_item_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_item_seq")
    private Long orderItemId;

    private String itemName;

    @Column(precision = 10, scale = 2)
    private BigDecimal unitPrice;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(referencedColumnName = "orderId", name = "order_id")
//    @JsonIgnore
    private Order order;
}
