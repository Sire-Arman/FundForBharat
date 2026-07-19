package com.example.demo.ai.tools;

import com.example.demo.model.Status;
import com.example.demo.model.UserDocument;
import com.example.demo.repository.UserDocumentRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EligibilityTool {

    private final UserDocumentRepository userDocumentRepository;

    @Autowired
    public EligibilityTool(UserDocumentRepository userDocumentRepository) {
        this.userDocumentRepository = userDocumentRepository;
    }

    @Tool(description = "Checks if a user is eligible to donate or raise a campaign by verifying their KYC document status.")
    public String checkUserKycEligibility(Long userId) {
        UserDocument doc = userDocumentRepository.findByUserId(userId);
        if (doc == null) {
            return "User has not uploaded any KYC documents. They must upload an identity document first.";
        }
        if (doc.getStatus() == Status.APPROVED) {
            return "Eligible. User's KYC document (" + doc.getDoc_type() + ") is APPROVED.";
        }
        if (doc.getStatus() == Status.PENDING) {
            return "Pending. User's KYC document (" + doc.getDoc_type() + ") is currently awaiting review.";
        }
        return "Not Eligible. User's KYC document was " + doc.getStatus() + ". Remarks: " + doc.getRemarks();
    }

    @Tool(description = "Returns the list of mandatory documents required to be uploaded for a specific campaign type.")
    public List<String> getRequiredDocumentsForCampaign(String campaignType) {
        List<String> docs = new ArrayList<>();
        docs.add("IDENTITY_PROOF (Aadhaar, PAN, or Voter ID)");
        
        String type = campaignType.toUpperCase();
        if (type.contains("MEDICAL") || type.contains("HEALTH")) {
            docs.add("MEDICAL_REPORT (Signed by certified doctor)");
            docs.add("HOSPITAL_ESTIMATE (Official bill/cost breakdown from the hospital)");
            docs.add("BANK_ACCOUNT_DETAILS (For fund transfers)");
        } else if (type.contains("EDUCATION") || type.contains("SCHOOL") || type.contains("COLLEGE")) {
            docs.add("SCHOOL_REGISTRATION (Government affiliation certificate)");
            docs.add("FEE_STRUCTURE (Official school/college fee breakdown)");
            docs.add("STUDENT_BONAFIDE (Certificate from school head)");
        } else if (type.contains("FLOOD") || type.contains("DISASTER") || type.contains("RELIEF")) {
            docs.add("DISASTER_AFFECTED_PROOFS (Local authority certificate)");
            docs.add("PROJECT_PROPOSAL (Detailing distribution plan)");
        } else {
            docs.add("PROJECT_PROPOSAL (Detailed description of fund utilization)");
            docs.add("LOCAL_AUTHORITY_NOC (No Objection Certificate)");
        }
        return docs;
    }

    @Tool(description = "Validates the campaign category and target amount against platform guidelines, indicating if it requires super admin approval.")
    public String checkCampaignEligibilityRules(String category, double targetAmount) {
        String cat = category.toUpperCase();
        if (targetAmount <= 0) {
            return "Invalid target amount. Must be greater than zero.";
        }
        if (cat.contains("MEDICAL") || cat.contains("HEALTH")) {
            if (targetAmount <= 1000000.0) {
                return "Eligible. Medical campaigns up to ₹1,000,000 are standard and approved automatically after KYC.";
            } else {
                return "Requires Review. Medical campaigns exceeding ₹1,000,000 require Super Admin manual vetting.";
            }
        }
        if (cat.contains("EDUCATION")) {
            if (targetAmount <= 500000.0) {
                return "Eligible. Education campaigns up to ₹500,000 are standard and auto-approved.";
            } else {
                return "Requires Review. Education campaigns exceeding ₹500,000 require special document vetting.";
            }
        }
        if (targetAmount <= 200000.0) {
            return "Eligible. General campaigns up to ₹200,000 are standard.";
        }
        return "Requires Review. General campaigns exceeding ₹200,000 require detailed project proposal reviews.";
    }
}
