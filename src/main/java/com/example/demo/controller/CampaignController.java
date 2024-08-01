package com.example.demo.controller;

import com.example.demo.DTO.CampaignSessionDTO;
import com.example.demo.DTO.CampaignWithDonationsDTO;
import com.example.demo.model.Campaign;
import com.example.demo.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.*;
//ToDo
//Handle 400 series errors in each controller before calling services
//add comments where ever possible
//export to fundraiser grp(Monday) - pending

// ----------------------------- //
//first step is jwt generation-done
//Add campaign document in the response of get-campaigns-done
//endpoint transactional annotation-done
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
    public ResponseEntity<?> getAllCampaigns() {
        try {
            List<CampaignWithDonationsDTO> campaigns = campaignServices.getAllCampaignsWithDonations();
            if(campaigns.isEmpty()) {
                return  ResponseEntity.status(HttpStatus.NO_CONTENT).body("No campaigns found");
            }
            return ResponseEntity.ok(campaigns);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }






    @GetMapping("/get-all-with-donations")
    public ResponseEntity<?> getAllCampaignsWithDonations() {
        try {
            // Fetch all campaigns with their donations
            List<CampaignWithDonationsDTO> campaigns = campaignServices.getAllCampaignsWithDonations();

            // Check if the list is empty
            if (campaigns.isEmpty()) {
                // Return 204 No Content if there are no campaigns
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No campaigns found");
            }

            // Return the list of campaigns with 200 OK status
            return ResponseEntity.ok(campaigns);

        } catch (IllegalArgumentException e) {
            // Handle invalid input parameters if any
            return ResponseEntity.badRequest().body("Invalid request parameters: " + e.getMessage());

        } catch (Exception e) {
            // Log the unexpected error

            // Return a generic error message for unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred while fetching campaigns");
        }
    }








    //  home page campaigns(handled by super admins)
    @GetMapping("/get-homepage-campaigns")
    public ResponseEntity<?> getHomePageCampaigns() {
        try {
            // Fetch homepage campaigns
            List<CampaignWithDonationsDTO> homepageCampaigns = campaignServices.getHomePageCampaigns();

            // Check if the list is empty
            if (homepageCampaigns.isEmpty()) {
                // Return 204 No Content if there are no campaigns
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No campaigns found");
            }

            // Return the list of campaigns with 200 OK status
            return ResponseEntity.ok(homepageCampaigns);

        } catch (IllegalArgumentException e) {
            // Handle invalid input parameters if any
            return ResponseEntity.badRequest().body("Invalid request parameters: " + e.getMessage());

        } catch (Exception e) {
            // Log the unexpected error

            // Return a generic error message for unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred while fetching campaigns");
        }
    }






    @GetMapping("/get-top-funded-campaigns")
    public ResponseEntity<?> getTopFundedCampaigns() {
        try{
            List<Campaign> top_funded = campaignServices.getTopFundedCampaigns();
            if(top_funded.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No Top Funded Campaigns Available");
            }
            return ResponseEntity.ok(top_funded);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @PostMapping("/create-campaign")
    public ResponseEntity<?> createCampaign(@RequestBody CampaignSessionDTO campaignDTO) {

        try{
//            Campaign cmp = new Campaign(campaignDTO.getId(), campaignDTO.getDescription(), campaignDTO.getTitle(), campaignDTO.getUserId());
            Campaign createdCampaign = campaignServices.createCampaign(campaignDTO);
            if(createdCampaign == null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Failed to create campaign, Please check your credentials!");
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCampaign);
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid request parameters: " + e.getMessage());
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }
//@GetMapping
//public ResponseEntity<?> getCampaign(@RequestParam Long id) {
//    try {
//        Optional<Campaign> campaignOptional = campaignServices.getCampaignById(id);
//
//        if (campaignOptional.isPresent()) {
//            return ResponseEntity.ok(campaignOptional.get());
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body("Campaign not found with id: " + id);
//        }
//    } catch (IllegalArgumentException e) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                .body("Invalid campaign id: " + e.getMessage());
//    } catch (Exception e) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body("An error occurred while fetching the campaign: " + e.getMessage());
//    }
//}
@GetMapping
public ResponseEntity<?> getCampaignById(@RequestParam Long id){
        try{
            CampaignWithDonationsDTO dto = campaignServices.getCampaignWithDonationAndDocumentById(id);
            if(dto == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Campaign not found with id: " + id);
            }
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Invalid campaign id: " + e.getMessage());
        }catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
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
            return patchedCampaign.map(campaign -> ResponseEntity.ok("Updated campaign with id " + id + "\n" + campaign))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Campaign not patched with id: " + id));
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
