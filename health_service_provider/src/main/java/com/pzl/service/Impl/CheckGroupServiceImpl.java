package com.pzl.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pzl.dao.CheckGroupDao;
import com.pzl.entity.PageResult;
import com.pzl.pojo.CheckGroup;
import com.pzl.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 检查组服务
 */
@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    private CheckGroupDao checkGroupDao;

    //添加检查组合，同时需要设置检查组合和检查项的关联关系
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        checkGroupDao.add(checkGroup);
        setCheckGroupAndCheckItem(checkGroup.getId(), checkitemIds);
    }

    //设置检查组合和检查项的关联关系
    public void setCheckGroupAndCheckItem(Integer checkGroupId, Integer[] checkitemIds) {
        if (checkitemIds != null && checkitemIds.length > 0) {
            for (Integer checkitemId : checkitemIds) {
                Map<String, Integer> map = new HashMap<>();
                map.put("checkgroup_id", checkGroupId);
                map.put("checkitem_id", checkitemId);
                checkGroupDao.setCheckGroupAndCheckItem(map);
            }
        }
    }

    //查询
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        //完成分页查询，基于myBatis框架提供的分页助手插件完成
        PageHelper.startPage(currentPage, pageSize);
        Page<CheckGroup> page = checkGroupDao.selectByCondition(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    //根据id查询
    public CheckGroup findById(Integer id) {
        return checkGroupDao.findById(id);
    }

    //根据id查找检查组和检查项的关联关系
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(id);
    }

    //编辑检查组，同时需要更新和检查项的关联关系
    public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {
        //根据检查组id删除中间表数据（清理原有关联关系）
        checkGroupDao.deleteAssociation(checkGroup.getId());
        //向中间表(t_checkgroup_checkitem)插入数据（建立检查组和检查项关联关系）
        setCheckGroupAndCheckItem(checkGroup.getId(),checkitemIds);
        //更新检查组基本信息
        checkGroupDao.edit(checkGroup);
    }

    //删除
    public void deleteById(Integer id) throws RuntimeException{
        //查询当前检查组是否和套餐关联
        long count = checkGroupDao.findCountByCheckGroupId(id);
        if(count > 0){
            //当前检查组被引用，不能删除
            throw new RuntimeException("当前检查组被引用，不能删除");
        }
        //根据检查组id删除和检查项的中间表数据（清理原有关联关系）
        checkGroupDao.deleteAssociation(id);
        //根据id删除检查组基本信息
        checkGroupDao.deleteById(id);
    }

    //查询所有(用于SetMeal中展示检查组列表)
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }
}
