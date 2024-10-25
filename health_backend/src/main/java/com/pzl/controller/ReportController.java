package com.pzl.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pzl.constant.MessageConstant;
import com.pzl.entity.Result;
import com.pzl.service.MemberService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 统计报表
 */
@RestController
@RequestMapping("/report")
public class ReportController {
    @Reference
    private MemberService memberService;
    /**
     * 会员数量统计
     * @return
     */
    @RequestMapping("/getMemberReport.do")
    public Result getMemberReport(){
        //获得日历对象
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-12);//获得当前日期之前12个月的日期

        List<String> list = new ArrayList<>();
        for(int i=0;i<12;i++){
            calendar.add(Calendar.MONTH,1);
            list.add(new SimpleDateFormat("yyyy.MM").format(calendar.getTime()));
        }

        Map<String,Object> map = new HashMap<>();
        map.put("months",list);

        List<Integer> memberCount = memberService.findMemberCountByMonth(list);
        map.put("memberCount",memberCount);

        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
    }
}