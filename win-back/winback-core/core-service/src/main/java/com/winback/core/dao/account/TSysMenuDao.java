
package com.winback.core.dao.account;

import com.winback.core.condition.account.TSysMenuCondition;
import com.winback.core.model.account.TSysMenu;
import com.winback.arch.core.abs.BaseDao;

import java.util.List;

/**
 * <p>TSysMenu的基础操作Dao</p>
 * @ClassName: TSysMenuDao
 * @Description: TSysMenu基础操作的Dao
 * @author Generator
 * @date 2019年01月30日 14时34分26秒
 */
public interface TSysMenuDao extends BaseDao<TSysMenu,TSysMenuCondition>{
    List<TSysMenu> findByModuleId(Integer moduleId);
}
