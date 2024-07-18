package com.example.demo.service;
import com.example.demo.DTO.CampaignSessionDTO;
import com.example.demo.model.Campaign;
import com.example.demo.controller.CampaignController;
import com.example.demo.repository.CampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class CampaignService {
    @Autowired
    private CampaignRepository campaignRepository;
    public CampaignService(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }
    public Campaign createCampaign(CampaignSessionDTO campaignDTO) {
        Campaign campaign = new Campaign();
        Long id = campaignDTO.getId();
        Long userId = campaignDTO.getUserId();
        String title = campaignDTO.getTitle();
        String description = campaignDTO.getDescription();
        Double target_amount = campaignDTO.getTarget_amount();
        LocalDate startDate = campaignDTO.getStartDate();
        LocalDate endDate = campaignDTO.getEndDate();



//        campaign.setId(id);
        campaign.setTitle(title);
        campaign.setDescription(description);
        campaign.setTarget_amount(target_amount);
        campaign.setStart_date(startDate);
        campaign.setEnd_date(endDate);
        campaign.setUser_id(userId);

         return campaignRepository.save(campaign);
//return campaignRepository.addCam
//        Campaign createdCampaign = campaignRepository.addCampaign(id,userId,title,description,startDate,endDate,target_amount);
////        Campaign campaign = new Campaign();
////        campaign.setId(campaignSessionDTO.id);
//        return createdCampaign;
    }
    public Campaign getCampaignById(Long id) {
        return campaignRepository.findCampaignById(id);
    }
    public Campaign partialUpdateCampaign(Long id, Map<String, Object> updates) {
        Campaign campaign = campaignRepository.findCampaignById(id);
        campaign.setDescription(updates.get("description").toString());
        campaign.setTitle(updates.get("title").toString());
        campaign.setTarget_amount(Double.valueOf(updates.get("target_amount").toString()));
        campaign.setStart_date(LocalDate.parse(updates.get("start_date").toString()));
        campaign.setEnd_date(LocalDate.parse(updates.get("end_date").toString()));
        campaign.setUser_id(Long.valueOf(updates.get("user_id").toString()));
        return campaignRepository.save(campaign);
    }
    public Campaign updateCampaign(Long id, CampaignSessionDTO campaignDTO) {
        Campaign campaign = getCampaignById(id);
        campaign.setTitle(campaignDTO.getTitle());
        campaign.setDescription(campaignDTO.getDescription());
        campaign.setTarget_amount(campaignDTO.getTarget_amount());
        campaign.setStart_date(campaignDTO.getStartDate());
        campaign.setEnd_date(campaignDTO.getEndDate());
        campaign.setUser_id(campaignDTO.getUserId());
        return campaignRepository.save(campaign);
    }
    public Campaign deleteCampaign(Long id) {
        Campaign campaign = getCampaignById(id);
        campaignRepository.delete(campaign);
        return campaign;
    }
    public List<Campaign> getAllCampaigns() {
//        CampaignSessionDTO campaignSessionDTO = new CampaignSessionDTO();
        List<Campaign> Allcampaigns = campaignRepository.findAllCampaigns();
        return Allcampaigns;
    }
    public List<Campaign> getTopFundedCampaigns(){
        return campaignRepository.findTop10ByOrderByAmountRaisedDesc();
    }

}
