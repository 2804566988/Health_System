package com.pzl.dao;

import com.github.pagehelper.Page;
import com.pzl.pojo.Order;
import com.pzl.pojo.OrderSettingList;

public interface OrderSettingListDao {
    //分页查询
    Page<Order> selectOrderWithMemberAndSetmeal(String queryString);
}
