package com.astarus.documentmanagementsystem.document.share;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentPublicShareRepository extends JpaRepository<DocumentPublicShare, Long> {
}
