package com.example.demo.config;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedDatabase(
            UserRepository userRepository,
            RoleRepository roleRepository,
            CampaignRepository campaignRepository,
            DonationRepository donationRepository,
            DocumentRepository documentRepository,
            UserDocumentRepository userDocumentRepository
    ) {
        return args -> {
            // Only seed if database is empty
            if (userRepository.count() > 0) {
                System.out.println("Database already seeded. Skipping...");
                return;
            }

            System.out.println("Seeding database with dummy data...");

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            // --- Users ---
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@fundforbharat.org");
            admin.setPassword(encoder.encode("admin123"));
            admin.setFullName("Arman Siddiqui");
            admin.setAddress("New Delhi, India");
            admin.setCreatedAt(LocalDateTime.now());
            userRepository.save(admin);

            User campaignAdmin = new User();
            campaignAdmin.setUsername("priya_sharma");
            campaignAdmin.setEmail("priya@fundforbharat.org");
            campaignAdmin.setPassword(encoder.encode("priya123"));
            campaignAdmin.setFullName("Priya Sharma");
            campaignAdmin.setAddress("Mumbai, Maharashtra");
            campaignAdmin.setCreatedAt(LocalDateTime.now());
            userRepository.save(campaignAdmin);

            User regularUser1 = new User();
            regularUser1.setUsername("rahul_verma");
            regularUser1.setEmail("rahul@gmail.com");
            regularUser1.setPassword(encoder.encode("rahul123"));
            regularUser1.setFullName("Rahul Verma");
            regularUser1.setAddress("Lucknow, Uttar Pradesh");
            regularUser1.setCreatedAt(LocalDateTime.now());
            userRepository.save(regularUser1);

            User regularUser2 = new User();
            regularUser2.setUsername("sneha_patel");
            regularUser2.setEmail("sneha@gmail.com");
            regularUser2.setPassword(encoder.encode("sneha123"));
            regularUser2.setFullName("Sneha Patel");
            regularUser2.setAddress("Ahmedabad, Gujarat");
            regularUser2.setCreatedAt(LocalDateTime.now());
            userRepository.save(regularUser2);

            User regularUser3 = new User();
            regularUser3.setUsername("amit_kumar");
            regularUser3.setEmail("amit@gmail.com");
            regularUser3.setPassword(encoder.encode("amit123"));
            regularUser3.setFullName("Amit Kumar");
            regularUser3.setAddress("Patna, Bihar");
            regularUser3.setCreatedAt(LocalDateTime.now());
            userRepository.save(regularUser3);

            // --- Roles ---
            Role adminRole = new Role();
            adminRole.setUser(admin);
            adminRole.setRoleUser(true);
            adminRole.setRoleSuperAdmin(true);
            adminRole.setRoleCampaignAdmin(true);
            adminRole.setRoleDocumentAdmin(true);
            adminRole.setRolePaymentAdmin(true);
            roleRepository.save(adminRole);

            Role campaignAdminRole = new Role();
            campaignAdminRole.setUser(campaignAdmin);
            campaignAdminRole.setRoleUser(true);
            campaignAdminRole.setRoleCampaignAdmin(true);
            campaignAdminRole.setRoleDocumentAdmin(true);
            roleRepository.save(campaignAdminRole);

            Role userRole1 = new Role();
            userRole1.setUser(regularUser1);
            userRole1.setRoleUser(true);
            roleRepository.save(userRole1);

            Role userRole2 = new Role();
            userRole2.setUser(regularUser2);
            userRole2.setRoleUser(true);
            roleRepository.save(userRole2);

            Role userRole3 = new Role();
            userRole3.setUser(regularUser3);
            userRole3.setRoleUser(true);
            roleRepository.save(userRole3);

            // --- Campaigns ---
            Campaign campaign1 = new Campaign();
            campaign1.setUser_id(admin.getId());
            campaign1.setTitle("Medical Aid for Flood Victims in Assam");
            campaign1.setDescription("Severe flooding in Assam has displaced thousands of families. This campaign aims to provide immediate medical supplies, clean drinking water, and temporary shelter to the affected communities in Barpeta and Nalbari districts.");
            campaign1.setTarget_amount(500000.0);
            campaign1.setAmount_raised(125000.0);
            campaign1.setToBeShown(true);
            campaign1.setStart_date(LocalDate.of(2024, 7, 1));
            campaign1.setEnd_date(LocalDate.of(2024, 12, 31));
            campaignRepository.save(campaign1);

            Campaign campaign2 = new Campaign();
            campaign2.setUser_id(campaignAdmin.getId());
            campaign2.setTitle("Education Fund for Underprivileged Children in Bihar");
            campaign2.setDescription("Support the education of 200+ children from marginalized communities in rural Bihar. Funds will cover school fees, books, uniforms, and mid-day meals for one academic year. Every child deserves access to quality education.");
            campaign2.setTarget_amount(300000.0);
            campaign2.setAmount_raised(87500.0);
            campaign2.setToBeShown(true);
            campaign2.setStart_date(LocalDate.of(2024, 6, 15));
            campaign2.setEnd_date(LocalDate.of(2025, 3, 31));
            campaignRepository.save(campaign2);

            Campaign campaign3 = new Campaign();
            campaign3.setUser_id(regularUser1.getId());
            campaign3.setTitle("Heart Surgery Fund for Baby Aisha");
            campaign3.setDescription("6-month-old Aisha needs an urgent heart surgery that her family cannot afford. The surgery costs ₹8,00,000 at AIIMS Delhi. Her father is a daily wage worker and the family has exhausted all savings. Please help save baby Aisha's life.");
            campaign3.setTarget_amount(800000.0);
            campaign3.setAmount_raised(340000.0);
            campaign3.setToBeShown(true);
            campaign3.setStart_date(LocalDate.of(2024, 8, 1));
            campaign3.setEnd_date(LocalDate.of(2024, 10, 31));
            campaignRepository.save(campaign3);

            Campaign campaign4 = new Campaign();
            campaign4.setUser_id(regularUser2.getId());
            campaign4.setTitle("Clean Water Project for Rural Rajasthan");
            campaign4.setDescription("Install solar-powered water purification systems in 5 villages of Barmer district, Rajasthan. Each system can provide clean drinking water to 500+ villagers daily, reducing waterborne diseases by up to 80%.");
            campaign4.setTarget_amount(1200000.0);
            campaign4.setAmount_raised(450000.0);
            campaign4.setToBeShown(true);
            campaign4.setStart_date(LocalDate.of(2024, 5, 1));
            campaign4.setEnd_date(LocalDate.of(2025, 5, 1));
            campaignRepository.save(campaign4);

            Campaign campaign5 = new Campaign();
            campaign5.setUser_id(admin.getId());
            campaign5.setTitle("Women's Skill Development Centre - Varanasi");
            campaign5.setDescription("Establish a skill development centre for women in Varanasi offering training in tailoring, handicrafts, computer literacy, and financial management. The centre will empower 100 women annually to become self-sufficient.");
            campaign5.setTarget_amount(600000.0);
            campaign5.setAmount_raised(95000.0);
            campaign5.setToBeShown(false);
            campaign5.setStart_date(LocalDate.of(2024, 9, 1));
            campaign5.setEnd_date(LocalDate.of(2025, 8, 31));
            campaignRepository.save(campaign5);

            // --- Donations ---
            Donation d1 = new Donation();
            d1.setUser_id(regularUser1.getId());
            d1.setCampaign_id(campaign1.getId());
            d1.setAmount(25000.0);
            d1.setMode_of_payment(PaymentMode.UPI);
            d1.setDonation_date(LocalDate.of(2024, 7, 15));
            donationRepository.save(d1);

            Donation d2 = new Donation();
            d2.setUser_id(regularUser2.getId());
            d2.setCampaign_id(campaign1.getId());
            d2.setAmount(50000.0);
            d2.setMode_of_payment(PaymentMode.NET_BANKING);
            d2.setDonation_date(LocalDate.of(2024, 7, 20));
            donationRepository.save(d2);

            Donation d3 = new Donation();
            d3.setAlias_name("Anonymous Donor");
            d3.setCampaign_id(campaign3.getId());
            d3.setAmount(100000.0);
            d3.setMode_of_payment(PaymentMode.CREDIT_CARD);
            d3.setDonation_date(LocalDate.of(2024, 8, 5));
            donationRepository.save(d3);

            Donation d4 = new Donation();
            d4.setUser_id(regularUser3.getId());
            d4.setCampaign_id(campaign2.getId());
            d4.setAmount(15000.0);
            d4.setMode_of_payment(PaymentMode.UPI);
            d4.setDonation_date(LocalDate.of(2024, 7, 1));
            donationRepository.save(d4);

            Donation d5 = new Donation();
            d5.setUser_id(admin.getId());
            d5.setCampaign_id(campaign3.getId());
            d5.setAmount(50000.0);
            d5.setMode_of_payment(PaymentMode.DEBIT_CARD);
            d5.setDonation_date(LocalDate.of(2024, 8, 10));
            donationRepository.save(d5);

            Donation d6 = new Donation();
            d6.setAlias_name("Ravi Foundation");
            d6.setCampaign_id(campaign4.getId());
            d6.setAmount(200000.0);
            d6.setMode_of_payment(PaymentMode.CHEQUE);
            d6.setDonation_date(LocalDate.of(2024, 6, 20));
            donationRepository.save(d6);

            Donation d7 = new Donation();
            d7.setUser_id(regularUser1.getId());
            d7.setCampaign_id(campaign2.getId());
            d7.setAmount(10000.0);
            d7.setMode_of_payment(PaymentMode.WALLET);
            d7.setDonation_date(LocalDate.of(2024, 8, 1));
            donationRepository.save(d7);

            Donation d8 = new Donation();
            d8.setUser_id(campaignAdmin.getId());
            d8.setCampaign_id(campaign4.getId());
            d8.setAmount(75000.0);
            d8.setMode_of_payment(PaymentMode.UPI);
            d8.setDonation_date(LocalDate.of(2024, 7, 25));
            donationRepository.save(d8);

            // --- Documents (campaign verification docs) ---
            Document doc1 = new Document();
            doc1.setDoc_type("MEDICAL_REPORT");
            doc1.setDoc_url("https://storage.fundforbharat.org/docs/assam-flood-medical-report.pdf");
            doc1.setCampaign_id(campaign1.getId());
            doc1.setUpload_date(LocalDate.of(2024, 7, 2));
            doc1.setUpload_user(admin.getId());
            doc1.setStatus(Status.APPROVED);
            doc1.setRemarks("Verified by district medical officer");
            documentRepository.save(doc1);

            Document doc2 = new Document();
            doc2.setDoc_type("IDENTITY_PROOF");
            doc2.setDoc_url("https://storage.fundforbharat.org/docs/aisha-birth-certificate.pdf");
            doc2.setCampaign_id(campaign3.getId());
            doc2.setUpload_date(LocalDate.of(2024, 8, 1));
            doc2.setUpload_user(regularUser1.getId());
            doc2.setStatus(Status.APPROVED);
            doc2.setRemarks("Birth certificate verified");
            documentRepository.save(doc2);

            Document doc3 = new Document();
            doc3.setDoc_type("HOSPITAL_ESTIMATE");
            doc3.setDoc_url("https://storage.fundforbharat.org/docs/aiims-surgery-estimate.pdf");
            doc3.setCampaign_id(campaign3.getId());
            doc3.setUpload_date(LocalDate.of(2024, 8, 2));
            doc3.setUpload_user(regularUser1.getId());
            doc3.setStatus(Status.APPROVED);
            doc3.setRemarks("AIIMS cost estimate approved");
            documentRepository.save(doc3);

            Document doc4 = new Document();
            doc4.setDoc_type("PROJECT_PROPOSAL");
            doc4.setDoc_url("https://storage.fundforbharat.org/docs/rajasthan-water-proposal.pdf");
            doc4.setCampaign_id(campaign4.getId());
            doc4.setUpload_date(LocalDate.of(2024, 5, 5));
            doc4.setUpload_user(regularUser2.getId());
            doc4.setStatus(Status.UNDER_REVIEW);
            doc4.setRemarks("Pending technical review");
            documentRepository.save(doc4);

            Document doc5 = new Document();
            doc5.setDoc_type("SCHOOL_REGISTRATION");
            doc5.setDoc_url("https://storage.fundforbharat.org/docs/bihar-school-registration.pdf");
            doc5.setCampaign_id(campaign2.getId());
            doc5.setUpload_date(LocalDate.of(2024, 6, 16));
            doc5.setUpload_user(campaignAdmin.getId());
            doc5.setStatus(Status.APPROVED);
            doc5.setRemarks("Registration certificate valid");
            documentRepository.save(doc5);

            // --- User Documents (personal KYC docs) ---
            UserDocument ud1 = new UserDocument();
            ud1.setUser_id(regularUser1.getId());
            ud1.setAlias_name("Rahul Verma");
            ud1.setDoc_type("AADHAAR");
            ud1.setDoc_url("https://storage.fundforbharat.org/kyc/rahul-aadhaar.pdf");
            ud1.setStatus(Status.APPROVED);
            ud1.setRemarks("Aadhaar verified successfully");
            userDocumentRepository.save(ud1);

            UserDocument ud2 = new UserDocument();
            ud2.setUser_id(regularUser2.getId());
            ud2.setAlias_name("Sneha Patel");
            ud2.setDoc_type("PAN_CARD");
            ud2.setDoc_url("https://storage.fundforbharat.org/kyc/sneha-pan.pdf");
            ud2.setStatus(Status.APPROVED);
            ud2.setRemarks("PAN card verified");
            userDocumentRepository.save(ud2);

            UserDocument ud3 = new UserDocument();
            ud3.setUser_id(regularUser3.getId());
            ud3.setAlias_name("Amit Kumar");
            ud3.setDoc_type("VOTER_ID");
            ud3.setDoc_url("https://storage.fundforbharat.org/kyc/amit-voterid.pdf");
            ud3.setStatus(Status.PENDING);
            ud3.setRemarks("Awaiting manual verification");
            userDocumentRepository.save(ud3);

            System.out.println("Database seeded successfully!");
            System.out.println("  - 5 Users");
            System.out.println("  - 5 Roles");
            System.out.println("  - 5 Campaigns");
            System.out.println("  - 8 Donations");
            System.out.println("  - 5 Documents");
            System.out.println("  - 3 User Documents");
        };
    }
}
