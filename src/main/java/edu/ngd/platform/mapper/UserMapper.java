package edu.ngd.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.ngd.platform.model.User;

/**
 * 用户Mapper接口
 */
public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据用户名获取用户
     * @param username 用户名
     * @return 用户信息
     */
    User selectByUsername(String username);
    
    /**
     * 根据邮箱获取用户
     * @param email 邮箱
     * @return 用户信息
     */
    User selectByEmail(String email);
}