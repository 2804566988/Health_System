package com.pzl.dao;
import com.github.pagehelper.Page;
import com.pzl.pojo.CheckItem;


/**
 * 持久层Dao接口
 */
public interface CheckItemDao {
    public void add(CheckItem checkItem);

    public Page<CheckItem> selectByCondition(String queryString);

    public void deleteById(Integer id);
    public long findCountByCheckItemId(Integer checkItemId);
}