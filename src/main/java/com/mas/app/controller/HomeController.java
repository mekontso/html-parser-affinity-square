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

  /**
   * This method is used to display the home page
   * @param model Model object for form binding
   * @return index.html
   */
    @RequestMapping("/")
    public String viewHomePage(Model model) {
      model.addAttribute("documentInfo", new DocumentInfo());
        return "index";
    }

    @PostMapping("/analyze")
    public String analyzeURL(@ModelAttribute DocumentInfo documentInfo, Model model) {
      System.out.println("Analyze URL");
      System.out.println(documentInfo.getUrl());
        return "redirect:/";
    }
}
