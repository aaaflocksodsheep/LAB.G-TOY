package com.labgenomics.practice.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author kaeun0320
 * @apiNote :
 * @since 2024-06-26
 */

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        return "login/login";
    }


    @GetMapping("/registration")
    public String registration(HttpServletRequest request, Model model) {
        return "login/register";
    }

    @GetMapping("/home")
    public String home(HttpServletRequest request, Model model) {
        return "content/home";
    }
}
