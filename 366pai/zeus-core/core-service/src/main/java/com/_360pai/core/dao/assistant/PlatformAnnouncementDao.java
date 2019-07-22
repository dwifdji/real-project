
package com._360pai.core.dao.assistant;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.assistant.PlatformAnnouncementCondition;
import com._360pai.core.model.assistant.PlatformAnnouncement;

/**
 * <p>PlatformAnnouncement的基础操作Dao</p>
 * @ClassName: PlatformAnnouncementDao
 * @Description: PlatformAnnouncement基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分17秒
 */
public interface PlatformAnnouncementDao extends BaseDao<PlatformAnnouncement,PlatformAnnouncementCondition>{

    int deletePlatformAnnouncement(Integer paramsId);

    int addViewCount(Integer id);
}
