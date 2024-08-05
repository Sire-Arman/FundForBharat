package com.example.demo.service;

import com.example.demo.DTO.DocumentSessionDTO;
import com.example.demo.model.Document;
import com.example.demo.model.Status;
import com.example.demo.repository.DocumentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;
import java.util.Optional;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;
    public DocumentService(DocumentRepository documentRepository) { this.documentRepository = documentRepository; }


    @Transactional
    public Optional<List<Document>> findAll() {
        List<Document> documents = documentRepository.findAll();
        return Optional.ofNullable(documents);
    }


    @Transactional
    public Optional<Document> findById(Long id) {
        return documentRepository.findById(id);
    }


    @Transactional
    public Optional<DocumentSessionDTO> findByUserId(Long user_id) {
        try {
            Optional<Document> documentOptional = documentRepository.findByUploadUser(user_id);
            if (documentOptional.isEmpty()) {
                return Optional.of(new DocumentSessionDTO("No doc found"));
            }
            Document dt = documentOptional.get();
            return Optional.of(new DocumentSessionDTO(dt.getId(), dt.getDoc_type(), dt.getDoc_url(),dt.getCampaign_id(),dt.getUpload_date(), dt.getUpload_user(), dt.getStatus(), dt.getRemarks(), ""));
        } catch (Exception e) {
            System.err.println("Exception while finding doc: " + e.getMessage());
            return Optional.of(new DocumentSessionDTO("Some error occurred"));
        }
    }


//    @Transactional
//    public Document addDocument(DocumentSessionDTO dto){
//        try{
//            Document doc = new Document();
//            doc.setCampaign_id(dto.getCampaign_id());
//            doc.setDoc_type(dto.getDoc_type());
//            doc.setDoc_url(dto.getDoc_url());
//            doc.setUpload_date(dto.getUpload_date());
//            doc.setUpload_user(dto.getUpload_user());
//            doc.setStatus(dto.getStatus());
//            doc.setRemarks(dto.getRemarks());
//            return documentRepository.save(doc);
//        }catch (Exception e){
//            System.err.println("Exception while adding doc: " + e.getMessage());
//            return null;
//        }
//    }


//    @Transactional
//    public Document updateDocument(Long id , DocumentSessionDTO dto){
//       try{
//           Document dt = documentRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Document not found with id: " + id));
//           System.out.println("\n\n Insided updateDocument \n");
//           if(dt != null){
//               dt.setDoc_type(dto.getDoc_type());
//               dt.setDoc_url(dto.getDoc_url());
//               dt.setRemarks(dto.getRemarks());
//               dt.setCampaign_id(dto.getCampaign_id());
//               dt.setUpload_user(dto.getUpload_user());
//               dt.setUpload_date(dto.getUpload_date());
////               if(dto.getStatus() != null){
////                   try {
////                       Status status = Status.valueOf(dto.getStatus().toString().toUpperCase());
////                       dt.setStatus(status);
////                   } catch (IllegalArgumentException e) {
////                       // Handle invalid status string
////                       throw new IllegalArgumentException("Invalid status value: " + dto.getStatus());
////                   }
////               }
//               dt.setStatus(dto.getStatus());
//               documentRepository.save(dt);
//               return dt;
//           }
//           return null;
//
//       }
//       catch (Exception e){
//           System.err.println("Exception while updating doc: " + e.getMessage());
//           return null;
//       }
//    }


//    @Transactional
//    public Optional<Document> partialUpdateDocument(Long id, Map<String, Object> updates) {
//        Optional<Document> documentOpt = documentRepository.findById(id);
//        if (documentOpt.isPresent()) {
//            Document document = documentOpt.get();
//
//            if (updates.containsKey("Doc_type")) {
//                document.setDoc_type((String) updates.get("Doc_type"));
//            }
//            if (updates.containsKey("Doc_url")) {
//                document.setDoc_url((String) updates.get("Doc_url"));
//            }
//            if (updates.containsKey("campaign_id")) {
//                document.setCampaign_id(Long.valueOf(updates.get("campaign_id").toString()));
//            }
//            if (updates.containsKey("upload_date")) {
//                document.setUpload_date(LocalDate.parse(updates.get("upload_date").toString()));
//            }
//            if (updates.containsKey("upload_user")) {
//                document.setUpload_user(Long.valueOf(updates.get("upload_user").toString()));
//            }
//            if (updates.containsKey("status")) {
//                try {
//                    Status status = Status.valueOf(updates.get("status").toString().toUpperCase());
//                    document.setStatus(status);
//                } catch (IllegalArgumentException e) {
//                    // Handle invalid status string
//                    throw new IllegalArgumentException("Invalid status value: " + updates.get("status"));
//                }
//            }
////            if(updates.containsKey("status")){
////                document.setStatus(updates.get("status").toString());
////            }
//            if (updates.containsKey("remarks")) {
//                document.setRemarks(updates.get("isPublic").toString());
//            }
//
//            return Optional.of(documentRepository.save(document));
//        } else {
//            return Optional.empty();
//        }
//    }
//
//
//    @Transactional
//    public boolean deleteDocument(Long id) {
//            if(documentRepository.existsById(id)){
//                documentRepository.deleteById(id);
//                return true;
//            }
//            return false;
//    }


    @Transactional
    public List<Document> get_doc_by_campaignId(Long campaign_id) {
        try{
            return documentRepository.findByCampaignId(campaign_id);
        }
        catch(Exception e){
            System.err.println("Exception while finding doc: " + e.getMessage());
            return null;
        }

    }

}
