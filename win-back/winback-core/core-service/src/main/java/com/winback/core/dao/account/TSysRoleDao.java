
package com.winback.core.dao.account;

import com.github.pagehelper.PageInfo;
import com.winback.arch.core.abs.BaseDao;
import com.winback.core.condition.account.TSysRoleCondition;
import com.winback.core.model.account.TSysRole;
import com.winback.core.model.account.TSysStaff;

import java.util.Map;

/**
 * <p>TSysRole的基础操作Dao</p>
 * @ClassName: TSysRoleDao
 * @Description: TSysRole基础操作的Dao
 * @author Generator
 * @date 2019年01月30日 14时34分26秒
 */
public interface TSysRoleDao extends BaseDao<TSysRole,TSysRoleCondition>{

    PageInfo<TSysRole> getList(int page, int perPage, Map<String, Object> params, String orderBy);

    TSysRole findBy(String name);
}
