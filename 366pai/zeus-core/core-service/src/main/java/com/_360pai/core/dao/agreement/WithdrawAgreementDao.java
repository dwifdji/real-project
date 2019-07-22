
package com._360pai.core.dao.agreement;

import com._360pai.core.condition.agreement.WithdrawAgreementCondition;
import com._360pai.core.model.agreement.WithdrawAgreement;
import com._360pai.arch.core.abs.BaseDao;

/**
 * <p>WithdrawAgreement的基础操作Dao</p>
 * @ClassName: WithdrawAgreementDao
 * @Description: WithdrawAgreement基础操作的Dao
 * @author Generator
 * @date 2018年12月06日 10时46分51秒
 */
public interface WithdrawAgreementDao extends BaseDao<WithdrawAgreement,WithdrawAgreementCondition>{
    WithdrawAgreement getByAcctDetailId(Long acctDetailId);
}
