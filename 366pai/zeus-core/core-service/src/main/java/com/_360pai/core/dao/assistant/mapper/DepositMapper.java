
package com._360pai.core.dao.assistant.mapper;

import com._360pai.core.model.activity.AuctionActivity;
import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.assistant.DepositCondition;
import com._360pai.core.model.assistant.Deposit;
import com._360pai.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>Deposit数据层的基础操作</p>
 *
 * @author Generator
 * @ClassName: DepositMapper
 * @Description: Deposit数据层的基础操作
 * @date 2018年08月10日 17时37分17秒
 */
@Mapper
public interface DepositMapper extends BaseMapper<Deposit, DepositCondition> {

    Integer getDepositCount(@Param("activityId") Integer activityId, @Param("agencyId") Integer agencyId);

    List<Map> myDepositList(@Param("partyId") Integer partyId,@Param("categoryId")String categoryId,@Param("name")String name);

    Deposit getByActivityIdPartId(@Param("activityId") Integer activityId, @Param("partyId") Integer partyId);

    List<Deposit> selectNoDealYX(Integer activityId);

    int updateDealYX(Integer level);

    List<AuctionActivity> getBeginIn5MinuteList(@Param("partyId") Integer partyId);

    List<AuctionActivity> getEndIn5MinuteList(@Param("partyId") Integer partyId);
}
