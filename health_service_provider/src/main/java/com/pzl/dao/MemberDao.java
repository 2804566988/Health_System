package com.pzl.dao;

import com.github.pagehelper.Page;
import com.pzl.pojo.CheckGroup;
import com.pzl.pojo.Member;

import java.util.List;

public interface MemberDao {
    List<Member> findAll();
    Page<Member> selectByCondition(String queryString);
    // 添加会员
    void add(Member member);
    void deleteById(Integer id);
    Member findById(Integer id);
    // 根据手机号查询会员
    Member findByTelephone(String telephone);
    void edit(Member member);
    Integer findMemberCountBeforeDate(String date);
    Integer findMemberCountByDate(String date);
    Integer findMemberCountAfterDate(String date);
    Integer findMemberTotalCount();
}
