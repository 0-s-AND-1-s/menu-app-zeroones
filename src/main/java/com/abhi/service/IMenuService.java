package com.abhi.service;

import com.abhi.pojo.MenuItemRequest;
import com.abhi.pojo.MenuItemResponse;

import java.util.List;

public interface IMenuService {
    MenuItemResponse getItemById(Integer itemId);

    List<MenuItemResponse> getAllItems();

    String createMenuItem(MenuItemRequest menuItemRequest);

    String updateMenuItem(Integer itemId, MenuItemRequest menuItemRequest);

    String partialUpdateMenuItem(Integer itemId, MenuItemRequest menuItemRequest);

    String deleteMenuItem(Integer itemId);
}
