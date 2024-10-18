package com.pzl.service;

import com.pzl.entity.PageResult;
import com.pzl.pojo.CheckGroup;

import java.util.List;

/**
 * 检查组服务接口
 */
public interface CheckGroupService {
    //添加
    void add(CheckGroup checkGroup, Integer[] checkitemIds);

    //分页查询
    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

    //编辑
    void edit(CheckGroup checkGroup, Integer[] checkitemIds);
    //编辑时回显检查项数据
    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);
    //编辑时回显检查组数据
    CheckGroup findById(Integer id);

    //删除
    void deleteById(Integer id);

    //查询所有(用于SetMeal中展示检查组列表)
    List<CheckGroup> findAll();
}
