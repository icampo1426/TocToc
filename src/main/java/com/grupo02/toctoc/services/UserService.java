package com.grupo02.toctoc.services;

import com.grupo02.toctoc.models.DTOs.UserSignup;
import com.grupo02.toctoc.models.FileEntity;
import com.grupo02.toctoc.models.FileType;
import com.grupo02.toctoc.models.User;
import com.grupo02.toctoc.models.UserRelationship;
import com.grupo02.toctoc.models.dto.LoginPBDTO;
import com.grupo02.toctoc.models.dto.NewUserPBDTO;
import com.grupo02.toctoc.models.dto.UserPBDTO;
import com.grupo02.toctoc.repository.cloudinary.CloudinaryRepository;
import com.grupo02.toctoc.repository.db.FileEntityRepository;
import com.grupo02.toctoc.repository.db.UserRelationshipRepository;
import com.grupo02.toctoc.repository.db.UserRepository;
import com.grupo02.toctoc.repository.rest.pocketbase.createUser.CreateUserPBRepository;
import com.grupo02.toctoc.repository.rest.pocketbase.deleteUser.DeleteUserPBRepository;
import com.grupo02.toctoc.repository.rest.pocketbase.login.LoginPBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginPBRepository loginPBRepository;

    @Autowired
    private CreateUserPBRepository createUserPBRepository;

    @Autowired
    private DeleteUserPBRepository deleteUserPBRepository;

    @Autowired
    private UserRelationshipRepository userRelationshipRepository;

    @Autowired
    private CloudinaryRepository cloudinaryRepository;

    @Autowired
    private FileEntityRepository fileEntityRepository;
    @Autowired
    private EmailService emailService;

    public LoginPBDTO login(String email, String password) {
        return loginPBRepository.execute(email, password).get();
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findUserById(UUID id) {
        return userRepository.findById(id);
    }

    public User createUser(UserSignup user) {
        NewUserPBDTO userPBDTO = userToPBuserMapper(user);
        UserPBDTO userPB=createUserPBRepository.execute(userPBDTO).get();
        User newuser = new User();
        newuser.setName(user.getName());
        newuser.setLastname(user.getLastname());
        newuser.setEmail(user.getEmail());
        newuser.setIdentityId(userPB.getId());
        newuser.setGender(user.getGender());
        userRepository.save(newuser);

        Map<String,Object> variable = new HashMap<>();
        variable.put("userName", user.getName());

        emailService.sendHtmlEmail(user.getEmail(), "Bienvenido a Toctoc", "onboarding.html", variable);

        return newuser;
    }

    public User updateUser(UUID id, User userDetails) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (userDetails.getName() != null) user.setName(userDetails.getName());
            if (userDetails.getLastname() != null) user.setLastname(userDetails.getLastname());
            if (userDetails.getProfileImage() != null) user.setProfileImage(userDetails.getProfileImage());
            if (userDetails.getBannerImage() != null) user.setBannerImage(userDetails.getBannerImage());
            if (userDetails.getBio() != null) user.setBio(userDetails.getBio());
            //if (userDetails.getGender() != 0) user.setGender(userDetails.getGender());
            return userRepository.save(user);
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }
    public List<UserRelationship> getAcceptedRelationships(UUID userId) {
        return userRelationshipRepository.findByRequesterIdAndStatusOrReceiverIdAndStatus(userId, UserRelationship.RelationshipStatus.ACCEPTED, userId, UserRelationship.RelationshipStatus.ACCEPTED);
    }

    public void deleteUser(UUID id) {
        User user = userRepository.findById(id).orElseThrow();
        deleteUserPBRepository.execute(user.getIdentityId());
        userRepository.deleteById(id);

    }

    private NewUserPBDTO userToPBuserMapper(UserSignup userSignup){
        return new NewUserPBDTO(userSignup.getName(), userSignup.getLastname(), userSignup.getEmail(), userSignup.getPassword());
    };

    public UserRelationship createRelationship(UUID requesterId, UUID receiverId) {
        User requester = userRepository.findById(requesterId).orElseThrow(() -> new RuntimeException("Requester not found"));
        User receiver = userRepository.findById(receiverId).orElseThrow(() -> new RuntimeException("Receiver not found"));

        UserRelationship relationship = new UserRelationship();
        relationship.setRequester(requester);
        relationship.setReceiver(receiver);
        relationship.setStatus(UserRelationship.RelationshipStatus.REQUESTED);

        return userRelationshipRepository.save(relationship);
    }
    @Transactional
    public UserRelationship acceptRelationship(UUID relationshipId, UUID userId) {
        UserRelationship relationship = userRelationshipRepository.findById(relationshipId)
                .orElseThrow(() -> new RuntimeException("Relationship not found"));

        if (!relationship.getRequester().getId().equals(userId) && !relationship.getReceiver().getId().equals(userId)) {
            throw new RuntimeException("User is not part of this relationship");
        }

        relationship.setStatus(UserRelationship.RelationshipStatus.ACCEPTED);
        return userRelationshipRepository.save(relationship);
    }

    @Transactional
    public UserRelationship rejectRelationship(UUID relationshipId, UUID userId) {
        UserRelationship relationship = userRelationshipRepository.findById(relationshipId)
                .orElseThrow(() -> new RuntimeException("Relationship not found"));

        if (!relationship.getRequester().getId().equals(userId) && !relationship.getReceiver().getId().equals(userId)) {
            throw new RuntimeException("User is not part of this relationship");
        }

        relationship.setStatus(UserRelationship.RelationshipStatus.REJECTED);
        return userRelationshipRepository.save(relationship);
    }

    @Transactional
    public void saveProfileImage(User user, MultipartFile file) {

        String url =cloudinaryRepository.savePhoto(user.getId().toString(), file);
        FileEntity profileImg= new FileEntity();
        profileImg.setName(user.getId().toString());
        profileImg.setType(FileType.IMAGE);
        profileImg.setUri(url);
        fileEntityRepository.save(profileImg);
        user.setProfileImage(profileImg);
        userRepository.saveAndFlush(user);
        // Implement the logic to save the profile image
        // For example, save the file to a storage service and update the user's profile image URL
    }

    @Transactional
    public void saveBannerImage(User user, MultipartFile file) {

        String url = cloudinaryRepository.savePhoto(user.getId().toString(), file);
        FileEntity profileImg= new FileEntity();
        profileImg.setName(user.getId().toString());
        profileImg.setType(FileType.IMAGE);
        profileImg.setUri(url);
        fileEntityRepository.save(profileImg);
        user.setBannerImage(profileImg);
        userRepository.saveAndFlush(user);
        // Implement the logic to save the profile image
        // For example, save the file to a storage service and update the user's profile image URL

    }

    public void sendFollowRequest(UUID requesterId, UUID receiverId) {
        User requester = userRepository.findById(requesterId).orElseThrow(() -> new RuntimeException("Requester not found"));
        User receiver = userRepository.findById(receiverId).orElseThrow(() -> new RuntimeException("Receiver not found"));

        UserRelationship relationship = new UserRelationship();
        relationship.setRequester(requester);
        relationship.setReceiver(receiver);
        relationship.setStatus(UserRelationship.RelationshipStatus.REQUESTED);

        userRelationshipRepository.save(relationship);
    }

    public List<UserRelationship> getPendingFollowRequests(UUID userId) {
        return userRelationshipRepository.findByReceiverIdAndStatus(userId, UserRelationship.RelationshipStatus.REQUESTED);
    }

    public List<User> getFollowing(UUID userId) {
        List<UserRelationship> relationships = userRelationshipRepository.findByRequesterIdAndStatus(userId, UserRelationship.RelationshipStatus.ACCEPTED);
        return relationships.stream().map(UserRelationship::getReceiver).toList();
    }

    public List<User> getFollowers(UUID userId) {
        List<UserRelationship> relationships = userRelationshipRepository.findByReceiverIdAndStatus(userId, UserRelationship.RelationshipStatus.ACCEPTED);
        return relationships.stream().map(UserRelationship::getRequester).toList();
    }

    @Transactional
    public void acceptFollowRequest(UUID relationshipId, UUID userId) {
        UserRelationship relationship = userRelationshipRepository.findById(relationshipId)
                .orElseThrow(() -> new RuntimeException("Relationship not found"));

        if (!relationship.getReceiver().getId().equals(userId)) {
            throw new RuntimeException("User is not the receiver of this request");
        }

        relationship.setStatus(UserRelationship.RelationshipStatus.ACCEPTED);
        userRelationshipRepository.save(relationship);
    }

    @Transactional
    public void rejectFollowRequest(UUID relationshipId, UUID userId) {
        UserRelationship relationship = userRelationshipRepository.findById(relationshipId)
                .orElseThrow(() -> new RuntimeException("Relationship not found"));

        if (!relationship.getReceiver().getId().equals(userId)) {
            throw new RuntimeException("User is not the receiver of this request");
        }

        relationship.setStatus(UserRelationship.RelationshipStatus.REJECTED);
        userRelationshipRepository.save(relationship);
    }

    @Transactional
    public void removeFollower(UUID userId, UUID followerId) {
        UserRelationship relationship = userRelationshipRepository
                .findByReceiverIdAndRequesterIdAndStatus(userId, followerId, UserRelationship.RelationshipStatus.ACCEPTED)
                .orElseThrow(() -> new RuntimeException("Follower relationship not found"));

        userRelationshipRepository.delete(relationship);
    }

    @Transactional
    public void unfollowUser(UUID userId, UUID followingId) {
        UserRelationship relationship = userRelationshipRepository
                .findByRequesterIdAndReceiverIdAndStatus(userId, followingId, UserRelationship.RelationshipStatus.ACCEPTED)
                .orElseThrow(() -> new RuntimeException("Following relationship not found"));

        userRelationshipRepository.delete(relationship);
    }
}
