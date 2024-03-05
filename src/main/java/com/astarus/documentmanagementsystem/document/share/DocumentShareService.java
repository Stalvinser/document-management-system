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
    private final DocumentPublicShareRepository documentPublicShareRepository;

    public void shareDocumentToUser(Long documentId, ShareRequest shareRequest) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found"));
        AppUser user = appUserRepository.findById(shareRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        DocumentShare documentShare = new DocumentShare();
        documentShare.setDocument(document);
        documentShare.setCanView(shareRequest.isCanView());
        documentShare.setCanEdit(shareRequest.isCanEdit());
        documentShare.setCanDelete(shareRequest.isCanDelete());
        documentShare.setAppUser(user);
        documentShareRepository.save(documentShare);
        documentRepository.save(document);
    }

    public void shareDocumentToEveryone(Long documentId, ShareRequest shareRequest) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found"));
        DocumentPublicShare documentPublicShare = new DocumentPublicShare();
        documentPublicShare.setDocument(document);
        documentPublicShare.setCanView(shareRequest.isCanView());
        documentPublicShare.setCanEdit(shareRequest.isCanEdit());
        documentPublicShare.setCanDelete(shareRequest.isCanDelete());
        documentPublicShareRepository.save(documentPublicShare);
        documentRepository.save(document);
    }


}
