package com.example.demo.DTO;


public class UserSessionDTO {
    private Long userId;
    private String email;
    private String password;
    private String errorMessage;
    // Constructors

    public UserSessionDTO() {
        // Default constructor
    }
    public UserSessionDTO(Long userId, String email, String password, String errorMessage) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.errorMessage = errorMessage;
    }

    public UserSessionDTO(String email , String password){
        this.email = email;
        this.password=password;
    }

    public UserSessionDTO(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

