package com.example.demo.DTO;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSessionDTO {
    @JsonIgnore
    private Long userId;

    private String email;
    private String username;
    private String fullname;
    private String password;
    private String errorMessage;
    private String token;
    private List<String> roles;
    // Constructors
    public UserSessionDTO(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

