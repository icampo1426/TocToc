package com.grupo02.toctoc.controllers;

import com.grupo02.toctoc.models.DTOs.UserLogin;
import com.grupo02.toctoc.models.DTOs.UserSignup;
import com.grupo02.toctoc.models.DTOs.UserUpdate;
import com.grupo02.toctoc.models.User;
import com.grupo02.toctoc.models.dto.LoginPBDTO;
import com.grupo02.toctoc.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @SecurityRequirement(name = "bearer")
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    @PostMapping("/login")
    public ResponseEntity loginUser(@RequestBody UserLogin userLogin) {
        LoginPBDTO u = userService.login(userLogin.getEmail(), userLogin.getPassword());
        return ResponseEntity.ok(new HashMap() {{
            put("token", u.getToken());
        }});
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearer")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.findUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/signup")
    public User createUser(@RequestBody UserSignup userSignup) {

        return userService.createUser(userSignup);
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearer")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserUpdate userUpdate) {
        User user = new User();
        user.setName(userUpdate.getName());
        user.setLastname(userUpdate.getLastname());
        //user.setProfileImage(userUpdate.getProfileImage());
        //user.setBannerImage(userUpdate.getBannerImage());
        user.setBio(userUpdate.getBio());
        //user.setGender(userUpdate.getGender());
        try {
            return ResponseEntity.ok(userService.updateUser(id, user));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearer")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }


}
