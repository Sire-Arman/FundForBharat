package com.example.demo.repository;

import com.example.demo.DTO.DonationSessionDTO;
import com.example.demo.model.Donation;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation , Long>  {
    @Query("SELECT d.id, u.fullName, u.email, d.alias_name, d.amount, d.user_id, d.campaign_id, d.mode_of_payment, d.donation_date " +
            "FROM Donation d LEFT JOIN User u ON d.user_id = u.id WHERE d.user_id = :userId")
    List<Object[]> findDonationsByUserId(@Param("userId") Long userId);


    @Query("Select u from Donation u")
    List<Donation> findAllDonations();

    @Query("Select u from Donation u where u.campaign_id = :CampaignId")
    List<Donation> findDonationsByCampaignId(Long CampaignId);
}
