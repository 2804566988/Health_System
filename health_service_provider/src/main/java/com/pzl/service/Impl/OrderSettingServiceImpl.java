package com.pzl.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pzl.dao.OrderSettingDao;
import com.pzl.pojo.OrderSetting;
import com.pzl.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OrderSettingDao orderSettingDao;

    //批量添加
    public void add(List<OrderSetting> list) {
        if (list != null && list.size() > 0) {
            for (OrderSetting orderSetting : list) {
                //检查此数据（日期）是否存在
                long count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
                if (count > 0) {
                    //已经存在，执行更新操作
                    orderSettingDao.editNumberByOrderDate(orderSetting);
                } else {
                    //不存在，执行添加操作
                    orderSettingDao.add(orderSetting);
                }
            }
        }
    }

    public List<Map<String, ?>> getOrderSettingByMonth(String date) {
        //将 2024-10 按"-"分割为年月
        String[] split = date.split("-");
        //每月第一天
        String dateBegin = date + "-1";
        //每月最后一天（需要根据不同情况判断，大小月和闰平年）
        String dateEnd = null;
        //获取年份
        int year = Integer.parseInt(split[0]);
        //获取月份
        String month = split[1];

        //如果是mysql8.0数据库，要考虑当前月份最后一天是多少号
        switch (month) {
            case "1":
            case "3":
            case "5":
            case "7":
            case "8":
            case "10":
            case "12":
                dateEnd = date + "-31";
                break;
            case "4":
            case "6":
            case "9":
            case "11":
                dateEnd = date + "-30";
                break;
            case "2":
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                    dateEnd = date + "-29";
                } else {
                    dateEnd = date + "-28";
                }
        }

        //将该月第一天和最后一天传入，返回该月所有日期的预约情况列表
        Map<String, String> map = new HashMap<>();
        map.put("dateBegin", dateBegin);
        map.put("dateEnd", dateEnd);
        List<OrderSetting> list = orderSettingDao.getOrderSettingByMonth(map);
        List<Map<String, ?>> data = new ArrayList<>();
        for (OrderSetting orderSetting : list) {
            Map<String, Integer> orderSettingMap = new HashMap<>();
            orderSettingMap.put("date", orderSetting.getOrderDate().getDate());//获得日期（几号）
            orderSettingMap.put("number", orderSetting.getNumber());//可预约人数
            orderSettingMap.put("reservations", orderSetting.getReservations());//已预约人数
            data.add(orderSettingMap);
        }
        return data;
    }

    //根据日期修改可预约人数
    public void editNumberByDate(OrderSetting orderSetting) {
        //检查此数据（日期）是否存在
        long count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
        if (count > 0) {
            //已经存在，执行更新操作
            orderSettingDao.editNumberByOrderDate(orderSetting);
        } else {
            //不存在，执行添加操作
            orderSettingDao.add(orderSetting);
        }
    }
}