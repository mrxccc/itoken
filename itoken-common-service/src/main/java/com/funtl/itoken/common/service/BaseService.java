package com.funtl.itoken.common.service;

import com.funtl.itoken.common.domain.BaseDomain;
import com.github.pagehelper.PageInfo;

/**
 * @Author: BernardLowe
 * @Description:
 * @Date: Created in 20:03 2019/6/20
 * @Modified By: BernardLowe
 */
public interface BaseService <T extends BaseDomain> {
    int insert(T t,String createBy);

    int delete(T t);

    int update(T t,String updateBy);

    int count(T t);

    T select(T t);

    public T selectOne(T t);

    PageInfo<T> page(int pageNum,int pageSize,T t);
}
