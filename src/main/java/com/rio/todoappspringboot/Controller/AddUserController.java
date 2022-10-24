package com.rio.todoappspringboot.Controller;


import com.rio.todoappspringboot.entity.User;
import com.rio.todoappspringboot.service.AddUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.transaction.Transactional;

@RestController
@RequestMapping("/api/add-user")
@Transactional
public class AddUserController {

    private AddUserService addUserService;

    public AddUserController(AddUserService addUserService){
        this.addUserService = addUserService;
    }

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User newUser) {
        User user = addUserService.addUser(newUser);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
