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


    @Transactional
    @Override
    public boolean deleteItem(Long id) {
        ItemDto itemDto = findById(id);
        boolean isRemoved = false;
        if(itemDto == null){
            return isRemoved;
        } else {
            List<ItemCategory> itemCategoryListOld = itemCategoryRepository.deleteByItemId(itemDto.getItem().getId());
            if(itemCategoryListOld.isEmpty()){
                return isRemoved;
            } else {
                itemRepository.deleteById(itemDto.getItem().getId());
                isRemoved = true;
            }
        }
        return isRemoved;
    }

    @Transactional
    @Override
    public ItemDto findById(Long itemId) {
        Optional<Item> item = itemRepository.findById(itemId);
        Item itemDb = item.get();
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

    @Transactional
    @Override
    public Item updateItem(ItemDto itemDto) {
        List<ItemCategory> itemCategoryListOld = itemCategoryRepository.deleteByItemId(itemDto.getItem().getId());
        if(itemCategoryListOld.size() > 0){
            itemDto.getCategories().forEach(category ->
                itemCategoryRepository.save(new ItemCategory(itemDto.getItem().getId(), category.getId()))
        );
        }
        return itemRepository.save(itemDto.getItem());
    }

    @Transactional
    @Override
    public List<ItemDto> getAllUserItems(Long userId) {
        List<Item> items = itemRepository.findAllByUserId(userId);
        List<Category> categoryList = categoryRepository.findAll();
        List<ItemDto> itemDb= new ArrayList<>();

        for (int i = 0; i < items.size(); i++) {
            List<ItemCategory> itemCategories = itemCategoryRepository.findByItemId(items.get(i).getId());
            ArrayList<Category> categoryArrayList = new ArrayList<>();
            for (int x = 0; x < itemCategories.size(); x++) {
                for(int y = 0; y < categoryList.size(); y++){
                    if(Objects.equals(itemCategories.get(x).getCategoryIid(), categoryList.get(y).getId())){
                        categoryArrayList.add(categoryList.get(y));
                    }
                }
            }
            itemDb.add(new ItemDto(items.get(i), categoryArrayList));
        }
        return itemDb;
    }

    @Transactional
    @Override
    public List<ItemDto> getAllItems() {
        List<Item> items = itemRepository.findAll();
        List<Category> categoryList = categoryRepository.findAll();
        List<ItemDto> itemDb= new ArrayList<>();

        for (int i = 0; i < items.size(); i++) {
            List<ItemCategory> itemCategories = itemCategoryRepository.findByItemId(items.get(i).getId());
            ArrayList<Category> categoryArrayList = new ArrayList<>();
            for (int x = 0; x < itemCategories.size(); x++) {
                for(int y = 0; y < categoryList.size(); y++){
                    if(Objects.equals(itemCategories.get(x).getCategoryIid(), categoryList.get(y).getId())){
                        categoryArrayList.add(categoryList.get(y));
                    }
                }
            }
            itemDb.add(new ItemDto(items.get(i), categoryArrayList));
        }
        return itemDb;
    }
}


