
package com.winback.core.dao.operate.mapper;

import com.winback.core.dto.operate.OperateAnnouncementDto;
import com.winback.core.vo.operate.AnnouncementDetailVO;
import com.winback.core.vo.operate.HomeAnnouncementVO;
import com.winback.core.vo.operate.OperateAnnouncementVO;
import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition.operate.TOperateAnnouncementCondition;
import com.winback.core.model.operate.TOperateAnnouncement;
import com.winback.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>TOperateAnnouncement数据层的基础操作</p>
 * @ClassName: TOperateAnnouncementMapper
 * @Description: TOperateAnnouncement数据层的基础操作
 * @author Generator
 * @date 2019年01月25日 17时01分48秒
 */
@Mapper
public interface TOperateAnnouncementMapper extends BaseMapper<TOperateAnnouncement, TOperateAnnouncementCondition>{

    List<OperateAnnouncementVO> getAnnouncementList(OperateAnnouncementDto params);

    List<HomeAnnouncementVO> getHomeAnnouncementList(OperateAnnouncementDto params);

    AnnouncementDetailVO getAnnouncementById(@Param("id") String id);
}
