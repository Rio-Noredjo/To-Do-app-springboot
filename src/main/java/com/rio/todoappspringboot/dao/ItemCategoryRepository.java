package com.rio.todoappspringboot.dao;

import com.rio.todoappspringboot.entity.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.List;

@RepositoryRestResource(collectionResourceRel = "itemsCategories", path = "itemsCategories")
public interface ItemCategoryRepository extends JpaRepository<ItemCategory, Long> {

    List<ItemCategory> findByItemId(@Param("itemId") Long itemId);

    List<ItemCategory> deleteByItemId(@Param("itemId") Long itemId);

}
