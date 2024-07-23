package com.example.demo.repository;

import com.example.demo.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.print.Doc;
import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    @Query("Select u from Document u")
    List<Document> findAll();

    @Query("Select u from Document u where u.upload_user = :user_id")
    Optional<Document> findByUserId(Long user_id);

    @Query("select u from Document u where u.campaign_id = :campaignId")
    List<Document> findByCampaignId(Long campaignId);


//    // Find documents by file type
//    List<Document> findByDocType(String doc);
//
//    // Find documents by file name containing a keyword (case-insensitive)
//    List<Document> findByFileNameContainingIgnoreCase(String keyword);
//
//    // Find documents by campaign id and file type
//    List<Document> findByCampaignIdAndFileType(Long campaignId, String fileType);

}
