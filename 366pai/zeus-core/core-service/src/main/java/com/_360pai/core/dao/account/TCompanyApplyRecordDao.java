
package com._360pai.core.dao.account;

import com._360pai.core.condition.account.TCompanyApplyRecordCondition;
import com._360pai.core.model.account.CompanyVerifyApplication;
import com._360pai.core.model.account.TCompanyApplyRecord;
import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.model.account.TUserApplyRecord;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>TCompanyApplyRecord的基础操作Dao</p>
 * @ClassName: TCompanyApplyRecordDao
 * @Description: TCompanyApplyRecord基础操作的Dao
 * @author Generator
 * @date 2018年08月17日 15时47分34秒
 */
public interface TCompanyApplyRecordDao extends BaseDao<TCompanyApplyRecord,TCompanyApplyRecordCondition>{
    PageInfo getListByPage(int page, int perPage, Map<String, Object> params, String orderBy);

    TCompanyApplyRecord getApprovedByLicense(String license);

    List<TCompanyApplyRecord> getByAccountId(Integer accountId);
}
