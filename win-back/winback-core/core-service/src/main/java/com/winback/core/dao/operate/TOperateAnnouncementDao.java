
package com.winback.core.dao.operate;

import com.winback.core.condition.operate.TOperateAnnouncementCondition;
import com.winback.core.dto.operate.OperateAnnouncementDto;
import com.winback.core.model.operate.TOperateAnnouncement;
import com.winback.arch.core.abs.BaseDao;
import com.winback.core.vo.operate.AnnouncementDetailVO;
import com.winback.core.vo.operate.HomeAnnouncementVO;
import com.winback.core.vo.operate.OperateAnnouncementVO;

import java.util.List;

/**
 * <p>TOperateAnnouncement的基础操作Dao</p>
 * @ClassName: TOperateAnnouncementDao
 * @Description: TOperateAnnouncement基础操作的Dao
 * @author Generator
 * @date 2019年01月25日 17时01分48秒
 */
public interface TOperateAnnouncementDao extends BaseDao<TOperateAnnouncement,TOperateAnnouncementCondition>{

    List<OperateAnnouncementVO> getAnnouncementList(OperateAnnouncementDto params);

    List<HomeAnnouncementVO> getHomeAnnouncementList(OperateAnnouncementDto params);

    AnnouncementDetailVO getAnnouncementById(String id);
}
