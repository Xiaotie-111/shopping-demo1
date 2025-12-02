package edu.ngd.eurke.shoppingdemo1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    private static final Map<String, String> USER_CREDENTIALS = new HashMap<>();
    static {
        USER_CREDENTIALS.put("admin", "123456");
        USER_CREDENTIALS.put("user1", "password");
        USER_CREDENTIALS.put("merchant1", "123456");
    }

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(defaultValue = "user") String role, Model model) {
        model.addAttribute("role", role);
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam(defaultValue = "user") String role,
            HttpSession session) {

        log.info("Login attempt: username={}, role={}", username, role);

        if (username == null || password == null || role == null) {
            return "redirect:/login?error=true&role=user";
        }

        if (USER_CREDENTIALS.containsKey(username) &&
                USER_CREDENTIALS.get(username).equals(password)) {

            session.setAttribute("username", username);
            session.setAttribute("role", role);
            return "redirect:/";
        } else {
            return "redirect:/login?role=" + role + "&error=true";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}