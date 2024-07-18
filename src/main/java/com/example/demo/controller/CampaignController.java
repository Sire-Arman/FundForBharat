package com.example.demo.controller;

import com.example.demo.DTO.CampaignSessionDTO;
import com.example.demo.model.Campaign;
import com.example.demo.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/campaigns")
public class CampaignController {
    private final CampaignService campaignServices;
    @Autowired
    public CampaignController(CampaignService campaignServices) {
        this.campaignServices = campaignServices;
    }
    @GetMapping("/get-all-campaigns")
    public ResponseEntity<List<Campaign>> getAllCampaigns() {
//        System.out.println(campaign);

        try {
            List<Campaign> AllCampaigns = campaignServices.getAllCampaigns();
            if (AllCampaigns.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(AllCampaigns);
            }
            return ResponseEntity.ok(AllCampaigns);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    //  home page campaigns(handled by super admins)
    @GetMapping("/get-homepage-campaigns")
    public ResponseEntity<List<Campaign>> getHomeCampaigns() {
        try{
                List<Campaign> top_funded = campaignServices.getTopFundedCampaigns();
                if(top_funded.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(top_funded);
                }
                return ResponseEntity.ok(top_funded);
            }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("/create-campaign")
    public ResponseEntity<Campaign> createCampaign(@RequestBody CampaignSessionDTO campaignDTO) {
        try{
//            Campaign cmp = new Campaign(campaignDTO.getId(), campaignDTO.getDescription(), campaignDTO.getTitle(), campaignDTO.getUserId());
            Campaign createdCampaign = campaignServices.createCampaign(campaignDTO);
            return new ResponseEntity<>(createdCampaign, HttpStatus.CREATED);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
//
@GetMapping("/{id}")
public ResponseEntity<Campaign> getCampaign(@PathVariable Long id) {
    try {
        Campaign campaign = campaignServices.getCampaignById(id);
        return ResponseEntity.ok(campaign);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
@PutMapping("/{id}")
public ResponseEntity<Campaign> updateCampaign(@PathVariable Long id, @RequestBody CampaignSessionDTO campaignDTO) {
    try {
        Campaign updatedCampaign = campaignServices.updateCampaign(id, campaignDTO);
        return ResponseEntity.ok(updatedCampaign);
    }catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}

    @PatchMapping("/{id}")
    public ResponseEntity<Campaign> partialUpdateCampaign(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        try {
            Campaign updatedCampaign = campaignServices.partialUpdateCampaign(id, updates);
            return ResponseEntity.ok(updatedCampaign);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCampaign(@PathVariable Long id) {
        try {
            campaignServices.deleteCampaign(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
//

}
