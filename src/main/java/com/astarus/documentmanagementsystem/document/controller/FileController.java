package com.astarus.documentmanagementsystem.document.controller;

import com.astarus.documentmanagementsystem.document.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/files")
public class FileController {
    private final FileService fileService;

    @GetMapping("/{fileUuid}")
    public ResponseEntity<?> viewDocument(@PathVariable String fileUuid) throws IOException {
        return fileService.viewFile(fileUuid);
    }
}

