package com.mas.app.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mas.app.model.DocumentInfo;

import jakarta.validation.Valid;

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
    public String analyzeURL(@Valid @ModelAttribute DocumentInfo documentInfo, BindingResult bindingResult, Model model) {
      System.out.println("Analyzing URL....");
      if(bindingResult.hasErrors()) return "index";
        return "redirect:/";
    }
}
