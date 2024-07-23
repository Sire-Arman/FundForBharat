package com.example.demo.service;
import com.example.demo.DTO.CampaignSessionDTO;
import com.example.demo.DTO.CampaignWithDonationsDTO;
import com.example.demo.DTO.DonationSessionDTO;
import com.example.demo.model.Campaign;
import com.example.demo.model.Donation;
import com.example.demo.repository.CampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CampaignService {
    @Autowired
    private CampaignRepository campaignRepository;
    public CampaignService(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }
    public Campaign createCampaign(CampaignSessionDTO campaignDTO) {
        Campaign campaign = new Campaign();
        Long userId = campaignDTO.getUserId();
        String title = campaignDTO.getTitle();
        String description = campaignDTO.getDescription();
        Boolean toBeShown = campaignDTO.getToBeShown();
        Double target_amount = campaignDTO.getTarget_amount();
        LocalDate startDate = campaignDTO.getStartDate();
        LocalDate endDate = campaignDTO.getEndDate();



//        campaign.setId(id);
        campaign.setUser_id(userId);
        campaign.setTitle(title);
        campaign.setDescription(description);
        campaign.setTarget_amount(target_amount);
        campaign.setToBeShown(toBeShown);
        campaign.setStart_date(startDate);
        campaign.setEnd_date(endDate);

         return campaignRepository.save(campaign);
//return campaignRepository.addCam
//        Campaign createdCampaign = campaignRepository.addCampaign(id,userId,title,description,startDate,endDate,target_amount);
////        Campaign campaign = new Campaign();
////        campaign.setId(campaignSessionDTO.id);
//        return createdCampaign;
    }
    public Optional<Campaign> getCampaignById(Long id) {
        return campaignRepository.findCampaignById(id);
    }
    public Optional<Campaign> partialUpdateCampaign(Long id, Map<String, Object> updates) {
        Optional<Campaign> campaignOpt = campaignRepository.findCampaignById(id);
        if (campaignOpt.isPresent()) {
            Campaign campaign = campaignOpt.get();

            if (updates.containsKey("description")) {
                campaign.setDescription((String) updates.get("description"));
            }
            if (updates.containsKey("title")) {
                campaign.setTitle((String) updates.get("title"));
            }
            if (updates.containsKey("toBeShown")) {
                campaign.setToBeShown(Boolean.valueOf(updates.get("toBeShown").toString()));
            }
            if (updates.containsKey("target_amount")) {
                campaign.setTarget_amount(Double.valueOf(updates.get("target_amount").toString()));
            }
            if (updates.containsKey("start_date")) {
                campaign.setStart_date(LocalDate.parse(updates.get("start_date").toString()));
            }
            if (updates.containsKey("end_date")) {
                campaign.setEnd_date(LocalDate.parse(updates.get("end_date").toString()));
            }
            if (updates.containsKey("user_id")) {
                campaign.setUser_id(Long.valueOf(updates.get("user_id").toString()));
            }

            return Optional.of(campaignRepository.save(campaign));
        } else {
            return Optional.empty();
        }
    }
    public Optional<Campaign> updateCampaign(Long id, CampaignSessionDTO campaignDTO) {
        return campaignRepository.findById(id)
                .map(campaign -> {
                    campaign.setTitle(campaignDTO.getTitle());
                    campaign.setDescription(campaignDTO.getDescription());
                    campaign.setTarget_amount(campaignDTO.getTarget_amount());
                    campaign.setStart_date(campaignDTO.getStartDate());
                    campaign.setEnd_date(campaignDTO.getEndDate());
                    campaign.setUser_id(campaignDTO.getUserId());
                    return campaignRepository.save(campaign);
                });
    }
    public boolean deleteCampaign(Long id) {
        if (campaignRepository.existsById(id)) {
            campaignRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public List<Campaign> getAllCampaigns() {
//        CampaignSessionDTO campaignSessionDTO = new CampaignSessionDTO();
        return campaignRepository.findAllCampaigns();
    }
//    public List<Campaign> getAllWithDonations(){
//        return campaignRepository.findAllWithDonations();
//    }
public boolean addDonationAmount(Long id, Double amount) {
    Optional<Campaign> optionalCampaign = campaignRepository.findById(id);

    if (optionalCampaign.isPresent()) {
        Campaign campaign = optionalCampaign.get();
        Double currentAmount = campaign.getAmount_raised();
        Double newAmount = currentAmount + amount;

        campaign.setAmount_raised(newAmount);
        campaignRepository.save(campaign);
        return true;  // Operation successful
    } else {
        System.err.println("Campaign with id " + id + " not found");
        return false;  // Operation failed
    }
}
    public List<Campaign> getTopFundedCampaigns(){
        return campaignRepository.findTop10ByOrderByAmountRaisedDesc();
    }

    public List<Campaign> getHomePageCampaigns(){
        return campaignRepository.findHomePageCampaigns();
    }

    public List<CampaignWithDonationsDTO> getAllCampaignsWithDonations() {
        List<Campaign> campaigns = campaignRepository.findAllWithDonations();
        return campaigns.stream()
                .map(this::convertToCampaignWithDonationsDTO)
                .collect(Collectors.toList());
    }

    private CampaignWithDonationsDTO convertToCampaignWithDonationsDTO(Campaign campaign) {
        CampaignWithDonationsDTO dto = new CampaignWithDonationsDTO();
        dto.setId(campaign.getId());
        dto.setUserId(campaign.getUser_id());
        dto.setTitle(campaign.getTitle());
        dto.setDescription(campaign.getDescription());

        dto.setTargetAmount(campaign.getTarget_amount());
        dto.setAmountRaised(campaign.getAmount_raised());
        dto.setStartDate(campaign.getStart_date());
        dto.setEndDate(campaign.getEnd_date());
        dto.setDonations(campaign.getDonations().stream()
                .map(this::convertToDonationDTO)
                .collect(Collectors.toList()));
        return dto;
    }

    private DonationSessionDTO convertToDonationDTO(Donation donation) {
        DonationSessionDTO dto = new DonationSessionDTO();
        dto.setId(donation.getId());
        dto.setUser_id(donation.getUser_id());
        dto.setCampaign_id(donation.getCampaign_id());
        dto.setAlias_name(donation.getAlias_name());
        dto.setAmount(donation.getAmount());
        dto.setModeOfPayment(donation.getMode_of_payment());
        dto.setDonation_date(donation.getDonation_date());
        return dto;
    }

}
