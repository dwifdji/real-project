
package com._360pai.core.dao.agreement;

import com._360pai.core.condition.agreement.DealAgreementCondition;
import com._360pai.core.model.agreement.DealAgreement;
import com._360pai.arch.core.abs.BaseDao;

/**
 * <p>DealAgreement的基础操作Dao</p>
 * @ClassName: DealAgreementDao
 * @Description: DealAgreement基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分16秒
 */
public interface DealAgreementDao extends BaseDao<DealAgreement,DealAgreementCondition>{
    DealAgreement getByOrderId(Long orderId,String contractType);
}
