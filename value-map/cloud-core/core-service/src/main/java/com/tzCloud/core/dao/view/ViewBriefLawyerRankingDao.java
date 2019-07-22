
package com.tzCloud.core.dao.view;

import com.tzCloud.core.condition.view.ViewBriefLawyerRankingCondition;
import com.tzCloud.core.facade.caseMatching.resp.LawyerForRankingListResp;
import com.tzCloud.core.model.view.ViewBriefLawyerRanking;
import com.tzCloud.arch.core.abs.BaseDao;

import java.util.List;

/**
 * <p>ViewBriefLawyerRanking的基础操作Dao</p>
 * @ClassName: ViewBriefLawyerRankingDao
 * @Description: ViewBriefLawyerRanking基础操作的Dao
 * @author Generator
 * @date 2019年03月15日 11时13分23秒
 */
public interface ViewBriefLawyerRankingDao extends BaseDao<ViewBriefLawyerRanking,ViewBriefLawyerRankingCondition>{

    List<LawyerForRankingListResp> getLawyerRankingList(ViewBriefLawyerRankingCondition condition);
}
