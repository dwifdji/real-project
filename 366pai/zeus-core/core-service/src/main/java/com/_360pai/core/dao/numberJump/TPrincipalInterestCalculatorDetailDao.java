
package com._360pai.core.dao.numberJump;

import com._360pai.core.condition.numberJump.TPrincipalInterestCalculatorDetailCondition;
import com._360pai.core.model.numberJump.TPrincipalInterestCalculatorDetail;
import com._360pai.arch.core.abs.BaseDao;

import java.util.List;

/**
 * <p>TPrincipalInterestCalculatorDetail的基础操作Dao</p>
 * @ClassName: TPrincipalInterestCalculatorDetailDao
 * @Description: TPrincipalInterestCalculatorDetail基础操作的Dao
 * @author Generator
 * @date 2019年04月28日 14时58分37秒
 */
public interface TPrincipalInterestCalculatorDetailDao extends BaseDao<TPrincipalInterestCalculatorDetail,TPrincipalInterestCalculatorDetailCondition>{
    List<TPrincipalInterestCalculatorDetail> findOverdueListByCalculatorId(Integer calculatorId);
}
