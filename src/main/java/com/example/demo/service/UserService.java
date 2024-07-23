package com.example.demo.service;

import com.example.demo.DTO.UserSessionDTO;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    private final UserRepository rep;

    public UserService(UserRepository rep) {
        this.rep = rep;
    }
//    public UserService(){};



    public UserSessionDTO loginUser(String email, String password) {
        try {
            User existingUser = rep.findUserByEmailUser(email, password);
            System.out.println("Existing user found: " + (existingUser != null));

            if (existingUser != null) {
                return new UserSessionDTO(existingUser.getId(), existingUser.getEmail(), existingUser.getPassword(), "");
            } else {
                return new UserSessionDTO("Invalid login details");
            }
        } catch (Exception e) {
            // Handle exceptions (e.g., database connection issues)
            System.err.println("Exception while logging in: " + e.getMessage());
            return new UserSessionDTO("Error logging in");
        }
    }

}
