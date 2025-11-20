package com.abhi.pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MenuItemResponse {

    private Integer itemId;

    private String itemName;

    private BigDecimal itemPrice;

    private String itemCategory;

    private Boolean itemAvailability;
}
