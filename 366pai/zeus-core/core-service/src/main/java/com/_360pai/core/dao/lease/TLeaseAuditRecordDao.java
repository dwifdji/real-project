
package com._360pai.core.dao.lease;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.lease.TLeaseAuditRecordCondition;
import com._360pai.core.model.lease.TLeaseAuditRecord;
import com._360pai.core.vo.lease.LeaseApplyRecordVo;
import com._360pai.core.vo.lease.LeaseAuditRecordVo;
import com._360pai.core.vo.lease.LeaseAuditVo;

import java.util.List;
import java.util.Map;

/**
 * <p>TLeaseAuditRecord的基础操作Dao</p>
 * @ClassName: TLeaseAuditRecordDao
 * @Description: TLeaseAuditRecord基础操作的Dao
 * @author Generator
 * @date 2019年04月26日 13时29分17秒
 */
public interface TLeaseAuditRecordDao extends BaseDao<TLeaseAuditRecord,TLeaseAuditRecordCondition> {

    List<LeaseAuditRecordVo> getAuditRecordList(Map<String,Object> params);


    List<LeaseApplyRecordVo> myApplyLeaseRecord(String name, String partyId);
}
