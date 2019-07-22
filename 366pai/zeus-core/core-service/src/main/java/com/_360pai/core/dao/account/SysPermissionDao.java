
package com._360pai.core.dao.account;

import com._360pai.core.condition.account.SysPermissionCondition;
import com._360pai.core.facade.account.vo.ModuleVo;
import com._360pai.core.model.account.SysPermission;
import com._360pai.arch.core.abs.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * <p>SysPermission的基础操作Dao</p>
 * @ClassName: SysPermissionDao
 * @Description: SysPermission基础操作的Dao
 * @author Generator
 * @date 2018年10月07日 16时55分54秒
 */
public interface SysPermissionDao extends BaseDao<SysPermission,SysPermissionCondition> {

    List<ModuleVo> getAllModuleList();

    List<ModuleVo> getStaffModuleList(Integer staffId);

    List<ModuleVo> getRoleModuleList(Integer roleId);

    Set<String> getStaffPermissionCodeList(Integer staffId);

    List<SysPermission> getByMenuCode(String menuCode);

    List<SysPermission> getByModuleCodeMenuCode(String moduleCode, String menuCode);

    List<SysPermission> getSpecialPermissionList();
}
