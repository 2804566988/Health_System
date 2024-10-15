package com.pzl.service;

import com.pzl.entity.PageResult;
import com.pzl.pojo.CheckItem;

public interface CheckItemService {
    public void add(CheckItem checkItem);
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);
}
