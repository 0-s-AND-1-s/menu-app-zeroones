package com.abhi.service.impl;


import CustomizedExceptions.MenuItemNotFoundException;
import com.abhi.repository.MenuRepository;
import com.abhi.model.MenuItem;
import com.abhi.dto.request.MenuItemRequest;
import com.abhi.dto.response.MenuItemResponse;
import com.abhi.service.MenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MenuServiceImpl implements MenuService {

    private MenuRepository repo;

    @Autowired
    public MenuServiceImpl(MenuRepository repo) {
        System.out.println("Service Object is ready " + this.getClass().getName());
        this.repo = repo;
    }

    @Override
    public MenuItemResponse getItemById(Integer itemId) {
        Optional<MenuItem> optional = Optional.empty();
        if (itemId != null) {
            optional = repo.findById(itemId);
        }
        MenuItem menuItem = optional.orElseThrow(() -> new MenuItemNotFoundException(itemId));
        MenuItemResponse itemResponse = new MenuItemResponse();
        BeanUtils.copyProperties(menuItem, itemResponse);
        return itemResponse;
    }

    @Override
    public List<MenuItemResponse> getAllItems() {
        List<MenuItem> itemsList = repo.findAll();
        List<MenuItemResponse> itemResponses = new ArrayList<>();
        itemsList.forEach((menuItem) -> {
            //Target Object
            MenuItemResponse menuItemResponse = new MenuItemResponse();
            BeanUtils.copyProperties(menuItem, menuItemResponse);
            itemResponses.add(menuItemResponse);
        });
        return itemResponses;
    }

    @Override
    public String createMenuItem(MenuItemRequest menuItemPojo) {
        MenuItem menuItemModel = new MenuItem();
        BeanUtils.copyProperties(menuItemPojo, menuItemModel);
        System.out.println("Model Entity object " + menuItemModel);
        Integer itemId = repo.save(menuItemModel).getItemId();
        return "Inserted successfully with the itemId " + itemId;
    }

    @Override
    public String updateMenuItem(Integer itemId, MenuItemRequest menuItemRequest) {
        Optional<MenuItem> itemOptional = repo.findById(itemId);
        if (itemOptional.isEmpty()) {
            throw new MenuItemNotFoundException(itemId);
        } else {
            /*MenuItem menuItemModel = new MenuItem();
            menuItemModel.setItemId(itemId);*/
            MenuItem menuItemModel = itemOptional.get();
            BeanUtils.copyProperties(menuItemRequest, menuItemModel, "itemId");
            System.out.println("MenuItemModel from updateMenuItem() " + menuItemModel);
            repo.save(menuItemModel);
            return "Updation success for the itemId " + itemId;
        }
    }

    @Override
    public String partialUpdateMenuItem(Integer itemId, MenuItemRequest menuItemRequest) {
        Optional<MenuItem> itemOptional = repo.findById(itemId);
        if (itemOptional.isEmpty()) {
            throw new MenuItemNotFoundException(itemId);
        } else {
            MenuItem menuItemModel = itemOptional.get();
            System.out.println("Before Updating MenuItemModel from partialUpdateMenuItem()" + menuItemModel);
            if (menuItemRequest.getItemName() != null) menuItemModel.setItemName(menuItemRequest.getItemName());
            if (menuItemRequest.getItemPrice() != null) menuItemModel.setItemPrice(menuItemRequest.getItemPrice());
            if (menuItemRequest.getItemCategory() != null)
                menuItemModel.setItemCategory(menuItemRequest.getItemCategory());
            if (menuItemRequest.getItemAvailability() != null)
                menuItemModel.setItemAvailability(menuItemRequest.getItemAvailability());
            System.out.println("Updated MenuItemModel from partialUpdateMenuItem() " + menuItemModel);
            repo.save(menuItemModel);
            return "Updation success for the itemId " + itemId;
        }
    }

    @Override
    public String deleteMenuItem(Integer itemId) {
        Optional<MenuItem> optional = repo.findById(itemId);
        if (optional.isEmpty()) {
            throw new MenuItemNotFoundException(itemId);
        } else {
            repo.deleteById(itemId);
            return "MenuItem with id ::" + itemId + " got deleted successfully";
        }
    }
}
