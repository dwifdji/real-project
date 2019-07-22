
package com._360pai.core.dao.account;

import com._360pai.core.condition.account.SysRoleCondition;
import com._360pai.core.model.account.SysRole;
import com._360pai.arch.core.abs.BaseDao;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * <p>SysRole的基础操作Dao</p>
 * @ClassName: SysRoleDao
 * @Description: SysRole基础操作的Dao
 * @author Generator
 * @date 2018年10月07日 16时55分54秒
 */
public interface SysRoleDao extends BaseDao<SysRole,SysRoleCondition>{
    PageInfo getListByPage(int page, int perPage, Map<String, Object> params, String orderBy);

    boolean isExistName(String name);
}
