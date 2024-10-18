package com.pzl.service;

import com.pzl.entity.PageResult;
import com.pzl.pojo.CheckItem;

import java.util.List;

public interface CheckItemService {
    //增加
    void add(CheckItem checkItem);

    //分页查询
    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

    //删除
    void deleteById(Integer id);

    //编辑
    void edit(CheckItem checkItem);

    //编辑时回显检查项信息
    CheckItem findById(Integer id);

    //查询所有(用于CheckGroup中展示检查项列表)
    List<CheckItem> findAll();
}
