# Spring AI & Business Tools Integration Status

This document provides a precise status update of what has been completed, what is left (pending), and how we plan to address outstanding issues.

## User Review Required

> [!WARNING]
> A critical logical bug was discovered in `UserDocumentRepository.java`. The JPA query uses `u.id = :userId` instead of `u.user_id = :userId`, which will cause `EligibilityTool` to check the wrong document or fail if the document primary key ID does not match the user ID. We should update this query.

> [!IMPORTANT]
> The path for RAG knowledge ingestion (`fundforbharat.knowledge.directory`) is hardcoded to a local OneDrive directory (`C:/Users/arman/OneDrive/Desktop/NGO guidelines`) in `application.properties`. For portability across different machines, this path should be updated to a relative directory within the workspace, e.g., `src/main/resources/knowledge/`, and we should place the PDF documents there.

> [!NOTE]
> The application API provider has been updated to Groq using its OpenAI-compatible endpoint. The `src/main/resources/application.properties` file has been added to `.gitignore` and untracked from Git history to allow local key configuration without risking exposures.

---

## Open Questions

> [!NOTE]
> 1. Do we have the PDF files for NGO guidelines available to move to a workspace-relative directory (e.g. `src/main/resources/knowledge/`)?
> 2. Should we update `DonationTool.java` to return structured DTOs or lists of maps instead of raw `Object[]` for cleaner LLM model tool parsing?

---

## Current Status (Precisely What Is Completed and Left)

### Completed Features

1. **Dependency Infrastructure**:
   - Integrated Spring AI BOM version `1.0.0-M6`.
   - Added `spring-ai-openai-spring-boot-starter` (configured to use OpenAI-compatible Groq endpoint).
   - Added `spring-ai-pgvector-store-spring-boot-starter` and `spring-ai-pdf-document-reader`.
2. **AI Provider Transition**:
   - Configured Groq (`llama-3.1-70b-versatile`) and Groq embeddings (`nomic-embed-text-v1.5`) via the OpenAI compatibility layer.
3. **Git History & Local Key Security**:
   - Added `src/main/resources/application.properties` to `.gitignore` and untracked it from Git to allow local key configuration without risking exposures.
4. **AI Chat Core Service**:
   - Implemented `AiChatService` with predefined platform system instructions.
   - Integrated `QuestionAnswerAdvisor` using PGVector store for contextual RAG capabilities.
5. **API Exposure & Security**:
   - Created `AiChatController` with the `POST /api/ai/chat` endpoint.
   - Updated `SecurityConfig` to permit all access to `/api/ai/**` endpoints.
6. **Knowledge Ingestion Service**:
   - Implemented `KnowledgeIngestionService` which checks existing vector store tables and automatically processes PDF documents into vector chunks on application startup.
7. **Business Database Tools**:
   - `CampaignTool`: Query top funded campaigns, active home page campaigns, and campaign detail lookups.
   - `DonationTool`: Fetch donations by campaign ID, get user donation histories, and calculate donation totals.
   - `EligibilityTool`: Validate KYC eligibility of a user, detail mandatory documentation list based on campaign category (medical, education, etc.), and enforce approval rules on campaign limits.
   - `FinancialHelpTool`: Step-by-step guides for campaigns, matching government financial schemes, and estimating timeline expectations.
   - `DocumentGuidanceTool`: Look up campaign verification documents and status-specific guidelines (e.g., handling rejected documents).

### Left / Outstanding Items

1. **Repository Bug Fix**:
   - Update `UserDocumentRepository.java` to match user documents using the correct column name `user_id` instead of `id`.
2. **Knowledge Ingestion Path Portability**:
   - Relocate the target PDF directory to a relative project directory and update `application.properties`.
3. **Tool Serialization Improvement**:
   - Refactor `DonationTool.java` and relevant queries to return custom DTOs or key-value structures rather than raw `Object[]`.

---

## Proposed Changes

### Repository & Model Layer

#### [MODIFY] [UserDocumentRepository.java](file:///d:/GolokaIT/FFB/FundforBharat-Server/src/main/java/com/example/demo/repository/UserDocumentRepository.java)
- Correct query JPQL to map to `u.user_id` instead of `u.id`.

### Configuration Layer

#### [MODIFY] [.gitignore](file:///d:/GolokaIT/FFB/FundforBharat-Server/.gitignore)
- Ignore `src/main/resources/application.properties` from tracking.

#### [MODIFY] [application.properties](file:///d:/GolokaIT/FFB/FundforBharat-Server/src/main/resources/application.properties)
- Configure Groq endpoints and models: `base-url=https://api.groq.com/openai/v1`, model=`llama-3.1-70b-versatile`, embedding model=`nomic-embed-text-v1.5`.
- Define portable knowledge directory: `fundforbharat.knowledge.directory=src/main/resources/knowledge`

---

## Verification Plan

### Automated Tests
- [x] Verified build compilation and application context load using Java 22:
  ```powershell
  $env:JAVA_HOME = "C:\Users\arman\.jdks\openjdk-22.0.1"; .\mvnw.cmd clean compile test
  ```
  *(Status: Passed successfully, context loaded without errors)*

### Manual Verification
- Test endpoint execution using Postman:
  - `POST http://localhost:8080/api/ai/chat` with body:
    ```json
    { "message": "What is the KYC status of user 1?" }
    ```
