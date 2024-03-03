package com.astarus.documentmanagementsystem.document.dto;

import com.astarus.documentmanagementsystem.document.entity.Document;
import com.astarus.documentmanagementsystem.document.entity.FileEntity;

import java.util.Date;

public record DocumentViewDTO(
        Long documentId,
        String name,
        Date date,
        String author,
        String description,
        Long fileId,
        String fileName,
        String uuid,
        String contentType
) {
    public static DocumentViewDTO dtoFromEntity(Document document) {
        FileEntity fileEntity = document.getFile();
        return new DocumentViewDTO(
                document.getId(),
                document.getName(),
                document.getDate(),
                document.getAppUser().getFullName(),
                document.getDescription(),
                fileEntity != null ? fileEntity.getId() : null,
                fileEntity != null ? fileEntity.getFileName() : null,
                fileEntity != null ? fileEntity.getUuid() : null,
                fileEntity != null ? fileEntity.getContentType() : null
        );
    }

}
