package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable =false)
    private String Doc_type;

    @Column(nullable = false)
    private String Doc_url;

    @Column(nullable = false)
    private Long campaign_id;

    @Column(nullable = false)
    private LocalDate upload_date;

    @Column(nullable = false)
//    stores userid
    private Long upload_user;

    @Column
    private String status;

    @Column
    private String remarks;

//    testing
//@ManyToOne(fetch = FetchType.LAZY)
//@JoinColumn(name = "id")
//private Campaign campaign;


//    Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDoc_type() { return Doc_type; }
    public void setDoc_type(String Doc_type) { this.Doc_type = Doc_type; }
    public String getDoc_url() { return Doc_url; }
    public void setDoc_url(String Doc_url) { this.Doc_url = Doc_url; }
    public Long getCampaign_id() { return campaign_id; }
    public void setCampaign_id(Long campaign_id) { this.campaign_id = campaign_id; }
    public LocalDate getUpload_date() { return upload_date; }
    public void setUpload_date(LocalDate upload_date) { this.upload_date = upload_date; }
    public Long getUpload_user() { return upload_user; }
    public void setUpload_user(Long upload_user) { this.upload_user = upload_user; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

//    public void setCampaign(Campaign campaign) {
//        this.campaign = campaign;
//    }
//
//    public Campaign getCampaign() {
//        return campaign;
//    }

}
