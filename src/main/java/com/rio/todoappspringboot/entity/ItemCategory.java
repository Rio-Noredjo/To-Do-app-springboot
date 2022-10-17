package com.rio.todoappspringboot.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Table(name="item_category")
@Getter
@Setter
public class ItemCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name = "item_id")
    Long itemId;

    @Column(name = "category_id")
    Long categoryIid;

}
