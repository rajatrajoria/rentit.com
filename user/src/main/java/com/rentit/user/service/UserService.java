package com.rentit.user.service;

import com.rentit.user.dto.UserRegistrationDTO;
import com.rentit.user.exception.UserAlreadyExistsException;
import com.rentit.user.model.UserEntity;
import com.rentit.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void registerUser(UserRegistrationDTO userDTO){
        Optional<UserEntity> existingUser = userRepository.findByEmail(userDTO.getEmail());
        if(existingUser.isPresent()){
            throw new UserAlreadyExistsException("This email is already registered");
        }
        UserEntity newUser = new UserEntity();
        newUser.setEmail(userDTO.getEmail());
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        newUser.setCreatedAt(LocalDateTime.now());
        userRepository.save(newUser);
    }
}
