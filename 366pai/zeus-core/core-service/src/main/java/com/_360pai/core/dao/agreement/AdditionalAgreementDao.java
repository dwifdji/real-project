
package com._360pai.core.dao.agreement;

import com._360pai.core.condition.agreement.AdditionalAgreementCondition;
import com._360pai.core.model.agreement.AdditionalAgreement;
import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.model.agreement.DelegationAgreement;

/**
 * <p>AdditionalAgreement的基础操作Dao</p>
 * @ClassName: AdditionalAgreementDao
 * @Description: AdditionalAgreement基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分15秒
 */
public interface AdditionalAgreementDao extends BaseDao<AdditionalAgreement,AdditionalAgreementCondition>{
    AdditionalAgreement getByActivityId(Integer activityId);

    AdditionalAgreement getByContractId(String contractId);
}
