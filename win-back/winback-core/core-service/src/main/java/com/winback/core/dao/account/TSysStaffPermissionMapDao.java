
package com.winback.core.dao.account;

import com.winback.core.condition.account.TSysStaffPermissionMapCondition;
import com.winback.core.model.account.TSysPermission;
import com.winback.core.model.account.TSysStaffPermissionMap;
import com.winback.arch.core.abs.BaseDao;

import java.util.List;

/**
 * <p>TSysStaffPermissionMap的基础操作Dao</p>
 * @ClassName: TSysStaffPermissionMapDao
 * @Description: TSysStaffPermissionMap基础操作的Dao
 * @author Generator
 * @date 2019年01月30日 14时34分26秒
 */
public interface TSysStaffPermissionMapDao extends BaseDao<TSysStaffPermissionMap,TSysStaffPermissionMapCondition>{

    List<TSysStaffPermissionMap> findBy(Integer staffId);

    TSysStaffPermissionMap findBy(Integer staffId, Integer permissionId);

    List<TSysPermission> findPermissionBy(Integer staffId);
}
