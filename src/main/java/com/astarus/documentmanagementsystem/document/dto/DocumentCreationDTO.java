package com.astarus.documentmanagementsystem.document.dto;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


public record DocumentCreationDTO(
        @NotBlank String name,
        @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
        @NotBlank String description) {
}

