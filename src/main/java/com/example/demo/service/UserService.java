package com.example.demo.service;

import com.example.demo.DTO.UserSessionDTO;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }


    @Transactional
    public UserSessionDTO register(UserSessionDTO request) {
        try {
            User user = new User();
            user.setFullName(request.getFullname());
            user.setEmail(request.getEmail());
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));

            Role role = new Role();

            for (String value : request.getRoles()) {
                    String x = value.toLowerCase();
                if (x.equals("user")) {
                    role.setRoleUser(true);
                }
                if (x.equals("campaignadmin")) {
                    role.setRoleCampaignAdmin(true);
                }
                if (x.equals("documentadmin")) {
                    role.setRoleDocumentAdmin(true);
                }
                if (x.equals("paymentadmin")) {
                    role.setRolePaymentAdmin(true);
                }
                if (x.equals("superadmin")) {
                    role.setRoleSuperAdmin(true);
                }
            }

            role.setUser(user);
            user.setRole(role);
            user = userRepository.save(user);
            roleRepository.save(role);

            String token = jwtService.generateToken(user);

            return new UserSessionDTO(user.getId(),user.getEmail(),user.getUsername(),user.getFullName(),
                    null,"Registered Successfully !! Please Login !!",token,role.getAllRoles());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new UserSessionDTO("Error Registering !!");
        }
    }


    @Transactional
    public UserSessionDTO login(UserSessionDTO request) {
        try {
            // Authenticate the user
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            // Fetch user details
            User user = userRepository.findByEmail(request.getEmail());
            Role role = user.getRole();

            // Generate JWT token
            String token = jwtService.generateToken(user);

            // Return DTO with user and token
            return new UserSessionDTO(user.getId(), user.getEmail(), user.getUsername(), user.getFullName(), null,"",token,role.getAllRoles());
        } catch (Exception e) {
            // Handle authentication failure
            System.out.println(e.getCause() + "======="+ e.getMessage());
            return new UserSessionDTO("Invalid Credentials!!");
        }
    }
    @Transactional
    public Boolean UserExists(String email) {
        User user = userRepository.findByEmail(email);
        return user != null;
    }

}
