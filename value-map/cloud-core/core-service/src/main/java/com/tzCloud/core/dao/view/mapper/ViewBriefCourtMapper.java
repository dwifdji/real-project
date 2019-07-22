
package com.tzCloud.core.dao.view.mapper;

import com.tzCloud.core.facade.caseMatching.resp.CourtResp;
import org.apache.ibatis.annotations.Mapper;

import com.tzCloud.core.condition.view.ViewBriefCourtCondition;
import com.tzCloud.core.model.view.ViewBriefCourt;
import com.tzCloud.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>ViewBriefCourt数据层的基础操作</p>
 * @ClassName: ViewBriefCourtMapper
 * @Description: ViewBriefCourt数据层的基础操作
 * @author Generator
 * @date 2019年03月15日 11时13分23秒
 */
@Mapper
public interface ViewBriefCourtMapper extends BaseMapper<ViewBriefCourt, ViewBriefCourtCondition>{

    List<CourtResp> getCourtList(ViewBriefCourtCondition viewBriefCourtCondition);
    Integer getTotalCaseCount(@Param("briefStr") String briefStr);
}
