package edu.ngd.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.ngd.platform.model.User;

/**
 * 用户服务接口
 */
public interface UserService extends IService<User> {
    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @param userType 用户类型
     * @return 登录成功返回用户信息，否则返回null
     */
    User login(String username, String password, String userType);
    
    /**
     * 根据用户名获取用户
     * @param username 用户名
     * @return 用户信息
     */
    User getByUsername(String username);
    
    /**
     * 用户注册
     * @param user 用户注册信息
     * @return 注册成功返回true，否则返回false
     */
    boolean register(User user);
}