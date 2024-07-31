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
//    @JsonIgnore
//    @OneToMany(mappedBy = "campaign", fetch = FetchType.LAZY)
//    private List<Donation> donations = new ArrayList<>();
//    @JsonIgnore
//    @OneToMany(mappedBy = "campaign", fetch = FetchType.LAZY)
//    private List<Document> documents = new ArrayList<>();


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
