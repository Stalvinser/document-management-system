package com.astarus.documentmanagementsystem.document.share;

import com.astarus.documentmanagementsystem.appuser.AppUserService;
import com.astarus.documentmanagementsystem.appuser.UserDTO;
import com.astarus.documentmanagementsystem.document.DocumentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/documents")
public class DocumentShareController {

    private final DocumentService documentService;
    private final DocumentShareService documentShareService;
    private final AppUserService appUserService;



    @GetMapping("/share/{documentId}")
    public String showShareForm(@PathVariable Long documentId, Model model) {
        List<UserDTO> users = UserDTO.convertToDTOList(appUserService.findAll());
        model.addAttribute("users", users);
        model.addAttribute("documentId", documentId);
        model.addAttribute("shareRequest", new ShareRequest());
        return "/documents/share/shareDocuments";
    }

    @PostMapping("/share/{documentId}")
    public String shareDocument(@PathVariable Long documentId, ShareRequest shareRequest, RedirectAttributes redirectAttributes) {
        try {
            if (shareRequest.isShareWithEveryone()) {
                documentShareService.shareDocumentToEveryone(documentId, shareRequest);
            } else {
                documentShareService.shareDocumentToUser(documentId, shareRequest);
            }
            redirectAttributes.addFlashAttribute("successMessage", "Document shared successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error sharing document: " + e.getMessage());
        }
        return "redirect:/documents";
    }

}
