
package com._360pai.core.dao.withfudig;

import com._360pai.core.condition.withfudig.TWithfudigRequirementInvestCondition;
import com._360pai.core.model.withfudig.TWithfudigRequirementInvest;
import com._360pai.arch.core.abs.BaseDao;

/**
 * <p>TWithfudigRequirementInvest的基础操作Dao</p>
 * @ClassName: TWithfudigRequirementInvestDao
 * @Description: TWithfudigRequirementInvest基础操作的Dao
 * @author Generator
 * @date 2018年09月06日 15时50分14秒
 */
public interface TWithfudigRequirementInvestDao extends BaseDao<TWithfudigRequirementInvest,TWithfudigRequirementInvestCondition>{

    Integer getCountByRequirementId(Integer requirementId);
}
