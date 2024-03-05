package com.astarus.documentmanagementsystem.document;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "document_info_view")
public class DocumentInfoView {
    @Id
    @Column(name = "document_id")
    private Long documentId;

    @Column(name = "app_user_id")
    private Long appUserId;

    @Column(name = "document_name")
    private String documentName;

    @Column(name = "document_date")
    private Date documentDate;

    @Column(name = "description")
    private String description;

    @Column(name = "author_name")
    private String authorName;

    @Column(name = "file_id")
    private Long fileId;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_uuid")
    private String fileUuid;

    @Column(name = "content_type")
    private String contentType;
}
