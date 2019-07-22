
package com._360pai.core.dao.comprador;

import com._360pai.core.condition.comprador.TCompradorRequirementRecommenderCondition;
import com._360pai.core.model.comprador.TCompradorRequirementRecommender;
import com._360pai.arch.core.abs.BaseDao;

/**
 * <p>TCompradorRequirementRecommender的基础操作Dao</p>
 * @ClassName: TCompradorRequirementRecommenderDao
 * @Description: TCompradorRequirementRecommender基础操作的Dao
 * @author Generator
 * @date 2018年09月03日 12时40分06秒
 */
public interface TCompradorRequirementRecommenderDao extends BaseDao<TCompradorRequirementRecommender,TCompradorRequirementRecommenderCondition>{

    int getCountByRequirementId(Integer requirementId);
}
