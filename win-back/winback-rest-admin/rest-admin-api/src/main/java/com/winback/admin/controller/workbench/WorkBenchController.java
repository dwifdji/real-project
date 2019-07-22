package com.winback.admin.controller.workbench;

import com.alibaba.dubbo.config.annotation.Reference;
import com.winback.arch.common.ResponseModel;
import com.winback.core.facade.systemsite.req.SystemSiteReq;
import com.winback.core.facade.workbench.WorkBenchFacade;
import com.winback.core.facade.workbench.req.WorkBenchReq;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WorkBenchController {

    @Reference(version = "1.0.0")
    private WorkBenchFacade workBenchFacade;

    /**
     * 今日新增用户，律师，律所，案件
     * @param
     * @return
     */
    @RequiresPermissions("workbench_mgt_1_1_1")
    @RequestMapping("/confined/workbench/getTodayRole")
    public ResponseModel getTodayRole(WorkBenchReq.SearchDay searchDay){

        return workBenchFacade.getTodayRole(searchDay);
    }

    /**
     * 查找不同的案件统计
     * @param
     * @return
     */
    @RequiresPermissions("workbench_mgt_1_1_1")
    @RequestMapping("/confined/workbench/getStatusCase")
    public ResponseModel getStatusCase(){

        return workBenchFacade.getStatusCase();
    }

    /**
     * 查找财务控制台
     * @param
     * @return
     */
    @RequiresPermissions("workbench_mgt_1_1_1")
    @RequestMapping("/confined/workbench/getFinance")
    public ResponseModel getFinance(WorkBenchReq.FinanceReq financeReq){

        return workBenchFacade.getFinance(financeReq);
    }

}
