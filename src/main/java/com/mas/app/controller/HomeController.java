package com.mas.app.controller;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mas.app.model.DocumentInfo;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String viewHomePage(Model model) {
      var documentInfo = new DocumentInfo();
      model.addAttribute("documentInfo", documentInfo);
      model.addAttribute("serverTime", LocalDateTime.now());
        return "index";
    }

    @PostMapping("/analyze")
    public String analyzeURL(@ModelAttribute DocumentInfo documentInfo, Model model) {
      System.out.println("Analyze URL");
      System.out.println(documentInfo.getUrl());
        return "redirect:/";
    }
}
