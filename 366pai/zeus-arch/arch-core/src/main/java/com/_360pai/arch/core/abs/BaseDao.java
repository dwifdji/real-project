
package com._360pai.arch.core.abs;

import java.util.List;

/**
 * <p>数据层的基础操作</p>
 *
 * @author Generator
 * @ClassName: BaseDao
 * @Description: 数据层的基础操作
 * @date 2018年08月02日 17时03分45秒
 */
public interface BaseDao<E, C extends DaoCondition> {

    /**
     * <p>通过主键ID更新一个实体bean</p>
     *
     * @param ${entityPackage} 当前需要更新的实体bean
     * @Title: updateById
     * @Description: 通过主键ID更新一个实体bean
     * @author Generator
     * @date 2018年08月02日 17时03分45秒
     */
    int updateById(E model);

    /**
     * <p>保存一条记录</p>
     *
     * @param model 需要保存的实体
     * @Title: insert
     * @Description: 保存一条记录
     * @author Generator
     * @date 2018年08月02日 17时03分45秒
     */
    int insert(E model);

    /**
     * <p>查询当前表里面所有的数据</p>
     *
     * @return List<E> 表中的所有数据
     * @Title: selectAll
     * @Description: 查询当前表里面所有的数据
     * @author Generator
     * @date 2018年08月02日 17时03分45秒
     */
    List<E> selectAll();

    /**
     * <p>通过一个查询条件，查询出一个查询结果</p>
     *
     * @param daoCondition 查询条件
     * @return E 查询结果
     * @Title: selectFirst
     * @Description: 通过一个查询条件，查询出一个查询结果
     * @author Generator
     * @date 2018年08月02日 17时03分45秒
     */
    E selectFirst(C daoCondition);

    E selectOneResult(C daoCondition);

    /**
     * <p>通过一个查询条件，查询出一个结果集合</p>
     *
     * @param daoCondition 查询条件
     * @return List<E> 查询结果
     * @Title: selectList
     * @Description: 通过一个查询条件，查询出一个结果集合
     * @author Generator
     * @date 2018年08月02日 17时03分45秒
     */
    List<E> selectList(C daoCondition);

    /**
     * 描述 根据id 查询实体
     * 注意：表中需要有id 字段 才能用此方法
     *
     * @author : whisky_vip
     * @date : 2018/9/12 13:19
     */
    E selectById(Object id);
}
