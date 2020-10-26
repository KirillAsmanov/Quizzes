package ru.asmanov.quizzes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {
    @GetMapping("/about")
    public String showAboutPage() {
        return "About";
    }
}