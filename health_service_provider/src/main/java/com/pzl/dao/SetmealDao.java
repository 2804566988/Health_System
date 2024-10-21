package com.pzl.dao;

import com.github.pagehelper.Page;
import com.pzl.pojo.CheckGroup;
import com.pzl.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealDao {
    //添加
    void add(Setmeal setmeal);
    //绑定套餐和检查组的多对多关系
    void setSetmealAndCheckGroup(Map<String, Integer> map);

    //分页查询
    Page<Setmeal> selectByCondition(String queryString);

    //编辑
    void edit(Setmeal setmeal);
    //编辑时回显检查组数据
    List<Integer> findCheckGroupIdsBySetMealId(Integer id);
    //编辑时回显套餐数据
    Setmeal findById(Integer id);
    //删除关联表中引用检查项的数据
    void deleteAssociation(Integer id);

    //删除
    void deleteById(Integer id);


    List<Setmeal> findAll();
}
