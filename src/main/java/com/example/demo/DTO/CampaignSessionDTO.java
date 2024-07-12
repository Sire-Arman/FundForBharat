package com.example.demo.DTO;

public class CampaignSessionDTO {
    private Long id;
    private Long userId;
    private String title;
    private String description;
    private String errorMessage;

    public CampaignSessionDTO() {};

    public CampaignSessionDTO(Long userId, String title, String description, String errorMessage) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.errorMessage = errorMessage;
    }
    public CampaignSessionDTO(Long userId, String title, String description) {
        this.userId = userId;
        this.title = title;
        this.description = description;
    }

    public CampaignSessionDTO(String title , String description){
        this.title = title;
        this.description=description;
    }

    public CampaignSessionDTO(String errorMessage) {
        this.errorMessage = errorMessage;
    }
//    getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
