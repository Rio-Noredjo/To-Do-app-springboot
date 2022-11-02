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

    /*Add user*/
    @Transactional
    public User addUser(User newUser) {
        return userRepository.save(newUser);
    }

    /*Update user*/
    @Override
    public User updateUser(User existingUser) {

        /*Get user. If not found return null else update user*/
        User user = getUserById(existingUser.getId());
        if(user == null){
            return null;
        } else {
            return userRepository.save(existingUser);
        }
    }

    //Get user by email address
    @Override
    public User getByEmail(String emailAddress) {
        return userRepository.findByEmail(emailAddress);
    }

    /*Get all user order by id descending*/
    @Override
    public List<User> getAll() {
        return userRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    /*Get user by id*/
    @Override
    public User getUserById(Long id) {
        /*Get user if not found return null*/
        Optional<User> userResponse =  userRepository.findById(id);
        if(userResponse.isEmpty()){
            return null;
        }
        return userResponse.get();
    }

    /*Get user by email address and password*/
    @Override
    public User getUserByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    /*Delete user*/
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
