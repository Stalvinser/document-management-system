package com.astarus.documentmanagementsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String mainPage() {
        return "home";
    }
    @GetMapping("/document")
    public String addDocument() {
        return "addDocument";
    }
    @GetMapping("/search")
    public String searchDocument() {
        return "searchDocument";
    }
}

