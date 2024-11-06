package com.pzl.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pzl.constant.MessageConstant;
import com.pzl.entity.PageResult;
import com.pzl.entity.QueryPageBean;
import com.pzl.entity.Result;
import com.pzl.pojo.Setmeal;
import com.pzl.service.OrderSettingListService;
import com.pzl.service.SetmealService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 预约列表（未完成）
 */
@RestController
@RequestMapping("/ordersettinglist")
public class OrderSettingListController {
    @Reference
    private OrderSettingListService orderSettingListService;
    @Reference
    private  SetmealService setmealService;

    //分页查询
    @RequestMapping("/findPage.do")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = orderSettingListService.pageQuery(
                queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),
                queryPageBean.getQueryString()
        );
        return pageResult;
    }

    //根据套餐id查询对应的所有检查组id
    @RequestMapping("/findCheckGroupIdsBySetmealId.do")
    public Result findCheckGroupIdsBySetMealId(Integer id){
        try{
            List<Integer> checkGroupIds = setmealService.findCheckGroupIdsBySetMealId(id);
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroupIds);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    //根据id查询
    @RequestMapping("/findById.do")
    public Result findById(Integer id){
        Setmeal setmeal = setmealService.findById(id);
        if(setmeal != null){
            Result result = new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS);
            result.setData(setmeal);
            return result;
        }
        return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
    }
}
