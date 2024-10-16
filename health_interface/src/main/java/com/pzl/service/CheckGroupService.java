package com.pzl.service;

import com.pzl.entity.PageResult;
import com.pzl.pojo.CheckGroup;

import java.util.List;

/**
 * 检查组服务接口
 */
public interface CheckGroupService {
   void add(CheckGroup checkGroup,Integer[] checkitemIds);

   PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

   CheckGroup findById(Integer id);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    void edit(CheckGroup checkGroup, Integer[] checkitemIds);

    void delete(Integer id);
}
