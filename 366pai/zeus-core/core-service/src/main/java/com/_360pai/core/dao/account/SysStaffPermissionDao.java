
package com._360pai.core.dao.account;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.account.SysStaffPermissionCondition;
import com._360pai.core.facade.account.vo.PermissionVo;
import com._360pai.core.model.account.SysStaffPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>SysStaffPermission的基础操作Dao</p>
 * @ClassName: SysStaffPermissionDao
 * @Description: SysStaffPermission基础操作的Dao
 * @author Generator
 * @date 2018年10月07日 16时55分54秒
 */
public interface SysStaffPermissionDao extends BaseDao<SysStaffPermission,SysStaffPermissionCondition>{
    List<PermissionVo> getStaffSpecialPermissionList(Integer staffId);

    List<SysStaffPermission> getStaffPermissionList(Integer staffId);

    List<SysStaffPermission> getAllStaffPermissionList(Integer staffId);
}
