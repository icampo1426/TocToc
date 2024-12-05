package com.grupo02.toctoc.controllers;

import com.grupo02.toctoc.models.DTOs.UserLogin;
import com.grupo02.toctoc.models.DTOs.UserSignup;
import com.grupo02.toctoc.models.DTOs.UserUpdate;
import com.grupo02.toctoc.models.User;
import com.grupo02.toctoc.models.UserRelationship;
import com.grupo02.toctoc.models.dto.LoginPBDTO;
import com.grupo02.toctoc.repository.db.UserRepository;
import com.grupo02.toctoc.repository.rest.pocketbase.refresh.RefreshToken;
import com.grupo02.toctoc.repository.rest.pocketbase.resetPassword.ResetPassword;
import com.grupo02.toctoc.services.UserService;
import com.grupo02.toctoc.utils.AuthUtils;
import com.grupo02.toctoc.utils.JWTUtils;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private UserRepository userRepository;

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
    @PostMapping(value = "/imgProfile",consumes = {"multipart/form-data"})
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
    @PostMapping(value = "/imgBanner",consumes = {"multipart/form-data"})
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

    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearer")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        Optional<User> user = userService.findUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

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

//    @PostMapping("/users/relationships/{receiverId}")
//    @SecurityRequirement(name = "bearer")
//    public ResponseEntity createRelationship(@PathVariable UUID receiverId) {
//        Optional<User> userAuth = AuthUtils.getCurrentAuthUser(User.class);
//        if (userAuth.isPresent()) {
//            UserRelationship res = userService.createRelationship(userAuth.get().getId(), receiverId);
//            return ResponseEntity.ok(new HashMap<>() {{
//                put("relationshipId", res.getId());
//            }});
//        }
//        return ResponseEntity.badRequest().build();
//    }
//
//    @PutMapping("/relationships/{relationshipId}/accept")
//    @SecurityRequirement(name = "bearer")
//    public UserRelationship acceptRelationship(@PathVariable UUID relationshipId) {
//        Optional<User> userAuth = AuthUtils.getCurrentAuthUser(User.class);
//        if (userAuth.isPresent()) {
//            return userService.acceptRelationship(relationshipId, userAuth.get().getId());
//        }
//        throw new RuntimeException("User not authenticated");
//    }
//
//    @PutMapping("/relationships/{relationshipId}/reject")
//    @SecurityRequirement(name = "bearer")
//    public UserRelationship rejectRelationship(@PathVariable UUID relationshipId) {
//        Optional<User> userAuth = AuthUtils.getCurrentAuthUser(User.class);
//        if (userAuth.isPresent()) {
//            return userService.rejectRelationship(relationshipId, userAuth.get().getId());
//        }
//        throw new RuntimeException("User not authenticated");
//    }

    @PostMapping("/follow/{receiverId}")
    @SecurityRequirement(name = "bearer")
    public ResponseEntity<String> sendFollowRequest(@PathVariable UUID receiverId) {
        Optional<User> userAuth = AuthUtils.getCurrentAuthUser(User.class);
        if (userAuth.isPresent()) {
            userService.sendFollowRequest(userAuth.get().getId(), receiverId);
            return ResponseEntity.ok("Follow request sent successfully.");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated.");
    }

    @GetMapping("/follow/requests")
    @SecurityRequirement(name = "bearer")
    public ResponseEntity<List<UserRelationship>> getPendingFollowRequests() {
        Optional<User> userAuth = AuthUtils.getCurrentAuthUser(User.class);
        if (userAuth.isPresent()) {
            List<UserRelationship> pendingRequests = userService.getPendingFollowRequests(userAuth.get().getId());
            return ResponseEntity.ok(pendingRequests);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/following")
    @SecurityRequirement(name = "bearer")
    public ResponseEntity<List<User>> getFollowing() {
        Optional<User> userAuth = AuthUtils.getCurrentAuthUser(User.class);
        if (userAuth.isPresent()) {
            List<User> following = userService.getFollowing(userAuth.get().getId());
            return ResponseEntity.ok(following);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/followers")
    @SecurityRequirement(name = "bearer")
    public ResponseEntity<List<User>> getFollowers() {
        Optional<User> userAuth = AuthUtils.getCurrentAuthUser(User.class);
        if (userAuth.isPresent()) {
            List<User> followers = userService.getFollowers(userAuth.get().getId());
            return ResponseEntity.ok(followers);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PutMapping("/follow/{relationshipId}/accept")
    @SecurityRequirement(name = "bearer")
    public ResponseEntity<String> acceptFollowRequest(@PathVariable UUID relationshipId) {
        Optional<User> userAuth = AuthUtils.getCurrentAuthUser(User.class);
        if (userAuth.isPresent()) {
            userService.acceptFollowRequest(relationshipId, userAuth.get().getId());
            return ResponseEntity.ok("Follow request accepted.");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated.");
    }

    @PutMapping("/follow/{relationshipId}/reject")
    @SecurityRequirement(name = "bearer")
    public ResponseEntity<String> rejectFollowRequest(@PathVariable UUID relationshipId) {
        Optional<User> userAuth = AuthUtils.getCurrentAuthUser(User.class);
        if (userAuth.isPresent()) {
            userService.rejectFollowRequest(relationshipId, userAuth.get().getId());
            return ResponseEntity.ok("Follow request rejected.");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated.");
    }

    @DeleteMapping("/followers/{followerId}")
    @SecurityRequirement(name = "bearer")
    public ResponseEntity<String> removeFollower(@PathVariable UUID followerId) {
        Optional<User> userAuth = AuthUtils.getCurrentAuthUser(User.class);
        if (userAuth.isPresent()) {
            userService.removeFollower(userAuth.get().getId(), followerId);
            return ResponseEntity.ok("Follower removed successfully.");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated.");
    }

    @DeleteMapping("/following/{followingId}")
    @SecurityRequirement(name = "bearer")
    public ResponseEntity<String> unfollowUser(@PathVariable UUID followingId) {
        Optional<User> userAuth = AuthUtils.getCurrentAuthUser(User.class);
        if (userAuth.isPresent()) {
            userService.unfollowUser(userAuth.get().getId(), followingId);
            return ResponseEntity.ok("Unfollowed user successfully.");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated.");
    }


    @GetMapping("/search/name/{name}")
    public ResponseEntity searchByName(@PathVariable String name) {

            List<User> users = userRepository.findAllByNameContaining(name);
            return ResponseEntity.ok(users);
    }

    @GetMapping("/search/lastname/{lastname}")
    public ResponseEntity searchByLastName(@PathVariable String lastname) {

        List<User> users = userRepository.findAllByLastnameContaining(lastname);
        return ResponseEntity.ok(users);
    }

}
