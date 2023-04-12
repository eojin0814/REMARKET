package com.softwareapplication.remarket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/thymeleaf")
public class TymeleafExController {

    @GetMapping(value = "/hi")
    public String typeleafExExample(Model model) {
        model.addAttribute("say", "Hello");
        return "layout/default";
    }
}
