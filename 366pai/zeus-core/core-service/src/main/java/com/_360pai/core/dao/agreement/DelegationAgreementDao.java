
package com._360pai.core.dao.agreement;

import com._360pai.core.condition.agreement.DelegationAgreementCondition;
import com._360pai.core.model.agreement.DelegationAgreement;
import com._360pai.arch.core.abs.BaseDao;

import java.util.List;

/**
 * <p>DelegationAgreement的基础操作Dao</p>
 * @ClassName: DelegationAgreementDao
 * @Description: DelegationAgreement基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分16秒
 */
public interface DelegationAgreementDao extends BaseDao<DelegationAgreement,DelegationAgreementCondition>{
    DelegationAgreement getByActivityId(Integer activityId);

    DelegationAgreement getByContractId(String contractId);

    List<Integer> getAllSignedTimeout();
}
