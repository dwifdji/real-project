
package com._360pai.core.dao.applet.mapper;

import com._360pai.core.facade.shop.vo.AppletMessageVO;
import com._360pai.core.facade.shop.vo.ShopMessageTypeVO;
import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.applet.TAppletMessageCondition;
import com._360pai.core.model.applet.TAppletMessage;
import com._360pai.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>TAppletMessage数据层的基础操作</p>
 * @ClassName: TAppletMessageMapper
 * @Description: TAppletMessage数据层的基础操作
 * @author Generator
 * @date 2018年11月22日 14时40分30秒
 */
@Mapper
public interface TAppletMessageMapper extends BaseMapper<TAppletMessage, TAppletMessageCondition>{

    List<AppletMessageVO> getShopMessageList(Map<String, Object> params);

    List<ShopMessageTypeVO> getMyShopMessage(Map<String, Object> params);

    List<AppletMessageVO> getAnnoucementList(Map<String, Object> params);

    String getRecentMessageAt(Map<String, Object> params);

    String getRecentAnnouncementAt(Map<String, Object> params);
}
