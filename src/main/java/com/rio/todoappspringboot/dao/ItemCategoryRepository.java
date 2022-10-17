package com.rio.todoappspringboot.dao;

import com.rio.todoappspringboot.entity.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "itemsCategories", path = "itemsCategories")
public interface ItemCategoryRepository extends JpaRepository<ItemCategory, Long> {

}
