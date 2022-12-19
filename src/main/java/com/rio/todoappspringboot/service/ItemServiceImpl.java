package com.rio.todoappspringboot.service;

import com.rio.todoappspringboot.dao.CategoryRepository;
import com.rio.todoappspringboot.dao.ItemCategoryRepository;
import com.rio.todoappspringboot.dao.ItemRepository;
import com.rio.todoappspringboot.dto.ItemCategories;
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
@Transactional
public class ItemServiceImpl implements ItemService {

    private ItemRepository itemRepository;
    private CategoryRepository categoryRepository;
    private ItemCategoryRepository itemCategoryRepository;
    private UserService userService;

    public ItemServiceImpl(ItemRepository itemRepository,
                           CategoryRepository categoryRepository,
                           ItemCategoryRepository itemCategoryRepository,
                           UserService userService) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
        this.itemCategoryRepository = itemCategoryRepository;
        this.userService = userService;
    }

    @Override
    public Item addItem(ItemCategories itemCategories, Long userId) {

        /*Get user, if not found return null*/
        User user = userService.getUserById(userId);
        if(user == null){
            return null;
        }

        //Add user to the new item and save the item in database
        Item Item = itemCategories.getItem();
        Item.setUser(user);
        Item newItem = itemRepository.save(Item);

        //Save item and categories in join table item_category
        itemCategories.getCategories().forEach(category ->
                    itemCategoryRepository.save(new ItemCategory(newItem.getId(), category.getId()))
        );
        return newItem;
    }

    @Override
    public boolean deleteItem(Long id) {

        //Get item and item categories. If not found return null
        ItemCategories itemCategories = getItemById(id);
        if(itemCategories == null){
            return false;
        }

        //Delete all records from join table item_category by itemId
        List<ItemCategory> itemCategoryList = itemCategoryRepository.deleteByItemId(itemCategories.getItem().getId());
            if(itemCategoryList.isEmpty()){
                return false;
            } else {
                itemRepository.deleteById(itemCategories.getItem().getId());
            }
        return true;
    }

    @Override
    public ItemCategories getItemById(Long itemId) {

        Optional<Item> item = itemRepository.findById(itemId);
        if(item.isEmpty()){
            return null;
        }

        Item itemDb = item.get();

        //Find all records from join table item_category by itemId
        List<ItemCategory> itemCategoryList = itemCategoryRepository.findByItemId(itemDb.getId());
        List<Category> categoryList = categoryRepository.findAll();
        ArrayList<Category> categoryArrayList = addCategoryArrayList(itemCategoryList, categoryList);
        return new ItemCategories(itemDb, categoryArrayList);
    }

    @Override
    public Item updateItem(ItemCategories itemCategories) {

        /*Delete all records from join table item_category by itemId.
        After deleting the old records insert the new records with new the values */
        List<ItemCategory> itemCategoryList = itemCategoryRepository.deleteByItemId(itemCategories.getItem().getId());

        if(itemCategoryList.size() > 0){
            itemCategories.getCategories().forEach(category ->
                itemCategoryRepository.save(new ItemCategory(itemCategories.getItem().getId(), category.getId())));
        }
        return itemRepository.save(itemCategories.getItem());
    }

    @Override
    public List<ItemCategories> getAllUserItems(Long userId) {
        List<Item> items = itemRepository.findAllByUserIdOrderByIdDesc(userId);
        return getAllItems(items);
    }

    @Override
    public List<ItemCategories> getAll() {
        List<Item> items = itemRepository.findAll();
        return getAllItems(items);
    }

    /*Items can have multiple categories.
    This method adds the selected categories to a categoryArrayList. */
    private ArrayList<Category> addCategoryArrayList(List<ItemCategory> itemCategoryList, List<Category> categoryList){
        ArrayList<Category> categoryArrayList = new ArrayList<>();
        for (ItemCategory itemCategory : itemCategoryList) {
            for (Category category : categoryList) {
                if (Objects.equals(itemCategory.getCategoryId(), category.getId())) {
                    categoryArrayList.add(category);
                }
            }
        }
        return categoryArrayList;
    }

    //Get all items and add the categories to the items
    private List<ItemCategories> getAllItems(List<Item> items){
        List<Category> categoryList = categoryRepository.findAll();
        List<ItemCategories> itemCategoriesList= new ArrayList<>();

        /*For each item get the item and categories from join table item_category.
         * Add the item and categories to the ItemCategories object */
        for (Item item : items) {
            List<ItemCategory> itemCategories = itemCategoryRepository.findByItemId(item.getId());
            ArrayList<Category> categoryArrayList = addCategoryArrayList(itemCategories, categoryList);
            itemCategoriesList.add(new ItemCategories(item, categoryArrayList));
        }
        return itemCategoriesList;
    }

}


