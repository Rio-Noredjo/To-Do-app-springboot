package com.rio.todoappspringboot.Controller;

import com.rio.todoappspringboot.dto.ItemDto;
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

    @PostMapping("/add/{userId}")
    public ResponseEntity<?> addItem(@PathVariable("userId") Long userId, @RequestBody ItemDto newItem) {
        Item item = itemService.addItem(newItem, userId);
        if (item == null) {
            return new ResponseEntity<>("Failed to add item" ,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @GetMapping("/get-item/{itemId}")
    public ResponseEntity<ItemDto> findUserByEmail(@PathVariable("itemId") Long itemId){
        ItemDto itemDto = itemService.findById(itemId);
        if (itemDto == null) {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(itemDto, HttpStatus.OK);
    }

    @PutMapping(value ="/update")
    public ResponseEntity<?> updateItem(@RequestBody ItemDto existingItem) {
        Item item = itemService.updateItem(existingItem);
        if (item == null) {
            return new ResponseEntity<>("Failed to update item" ,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @GetMapping("/all-user-items/{userId}")
    public ResponseEntity<List<ItemDto>> getAllUserItems(@PathVariable("userId") Long userId) {
        List<ItemDto> itemDtoList = itemService.getAllUserItems(userId);
        return new ResponseEntity<>(itemDtoList, HttpStatus.OK);
    }

    @GetMapping("/all-items")
    public ResponseEntity<List<ItemDto>> getAllItems() {
        List<ItemDto> itemDtoList = itemService.getAllItems();
        if (itemDtoList.isEmpty()) {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(itemDtoList, HttpStatus.OK);
    }

    @DeleteMapping(value="/delete-item/{itemId}")
    public ResponseEntity<?> deleteItem(@PathVariable("itemId") Long itemId) {
        boolean isRemoved = itemService.deleteItem(itemId);
        if (!isRemoved) {
            return new ResponseEntity<>("Item with Id: " + itemId + " not deleted or found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(itemId, HttpStatus.OK);
    }
}
