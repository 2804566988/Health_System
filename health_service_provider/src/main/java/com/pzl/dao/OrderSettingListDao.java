package com.pzl.dao;

import com.github.pagehelper.Page;
import com.pzl.pojo.Member;

public interface OrderSettingListDao {
    //分页查询
    Page<Member> selectOrderWithMemberAndSetmeal(String queryString);
}
