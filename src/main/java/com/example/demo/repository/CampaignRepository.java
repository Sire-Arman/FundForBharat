package com.example.demo.repository;

import com.example.demo.DTO.CampaignSessionDTO;
import com.example.demo.DTO.CampaignWithDonationsDTO;
import com.example.demo.DTO.DocumentSessionDTO;
import com.example.demo.DTO.DonationSessionDTO;
import com.example.demo.model.Campaign;
import com.example.demo.model.Document;
import com.example.demo.model.Donation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.print.Doc;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

//gecampaignwith don and doc
//getcampaigns with don and doc by id

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {


    @Query("SELECT u FROM Campaign u ORDER BY u.amount_raised DESC")
    List<Campaign> findTopByOrderByAmountRaisedDesc(Pageable pageable);


    @Query("SELECT u from Campaign u where u.id = :Id")
    Optional<Campaign> findCampaignById(Long Id) ;


    @Query("Select u from Campaign u where u.toBeShown=true")
    List<CampaignWithDonationsDTO> findHomePageCampaigns();


    @Query("SELECT c.id, c.user_id, c.title, c.description, c.start_date, c.end_date, " +
            "c.target_amount, c.amount_raised, c.createdAt, c.updatedAt, c.toBeShown, " +
            "d.id, d.user_id, d.campaign_id, d.alias_name, d.amount, d.mode_of_payment, d.donation_date, " +
            "doc.id, doc.Doc_type, doc.Doc_url, doc.campaign_id, doc.remarks, doc.status, doc.upload_date, doc.upload_user " +
            "FROM Campaign c " +
            "LEFT JOIN Donation d on d.campaign_id = c.id " +
            "LEFT JOIN Document doc on doc.campaign_id = c.id "+
            "Where c.toBeShown = true")
    List<Object[]> findAllCampaignsWithDonationsAndDocuments();

    @Query("SELECT c.id, c.user_id, c.title, c.description, c.start_date, c.end_date, " +
            "c.target_amount, c.amount_raised, c.createdAt, c.updatedAt, c.toBeShown, " +
            "d.id, d.user_id, d.campaign_id, d.alias_name, d.amount, d.mode_of_payment, d.donation_date, " +
            "doc.id, doc.Doc_type, doc.Doc_url, doc.campaign_id, doc.remarks, doc.status, doc.upload_date, doc.upload_user " +
            "FROM Campaign c " +
            "LEFT JOIN Donation d on d.campaign_id = c.id " +
            "LEFT JOIN Document doc on doc.campaign_id = c.id where c.id = :id")
    Object[] findCampaignsWithDonationsAndDocuments(Long id);

}
