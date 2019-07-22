
package com._360pai.core.dao.assistant;

import com._360pai.core.condition.assistant.NotifyPartyActivityCondition;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.assistant.FavoriteActivity;
import com._360pai.core.model.assistant.NotifyPartyActivity;
import com._360pai.arch.core.abs.BaseDao;

import java.util.List;
import java.util.Map;

/**
 * <p>NotifyPartyActivity的基础操作Dao</p>
 * @ClassName: NotifyPartyActivityDao
 * @Description: NotifyPartyActivity基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分17秒
 */
public interface NotifyPartyActivityDao extends BaseDao<NotifyPartyActivity,NotifyPartyActivityCondition>{

    List<Map> myNotify(Integer partyId, Integer accountId,String categoryId,String name);

    int deleteNotify(Integer id);

    int cancel(Integer activityId, Integer partyPrimaryId, Integer accountId);

    NotifyPartyActivity selectByPartyIdAndActivityId(Integer partyId, Integer activityId, Integer accountId);

    int activityNotifiersCount(Integer activityId);

    List<NotifyPartyActivity> getByActivityId(Integer activityId);

    List<AuctionActivity> getBeginIn5MinuteList(Integer accountId, Integer partyId);

    List<AuctionActivity> getEndIn5MinuteList(Integer accountId, Integer partyId);
}
