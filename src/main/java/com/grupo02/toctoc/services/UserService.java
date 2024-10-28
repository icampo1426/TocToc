package com.grupo02.toctoc.services;

import com.grupo02.toctoc.models.DTOs.UserSignup;
import com.grupo02.toctoc.models.User;
import com.grupo02.toctoc.models.dto.LoginPBDTO;
import com.grupo02.toctoc.models.dto.NewUserPBDTO;
import com.grupo02.toctoc.models.dto.UserPBDTO;
import com.grupo02.toctoc.repository.db.UserRepository;
import com.grupo02.toctoc.repository.rest.pocketbase.createUser.CreateUserPBRepository;
import com.grupo02.toctoc.repository.rest.pocketbase.login.LoginPBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginPBRepository loginPBRepository;

    @Autowired
    private CreateUserPBRepository createUserPBRepository;

    public LoginPBDTO login(String email, String password) {
        return loginPBRepository.execute(email, password).get();
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
        return userRepository.save(newuser);
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

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    private NewUserPBDTO userToPBuserMapper(UserSignup userSignup){
        return new NewUserPBDTO(userSignup.getName(), userSignup.getLastname(), userSignup.getEmail(), userSignup.getPassword());
    };
}
