package com.rio.todoappspringboot.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="item_category")
public class ItemCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name = "item_id")
    Long itemId;

    @Column(name = "category_id")
    Long categoryId;

    public ItemCategory(Long itemId, Long categoryId) {
        this.itemId = itemId;
        this.categoryId = categoryId;
    }

    public ItemCategory() {}

}
