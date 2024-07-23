package com.example.demo.repository;

import com.example.demo.DTO.DonationSessionDTO;
import com.example.demo.model.Donation;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation , Long>  {
    @Query("SELECT u FROM Donation u WHERE u.user_id = :UserId")
    List<Donation> findDonationsByUserId(Long UserId);

    @Query("Select u from Donation u")
    List<Donation> findAllDonations();

    @Query("Select u from Donation u where u.campaign_id = :CampaignId")
    List<Donation> findDonationsByCampaignId(Long CampaignId);
}
