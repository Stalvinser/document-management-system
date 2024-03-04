package com.astarus.documentmanagementsystem.document;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


public record DocumentCreationDTO(
        @NotBlank(message = "Name is required")
        @NotBlank String name,
        @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
        @NotBlank String description) {
}

