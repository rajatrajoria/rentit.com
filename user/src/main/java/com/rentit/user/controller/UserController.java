package com.rentit.user.controller;

import com.rentit.user.dto.UserLoginDTO;
import com.rentit.user.dto.UserRegistrationDTO;
import com.rentit.user.service.AuthService;
import com.rentit.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO){
        userService.registerUser(userRegistrationDTO);
        return ResponseEntity.status(HttpStatus.OK).body("User has been registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody UserLoginDTO userLoginDTO){
        return authService.login(userLoginDTO);
    }

}
