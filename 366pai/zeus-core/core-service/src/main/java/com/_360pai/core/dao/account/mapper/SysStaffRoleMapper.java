
package com._360pai.core.dao.account.mapper;

import com._360pai.core.model.account.SysRole;
import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.account.SysStaffRoleCondition;
import com._360pai.core.model.account.SysStaffRole;
import com._360pai.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>SysStaffRole数据层的基础操作</p>
 * @ClassName: SysStaffRoleMapper
 * @Description: SysStaffRole数据层的基础操作
 * @author Generator
 * @date 2018年10月07日 16时55分54秒
 */
@Mapper
public interface SysStaffRoleMapper extends BaseMapper<SysStaffRole, SysStaffRoleCondition>{
    List<SysRole> getRoleList(@Param("staffId") Integer staffId);
}
