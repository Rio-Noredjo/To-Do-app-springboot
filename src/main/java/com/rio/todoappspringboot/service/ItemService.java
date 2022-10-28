package com.rio.todoappspringboot.service;


import com.rio.todoappspringboot.dto.ItemDto;
import com.rio.todoappspringboot.entity.Item;

import java.util.List;

public interface ItemService {

    Item addItem(ItemDto item, Long userId);

    Item updateItem(Item item);

    List<Item> getAll();

    Item getItemById(Long id);

    boolean deleteItem(Long id);
}
