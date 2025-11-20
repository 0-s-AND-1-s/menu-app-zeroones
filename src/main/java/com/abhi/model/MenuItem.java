package com.abhi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Table(name = "MenuItems")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class MenuItem {

    @Id
    @SequenceGenerator(
            name = "menu_items_seq",
            sequenceName = "menu_items_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menu_items_seq")
    private Integer itemId;

    private String itemName;

    @Column(precision = 10, scale = 2)
    private BigDecimal itemPrice;

    private String itemCategory;

    private Boolean itemAvailability;

}
