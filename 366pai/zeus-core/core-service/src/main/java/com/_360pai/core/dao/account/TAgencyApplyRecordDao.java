
package com._360pai.core.dao.account;

import com._360pai.core.condition.account.TAgencyApplyRecordCondition;
import com._360pai.core.model.account.AgencyApplication;
import com._360pai.core.model.account.TAgencyApplyRecord;
import com._360pai.arch.core.abs.BaseDao;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * <p>TAgencyApplyRecord的基础操作Dao</p>
 * @ClassName: TAgencyApplyRecordDao
 * @Description: TAgencyApplyRecord基础操作的Dao
 * @author Generator
 * @date 2018年08月17日 15时47分34秒
 */
public interface TAgencyApplyRecordDao extends BaseDao<TAgencyApplyRecord,TAgencyApplyRecordCondition>{

    PageInfo getListByPage(int page, int perPage, Map<String, Object> params, String orderBy);

    TAgencyApplyRecord getApprovedByLicense(String license);
}
