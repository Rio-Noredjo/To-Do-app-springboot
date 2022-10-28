package com.rio.todoappspringboot.dao;

import com.rio.todoappspringboot.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findAllByUserId(@Param("userId") Long userId);

}
