package com.rio.todoappspringboot.service;

import com.rio.todoappspringboot.dao.ItemCategoryRepository;
import com.rio.todoappspringboot.dao.ItemRepository;
import com.rio.todoappspringboot.dto.ItemDto;
import com.rio.todoappspringboot.entity.Item;
import com.rio.todoappspringboot.entity.ItemCategory;
import com.rio.todoappspringboot.entity.User;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    private ItemRepository itemRepository;
    private ItemCategoryRepository itemCategoryRepository;
    private UserService userService;

    public ItemServiceImpl(
            ItemRepository itemRepository,
            ItemCategoryRepository itemCategoryRepository,
            UserService userService) {
        this.itemRepository = itemRepository;
        this.itemCategoryRepository = itemCategoryRepository;
        this.userService = userService;
    }

    @Transactional
    @Override
    public Item addItem(ItemDto ItemDto, Long userId) {
        User user = userService.getUserById(userId);
        if(user == null){
            return null;
        }
        Item Item = ItemDto.getItem();
        Item.setUser(user);
        Item newItem = itemRepository.save(Item);
        ItemDto.getCategories().forEach(category ->
                    itemCategoryRepository.save(new ItemCategory(newItem.getId(), category.getId()))
        );
        return newItem;
    }


    @Override
    public Item updateItem(Item item) {
        return null;
    }

    @Override
    public List<Item> getAll() {
        return null;
    }

    @Override
    public Item getItemById(Long id) {
        return null;
    }

    @Override
    public boolean deleteItem(Long id) {
        return false;
    }

}
