
package com._360pai.core.dao.applet.mapper;

import com._360pai.core.facade.applet.vo.InviteRecord;
import com._360pai.core.facade.applet.vo.ShopVo;
import com._360pai.core.model.applet.TAppletShop;
import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.applet.TAppletFavoriteShopCondition;
import com._360pai.core.model.applet.TAppletFavoriteShop;
import com._360pai.arch.core.abs.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>TAppletFavoriteShop数据层的基础操作</p>
 * @ClassName: TAppletFavoriteShopMapper
 * @Description: TAppletFavoriteShop数据层的基础操作
 * @author Generator
 * @date 2018年11月22日 14时40分30秒
 */
@Mapper
public interface TAppletFavoriteShopMapper extends BaseMapper<TAppletFavoriteShop, TAppletFavoriteShopCondition>{
    List<TAppletShop> getShopListByPage(Map<String, Object> params);
}
