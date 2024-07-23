package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Campaign {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long user_id;

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @Column(nullable = false)
    private Double target_amount;

    @Column
    private Boolean toBeShown = false;

    @Column
    private Double amount_raised = 0.0;

    @Column
    private LocalDate start_date;

    @Column
    private LocalDate end_date;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;






//    testing
@OneToMany(mappedBy = "campaign", fetch = FetchType.LAZY)
private List<Donation> donations = new ArrayList<>();
//    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Document> documents = new ArrayList<>();
//
//    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Donation> donations = new ArrayList<>();
//@OneToMany(mappedBy = "campaign", fetch = FetchType.LAZY)
//@JsonIgnore
//private List<Donation> donations;










//    Getters and Setters

//    public void setId(Long id) {
//        this.id = id;
//    }
//    public Long getUser_id() {
//        return user_id;
//    }
//    public void setUser_id(Long user_id) {
//        this.user_id = user_id;
//    }
//    public String getTitle() {
//        return title;
//    }
//    public void setTitle(String title) {
//        this.title = title;
//    }
//    public String getDescription() {
//        return description;
//    }
//    public void setDescription(String description) {
//        this.description = description;
//    }
//    public Double getTarget_amount() {
//        return target_amount;
//    }
//    public void setTarget_amount(Double target_amount) {
//        this.target_amount = target_amount;
//    }
//    public Double getAmount_raised() {
//        return amount_raised;
//    }
//    public void setAmount_raised(Double amount_raised) {
//        this.amount_raised = amount_raised;
//    }
//
//    public Boolean getToBeShown() {
//        return toBeShown;
//    }
//    public void setToBeShown(Boolean toBeShown) { this.toBeShown = toBeShown; }
//
//    public LocalDate getStart_date() {
//        return start_date;
//    }
//    public void setStart_date(LocalDate start_date) {
//        this.start_date = start_date;
//    }
//    public LocalDate getEnd_date() {
//        return end_date;
//    }
//    public void setEnd_date(LocalDate end_date) {
//        this.end_date = end_date;
//    }
//    public LocalDateTime getCreatedAt() {
//        return createdAt;
//    }
//    public void setCreatedAt(LocalDateTime createdAt) {
//        this.createdAt = createdAt;
//    }
//    public LocalDateTime getUpdatedAt() {
//        return updatedAt;
//    }
//    public void setUpdatedAt(LocalDateTime updatedAt) {
//        this.updatedAt = updatedAt;
//    }


    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Helper methods to manage bidirectional relationships
//    public void addDocument(Document document) {
//        documents.add(document);
//        document.setCampaign(this);
//    }
//
//    public void removeDocument(Document document) {
//        documents.remove(document);
//        document.setCampaign(null);
//    }
//
//    public void addDonation(Donation donation) {
//        donations.add(donation);
////        donation.setCampaign(this);
//        this.amount_raised += donation.getAmount();
//    }
//
//    public void removeDonation(Donation donation) {
//        donations.remove(donation);
////        donation.setCampaign(null);
//        this.amount_raised -= donation.getAmount();
//    }

}
