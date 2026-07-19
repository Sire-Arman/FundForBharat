package com.example.demo.ai;

import com.example.demo.ai.tools.*;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AiChatService {

    private final ChatClient chatClient;

    @Autowired
    public AiChatService(
            ChatClient.Builder builder,
            VectorStore vectorStore,
            CampaignTool campaignTool,
            DonationTool donationTool,
            EligibilityTool eligibilityTool,
            DocumentGuidanceTool documentGuidanceTool,
            FinancialHelpTool financialHelpTool
    ) {
        this.chatClient = builder
                .defaultSystem("You are a helpful, professional AI assistant for the FundForBharat platform.\n\n" +
                        "Your mission is to support needy victims raising funds, and donors looking to contribute.\n" +
                        "You have access to:\n" +
                        "1. Live Database Tools: Query campaigns, donations, document status, and KYC eligibility.\n" +
                        "2. RAG Knowledge Documents: Retrieve legal guidelines, NGO rules, and platform guides.\n\n" +
                        "When answering user queries, always check relevant RAG documents and invoke matching database tools to obtain accurate details. " +
                        "Be polite, empathetic, and professional in your replies.")
                .defaultAdvisors(new QuestionAnswerAdvisor(vectorStore))
                .defaultTools(campaignTool, donationTool, eligibilityTool, documentGuidanceTool, financialHelpTool)
                .build();
    }

    public String getChatResponse(String userMessage) {
        return chatClient.prompt()
                .user(userMessage)
                .call()
                .content();
    }
}
