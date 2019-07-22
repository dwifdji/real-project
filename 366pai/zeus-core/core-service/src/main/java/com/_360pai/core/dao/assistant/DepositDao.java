
package com._360pai.core.dao.assistant;

import com._360pai.core.condition.assistant.DepositCondition;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.assistant.Deposit;
import com._360pai.arch.core.abs.BaseDao;

import java.util.List;
import java.util.Map;

/**
 * <p>Deposit的基础操作Dao</p>
 * @ClassName: DepositDao
 * @Description: Deposit基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分17秒
 */
public interface DepositDao extends BaseDao<Deposit,DepositCondition>{

    Integer getDepositCount(Integer activityId);

    Integer getDepositCount(Integer activityId, Integer agencyId);

    List<Map> myDepositList(Integer partyId,String categoryId,String name);

    Deposit getByActivityIdPartId(Integer activityId, Integer partyId);

    List<Deposit> selectNoDealYX(Integer activityId);

    int updateDealYX(Integer level);

    List<AuctionActivity> getBeginIn5MinuteList(Integer partyId);

    List<AuctionActivity> getEndIn5MinuteList(Integer partyId);
}
