
package com.winback.core.dao.applet.mapper;

import com.winback.arch.core.abs.BaseMapper;
import com.winback.core.condition.applet.TAppletMessageCondition;
import com.winback.core.facade.account.vo.AppletMessage;
import com.winback.core.model.applet.TAppletMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>TAppletMessage数据层的基础操作</p>
 * @ClassName: TAppletMessageMapper
 * @Description: TAppletMessage数据层的基础操作
 * @author Generator
 * @date 2019年01月16日 15时40分04秒
 */
@Mapper
public interface TAppletMessageMapper extends BaseMapper<TAppletMessage, TAppletMessageCondition>{
    List<AppletMessage> getAppletMessageList(Map<String, Object> params);

    int unreadMessageCount(@Param("extBindId") Integer extBindId);
}
