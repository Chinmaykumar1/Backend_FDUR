package com.fullstack.dream.Service;

import com.fullstack.dream.dao.ProductDao;
import com.fullstack.dream.dao.UserRepository;
import com.fullstack.dream.model.Product;
import com.fullstack.dream.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    ProductDao productDao;

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public ResponseEntity<?> registerUser(User user){
        if(userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("status", "error", "message", "User Already Registered "+user.getUsername()));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
         User savedUser = userRepository.save(user);
         return  ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                 "status", "success", "message","User Registered successfully",
                 "data",savedUser
         ));
    }


    // SIGNIN
    public ResponseEntity<?> login(User user){
        Optional<User> userOptional = userRepository.findByUsername(user.getUsername());

        if(userOptional.isPresent()){
            if(passwordEncoder.matches(user.getPassword(),userOptional.get().getPassword())){
                return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                        "status","success",
                        "message","Successful Sign in",
                        "data",userOptional
                ));
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                        "status","Failed",
                        "message","Wrong Password",
                        "data",userOptional
                ));
            }
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status","Failed",
                    "message","User Not Found, Please Register"
            ));
        }

    }

    public List<Product> getProducts() {

        return productDao.findAll();
    }

    public Product getProductById(long prodId){
        return productDao.getProductById(prodId);
    }

    public void addProdouct(Product product) {

        productDao.save(product);

    }

    public String updateProduct(Product product) {
       if(productDao.existsById(product.getId())){
           productDao.save(product);
       }else{
           return "Product Doesn't exits";
       }
       return "Product Saved with"+product.getId();
    }
}