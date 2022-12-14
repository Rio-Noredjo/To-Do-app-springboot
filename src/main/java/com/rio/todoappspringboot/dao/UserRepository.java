package com.rio.todoappspringboot.dao;

import com.rio.todoappspringboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.Optional;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findByEmailAndPassword(String email, String password);

    Optional<User> findById(Long id);

}
