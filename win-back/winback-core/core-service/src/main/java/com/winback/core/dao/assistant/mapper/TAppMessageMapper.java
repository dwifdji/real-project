
package com.winback.core.dao.assistant.mapper;

import com.winback.core.facade.account.vo.AppMessage;
import com.winback.core.model.account.TAccount;
import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition.assistant.TAppMessageCondition;
import com.winback.core.model.assistant.TAppMessage;
import com.winback.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>TAppMessage数据层的基础操作</p>
 * @ClassName: TAppMessageMapper
 * @Description: TAppMessage数据层的基础操作
 * @author Generator
 * @date 2019年01月18日 10时01分28秒
 */
@Mapper
public interface TAppMessageMapper extends BaseMapper<TAppMessage, TAppMessageCondition>{
    List<AppMessage> getAppMessageList(Map<String, Object> params);

    int unreadMessageCount(@Param("accountId") Integer accountId);


    int unreadConnectCount(@Param("accountId")Integer accountId, @Param("type")String type);

}
