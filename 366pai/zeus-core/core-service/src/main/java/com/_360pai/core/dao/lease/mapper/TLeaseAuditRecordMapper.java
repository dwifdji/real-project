
package com._360pai.core.dao.lease.mapper;

import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.vo.lease.LeaseApplyRecordVo;
import com._360pai.core.vo.lease.LeaseAuditRecordVo;
import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.lease.TLeaseAuditRecordCondition;
import com._360pai.core.model.lease.TLeaseAuditRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>TLeaseAuditRecord数据层的基础操作</p>
 * @ClassName: TLeaseAuditRecordMapper
 * @Description: TLeaseAuditRecord数据层的基础操作
 * @author Generator
 * @date 2019年04月26日 13时29分17秒
 */
@Mapper
public interface TLeaseAuditRecordMapper extends BaseMapper<TLeaseAuditRecord, TLeaseAuditRecordCondition> {


    List<LeaseAuditRecordVo> getAuditRecordList(Map<String, Object> params);

    List<LeaseApplyRecordVo> myApplyLeaseRecord(@Param("name") String name, @Param("partyId") String partyId);
}
