package com.astarus.documentmanagementsystem.document.repository;

import com.astarus.documentmanagementsystem.document.entity.DocumentInfoView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DocumentInfoViewRepository extends JpaRepository<DocumentInfoView, Long>, JpaSpecificationExecutor<DocumentInfoView> {
}
