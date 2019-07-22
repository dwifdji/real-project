
package com._360pai.core.dao.applet;

import com._360pai.core.condition.applet.TAppletMessageCondition;
import com._360pai.core.facade.shop.vo.AppletMessageVO;
import com._360pai.core.facade.shop.vo.ShopMessageTypeVO;
import com._360pai.core.model.applet.TAppletMessage;
import com._360pai.arch.core.abs.BaseDao;

import java.util.List;
import java.util.Map;

/**
 * <p>TAppletMessage的基础操作Dao</p>
 * @ClassName: TAppletMessageDao
 * @Description: TAppletMessage基础操作的Dao
 * @author Generator
 * @date 2018年11月22日 14时40分30秒
 */
public interface TAppletMessageDao extends BaseDao<TAppletMessage,TAppletMessageCondition>{

    List<AppletMessageVO> getShopMessageList(Map<String, Object> params);

    List<ShopMessageTypeVO> getMyShopMessage(Map<String, Object> params);

    List<AppletMessageVO> getAnnoucementList(Map<String, Object> params);

    String getRecentMessageAt(Map<String, Object> params);

    String getRecentAnnouncementAt(Map<String, Object> params);
}
