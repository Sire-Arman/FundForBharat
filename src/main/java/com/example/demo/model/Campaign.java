package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalDate;


@Entity
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long user_id;

    @Column(nullable = false)
    private String title;

    @Column()
    private String description;

    @Column(nullable = false)
    private Double target_amount;

    @Column ()
    private Boolean ToBeShown = false;
//    tobeshown at home page
    @Column()
    private Double amount_raised;
//    call


    private LocalDate start_date;
    private LocalDate end_date;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

//    Getters and Setters

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getUser_id() {
        return user_id;
    }
    public void setUser_id(Long user_id) {
        this.user_id = user_id;
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
    public Double getTarget_amount() {
        return target_amount;
    }
    public void setTarget_amount(Double target_amount) {
        this.target_amount = target_amount;
    }
    public Double getAmount_raised() {
        return amount_raised;
    }
    public void setAmount_raised(Double amount_raised) {
        this.amount_raised = amount_raised;
    }

    public Boolean getToBeShown() {
        return ToBeShown;
    }
    public void setToBeShown(Boolean toBeShown) { this.ToBeShown = toBeShown; }

    public LocalDate getStart_date() {
        return start_date;
    }
    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }
    public LocalDate getEnd_date() {
        return end_date;
    }
    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}
