
package com.tzCloud.core.dao.view.mapper;

import com.tzCloud.core.facade.caseMatching.resp.LawyerForRankingListResp;
import org.apache.ibatis.annotations.Mapper;

import com.tzCloud.core.condition.view.ViewBriefLawyerRankingCondition;
import com.tzCloud.core.model.view.ViewBriefLawyerRanking;
import com.tzCloud.arch.core.abs.BaseMapper;

import java.util.List;

/**
 * <p>ViewBriefLawyerRanking数据层的基础操作</p>
 * @ClassName: ViewBriefLawyerRankingMapper
 * @Description: ViewBriefLawyerRanking数据层的基础操作
 * @author Generator
 * @date 2019年03月15日 11时13分23秒
 */
@Mapper
public interface ViewBriefLawyerRankingMapper extends BaseMapper<ViewBriefLawyerRanking, ViewBriefLawyerRankingCondition>{

    List<LawyerForRankingListResp> getLawyerRankingList(ViewBriefLawyerRankingCondition condition);
}
