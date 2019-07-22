
package com._360pai.core.dao.account;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.account.TPersonaCondition;
import com._360pai.core.model.account.TPersona;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * <p>TPersona的基础操作Dao</p>
 * @ClassName: TPersonaDao
 * @Description: TPersona基础操作的Dao
 * @author Generator
 * @date 2018年08月29日 13时21分37秒
 */
public interface TPersonaDao extends BaseDao<TPersona, TPersonaCondition> {

    TPersona selectById(Integer id);

    PageInfo getListByPage(int page, int perPage, Map<String, Object> params, String orderBy);
}
