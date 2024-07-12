package com.example.demo.controller;

import com.example.demo.DTO.DocumentSessionDTO;
import com.example.demo.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/documents")
public class DocumentController {
    private final DocumentService documentService;
    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }
}
