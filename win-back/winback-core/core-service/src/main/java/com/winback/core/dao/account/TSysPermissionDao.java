
package com.winback.core.dao.account;

import com.winback.core.condition.account.TSysPermissionCondition;
import com.winback.core.model.account.TSysPermission;
import com.winback.arch.core.abs.BaseDao;

import java.util.List;

/**
 * <p>TSysPermission的基础操作Dao</p>
 * @ClassName: TSysPermissionDao
 * @Description: TSysPermission基础操作的Dao
 * @author Generator
 * @date 2019年01月30日 14时34分26秒
 */
public interface TSysPermissionDao extends BaseDao<TSysPermission,TSysPermissionCondition>{
    List<TSysPermission> getSpecialPermissionList();

    List<TSysPermission> getNormalPermissionList();
}
