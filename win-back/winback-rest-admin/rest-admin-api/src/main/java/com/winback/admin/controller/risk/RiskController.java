package com.winback.admin.controller.risk;


import com.alibaba.dubbo.config.annotation.Reference;
import com.winback.admin.controller.AbstractController;
import com.winback.admin.vo.LoginInfo;
import com.winback.arch.common.ResponseModel;
import com.winback.arch.common.enums.ApiCallResult;
import com.winback.core.facade.risk.RiskFacade;
import com.winback.core.facade.risk.req.RiskReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * 描述：风控预检接口
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2019/1/22 11:09
 */
@Slf4j
@RestController
public class RiskController extends AbstractController {



    @Reference(version = "1.0.0")
    private RiskFacade riskFacade;


    /**
     * 获取风控公司信息
     * @return
     */
    @RequiresPermissions("risk_mgt_1_1_1")
    @GetMapping("/confined/risk/getComInfo")
    public ResponseModel getComInfo(RiskReq.keyWordReq req) {


        if(StringUtils.isBlank(req.getKeyWord())) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }
        LoginInfo info = loadCurLoginInfo();
        req.setOperatorName(info.getName());

        req.setKeyWord(req.getKeyWord().trim());
        return riskFacade.getComInfo(req);
    }




    /**
     * 获取对外投资列表
     * @return
     */
    @RequiresPermissions("risk_mgt_1_1_1")
    @GetMapping("/confined/risk/getInvestList")
    public ResponseModel getInvestList(RiskReq.keyWordReq req) {

        if(StringUtils.isBlank(req.getKeyWord())) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }
        LoginInfo info = loadCurLoginInfo();
        req.setOperatorName(info.getName());
        req.setKeyWord(req.getKeyWord().trim());
        return riskFacade.getInvestList(req);
    }





    /**
     * 获取股权穿透信息
     * @return
     */
    @GetMapping("/open/risk/getEquityInfo")
    public ResponseModel getEquityInfo(RiskReq.keyWordReq req) {

        if(StringUtils.isBlank(req.getKeyWord())) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }
        LoginInfo info = loadCurLoginInfo();
        req.setOperatorName(info.getName());
        req.setKeyWord(req.getKeyWord().trim());
        return riskFacade.getEquityInfo(req);
    }

}
