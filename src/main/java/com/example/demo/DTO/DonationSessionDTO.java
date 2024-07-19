package com.example.demo.DTO;


import com.example.demo.model.PaymentMode;

import java.time.LocalDate;

public class DonationSessionDTO {
    private Long id;
    private Long UserId;
    private Long CampaignId;
    private String alias_name;
    private Double amount;
    private LocalDate DonationDate;
    private PaymentMode modeOfPayment;
    private String errorMessage;
//   add custom messages or remarks
//    two donate buttons : one for specific campaigns and one for generic purpose
    public DonationSessionDTO() {};
    public DonationSessionDTO(Long id, Long UserId, Long CampaignId, String alias_name, Double amount, LocalDate DonationDate, PaymentMode modeOfPayment, String errorMessage) {
        this.id = id;
        this.UserId = UserId;
        this.CampaignId = CampaignId;
        this.alias_name = alias_name;
        this.modeOfPayment = modeOfPayment;
        this.amount = amount;
        this.DonationDate = DonationDate;
        this.errorMessage = errorMessage;
    }
    public DonationSessionDTO(Long id, Long UserId, Long CampaignId, Double amount, LocalDate DonationDate) {
        this.id = id;
        this.UserId = UserId;
        this.CampaignId = CampaignId;
        this.amount = amount;
        this.DonationDate = DonationDate;
    }
    public DonationSessionDTO(Long id, Long UserId, Long CampaignId, Double amount) {
        this.id = id;
        this.UserId = UserId;
        this.CampaignId = CampaignId;
        this.amount = amount;
    }
    public DonationSessionDTO(String errorMessage) {
        this.errorMessage = errorMessage;
    }
//    getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return UserId; }
    public void setUserId(Long UserId) { this.UserId = UserId; }
    public String getAlias_name() { return alias_name; }
    public void setAlias_name(String alias_name) { this.alias_name = alias_name; }
    public Long getCampaignId() { return CampaignId; }
    public void setCampaignId(Long CampaignId) { this.CampaignId = CampaignId; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    public LocalDate getDonationDate() { return DonationDate; }
    public void setDonationDate(LocalDate DonationDate) { this.DonationDate = DonationDate; }
    public PaymentMode getModeOfPayment() { return modeOfPayment; }
    public void setModeOfPayment(PaymentMode modeOfPayment) { this.modeOfPayment = modeOfPayment; }
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }


}
