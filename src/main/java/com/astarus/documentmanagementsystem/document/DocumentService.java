package com.astarus.documentmanagementsystem.document;

import com.astarus.documentmanagementsystem.appuser.AppUser;
import com.astarus.documentmanagementsystem.appuser.AppUserRepository;
import com.astarus.documentmanagementsystem.document.view.DocumentInfoView;
import com.astarus.documentmanagementsystem.document.file.FileEntity;
import com.astarus.documentmanagementsystem.document.view.DocumentInfoViewRepository;
import com.astarus.documentmanagementsystem.document.file.FileRepository;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import org.springframework.data.domain.Sort;

@Service
@AllArgsConstructor
public class DocumentService {

    private final AppUserRepository appUserRepository;
    private final DocumentRepository documentRepository;
    private final FileRepository fileRepository;
    private final DocumentInfoViewRepository documentInfoViewRepository;

    @Transactional
    public void saveDocument(DocumentCreationDTO documentCreationDTO, MultipartFile multipartFile) throws IOException {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser user = appUserRepository.findByEmailIgnoreCase(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        String uuid = UUID.randomUUID().toString();
        String originalFilename = multipartFile.getOriginalFilename();

        String directoryPath = "files";
        Path directory = Paths.get(directoryPath);
        if (!Files.exists(directory)) {
            Files.createDirectories(directory);
        }
        Path filePath = directory.resolve(uuid);

        try {
            multipartFile.transferTo(filePath);

            Document document = new Document();
            document.setName(documentCreationDTO.name());
            document.setDate(documentCreationDTO.date());
            document.setDescription(documentCreationDTO.description());
            document.setAppUser(user);
            Document savedDocument = documentRepository.save(document);

            FileEntity fileEntity = new FileEntity();
            fileEntity.setFileName(originalFilename);
            fileEntity.setUuid(uuid);
            fileEntity.setContentType(multipartFile.getContentType());
            fileEntity.setDocument(savedDocument);
            fileRepository.save(fileEntity);
        } catch (Exception e) {
            Files.deleteIfExists(filePath);
            throw new RuntimeException("Failed to save document and file: " + e.getMessage(), e);
        }
    }

    public List<DocumentInfoView> findMyDocuments(String sort, String dir) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Sort.Direction direction = Sort.Direction.fromString(dir);
        Sort sortSpecification = Sort.by(direction, sort);

        Optional<AppUser> userOptional = appUserRepository.findByEmailIgnoreCase(userEmail);
        if (userOptional.isEmpty()) {
            return Collections.emptyList();
        }
        Long userId = userOptional.get().getId();

        return documentInfoViewRepository.findMyDocuments(userId, sortSpecification);
    }


    public List<DocumentInfoView> searchDocuments(String name, String author,
                                                  String description,
                                                  Date dateFrom,
                                                  Date dateTo,
                                                  String sort,
                                                  String dir) {
        Specification<DocumentInfoView> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("documentName")), "%" + name.toLowerCase() + "%"));
            }

            if (author != null && !author.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("authorName")), "%" + author.toLowerCase() + "%"));
            }

            if (description != null && !description.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("description")), "%" + description.toLowerCase() + "%"));
            }

            if (dateFrom == null && dateTo != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("documentDate"), dateTo));
            } else if (dateFrom != null && dateTo == null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("documentDate"), dateFrom));
            } else if (dateFrom != null && dateTo != null) {

                predicates.add(cb.between(root.get("documentDate"), dateFrom, dateTo));
            }

            jakarta.persistence.criteria.Path<Object> sortBy = root.get(sort);
            Order order = "asc".equalsIgnoreCase(dir) ? cb.asc(sortBy) : cb.desc(sortBy);
            query.orderBy(order);

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return documentInfoViewRepository.findAll(spec);
    }


}
