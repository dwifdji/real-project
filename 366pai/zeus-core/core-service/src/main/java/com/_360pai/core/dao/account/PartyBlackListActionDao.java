
package com._360pai.core.dao.account;

import com._360pai.core.condition.account.PartyBlackListActionCondition;
import com._360pai.core.model.account.PartyBlackListAction;
import com._360pai.arch.core.abs.BaseDao;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * <p>PartyBlackListAction的基础操作Dao</p>
 * @ClassName: PartyBlackListActionDao
 * @Description: PartyBlackListAction基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分13秒
 */
public interface PartyBlackListActionDao extends BaseDao<PartyBlackListAction,PartyBlackListActionCondition>{

    PageInfo getListByPage(int page, int perPage, PartyBlackListActionCondition condition, String orderBy);

    PartyBlackListAction getLatestByPartyId(Integer partyId);
}
