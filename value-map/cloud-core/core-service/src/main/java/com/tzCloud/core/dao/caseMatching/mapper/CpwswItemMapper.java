
package com.tzCloud.core.dao.caseMatching.mapper;

import com.tzCloud.core.facade.caseMatching.resp.*;
import org.apache.ibatis.annotations.Mapper;

import com.tzCloud.core.condition.caseMatching.CpwswItemCondition;
import com.tzCloud.core.model.caseMatching.CpwswItem;
import com.tzCloud.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>CpwswItem数据层的基础操作</p>
 *
 * @author Generator
 * @ClassName: CpwswItemMapper
 * @Description: CpwswItem数据层的基础操作
 * @date 2019年03月07日 16时22分18秒
 */
@Mapper
public interface CpwswItemMapper extends BaseMapper<CpwswItem, CpwswItemCondition> {

    List<CourtResp> getCourtListByBrief(CpwswItemCondition condition);

    List<LawyerFirmResp> getLawFirmList(CpwswItemCondition condition);

    List<LawyerForSearchingListResp> getLawyerList(CpwswItemCondition condition);

    List<LawyerForRankingListResp> getLawyerRankingList(CpwswItemCondition condition);

    /**
     * @param lawyer
     * @param brief
     * @return caseNum
     * rate
     */
    Map<String, Object> getCaseNumAndRates(@Param("lawyer") String lawyer, @Param("brief")String brief);

    List<CaseListForLawyerResp> getCaseListByLawyer(CpwswItemCondition condition);

    List<CaseListForLawyerResp> getCaseListByLawyer2(CpwswItemCondition condition);

    List<LawyerDetailResp> getLawyerDetail(CpwswItemCondition condition);

    List<Map> getCourtList(CpwswItemCondition condition);

    List<Map> getCountGroupByBrief(CpwswItemCondition condition);

    List<Map<String,Object>> getCaseCountByMonthList(CpwswItemCondition condition);

    Integer getTotalCaseNum(@Param("lawyer") String lawyer);
}
