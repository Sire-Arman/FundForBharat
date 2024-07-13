package com.example.demo.repository;

import com.example.demo.DTO.DocumentSessionDTO;
import com.example.demo.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.print.Doc;
import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    @Query("Select u from Document u")
    List<Document> findAll();

    @Query("Select u from Document u where u.upload_user = :user_id")
    Document findbyUserId(Long user_id);

}
