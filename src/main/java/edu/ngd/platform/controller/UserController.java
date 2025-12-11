package edu.ngd.platform.controller;

import edu.ngd.platform.model.User;
import edu.ngd.platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户服务Controller
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    /**
     * 获取所有用户
     * @return 用户列表
     */
    @GetMapping("/list")
    public List<User> list() {
        return userService.list();
    }
    
    /**
     * 根据ID获取用户
     * @param id 用户ID
     * @return 用户信息
     */
    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        return userService.getById(id);
    }
    
    /**
     * 添加用户
     * @param user 用户信息
     * @return 是否添加成功
     */
    @PostMapping("/add")
    public boolean add(@RequestBody User user) {
        return userService.save(user);
    }
    
    /**
     * 更新用户
     * @param user 用户信息
     * @return 是否更新成功
     */
    @PutMapping("/update")
    public boolean update(@RequestBody User user) {
        return userService.updateById(user);
    }
    
    /**
     * 删除用户
     * @param id 用户ID
     * @return 是否删除成功
     */
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return userService.removeById(id);
    }
    
    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @param userType 用户类型
     * @return 用户信息
     */
    @PostMapping("/login")
    public User login(@RequestParam String username, @RequestParam String password, @RequestParam(defaultValue = "USER") String userType) {
        return userService.login(username, password, userType);
    }
    
    /**
     * 根据用户名获取用户
     * @param username 用户名
     * 
     * 



     
     * @return 用户信息
     */
    @GetMapping("/username/{username}")
    public User getByUsername(@PathVariable String username) {
        return userService.getByUsername(username);
    }
}