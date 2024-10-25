package com.pzl.service;

import com.pzl.pojo.User;

/**
 * 用户服务接口
 */
public interface UserService {
    public User findByUsername(String username);
}