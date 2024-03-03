package com.astarus.documentmanagementsystem.document.controller;

import com.astarus.documentmanagementsystem.document.dto.DocumentCreationDTO;
import com.astarus.documentmanagementsystem.document.dto.DocumentViewDTO;
import com.astarus.documentmanagementsystem.document.entity.DocumentInfoView;
import com.astarus.documentmanagementsystem.document.service.DocumentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/documents")
public class DocumentController {

    private final DocumentService documentService;

    @GetMapping()
    public String listAllDocuments(@RequestParam(name = "sort", defaultValue = "documentDate") String sort,
                                   @RequestParam(name = "dir", defaultValue = "desc") String dir,
                                   Model model) {
        List<DocumentInfoView> documents = documentService.findAllDocuments(sort, dir);
        model.addAttribute("documents", documents);
        model.addAttribute("currentSort", sort);
        model.addAttribute("currentDir", dir);
        return "documents/documents";
    }


    @GetMapping("/add")
    public String addDocument() {
        return "/documents/addDocuments";
    }

    @PostMapping("/add")
    public String addNewDocument(@Valid DocumentCreationDTO documentCreationDTO,
                                 @RequestParam("file") MultipartFile file,
                                 RedirectAttributes redirectAttributes) {
        try {
            documentService.saveDocument(documentCreationDTO, file);
            redirectAttributes.addFlashAttribute("successMessage", "Document uploaded successfully!");
            return "redirect:/documents";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Upload failed: " + e.getMessage());
            return "redirect:/documents";
        }
    }

    @GetMapping("/search")
    public String searchDocument() {
        return "/documents/searchDocuments";
    }


}
