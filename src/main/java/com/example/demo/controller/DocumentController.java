package com.example.demo.controller;

import com.example.demo.DTO.DocumentSessionDTO;
import com.example.demo.DTO.DonationSessionDTO;
import com.example.demo.model.Document;
import com.example.demo.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.util.List;


@RestController
@RequestMapping("/api/documents")
public class DocumentController {
    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }
    @GetMapping("/get-by-Id")
    public ResponseEntity<Document> getById(@RequestParam("id") Long id) {
        try{
            Document dt = documentService.get_doc_by_Id(id);
            if(dt != null) {
                return ResponseEntity.ok(dt);
            }
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("/get-by-userId")
    public ResponseEntity<DocumentSessionDTO> getDocByUserId(@RequestBody DocumentSessionDTO dto) {
        try{
            Long user_id = dto.getUpload_user();
            DocumentSessionDTO documentSessionDTO = documentService.get_doc_by_userId(user_id);
            if(documentSessionDTO == null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            }
            return ResponseEntity.ok(documentSessionDTO);

        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("/get-by-campaignId")
    public ResponseEntity<List<Document>> getDocByCampaignId(@RequestBody Long CampaignId){
        try{
            List<Document> documents = documentService.get_doc_by_campaignId(CampaignId);
            if(documents == null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            }
            return ResponseEntity.ok(documents);

        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PutMapping("/update-doc")
    public ResponseEntity<Document> updateDoc(Long id, DocumentSessionDTO dto) {
        try{

            Document dt = documentService.updateDocument(id,dto);
            if(dt != null) {
                return ResponseEntity.ok(dt);
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);


        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
