package com.abhi.service.impl;


import CustomizedExceptions.MenuItemNotFoundException;
import com.abhi.dto.mappers.MenuItemMapper;
import com.abhi.dto.request.MenuItemRequest;
import com.abhi.dto.response.MenuItemResponse;
import com.abhi.model.MenuItem;
import com.abhi.repository.MenuRepository;
import com.abhi.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    private MenuRepository menuRepo;
    private MenuItemMapper menuItemMapper;

    @Autowired
    public MenuServiceImpl(MenuRepository menuRepo, MenuItemMapper menuItemMapper) {
        System.out.println("Service Object is ready " + this.getClass().getName());
        this.menuRepo = menuRepo;
        this.menuItemMapper = menuItemMapper;
    }

    @Override
    public MenuItemResponse getItemById(Integer itemId) {
        MenuItem menuItemEntity = menuRepo.findById(itemId).orElseThrow(() -> new MenuItemNotFoundException(itemId));
        return menuItemMapper.toMenuItemResponse(menuItemEntity);
    }

    @Override
    public List<MenuItemResponse> getAllItems() {
        List<MenuItem> itemsList = menuRepo.findAll();
        List<MenuItemResponse> itemResponses = new ArrayList<>();
        itemsList.forEach(menuItemEntity -> itemResponses.add(menuItemMapper.toMenuItemResponse(menuItemEntity)));
        return itemResponses;
    }

    @Override
    public String createMenuItem(MenuItemRequest menuItemPojo) {
        MenuItem menuItemEntity = menuItemMapper.toMenuItemEntity(menuItemPojo);
        System.out.println("Model Entity object " + menuItemEntity);
        Integer itemId = menuRepo.save(menuItemEntity).getItemId();
        return "Inserted successfully with the itemId " + itemId;
    }

    @Override
    public String updateMenuItem(Integer itemId, MenuItemRequest menuItemRequest) {
        MenuItem menuItemEntity = menuRepo.findById(itemId).orElseThrow(() -> new MenuItemNotFoundException(itemId));
        menuItemMapper.updateMenuItemEntityFromDto(menuItemRequest, menuItemEntity);
        menuRepo.save(menuItemEntity);
        return "Updation success for the itemId " + itemId;

    }

    @Override
    public String partialUpdateMenuItem(Integer itemId, MenuItemRequest menuItemRequest) {
        MenuItem menuItemEntity = menuRepo.findById(itemId).orElseThrow(() -> new MenuItemNotFoundException(itemId));
        System.out.println("Before Updating MenuItemModel from partialUpdateMenuItem()" + menuItemEntity);
        menuItemMapper.partialEntityUpdateFromDto(menuItemRequest, menuItemEntity);
        System.out.println("Updated MenuItemModel from partialUpdateMenuItem() " + menuItemEntity);
        menuRepo.save(menuItemEntity);
        return "Updation success for the itemId " + itemId;
    }

    @Override
    public String deleteMenuItem(Integer itemId) {
        if (!menuRepo.existsById(itemId)) {
            throw new MenuItemNotFoundException(itemId);
        }
        menuRepo.deleteById(itemId);
        return "MenuItem with id ::" + itemId + " got deleted successfully";
    }
}
