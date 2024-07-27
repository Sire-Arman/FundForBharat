package com.example.demo.controller;

import com.example.demo.DTO.UserSessionDTO;
import com.example.demo.Exceptions.UserAlreadyExistsException;
import com.example.demo.service.UserService;
import com.example.demo.Exceptions.BadRequestException;
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
        try {
            if (user == null || user.getEmail()==null || user.getUsername() == null || user.getPassword() == null) {
                throw new BadRequestException("Username, Email or password cannot be null");
            }
            else if (!userServices.UserExists(user.getEmail())){
                throw new BadRequestException("User does not exist");
            }
            else return ResponseEntity.ok(userServices.login(user));
        } catch (BadRequestException ex) {
            throw new BadRequestException("Invalid login credentials");
        }catch (Exception e){
            throw new RuntimeException("Something went wrong");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> signup(@RequestBody UserSessionDTO user) {
        try{
            if(user == null || user.getEmail()==null ||user.getUsername() == null || user.getPassword() == null) {
                throw new BadRequestException("Username or password cannot be null");
            }
            else if(userServices.UserExists(user.getEmail())){
                throw new UserAlreadyExistsException("A user with this email already exists");
            }
            else if(user.getRoles() == null){
                throw new BadRequestException("Roles cannot be null");
            }
            else return ResponseEntity.ok(userServices.register(user));
        }
        catch(BadRequestException | UserAlreadyExistsException ex){
            throw ex;
        }
        catch (Exception e){
            throw new RuntimeException("Something went wrong");
        }
    }

}
