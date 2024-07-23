package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;


@Entity
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = true)
    private Long user_id;

    @Column(nullable = true)
    private String alias_name;
//    at-least one of name and id must not be null

    @Column(nullable = false)
    private Long campaign_id;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
//    private Enum<> mode_of_payment;
    private PaymentMode mode_of_payment;

    @Column(nullable = false)
    private LocalDate donation_date;

//    testing
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "campaign_id", insertable = false, updatable = false)
private Campaign campaign;
//@ManyToOne
//@JoinColumn(name = "campaign_id")
//@JsonIgnore
//private Campaign campaign;
//@ManyToOne(fetch = FetchType.LAZY)
//@JoinColumn(name = "campaign_id")
//private Campaign campaign;



//   getters and setters
    public long getId() {return id;}
    public void setId(long id) {this.id = id;}
    public Long getUser_id() {return user_id;}
    public void setUser_id(Long user_id) { this.user_id = user_id;}
    public Long getCampaign_id() { return campaign_id;    }
    public void setCampaign_id(Long campaign_id) { this.campaign_id = campaign_id; }
    public Double getAmount() { return amount; }
    public String getAlias_name() { return alias_name;}
    public void setAlias_name(String alias_name) { this.alias_name = alias_name;}
    public PaymentMode getMode_of_payment() {return mode_of_payment;}
    public void setMode_of_payment(PaymentMode mode_of_payment) { this.mode_of_payment = mode_of_payment; }
    public void setAmount(Double amount) { this.amount = amount; }
    public LocalDate getDonation_date() { return donation_date; }
    public void setDonation_date(LocalDate donation_date) { this.donation_date = donation_date; }

}
