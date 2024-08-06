package com.example.demo.service;
import com.example.demo.DTO.CampaignSessionDTO;
import com.example.demo.DTO.CampaignWithDonationsDTO;
import com.example.demo.DTO.DocumentSessionDTO;
import com.example.demo.DTO.DonationSessionDTO;
import com.example.demo.model.*;
import com.example.demo.repository.CampaignRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
public class CampaignService {
    @Autowired
    private CampaignRepository campaignRepository;
    public CampaignService(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }

    /**
     * this method takes in Campaign DTO
     * sets data
     * saves data
     * @param campaignDTO
     * @returns saved data.
     */

//    @Transactional
//    public Campaign createCampaign(CampaignSessionDTO campaignDTO) {
//        Campaign campaign = new Campaign();
//        Long userId = campaignDTO.getUserId();
//        String title = campaignDTO.getTitle();
//        String description = campaignDTO.getDescription();
//        Boolean toBeShown = campaignDTO.getToBeShown();
//        Double target_amount = campaignDTO.getTarget_amount();
//        LocalDate startDate = campaignDTO.getStartDate();
//        LocalDate endDate = campaignDTO.getEndDate();
//
//        campaign.setUser_id(userId);
//        campaign.setTitle(title);
//        campaign.setDescription(description);
//        campaign.setTarget_amount(target_amount);
//        campaign.setToBeShown(toBeShown);
//        campaign.setStart_date(startDate);
//        campaign.setEnd_date(endDate);
//
//         return campaignRepository.save(campaign);
//    }

    /**
     * this method takes in Campaign Id
     * returns Optional Campaign Object
     * @param id
     * @return Campaign
     */


    @Transactional
    public Optional<Campaign> getCampaignById(Long id) {
        return campaignRepository.findCampaignById(id);
    }


    /**
     * this method takes in Campaign Id and a map of
     * String (denoting column) and object (denoting column value)
     * returns Optional Campaign Object
     * @param id
     * *@param Map<String,Object>
     */
//    @Transactional
//    public Optional<Campaign> partialUpdateCampaign(Long id, Map<String, Object> updates) {
//        Optional<Campaign> campaignOpt = campaignRepository.findCampaignById(id);
//        if (campaignOpt.isPresent()) {
//            Campaign campaign = campaignOpt.get();
//
//            if (updates.containsKey("description")) {
//                campaign.setDescription((String) updates.get("description"));
//            }
//            if (updates.containsKey("title")) {
//                campaign.setTitle((String) updates.get("title"));
//            }
//            if (updates.containsKey("toBeShown")) {
//                campaign.setToBeShown(Boolean.valueOf(updates.get("toBeShown").toString()));
//            }
//            if (updates.containsKey("target_amount")) {
//                campaign.setTarget_amount(Double.valueOf(updates.get("target_amount").toString()));
//            }
//            if (updates.containsKey("start_date")) {
//                campaign.setStart_date(LocalDate.parse(updates.get("start_date").toString()));
//            }
//            if (updates.containsKey("end_date")) {
//                campaign.setEnd_date(LocalDate.parse(updates.get("end_date").toString()));
//            }
//            if (updates.containsKey("user_id")) {
//                campaign.setUser_id(Long.valueOf(updates.get("user_id").toString()));
//            }
//
//            return Optional.of(campaignRepository.save(campaign));
//        } else {
//            return Optional.empty();
//        }
//    }

    /**
     * This method takes id and campaignDTO to completely update
     * a Campaign and returns it
     * @param id
     * @param campaignDTO
     * @return Campaign
     */
//    @Transactional
//    public Optional<Campaign> updateCampaign(Long id, CampaignSessionDTO campaignDTO) {
//        Optional<Campaign> cmpOpt = campaignRepository.findCampaignById(id);
//        Campaign updated = new Campaign();
//        if(cmpOpt.isPresent()) {
//            Campaign campaign = cmpOpt.get();
//            campaign.setTitle(campaignDTO.getTitle());
//            campaign.setDescription(campaignDTO.getDescription());
//            campaign.setTarget_amount(campaignDTO.getTarget_amount());
//            System.out.println(campaignDTO.getStartDate());
//            campaign.setStart_date(campaignDTO.getStartDate());
//            campaign.setEnd_date(campaignDTO.getEndDate());
//            campaign.setUser_id(campaignDTO.getUserId());
//            updated = campaign;
//
//            campaignRepository.save(campaign);
//        }
//        return Optional.of(updated);
//    }

