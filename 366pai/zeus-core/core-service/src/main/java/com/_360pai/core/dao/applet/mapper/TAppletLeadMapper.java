
package com._360pai.core.dao.applet.mapper;

import com._360pai.core.facade.shop.vo.ShopGuideVO;
import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.applet.TAppletLeadCondition;
import com._360pai.core.model.applet.TAppletLead;
import com._360pai.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>TAppletLead数据层的基础操作</p>
 * @ClassName: TAppletLeadMapper
 * @Description: TAppletLead数据层的基础操作
 * @author Generator
 * @date 2018年11月22日 14时40分30秒
 */
@Mapper
public interface TAppletLeadMapper extends BaseMapper<TAppletLead, TAppletLeadCondition>{

    List<ShopGuideVO> getRemainingGuides(@Param("openId") String openId);
}
