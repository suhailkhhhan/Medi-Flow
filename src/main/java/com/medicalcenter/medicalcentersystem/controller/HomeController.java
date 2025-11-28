package com.medicalcenter.medicalcentersystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    /**
     * This method handles requests to the homepage URL ("/")
     * and returns the "index.html" template.
     */
    @GetMapping("/")
    public String showHomePage() {
        return "index";
    }

    /**
     * This method handles requests to the login URL ("/login")
     * and returns the "login.html" template.
     */
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }
}