package com.wangyc.hehe.dao;

import com.wangyc.hehe.dao.provider.BaseProvider;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;

/**
 * TODO:
 *  1 更新参数只能是一个的问题
 *  2 主键策略
 *  3 insert 自增主键 return的问题
 * Describe: BaseDao
 * Date: 2021/1/28
 * Time: 4:08 下午
 * Author: wangyc
 */
public interface BaseDao <M,K>{
    @SelectProvider(type = BaseProvider.class, method = "findByEntity")
    List<M> findByEntity(M m);

    @SelectProvider(type = BaseProvider.class, method = "getByEntity")
    M getByEntity(M m);

    @SelectProvider(type = BaseProvider.class, method = "getByPrimaryKey")
    M getByPrimaryKey(K k);

    @InsertProvider(type = BaseProvider.class, method = "insertIgnoreNull")
    void insertIgnoreNull(M m);

    @InsertProvider(type = BaseProvider.class, method = "insertNoIgnoreNull")
    void insertNoIgnoreNull(M m);

    @UpdateProvider(type = BaseProvider.class, method = "updateByPrimaryIgnoreNull")
    void updateByPrimaryIgnoreNull(M m);

    @UpdateProvider(type = BaseProvider.class, method = "updateByPrimaryNoIgnoreNull")
    void updateByPrimaryNoIgnoreNull(M m);

    @DeleteProvider(type = BaseProvider.class,method = "deleteByEntity")
    void deleteByEntity(M m);

    @DeleteProvider(type = BaseProvider.class,method = "deleteByPrimaryKey")
    void deleteByPrimaryKey(K k);
}
