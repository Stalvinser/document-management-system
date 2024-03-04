package com.astarus.documentmanagementsystem.document.view;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DocumentInfoViewRepository extends JpaRepository<DocumentInfoView, Long>,
        JpaSpecificationExecutor<DocumentInfoView> {

    @Query("SELECT v FROM DocumentInfoView v WHERE v.appUserId = :userId " +
            "OR v.documentId IN (SELECT ds.document.id FROM DocumentShare ds WHERE ds.appUser.id = :userId) " +
            "OR v.isPublic = true")
    List<DocumentInfoView> findMyDocuments(@Param("userId") Long userId, Sort sort);

}
