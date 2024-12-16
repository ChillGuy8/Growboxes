package com.example.springwebapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    // Главная страница
    @GetMapping("/")
    public String main() {
        return "redirect:/login.html"; // Отобразится файл "index.html" из папки templates
    }

    // Страница входа
    @GetMapping("/login")
    public String login() {
        return "redirect:/login.html";  // Отобразится файл "login.html"
    }

    // Страница регистрации
    @GetMapping("/register")
    public String register() {
        return "redirect:/register.html";   // Отобразится файл "register.html"
    }

    // Страница аккаунта
    @GetMapping("/account")
    public String account() {
        return "redirect:/register.html";  // Отобразится файл "account.html"
    }
}

