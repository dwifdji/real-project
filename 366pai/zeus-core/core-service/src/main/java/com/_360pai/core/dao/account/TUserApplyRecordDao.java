
package com._360pai.core.dao.account;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.account.TUserApplyRecordCondition;
import com._360pai.core.model.account.TUserApplyRecord;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>TUserApplyRecord的基础操作Dao</p>
 * @ClassName: TUserApplyRecordDao
 * @Description: TUserApplyRecord基础操作的Dao
 * @author Generator
 * @date 2018年08月17日 15时47分34秒
 */
public interface TUserApplyRecordDao extends BaseDao<TUserApplyRecord,TUserApplyRecordCondition>{
    PageInfo getListByPage(int page, int perPage, Map<String, Object> params, String orderBy);

    TUserApplyRecord getApprovedByCertificateNumber(String certificateNumber);

    List<TUserApplyRecord> getByAccountId(Integer accountId);
}
