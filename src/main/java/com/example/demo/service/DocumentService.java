package com.example.demo.service;

import com.example.demo.DTO.DocumentSessionDTO;
import com.example.demo.DTO.DonationSessionDTO;
import com.example.demo.model.Document;
import com.example.demo.repository.DocumentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.List;

@Service
public class DocumentService {
    @Autowired
    private final DonationService donationService;
    @Autowired
    private DocumentRepository documentRepository;

    public DocumentService(DonationService donationService) { this.donationService = donationService; }
    public Document get_doc_by_Id(Long id){
        return documentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Document not found with id: " + id));
    }
    public DocumentSessionDTO get_doc_by_userId(Long user_id) {
        try{
            Document dt = documentRepository.findbyUserId(user_id);
            if(dt == null){
                return new DocumentSessionDTO("No doc found");
            }
            return new DocumentSessionDTO(dt.getId(),dt.getDoc_type(),dt.getDoc_url(),dt.getUpload_date(),dt.getCampaign_id(),dt.getUpload_user(), dt.getStatus(),dt.getRemarks(),"");
        }
        catch(Exception e){
            System.err.println("Exception while finding doc: " + e.getMessage());
//            return new DonationSessionDTO("Error logging in");
            return new DocumentSessionDTO("Some error occured");
        }

    }
    public Document addDocument(DocumentSessionDTO dto){
        try{
            Document doc = new Document();
            doc.setCampaign_id(dto.getCampaign_id());
            doc.setDoc_type(dto.getDoc_type());
            doc.setDoc_url(dto.getDoc_url());
            doc.setUpload_date(dto.getUpload_date());
            doc.setUpload_user(dto.getUpload_user());
            doc.setStatus(dto.getStatus());
            doc.setRemarks(dto.getRemarks());
            return documentRepository.save(doc);
        }catch (Exception e){
            System.err.println("Exception while adding doc: " + e.getMessage());
            return null;
        }
    }
    public Document updateDocument(Long id , DocumentSessionDTO dto){
       try{
           Document dt = documentRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Document not found with id: " + id));
           if(dt != null){
               dt.setDoc_type(dto.getDoc_type());
               dt.setDoc_url(dto.getDoc_url());
               dt.setRemarks(dto.getRemarks());
               dt.setCampaign_id(dto.getCampaign_id());
               dt.setUpload_user(dto.getUpload_user());
               dt.setUpload_date(dto.getUpload_date());
               dt.setStatus(dto.getStatus());
               documentRepository.save(dt);
               return dt;
           }
           return null;

       }catch (Exception e){
           System.err.println("Exception while updating doc: " + e.getMessage());
           return null;
       }
    }
    public void deleteDocument(Long id ){
        try{
            documentRepository.deleteById(id);
        }catch (Exception e){
            System.err.println("Exception while updating doc: " + e.getMessage());
        }
    }
    public List<Document> get_doc_by_campaignId(Long campaign_id) {
        try{
            return documentRepository.findByCampaignId(campaign_id);
        }
        catch(Exception e){
            System.err.println("Exception while finding doc: " + e.getMessage());
//            return new DonationSessionDTO("Error logging in");
            return null;
        }

    }

}
