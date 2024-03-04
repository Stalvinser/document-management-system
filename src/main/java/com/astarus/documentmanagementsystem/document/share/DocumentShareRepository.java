package com.astarus.documentmanagementsystem.document.share;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DocumentShareRepository extends JpaRepository<DocumentShare, Long> {
    Optional<DocumentShare> findByDocumentIdAndAppUserId(Long documentId, Long userId);
}
