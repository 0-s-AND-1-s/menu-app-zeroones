package com.abhi.controller;

import com.abhi.dto.request.MenuItemRequest;
import com.abhi.dto.response.MenuItemResponse;
import com.abhi.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/menu-items")
public class MenuItemsController {


    private MenuService menuService;

    @Autowired
    public MenuItemsController(MenuService menuService) {
        System.out.println("Controller Object is ready " + this.getClass().getName());
        this.menuService = menuService;
    }

    @PostMapping
    public ResponseEntity<String> createMenuItem(@RequestBody MenuItemRequest menuItemRequest) {
        String createdItem = menuService.createMenuItem(menuItemRequest);
        return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<MenuItemResponse> getMenuItemById(@PathVariable Integer itemId) {
        MenuItemResponse itemResponse = menuService.getItemById(itemId);
        return ResponseEntity.ok(itemResponse);
    }

    @GetMapping
    public ResponseEntity<List<MenuItemResponse>> getAllItems() {
        List<MenuItemResponse> itemsList = menuService.getAllItems();
        return ResponseEntity.ok(itemsList);
    }

    /*
     * If you stick to PUT, your API should require clients to send every field (including itemAvailability).
       If a client forgets one → that field becomes null.
       This is why many developers prefer PATCH for updates — it avoids accidental nulls by treating missing fields as "don’t change".
    * */
    @PutMapping("/{itemId}")
    public ResponseEntity<String> updateItem(@PathVariable Integer itemId, @RequestBody MenuItemRequest menuItemRequest) {
        String updatedMenuItem = menuService.updateMenuItem(itemId, menuItemRequest);
        return new ResponseEntity<>(updatedMenuItem, HttpStatus.OK);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<String> partialUpdate(@PathVariable Integer itemId, @RequestBody MenuItemRequest menuItemRequest) {
        System.out.println("MenuItemsController.partialUpdate() " + menuItemRequest);
        String updatedMenuItem = menuService.partialUpdateMenuItem(itemId, menuItemRequest);
        return new ResponseEntity<>(updatedMenuItem, HttpStatus.OK);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<String> removeItem(@PathVariable Integer itemId) {
        String deletedItem = menuService.deleteMenuItem(itemId);
        return new ResponseEntity<>(deletedItem, HttpStatus.OK);
    }

}