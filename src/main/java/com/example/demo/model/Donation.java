package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

}
