package com.fullstack.dream.dao;

import com.fullstack.dream.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductDao extends JpaRepository<Product,Long> {

    Optional<Product> findProductById(Long id);

    Product getProductById(Long id);
}
