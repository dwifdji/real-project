
package com._360pai.core.dao.applet;

import com._360pai.core.condition.applet.TAppletHallActivityCondition;
import com._360pai.core.facade.assistant.vo.BackAppletHallActivity;
import com._360pai.core.facade.shop.vo.AppletHallActivityVO;
import com._360pai.core.model.applet.TAppletHallActivity;
import com._360pai.arch.core.abs.BaseDao;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * <p>TAppletHallActivity的基础操作Dao</p>
 * @ClassName: TAppletHallActivityDao
 * @Description: TAppletHallActivity基础操作的Dao
 * @author Generator
 * @date 2019年02月26日 14时42分55秒
 */
public interface TAppletHallActivityDao extends BaseDao<TAppletHallActivity,TAppletHallActivityCondition>{
    PageInfo<AppletHallActivityVO> getFrontList(int page, int perPage, Map<String, Object> params, String orderBy);

    PageInfo<BackAppletHallActivity> getBackList(int page, int perPage, Map<String, Object> params, String orderBy);

    TAppletHallActivity findBy(String type, Integer activityId);
}
