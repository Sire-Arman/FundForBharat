package com.example.demo.controller;

import com.example.demo.DTO.UserSessionDTO;
import com.example.demo.Exceptions.UserAlreadyExistsException;
import com.example.demo.service.UserService;
import com.example.demo.Exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    private final UserService userServices;

    @Autowired
    public AuthController(UserService userServices) {this.userServices = userServices;}

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserSessionDTO user) {
        try {
            // Validate input
            if (user == null || user.getEmail() == null || user.getPassword() == null) {
                return ResponseEntity.badRequest().body("Email and password are required");
            }

            // Check if user exists
            if (!userServices.UserExists(user.getEmail())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            // Attempt login
            UserSessionDTO loggedInUser = userServices.login(user);
            if (loggedInUser != null) {
                return ResponseEntity.ok(loggedInUser);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }

        } catch (Exception e) {
            // Log the exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during login");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> signup(@RequestBody UserSessionDTO user) {
        try {
            // Validate input
            if (user == null || user.getEmail() == null || user.getUsername() == null || user.getPassword() == null) {
                return ResponseEntity.badRequest().body("Email, username, and password are required");
            }

            if (user.getRoles() == null || user.getRoles().isEmpty()) {
                return ResponseEntity.badRequest().body("At least one role must be specified");
            }

            // Check if user already exists
            if (userServices.UserExists(user.getEmail())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("A user with this email already exists");
            }

            // Attempt registration
            UserSessionDTO registeredUser = userServices.register(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);

        } catch (Exception e) {
            // Log the exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during registration");
        }
    }

}
