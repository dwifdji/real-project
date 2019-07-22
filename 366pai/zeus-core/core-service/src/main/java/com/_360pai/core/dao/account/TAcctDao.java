
package com._360pai.core.dao.account;

import com._360pai.core.condition.account.TAcctCondition;
import com._360pai.core.facade.account.vo.AcctVo;
import com._360pai.core.model.account.TAcct;
import com._360pai.arch.core.abs.BaseDao;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>TAcct的基础操作Dao</p>
 * @ClassName: TAcctDao
 * @Description: TAcct基础操作的Dao
 * @author Generator
 * @date 2018年11月22日 15时21分14秒
 */
public interface TAcctDao extends BaseDao<TAcct,TAcctCondition>{
    TAcct getByPartyIdTypeForUpdate(String type,Integer partyId);
    TAcct getByIdForUpdate(Integer id);
    int addAmt(TAcct acct);
    int subAmt(TAcct acct);

    TAcct getByPartyIdType(Integer partyId, String type);

    PageInfo getListByPage(int page, int perPage, Map<String, Object> params, String orderBy);

    Map<String, Object> getSummaryInfo(Map<String, Object> params);
}
