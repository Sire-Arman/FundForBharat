package com.example.demo.service;
import com.example.demo.DTO.CampaignSessionDTO;
import com.example.demo.model.Campaign;
import com.example.demo.controller.CampaignController;
import com.example.demo.repository.CampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CampaignService {
    @Autowired
    private CampaignRepository campaignRepository;
    public CampaignService(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }
    public CampaignSessionDTO createCampaign(Campaign campaign) {
        String title = campaign.getTitle();
        String description = campaign.getDescription();
        Long user_id = campaign.getUser_id();

        CampaignSessionDTO createdCampaign = new CampaignSessionDTO(user_id,title,description);
//        Campaign campaign = new Campaign();
//        campaign.setId(campaignSessionDTO.id);
        return createdCampaign;
    }
    public List<Campaign> getAllCampaigns() {
//        CampaignSessionDTO campaignSessionDTO = new CampaignSessionDTO();
        List<Campaign> Allcampaigns = campaignRepository.findAllCampaigns();
        return Allcampaigns;
    }

}
