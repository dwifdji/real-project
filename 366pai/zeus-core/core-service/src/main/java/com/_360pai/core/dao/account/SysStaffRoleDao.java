
package com._360pai.core.dao.account;

import com._360pai.core.condition.account.SysStaffRoleCondition;
import com._360pai.core.model.account.SysRole;
import com._360pai.core.model.account.SysStaffRole;
import com._360pai.arch.core.abs.BaseDao;

import java.util.List;
import java.util.Set;

/**
 * <p>SysStaffRole的基础操作Dao</p>
 * @ClassName: SysStaffRoleDao
 * @Description: SysStaffRole基础操作的Dao
 * @author Generator
 * @date 2018年10月07日 16时55分54秒
 */
public interface SysStaffRoleDao extends BaseDao<SysStaffRole,SysStaffRoleCondition>{
    List<SysStaffRole> getByStaffId(Integer staffId);

    List<SysStaffRole> getAllByStaffId(Integer staffId);

    Set<String> getRoleIdList(Integer staffId);

    List<SysRole> getRoleList(Integer staffId);
}
