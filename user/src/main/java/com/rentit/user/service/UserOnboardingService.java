package com.rentit.user.service;

import com.rentit.user.dto.UserLoginDTO;
import com.rentit.user.dto.UserRegistrationDTO;
import com.rentit.user.exception.UserAlreadyExistsException;
import com.rentit.user.model.UserEntity;
import com.rentit.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserOnboardingService {
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

    public ResponseEntity<String> loginUser(UserLoginDTO userLoginDTO){
        Optional<UserEntity> existingUser = userRepository.findByEmail(userLoginDTO.getEmail());
        if(existingUser.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("incorrect email or password");
        }
        if(!passwordEncoder.matches(userLoginDTO.getPassword(), existingUser.get().getPassword())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("incorrect email or password");
        }
        return ResponseEntity.status(HttpStatus.OK).body("user is authenticated successfully");
    }
}
