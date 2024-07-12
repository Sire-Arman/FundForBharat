package com.example.demo.model;

import jakarta.persistence.*;
import org.springframework.web.bind.annotation.CookieValue;

import java.time.LocalDateTime;
@Entity
public class UserDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private Long user_id;

    @Column(nullable = true)
    private String alias_name;

    @Column(nullable = false)
    private String Doc_type;

    @Column(nullable = false)
    private String Doc_url;

    @Column(nullable = false)
    private String status;

    @Column
    private String remarks;

    @Column
    private LocalDateTime created_at;
    @Column
    private LocalDateTime updated_at;

//    getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUser_id() { return user_id; }
    public void setUser_id(Long user_id) { this.user_id = user_id; }
    public String getAlias_name() { return alias_name; }
    public void setAlias_name(String alias_name) { this.alias_name = alias_name; }
    public String getDoc_type() { return Doc_type; }
    public void setDoc_type(String Doc_type) { this.Doc_type = Doc_type; }
    public String getDoc_url() { return Doc_url; }
    public void setDoc_url(String Doc_url) { this.Doc_url = Doc_url; }
    public String getStatus() { return status; }
    public String getRemarks() {return remarks;}
    public void setRemarks(String remarks) { this.remarks = remarks; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreated_at() { return created_at; }
    public void setCreated_at(LocalDateTime created_at) { this.created_at = created_at; }
    public LocalDateTime getUpdated_at() { return updated_at; }
    public void setUpdated_at(LocalDateTime updated_at) { this.updated_at = updated_at; }


}
