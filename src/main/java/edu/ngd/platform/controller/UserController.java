package edu.ngd.platform.controller;

import edu.ngd.platform.model.User;
import edu.ngd.platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 用户中心控制器
 */
@Controller
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    /**
     * 跳转到个人中心页面
     * @param model 模型对象
     * @param session HTTP会话
     * @return 个人中心页面视图
     */
    @GetMapping("/center")
    public String userCenter(Model model, HttpSession session) {
        // 从会话中获取用户信息
        Object user = session.getAttribute("user");
        model.addAttribute("user", user);
        return "user/center";
    }
    
    /**
     * 更新收货信息
     * @param phone 联系电话
     * @param session HTTP会话
     * @return 重定向到个人中心页面
     */
    @PostMapping("/updateAddress")
    public String updateAddress(@RequestParam String phone,
                               HttpSession session) {
        // 从会话中获取用户信息
        User user = (User) session.getAttribute("user");
        if (user != null) {
            // 更新用户信息
            user.setPhone(phone);
            userService.updateById(user);
            // 更新会话中的用户信息
            session.setAttribute("user", user);
        }
        return "redirect:/user/center";
    }
    
    /**
     * 更新个人信息
     * @param phone 联系电话
     * @param session HTTP会话
     * @return 重定向到个人中心页面
     */
    @PostMapping("/updateInfo")
    public String updateInfo(@RequestParam String phone,
                            HttpSession session) {
        // 从会话中获取用户信息
        User user = (User) session.getAttribute("user");
        if (user != null) {
            // 更新用户信息
            user.setPhone(phone);
            userService.updateById(user);
            // 更新会话中的用户信息
            session.setAttribute("user", user);
        }
        return "redirect:/user/center";
    }
    
    /**
     * 更新密码
     * @param password 原密码
     * @param newPassword 新密码
     * @param confirmPassword 确认密码
     * @param session HTTP会话
     * @return 重定向到个人中心页面
     */
    @PostMapping("/updatePassword")
    public String updatePassword(@RequestParam String password,
                                @RequestParam String newPassword,
                                @RequestParam String confirmPassword,
                                HttpSession session) {
        // 从会话中获取用户信息
        User user = (User) session.getAttribute("user");
        if (user != null) {
            // 验证原密码
            if (user.getPassword().equals(password)) {
                // 验证新密码和确认密码是否一致
                if (newPassword.equals(confirmPassword)) {
                    // 更新密码
                    user.setPassword(newPassword);
                    userService.updateById(user);
                    // 更新会话中的用户信息
                    session.setAttribute("user", user);
                }
            }
        }
        return "redirect:/user/center";
    }
}