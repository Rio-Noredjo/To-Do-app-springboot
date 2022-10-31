package com.rio.todoappspringboot.service;

import com.rio.todoappspringboot.dao.UserRepository;
import com.rio.todoappspringboot.entity.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public User addUser(User newUser) { return userRepository.save(newUser); }

    @Transactional
    @Override
    public User updateUser(User existingUser) {
        User user = getUserById(existingUser.getId());
        if(user == null){
            return null;
        } else {
            return userRepository.save(existingUser);
        }
    }

    @Transactional
    @Override
    public User findByEmail(String emailAddress) { return userRepository.findByEmail(emailAddress); }

    @Transactional
    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public User getUserById(Long id) {
        Optional<User> userResponse =  userRepository.findById(id);
        User user = null;
        if(userResponse.isPresent()){
            user = userResponse.get();
        }
        return user;
    }

    @Override
    public User getUserByEmailAndPassword(String email, String password) {
        User user = userRepository.findByEmailAndPassword(email, password);
        if(user != null){
            return user;
        }
        return null;
    }

    @Transactional
    @Override
    public boolean deleteUser(Long id) {
        User user = getUserById(id);
        boolean isRemoved = false;
        if(user != null){
            userRepository.deleteById(id);
            isRemoved = true;
        }
        return isRemoved;
    }
}
