package com.example.demo.controller;

import com.example.demo.DTO.UserDocumentSessionDTO;
import com.example.demo.service.UserDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user_documents")
public class UserDocumentController {
    private final UserDocumentService userDocumentService;

    @Autowired
    public UserDocumentController(UserDocumentService userDocumentService) {
        this.userDocumentService = userDocumentService;
    }
    @PostMapping("/get_doc_by_id")
    public ResponseEntity<UserDocumentSessionDTO> getDocById(@RequestBody UserDocumentSessionDTO userDocumentSessionDTO) {
        try{
            Long id = userDocumentSessionDTO.getId();
            UserDocumentSessionDTO usdoc = userDocumentService.getUserDocumentById(id);
            if (usdoc == null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(usdoc);
            }
            return ResponseEntity.status(HttpStatus.OK).body(usdoc);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
