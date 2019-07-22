
package com._360pai.core.dao.account.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.account.SysRolePermissionCondition;
import com._360pai.core.model.account.SysRolePermission;
import com._360pai.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * <p>SysRolePermission数据层的基础操作</p>
 * @ClassName: SysRolePermissionMapper
 * @Description: SysRolePermission数据层的基础操作
 * @author Generator
 * @date 2018年10月07日 16时55分54秒
 */
@Mapper
public interface SysRolePermissionMapper extends BaseMapper<SysRolePermission, SysRolePermissionCondition>{
    Set<String> getRolePermissionCodeList(@Param("roleId") Integer roleId);
}