    /**
     * Deletes a Campaign with given id
     * return false if failed
     * @param id
     * @return
     */
//    @Transactional
//    public boolean deleteCampaign(Long id) {
//        if (campaignRepository.existsById(id)) {
//            campaignRepository.deleteById(id);
//            return true;
//        }
//        return false;
//    }

    /**
     * Finds a campaign by given and
     * adds amount to its amount_raised
     * @param id
     * @param amount
     * @return
     */
    @Transactional
    public boolean addDonationAmount(Long id, Double amount) {
        Optional<Campaign> optionalCampaign = campaignRepository.findById(id);

        if (optionalCampaign.isPresent()) {
            Campaign campaign = optionalCampaign.get();
            Double currentAmount = campaign.getAmount_raised();
            Double newAmount = currentAmount + amount;

            campaign.setAmount_raised(newAmount);
            campaignRepository.save(campaign);
            return true;  // Operation successful
        } else {
            System.err.println("Campaign with id " + id + " not found");
            return false;  // Operation failed
        }
    }

    /**
     * gives a List of top funded campaigns
     * @return List
     */
    @Transactional
    public List<Campaign> getTopFundedCampaigns(){
        return campaignRepository.findTop10ByOrderByAmountRaisedDesc();
    }

    /**
     * Returns a list of campaigns to be displayed at home page
     * handled by super admin
     * @return List
     */
    @Transactional
    public List<Object[]> getHomePageCampaigns(){
        return campaignRepository.findAllCampaignsWithDonationsAndDocuments();
    }

