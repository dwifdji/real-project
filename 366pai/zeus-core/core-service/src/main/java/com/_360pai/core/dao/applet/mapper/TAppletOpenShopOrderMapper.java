
package com._360pai.core.dao.applet.mapper;

import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.applet.TAppletOpenShopOrderCondition;
import com._360pai.core.model.applet.TAppletOpenShopOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>TAppletOpenShopOrder数据层的基础操作</p>
 * @ClassName: TAppletOpenShopOrderMapper
 * @Description: TAppletOpenShopOrder数据层的基础操作
 * @author Generator
 * @date 2018年11月28日 13时30分57秒
 */
@Mapper
public interface TAppletOpenShopOrderMapper extends BaseMapper<TAppletOpenShopOrder, TAppletOpenShopOrderCondition> {

    List<TAppletOpenShopOrder> getAppletNotPayOrderList();
}
