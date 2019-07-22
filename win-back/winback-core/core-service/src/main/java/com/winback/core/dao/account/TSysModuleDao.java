
package com.winback.core.dao.account;

import com.winback.core.condition.account.TSysModuleCondition;
import com.winback.core.model.account.TSysModule;
import com.winback.arch.core.abs.BaseDao;

import java.util.List;

/**
 * <p>TSysModule的基础操作Dao</p>
 * @ClassName: TSysModuleDao
 * @Description: TSysModule基础操作的Dao
 * @author Generator
 * @date 2019年01月30日 14时34分26秒
 */
public interface TSysModuleDao extends BaseDao<TSysModule,TSysModuleCondition>{
    List<TSysModule> findAll();
}
