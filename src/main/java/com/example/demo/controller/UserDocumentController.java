package com.example.demo.controller;

import com.example.demo.DTO.DocumentSessionDTO;
import com.example.demo.DTO.UserDocumentSessionDTO;
import com.example.demo.model.UserDocument;
import com.example.demo.service.UserDocumentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user_documents")
public class UserDocumentController {
    private final UserDocumentService userDocumentService;

    @Autowired
    public UserDocumentController(UserDocumentService userDocumentService) {
        this.userDocumentService = userDocumentService;
    }
    @GetMapping
    public ResponseEntity<?> getDocById(@RequestParam Long id) {
        if(id.equals(null)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id is required");
        }
        try {
            UserDocumentSessionDTO usdoc = userDocumentService.getUserDocumentById(id);
            if (usdoc == null) {
                return ResponseEntity.ok("User Document not found");
            }
            return ResponseEntity.ok(usdoc);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
//            System.err.println("Error fetching user document"+e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }
    @PutMapping
    public ResponseEntity<?> updateDocById(@RequestParam Long id, @RequestBody UserDocumentSessionDTO dto) {
        List<String> errors = validateDTO(dto);
        if (!errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        try {
            UserDocumentSessionDTO updatedDoc = userDocumentService.updateDoc(id, dto);
            if (updatedDoc == null) {
                return ResponseEntity.ok("User Document not found");
            }
            return ResponseEntity.ok(updatedDoc);
        } catch (Exception e) {
            System.err.println("Error updating user document: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new UserDocumentSessionDTO("Error updating user document"));
        }
    }
    @PatchMapping
    public ResponseEntity<?> patchDocById(@RequestParam Long id, @RequestBody UserDocumentSessionDTO dto) {
        List<String> errors = validateDTO(dto);
        if (!errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        try {
            UserDocumentSessionDTO updatedDoc = userDocumentService.patchDoc(id, dto);
            if (updatedDoc == null) {
                return ResponseEntity.ok("Cannot update user document as there is no document with this id");
            }
            return ResponseEntity.ok(updatedDoc);
        } catch (Exception e) {
//            System.err.println("Error updating user document: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new UserDocumentSessionDTO("Error updating user document"));
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteDocById(@RequestParam Long id) {
        if(id.equals(null)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id is required");
        }
        try{
            UserDocumentSessionDTO deletedDoc = userDocumentService.deleteDoc(id);
            if(deletedDoc == null) {
                return ResponseEntity.ok("Cannot delete user document as there is no document with this id");
            }
            return ResponseEntity.ok(deletedDoc);
        }catch (Exception e) {
//            System.err.println("Error deleting user document: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/get-by-userId")
    public ResponseEntity<?> getDocByUserId(@RequestParam Long userId) {
        if(userId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("userId is required");
        }
        try{
            UserDocument userdoc = userDocumentService.get_doc_by_userId(userId);
            if(userdoc == null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot find user document with this id");
            }
            return ResponseEntity.ok(userdoc);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    private List<String> validateDTO(UserDocumentSessionDTO documentDto) {
        List<String> errors = new ArrayList<>();

        if (documentDto.getId() != null && documentDto.getId() <= 0) {
            errors.add("ID must be a positive number");
        }
        if (documentDto.getUserId() == null || documentDto.getUserId() <= 0) {
            errors.add("User ID is required and must be a positive number");
        }
        if (documentDto.getAlias_name() == null || documentDto.getAlias_name().trim().isEmpty()) {
            errors.add("Alias name is required");
        }
        if (documentDto.getDoc_type() == null || documentDto.getDoc_type().trim().isEmpty()) {
            errors.add("Document type is required");
        }
        if (documentDto.getDoc_url() == null || documentDto.getDoc_url().trim().isEmpty()) {
            errors.add("Document URL is required");
        }
        // errorMessage can be null or empty, so we don't validate it

        return errors;
    }
}
