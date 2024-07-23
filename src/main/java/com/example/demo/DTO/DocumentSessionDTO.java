package com.example.demo.DTO;

import java.time.LocalDate;

public class DocumentSessionDTO {
    private Long id;
//    private Long userId;
    private String Doc_type;
    private String Doc_url;
    private Long campaign_id;
    private LocalDate upload_date;
    private Long upload_user;
    private String status;
    private String remarks;
    private String errorMessage;

    public DocumentSessionDTO() {};
//    public DocumentSessionDTO(Long Id);
    public DocumentSessionDTO(Long id, String doc_type, String doc_url, LocalDate upload_date,Long campaign_id, Long upload_user, String status, String remarks, String errorMessage ){
        this.id = id;
        this.Doc_type = doc_type;
        this.Doc_url = doc_url;
        this.upload_date = upload_date;
        this.campaign_id = campaign_id;
        this.upload_user = upload_user;
        this.status = status;
        this.remarks = remarks;
        this.errorMessage = errorMessage;
    }
    public DocumentSessionDTO(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
//    public Long getUserId() { return userId; }
//    public void setUserId(Long userId) { this.userId = userId; }
    public Long getCampaign_id() { return campaign_id; }
    public void setCampaign_id(Long campaign_id) { this.campaign_id = campaign_id; }
    public String getDoc_type() { return Doc_type; }
    public void setDoc_type(String Doc_type) { this.Doc_type = Doc_type; }
    public String getDoc_url() { return Doc_url; }
    public void setDoc_url(String Doc_url) { this.Doc_url = Doc_url; }
    public LocalDate getUpload_date() { return upload_date; }
    public void setUpload_date(LocalDate upload_date) { this.upload_date = upload_date; }
    public Long getUpload_user() { return upload_user; }
    public void setUpload_user(Long upload_user) { this.upload_user = upload_user; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}
