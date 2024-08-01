package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    @Column(nullable = false)
//    private Status status;
    private String status;
    @Column
    private String remarks;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "campaign_id", insertable = false, updatable = false)
//    private Campaign campaign;

//    testing

}
