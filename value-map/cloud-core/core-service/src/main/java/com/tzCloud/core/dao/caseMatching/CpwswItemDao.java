
package com.tzCloud.core.dao.caseMatching;

import com.tzCloud.core.condition.caseMatching.CpwswItemCondition;
import com.tzCloud.core.facade.caseMatching.resp.*;
import com.tzCloud.core.model.caseMatching.CpwswItem;
import com.tzCloud.arch.core.abs.BaseDao;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * <p>CpwswItem的基础操作Dao</p>
 * @ClassName: CpwswItemDao
 * @Description: CpwswItem基础操作的Dao
 * @author Generator
 * @date 2019年03月07日 16时22分18秒
 */
public interface CpwswItemDao extends BaseDao<CpwswItem,CpwswItemCondition>{

    List<CourtResp> getCourtListByBrief(CpwswItemCondition condition);

    List<LawyerFirmResp> getLawFirmList(CpwswItemCondition condition);

    List<LawyerForSearchingListResp> getLawyerList(CpwswItemCondition condition);

    List<LawyerForRankingListResp> getLawyerRankingList(CpwswItemCondition condition);

    Map<String,Object> getCaseNumAndRates(String lawyerName, String brief);

    Future<Integer> getTotalCaseNum(String lawyerName);

    List<CaseListForLawyerResp> getCaseListByLawyer(CpwswItemCondition condition);
    List<CaseListForLawyerResp> getCaseListByLawyer2(CpwswItemCondition condition);

    Future<List<LawyerDetailResp>> getLawyerDetail(CpwswItemCondition condition);

    Future<List<Map>> getCourtList(CpwswItemCondition condition);

    Future<List<Map>> getCountGroupByBrief(CpwswItemCondition condition);

    Future<List<Map<String,Object>>> getCaseCountByMonthList(CpwswItemCondition condition);
}
