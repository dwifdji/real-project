
package com._360pai.core.dao.account;

import com._360pai.core.condition.account.TAccountExtBindCondition;
import com._360pai.core.model.account.TAccountExtBind;
import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.model.account.TUser;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * <p>TAccountExtBind的基础操作Dao</p>
 * @ClassName: TAccountExtBindDao
 * @Description: TAccountExtBind基础操作的Dao
 * @author Generator
 * @date 2018年11月23日 09时16分44秒
 */
public interface TAccountExtBindDao extends BaseDao<TAccountExtBind,TAccountExtBindCondition>{

    TAccountExtBind selectById(Integer id);

    int updateById(TAccountExtBind model);

    TAccountExtBind findAppletByOpenId(String openId);

    TAccountExtBind findCalculatorByOpenId(String openId);

    PageInfo getInviteRecordListByPage(int page, int perPage, Map<String, Object> params, String orderBy);

    TAccountExtBind findAppletByAccountId(Integer accountId);

    TAccountExtBind findAppletByCurrentPartyId(Integer currentPartyId);

    int inviteCount(String inviteType, Integer inviteCode);

    TAccountExtBind findMp360PaiBy(String unionId);

    TAccountExtBind findMp360PaiByOpenId(String openId);

    TAccountExtBind findAppletBy(String unionId);
}
