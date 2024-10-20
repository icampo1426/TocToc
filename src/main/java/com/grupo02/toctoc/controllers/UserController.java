package com.grupo02.toctoc.controllers;

import com.grupo02.toctoc.DTOs.UserSignup;
import com.grupo02.toctoc.DTOs.UserUpdate;
import com.grupo02.toctoc.models.User;
import com.grupo02.toctoc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.findUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public User createUser(@RequestBody UserSignup userSignup) {
        User user = new User();
        user.setName(userSignup.getName());
        user.setLastname(userSignup.getLastname());
        user.setEmail(userSignup.getEmail());
        User savedUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser).getBody();
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserUpdate userUpdate) {
        User user = new User();
        user.setName(userUpdate.getName());
        user.setLastname(userUpdate.getLastname());
        user.setProfileImage(userUpdate.getProfileImage());
        user.setBannerImage(userUpdate.getBannerImage());
        user.setBio(userUpdate.getBio());
        user.setGender(userUpdate.getGender());
        try {
            return ResponseEntity.ok(userService.updateUser(id, user));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }


}
