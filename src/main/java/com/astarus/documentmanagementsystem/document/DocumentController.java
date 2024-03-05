package com.astarus.documentmanagementsystem.document;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/documents")
public class DocumentController {

    private final DocumentService documentService;

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
            redirectAttributes.addFlashAttribute("successMessage", "Документ успешно загружен!");
            return "redirect:/documents";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка загрузки документа");
            return "redirect:/documents";
        }
    }

    @GetMapping("/search")
    public String searchDocuments(@RequestParam(required = false) String name,
                                  @RequestParam(required = false) String author,
                                  @RequestParam(required = false) String description,
                                  @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom,
                                  @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo,
                                  @RequestParam(name = "sort", defaultValue = "documentDate") String sort,
                                  @RequestParam(name = "dir", defaultValue = "desc") String dir,
                                  Model model) {
        List<DocumentInfoView> documents = documentService.searchDocuments(name, author, description, dateFrom, dateTo, sort, dir);
        model.addAttribute("documents", documents);
        model.addAttribute("currentSort", sort);
        model.addAttribute("currentDir", dir);
        return "documents/searchDocuments";
    }

    @GetMapping()
    public String listMyDocuments(@RequestParam(name = "sort", defaultValue = "documentDate") String sort,
                                  @RequestParam(name = "dir", defaultValue = "desc") String dir,
                                  Model model) {
        List<DocumentInfoView> documents = documentService.findMyDocuments(sort, dir);
        model.addAttribute("documents", documents);
        model.addAttribute("currentSort", sort);
        model.addAttribute("currentDir", dir);
        return "documents/documents";
    }

}
