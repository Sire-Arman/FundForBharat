package com.example.demo.controller;

import com.example.demo.DTO.DonationSessionDTO;
import com.example.demo.DTO.UserSessionDTO;
import com.example.demo.model.Donation;
import com.example.demo.repository.DocumentRepository;
import com.example.demo.repository.DonationRepository;
import com.example.demo.service.CampaignService;
import com.example.demo.service.DocumentService;
import com.example.demo.service.DonationService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/donations")
public class DonationController {
    private final DonationService donationService;
    private final CampaignService campaignService;
    private final DocumentRepository documentRepository;

    public DonationController (DonationService donationService, DocumentService documentService, CampaignService campaignService, DocumentRepository documentRepository) {
        this.donationService = donationService;
        this.campaignService = campaignService;
        this.documentRepository = documentRepository;
    }
//    add donations
    @GetMapping("/get-all")
    public ResponseEntity<?> getAllDonations() {
        try {
            Optional<List<Donation>> allDonations = donationService.getAllDonations();
            if (allDonations.isPresent() && !allDonations.get().isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(allDonations);
            }
            return ResponseEntity.ok("No Donations found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/get-by-id")
    public ResponseEntity<?> getDonationById(@RequestParam("id") Long id) {
        try{
            Optional<Donation> donationOptional = donationService.getById(id);
            if(donationOptional.isPresent()){
                return ResponseEntity.status(HttpStatus.OK).body(donationOptional);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Donation with id " + id + " not found");
        }
        catch (IllegalArgumentException e) {
            // For invalid input data
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid donation data: " + e.getMessage());
        } catch (Exception e) {
            // For any other unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing your request");
        }
    }
    @GetMapping("/get-by-userId")
    public ResponseEntity<?> getDonationsByUserId(@RequestParam Long userId) {
        try {
            Optional<List<Donation>> optionalDonations = donationService.getDonationsByUserId(userId);

            if (optionalDonations.isPresent() && !optionalDonations.get().isEmpty()) {
                return ResponseEntity.ok(optionalDonations.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Donation not found with UserId: " + userId);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid UserId: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the donation: " + e.getMessage());
        }
    }
//    get donations by campaign id
    @GetMapping("/get-by-campaignId")
    public ResponseEntity<?> getDonationsByCampaignId(@RequestParam Long campaignId) {
        try {
            Optional<List<Donation>> optionalDonations = donationService.getDonationsByCampaignId(campaignId);

            if (optionalDonations.isPresent() && !optionalDonations.get().isEmpty()) {
                return ResponseEntity.ok(optionalDonations.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Donation not found with CampaignId: " + campaignId);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid campaignId: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the campaign: " + e.getMessage());
        }
    }
//    Add donation
//    @PostMapping("/")
    @PostMapping("/add-donation")
    public ResponseEntity<?> addDonation(@RequestBody DonationSessionDTO dto) {
        try {
            // Create the donation
            Donation createdDonation = donationService.createDonation(dto);

            // Add the donation amount to the campaign
            Long campaignId = dto.getCampaign_id();
            Double donationAmount = dto.getAmount();
            boolean amountAdded = campaignService.addDonationAmount(campaignId, donationAmount);

            if (!amountAdded) {
                // If adding amount to campaign failed, rollback the donation creation
                Boolean deleted = donationService.deleteById(createdDonation.getId(), false);
                if (!deleted) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Donation with id " + createdDonation.getId() + " not found");
                }
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Campaign not found. Donation not added.");
            }

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Donation added successfully\n" + createdDonation);

        } catch (IllegalArgumentException e) {
            // For invalid input data
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid donation data: " + e.getMessage());
        } catch (Exception e) {
            // For any other unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing your request");
        }
    }
    @PutMapping("/update-donation")
    public ResponseEntity<Donation> updateDonation(@RequestParam Long id, @RequestBody DonationSessionDTO dto) {
        try {
            Donation updatedDonation = donationService.updateDonation(id, dto);
            return ResponseEntity.ok(updatedDonation);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @DeleteMapping
    public ResponseEntity<?> deleteDonation(@RequestParam Long id, @RequestParam(defaultValue = "true") Boolean AmountToBeReduced) {
        try {
            boolean deleted = donationService.deleteById(id, AmountToBeReduced);

            if (deleted) {
                return ResponseEntity.ok("Donation with id " + id + " deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Donation with id " + id + " not found");
            }
        } catch (IllegalArgumentException e) {
            // For invalid input data
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid donation data: " + e.getMessage());
        } catch (Exception e) {
            // For any other unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing your request");
        }
    }
}
