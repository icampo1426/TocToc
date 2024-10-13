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
        // Validaciones adicionales pueden ir aquí
        return userRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        return userRepository.findById(id)
                .map(user -> {
                    if (userDetails.getName() != null) user.setName(userDetails.getName());
                    if (userDetails.getLastname() != null) user.setLastname(userDetails.getLastname());
                    if (userDetails.getProfileImage() != null) user.setProfileImage(userDetails.getProfileImage());
                    if (userDetails.getBannerImage() != null) user.setBannerImage(userDetails.getBannerImage());
                    if (userDetails.getBio() != null) user.setBio(userDetails.getBio());
                    if (userDetails.getGender() != 0) user.setGender(userDetails.getGender());
                    return userRepository.save(user);
                }).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("Usuario no encontrado para eliminar");
        }
    }
}

