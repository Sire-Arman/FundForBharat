package com.example.demo.controller;

import com.example.demo.DTO.UserSessionDTO;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userServices;

    @Autowired
    public UserController(UserService userServices){
        this.userServices = userServices;
    }

    @GetMapping("/get-user")
    public ResponseEntity<UserSessionDTO> getUser(@RequestBody UserSessionDTO user) {


        try {
            UserSessionDTO userSessionDTO = userServices.login(user);

            if (userSessionDTO == null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            }
            return ResponseEntity.ok(userSessionDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    private List<String> validateUser(UserSessionDTO userDto) {
        List<String> errors = new ArrayList<>();

        if (userDto.getUserId() != null && userDto.getUserId() <= 0) {
            errors.add("User ID must be a positive number");
        }
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
        if (userDto.getRoles() == null || userDto.getRoles().isEmpty()) {
            errors.add("At least one role is required");
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
