package com.example.demo.DTO;

import com.example.demo.model.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Stack;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserDocumentSessionDTO {
    @JsonIgnore
    private Long id;

    private Long userId;
    private String alias_name;
    private String doc_type;
    private String doc_url;
    private Status status;
    private String errorMessage;
    public UserDocumentSessionDTO(String errorMessage) {
        this.errorMessage = errorMessage;
    }
//    getter and setter



}
