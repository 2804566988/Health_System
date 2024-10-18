package com.pzl.service;

import com.pzl.entity.PageResult;
import com.pzl.pojo.Setmeal;

import java.util.List;

public interface SetmealService {
    void add(Setmeal setmeal, Integer[] checkgroupIds);

    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

    void edit(Setmeal setmeal, Integer[] checkGroupIds);

    List<Integer> findCheckGroupIdsBySetMealId(Integer id);

    Setmeal findById(Integer id);

    void deleteById(Integer id);
}
