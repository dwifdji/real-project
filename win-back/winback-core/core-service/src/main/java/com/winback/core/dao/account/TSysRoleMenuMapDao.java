
package com.winback.core.dao.account;

import com.winback.core.condition.account.TSysRoleMenuMapCondition;
import com.winback.core.model.account.TSysPermission;
import com.winback.core.model.account.TSysRoleMenuMap;
import com.winback.arch.core.abs.BaseDao;
import com.winback.core.model.account.TSysStaffRoleMap;

import java.util.List;

/**
 * <p>TSysRoleMenuMap的基础操作Dao</p>
 * @ClassName: TSysRoleMenuMapDao
 * @Description: TSysRoleMenuMap基础操作的Dao
 * @author Generator
 * @date 2019年01月30日 14时34分26秒
 */
public interface TSysRoleMenuMapDao extends BaseDao<TSysRoleMenuMap,TSysRoleMenuMapCondition>{
    List<TSysRoleMenuMap> findBy(Integer roleId);

    List<TSysPermission> findPermissionBy(Integer roleId);
}
