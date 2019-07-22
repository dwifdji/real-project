package com.tzCloud.core.dao.view.impl;

import com.tzCloud.core.condition.view.ViewBriefCourtCondition;
import com.tzCloud.core.condition.view.ViewBriefCourtLawfirmCondition;
import com.tzCloud.core.condition.view.ViewBriefLawfirmLawyerCondition;
import com.tzCloud.core.condition.view.ViewBriefLawyerRankingCondition;
import com.tzCloud.core.dao.view.ViewTableDao;
import com.tzCloud.core.dao.view.mapper.ViewTableMapper;
import com.tzCloud.core.facade.caseMatching.resp.CourtResp;
import com.tzCloud.core.facade.caseMatching.resp.LawyerFirmResp;
import com.tzCloud.core.facade.caseMatching.resp.LawyerForRankingListResp;
import com.tzCloud.core.facade.caseMatching.resp.LawyerForSearchingListResp;
import com.tzCloud.core.model.view.ViewBriefLawfirmLawyer;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2019/3/26 13:45
 */
@Repository
public class ViewTableDaoImpl implements ViewTableDao {

    @Resource
    private ViewTableMapper viewTableMapper;

    @Override
    public List<CourtResp> getCourtList(ViewBriefCourtCondition viewBriefCourtCondition) {
        return viewTableMapper.getCourtList(viewBriefCourtCondition);
    }

    @Override
    public Integer getTotalCaseCount(String briefStr) {
        return viewTableMapper.getTotalCaseCount(briefStr);
    }

    @Override
    public List<LawyerFirmResp> getLawFirmList(ViewBriefCourtLawfirmCondition req) {
        return viewTableMapper.getLawFirmList(req);
    }

    @Override
    public List<LawyerForSearchingListResp> getLawyerList(ViewBriefLawfirmLawyerCondition condition) {
        return viewTableMapper.getLawyerList(condition);
    }

    @Override
    public List<ViewBriefLawfirmLawyer> selectNeedProcessList(ViewBriefLawfirmLawyerCondition condition) {
        return viewTableMapper.selectNeedProcessList(condition);
    }

    @Override
    public List<LawyerForRankingListResp> getLawyerRankingList(ViewBriefLawyerRankingCondition condition) {
        return viewTableMapper.getLawyerRankingList(condition);
    }

    @Override
    public String getCollegialPanel(String docId) {
        return viewTableMapper.getCollegialPanel(docId);
    }

    @Override
    public List<Map<String, Object>> getInitLawyerList(String brief) {
        return viewTableMapper.getInitLawyerList(brief);
    }

    @Override
    public List<Map<String,Object>> getInitBrief() {
        return viewTableMapper.getInitBrief();
    }
}
