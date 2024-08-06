package com.example.demo.service;

import com.example.demo.DTO.DonationSessionDTO;
import com.example.demo.model.Campaign;
import com.example.demo.model.Donation;
import com.example.demo.model.PaymentMode;
import com.example.demo.repository.DonationRepository;
import com.example.demo.repository.CampaignRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;
import org.springframework.transaction.annotation.Transactional;


@Service
public class DonationService {
    @Autowired
    private final DonationRepository donationRepository;
    private final CampaignRepository campaignRepository;
    public DonationService(DonationRepository donationRepository, CampaignRepository campaignRepository){ this.donationRepository = donationRepository;
        this.campaignRepository = campaignRepository;
    }


//rectify its modeof payment


    @Transactional
    public Optional<List<DonationSessionDTO>> getDonationsByUserId(Long userId) {
        try {
            List<Object[]> donations = donationRepository.findDonationsByUserId(userId);

            List<DonationSessionDTO> donationList = new ArrayList<>();

            for (Object[] donationData : donations) {

                DonationSessionDTO dto = new DonationSessionDTO();
                dto.setUser_id((Long) donationData[5]);
                dto.setId((Long) donationData[0]);
                dto.setDonation_date((LocalDate) donationData[8]);
                dto.setAmount((Double) donationData[4]);
                dto.setAlias_name((String) donationData[3]);
                dto.setEmail((String) donationData[2]);
                dto.setCampaign_id((Long) donationData[6]);
                dto.setFullname((String) donationData[1]);
                donationList.add(dto);
            }
//            for (Object[] donationData : donations) {
//                // Print out the values for debugging
//                for (Object value : donationData) {
//                    System.out.print(value + "====");
//                }
//                System.out.println();
//
//                // Create a new DTO instance
//                DonationSessionDTO dto = new DonationSessionDTO();
//
//                // Map fields from the Object[] to the DTO
//                try {
//                    dto.setId((Long) donationData[0]); // ID
//                    dto.setFullname((String) donationData[1]); // Full Name
//                    dto.setEmail((String) donationData[2]); // Email
//                    dto.setAlias_name((String) donationData[3]); // Alias Name (can be null)
//                    dto.setAmount((Double) donationData[4]); // Amount
//                    dto.setUser_id((Long) donationData[5]); // User ID
//                    dto.setCampaign_id((Long) donationData[6]); // Campaign ID
//
//                    // Handle the PaymentMode enum
//                    String modeOfPaymentStr = (String) donationData[7];
//                    PaymentMode modeOfPayment;
//                    try {
//                        modeOfPayment = PaymentMode.valueOf(modeOfPaymentStr);
//                    } catch (IllegalArgumentException e) {
//                        System.err.println("Invalid PaymentMode value: " + modeOfPaymentStr);
//                        modeOfPayment = null; // or handle accordingly
//                    }
//                    dto.setModeOfPayment(modeOfPayment);
//
//                    // Handle LocalDate conversion
//                    LocalDate donationDate = (LocalDate) donationData[8];
//                    dto.setDonation_date(donationDate);
//
//                } catch (ClassCastException e) {
//                    System.err.println("Error casting data: " + e.getMessage());
//                }
//
//                // Add the DTO to the list
//                donationList.add(dto);
//            }

            return Optional.of(donationList);
        } catch (Exception e) {
            System.err.println("Exception while fetching donations: " + e.getMessage());
            return Optional.empty();
        }
    }


    @Transactional
    public Optional<List<Donation>> getAllDonations() {
        try {
            List<Donation> optionalDonations = donationRepository.findAllDonations();

            return Optional.of(optionalDonations);
        } catch (Exception e) {
            return Optional.empty();
        }
    }


    @Transactional
    public Optional<Donation> getById(Long id) {
        try{
            return donationRepository.findById(id);
        }
        catch (Exception e){
            return Optional.empty();
        }
    }


    @Transactional
    public Optional<List<Donation>> getDonationsByCampaignId(Long campaignId) {
        try {
            List<Donation> donations = donationRepository.findDonationsByCampaignId(campaignId);
            return Optional.of(donations);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Transactional
    public Donation createDonation(DonationSessionDTO dto) {
        // Validate input
        if (dto == null) {
            throw new IllegalArgumentException("Donation data cannot be null");
        }
        System.out.println(dto);
        if (dto.getAmount() <= 0) {
            throw new IllegalArgumentException("Donation amount must be positive");
        }

        if (dto.getCampaign_id() == null) {

            throw new IllegalArgumentException("Campaign ID is required");
        }

        // Check if campaign exists
        if (!campaignRepository.existsById(dto.getCampaign_id())) {
            throw new EntityNotFoundException("Campaign with id " + dto.getCampaign_id() + " not found");
        }

        Donation donation = new Donation();
        donation.setUser_id(dto.getUser_id());
        donation.setCampaign_id(dto.getCampaign_id());
        donation.setMode_of_payment(dto.getModeOfPayment());
        donation.setAlias_name(dto.getAlias_name());
        donation.setAmount(dto.getAmount());
        donation.setDonation_date(dto.getDonation_date() != null ? dto.getDonation_date() : LocalDate.now());

        Donation savedDonation = donationRepository.save(donation);

        // Update campaign amount
        Campaign campaign = campaignRepository.findById(dto.getCampaign_id())
                .orElseThrow(() -> new EntityNotFoundException("Campaign not found"));
        campaign.setAmount_raised(campaign.getAmount_raised() + dto.getAmount());
        campaignRepository.save(campaign);

        return savedDonation;
    }


//    @Transactional
//    public Donation updateDonation(Long id, DonationSessionDTO dto) {
//        return donationRepository.findById(id)
//                .map(donation -> {
//                    donation.setUser_id(dto.getUser_id());
//                    donation.setCampaign_id(dto.getCampaign_id());
//                    donation.setMode_of_payment(dto.getModeOfPayment());
//                    donation.setAlias_name(dto.getAlias_name());
//                    donation.setAmount(dto.getAmount());
//                    donation.setDonation_date(dto.getDonation_date());
//                    return donationRepository.save(donation);
//                })
//                .orElseThrow(() -> new EntityNotFoundException("Donation not found with id: " + id));
//    }

//
//    @Transactional
//    public Boolean deleteById(Long id,Boolean AmountToBeReduced) {
//        try {
//            Optional<Donation> optionalDonation = donationRepository.findById(id);
//
//            if (optionalDonation.isPresent()) {
//                Donation donation = optionalDonation.get();
//                if(AmountToBeReduced){
//                    Long campaignId = donation.getCampaign_id();
//                    Campaign campaign = campaignRepository.findById(campaignId)
//                            .orElseThrow(() -> new EntityNotFoundException("Campaign not found"));
//
//                    Double newAmount = campaign.getAmount_raised() - donation.getAmount();
//                    campaign.setAmount_raised(newAmount);
//                    campaignRepository.save(campaign);
//
//                }
//                // First, update the campaign's amount_raised
//
//                // Then, delete the donation
//                donationRepository.deleteById(id);
//                return true;
//            } else {
//                return false;
//            }
//        } catch (Exception e) {
//            return false;
//        }
//    }

}
