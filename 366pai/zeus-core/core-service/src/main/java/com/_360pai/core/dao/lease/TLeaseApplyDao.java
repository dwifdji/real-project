
package com._360pai.core.dao.lease;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.lease.TLeaseApplyCondition;
import com._360pai.core.model.lease.TLeaseApply;
import com._360pai.core.vo.lease.LeaseAuditVo;
import com._360pai.core.vo.lease.LeaseCompeteApplyVo;
import com._360pai.core.vo.lease.LeaseLeadAuditVo;

import java.util.List;
import java.util.Map;

/**
 * <p>TLeaseApply的基础操作Dao</p>
 * @ClassName: TLeaseApplyDao
 * @Description: TLeaseApply基础操作的Dao
 * @author Generator
 * @date 2019年04月24日 12时55分32秒
 */
public interface TLeaseApplyDao extends BaseDao<TLeaseApply,TLeaseApplyCondition> {


    List<LeaseAuditVo> myLeaseAuditList(Map<String,Object> params);


    List<LeaseCompeteApplyVo> getCompeteApplyList(Map<String,Object> params);


    List<LeaseLeadAuditVo> getLeadAuditList(Map<String,Object> params);



    List<LeaseCompeteApplyVo> getLeaseCompeteApply(TLeaseApplyCondition condition);



}
