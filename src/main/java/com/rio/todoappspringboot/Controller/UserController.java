package com.rio.todoappspringboot.Controller;

import com.rio.todoappspringboot.entity.User;
import com.rio.todoappspringboot.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    // Get user by email address
    @GetMapping("/find-by-email/{emailAddress}")
    public ResponseEntity<User> findUserByEmail(@PathVariable("emailAddress") String email){
        User user = userService.getByEmail(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Get all users
    @GetMapping("/all-users")
    public ResponseEntity<?> getAll() {
        List<User> userList = userService.getAll();
        if (userList.isEmpty()) {
            return new ResponseEntity<>("No users found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    // Get user by id
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return new ResponseEntity<>("No user found with id: " + id, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Get user by email and password
    @GetMapping("/user/user-email-password/{email}/{password}")
    public ResponseEntity<User> getUserByEmailAndPassword(@PathVariable("email") String email,@PathVariable("password") String password) {
        User user = userService.getUserByEmailAndPassword(email, password);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Add user
    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody User newUser) {
        User user = userService.addUser(newUser);
        if (user == null) {
            return new ResponseEntity<>("Failed to add user" ,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Update user
    @PutMapping(value ="/update")
    public ResponseEntity<?> updateUser(@RequestBody User existingUser) {
        User user = userService.updateUser(existingUser);
        if (user == null) {
            return new ResponseEntity<>("Failed to update user" ,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Delete user by id
    @DeleteMapping(value="/delete-user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        boolean isRemoved = userService.deleteUser(id);
        if (!isRemoved) {
            return new ResponseEntity<>("User with Id: " + id + " not deleted or found",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

}
