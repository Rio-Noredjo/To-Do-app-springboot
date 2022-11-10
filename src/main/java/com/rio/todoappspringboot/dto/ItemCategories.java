package com.rio.todoappspringboot.dto;

import com.rio.todoappspringboot.entity.Category;
import com.rio.todoappspringboot.entity.Item;
import lombok.Data;
import java.util.ArrayList;

@Data
public class ItemCategories {
    private Item item;
    private ArrayList<Category> categories;

    public ItemCategories(Item item, ArrayList<Category> categories) {
        this.item = item;
        this.categories = categories;
    }

}
