package com.pzl.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pzl.dao.OrderSettingListDao;
import com.pzl.entity.PageResult;
import com.pzl.pojo.Member;
import com.pzl.service.OrderSettingListService;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderSettingListServiceImpl implements OrderSettingListService {
    @Autowired
    private OrderSettingListDao orderSettingListDao;

    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<Member> page = orderSettingListDao.selectOrderWithMemberAndSetmeal(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

}
