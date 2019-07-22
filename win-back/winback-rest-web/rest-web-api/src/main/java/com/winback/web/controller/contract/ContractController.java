package com.winback.web.controller.contract;

import com.alibaba.dubbo.config.annotation.Reference;
import com.winback.arch.common.ResponseModel;
import com.winback.core.facade.contract.ContractFacade;
import com.winback.core.facade.contract.req.AppContractReq;
import com.winback.core.facade.contract.req.AppletContractReq;
import com.winback.web.controller.AbstractController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xdrodger
 * @Title: ContractController
 * @ProjectName winback
 * @Description:
 * @date 2019/2/11 10:06
 */
@Slf4j
@RestController
public class ContractController  extends AbstractController {

    @Reference(version = "1.0.0")
    private ContractFacade contractFacade;

    /**
     * 获取合同列表接口(分页)
     */
    @GetMapping(value = "/open/contract/list")
    public ResponseModel getContractList(AppContractReq.QueryReq req) {
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(contractFacade.getContractList(req));
    }

    /**
     * 获取合同详情接口
     */
    @GetMapping(value = "/open/contract/detail")
    public ResponseModel getContract(AppContractReq.QueryReq req) {
        Assert.notNull(req.getContractId(), "contractId 参数不能为空");
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(contractFacade.getContract(req));
    }

    /**
     * 获取合同搜索记录接口
     */
    @GetMapping(value = "/confined/contract/search/record/list")
    public ResponseModel getSearchRecordList(AppContractReq.QueryReq req) {
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(contractFacade.getSearchRecordList(req));
    }

    /**
     * 清空合同搜索记录接口
     */
    @PostMapping(value = "/confined/contract/clear/search/record")
    public ResponseModel clearSearchRecord(@RequestBody AppContractReq.QueryReq req) {
        req.setLoginId(loadCurLoginId());
        contractFacade.clearSearchRecord(req);
        return ResponseModel.succ();
    }

    /**
     * 合同下载接口
     */
    @PostMapping(value = "/confined/contract/download")
    public ResponseModel download(@RequestBody AppContractReq.DownloadReq req) {
        Assert.notNull(req.getContractIds(), "contractId 参数不能为空");
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(contractFacade.download(req));
    }

    /**
     * 收藏合同接口
     */
    @PostMapping(value = "/confined/contract/favor")
    public ResponseModel favor(@RequestBody AppContractReq.QueryReq req) {
        Assert.notNull(req.getContractId(), "contractId 参数不能为空");
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(contractFacade.favor(req));
    }

    /**
     * 取消收藏合同接口
     */
    @PostMapping(value = "/confined/contract/unfavor")
    public ResponseModel unfavor(@RequestBody AppContractReq.QueryReq req) {
        Assert.notNull(req.getContractId(), "contractId 参数不能为空");
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(contractFacade.unfavor(req));
    }

    /**
     * 预开发票接口
     */
    @PostMapping(value = "/confined/contract/pre/invoice")
    public ResponseModel preInvoice(@RequestBody AppContractReq.PreInvoiceReq req) {
        Assert.notNull(req.getOrderIds(), "orderIds 参数不能为空");
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(contractFacade.preInvoice(req));
    }

    /**
     * 开发票接口
     */
    @PostMapping(value = "/confined/contract/invoice")
    public ResponseModel invoice(@RequestBody AppContractReq.InvoiceReq req) {
        Assert.notNull(req.getOrderIds(), "orderIds 参数不能为空");
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(contractFacade.invoice(req));
    }

    /**
     * 获取发票列表接口(分页)
     */
    @GetMapping(value = "/confined/contract/invoice/list")
    public ResponseModel getInvoiceList(AppContractReq.QueryReq req) {
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(contractFacade.getContractInvoiceList(req));
    }
}
