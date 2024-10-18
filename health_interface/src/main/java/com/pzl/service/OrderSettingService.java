package com.pzl.service;

import com.pzl.pojo.OrderSetting;
import java.util.List;
import java.util.Map;

public interface OrderSettingService {
    //Excel文件上传，并解析文件内容保存到数据库
    void add(List<OrderSetting> list);
    //根据日期查询预约设置数据(获取指定日期所在月份的预约设置数据)
    List<Map<String,?>> getOrderSettingByMonth(String date);

    void editNumberByDate(OrderSetting orderSetting);

}
