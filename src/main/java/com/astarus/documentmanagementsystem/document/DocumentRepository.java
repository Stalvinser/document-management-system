package com.astarus.documentmanagementsystem.document;

import com.astarus.documentmanagementsystem.document.Document;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.lang.NonNull;
import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    @NonNull
    List<Document> findAll(@NonNull Sort sort);

}
