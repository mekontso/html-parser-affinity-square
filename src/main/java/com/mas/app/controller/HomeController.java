package com.mas.app.controller;


import com.mas.app.service.AnalyzeHtmlService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mas.app.model.DocumentInfo;

import jakarta.validation.Valid;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final AnalyzeHtmlService analyzeHtmlService;

    /**
     * This method is used to display the home page
     *
     * @param model Model object for form binding
     * @return index.html
     */
    @RequestMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("documentInfo", new DocumentInfo());
        return "index";
    }

    @PostMapping("/analyze")
    public String analyzeURL(@Valid @ModelAttribute DocumentInfo documentInfo, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) return "index";
        DocumentInfo result = analyzeHtmlService.analyzeHtml(documentInfo.getUrl());
        model.addAttribute("documentInfo", result);
        return "index";
    }
}
