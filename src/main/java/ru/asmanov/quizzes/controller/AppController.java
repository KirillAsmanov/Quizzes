package ru.asmanov.quizzes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller containing methods for navigating the application
 * @author Kirill Asmanov
 * @since 13.10.2020
 */

@Controller
public class AppController {
    @GetMapping("/about")
    public String showAboutPage() {
        return "About";
    }
}