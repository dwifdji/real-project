
package com._360pai.core.dao.assistant.mapper;

import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.assistant.FavoriteActivity;
import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.assistant.NotifyPartyActivityCondition;
import com._360pai.core.model.assistant.NotifyPartyActivity;
import com._360pai.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>NotifyPartyActivity数据层的基础操作</p>
 * @ClassName: NotifyPartyActivityMapper
 * @Description: NotifyPartyActivity数据层的基础操作
 * @author Generator
 * @date 2018年08月10日 17时37分17秒
 */
@Mapper
public interface NotifyPartyActivityMapper extends BaseMapper<NotifyPartyActivity, NotifyPartyActivityCondition>{

    List<Map> myNotify(@Param("partyId") Integer partyId, @Param("accountId") Integer accountId,@Param("categoryId") String categoryId,@Param("name") String name);

    int deleteNotify(@Param("paramsId") Integer id);

    int cancel(@Param("activityId") Integer activityId, @Param("partyPrimaryId") Integer partyPrimaryId,
               @Param("accountId") Integer accountId);

    NotifyPartyActivity selectByPartyIdAndActivityId(@Param("partyId") Integer partyId, @Param("activityId") Integer activityId, @Param("accountId") Integer accountId);

    List<AuctionActivity> getBeginIn5MinuteList(@Param("accountId") Integer accountId, @Param("partyId") Integer partyId);

    List<AuctionActivity> getEndIn5MinuteList(@Param("accountId") Integer accountId, @Param("partyId") Integer partyId);
}
