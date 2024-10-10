package com.grupo02.toctoc.services;

import com.grupo02.toctoc.models.User;
import com.grupo02.toctoc.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (userDetails.getName() != null) user.setName(userDetails.getName());
            if (userDetails.getLastname() != null) user.setLastname(userDetails.getLastname());
            if (userDetails.getProfileImage() != null) user.setProfileImage(userDetails.getProfileImage());
            if (userDetails.getBannerImage() != null) user.setBannerImage(userDetails.getBannerImage());
            if (userDetails.getBio() != null) user.setBio(userDetails.getBio());
            if (userDetails.getGender() != 0) user.setGender(userDetails.getGender());
            return userRepository.save(user);
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
