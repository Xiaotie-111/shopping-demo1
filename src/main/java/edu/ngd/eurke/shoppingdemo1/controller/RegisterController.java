//package edu.ngd.eurke.shoppingdemo1.controller;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import javax.servlet.http.HttpSession;
//import java.util.HashMap;
//import java.util.Map;
//
//@Controller
//public class RegisterController {
//
//    // 模拟用户数据库（实际项目应使用数据库）
//    private static final Map<String, String> USER_CREDENTIALS = new HashMap<>();
//
//    @GetMapping("/register")
//    public String showRegisterPage(@RequestParam(defaultValue = "user") String role, Model model) {
//        // 校验 role 合法性
//        if (!"user".equals(role) && !"merchant".equals(role)) {
//            role = "user";
//        }
//        model.addAttribute("role", role);
//        return "register";
//    }
//
//    @PostMapping("/register")
//    public String handleRegister(
//            @RequestParam String username,
//            @RequestParam String password,
//            @RequestParam(defaultValue = "user") String role,
//            Model model) {
//
//        // 校验 role
//        if (!"user".equals(role) && !"merchant".equals(role)) {
//            role = "user";
//        }
//
//        if (username == null || username.trim().isEmpty() ||
//                password == null || password.trim().isEmpty()) {
//            model.addAttribute("error", "用户名和密码不能为空");
//            model.addAttribute("role", role);
//            return "register";
//        }
//
//        if (USER_CREDENTIALS.containsKey(username)) {
//            model.addAttribute("error", "用户名已存在");
//            model.addAttribute("role", role);
//            return "register";
//        }
//
//        // 注册成功，保存到模拟数据库
//        USER_CREDENTIALS.put(username, password);
//
//        // 自动登录（可选）
//        // 如果你希望注册后自动登录并跳首页，取消注释以下代码：
//        /*
//        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
//        session.setAttribute("username", username);
//        session.setAttribute("role", role);
//        return "redirect:/";
//        */
//
//        // 默认：注册成功后跳转到登录页
//        return "redirect:/login?role=" + role + "&registered=true";
//    }
//}

package edu.ngd.eurke.shoppingdemo1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class RegisterController {

    // 模拟用户数据库（实际项目应使用数据库）
    private static final Map<String, String> USER_CREDENTIALS = new HashMap<>();

    @GetMapping("/register")
    public String showRegisterPage(@RequestParam(defaultValue = "user") String role, Model model) {
        // 校验 role 合法性
        if (!"user".equals(role) && !"merchant".equals(role)) {
            role = "user";
        }
        model.addAttribute("role", role);
        return "register";
    }

    @PostMapping("/register")
    public String handleRegister(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String confirmPassword, // ← 新增：确认密码
            @RequestParam(defaultValue = "user") String role,
            Model model) {

        // 校验 role 合法性
        if (!"user".equals(role) && !"merchant".equals(role)) {
            role = "user";
        }

        // 非空校验
        if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty() ||
                confirmPassword == null || confirmPassword.trim().isEmpty()) {
            model.addAttribute("error", "所有字段都不能为空");
            model.addAttribute("role", role);
            return "register";
        }

        // 密码一致性校验
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "两次输入的密码不一致");
            model.addAttribute("role", role);
            return "register";
        }

        // 用户名重复校验
        if (USER_CREDENTIALS.containsKey(username)) {
            model.addAttribute("error", "用户名已存在");
            model.addAttribute("role", role);
            return "register";
        }

        // 注册成功，保存到模拟数据库（注意：生产环境必须加密存储！）
        USER_CREDENTIALS.put(username, password);

        // 跳转到登录页，并携带角色和注册成功标志
        return "redirect:/login?role=" + role + "&registered=true";
    }
}
