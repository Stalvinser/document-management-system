package com.astarus.documentmanagementsystem.document.share;

import com.astarus.documentmanagementsystem.appuser.AppUser;
import com.astarus.documentmanagementsystem.appuser.AppUserRepository;
import com.astarus.documentmanagementsystem.document.Document;
import com.astarus.documentmanagementsystem.document.DocumentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DocumentShareService {
    private final DocumentRepository documentRepository;
    private final DocumentShareRepository documentShareRepository;
    private final AppUserRepository appUserRepository;

    public void shareDocument(Long documentId, Long userId, boolean canView, boolean canEdit, boolean canDelete) {
        Document document = documentRepository.findById(documentId).orElseThrow(() -> new RuntimeException("Document not found"));
        if (userId != null) {
            AppUser user = appUserRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            DocumentShare share = new DocumentShare();
            share.setDocument(document);
            share.setAppUser(user);
            share.setCanView(canView);
            share.setCanEdit(canEdit);
            share.setCanDelete(canDelete);
            documentShareRepository.save(share);
        } else {
            document.setIsPublic(true);
            documentRepository.save(document);
        }
    }
    public boolean userHasAccess(Long documentId, Long userId) {
        Document document = documentRepository.findById(documentId).orElseThrow(() -> new RuntimeException("Document not found"));
        if (document.getIsPublic() || document.getAppUser().getId().equals(userId)) {
            return true;
        }
        return documentShareRepository.findByDocumentIdAndAppUserId(documentId, userId)
                .map(share -> share.isCanView() || share.isCanEdit() || share.isCanDelete())
                .orElse(false);
    }
}
