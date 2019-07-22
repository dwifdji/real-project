
package com.winback.core.dao.account;

import com.winback.core.condition.account.TAccountExtBindCondition;
import com.winback.core.model.account.TAccountExtBind;
import com.winback.arch.core.abs.BaseDao;

/**
 * <p>TAccountExtBind的基础操作Dao</p>
 * @ClassName: TAccountExtBindDao
 * @Description: TAccountExtBind基础操作的Dao
 * @author Generator
 * @date 2019年01月16日 15时40分01秒
 */
public interface TAccountExtBindDao extends BaseDao<TAccountExtBind,TAccountExtBindCondition>{
    TAccountExtBind findAppletByOpenId(String openId);

    TAccountExtBind findAppletByAccountId(Integer accountId);
}
