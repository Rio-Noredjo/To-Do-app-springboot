package com.rio.todoappspringboot.Controller;

import com.rio.todoappspringboot.dto.ItemDto;
import com.rio.todoappspringboot.entity.Item;
import com.rio.todoappspringboot.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private ItemService itemService;

    public ItemController(ItemService itemService){
        this.itemService = itemService;
    }

    @PostMapping("/add/{userId}")
    public ResponseEntity<?> addUser(@PathVariable("userId") Long userId, @RequestBody ItemDto newItem) {
        Item item = itemService.addItem(newItem, userId);
        if (item == null) {
            return new ResponseEntity<>("Failed to add item" ,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

/*    @PutMapping(value ="/update")
    public ResponseEntity<?> update(@RequestBody User existingUser) {
        User user = userService.updateUser(existingUser);
        if (user == null) {
            return new ResponseEntity<>("Failed to update user" ,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/find-by-email/{emailAddress}")
    public ResponseEntity<User> findUserByEmail(@PathVariable("emailAddress") String emailAddress){
        return new ResponseEntity<>(userService.findByEmail(emailAddress), HttpStatus.OK);
    }

    @GetMapping("/all-users")
    public ResponseEntity<?> getAll() {
        List<User> userList = userService.getAll();
        if (userList.isEmpty()) {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return new ResponseEntity<>("No user found with id: " + id,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping(value="/delete-user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        boolean isRemoved = userService.deleteUser(id);
        if (!isRemoved) {
            return new ResponseEntity<>("User with Id: " + id + " not deleted or found",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(id, HttpStatus.OK);
    }*/

}
