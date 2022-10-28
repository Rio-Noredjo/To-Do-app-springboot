package com.rio.todoappspringboot.service;

import com.rio.todoappspringboot.dao.CategoryRepository;
import com.rio.todoappspringboot.dao.ItemCategoryRepository;
import com.rio.todoappspringboot.dao.ItemRepository;
import com.rio.todoappspringboot.dto.ItemDto;
import com.rio.todoappspringboot.entity.Category;
import com.rio.todoappspringboot.entity.Item;
import com.rio.todoappspringboot.entity.ItemCategory;
import com.rio.todoappspringboot.entity.User;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    private ItemRepository itemRepository;
    private CategoryRepository categoryRepository;
    private ItemCategoryRepository itemCategoryRepository;
    private UserService userService;

    public ItemServiceImpl(
            ItemRepository itemRepository,
            CategoryRepository categoryRepository,
            ItemCategoryRepository itemCategoryRepository,
            UserService userService) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
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

    @Override
    public ItemDto findById(Long itemId) {
        Optional<Item> item = itemRepository.findById(itemId);
        Item itemDb = item.get();
        if(itemDb != null){
            List<ItemCategory> itemCategories = itemCategoryRepository.findByItemId(itemDb.getId());
            ArrayList<Category> categoryArrayList = new ArrayList<>();
            List<Category> categoryList = categoryRepository.findAll();

            for (int i = 0; i < itemCategories.size(); i++) {
                for(int x = 0; x < categoryList.size(); x++){
                    if(Objects.equals(itemCategories.get(i).getCategoryIid(), categoryList.get(x).getId())){
                        categoryArrayList.add(categoryList.get(x));
                    }
                }
            }
            ItemDto itemDto = new ItemDto(itemDb, categoryArrayList);
            return itemDto;
        }
        return null;
    }

    @Override
    @Transactional
    public Item updateItem(ItemDto ItemDto) {
        List<ItemCategory> itemCategoryListOld = itemCategoryRepository.deleteByItemId(ItemDto.getItem().getId());
        if(itemCategoryListOld.size() > 0){
            ItemDto.getCategories().forEach(category ->
                itemCategoryRepository.save(new ItemCategory(ItemDto.getItem().getId(), category.getId()))
        );
        }
        Item item = itemRepository.save(ItemDto.getItem());
        return item;
    }

}
