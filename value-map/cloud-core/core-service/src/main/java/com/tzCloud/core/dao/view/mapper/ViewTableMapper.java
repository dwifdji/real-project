package com.tzCloud.core.dao.view.mapper;

import com.tzCloud.core.condition.view.ViewBriefCourtCondition;
import com.tzCloud.core.condition.view.ViewBriefCourtLawfirmCondition;
import com.tzCloud.core.condition.view.ViewBriefLawfirmLawyerCondition;
import com.tzCloud.core.condition.view.ViewBriefLawyerRankingCondition;
import com.tzCloud.core.facade.caseMatching.resp.CourtResp;
import com.tzCloud.core.facade.caseMatching.resp.LawyerFirmResp;
import com.tzCloud.core.facade.caseMatching.resp.LawyerForRankingListResp;
import com.tzCloud.core.facade.caseMatching.resp.LawyerForSearchingListResp;
import com.tzCloud.core.model.view.ViewBriefLawfirmLawyer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2019/3/26 13:45
 */
@Mapper
public interface ViewTableMapper {

    List<LawyerFirmResp> getLawFirmList(ViewBriefCourtLawfirmCondition req);

    List<CourtResp> getCourtList(ViewBriefCourtCondition viewBriefCourtCondition);

    Integer getTotalCaseCount(@Param("briefStr") String briefStr);

    List<LawyerForSearchingListResp> getLawyerList(ViewBriefLawfirmLawyerCondition condition);

    List<ViewBriefLawfirmLawyer> selectNeedProcessList(ViewBriefLawfirmLawyerCondition condition);

    List<LawyerForRankingListResp> getLawyerRankingList(ViewBriefLawyerRankingCondition condition);

    String getCollegialPanel(@Param("docId") String docId);

    List<Map<String,Object>> getInitLawyerList(@Param("brief") String brief);

    List<Map<String,Object>> getInitBrief();
}
