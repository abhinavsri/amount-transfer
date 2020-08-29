package com.example.payment.transfer.controller;

import com.example.payment.transfer.exception.UserNotFoundException;
import com.example.payment.transfer.model.User;
import com.example.payment.transfer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")

public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("users/{id}")
    public ResponseEntity<User> getUser(@PathVariable(value="id") Long id) throws UserNotFoundException {
        return ResponseEntity.ok().body(userService.getUser(id));
    }
    @PostMapping("users")
    public User getUser(@Valid @RequestBody User user)  {
        return userService.save(user);
    }

    @PutMapping("users/{id}")
    public User updateUser(@PathVariable(value="id") Long id, @Valid @RequestBody User userDetails) throws UserNotFoundException {
        return userService.updateUser(id,userDetails);
    }
}
