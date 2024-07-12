package com.example.demo.controller;

import com.example.demo.DTO.CampaignSessionDTO;
import com.example.demo.model.Campaign;
import com.example.demo.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/campaigns")
public class CampaignController {
    private final CampaignService campaignServices;
    @Autowired
    public CampaignController(CampaignService campaignServices) {
        this.campaignServices = campaignServices;
    }
    @GetMapping("/get-all-campaigns")
    public ResponseEntity<List<Campaign>> getAllCampaigns(@RequestBody CampaignSessionDTO campaign) {
        System.out.println(campaign);

        try {
            List<Campaign> AllCampaigns = campaignServices.getAllCampaigns();

            if (AllCampaigns.size() == 0) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(AllCampaigns);
            }
            return ResponseEntity.ok(AllCampaigns);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
