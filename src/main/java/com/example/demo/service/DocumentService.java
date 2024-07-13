package com.example.demo.service;

import com.example.demo.DTO.DocumentSessionDTO;
import com.example.demo.DTO.DonationSessionDTO;
import com.example.demo.model.Document;
import com.example.demo.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {
    @Autowired
    private final DonationService donationService;
    @Autowired
    private DocumentRepository documentRepository;

    public DocumentService(DonationService donationService) { this.donationService = donationService; }
    public DocumentSessionDTO get_doc_by_userId(Long user_id) {
        try{
            Document dt = documentRepository.findbyUserId(user_id);
            if(dt == null){
                return new DocumentSessionDTO("No doc found");
            }
            return new DocumentSessionDTO(dt.getId(),dt.getDoc_type(),dt.getDoc_url(),dt.getUpload_date(),dt.getUpload_user(), dt.getStatus(),dt.getRemarks(),"");
        }
        catch(Exception e){
            System.err.println("Exception while finding doc: " + e.getMessage());
//            return new DonationSessionDTO("Error logging in");
            return new DocumentSessionDTO("Some error occured");
        }

    }

}
