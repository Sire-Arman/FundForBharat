package com.example.demo.service;

import com.example.demo.DTO.UserDocumentSessionDTO;
import com.example.demo.model.UserDocument;
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
            UserDocumentSessionDTO userDocumentSessionDTO = new UserDocumentSessionDTO();
            Optional<UserDocument> userDocument = userDocumentRepository.findById(id);
            if (userDocument.isPresent()) {
                return new UserDocumentSessionDTO(userDocument.get().getId(), userDocument.get().getUser_id(),
                        userDocument.get().getAlias_name(), userDocument.get().getDoc_type()
                        , userDocument.get().getDoc_url(), "Doc Found");
            } else {
                return new UserDocumentSessionDTO("Document Not found");
            }
        }
        catch(Exception e){
            // Handle exceptions (e.g., database connection issues)
            System.err.println("Exception while searching document: " + e.getMessage());
            return new UserDocumentSessionDTO("Error found in Userdocuments");

        }
    }
    public UserDocument get_doc_by_userId(Long userId){
        return userDocumentRepository.findByUserId(userId);
    }

}
