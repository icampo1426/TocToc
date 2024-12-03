package com.grupo02.toctoc.controllers;

import com.grupo02.toctoc.models.DTOs.UserLogin;
import com.grupo02.toctoc.models.DTOs.UserSignup;
import com.grupo02.toctoc.models.DTOs.UserUpdate;
import com.grupo02.toctoc.models.User;
import com.grupo02.toctoc.models.UserRelationship;
import com.grupo02.toctoc.models.dto.LoginPBDTO;
import com.grupo02.toctoc.repository.rest.pocketbase.refresh.RefreshToken;
import com.grupo02.toctoc.repository.rest.pocketbase.resetPassword.ResetPassword;
import com.grupo02.toctoc.services.UserService;
import com.grupo02.toctoc.utils.AuthUtils;
import com.grupo02.toctoc.utils.JWTUtils;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ResetPassword resetPasswordService;

    @Autowired
    private RefreshToken refreshToken;

    @PostMapping("/reset-password")
    public void resetPassword(@RequestBody ResetPasswordDTO email) {
        userService.findByEmail(email.email)
                .orElseThrow(() -> new NotFoundException("User not found"));

        this.resetPasswordService.execute(email.email);
    }

    public record ResetPasswordDTO(String email) {
    }

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
        String refresh = JWTUtils.generateToken(userLogin.getEmail(), u.getToken(), u.getRecord().getId());

        return ResponseEntity.ok(new HashMap() {{
            put("token", u.getToken());
            put("refreshToken", refresh);
        }});

    }

    @PostMapping("/refresh")
    public ResponseEntity refresh(@RequestHeader("Authorization") String jwt) {

        Claims refresh = JWTUtils.deserializeToken(jwt.replace("Bearer ", ""));
        String newtoken = refreshToken.execute(refresh.get("lastToken").toString()).get().getToken();
        String newRefresh = JWTUtils.generateToken(refresh.get("email").toString(), newtoken, refresh.get("identity_id").toString());

        return ResponseEntity.ok(new HashMap() {{
            put("token", newtoken);
            put("refreshToken", newRefresh);
        }});

    }

    @SecurityRequirement(name = "bearer")
    @PostMapping("/imgProfile")
    public ResponseEntity imgProfile(@RequestParam("file") MultipartFile file) {

        Optional<User> userAuth = AuthUtils.getCurrentAuthUser(User.class);
        if (userAuth.isPresent()) {
            // Handle file upload and save the profile image
            // Assuming you have a method in UserService to handle the file upload
            userService.saveProfileImage(userAuth.get(), file);

            Optional<User> user = userService.findUserById(userAuth.get().getId());
            return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }
        return ResponseEntity.notFound().build();
    }

    @SecurityRequirement(name = "bearer")
    @PostMapping("/imgBanner")
    public ResponseEntity imgBanner(@RequestParam("file") MultipartFile file) {

        Optional<User> userAuth = AuthUtils.getCurrentAuthUser(User.class);
        if (userAuth.isPresent()) {
            // Handle file upload and save the profile image
            // Assuming you have a method in UserService to handle the file upload
            userService.saveBannerImage(userAuth.get(), file);

            Optional<User> user = userService.findUserById(userAuth.get().getId());
            return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }
        return ResponseEntity.notFound().build();
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

    @PostMapping("/users/relationships/{receiverId}")
    @SecurityRequirement(name = "bearer")
    public ResponseEntity createRelationship(@PathVariable UUID receiverId) {
        Optional<User> userAuth = AuthUtils.getCurrentAuthUser(User.class);
        if (userAuth.isPresent()) {
            UserRelationship res = userService.createRelationship(userAuth.get().getId(), receiverId);
            return ResponseEntity.ok(new HashMap<>() {{
                put("relationshipId", res.getId());
            }});
        }
        return ResponseEntity.badRequest().build();
    }


    @PutMapping("/relationships/{relationshipId}/accept")
    @SecurityRequirement(name = "bearer")
    public UserRelationship acceptRelationship(@PathVariable UUID relationshipId) {
        Optional<User> userAuth = AuthUtils.getCurrentAuthUser(User.class);
        if (userAuth.isPresent()) {
            return userService.acceptRelationship(relationshipId, userAuth.get().getId());
        }
        throw new RuntimeException("User not authenticated");
    }

    @PutMapping("/relationships/{relationshipId}/reject")
    @SecurityRequirement(name = "bearer")
    public UserRelationship rejectRelationship(@PathVariable UUID relationshipId) {
        Optional<User> userAuth = AuthUtils.getCurrentAuthUser(User.class);
        if (userAuth.isPresent()) {
            return userService.rejectRelationship(relationshipId, userAuth.get().getId());
        }
        throw new RuntimeException("User not authenticated");
    }
}
