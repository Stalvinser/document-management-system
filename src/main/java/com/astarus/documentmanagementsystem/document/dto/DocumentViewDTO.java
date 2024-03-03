package com.astarus.documentmanagementsystem.document.dto;

import com.astarus.documentmanagementsystem.document.entity.Document;
import com.astarus.documentmanagementsystem.document.entity.File;

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
        File file = document.getFile();
        return new DocumentViewDTO(
                document.getId(),
                document.getName(),
                document.getDate(),
                document.getAppUser().getFullName(),
                document.getDescription(),
                file != null ? file.getId() : null,
                file != null ? file.getFileName() : null,
                file != null ? file.getUuid() : null,
                file != null ? file.getContentType() : null
        );
    }

}
