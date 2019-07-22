
package com.winback.arch.core.abs;


import com.winback.arch.common.enums.ApiCallResult;
import com.winback.arch.common.exception.BaseBusinessException;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

/**
 * <p>服务接口的抽象实现</p>
 *
 * @param <E> Element 查询结果返回类型元素
 * @param <C> Condition 查询条件
 * @param <D> Mapper 数据层操作支持
 * @author Generator
 * @ClassName: AbstractDaoImpl
 * @Description: <p>服务接口的抽象实现</p>
 * @date 2018年08月02日 17时03分45秒
 */
public abstract class AbstractDaoImpl<E, C extends com.winback.arch.core.abs.DaoCondition, D extends com.winback.arch.core.abs.BaseMapper<E, C>> implements com.winback.arch.core.abs.BaseDao<E, C> {

    @Override
    public int updateById(E model) {
        return daoSupport().updateById(model);
    }

    @Override
    public List<E> selectAll() {
        C daoCondition = blankCondition();
        return selectList(daoCondition);
    }

    @Override
    public int insert(E model) {
        return daoSupport().insert(model);
    }

    /**
     * <p>查询一个符合条件的记录，即查询一条数据记录，如果有多条符合条件的结果，则返回第一条</p>
     * <p>这个方法的思想，在于只希望获取一个对象，如果获取对象，说明查询条件的唯一索引没有成功建立！是否需要抛异常，自行修改~</p>
     *
     * @param daoCondition 查询条件
     * @return E 查询结果
     * @Title: selectFirst
     * @Description: 查询一个符合条件的记录
     * @author Generator
     * @date 2018年08月02日 17时03分45秒
     */
    @Override
    public E selectFirst(C daoCondition) {
        E       result = null;
        List<E> list   = selectList(daoCondition);
        if (null != list && !list.isEmpty()) {
            result = list.get(0);
        }
        return result;
    }

    @Override
    public E selectOneResult(C daoCondition) {
        E       result = null;
        List<E> list   = selectList(daoCondition);
        if (null != list && !list.isEmpty()) {
            if (list.size() == 1) {
                result = list.get(0);
            } else {
                throw new RuntimeException("sql查询结果大于1");
            }
        }
        return result;
    }

    /**
     * <p>查询一个结果集合</p>
     *
     * @param daoCondition 查询条件
     * @return List<E> 查询结果
     * @Title: selectList
     * @Description: 查询一个结果集合
     * @author Generator
     * @date 2018年08月02日 17时03分45秒
     */
    @Override
    public List<E> selectList(C daoCondition) {
        D dao = daoSupport();
        return dao.selectByCondition(daoCondition);
    }

    private void setValue(Object dto, String name, Object value) {
        Method[] m = dto.getClass().getMethods();
        try {
            for (int i = 0; i < m.length; i++) {
                if (("set" + name).toLowerCase().equals(m[i].getName().toLowerCase())) {
                    Parameter[] parameters = m[i].getParameters();
                    Class c = parameters[0].getType();
                    if (c.equals(value.getClass())) {
                        m[i].invoke(dto,  value);
                    } else {
                        if (value.getClass().equals(String.class)) {
                            if (c.equals(Integer.class)) {
                                m[i].invoke(dto, Integer.parseInt((String) value));
                            } else if (c.equals(Long.class)) {
                                m[i].invoke(dto, Long.parseLong((String) value));
                            }
                        } else if (value.getClass().equals(Integer.class)) {
                            if (c.equals(String.class)) {
                                m[i].invoke(dto, String.valueOf(value));
                            } else if (c.equals(Long.class)) {
                                m[i].invoke(dto, ((Integer) value).longValue());
                            }
                        } else if (value.getClass().equals(Long.class)) {
                            if (c.equals(String.class)) {
                                m[i].invoke(dto, String.valueOf(value));
                            } else if (c.equals(Integer.class)) {
                                m[i].invoke(dto, ((Long) value).intValue());
                            }
                        }
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public E selectById(Object id) {
        if (id == null) {
            throw new BaseBusinessException(ApiCallResult.EMPTY);
        }
        C daoCondition = blankCondition();
        setValue(daoCondition, "id", id);
        return selectFirst(daoCondition);
    }

    /**
     * <p>DAO对象提供支持</p>
     * <p>因为是抽象类，不在spring环境下，所有，由子类自身提供操作对象</p>
     *
     * @return D DAO操作对象
     * @Title: daoSupport
     * @Description: DAO对象提供支持
     * @author Generator
     * @date 2018年08月02日 17时03分45秒
     */
    protected abstract D daoSupport();

    /**
     * <p>提供一个空白的查询条件</p>
     *
     * @return C 一个空白条件的查询条件
     * @Title: blankCondition
     * @Description: 提供一个空白的查询条件
     * @author Generator
     * @date 2018年08月02日 17时03分45秒
     */
    protected abstract C blankCondition();

}
