package com.example.demo.controller;

import com.example.demo.DTO.DocumentSessionDTO;
import com.example.demo.model.Campaign;
import com.example.demo.model.Document;
import com.example.demo.service.CampaignService;
import com.example.demo.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;


@RestController
@RequestMapping("/api/documents")
public class DocumentController {
    private final DocumentService documentService;
    private final CampaignService campaignService;

    @Autowired
    public DocumentController(DocumentService documentService, CampaignService campaignService) {
        this.documentService = documentService;
        this.campaignService = campaignService;
    }
    @GetMapping("/get-all")
    public ResponseEntity<?> getAllDocuments() {
        try {
            Optional<List<Document>> optionalDTOs = documentService.findAll();
            if(optionalDTOs.isPresent() && !optionalDTOs.get().isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(optionalDTOs.get());
            }
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No document found ");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the document: " + e.getMessage());
        }

    }
    /**
     * Retrieves a document by its ID.
     *
     * @param id The ID of the document to retrieve
     * @return ResponseEntity containing the document or an error message
     */
    @GetMapping("/by-id")
    public ResponseEntity<?> getById(@RequestParam Long id) {
        if(id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id is mandatory");
        }
        try {
            // Attempt to find the document by ID
            return documentService.findById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            // Handle invalid ID input
            return ResponseEntity.badRequest().body("Invalid document ID: " + e.getMessage());
        } catch (Exception e) {
            // Handle any other unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the document: " + e.getMessage());
        }
    }
    @GetMapping("/get-by-userId")
    public ResponseEntity<?> getByUserId(@RequestParam Long userId) {
        if(userId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UserId is mandatory");
        }
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
        if(CampaignId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CampaignId is mandatory");
        }
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
    public ResponseEntity<?> addDoc(@RequestBody DocumentSessionDTO dto) {
        if(dto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("data is mandatory");
        }
        List<String> errors = validateDocument(dto);
        if(!errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
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
        if(dto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("data is mandatory");
        }

        List<String> errors = validateDocument(dto);
        if(!errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
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
        if(updates == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("data is mandatory");
        }
        List<String> errors = validateDocument(id);
        if(!errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        try {
            Optional<Document> patchedDocument = documentService.partialUpdateDocument(id, updates);
            return patchedDocument.map(document -> ResponseEntity.ok("Updated Document with id " + id + "\n" + document)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Document not patched with id: " + id));
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid document ID: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the document: " + e.getMessage());
        }
    }
    @DeleteMapping
    public ResponseEntity<?> deleteDoc(@RequestParam Long id) {
        List<String> errors = validateDocument(id);
        if(!errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
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
    private List<String> validateDocument(DocumentSessionDTO documentDto) {
        List<String> errors = new ArrayList<>();

        if (documentDto.getId() != null && documentDto.getId() <= 0) {
            errors.add("ID must be a positive number");
        }
        if (documentDto.getDoc_type() == null || documentDto.getDoc_type().trim().isEmpty()) {
            errors.add("Document type is required");
        }
        if (documentDto.getDoc_url() == null || documentDto.getDoc_url().trim().isEmpty()) {
            errors.add("Document URL is required");
        }
        if (documentDto.getCampaign_id() == null || documentDto.getCampaign_id() <= 0) {
            errors.add("Campaign ID must be a positive number");
        }
        if (documentDto.getUpload_date() == null || documentDto.getUpload_date().isAfter(LocalDate.now())) {
            errors.add("Upload date must be a valid date not in the future");
        }
        if (documentDto.getUpload_user() == null || documentDto.getUpload_user() <= 0) {
            errors.add("Upload user ID must be a positive number");
        }
        if (documentDto.getStatus() == null || documentDto.getStatus().trim().isEmpty()) {
            errors.add("Status is required");
        }
        // Remarks and errorMessage can be null or empty, so we don't validate them
//        validateDate(documentDto.getUpload_date(), "Upload_date",false,errors);
        return errors;
    }
    private void validateDate(Function<LocalDate,String> dateGetter, String fieldName, Boolean allowNull, List<String> errors) {
        String dateStr = dateGetter.apply(null);
        if (dateStr == null || dateStr.trim().isEmpty()) {
            if (!allowNull) {
                errors.add(fieldName + " is required");
            }
            return;
        }

        try {
            LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ISO_DATE);
            if (date.isAfter(LocalDate.now())) {
                errors.add(fieldName + " must not be in the future");
            }
        } catch (DateTimeParseException e) {
            errors.add("Invalid date format for " + fieldName + ". Use YYYY-MM-DD format");
        }
    }
    private List<String> validateDocument(Long id) {
        List<String> errors = new ArrayList<>();
        if (id == null || id <= 0) {
            errors.add("ID must be a positive number");
        }
        if(documentService.findById(id).isEmpty()) {
            errors.add("Document with id " + id + " not found");
        }
        return errors;
    }
}
