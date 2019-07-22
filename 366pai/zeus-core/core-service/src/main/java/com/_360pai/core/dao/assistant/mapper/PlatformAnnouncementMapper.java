
package com._360pai.core.dao.assistant.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.assistant.PlatformAnnouncementCondition;
import com._360pai.core.model.assistant.PlatformAnnouncement;
import com._360pai.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>PlatformAnnouncement数据层的基础操作</p>
 *
 * @author Generator
 * @ClassName: PlatformAnnouncementMapper
 * @Description: PlatformAnnouncement数据层的基础操作
 * @date 2018年08月10日 17时37分17秒
 */
@Mapper
public interface PlatformAnnouncementMapper extends BaseMapper<PlatformAnnouncement, PlatformAnnouncementCondition> {
    int deletePlatformAnnouncement(@Param("paramId") Integer paramsId);

    int addViewCount(@Param("id") Integer id);
}
