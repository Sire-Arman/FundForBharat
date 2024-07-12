package com.example.demo.service;

import com.example.demo.DTO.DonationSessionDTO;
import com.example.demo.model.Donation;
import com.example.demo.repository.DonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;


@Service
public class DonationService {
    @Autowired
    private final DonationRepository donationRepository;
    public DonationService(DonationRepository donationRepository){ this.donationRepository = donationRepository; }

    public DonationSessionDTO getDonationsByUserId(Long userId){
        try{
            DonationSessionDTO donationSessionDTO = new DonationSessionDTO();
            List<Donation> donations = donationRepository.findDonationsByUserId(userId);
            if (!donations.isEmpty()) {
                return new DonationSessionDTO(donations.getFirst().getId(), donations.get(0).getUser_id(),
                        donations.get(0).getCampaign_id(), donations.get(0).getAmount(), donations.get(0).getDonation_date(), "");
            } else {
                return new DonationSessionDTO("No donations found");
            }
        }catch (Exception e){
            // Handle exceptions (e.g., database connection issues)
            System.err.println("Exception while logging in: " + e.getMessage());
//            return new DonationSessionDTO("Error logging in");
            return new DonationSessionDTO("Some error occured");
        }
    }
    public List<DonationSessionDTO> getAllDonations(){
        try {
            List<Donation> donation = donationRepository.findAllDonations();
            System.out.println("Existing donation found: " + (!donation.isEmpty()));
            List<DonationSessionDTO> donationSessions = new ArrayList<>();
            if (!donation.isEmpty()) {
                for (Donation value : donation) {
                    donationSessions.add(new DonationSessionDTO(value.getId(), value.getUser_id(),
                            value.getCampaign_id(), value.getAmount(), value.getDonation_date(), ""));
                }
                return donationSessions;
            }
            else {
                return donationSessions;
            }
        } catch (Exception e) {
            // Handle exceptions (e.g., database connection issues)
            System.err.println("Exception while logging in: " + e.getMessage());
//            return new DonationSessionDTO("Error logging in");
            return null;
        }

//        return donationRepository.findAllDonations();
    }
    public DonationSessionDTO createDonation(  DonationRepository donationRepository){
        DonationSessionDTO donationSessionDTO = new DonationSessionDTO();
        System.out.println(donationRepository);
        return donationSessionDTO;
    }
}
