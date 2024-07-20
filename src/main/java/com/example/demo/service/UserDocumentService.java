package com.example.demo.service;

import com.example.demo.DTO.UserDocumentSessionDTO;
import com.example.demo.model.UserDocument;
import com.example.demo.repository.DocumentRepository;
import com.example.demo.repository.UserDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserDocumentService {

    @Autowired
    private final UserDocumentRepository userDocumentRepository;

    public UserDocumentService( UserDocumentRepository userDocumentRepository) {
        this.userDocumentRepository=userDocumentRepository;
    }
    public UserDocumentSessionDTO getUserDocumentById(Long id) {
        try{
            Optional<UserDocument> userDocument = userDocumentRepository.findById(id);
            return userDocument.map(document -> new UserDocumentSessionDTO(document.getId(), document.getUser_id(),
                    document.getAlias_name(), document.getDoc_type()
                    , document.getDoc_url(), "Doc Found")).orElseGet(() -> new UserDocumentSessionDTO("Document Not found"));
        }
        catch(Exception e){
            // Handle exceptions (e.g., database connection issues)
            System.err.println("Exception while searching document: " + e.getMessage());
            return new UserDocumentSessionDTO("Error found in User documents");

        }
    }
    public UserDocument get_doc_by_userId(Long userId){
        return userDocumentRepository.findByUserId(userId);
    }
    public UserDocumentSessionDTO updateDoc(Long id, UserDocumentSessionDTO dto) {
        try {
            Optional<UserDocument> userDocumentOptional = userDocumentRepository.findById(id);

            return userDocumentOptional.map(doc -> {
                    doc.setDoc_type(dto.getDoc_type());
                    doc.setDoc_url(dto.getDoc_url());
                    doc.setUser_id(dto.getUserId());
                    doc.setAlias_name(dto.getAlias_name());
                UserDocument savedDoc = userDocumentRepository.save(doc);
                return convertToDTO(savedDoc);
            }).orElseThrow(() -> new RuntimeException("Document not found"));
        }
        catch (Exception e) {
            System.err.println("Exception while updating document: " + e.getMessage());
            return new UserDocumentSessionDTO("Error found in User documents");
        }
    }
    public UserDocumentSessionDTO patchDoc(Long id, UserDocumentSessionDTO dto) {
        try {
            Optional<UserDocument> userDocumentOptional = userDocumentRepository.findById(id);

            return userDocumentOptional.map(doc -> {
                if (dto.getDoc_type() != null) {
                    doc.setDoc_type(dto.getDoc_type());
                }
                if (dto.getDoc_url() != null) {
                    doc.setDoc_url(dto.getDoc_url());
                }
                if (dto.getUserId() != null) {
                    doc.setUser_id(dto.getUserId());
                }
                if (dto.getAlias_name() != null) {
                    doc.setAlias_name(dto.getAlias_name());
                }
                UserDocument savedDoc = userDocumentRepository.save(doc);
                return convertToDTO(savedDoc);
            }).orElseThrow(() -> new RuntimeException("Document not found"));
        }
        catch (Exception e) {
            System.err.println("Exception while updating document: " + e.getMessage());
            return new UserDocumentSessionDTO("Error found in User documents");
        }
    }
    public UserDocumentSessionDTO deleteDoc(Long id) {
        try {
            Optional<UserDocument> userDocumentOptional = userDocumentRepository.findById(id);

            if (userDocumentOptional.isPresent()) {
                UserDocument userDocument = userDocumentOptional.get();
                UserDocumentSessionDTO dto = convertToDTO(userDocument);
                userDocumentRepository.deleteById(id);
                return dto;
            } else {
                throw new RuntimeException("Document not found with id: " + id);
            }
        } catch (Exception e) {
            System.err.println("Exception while deleting document: " + e.getMessage());
            return new UserDocumentSessionDTO("Error deleting User document: " + e.getMessage());
        }
    }
    private UserDocumentSessionDTO convertToDTO(UserDocument doc) {
        UserDocumentSessionDTO dto = new UserDocumentSessionDTO();
        // Set fields from doc to dto
        dto.setDoc_type(doc.getDoc_type());
        dto.setDoc_url(doc.getDoc_url());
        dto.setUserId(doc.getUser_id());
        dto.setAlias_name(doc.getAlias_name());
        return dto;
    }

}
