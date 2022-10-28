package com.rio.todoappspringboot.dto;

import com.rio.todoappspringboot.entity.Category;
import com.rio.todoappspringboot.entity.Item;
import lombok.Data;
import java.util.ArrayList;

@Data
public class ItemDto {
    private Item item;
    private ArrayList<Category> categories = new ArrayList<>();

}
