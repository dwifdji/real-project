
package com.winback.core.dao.account;

import com.winback.core.condition.account.TSysStaffRoleMapCondition;
import com.winback.core.model.account.TSysRole;
import com.winback.core.model.account.TSysStaffRoleMap;
import com.winback.arch.core.abs.BaseDao;

import java.util.List;

/**
 * <p>TSysStaffRoleMap的基础操作Dao</p>
 * @ClassName: TSysStaffRoleMapDao
 * @Description: TSysStaffRoleMap基础操作的Dao
 * @author Generator
 * @date 2019年01月30日 14时34分26秒
 */
public interface TSysStaffRoleMapDao extends BaseDao<TSysStaffRoleMap,TSysStaffRoleMapCondition>{
    TSysStaffRoleMap findBy(Integer staffId, Integer roleId);

    List<TSysStaffRoleMap> findBy(Integer staffId);

    List<TSysStaffRoleMap> findAllBy(Integer staffId);

    List<TSysRole> findRoleBy(Integer staffId);
}
