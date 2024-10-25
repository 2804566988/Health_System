package com.pzl.dao;

import com.pzl.pojo.User;

public interface UserDao {

    User findByUsername(String username);

}
