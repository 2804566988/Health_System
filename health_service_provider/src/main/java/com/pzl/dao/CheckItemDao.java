package com.pzl.dao;
import com.github.pagehelper.Page;
import com.pzl.pojo.CheckItem;

import java.util.List;


/**
 * 持久层Dao接口
 */
public interface CheckItemDao {
    //添加
    void add(CheckItem checkItem);

    //分页查询
    Page<CheckItem> selectByCondition(String queryString);

    //编辑
    void edit(CheckItem checkItem);
    //编辑时回显检查项信息
    CheckItem findById(Integer id);

    //查询所有(用于CheckGroup中展示检查项列表)
    List<CheckItem> findAll();

    //删除
    void deleteById(Integer id);
    //查询关联表判断是否被检查组引用
    long findCountByCheckItemId(Integer checkitemId);
}