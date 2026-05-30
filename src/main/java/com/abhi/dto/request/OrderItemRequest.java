package com.abhi.dto.request;

import lombok.Data;

@Data
public class OrderItemRequest {

    private Integer menuId;

    private Integer quantity;

}
