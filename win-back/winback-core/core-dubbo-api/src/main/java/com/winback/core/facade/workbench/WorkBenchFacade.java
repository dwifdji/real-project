package com.winback.core.facade.workbench;

import com.winback.arch.common.ResponseModel;
import com.winback.core.facade.workbench.req.WorkBenchReq;

public interface WorkBenchFacade {

    ResponseModel getFinance(WorkBenchReq.FinanceReq financeReq);

    ResponseModel getStatusCase();

    ResponseModel getTodayRole(WorkBenchReq.SearchDay searchDay);
}
