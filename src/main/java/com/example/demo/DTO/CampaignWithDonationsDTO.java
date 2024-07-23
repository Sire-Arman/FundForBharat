package com.example.demo.DTO;


import com.example.demo.model.PaymentMode;
import com.example.demo.repository.CampaignRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CampaignWithDonationsDTO {
    private Long id;
    private Long userId;
    private String title;
    private String description;
    private Double targetAmount;
    private Double amountRaised;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<DonationSessionDTO> donations;

    // Constructor, getters, and setters
}

