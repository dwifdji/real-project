
package com.winback.core.dao.applet;

import com.github.pagehelper.PageInfo;
import com.winback.arch.core.abs.BaseDao;
import com.winback.core.condition.applet.TAppletMessageCondition;
import com.winback.core.facade.account.vo.AppletMessage;
import com.winback.core.model.applet.TAppletMessage;

import java.util.Map;

/**
 * <p>TAppletMessage的基础操作Dao</p>
 * @ClassName: TAppletMessageDao
 * @Description: TAppletMessage基础操作的Dao
 * @author Generator
 * @date 2019年01月16日 15时40分04秒
 */
public interface TAppletMessageDao extends BaseDao<TAppletMessage,TAppletMessageCondition>{
    PageInfo<AppletMessage> getAppletMessageList(int page, int perPage, Map<String, Object> params, String orderBy);

    int unreadMessageCount(Integer extBindId);

}
