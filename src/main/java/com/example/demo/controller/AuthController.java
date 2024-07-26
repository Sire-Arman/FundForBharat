package com.example.demo.controller;

import com.example.demo.DTO.UserSessionDTO;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    private final UserService userServices;

    @Autowired
    public AuthController(UserService userServices) {
        this.userServices = userServices;

    }

    @PostMapping("/login")
    public ResponseEntity<UserSessionDTO> login(@RequestBody UserSessionDTO user) {
        return ResponseEntity.ok(userServices.login(user));
    }

    @PostMapping("/register")
    public ResponseEntity<UserSessionDTO> signup(@RequestBody UserSessionDTO user) {

        return ResponseEntity.ok(userServices.register(user));
    }

}
