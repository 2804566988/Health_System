package com.pzl.dao;

import com.github.pagehelper.Page;
import com.pzl.pojo.CheckGroup;

import java.util.List;
import java.util.Map;

public interface CheckGroupDao {
    //添加
    void add(CheckGroup checkGroup);
    //绑定检查组和检查项的关联关系
    void setCheckGroupAndCheckItem(Map<String,Integer> map);

    //分页查询
    Page<CheckGroup> selectByCondition(String queryString);

    //编辑
    void edit(CheckGroup checkGroup);
    //编辑时回显检查项数据
    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);
    //编辑时回显检查组数据
    CheckGroup findById(Integer id);

    //删除
    void deleteById(Integer id);
    //查询关联表判断是否被套餐引用
    long findCountByCheckGroupId(Integer checkitemId);
    //删除关联表中引用检查项的数据
    void deleteAssociation(Integer id);


    //查询所有(用于SetMeal中展示检查组列表)
    List<CheckGroup> findAll();

}
