
package com._360pai.core.dao.applet;

import com._360pai.core.condition.applet.TAppletFavoriteShopCondition;
import com._360pai.core.model.applet.TAppletFavoriteShop;
import com._360pai.arch.core.abs.BaseDao;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * <p>TAppletFavoriteShop的基础操作Dao</p>
 * @ClassName: TAppletFavoriteShopDao
 * @Description: TAppletFavoriteShop基础操作的Dao
 * @author Generator
 * @date 2018年11月22日 14时40分30秒
 */
public interface TAppletFavoriteShopDao extends BaseDao<TAppletFavoriteShop,TAppletFavoriteShopCondition>{
    TAppletFavoriteShop getByShopIdPartyId(Integer shopId, String openId);

    PageInfo getListByPage(int page, int perPage, Map<String, Object> params, String orderBy);

    boolean isFavorShop(String openId, Integer shopId);
}
