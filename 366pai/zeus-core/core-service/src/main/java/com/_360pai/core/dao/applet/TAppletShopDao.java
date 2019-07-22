
package com._360pai.core.dao.applet;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.applet.TAppletShopCondition;
import com._360pai.core.model.applet.TAppletShop;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * <p>TAppletShop的基础操作Dao</p>
 * @ClassName: TAppletShopDao
 * @Description: TAppletShop基础操作的Dao
 * @author Generator
 * @date 2018年11月22日 14时40分30秒
 */
public interface TAppletShopDao extends BaseDao<TAppletShop,TAppletShopCondition>{

    int addFavoriteCount(Integer id);

    int subFavoriteCount(Integer id);

    PageInfo getListByPage(int page, int perPage, Map<String, Object> params, String orderBy);

    int countShopInviteNum(Integer shopId);

    TAppletShop getByPartyId(Integer partyId);

    Map<String, Object> getSummaryInfo(Map<String, Object> params);

    PageInfo<TAppletShop> getInvitedList(int page, int perPage, Map<String, Object> params, String orderBy);
}
