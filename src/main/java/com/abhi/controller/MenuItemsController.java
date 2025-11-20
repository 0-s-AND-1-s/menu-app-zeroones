package com.abhi.controller;

import com.abhi.pojo.MenuItemRequest;
import com.abhi.pojo.MenuItemResponse;
import com.abhi.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class MenuItemsController {


    private IMenuService menuService;

    @Autowired
    public MenuItemsController(IMenuService menuService) {
        System.out.println("Controller Object is ready " + this.getClass().getName());
        this.menuService = menuService;
    }

    @PostMapping("/createItem")
    public ResponseEntity<String> createMenuItem(@RequestBody MenuItemRequest menuItemRequest) {
        String createdItem = menuService.createMenuItem(menuItemRequest);
        return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
    }

    @GetMapping("/getItem/{itemId}")
    public ResponseEntity<MenuItemResponse> getMenuItemById(@PathVariable Integer itemId) {
        MenuItemResponse itemResponse = menuService.getItemById(itemId);
        return ResponseEntity.ok(itemResponse);
    }

    @GetMapping("/getAllItems")
    public ResponseEntity<List<MenuItemResponse>> getAllItems() {
        List<MenuItemResponse> itemsList = menuService.getAllItems();
        return ResponseEntity.ok(itemsList);
    }

    /*
     * If you stick to PUT, your API should require clients to send every field (including itemAvailability).
       If a client forgets one → that field becomes null.
       This is why many developers prefer PATCH for updates — it avoids accidental nulls by treating missing fields as "don’t change".
    * */
    @PutMapping("/updateItem/{itemId}")
    public ResponseEntity<String> updateItem(@PathVariable Integer itemId, @RequestBody MenuItemRequest menuItemRequest) {
        String updatedMenuItem = menuService.updateMenuItem(itemId, menuItemRequest);
        return new ResponseEntity<>(updatedMenuItem, HttpStatus.OK);
    }

    @PatchMapping("/updateItem/{itemId}")
    public ResponseEntity<String> partialUpdate(@PathVariable Integer itemId, @RequestBody MenuItemRequest menuItemRequest) {
        System.out.println("MenuItemsController.partialUpdate() " + menuItemRequest);
        String updatedMenuItem = menuService.partialUpdateMenuItem(itemId, menuItemRequest);
        return new ResponseEntity<>(updatedMenuItem, HttpStatus.OK);
    }

    @DeleteMapping("/deleteItem/{itemId}")
    public ResponseEntity<String> removeItem(@PathVariable Integer itemId) {
        String deletedItem = menuService.deleteMenuItem(itemId);
        return new ResponseEntity<>(deletedItem, HttpStatus.OK);
    }

}

/*1. PUT → Full Update
Semantics: Replace the entire resource with what the client sends.
If a field is missing or null in the request, it means "wipe it out / reset it".
Example:
Current DB row:
{
  "itemId": 1,
  "itemName": "Pizza",
  "itemPrice": 15.99,
  "itemCategory": "Main Course",
  "itemAvailability": true
}
PUT Request:
{
  "itemName": "Veg Pizza",
  "itemPrice": 16.99,
  "itemCategory": "Main Course"
  // no availability field
}
Result after PUT:
{
  "itemId": 1,
  "itemName": "Veg Pizza",
  "itemPrice": 16.99,
  "itemCategory": "Main Course",
  "itemAvailability": null   ❌ overwritten
}
👉 PUT assumes the request contains the entire resource state, so missing fields are treated as null/empty.
2. PATCH → Partial Update
Semantics: Modify only the fields provided.
If a field is missing in the request, leave the existing value untouched.
Example:
PATCH Request:
{
  "itemPrice": 17.99
}
Result after PATCH:
{
  "itemId": 1,
  "itemName": "Pizza",
  "itemPrice": 17.99,   ✅ updated
  "itemCategory": "Main Course",
  "itemAvailability": true   ✅ unchanged
}
👉 PATCH assumes missing fields = "don’t touch".

Implementation Difference in Code

PUT → your current BeanUtils.copyProperties is fine (nulls overwrite).

PATCH → you need a null-skipping strategy (manual checks, custom BeanUtils, or MapStruct).
* */