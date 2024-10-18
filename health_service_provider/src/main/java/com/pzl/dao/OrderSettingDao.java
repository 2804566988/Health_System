package com.pzl.dao;

import com.pzl.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingDao {
    long findCountByOrderDate(Date orderDate);

    void editNumberByOrderDate(OrderSetting orderSetting);

    void add(OrderSetting orderSetting);

    //根据日期查询预约设置数据(获取指定日期所在月份的预约设置数据)
    List<OrderSetting> getOrderSettingByMonth(Map<String, String> map);
}
