package com.pzl.dao;

import com.github.pagehelper.Page;
import com.pzl.pojo.CheckGroup;

import java.util.List;
import java.util.Map;

public interface CheckGroupDao {
    void add(CheckGroup checkGroup);

    void setCheckGroupAndCheckItem(Map<String,Integer> map);

    Page<CheckGroup> selectByCondition(String queryString);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    CheckGroup findById(Integer id);

    void deleteAssociation(Integer id);

    void edit(CheckGroup checkGroup);

    void delete(Integer id);
}
