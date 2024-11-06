package com.pzl.service;

import com.pzl.entity.PageResult;
import com.pzl.pojo.Setmeal;

import java.util.List;

public interface OrderSettingListService {
    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);
}
