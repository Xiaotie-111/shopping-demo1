package edu.ngd.platform.controller;

import edu.ngd.platform.model.User;
import edu.ngd.platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * 登录控制器
 */
@Controller
public class LoginController {
    
    @Autowired
    private UserService userService;
    
    /**
     * 跳转到登录页面（已废弃，保留兼容）
     * @return 登录页面视图
     */
    @GetMapping("/old-index")
    public String oldIndex() {
        return "redirect:/login";
    }
    
    /**
     * 跳转到登录页面
     * @return 登录页面视图
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    /**
     * 处理登录请求
     * @param username 用户名
     * @param password 密码
     * @param userType 用户类型（admin/user）
     * @param session HTTP会话
     * @return 登录成功跳转到主页面，失败返回登录页面并显示错误信息
     */
    @PostMapping("/login")
    public String doLogin(@RequestParam String username, @RequestParam String password, @RequestParam(defaultValue = "admin") String userType, HttpSession session) {
        // 调用用户服务进行登录验证
        User user = userService.login(username, password, userType);
        
        if (user != null) {
            // 登录成功，将用户信息存入会话
            session.setAttribute("user", user);
            
            // 根据用户角色确定跳转地址
            if ("admin".equals(user.getRole())) {
                // 管理员登录，跳转到电商管理系统首页
                return "redirect:/index";
            } else {
                // 普通用户登录，跳转到商品浏览界面
                return "redirect:/product/browse";
            }
        } else {
            // 登录失败，返回登录页面并显示错误信息
            return "redirect:/login?error";
        }
    }
    
    /**
     * 跳转到主页面
     * @return 主页面视图
     */
    @GetMapping("/index")
    public String index() {
        // 允许直接访问首页，不检查登录状态
        return "index";
    }
    
    /**
     * 跳转到注册页面
     * @return 注册页面视图
     */
    @GetMapping("/register")
    public String register() {
        return "register";
    }
    
    /**
     * 处理注册请求
     * @param user 用户注册信息
     * @return 注册成功跳转到登录页面，失败返回注册页面并显示错误信息
     */
    @PostMapping("/register")
    public String doRegister(User user) {
        try {
            // 调用用户服务进行注册
            boolean success = userService.register(user);
            if (success) {
                // 注册成功，跳转到登录页面
                return "redirect:/login?register=success";
            } else {
                // 注册失败，返回注册页面并显示错误信息
                return "redirect:/register?error";
            }
        } catch (Exception e) {
            // 处理注册异常
            if (e.getMessage().contains("用户名")) {
                return "redirect:/register?error=username";
            } else if (e.getMessage().contains("邮箱")) {
                return "redirect:/register?error=email";
            }
            return "redirect:/register?error";
        }
    }
    
    /**
     * 处理退出登录请求
     * @param session HTTP会话
     * @return 跳转到登录页面
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // 清除会话中的用户信息
        session.invalidate();
        return "redirect:/login";
    }
}