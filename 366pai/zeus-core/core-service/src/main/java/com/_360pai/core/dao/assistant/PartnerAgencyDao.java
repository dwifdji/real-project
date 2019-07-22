
package com._360pai.core.dao.assistant;

import com._360pai.core.condition.assistant.PartnerAgencyCondition;
import com._360pai.core.model.assistant.PartnerAgency;
import com._360pai.arch.core.abs.BaseDao;

/**
 * <p>PartnerAgency的基础操作Dao</p>
 * @ClassName: PartnerAgencyDao
 * @Description: PartnerAgency基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分17秒
 */
public interface PartnerAgencyDao extends BaseDao<PartnerAgency,PartnerAgencyCondition>{

    int deletePartnerAgency(Integer paramsId);
}
