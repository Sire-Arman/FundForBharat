package com.example.demo.DTO;

import com.example.demo.repository.CampaignRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;

public class CampaignSessionDTO {
    @JsonIgnore
    private Long id;


    private Long userId;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean toBeShown;
    private Double target_amount;
    private String errorMessage;

    public CampaignSessionDTO() {};

//    public CampaignSessionDTO(Long userId, String title, String description, String errorMessage) {
//        this.userId = userId;
//        this.title = title;
//        this.description = description;
//        this.errorMessage = errorMessage;
//    }
    public CampaignSessionDTO(Long id, Long userId, String title, String description,Boolean toBeShown, LocalDate startDate, LocalDate endDate, Double target_amount) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.toBeShown = toBeShown;
        this.startDate = startDate;
        this.endDate = endDate;
        this.target_amount = target_amount;
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
    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Boolean getToBeShown() {
        return toBeShown;
    }
    public void setToBeShown(Boolean toBeShown) { this.toBeShown = toBeShown; }

    public Double getTarget_amount() {
        return target_amount;
    }
    public void setTarget_amount(Double target_amount) {
        this.target_amount = target_amount;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
