package com.pzl.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pzl.dao.MemberDao;
import com.pzl.entity.PageResult;
import com.pzl.pojo.CheckItem;
import com.pzl.pojo.Member;
import com.pzl.service.MemberService;
import com.pzl.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 会员实现类
 */
@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService{
    @Autowired
    private MemberDao memberDao;

    //新增会员
    public void add(Member member) {
        if(member.getPassword() != null){
            member.setPassword(MD5Utils.md5(member.getPassword()));
        }
        memberDao.add(member);
    }
    //根据手机号查询会员
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }

    //根据月份统计会员数量
    public List<Integer> findMemberCountByMonth(List<String> month) {
        List<Integer> list = new ArrayList<>();
        String mon;
        for(String m : month){
            mon = m.substring(5);
            int year =Integer.parseInt(m.substring(0,4));
            switch (mon) {
                case "01":
                case "03":
                case "05":
                case "07":
                case "08":
                case "10":
                case "12":
                    m = m + ".31";
                    break;
                case "04":
                case "06":
                case "09":
                case "11":
                    m = m + ".30";
                    break;
                case "02":
                    if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                        m = m + ".29";
                    } else {
                        m = m + ".28";
                    }
            }

            Integer count = memberDao.findMemberCountBeforeDate(m);
            list.add(count);
        }
        return list;
    }
}
