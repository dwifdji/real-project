
package com._360pai.core.dao.account.mapper;

import com._360pai.core.facade.account.vo.AcctVo;
import com._360pai.core.model.account.TAcctDetail;
import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.account.TAcctCondition;
import com._360pai.core.model.account.TAcct;
import com._360pai.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>TAcct数据层的基础操作</p>
 * @ClassName: TAcctMapper
 * @Description: TAcct数据层的基础操作
 * @author Generator
 * @date 2018年11月22日 15时21分14秒
 */
@Mapper
public interface TAcctMapper extends BaseMapper<TAcct, TAcctCondition>{
    TAcct getByPartyIdTypeForUpdate(@Param("type") String type,@Param("partyId") Integer partyId);
    TAcct getByIdForUpdate(Integer id);
    int addAmt(TAcct acct);
    int subAmt(TAcct acct);

    List<AcctVo> getList(Map<String, Object> params);

    Map<String, Object> getSummaryInfo(Map<String, Object> params);
}
