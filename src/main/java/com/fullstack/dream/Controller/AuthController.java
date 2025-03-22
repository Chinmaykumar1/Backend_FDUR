package com.fullstack.dream.Controller;

import com.fullstack.dream.Service.UserService;
import com.fullstack.dream.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "https://dream-front-mhz68d36l-chinmaykumar1s-projects.vercel.app")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("register")
    public ResponseEntity<?> registerUser(@RequestBody User user){
        return userService.registerUser(user);
    }

    @PostMapping("signin")
    public ResponseEntity<?> loginUser(@RequestBody User loginUser){
        return userService.login(loginUser);
    }
}