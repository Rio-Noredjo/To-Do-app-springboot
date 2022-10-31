package com.rio.todoappspringboot.service;


import com.rio.todoappspringboot.entity.User;

import java.util.List;

public interface UserService {

    User addUser(User user);

    User updateUser(User user);

    User findByEmail(String emailAddress);

    List<User> getAll();

    User getUserById(Long id);

    User getUserByEmailAndPassword(String email, String password);

    boolean deleteUser(Long id);
}
