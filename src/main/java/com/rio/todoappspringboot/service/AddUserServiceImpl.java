package com.rio.todoappspringboot.service;

import com.rio.todoappspringboot.dao.UserRepository;
import com.rio.todoappspringboot.entity.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AddUserServiceImpl implements AddUserService{

    private UserRepository userRepository;

    public AddUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Transactional
    public User addUser(User user) {
        return userRepository.save(user);
    }
}
