package com.rio.todoappspringboot.Controller;


import com.rio.todoappspringboot.dao.UserRepository;
import com.rio.todoappspringboot.entity.User;
import com.rio.todoappspringboot.service.AddUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;

@RestController
@RequestMapping("/api/add-user")
@Transactional
public class AddUserController {

    private AddUserService addUserService;
    private UserRepository userRepository;


    public AddUserController(AddUserService addUserService,
                             UserRepository userRepository){
        this.addUserService = addUserService;
        this.userRepository = userRepository;
    }

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User newUser) {
        User user = addUserService.addUser(newUser);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping("/find-user-by-email/{emailAddress}")
    public User findUserByEmail( @PathVariable("emailAddress") String emailAddress){
        return userRepository.findByEmail(emailAddress);
    }
}
