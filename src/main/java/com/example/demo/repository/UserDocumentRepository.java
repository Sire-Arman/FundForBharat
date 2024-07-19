package com.example.demo.repository;

import com.example.demo.model.UserDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Indexed;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserDocumentRepository extends JpaRepository<UserDocument, Long> {
    @Query("Select u from UserDocument u")
    List<UserDocument> findAll();
    @Query("select u from UserDocument u where u.id = :id")
    Optional<UserDocument> findById(Long id);

    @Query("select u from UserDocument u where u.id = :userId")
    UserDocument findByUserId(Long userId);

}
