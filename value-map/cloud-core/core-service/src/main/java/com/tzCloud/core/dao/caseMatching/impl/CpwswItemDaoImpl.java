
package com.tzCloud.core.dao.caseMatching.impl;

import javax.annotation.Resource;

import com.tzCloud.core.facade.caseMatching.resp.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.tzCloud.core.condition.caseMatching.CpwswItemCondition;
import com.tzCloud.core.dao.caseMatching.mapper.CpwswItemMapper;
import com.tzCloud.core.model.caseMatching.CpwswItem;
import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.arch.core.abs.AbstractDaoImpl;
import com.tzCloud.core.dao.caseMatching.CpwswItemDao;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

@Service
public class CpwswItemDaoImpl extends AbstractDaoImpl<CpwswItem, CpwswItemCondition, BaseMapper<CpwswItem, CpwswItemCondition>> implements CpwswItemDao {

    @Resource
    private CpwswItemMapper cpwswItemMapper;

    @Override
    protected BaseMapper<CpwswItem, CpwswItemCondition> daoSupport() {
        return cpwswItemMapper;
    }

    @Override
    protected CpwswItemCondition blankCondition() {
        return new CpwswItemCondition();
    }

    @Override
    public List<CourtResp> getCourtListByBrief(CpwswItemCondition condition) {

        return cpwswItemMapper.getCourtListByBrief(condition);
    }

    @Override
    public List<LawyerFirmResp> getLawFirmList(CpwswItemCondition condition) {

        return cpwswItemMapper.getLawFirmList(condition);
    }

    @Override
    public List<LawyerForSearchingListResp> getLawyerList(CpwswItemCondition condition) {
        return cpwswItemMapper.getLawyerList(condition);

    }

    @Override
    public List<LawyerForRankingListResp> getLawyerRankingList(CpwswItemCondition condition) {

        return cpwswItemMapper.getLawyerRankingList(condition);
    }

    @Override
    public Map<String, Object> getCaseNumAndRates(String lawyer, String brief) {
        return cpwswItemMapper.getCaseNumAndRates(lawyer, brief);
    }

    @Override
    @Async("taskExecutor")
    public Future<Integer> getTotalCaseNum(String lawyerName) {
        Integer totalCaseNum = cpwswItemMapper.getTotalCaseNum(lawyerName);
        return new AsyncResult<> (totalCaseNum);
    }

    @Override
    public List<CaseListForLawyerResp> getCaseListByLawyer(CpwswItemCondition condition) {
        return cpwswItemMapper.getCaseListByLawyer(condition);
    }

    @Override
    public List<CaseListForLawyerResp> getCaseListByLawyer2(CpwswItemCondition condition) {
        return cpwswItemMapper.getCaseListByLawyer2(condition);
    }

    @Override
    @Async("taskExecutor")
    public Future<List<LawyerDetailResp>> getLawyerDetail(CpwswItemCondition condition) {
        List<LawyerDetailResp> list = cpwswItemMapper.getLawyerDetail(condition);
        return new AsyncResult<>(list);
    }

    @Override
    @Async("taskExecutor")
    public Future<List<Map>> getCourtList(CpwswItemCondition condition) {
        List<Map> list = cpwswItemMapper.getCourtList(condition);
        return new AsyncResult<>(list);
    }

    @Override
    @Async("taskExecutor")
    public  Future<List<Map>> getCountGroupByBrief(CpwswItemCondition condition) {
        List<Map> list = cpwswItemMapper.getCountGroupByBrief(condition);
        return new AsyncResult<>(list);
    }

    @Override
    @Async("taskExecutor")
    public  Future<List<Map<String, Object>>> getCaseCountByMonthList(CpwswItemCondition condition) {
        List<Map<String, Object>> list = cpwswItemMapper.getCaseCountByMonthList(condition);
        return new AsyncResult<>(list);
    }

}
