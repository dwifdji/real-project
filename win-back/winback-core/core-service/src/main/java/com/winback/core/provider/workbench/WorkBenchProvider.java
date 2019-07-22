package com.winback.core.provider.workbench;

import com.alibaba.dubbo.config.annotation.Service;
import com.winback.arch.common.ResponseModel;
import com.winback.arch.common.utils.DateUtil;
import com.winback.core.facade.workbench.WorkBenchFacade;
import com.winback.core.facade.workbench.req.WorkBenchReq;
import com.winback.core.service._case.CaseService;
import com.winback.core.service.account.AccountService;
import com.winback.core.service.finance.FinanceService;
import com.winback.core.vo.finance.WorkBenchVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by liuhaolei on 2018/2/13
 * 工作台生产者
 */
@Component
@Service(version = "1.0.0")
public class WorkBenchProvider implements WorkBenchFacade {

    @Autowired
    private FinanceService financeService;
    @Autowired
    private CaseService caseService;
    @Autowired
    private AccountService accountService;

    @Override
    public ResponseModel getFinance(WorkBenchReq.FinanceReq financeReq) {
        List<WorkBenchVO> financeList = financeService.getFinanceWorkBench();

        Map<String, Object> result = new HashMap<>();
        result.put("list", financeList);
        return ResponseModel.succ(result);
    }

    @Override
    public ResponseModel getStatusCase() {
        List<WorkBenchVO> statusCaseList = caseService.getStatusCase();

        Map<String, Object> result = new HashMap<>();
        result.put("list", statusCaseList);
        return ResponseModel.succ(result);
    }

    @Override
    public ResponseModel getTodayRole(WorkBenchReq.SearchDay searchDay) {
        String searchDaySt = searchDay.getSearchDay()==null?
                DateUtil.formatNormDate(new Date()):searchDay.getSearchDay();
        List<WorkBenchVO> accountList = accountService.getTodayRole(searchDaySt);
        for (WorkBenchVO workBenchVO : accountList) {
            workBenchVO.setParams(workBenchVO.getParams() == null ?
                    searchDay.getSearchDay() : workBenchVO.getParams());
        }

        Map<String, Object> result = new HashMap<>();
        result.put("list", accountList);
        return ResponseModel.succ(result);
    }
}
