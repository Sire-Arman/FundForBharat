package com.example.demo.controller;

import com.example.demo.DTO.CampaignSessionDTO;
import com.example.demo.DTO.CampaignWithDonationsDTO;
import com.example.demo.model.Campaign;
import com.example.demo.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
//ToDo
//endpoint transactional annotation-pending
//export to fundraiser grp(Monday) - pending
//first step is jwt generation
//Add campaign document in the response of get-campaigns
//Handle 400 series errors in each controller before calling services
//add comments where ever possible

// ----------------------------- //
//fix the get all campaign with donations endpoint-done
//path variable to request param-done
//proper collection for postman-done
//implement jwt asap-pending
//user error handling correctly. - done

@RestController
@RequestMapping("/api/campaigns")
public class CampaignController {
    private final CampaignService campaignServices;
    @Autowired
    public CampaignController(CampaignService campaignServices) {
        this.campaignServices = campaignServices;
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Campaign>> getAllCampaigns() {
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
    @GetMapping("/get-all-with-donations")
    public ResponseEntity<List<CampaignWithDonationsDTO>> getAllCampaignsWithDonations() {
        List<CampaignWithDonationsDTO> campaigns = campaignServices.getAllCampaignsWithDonations();
        return ResponseEntity.ok(campaigns);
    }
    //  home page campaigns(handled by super admins)
    @GetMapping("/get-homepage-campaigns")
    public ResponseEntity<List<Campaign>> getHomePageCampaigns() {
        try{
                List<Campaign> homepage_c = campaignServices.getHomePageCampaigns();
                if(homepage_c.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.OK).body(homepage_c);
                }
                return ResponseEntity.ok(homepage_c);
            }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/get-top-funded-campaigns")
    public ResponseEntity<List<Campaign>> getTopFundedCampaigns() {
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
@GetMapping
public ResponseEntity<?> getCampaign(@RequestParam Long id) {
    try {
        Optional<Campaign> campaignOptional = campaignServices.getCampaignById(id);

        if (campaignOptional.isPresent()) {
            return ResponseEntity.ok(campaignOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Campaign not found with id: " + id);
        }
    } catch (IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Invalid campaign id: " + e.getMessage());
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An error occurred while fetching the campaign: " + e.getMessage());
    }
}
    @PutMapping
    public ResponseEntity<?> updateCampaign(@RequestParam Long id, @RequestBody CampaignSessionDTO campaignDTO) {
        try {
            Optional<Campaign> updatedCampaign = campaignServices.updateCampaign(id, campaignDTO);
            if (updatedCampaign.isPresent()) {
                return ResponseEntity.ok(updatedCampaign.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Campaign not updated with id: " + id);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid campaign id: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the campaign: " + e.getMessage());
        }
    }

    @PatchMapping
    public ResponseEntity<?> partialUpdateCampaign(@RequestParam Long id, @RequestBody Map<String, Object> updates) {
        try {
            Optional<Campaign> patchedCampaign = campaignServices.partialUpdateCampaign(id, updates);
            if (patchedCampaign.isPresent()) {
                return ResponseEntity.ok("Updated campaign with id "+ id + "\n" +patchedCampaign.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Campaign not patched with id: " + id);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid campaign id: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the campaign: " + e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCampaign(@RequestParam Long id) {
        try {
            boolean deleted = campaignServices.deleteCampaign(id);
            if (deleted) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body("Campaign deleted with id: " + id);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Campaign not found with id: " + id);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid campaign ID: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the campaign: " + e.getMessage());
        }
    }
//

}
