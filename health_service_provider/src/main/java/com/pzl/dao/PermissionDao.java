package com.pzl.dao;

import com.pzl.pojo.Permission;

import java.util.Set;

public interface PermissionDao {
    Set<Permission> findByRoleId(Integer roleId);
}
