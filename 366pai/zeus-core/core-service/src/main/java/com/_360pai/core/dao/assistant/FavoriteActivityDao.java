
package com._360pai.core.dao.assistant;

import com._360pai.core.condition.assistant.FavoriteActivityCondition;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.assistant.FavoriteActivity;
import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.vo.activity.FavoriteActivityVo;

import java.util.List;
import java.util.Map;

/**
 * <p>FavoriteActivity的基础操作Dao</p>
 * @ClassName: FavoriteActivityDao
 * @Description: FavoriteActivity基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分17秒
 */
public interface FavoriteActivityDao extends BaseDao<FavoriteActivity,FavoriteActivityCondition>{

    int cancelFavor(Integer id, Integer paramsId);

    List<FavoriteActivityVo> myFavor(Map<String, Object> params);

    int unFavor(Integer partyId, Integer activityId,String type, Integer resourceId, Integer accountId);

    FavoriteActivity selectByPartyIdAndActivityId(Integer partyId, Integer activityId);

    int appletFavoriteCount(Integer partyId, Integer accountId);

    int activityFavorCount(Integer activityId);

    List<FavoriteActivityVo> getMyShopFavor(Integer partyId, Integer accountId);

    FavoriteActivity selectShopFavorActivityId(Integer partyId, Integer valueOf, String shopId, Integer accountId);

    List<AuctionActivity> getBeginIn5MinuteList(Integer accountId, Integer partyId);

    List<AuctionActivity> getEndIn5MinuteList(Integer accountId, Integer partyId);
}
