
package com.winback.core.dao.assistant;

import com.github.pagehelper.PageInfo;
import com.winback.arch.core.abs.BaseDao;
import com.winback.core.condition.assistant.TAppMessageCondition;
import com.winback.core.facade.account.vo.AppMessage;
import com.winback.core.model.assistant.TAppMessage;

import java.util.Map;

/**
 * <p>TAppMessage的基础操作Dao</p>
 * @ClassName: TAppMessageDao
 * @Description: TAppMessage基础操作的Dao
 * @author Generator
 * @date 2019年01月18日 10时01分28秒
 */
public interface TAppMessageDao extends BaseDao<TAppMessage,TAppMessageCondition>{
    PageInfo<AppMessage> getAppMessageList(int page, int perPage, Map<String, Object> params, String orderBy);

    int unreadMessageCount(Integer accountId);


    int unreadConnectCount(Integer accountId,String type);
}
