
package com._360pai.core.dao.applet.mapper;

import com._360pai.core.facade.assistant.vo.BackAppletHallActivity;
import com._360pai.core.facade.shop.vo.AppletHallActivityVO;
import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.applet.TAppletHallActivityCondition;
import com._360pai.core.model.applet.TAppletHallActivity;
import com._360pai.arch.core.abs.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>TAppletHallActivity数据层的基础操作</p>
 * @ClassName: TAppletHallActivityMapper
 * @Description: TAppletHallActivity数据层的基础操作
 * @author Generator
 * @date 2019年02月26日 14时42分55秒
 */
@Mapper
public interface TAppletHallActivityMapper extends BaseMapper<TAppletHallActivity, TAppletHallActivityCondition>{
    List<AppletHallActivityVO> getFrontList(Map<String, Object> params);

    List<BackAppletHallActivity> getBackList(Map<String, Object> params);
}
