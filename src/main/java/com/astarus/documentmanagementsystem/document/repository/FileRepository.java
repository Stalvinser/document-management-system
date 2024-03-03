package com.astarus.documentmanagementsystem.document.repository;

import com.astarus.documentmanagementsystem.document.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
