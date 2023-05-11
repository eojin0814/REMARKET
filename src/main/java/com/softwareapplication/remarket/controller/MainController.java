package com.softwareapplication.remarket.controller;

import com.softwareapplication.remarket.domain.User;
import com.softwareapplication.remarket.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class MainController {

    private final UserService userService;
    @GetMapping()
    public String home(Model model, @SessionAttribute(name = "email", required = false) String email) {

        User loginUser = userService.getLoginUserByEmail(email);

        if(loginUser != null) {
            model.addAttribute("email", loginUser.getEmail());
        }
        return "layout/default_main";
    }
}
