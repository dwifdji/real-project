
package com.winback.core.dao.account.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition.account.TSysPermissionCondition;
import com.winback.core.model.account.TSysPermission;
import com.winback.arch.core.abs.BaseMapper;

import java.util.List;

/**
 * <p>TSysPermission数据层的基础操作</p>
 * @ClassName: TSysPermissionMapper
 * @Description: TSysPermission数据层的基础操作
 * @author Generator
 * @date 2019年01月30日 14时34分26秒
 */
@Mapper
public interface TSysPermissionMapper extends BaseMapper<TSysPermission, TSysPermissionCondition>{
    List<TSysPermission> findBy(Integer staffId);
}
