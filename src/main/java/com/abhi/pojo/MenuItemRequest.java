package com.abhi.pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MenuItemRequest {
    private String itemName;

    private BigDecimal itemPrice;

    private String itemCategory;

    private Boolean itemAvailability;
}
