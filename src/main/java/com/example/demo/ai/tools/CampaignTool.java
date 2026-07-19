package com.example.demo.ai.tools;

import com.example.demo.model.Campaign;
import com.example.demo.repository.CampaignRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CampaignTool {

    private final CampaignRepository campaignRepository;

    @Autowired
    public CampaignTool(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }

    @Tool(description = "Retrieves the top 10 most funded campaigns currently on the platform.")
    public List<Campaign> getTopCampaigns() {
        return campaignRepository.findTopByOrderByAmountRaisedDesc(PageRequest.of(0, 10));
    }

    @Tool(description = "Retrieves the detailed information of a specific campaign by its unique ID.")
    public Optional<Campaign> getCampaignById(Long id) {
        return campaignRepository.findCampaignById(id);
    }

    @Tool(description = "Retrieves a list of active campaigns that are configured to be shown on the home page.")
    public List<com.example.demo.DTO.CampaignWithDonationsDTO> getActiveCampaigns() {
        return campaignRepository.findHomePageCampaigns();
    }
}
