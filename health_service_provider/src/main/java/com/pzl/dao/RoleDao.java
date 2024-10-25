package com.pzl.dao;

import com.pzl.pojo.Role;

import java.util.Set;

public interface RoleDao {
    Set<Role> findByUserId(Integer id);
}
