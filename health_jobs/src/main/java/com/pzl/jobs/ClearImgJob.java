package com.pzl.jobs;

import com.pzl.constant.RedisConstant;
import com.pzl.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.Set;

/**
 * 自定义Job，实现定时清理垃圾图片
 */
@Component
public class ClearImgJob {
    @Autowired
    StringRedisTemplate redisTemplate;  //操作k-v都是字符串
    //定时任务每日上午四点触发* * 4 * * ?
    @Scheduled(cron = "0/5 * * * * ?")
    public void clearImg(){
        //根据Redis中保存的两个set集合进行差值计算，获得垃圾图片名称集合
        //sdiff方法是计算两个集合的差值
        Set<String> set =
                redisTemplate.opsForSet().difference(RedisConstant.SETMEAL_PIC_RESOURCES,
                        RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        if(set != null){
            for (String picName : set) {
                //删除七牛云服务器上的图片
                QiniuUtils.deleteFileFromQiniu(picName);
                //从Redis集合中删除图片名称
                redisTemplate.opsForSet().
                        remove(RedisConstant.SETMEAL_PIC_RESOURCES,picName);
                System.out.println("自定义任务执行，清理垃圾图片"+picName);
            }
        }
    }
}