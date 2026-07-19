package com.example.demo.ai.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FinancialHelpTool {

    @Tool(description = "Returns a step-by-step guide on how to raise a campaign and collect funds on FundForBharat.")
    public List<String> getStepsToRaiseCampaign() {
        List<String> steps = new ArrayList<>();
        steps.add("Step 1: Sign up and complete your personal profile KYC by uploading Aadhaar, PAN, or Voter ID.");
        steps.add("Step 2: Click on 'Raise Campaign' on the user dashboard.");
        steps.add("Step 3: Fill in the campaign title, category (Medical, Education, Disaster Relief), target amount, and a detailed description.");
        steps.add("Step 4: Upload the mandatory verification documents (e.g., hospital bill estimate for medical campaigns, or school fee structures for education).");
        steps.add("Step 5: Click Submit. The verification team will review your documents and verify the campaign within 24-48 hours.");
        steps.add("Step 6: Once approved, your campaign goes live. Share the campaign link on WhatsApp, Facebook, and Twitter to collect donations.");
        steps.add("Step 7: Withdraw the raised funds directly to your verified bank account once the verification process is complete.");
        return steps;
    }

    @Tool(description = "Retrieves a list of applicable government financial schemes and assistance programs based on category (e.g. medical, education).")
    public List<Map<String, String>> getAvailableGovernmentSchemes(String category) {
        List<Map<String, String>> schemes = new ArrayList<>();
        String cat = category.toUpperCase();

        if (cat.contains("MEDICAL") || cat.contains("HEALTH")) {
            Map<String, String> s1 = new HashMap<>();
            s1.put("name", "Ayushman Bharat Pradhan Mantri Jan Arogya Yojana (PM-JAY)");
            s1.put("coverage", "Up to ₹5,000,000 per family per year for secondary and tertiary care hospitalization.");
            s1.put("eligibility", "Low-income families identified by SECC database.");
            schemes.add(s1);

            Map<String, String> s2 = new HashMap<>();
            s2.put("name", "Rashtriya Arogya Nidhi (RAN)");
            s2.put("coverage", "One-time financial assistance up to ₹1,500,000 for patients suffering from life-threatening diseases.");
            s2.put("eligibility", "Patients living below poverty line (BPL) receiving treatment in super-specialty government hospitals.");
            schemes.add(s2);
        } else if (cat.contains("EDUCATION") || cat.contains("SCHOOL")) {
            Map<String, String> s1 = new HashMap<>();
            s1.put("name", "Pradhan Mantri Uchchatar Shiksha Protsahan (PM-USP)");
            s1.put("coverage", "Interest subsidy on education loans and scholarships for economically weaker section students.");
            s1.put("eligibility", "Students with parental income below ₹8,00,000 per annum pursuing higher education.");
            schemes.add(s1);

            Map<String, String> s2 = new HashMap<>();
            s2.put("name", "Post Matric Scholarship Scheme");
            s2.put("coverage", "Full tuition fee reimbursement and monthly maintenance allowance.");
            s2.put("eligibility", "SC, ST, and OBC students pursuing post-matric courses with family income below ₹2,500,000.");
            schemes.add(s2);
        }

        return schemes;
    }

    @Tool(description = "Estimates the approximate duration required to raise the target amount based on typical platform statistics.")
    public String estimateFundingTimeline(double targetAmount) {
        if (targetAmount <= 50000.0) {
            return "Estimated 3 to 7 days. Low target amounts are usually funded rapidly within your close social circles.";
        }
        if (targetAmount <= 200000.0) {
            return "Estimated 10 to 20 days. Requires sharing across messaging groups and social media channels.";
        }
        if (targetAmount <= 500000.0) {
            return "Estimated 20 to 40 days. Involves community outreach and active updates on the campaign page.";
        }
        return "Estimated 45 to 90 days. Large funding targets require sustained outreach, corporate sponsorship, or platform highlight features.";
    }
}
