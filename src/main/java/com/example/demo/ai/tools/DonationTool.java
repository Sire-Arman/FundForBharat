package com.example.demo.ai.tools;

import com.example.demo.model.Donation;
import com.example.demo.repository.DonationRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DonationTool {

    private final DonationRepository donationRepository;

    @Autowired
    public DonationTool(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    @Tool(description = "Retrieves all donations associated with a specific campaign ID.")
    public List<Donation> getDonationsForCampaign(Long campaignId) {
        return donationRepository.findDonationsByCampaignId(campaignId);
    }

    @Tool(description = "Retrieves the donation history for a specific user by their user ID.")
    public List<Object[]> getDonationsByUser(Long userId) {
        return donationRepository.findDonationsByUserId(userId);
    }

    @Tool(description = "Calculates the total amount raised for a specific campaign ID based on its donations.")
    public double getTotalAmountRaisedForCampaign(Long campaignId) {
        List<Donation> donations = donationRepository.findDonationsByCampaignId(campaignId);
        return donations.stream().mapToDouble(Donation::getAmount).sum();
    }
}
