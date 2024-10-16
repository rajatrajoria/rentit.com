package com.rentit.user.service;

import com.rentit.user.dto.UserLoginDTO;
import com.rentit.user.exception.UserNotFoundException;
import com.rentit.user.model.UserEntity;
import com.rentit.user.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public ResponseEntity<String> login(UserLoginDTO userLoginDTO){
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
