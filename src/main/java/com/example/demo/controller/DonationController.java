package com.example.demo.controller;

import com.example.demo.DTO.DonationSessionDTO;
import com.example.demo.DTO.UserSessionDTO;
import com.example.demo.repository.DonationRepository;
import com.example.demo.service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donations")
public class DonationController {
    private final DonationService donationService;

    public DonationController (DonationService donationService) {
        this.donationService = donationService;
    }
    @GetMapping("/get-all-donations")
    public ResponseEntity<List<DonationSessionDTO>> getAllDonations() {
        try {
            List<DonationSessionDTO> allDonations = donationService.getAllDonations();
            System.out.println(allDonations.size()+"size");
            if (allDonations == null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(allDonations);
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
                return ResponseEntity.status(HttpStatus.CONFLICT).body(donationSessionDTO);
            }
            else{
                return ResponseEntity.ok(donationSessionDTO);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
