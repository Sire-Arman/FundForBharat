package com.example.demo.controller;

import com.example.demo.DTO.DonationSessionDTO;
import com.example.demo.DTO.UserSessionDTO;
import com.example.demo.model.Donation;
import com.example.demo.repository.DonationRepository;
import com.example.demo.service.DocumentService;
import com.example.demo.service.DonationService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donations")
public class DonationController {
    private final DonationService donationService;

    public DonationController (DonationService donationService, DocumentService documentService) {
        this.donationService = donationService;
    }
//    add donations
    @GetMapping("/get-all-donations")
    public ResponseEntity<List<DonationSessionDTO>> getAllDonations() {
        try {
            List<DonationSessionDTO> allDonations = donationService.getAllDonations();
            System.out.println(allDonations.size()+"size");
            if (allDonations.isEmpty()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            }
            return ResponseEntity.ok(allDonations);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("/get-donations-by-userId")
    public ResponseEntity<DonationSessionDTO> getDonationsByUserId(@RequestBody DonationSessionDTO donation){
        System.out.println(donation);
        try {
            DonationSessionDTO donationSessionDTO = donationService.getDonationsByUserId(donation.getUserId());
            if(donationSessionDTO == null){
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            }
            else{
                return ResponseEntity.ok(donationSessionDTO);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
//    get donations by campaign id
    @PostMapping("/get-donations-by-campaignId")
    public ResponseEntity<List<Donation>> getDonationByCampaignId(@RequestBody Long CampaignId){
        try{
            List<Donation> DonationsByCampaignId = donationService.getDonationsByCampaignId(CampaignId);
            if(DonationsByCampaignId == null){
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            }
            return ResponseEntity.ok(DonationsByCampaignId);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
//    Add donation
//    @PostMapping("/")
    @PostMapping("/add-donation")
    public void addDonation(@RequestBody DonationSessionDTO dto){
        try {
            donationService.createDonation(dto);
        }catch (Exception e){
            System.out.println(e);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Donation> updateDonation(@PathVariable Long id, @RequestBody DonationSessionDTO dto) {
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
}
