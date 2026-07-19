package com.example.demo.ai;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class KnowledgeIngestionService implements CommandLineRunner {

    private final VectorStore vectorStore;
    private final JdbcTemplate jdbcTemplate;

    @Value("${fundforbharat.knowledge.directory}")
    private String knowledgeDirectoryPath;

    @Autowired
    public KnowledgeIngestionService(VectorStore vectorStore, JdbcTemplate jdbcTemplate) {
        this.vectorStore = vectorStore;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) {
        try {
            // Check if vector store table exists and has documents to avoid duplicate ingestion on restart
            boolean alreadyIngested = false;
            try {
                Integer count = jdbcTemplate.queryForObject("SELECT count(*) FROM vector_store", Integer.class);
                if (count != null && count > 0) {
                    alreadyIngested = true;
                    System.out.println("Vector store already contains " + count + " document chunks. Skipping PDF ingestion.");
                }
            } catch (Exception e) {
                // Table might not exist yet; PgVectorStore will initialize it on first add() call
                System.out.println("Vector store table not found or empty. Proceeding with fresh PDF ingestion.");
            }

            if (alreadyIngested) {
                return;
            }

            File dir = new File(knowledgeDirectoryPath);
            if (!dir.exists() || !dir.isDirectory()) {
                System.err.println("Knowledge directory does not exist or is not a directory: " + knowledgeDirectoryPath);
                return;
            }

            File[] files = dir.listFiles((d, name) -> name.toLowerCase().endsWith(".pdf"));
            if (files == null || files.length == 0) {
                System.out.println("No PDF files found in knowledge directory: " + knowledgeDirectoryPath);
                return;
            }

            System.out.println("Starting knowledge ingestion from: " + knowledgeDirectoryPath);
            List<Document> allChunks = new ArrayList<>();
            TokenTextSplitter splitter = new TokenTextSplitter(800, 150, 5, 10000, true);

            for (File file : files) {
                System.out.println("Processing PDF document: " + file.getName());
                Resource resource = new FileSystemResource(file);
                PagePdfDocumentReader reader = new PagePdfDocumentReader(resource);
                
                List<Document> parsedDocs = reader.get();
                // Add source metadata to each chunk
                for (Document doc : parsedDocs) {
                    doc.getMetadata().put("source_filename", file.getName());
                }
                
                List<Document> chunks = splitter.apply(parsedDocs);
                allChunks.addAll(chunks);
                System.out.println("  Split " + file.getName() + " into " + chunks.size() + " text chunks.");
            }

            if (!allChunks.isEmpty()) {
                System.out.println("Ingesting " + allChunks.size() + " total chunks into Neon PostgreSQL vector store...");
                vectorStore.add(allChunks);
                System.out.println("Ingestion completed successfully!");
            }

        } catch (Exception e) {
            System.err.println("Error during knowledge document ingestion: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
