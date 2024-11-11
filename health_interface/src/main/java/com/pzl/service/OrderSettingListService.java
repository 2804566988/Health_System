package com.pzl.service;

import com.pzl.entity.PageResult;

public interface OrderSettingListService {
    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);
}