    /**
     * Returns a specific Campaign with its donations and documents
     * using given id
     * @param id
     * @return
     */
    @Transactional
    public CampaignWithDonationsDTO getCampaignWithDonationAndDocumentById(Long id) {
        try {
            Object[] row = campaignRepository.findCampaignsWithDonationsAndDocuments(id);


            CampaignWithDonationsDTO dto = null;


                Long campaignId = (Long) row[0];

                if (dto == null) {
                    dto = getCampaignWithDonationsDTO(row);
                }

                // Process donation
                if (row[11] != null) {
                    Long donationId = (Long) row[11];
                    if (dto.getDonations().stream().noneMatch(d -> donationId.equals(d.getId()))) {
                        DonationSessionDTO donation = getDonationSessionDTO(row);
                        dto.getDonations().add(donation);
                    }
                }

                // Process document
                if (row[18] != null) {
                    Long documentId = (Long) row[18];
                    if (dto.getDocuments().stream().noneMatch(d -> documentId.equals(d.getId()))) {
                        DocumentSessionDTO document = getDocumentSessionDTO(row);
                        dto.getDocuments().add(document);
                    }
                }

            return dto;

        } catch (Exception e) {
//            logger.error("Error fetching campaign with id: {}", id, e);
            throw new RuntimeException("Error fetching campaign details", e);
        }
    }
    /**
     * This methods returns a List of Campaigns
     * along with donations and documents
     * @return CampaignWithDonationsDTO
     */
    @Transactional
    public List<CampaignWithDonationsDTO> getAllCampaignsWithDonations() {
        List<CampaignWithDonationsDTO> results = new ArrayList<>();
        try {
            List<Object[]> rawResults = campaignRepository.findAllCampaignsWithDonationsAndDocuments();
            Map<Long, CampaignWithDonationsDTO> campaignMap = new LinkedHashMap<>();

            for (Object[] row : rawResults) {
                try {
                    Long campaignId = (Long) row[0];
//                    System.out.println("Processing campaign ID: " + campaignId);

                    CampaignWithDonationsDTO campaign = campaignMap.computeIfAbsent(campaignId, id -> {
                        try {
                            return getCampaignWithDonationsDTO(row);
                        } catch (Exception e) {
                            System.err.println("Error in getCampaignWithDonationsDTO for campaign ID: " + id);
                            e.printStackTrace();
                            return null;
                        }
                    });

                    if (campaign == null) {
                        System.err.println("Failed to create campaign DTO for ID: " + campaignId);
                        continue;
                    }

                    if (row[11] != null) {
                        Long donationId = (Long) row[11];
//                        System.out.println("Processing donation ID: " + donationId + " for campaign ID: " + campaignId);
                        if (campaign.getDonations().stream().noneMatch(d -> d.getId().equals(donationId))) {
                            try {
                                DonationSessionDTO donation = getDonationSessionDTO(row);
                                campaign.getDonations().add(donation);
                            } catch (Exception e) {
                                System.err.println("Error processing donation ID: " + donationId);
                                e.printStackTrace();
                            }
                        }
                    }

                    if (row[18] != null) {
                        Long documentId = (Long) row[18];
//                        System.out.println("Processing document ID: " + documentId + " for campaign ID: " + campaignId);
                        if (campaign.getDocuments().stream().noneMatch(d -> d.getId().equals(documentId))) {
                            try {
                                DocumentSessionDTO document = getDocumentSessionDTO(row);
                                campaign.getDocuments().add(document);
                            } catch (Exception e) {
                                System.err.println("Error processing document ID: " + documentId);
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Error processing row: " + Arrays.toString(row));
                    e.printStackTrace();
                }
            }

            results = new ArrayList<>(campaignMap.values());
        } catch (Exception e) {
            System.err.println("Error in getAllCampaignsWithDonations");
            e.printStackTrace();
        }

        return results;
    }

        private static CampaignWithDonationsDTO getCampaignWithDonationsDTO(Object[] row) {
            CampaignWithDonationsDTO dto = new CampaignWithDonationsDTO();
            dto.setId((Long) row[0]);
            dto.setUserId((Long) row[1]);
            dto.setTitle((String) row[2]);
            dto.setDescription((String) row[3]);
            dto.setStartDate((LocalDate) row[4]);
            dto.setEndDate((LocalDate) row[5]);
            dto.setTargetAmount((Double) row[6]);
            dto.setAmountRaised((Double) row[7]);
            dto.setCreatedAt((LocalDateTime) row[8]);
            dto.setUpdatedAt((LocalDateTime) row[9]);
            dto.setToBeShown((Boolean) row[10]);
            dto.setDonations(new ArrayList<>());
            dto.setDocuments(new ArrayList<>());
            return dto;
        }

    private static DonationSessionDTO getDonationSessionDTO(Object[] row) {
        DonationSessionDTO donation = new DonationSessionDTO();
        donation.setId((Long) row[11]);
        donation.setUser_id((Long) row[12]);
        donation.setCampaign_id((Long) row[13]);
        donation.setAlias_name((String) row[14]);
        donation.setAmount((Double) row[15]);
        donation.setModeOfPayment((PaymentMode) row[16]);
        donation.setDonation_date((LocalDate) row[17]);
        return donation;
    }

    private static DocumentSessionDTO getDocumentSessionDTO(Object[] row) {
        DocumentSessionDTO document = new DocumentSessionDTO();
        try {
            document.setId((Long) row[18]);
            document.setDoc_type((String) row[19]);
            document.setDoc_url((String) row[20]);
            document.setCampaign_id((Long) row[21]);
            document.setRemarks((String) row[22]);

            if (row[23] != null) {
                try {
//                    String statusStr = (String) row[23];
//                    Status status = Status.valueOf(statusStr.toUpperCase());
                    document.setStatus((Status) row[23]);
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid status value: " + row[23]);
                    e.printStackTrace();
                    document.setStatus(Status.UNDER_REVIEW); // Default status
                }
            }

            document.setUpload_date((LocalDate) row[24]);
            document.setUpload_user((Long) row[25]);
        } catch (Exception e) {
            System.err.println("Error in getDocumentSessionDTO: " + Arrays.toString(row));
            e.printStackTrace();
        }
        return document;
    }

}
