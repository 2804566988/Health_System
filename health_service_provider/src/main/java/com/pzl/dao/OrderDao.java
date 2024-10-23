package com.pzl.dao;

import com.pzl.pojo.Order;

import java.util.List;
import java.util.Map;

public interface OrderDao {

    void add(Order order);

    List<Order> findByCondition(Order order);

    Map findById4Detail(Integer id);
}
