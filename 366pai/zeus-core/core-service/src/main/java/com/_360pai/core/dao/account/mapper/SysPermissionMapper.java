
package com._360pai.core.dao.account.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.account.SysPermissionCondition;
import com._360pai.core.model.account.SysPermission;
import com._360pai.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>SysPermission数据层的基础操作</p>
 * @ClassName: SysPermissionMapper
 * @Description: SysPermission数据层的基础操作
 * @author Generator
 * @date 2018年10月07日 16时55分54秒
 */
@Mapper
public interface SysPermissionMapper extends BaseMapper<SysPermission, SysPermissionCondition>{

    List<SysPermission> getAllModuleList();

    List<SysPermission> getStaffModuleList(@Param("staffId") Integer staffId);

    List<SysPermission> getRoleModuleList(@Param("roleId") Integer roleId);

    Set<String> getStaffPermissionCodeList(@Param("staffId") Integer staffId);
}
