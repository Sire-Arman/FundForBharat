package com.example.demo.DTO;


import com.example.demo.model.PaymentMode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DonationSessionDTO {
    //    getters and setters
    @JsonIgnore
    private Long id;


    private Long user_id;
    private Long campaign_id;
    private String alias_name;
    private Double amount;
    private LocalDate donation_date;
    private PaymentMode modeOfPayment;
    private String errorMessage;
//   add custom messages or remarks
//    two donate buttons : one for specific campaigns and one for generic purpose
    public DonationSessionDTO(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
