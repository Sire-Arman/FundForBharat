package com.example.demo.ai.tools;

import com.example.demo.model.Document;
import com.example.demo.repository.DocumentRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DocumentGuidanceTool {

    private final DocumentRepository documentRepository;

    @Autowired
    public DocumentGuidanceTool(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Tool(description = "Retrieves all verification documents submitted for a specific campaign ID.")
    public List<Document> getCampaignVerificationDocuments(Long campaignId) {
        return documentRepository.findByCampaignId(campaignId);
    }

    @Tool(description = "Retrieves the status of verification documents submitted by a specific user ID.")
    public String getUserUploadedCampaignDocuments(Long userId) {
        return documentRepository.findByUserId(userId)
                .map(doc -> "Document: " + doc.getDoc_type() + ", Status: " + doc.getStatus() + ", Remarks: " + doc.getRemarks())
                .orElse("No documents found for user ID: " + userId);
    }

    @Tool(description = "Provides guidance on what to do if a document status is REJECTED or UNDER_REVIEW based on the status code.")
    public String getActionableGuidanceForStatus(String docStatus, String remarks) {
        String status = docStatus.toUpperCase();
        if (status.contains("APPROVED")) {
            return "No action needed. The document is verified and approved.";
        }
        if (status.contains("PENDING")) {
            return "The document is in the verification queue. The verification team usually reviews documents within 24-48 hours.";
        }
        if (status.contains("UNDER_REVIEW")) {
            return "The document is currently being audited by a senior verification officer. Status will update shortly.";
        }
        if (status.contains("REJECTED")) {
            String note = "Reason: " + (remarks != null ? remarks : "No specific remarks provided.") + "\n";
            note += "Suggested Action: Please re-scan and re-upload the document ensuring all details (name, seal, dates) are clearly visible and match the campaign details.";
            return note;
        }
        return "Unknown document status: " + status + ". Please contact support.";
    }
}
