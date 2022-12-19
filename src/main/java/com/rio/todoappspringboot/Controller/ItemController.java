package com.rio.todoappspringboot.Controller;

import com.rio.todoappspringboot.dto.ItemCategories;
import com.rio.todoappspringboot.entity.Item;
import com.rio.todoappspringboot.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private ItemService itemService;

    public ItemController(ItemService itemService){
        this.itemService = itemService;
    }

    @GetMapping("/all-user-items/{userId}")
    public ResponseEntity<?> getAllUserItems(@PathVariable("userId") Long userId) {
        List<ItemCategories> itemCategoriesList = itemService.getAllUserItems(userId);
        return new ResponseEntity<>(itemCategoriesList, HttpStatus.OK);
    }

    @GetMapping("/all-items")
    public ResponseEntity<?> getAllItems() {
        List<ItemCategories> itemCategoriesList = itemService.getAll();
        if (itemCategoriesList.isEmpty()) {
            return new ResponseEntity<>("No items found",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(itemCategoriesList, HttpStatus.OK);
    }

    @GetMapping("/get-item/{itemId}")
    public ResponseEntity<?> getItemById(@PathVariable("itemId") Long itemId){
        ItemCategories itemCategories = itemService.getItemById(itemId);
        if (itemCategories == null) {
            return new ResponseEntity<>("Failed to get item with Id:" + itemId ,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(itemCategories, HttpStatus.OK);
    }

    @PostMapping("/add/{userId}")
    public ResponseEntity<?> addItem(@PathVariable("userId") Long userId, @RequestBody ItemCategories newItem) {
        Item item = itemService.addItem(newItem, userId);
        if (item == null) {
            return new ResponseEntity<>("Failed to add item for userId: " + userId ,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @PutMapping(value ="/update")
    public ResponseEntity<?> updateItem(@RequestBody ItemCategories existingItem) {
        Item item = itemService.updateItem(existingItem);
        if (item == null) {
            return new ResponseEntity<>("Failed to update item" ,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @DeleteMapping(value="/delete-item/{itemId}")
    public ResponseEntity<?> deleteItem(@PathVariable("itemId") Long itemId) {
        boolean isRemoved = itemService.deleteItem(itemId);
        if (!isRemoved) {
            return new ResponseEntity<>("Item with id: " + itemId + " not deleted or found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(itemId, HttpStatus.OK);
    }

}
