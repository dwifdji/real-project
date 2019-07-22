
package com.tzCloud.core.dao.view;

import com.tzCloud.core.condition.view.ViewBriefCourtCondition;
import com.tzCloud.core.facade.caseMatching.resp.CourtResp;
import com.tzCloud.core.model.view.ViewBriefCourt;
import com.tzCloud.arch.core.abs.BaseDao;

import java.util.List;

/**
 * <p>ViewBriefCourt的基础操作Dao</p>
 * @ClassName: ViewBriefCourtDao
 * @Description: ViewBriefCourt基础操作的Dao
 * @author Generator
 * @date 2019年03月15日 11时13分23秒
 */
public interface ViewBriefCourtDao extends BaseDao<ViewBriefCourt,ViewBriefCourtCondition>{

    List<CourtResp> getCourtList(ViewBriefCourtCondition viewBriefCourtCondition);

    Integer getTotalCaseCount(String briefStr);
}
