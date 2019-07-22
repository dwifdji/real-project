
package com._360pai.core.dao.pay.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.pay.GatewayPayOrderCondition;
import com._360pai.core.model.pay.GatewayPayOrder;
import com._360pai.arch.core.abs.BaseMapper;

import java.util.List;

/**
 * <p>GatewayPayOrder数据层的基础操作</p>
 * @ClassName: GatewayPayOrderMapper
 * @Description: GatewayPayOrder数据层的基础操作
 * @author Generator
 * @date 2018年09月15日 18时40分38秒
 */
@Mapper
public interface GatewayPayOrderMapper extends BaseMapper<GatewayPayOrder, GatewayPayOrderCondition>{

    List<GatewayPayOrder> getNoticeList();
}
