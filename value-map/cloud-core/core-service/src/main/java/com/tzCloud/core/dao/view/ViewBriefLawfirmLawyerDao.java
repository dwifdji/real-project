
package com.tzCloud.core.dao.view;

import com.tzCloud.core.condition.view.ViewBriefLawfirmLawyerCondition;
import com.tzCloud.core.facade.caseMatching.resp.LawyerForSearchingListResp;
import com.tzCloud.core.model.view.ViewBriefLawfirmLawyer;
import com.tzCloud.arch.core.abs.BaseDao;

import java.util.List;

/**
 * <p>ViewBriefLawfirmLawyer的基础操作Dao</p>
 * @ClassName: ViewBriefLawfirmLawyerDao
 * @Description: ViewBriefLawfirmLawyer基础操作的Dao
 * @author Generator
 * @date 2019年03月15日 11时13分23秒
 */
public interface ViewBriefLawfirmLawyerDao extends BaseDao<ViewBriefLawfirmLawyer,ViewBriefLawfirmLawyerCondition>{

    List<LawyerForSearchingListResp> getLawyerList(ViewBriefLawfirmLawyerCondition condition);

    List<ViewBriefLawfirmLawyer> selectNeedProcessList(ViewBriefLawfirmLawyerCondition condition);
}
