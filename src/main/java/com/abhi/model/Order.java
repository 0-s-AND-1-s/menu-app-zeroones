package com.abhi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//BiDirectional association
@Entity
@Table(name = "Orders"/*,
        indexes = { @Index(name = "idx_orders_order_time", columnList = "order_time") }*/
)
@Getter
@Setter
@NoArgsConstructor
public class Order {

    @Id
    @SequenceGenerator(
            name = "orders_seq",
            sequenceName = "orders_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_seq")
    private Long orderId;

    //by default fetch = FetchType.LAZY for @oneToMany
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDateTime orderTime;

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void removeOrderItem(OrderItem ordItem) {
        orderItems.remove(ordItem);
        ordItem.setOrder(null);
    }
}
