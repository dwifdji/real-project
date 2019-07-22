
package com.winback.core.dao.account.mapper;

import com.winback.core.model.account.TSysPermission;
import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition.account.TSysRoleMenuMapCondition;
import com.winback.core.model.account.TSysRoleMenuMap;
import com.winback.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>TSysRoleMenuMap数据层的基础操作</p>
 * @ClassName: TSysRoleMenuMapMapper
 * @Description: TSysRoleMenuMap数据层的基础操作
 * @author Generator
 * @date 2019年01月30日 14时34分26秒
 */
@Mapper
public interface TSysRoleMenuMapMapper extends BaseMapper<TSysRoleMenuMap, TSysRoleMenuMapCondition>{
    List<TSysPermission> findPermissionBy(@Param("roleId") Integer roleId);
}
