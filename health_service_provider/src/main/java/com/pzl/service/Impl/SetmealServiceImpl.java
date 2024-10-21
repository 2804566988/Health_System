package com.pzl.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pzl.constant.RedisConstant;
import com.pzl.dao.SetmealDao;
import com.pzl.entity.PageResult;
import com.pzl.pojo.Setmeal;
import com.pzl.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 体检套餐服务实现类
 */
@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    StringRedisTemplate redisTemplate;  //操作k-v都是字符串的
    @Autowired
    private SetmealDao setmealDao;

    //新增套餐
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealDao.add(setmeal);
        if (checkgroupIds != null && checkgroupIds.length > 0) {
            //绑定套餐和检查组的多对多关系
            setSetmealAndCheckGroup(setmeal.getId(), checkgroupIds);
        }
        //将图片名称保存到Redis
        redisTemplate.opsForSet().add(RedisConstant.SETMEAL_PIC_DB_RESOURCES, setmeal.getImg());
    }

    //绑定套餐和检查组的多对多关系
    private void setSetmealAndCheckGroup(Integer id, Integer[] checkgroupIds) {
        for (Integer checkgroupId : checkgroupIds) {
            Map<String, Integer> map = new HashMap<>();
            map.put("setmeal_id", id);
            map.put("checkgroup_id", checkgroupId);
            setmealDao.setSetmealAndCheckGroup(map);
        }
    }

    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<Setmeal> page = setmealDao.selectByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    //编辑套餐，同时需要更新和检查组的关联关系
    public void edit(Setmeal setmeal, Integer[] checkGroupIds) {
        //删除Redis中的图片名称（已存入数据库的图片）
        redisTemplate.opsForSet().remove(RedisConstant.SETMEAL_PIC_DB_RESOURCES, setmealDao.findById(setmeal.getId()).getImg());
        //根据套餐id删除中间表数据（清理原有关联关系）
        setmealDao.deleteAssociation(setmeal.getId());
        //向中间表(t_setmeal_checkgroup)插入数据（建立套餐和检查组关联关系）
        setSetmealAndCheckGroup(setmeal.getId(),checkGroupIds);
        //更新套餐基本信息
        setmealDao.edit(setmeal);
        //将图片名称保存到Redis
        redisTemplate.opsForSet().add(RedisConstant.SETMEAL_PIC_DB_RESOURCES, setmeal.getImg());
    }

    //根据id查找套餐和检查组的关联关系
    public List<Integer> findCheckGroupIdsBySetMealId(Integer id) {
        return setmealDao.findCheckGroupIdsBySetMealId(id);
    }

    public Setmeal findById(Integer id) {
        return setmealDao.findById(id);
    }

    //删除
    public void deleteById(Integer id) {
        //删除Redis中的图片名称（已存入数据库的图片）
        redisTemplate.opsForSet().remove(RedisConstant.SETMEAL_PIC_DB_RESOURCES, setmealDao.findById(id).getImg());
        //根据套餐id删除和检查组的中间表数据（清理原有关联关系）
        setmealDao.deleteAssociation(id);
        //根据id删除套餐基本信息
        setmealDao.deleteById(id);
    }

    public List<Setmeal> findAll() {
        return setmealDao.findAll();
    }
}