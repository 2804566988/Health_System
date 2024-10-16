package com.pzl.service;

import com.pzl.entity.PageResult;
import com.pzl.pojo.CheckItem;

import java.util.List;

public interface CheckItemService {
    void add(CheckItem checkItem);
    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);
    void delete(Integer id);
    void edit(CheckItem checkItem);
    CheckItem findById(Integer id);
    List<CheckItem> findAll();
}
