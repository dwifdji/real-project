
package com.winback.core.dao.account.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition.account.TSysMenuCondition;
import com.winback.core.model.account.TSysMenu;
import com.winback.arch.core.abs.BaseMapper;

/**
 * <p>TSysMenu数据层的基础操作</p>
 * @ClassName: TSysMenuMapper
 * @Description: TSysMenu数据层的基础操作
 * @author Generator
 * @date 2019年01月30日 14时34分26秒
 */
@Mapper
public interface TSysMenuMapper extends BaseMapper<TSysMenu, TSysMenuCondition>{

}
