package com.fullstack.dream.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
public class Product {

    @Id
    private Long id;
    private String item;
    private int price;

    public Product() {

    }
}
