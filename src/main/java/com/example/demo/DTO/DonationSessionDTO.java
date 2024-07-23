package com.example.demo.DTO;


import com.example.demo.model.PaymentMode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DonationSessionDTO {
    //    getters and setters
    @JsonIgnore
    private Long id;


    private Long user_id;
    private Long campaign_id;
    private String alias_name;
    private Double amount;
    private LocalDate donation_date;
    private PaymentMode modeOfPayment;
    private String errorMessage;
//   add custom messages or remarks
//    two donate buttons : one for specific campaigns and one for generic purpose
//    public DonationSessionDTO() {};
//    public DonationSessionDTO(Long id, Long UserId, Long CampaignId, String alias_name, Double amount, LocalDate DonationDate, PaymentMode modeOfPayment, String errorMessage) {
//        this.id = id;
//        this.UserId = UserId;
//        this.CampaignId = CampaignId;
//        this.alias_name = alias_name;
//        this.modeOfPayment = modeOfPayment;
//        this.amount = amount;
//        this.DonationDate = DonationDate;
//        this.errorMessage = errorMessage;
//    }
//    public DonationSessionDTO(Long id, Long UserId, Long CampaignId, Double amount, LocalDate DonationDate) {
//        this.id = id;
//        this.UserId = UserId;
//        this.CampaignId = CampaignId;
//        this.amount = amount;
//        this.DonationDate = DonationDate;
//    }
//    public DonationSessionDTO(Long id, Long UserId, Long CampaignId, Double amount) {
//        this.id = id;
//        this.UserId = UserId;
//        this.CampaignId = CampaignId;
//        this.amount = amount;
//    }
    public DonationSessionDTO(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
