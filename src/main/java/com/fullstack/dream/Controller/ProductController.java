package com.fullstack.dream.Controller;



import com.fullstack.dream.Service.UserService;
import com.fullstack.dream.model.Product;
import com.fullstack.dream.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    UserService service;

    @GetMapping("/products")
    public List<Product> getProducts(){
        return  service.getProducts();
    }

    @GetMapping("/products/{prodId}")
    public Product getProductById(@PathVariable long prodId){
        return service.getProductById(prodId);
    }

    @PostMapping("/products")
    public void addProducts(@RequestBody Product product){
        service.addProdouct(product);
    }
    @PutMapping("/products")
    public String updateProduct(@RequestBody Product product){
        return  service.updateProduct(product);
    }

}
