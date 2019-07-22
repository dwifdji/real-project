
package com._360pai.core.dao.account;

import com._360pai.core.condition.account.SysRolePermissionCondition;
import com._360pai.core.model.account.SysRolePermission;
import com._360pai.arch.core.abs.BaseDao;

import java.util.List;
import java.util.Set;

/**
 * <p>SysRolePermission的基础操作Dao</p>
 * @ClassName: SysRolePermissionDao
 * @Description: SysRolePermission基础操作的Dao
 * @author Generator
 * @date 2018年10月07日 16时55分54秒
 */
public interface SysRolePermissionDao extends BaseDao<SysRolePermission,SysRolePermissionCondition>{
    List<SysRolePermission> getByRoleId(Integer roleId);

    List<SysRolePermission> getAllByRoleId(Integer roleId);

    List<Integer> getByPermissionIdRoleId(Integer roleId);

    Set<String> getRolePermissionCodeList(Integer roleId);
}
