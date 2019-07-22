
package com._360pai.core.dao.account.mapper;

import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.account.SysStaffPermissionCondition;
import com._360pai.core.facade.account.vo.PermissionVo;
import com._360pai.core.model.account.SysStaffPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>SysStaffPermission数据层的基础操作</p>
 * @ClassName: SysStaffPermissionMapper
 * @Description: SysStaffPermission数据层的基础操作
 * @author Generator
 * @date 2018年10月07日 16时55分54秒
 */
@Mapper
public interface SysStaffPermissionMapper extends BaseMapper<SysStaffPermission, SysStaffPermissionCondition>{
    List<PermissionVo> getStaffSpecialPermissionList(@Param("staffId") Integer staffId);
}
