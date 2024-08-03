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
    @Query("select u from Document u where u.upload_user = :userId")
    Optional<Document> findByUploadUser(Long userId);

}
