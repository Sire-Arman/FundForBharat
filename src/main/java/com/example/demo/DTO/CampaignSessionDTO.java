package com.example.demo.DTO;

import com.example.demo.repository.CampaignRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CampaignSessionDTO {
    //    getters and setters
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


    public CampaignSessionDTO(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
