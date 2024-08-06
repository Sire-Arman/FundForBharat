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
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/donations")
public class DonationController {
    private final DonationService donationService;
    private final CampaignService campaignService;
    private final DocumentRepository documentRepository;

    @Autowired
    public DonationController (DonationService donationService, CampaignService campaignService, DocumentRepository documentRepository) {
        this.donationService = donationService;
        this.campaignService = campaignService;
        this.documentRepository = documentRepository;
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllDonations() {
        try {
            Optional<List<Donation>> allDonations = donationService.getAllDonations();
            if (allDonations.isPresent() && !allDonations.get().isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(allDonations.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Donations found");
            }
        } catch (DataAccessException e) {
            // Handles database-related errors
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Database error occurred");
        } catch (IllegalArgumentException e) {
            // Handles illegal arguments passed to the method
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input parameters");
        } catch (Exception e) {
            // Handles other unforeseen exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<?> getDonationById(@RequestParam("id") Long id) {
        if(id.equals(null)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input parameters");
        }
        try {
            Optional<Donation> donationOptional = donationService.getById(id);
            if (donationOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(donationOptional.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Donation with id " + id + " not found");
            }
        } catch (IllegalArgumentException e) {
            // For invalid input data
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid donation id: " + e.getMessage());
        } catch (Exception e) {
            // For any other unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request: " + e.getMessage());
        }
    }

    @GetMapping("/get-by-userId")
    public ResponseEntity<?> getDonationsByUserId(@RequestParam Long userId) {
        if(userId.equals(null)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input parameters");
        }
        try {
            Optional<List<DonationSessionDTO>> optionalDonations = donationService.getDonationsByUserId(userId);
            if (optionalDonations.isPresent() && !optionalDonations.get().isEmpty()) {
                return ResponseEntity.ok(optionalDonations.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Donations not found for UserId: " + userId);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid UserId: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching the donations: " + e.getMessage());
        }
    }

    //    get donations by campaign id
    @GetMapping("/get-by-campaignId")
    public ResponseEntity<?> getDonationsByCampaignId(@RequestParam Long campaignId) {
        if(campaignId.equals(null)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input parameters");
        }
        try {
            Optional<List<Donation>> optionalDonations = donationService.getDonationsByCampaignId(campaignId);

            if (optionalDonations.isPresent() && !optionalDonations.get().isEmpty()) {
                return ResponseEntity.ok(optionalDonations.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Donations not found for CampaignId: " + campaignId);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid campaignId: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching the donations: " + e.getMessage());
        }
    }

    //    Add donation
//    inter server call will be required for this
    @PostMapping("/add-donation")
    public ResponseEntity<?> addDonation(@RequestBody DonationSessionDTO dto) {
        List<String> errors = validateDTO(dto);
        if (!errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        try {
            // Validate input data (you can add more validation if needed)
            if (dto.getCampaign_id() == null || dto.getAmount() == null || dto.getAmount() <= 0) {
                throw new IllegalArgumentException("Invalid donation data: Campaign ID and amount must be provided and amount must be greater than zero.");
            }

            // Create the donation
            Donation createdDonation = donationService.createDonation(dto);

            // Add the donation amount to the campaign
            Long campaignId = dto.getCampaign_id();
            Double donationAmount = dto.getAmount();

            boolean amountAdded = campaignService.addDonationAmount(campaignId, donationAmount);

//            if (!amountAdded) {
//                // If adding amount to campaign failed, rollback the donation creation
//                boolean deleted = donationService.deleteById(createdDonation.getId(), false);
//                if (!deleted) {
//                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to rollback donation creation after campaign update failure.");
//                }
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Campaign not found. Donation not added.");
//            }

            return ResponseEntity.status(HttpStatus.CREATED).body("Donation added successfully: " + createdDonation);

        } catch (IllegalArgumentException e) {
            // For invalid input data
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid donation data: " + e.getMessage());
        } catch (Exception e) {
            // For any other unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request: " + e.getMessage());
        }
    }

//    @PutMapping("/update-donation")
//    public ResponseEntity<?> updateDonation(@RequestParam Long id, @RequestBody DonationSessionDTO dto) {
//
//        List<String> errors = validateDTO(dto);
//        if (!errors.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
//        }
//        try {
//            // Validate input data (you can add more validation if needed)
//            if (dto.equals(null)) {
//                throw new IllegalArgumentException("Donation ID and update data must be provided.");
//            }
//
//            Donation updatedDonation = donationService.updateDonation(id, dto);
//            return ResponseEntity.ok(updatedDonation);
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Donation with id " + id + " not found");
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid donation data: " + e.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the donation: " + e.getMessage());
//        }
//    }

//    @DeleteMapping("/delete-donation")
//    public ResponseEntity<?> deleteDonation(@RequestParam Long id, @RequestParam(defaultValue = "true") Boolean AmountToBeReduced) {
//        if(id.equals(null)){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input parameters");
//        }
//        try {
//            // Validate input data (you can add more validation if needed)
//            if (id == null) {
//                throw new IllegalArgumentException("Donation ID must be provided.");
//            }
//
//            boolean deleted = donationService.deleteById(id, AmountToBeReduced);
//
//            if (deleted) {
//                return ResponseEntity.ok("Donation with id " + id + " deleted successfully");
//            } else {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Donation with id " + id + " not found");
//            }
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid donation data: " + e.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the donation: " + e.getMessage());
//        }
//    }

    private List<String> validateDTO(DonationSessionDTO donationDto) {
        List<String> errors = new ArrayList<>();

        if (donationDto.getId() != null && donationDto.getId() <= 0) {
            errors.add("ID must be a positive number");
        }
        if (donationDto.getUser_id() == null || donationDto.getUser_id() <= 0) {
            errors.add("User ID is required and must be a positive number");
        }
        if (donationDto.getCampaign_id() == null || donationDto.getCampaign_id() <= 0) {
            errors.add("Campaign ID is required and must be a positive number");
        }
        if (donationDto.getAlias_name() == null || donationDto.getAlias_name().trim().isEmpty()) {
            errors.add("Alias name is required");
        }
        if (donationDto.getAmount() == null || donationDto.getAmount() <= 0) {
            errors.add("Amount is required and must be a positive number");
        }
        if (donationDto.getDonation_date() == null || donationDto.getDonation_date().isAfter(LocalDate.now())) {
            errors.add("Donation date is required and must not be in the future");
        }
        if (donationDto.getModeOfPayment() == null) {
            errors.add("Mode of payment is required");
        }
        // errorMessage can be null or empty, so we don't validate it

        return errors;
    }

}
