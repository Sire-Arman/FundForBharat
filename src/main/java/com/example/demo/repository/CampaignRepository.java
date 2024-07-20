package com.example.demo.repository;

import com.example.demo.DTO.CampaignSessionDTO;
import com.example.demo.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    @Query("SELECT u FROM Campaign u")
    List<Campaign> findAllCampaigns();

    @Query("SELECT u FROM Campaign u ORDER BY u.amount_raised DESC LIMIT 10")
    List<Campaign> findTop10ByOrderByAmountRaisedDesc();


    @Query("SELECT u from Campaign u where u.id = :Id")
    Campaign findCampaignById(Long Id) ;

    @Query("Select u from Campaign u where u.toBeShown=true")
    List<Campaign> findHomePageCampaigns();

//    @Query("SELECT DISTINCT c FROM Campaign c LEFT JOIN FETCH c.donations")
//    List<Campaign> findAllWithDonations();
//    @Query("Insert into Campaign () Values () ")
//    Campaign addCampaign(Campaign campaign);
}
