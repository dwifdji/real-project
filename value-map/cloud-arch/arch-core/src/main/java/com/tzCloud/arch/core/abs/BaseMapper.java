
package com.tzCloud.arch.core.abs;

import java.util.List;

/**
 * <p>数据库层操作的共性方法抽象</p>
 *
 * @param <E> E Element 类型元素，返回类型
 * @param <C> Condition 操作条件
 * @author Generator
 * @ClassName: BaseMapper
 * @Description: 数据库层操作的共性方法抽象
 * @date 2018年08月02日 17时03分45秒
 */
public interface BaseMapper<E, C extends DaoCondition> {

    /**
     * <p>通过给定查询条件，查询出一组结果集</p>
     *
     * @param daoMapper 查询条件
     * @return List<E> 查询结果集
     * @Title: selectByCondition
     * @Description: 通过给定查询条件，查询出一组结果集
     * @author Generator
     * @date 2018年08月02日 17时03分45秒
     */
    List<E> selectByCondition(C daoCondition);

    /**
     * <p>查询当前表下的所有数据</p>
     *
     * @return List<E> 结果集合
     * @Title: selectAll
     * @Description: 查询当前表下的所有数据
     * @author Generator
     * @date 2018年08月02日 17时03分45秒
     */
    List<E> selectAll();

    /**
     * <p>插入指定实体bean到数据库中</p>
     *
     * @param entity 需要插入的实体bean
     * @Title: insert
     * @Description: 插入指定实体bean到数据库中
     * @author Generator
     * @date 2018年08月02日 17时03分45秒
     */
    int insert(E entity);

    /**
     * <p>根据主键更新指定实体bean</p>
     *
     * @param entity 需要更新的实体bean
     * @Title: updateById
     * @Description: 根据主键更新指定实体bean
     * @author Generator
     * @date 2018年08月02日 17时03分45秒
     */
    int updateById(E entity);

}
