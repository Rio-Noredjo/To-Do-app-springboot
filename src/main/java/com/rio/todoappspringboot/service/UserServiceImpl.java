package com.rio.todoappspringboot.service;

import com.rio.todoappspringboot.dao.UserRepository;
import com.rio.todoappspringboot.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User addUser(User newUser) {
        return userRepository.save(newUser);
    }

    @Override
    public User updateUser(User existingUser) {

        //Get user, if not found return null else update user
        User user = getUserById(existingUser.getId());
        if(user == null){
            return null;
        } else {
            return userRepository.save(existingUser);
        }
    }

    @Override
    public User getByEmail(String emailAddress) {
        return userRepository.findByEmail(emailAddress);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Override
    public User getUserById(Long id) {

        Optional<User> userResponse =  userRepository.findById(id);
        if(userResponse.isEmpty()){
            return null;
        }
        return userResponse.get();
    }

    @Override
    public User getUserByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public boolean deleteUser(Long id) {
        User user = getUserById(id);
        if(user == null){
            return false;
        } else {
            userRepository.deleteById(id);
            return true;
        }
    }
}
