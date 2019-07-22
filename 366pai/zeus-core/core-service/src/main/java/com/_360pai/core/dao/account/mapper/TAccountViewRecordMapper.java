
package com._360pai.core.dao.account.mapper;

import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.account.TAccountViewRecordCondition;
import com._360pai.core.facade.account.req.AcctReq;
import com._360pai.core.model.account.TAccountViewRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * <p>TAccountViewRecord数据层的基础操作</p>
 * @ClassName: TAccountViewRecordMapper
 * @Description: TAccountViewRecord数据层的基础操作
 * @author Generator
 * @date 2019年01月14日 09时48分08秒
 */
@Mapper
public interface TAccountViewRecordMapper extends BaseMapper<TAccountViewRecord, TAccountViewRecordCondition> {

    void updateActivityByPartyId(@Param("accountId")Integer accountId, @Param("partyId")Integer partyId);
}
