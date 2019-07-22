
package com._360pai.core.dao.fastway;

import com._360pai.core.condition.fastway.TFastwayFundApplyCondition;
import com._360pai.core.model.account.TCompany;
import com._360pai.core.model.fastway.TFastwayAgencyApply;
import com._360pai.core.model.fastway.TFastwayFundApply;
import com._360pai.arch.core.abs.BaseDao;

import java.util.List;
import java.util.Map;

/**
 * <p>TFastwayFundApply的基础操作Dao</p>
 * @ClassName: TFastwayFundApplyDao
 * @Description: TFastwayFundApply基础操作的Dao
 * @author Generator
 * @date 2018年12月07日 09时54分39秒
 */
public interface TFastwayFundApplyDao extends BaseDao<TFastwayFundApply,TFastwayFundApplyCondition>{
    List<TFastwayAgencyApply> findByParam(Map<String, Object> query);
    List<TCompany> findApplyStatusByAccountId(Integer accountId);
}
