package com.example.demo.DTO;

import com.example.demo.model.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentSessionDTO {
    private Long id;
//    private Long userId;
    private String Doc_type;
    private String Doc_url;
    private Long campaign_id;
    private LocalDate upload_date;
    private Long upload_user;
    private Status status;
//    private String status;
    private String remarks;
    private String errorMessage;


    public DocumentSessionDTO(String errorMessage){
        this.errorMessage = errorMessage;
    }
}
