package com.pzl.service;

import com.pzl.entity.PageResult;
import com.pzl.pojo.Member;

import java.util.List;

/*
会员接口
 */
public interface MemberService {
    //添加会员
    void add(Member member);
    //通过手机号查找
    Member findByTelephone(String telephone);

    List<Integer> findMemberCountByMonth(List<String> list);
}
