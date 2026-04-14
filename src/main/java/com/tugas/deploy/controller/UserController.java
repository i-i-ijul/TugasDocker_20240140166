package com.tugas.deploy.controller;

import com.tugas.deploy.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    // User hardcoded untuk login
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "1234";

    // 1. Halaman Login
    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String username,
                               @RequestParam String password,
                               HttpSession session) {
        if (USERNAME.equals(username) && PASSWORD.equals(password)) {
            session.setAttribute("loggedIn", true);
            session.setAttribute("username", username);
            return "redirect:/home";
        }
        return "redirect:/login?error";
    }

    // 2. Halaman Home (sebelum input)
    @GetMapping("/home")
    public String showHome(HttpSession session) {
        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/login";
        }
        return "home";
    }

    // 3. Halaman Form
    @GetMapping("/form")
    public String showForm(HttpSession session) {
        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/login";
        }
        return "form";
    }

    @PostMapping("/submit")
    public String submitForm(@RequestParam String nama,
                             @RequestParam String email,
                             HttpSession session) {
        // Simpan data ke session
        session.setAttribute("nama", nama);
        session.setAttribute("email", email);
        return "redirect:/result";
    }

    // 4. Halaman Home Setelah Input
    @GetMapping("/result")
    public String showResult(HttpSession session, Model model) {
        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/login";
        }

        // Ambil dari session
        Object nama = session.getAttribute("nama");
        Object email = session.getAttribute("email");

        // Kirim ke view
        model.addAttribute("nama", nama);
        model.addAttribute("email", email);

        return "result";
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}