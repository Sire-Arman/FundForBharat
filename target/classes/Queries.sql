-- Database Queries Documentation (PostgreSQL)

-- Table of Contents
-- 1. Campaign Repository
-- 2. Document Repository
-- 3. Donation Repository
-- 4. User Repository
-- 5. User Documents Repository


-- 1. Campaign Repository

-- ## DDL (Data Definition Language)
-- ### Create Campaign Table
CREATE TABLE Campaign (
                          id BIGSERIAL PRIMARY KEY,
                          user_id BIGINT NOT NULL,
                          title VARCHAR(255) NOT NULL,
                          description TEXT,
                          target_amount DOUBLE PRECISION NOT NULL,
                          toBeShown BOOLEAN DEFAULT FALSE,
                          amount_raised DOUBLE PRECISION DEFAULT 0.0,
                          start_date DATE,
                          end_date DATE,
                          createdAt TIMESTAMP,
                          updatedAt TIMESTAMP
);

-- DQL (Data Query Language)

    SELECT u FROM Campaign u ORDER BY u.amount_raised DESC LIMIT 10;
--     List<Campaign> findTopByOrderByAmountRaisedDesc(Pageable pageable);


    SELECT u from Campaign u where u.id = :Id;
--     Optional<Campaign> findCampaignById(Long Id);


    Select u from Campaign u where u.toBeShown=true;
--     List<CampaignWithDonationsDTO> findHomePageCampaigns();


    SELECT c.id, c.user_id, c.title, c.description, c.start_date, c.end_date,
            c.target_amount, c.amount_raised, c.createdAt, c.updatedAt, c.toBeShown,
            d.id, d.user_id, d.campaign_id, d.alias_name, d.amount, d.mode_of_payment, d.donation_date,
            doc.id, doc.Doc_type, doc.Doc_url, doc.campaign_id, doc.remarks, doc.status, doc.upload_date, doc.upload_user
            FROM Campaign c
            LEFT JOIN Donation d on d.campaign_id = c.id
            LEFT JOIN Document doc on doc.campaign_id = c.id;
--     List<Object[]> findAllCampaignsWithDonationsAndDocuments();

    SELECT c.id, c.user_id, c.title, c.description, c.start_date, c.end_date,
            c.target_amount, c.amount_raised, c.createdAt, c.updatedAt, c.toBeShown,
            d.id, d.user_id, d.campaign_id, d.alias_name, d.amount, d.mode_of_payment, d.donation_date,
            doc.id, doc.Doc_type, doc.Doc_url, doc.campaign_id, doc.remarks, doc.status, doc.upload_date, doc.upload_user
            FROM Campaign c
            LEFT JOIN Donation d on d.campaign_id = c.id
            LEFT JOIN Document doc on doc.campaign_id = c.id where c.id = :id;
--     Object[] findCampaignsWithDonationsAndDocuments(Long id);


-- 2. Document Repository

-- ## DDL (Data Definition Language)
-- ### Create Document Table

CREATE TABLE Document (
                          id BIGSERIAL PRIMARY KEY,
                          Doc_type VARCHAR(255) NOT NULL,
                          Doc_url VARCHAR(255) NOT NULL,
                          campaign_id BIGINT NOT NULL,
                          upload_date DATE NOT NULL,
                          upload_user BIGINT NOT NULL,
                          status VARCHAR(50) NOT NULL,
                          remarks VARCHAR(255),
                          FOREIGN KEY (campaign_id) REFERENCES Campaign(id)
);

-- Create an enum type for the status column
    CREATE TYPE Status AS ENUM ('PENDING', 'APPROVED', 'REJECTED', 'UNDER_REVIEW', 'EXPIRED', 'ARCHIVED');

-- DQL (Data Query Language)
    Select u from Document u;
--     List<Document> findAll();

    Select u from Document u where u.upload_user = :user_id;
--     Optional<Document> findByUserId(Long user_id);

    select u from Document u where u.campaign_id = :campaignId;
--     List<Document> findByCampaignId(Long campaignId);


-- 3. Donation Repository
          -- ## DDL (Data Definition Language)
-- ### Create Donation Table
CREATE TABLE Donation (
                          id BIGSERIAL PRIMARY KEY,
                          user_id BIGINT,
                          alias_name VARCHAR(255),
                          campaign_id BIGINT NOT NULL,
                          amount DOUBLE PRECISION NOT NULL,
                          mode_of_payment VARCHAR(50) NOT NULL,
                          donation_date DATE NOT NULL,
                          FOREIGN KEY (campaign_id) REFERENCES Campaign(id),
                          CONSTRAINT check_user_or_alias CHECK (user_id IS NOT NULL OR alias_name IS NOT NULL)
);

-- Create an enum type for the mode_of_payment column
    CREATE TYPE PaymentMode AS ENUM ('CASH', 'CREDIT_CARD', 'DEBIT_CARD', 'NET_BANKING', 'UPI', 'WALLET');

-- DQL (Data Query Language)
    SELECT u FROM Donation u WHERE u.user_id = :UserId;
--     List<Donation> findDonationsByUserId(Long UserId);

    Select u from Donation u;
--     List<Donation> findAllDonations();

    Select u from Donation u where u.campaign_id = :CampaignId;
--     List<Donation> findDonationsByCampaignId(Long CampaignId);

-- 4. User Repository
-- ## DDL (Data Definition Language)
-- ### Create User Table
CREATE TABLE "user" (
                      id BIGSERIAL PRIMARY KEY,
                      username VARCHAR(255) NOT NULL UNIQUE,
                      email VARCHAR(255) NOT NULL UNIQUE,
                      password VARCHAR(255) NOT NULL,
                      fullName VARCHAR(255) NOT NULL,
                      address VARCHAR(255),
                      doc_id BIGINT,
                      createdAt TIMESTAMP,
                      updatedAt TIMESTAMP
);

-- Add indexes for frequently queried columns
    CREATE INDEX idx_username ON "user"(username);
    CREATE INDEX idx_email ON "user"(email);

-- DQL (Data Query Language)
    SELECT u FROM User u WHERE u.email = :email AND u.password = :password;
--     User findUserByEmailUser(String email, String password);


    SELECT u FROM User u WHERE u.email = :email;
--     User findByEmail(@Param("email") String email);

-- 5. User Document Repository
-- ## DDL (Data Definition Language)
-- ### Create UserDocument Table
CREATE TABLE UserDocument (
                          id BIGSERIAL PRIMARY KEY,
                          alias_name VARCHAR(255),
                          user_id BIGINT,
                          doc_type VARCHAR(255) NOT NULL,
                          doc_url VARCHAR(255) NOT NULL,
                          status VARCHAR(255) NOT NULL,
                          remarks VARCHAR(255),
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Indexes
    CREATE INDEX idx_userdoc_user_id ON UserDocument(user_id);
    CREATE INDEX idx_userdoc_doc_type ON UserDocument(doc_type);
    CREATE INDEX idx_userdoc_status ON UserDocument(status);

-- DQL (Data Query Language)
    Select u from UserDocument u;
--     List<UserDocument> findAll();
    select u from UserDocument u where u.id = :id;
--     Optional<UserDocument> findById(Long id);

    select u from UserDocument u where u.id = :userId;
--     UserDocument findByUserId(Long userId);
