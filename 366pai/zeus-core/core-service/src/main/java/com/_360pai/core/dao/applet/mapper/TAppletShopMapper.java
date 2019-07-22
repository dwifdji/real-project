
package com._360pai.core.dao.applet.mapper;

import com._360pai.core.model.applet.TAppletShopUpdateApplyRecord;
import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.applet.TAppletShopCondition;
import com._360pai.core.model.applet.TAppletShop;
import com._360pai.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>TAppletShop数据层的基础操作</p>
 * @ClassName: TAppletShopMapper
 * @Description: TAppletShop数据层的基础操作
 * @author Generator
 * @date 2018年11月22日 14时40分30秒
 */
@Mapper
public interface TAppletShopMapper extends BaseMapper<TAppletShop, TAppletShopCondition>{
    int addFavoriteCount(@Param("id") Integer id);

    int subFavoriteCount(@Param("id") Integer id);

    List<TAppletShop> getList(Map<String, Object> params);

    int countInviteNum(@Param("inviteType") String inviteType, @Param("inviteCode") Integer inviteCode);

    Map<String, Object> getSummaryInfo(Map<String, Object> params);

    List<TAppletShop> getInvitedList(Map<String, Object> params);
}
