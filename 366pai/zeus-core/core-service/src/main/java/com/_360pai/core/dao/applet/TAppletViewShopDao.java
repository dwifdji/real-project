
package com._360pai.core.dao.applet;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.applet.TAppletViewShopCondition;
import com._360pai.core.facade.applet.vo.AppletVisitVo;
import com._360pai.core.model.applet.TAppletViewShop;

import java.util.List;

/**
 * <p>TAppletViewShop的基础操作Dao</p>
 * @ClassName: TAppletViewShopDao
 * @Description: TAppletViewShop基础操作的Dao
 * @author Generator
 * @date 2018年11月23日 09时16分48秒
 */
public interface TAppletViewShopDao extends BaseDao<TAppletViewShop,TAppletViewShopCondition>{

    List<AppletVisitVo> getAppletVisitList(TAppletViewShopCondition req);

}
