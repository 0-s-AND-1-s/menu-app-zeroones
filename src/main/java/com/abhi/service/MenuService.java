package com.abhi.service;

import com.abhi.dto.request.MenuItemRequest;
import com.abhi.dto.response.MenuItemResponse;

import java.util.List;

public interface MenuService {
    MenuItemResponse getItemById(Integer itemId);

    List<MenuItemResponse> getAllItems();

    String createMenuItem(MenuItemRequest menuItemRequest);

    String updateMenuItem(Integer itemId, MenuItemRequest menuItemRequest);

    String partialUpdateMenuItem(Integer itemId, MenuItemRequest menuItemRequest);

    String deleteMenuItem(Integer itemId);
}
