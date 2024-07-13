package com.example.demo.controller;

import com.example.demo.DTO.DocumentSessionDTO;
import com.example.demo.DTO.DonationSessionDTO;
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
    @PostMapping("/get_doc_by_userId")
    public ResponseEntity<DocumentSessionDTO> getDocByUserId(@RequestBody DocumentSessionDTO dto) {
        try{
            Long user_id = dto.getUpload_user();
            DocumentSessionDTO documentSessionDTO = documentService.get_doc_by_userId(user_id);
            if(documentSessionDTO == null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(documentSessionDTO);
            }
            return ResponseEntity.ok(documentSessionDTO);

        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
