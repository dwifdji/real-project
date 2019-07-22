
package com._360pai.core.dao.assistant.mapper;

import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.vo.activity.FavoriteActivityVo;
import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.assistant.FavoriteActivityCondition;
import com._360pai.core.model.assistant.FavoriteActivity;
import com._360pai.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>FavoriteActivity数据层的基础操作</p>
 *
 * @author Generator
 * @ClassName: FavoriteActivityMapper
 * @Description: FavoriteActivity数据层的基础操作
 * @date 2018年08月10日 17时37分17秒
 */
@Mapper
public interface FavoriteActivityMapper extends BaseMapper<FavoriteActivity, FavoriteActivityCondition> {

    int cancelFavor(@Param("paramsId") Integer id, @Param("partyId") Integer partyId);

    int unFavor(@Param("partyId") Integer partyId,
                @Param("activityId") Integer activityId,
                @Param("type") String type,
                @Param("resourceId") Integer resourceId,
                @Param("accountId") Integer accountId);

    List<FavoriteActivityVo> myFavor(Map<String, Object> params);

    FavoriteActivity selectByPartyIdAndActivityId(@Param("partyId") Integer partyId, @Param("activityId") Integer activityId);

    int favoriteCount(@Param("partyId") Integer partyId, @Param("accountId") Integer accountId, @Param("type") String type);

    List<FavoriteActivityVo> getMyShopFavor(@Param("partyId")Integer partyId, @Param("accountId") Integer accountId);

    FavoriteActivity selectShopFavorActivityId(@Param("partyId") Integer partyId, @Param("activityId") Integer activityId,
                                               @Param("shopId") String shopId, @Param("accountId") Integer accountId);

    List<AuctionActivity> getBeginIn5MinuteList(@Param("accountId") Integer accountId, @Param("partyId") Integer partyId);

    List<AuctionActivity> getEndIn5MinuteList(@Param("accountId") Integer accountId, @Param("partyId") Integer partyId);
}
