
package com._360pai.core.dao.applet.mapper;

import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.applet.TAppletViewShopCondition;
import com._360pai.core.facade.applet.vo.AppletVisitVo;
import com._360pai.core.model.applet.TAppletViewShop;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>TAppletViewShop数据层的基础操作</p>
 * @ClassName: TAppletViewShopMapper
 * @Description: TAppletViewShop数据层的基础操作
 * @author Generator
 * @date 2018年11月23日 09时16分48秒
 */
@Mapper
public interface TAppletViewShopMapper extends BaseMapper<TAppletViewShop, TAppletViewShopCondition>{

    List<AppletVisitVo> getAppletVisitList(TAppletViewShopCondition req);
}
