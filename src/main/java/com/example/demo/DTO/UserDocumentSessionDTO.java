package com.example.demo.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDocumentSessionDTO {
    @JsonIgnore
    private Long id;

    private Long userId;
    private String alias_name;
    private String doc_type;
    private String doc_url;
    private String errorMessage;
    public UserDocumentSessionDTO() {}
    public UserDocumentSessionDTO(Long id, Long userId, String alias_name, String doc_type, String doc_url) {
        this.id = id;
        this.userId = userId;
        this.alias_name = alias_name;
        this.doc_type = doc_type;
        this.doc_url = doc_url;
    }
    public UserDocumentSessionDTO(Long id, Long userId, String alias_name, String doc_type, String doc_url, String errorMessage) {
        this.id = id;
        this.userId = userId;
        this.alias_name = alias_name;
        this.doc_type = doc_type;
        this.doc_url = doc_url;
        this.errorMessage = errorMessage;
    }
    public UserDocumentSessionDTO(String errorMessage) {
        this.errorMessage = errorMessage;
    }
//    getter and setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getAlias_name() { return alias_name;}
    public void setAlias_name(String alias_name) { this.alias_name = alias_name; }
    public String getDoc_type() { return doc_type; }
    public void setDoc_type(String doc_type) { this.doc_type = doc_type; }
    public String getDoc_url() { return doc_url; }
    public void setDoc_url(String doc_url) { this.doc_url = doc_url; }
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }



}
