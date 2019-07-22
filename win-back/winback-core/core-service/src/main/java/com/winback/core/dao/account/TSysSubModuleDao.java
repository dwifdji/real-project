
package com.winback.core.dao.account;

import com.winback.core.condition.account.TSysSubModuleCondition;
import com.winback.core.model.account.TSysModule;
import com.winback.core.model.account.TSysSubModule;
import com.winback.arch.core.abs.BaseDao;

import java.util.List;

/**
 * <p>TSysSubModule的基础操作Dao</p>
 * @ClassName: TSysSubModuleDao
 * @Description: TSysSubModule基础操作的Dao
 * @author Generator
 * @date 2019年03月04日 13时53分51秒
 */
public interface TSysSubModuleDao extends BaseDao<TSysSubModule,TSysSubModuleCondition>{
    List<TSysSubModule> findBy(Integer moduleId);
}
