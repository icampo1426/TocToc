package com.grupo02.toctoc.controllers;

import com.grupo02.toctoc.models.DTOs.UserLogin;
import com.grupo02.toctoc.models.DTOs.UserSignup;
import com.grupo02.toctoc.models.DTOs.UserUpdate;
import com.grupo02.toctoc.models.User;
import com.grupo02.toctoc.models.dto.LoginPBDTO;
import com.grupo02.toctoc.services.UserService;
import com.grupo02.toctoc.utils.AuthUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/search/all")
    @SecurityRequirement(name = "bearer")
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    @PostMapping("/signup")
    public User createUser(@RequestBody UserSignup userSignup) {

        return userService.createUser(userSignup);
    }


    @PostMapping("/login")
    public ResponseEntity loginUser(@RequestBody UserLogin userLogin) {
        LoginPBDTO u = userService.login(userLogin.getEmail(), userLogin.getPassword());
        return ResponseEntity.ok(new HashMap() {{
            put("token", u.getToken());
        }});
    }

    /**
     * @GetMapping("/{id}")
     * @SecurityRequirement(name = "bearer")
     * public ResponseEntity<User> getUserById(@PathVariable Long id) {
     * Optional<User> user = userService.findUserById(id);
     * return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
     * }
     */

    @GetMapping("")
    @SecurityRequirement(name = "bearer")
    public ResponseEntity<User> getUserById() {

        Optional<User> userAuth = AuthUtils.getCurrentAuthUser(User.class);
        if (userAuth.isPresent()) {

            Optional<User> user = userService.findUserById(userAuth.get().getId());
            return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("")
    @SecurityRequirement(name = "bearer")
    public ResponseEntity<User> updateUser(@RequestBody UserUpdate userUpdate) {

        Optional<User> userAuth = AuthUtils.getCurrentAuthUser(User.class);
        if (userAuth.isPresent()) {

            User user = new User();
            user.setName(userUpdate.getName());
            user.setLastname(userUpdate.getLastname());
            //user.setProfileImage(userUpdate.getProfileImage());
            //user.setBannerImage(userUpdate.getBannerImage());
            user.setBio(userUpdate.getBio());
            //user.setGender(userUpdate.getGender());
            try {
                return ResponseEntity.ok(userService.updateUser(userAuth.get().getId(), user));
            } catch (RuntimeException e) {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("")
    @SecurityRequirement(name = "bearer")
    public ResponseEntity<Void> deleteUser() {
        Optional<User> userAuth = AuthUtils.getCurrentAuthUser(User.class);
        if (userAuth.isPresent()) {
            userService.deleteUser(userAuth.get().getId());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
