package com.rio.todoappspringboot.service;


import com.rio.todoappspringboot.dto.ItemCategories;
import com.rio.todoappspringboot.entity.Item;

import java.util.List;

public interface ItemService {

    Item addItem(ItemCategories item, Long userId);

    boolean deleteItem(Long id);

    ItemCategories getItemById(Long itemId);

    Item updateItem(ItemCategories existingItem);

    List<ItemCategories> getAllUserItems(Long userId);

    List<ItemCategories> getAll();
}
