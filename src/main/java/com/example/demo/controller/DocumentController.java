package com.example.demo.controller;

import com.example.demo.DTO.DocumentSessionDTO;
import com.example.demo.DTO.DonationSessionDTO;
import com.example.demo.model.Campaign;
import com.example.demo.model.Document;
import com.example.demo.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//import javax.print.Doc;
import javax.print.Doc;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/documents")
public class DocumentController {
    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }
    @GetMapping("/get-all")
    public ResponseEntity<?> getAllDocuments() {
        try {
            Optional<List<Document>> optionalDTOs = documentService.findAll();
            if(optionalDTOs.isPresent() && !optionalDTOs.get().isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(optionalDTOs.get());
            }
            return ResponseEntity.ok("No document found ");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the document: " + e.getMessage());
        }

    }
    @GetMapping
    public ResponseEntity<?> getById(@RequestParam Long id) {
        try {
            Optional<Document> documentOptional = documentService.findById(id);
            if(documentOptional.isPresent()) {
                Document document = documentOptional.get();
                return ResponseEntity.status(HttpStatus.OK).body(document);
            }
            return ResponseEntity.ok("No document found with id " + id);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid document ID: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the document: " + e.getMessage());
        }
    }
    @GetMapping("/get-by-userId")
    public ResponseEntity<?> getByUserId(@RequestParam Long userId) {
        try {
            Optional<DocumentSessionDTO> documentSessionDTO = documentService.findByUserId(userId);
            if (documentSessionDTO.isEmpty() || documentSessionDTO.get().getErrorMessage() != null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No document found for the given user ID");
            }
            return ResponseEntity.ok(documentSessionDTO.get());
        } catch (Exception e) {
            System.err.println("Exception while getting document: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while retrieving the document");
        }
    }

    @GetMapping("/get-by-campaignId")
    public ResponseEntity<?> getDocByCampaignId(@RequestParam Long CampaignId){
        try{
            List<Document> documents = documentService.get_doc_by_campaignId(CampaignId);
            if(documents.isEmpty()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("No documents found for Campaign id : "+ CampaignId);
            }
            return ResponseEntity.ok(documents);

        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("/add-doc")
    public ResponseEntity<Document> addDoc(@RequestBody DocumentSessionDTO dto) {
        try {
            Document dt = documentService.addDocument(dto);
            if(dt != null) {
                return ResponseEntity.ok(dt);
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PutMapping
    public ResponseEntity<?> updateDoc(@RequestParam Long id,@RequestBody DocumentSessionDTO dto) {
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
    @PatchMapping()
    public ResponseEntity<?> patchDoc(@RequestParam Long id, @RequestBody Map<String, Object> updates) {
        try {
            Optional<Document> patchedDocument = documentService.partialUpdateDocument(id, updates);
            if (patchedDocument.isPresent()) {
                return ResponseEntity.ok("Updated Document with id "+ id + "\n" +patchedDocument.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Document not patched with id: " + id);
            }
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid document ID: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the document: " + e.getMessage());
        }
    }
    @DeleteMapping
    public ResponseEntity<?> deleteDoc(@RequestParam Long id) {
        try {
            Optional<Document> documentOptional = documentService.findById(id);
            if (documentOptional.isPresent()) {
                Document document = documentOptional.get();
                boolean deleted = documentService.deleteDocument(id);
                if (deleted) {
                    return ResponseEntity.ok(document);
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Failed to delete document with id: " + id);
                }
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid document ID: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the document: " + e.getMessage());
        }
    }
}
