
package com._360pai.core.dao.lease.mapper;

import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.vo.lease.LeaseAuditVo;
import com._360pai.core.vo.lease.LeaseCompeteApplyVo;
import com._360pai.core.vo.lease.LeaseLeadAuditVo;
import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.lease.TLeaseApplyCondition;
import com._360pai.core.model.lease.TLeaseApply;

import java.util.List;
import java.util.Map;

/**
 * <p>TLeaseApply数据层的基础操作</p>
 * @ClassName: TLeaseApplyMapper
 * @Description: TLeaseApply数据层的基础操作
 * @author Generator
 * @date 2019年04月24日 12时55分32秒
 */
@Mapper
public interface TLeaseApplyMapper extends BaseMapper<TLeaseApply, TLeaseApplyCondition> {

    List<LeaseAuditVo> myLeaseAuditList(Map<String, Object> params);


    List<LeaseCompeteApplyVo> getCompeteApplyList(Map<String, Object> params);

    List<LeaseLeadAuditVo> getLeadAuditList(Map<String, Object> params);


    List<LeaseCompeteApplyVo> getLeaseCompeteApply(TLeaseApplyCondition condition);

}