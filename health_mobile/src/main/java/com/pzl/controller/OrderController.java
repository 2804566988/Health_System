package com.pzl.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pzl.constant.MessageConstant;
import com.pzl.constant.RedisMessageConstant;
import com.pzl.entity.Result;
import com.pzl.pojo.Order;
import com.pzl.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Reference
    private OrderService orderService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 体检预约
     * @param map
     * @return
     */
    @RequestMapping("/submit.do")
    public Result submitOrder(@RequestBody Map map){
        String telephone = (String) map.get("telephone");
        //从Redis中获取缓存的验证码，key为手机号+RedisConstant.SENDTYPE_ORDER
        String codeInRedis =stringRedisTemplate.opsForValue().get(
                telephone + RedisMessageConstant.SENDTYPE_ORDER);
        String validateCode = (String) map.get("validateCode");
        //校验手机验证码
        if(codeInRedis == null || !codeInRedis.equals(validateCode)){ //验证码无效(不在redis缓存中或输入错误)
            return new Result(false, MessageConstant.VALIDATECODE_INVALID);
        }
        Result result =null;
        //调用体检预约服务
        try{
            map.put("orderType", Order.ORDERTYPE_WEIXIN);
            result = orderService.order(map);
        }catch (Exception e){
            e.printStackTrace();
            //预约失败
            return result;
        }
//        if(result.isFlag()){
//            //预约成功，发送短信通知
//            String orderDate = (String) map.get("orderDate");
//            try {
//                SMSUtils.sendShortMessage(SMSUtils.ORDER_NOTICE,telephone,orderDate);
//            } catch (ClientException e) {
//                e.printStackTrace();
//            }
//        }
        return result;
    }

    /**
     * 根据id查询预约信息，包括套餐信息和会员信息
     * @param id
     * @return
     */
    @RequestMapping("/findById.do")
    public Result findById(Integer id){
        try{
            Map map = orderService.findById(id);
            //查询预约信息成功
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
        }catch (Exception e){
            e.printStackTrace();
            //查询预约信息失败
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }
    }
}
