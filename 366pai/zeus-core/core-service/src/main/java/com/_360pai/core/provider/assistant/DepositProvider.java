package com._360pai.core.provider.assistant;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.core.facade.assistant.DepositFacade;
import com._360pai.core.facade.assistant.req.DepositReq;
import com._360pai.core.facade.assistant.resp.DepositResp;
import com._360pai.core.facade.assistant.vo.DepositVo;
import com._360pai.core.service.assistant.DepositService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zxiao
 * @Title: DepositProvider
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/12 14:00
 */
@Component
@Service(version = "1.0.0")
public class DepositProvider implements DepositFacade {

    @Resource
    private DepositService depositService;

    @Override
    public PageInfo myDepositList(DepositReq.BaseReq req) {
        return depositService.myDepositList(req.getPage(),req.getPerPage(),req.getPartyId(),req.getCategoryId(),req.getName());
    }

    @Override
    public PageInfoResp<DepositVo> getDepositOfflineListByPage(DepositReq.QueryReq req) {
        return depositService.getDepositOfflineListByPage(req);
    }

    @Override
    public DepositResp.DetailResp getDepositOffline(DepositReq.BaseReq req) {
        DepositReq.QueryReq queryReq = new DepositReq.QueryReq();
        queryReq.setId(req.getId());
        PageInfoResp<DepositVo> pageInfoResp = depositService.getDepositOfflineListByPage(queryReq);
        DepositResp.DetailResp resp = new DepositResp.DetailResp();
        if (pageInfoResp.getList().size() > 0) {
            resp.setDeposit(pageInfoResp.getList().get(0));
        }
        return resp;
    }

    @Override
    public PageInfoResp<DepositVo> getDepositOfflineRefundListByPage(DepositReq.QueryReq req) {
        return depositService.getDepositOfflineRefundListByPage(req);
    }

    @Override
    public DepositResp.DetailResp getDepositOfflineRefund(DepositReq.BaseReq req) {
        DepositReq.QueryReq queryReq = new DepositReq.QueryReq();
        queryReq.setId(req.getId());
        PageInfoResp<DepositVo> pageInfoResp = depositService.getDepositOfflineRefundListByPage(queryReq);
        DepositResp.DetailResp resp = new DepositResp.DetailResp();
        if (pageInfoResp.getList().size() > 0) {
            resp.setDeposit(pageInfoResp.getList().get(0));
        }
        return resp;
    }

    @Override
    public DepositResp receiveDeposit(DepositReq.OfflineConfirmReq req) {
        return depositService.receiveDeposit(req);
    }

    @Override
    public DepositResp refundDeposit(DepositReq.OfflineConfirmReq req) {
        return depositService.refundDeposit(req);
    }

    @Override
    public DepositResp transferDeposit(DepositReq.OfflineConfirmReq req) {
        return depositService.transferDeposit(req);
    }
}
