package com.example.demo.controller;

import com.example.demo.DTO.UserSessionDTO;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        System.out.println(user);

        try {
            UserSessionDTO userSessionDTO = userServices.loginUser(user.getEmail(), user.getPassword());

            if (userSessionDTO == null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(userSessionDTO);
            }
            return ResponseEntity.ok(userSessionDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
