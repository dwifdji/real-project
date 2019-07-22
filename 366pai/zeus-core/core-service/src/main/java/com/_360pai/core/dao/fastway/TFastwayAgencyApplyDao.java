
package com._360pai.core.dao.fastway;

import com._360pai.core.condition.fastway.TFastwayAgencyApplyCondition;
import com._360pai.core.model.account.TCompany;
import com._360pai.core.model.fastway.TFastwayAgencyApply;
import com._360pai.arch.core.abs.BaseDao;

import java.util.List;
import java.util.Map;

/**
 * <p>TFastwayAgencyApply的基础操作Dao</p>
 * @ClassName: TFastwayAgencyApplyDao
 * @Description: TFastwayAgencyApply基础操作的Dao
 * @author Generator
 * @date 2018年11月29日 14时19分01秒
 */
public interface TFastwayAgencyApplyDao extends BaseDao<TFastwayAgencyApply,TFastwayAgencyApplyCondition>{
    List<TFastwayAgencyApply> findByParam(Map<String, Object> query);
    List<TCompany> findByAccountId(Integer accountId);
}
