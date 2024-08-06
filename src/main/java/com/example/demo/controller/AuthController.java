package com.example.demo.controller;

import com.example.demo.DTO.UserSessionDTO;
import com.example.demo.Exceptions.UserAlreadyExistsException;
import com.example.demo.service.UserService;
import com.example.demo.Exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/auth")
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
            if (!userServices.UserEmailExists(user.getEmail())) {
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
        List<String> errors = validateUser(user);
        if (!errors.isEmpty()) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        try {
            // Validate input
            if (user == null || user.getEmail() == null || user.getUsername() == null || user.getPassword() == null) {
                return ResponseEntity.badRequest().body("Email, username, and password are required");
            }

            // Check if user already exists
            if (userServices.UserEmailExists(user.getEmail())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("A user with this email already exists");
            }
            if (userServices.UserUsernameExists(user.getUsername())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("A user with this username already exists");
            }

            // Attempt registration
            UserSessionDTO registeredUser = userServices.register(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);

        } catch (Exception e) {
            // Log the exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during registration");
        }
    }
    private List<String> validateUser(UserSessionDTO userDto) {
        List<String> errors = new ArrayList<>();

        if (userDto.getEmail() == null || userDto.getEmail().trim().isEmpty()) {
            errors.add("Email is required");
        } else if (!isValidEmail(userDto.getEmail())) {
            errors.add("Invalid email format");
        }
        if (userDto.getUsername() == null || userDto.getUsername().trim().isEmpty()) {
            errors.add("Username is required");
        }
        if (userDto.getFullname() == null || userDto.getFullname().trim().isEmpty()) {
            errors.add("Full name is required");
        }
        if (userDto.getPassword() == null || userDto.getPassword().trim().isEmpty()) {
            errors.add("Password is required");
        } else if (userDto.getPassword().length() < 8) {
            errors.add("Password must be at least 8 characters long");
        }
        // errorMessage and token can be null or empty, so we don't validate them

        return errors;
    }

    // Helper method to validate email format
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (email == null) return false;
        return pattern.matcher(email).matches();
    }

}
