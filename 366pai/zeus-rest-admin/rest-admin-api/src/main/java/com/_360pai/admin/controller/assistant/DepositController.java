package com._360pai.admin.controller.assistant;

import com._360pai.admin.controller.AbstractController;
import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.ExcelUtil;
import com._360pai.arch.common.utils.JsonUtil;
import com._360pai.core.facade.activity.AuctionFacade;
import com._360pai.core.facade.activity.req.AuctionOfflineFinanceReq;
import com._360pai.core.facade.activity.vo.AuctionOfflineFinaceVo;
import com._360pai.core.facade.assistant.DepositFacade;
import com._360pai.core.facade.assistant.req.DepositReq;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author xdrodger
 * @Title: DepositController
 * @ProjectName zeus
 * @Description:
 * @date 2018/10/6 16:04
 */
@RestController
public class DepositController extends AbstractController {
    @Reference(version = "1.0.0")
    private DepositFacade depositFacade;

    @Reference(version = "1.0.0")
    private AuctionFacade auctionFacade;

    public static final Logger LOGGER = LoggerFactory.getLogger(DepositController.class);

    /**
     * 线下保证金列表
     */
    @RequiresPermissions("cwgl_xxbzjgl:list")
    @GetMapping(value = "/admin/deposit/offline/list")
    public ResponseModel depositOfflineList(DepositReq.QueryReq req) {
        return ResponseModel.succ(depositFacade.getDepositOfflineListByPage(req));
    }

    /**
     * 线下保证金详情
     */
    @GetMapping(value = "/admin/deposit/offline/detail")
    public ResponseModel depositOfflineDetail(DepositReq.BaseReq req) {
        return ResponseModel.succ(depositFacade.getDepositOffline(req));
    }

    /**
     * 下线保证金释放列表
     */
    @RequiresPermissions("cwgl_xxbzjgl:refund_list")
    @GetMapping(value = "/admin/deposit/offline//refund/list")
    public ResponseModel depositOfflineRefundList(DepositReq.QueryReq req) {
        return ResponseModel.succ(depositFacade.getDepositOfflineRefundListByPage(req));
    }

    /**
     * 线下保证金释放详情
     */
    @GetMapping(value = "/admin/deposit/offline/refund/detail")
    public ResponseModel depositOfflineRefundDetail(DepositReq.BaseReq req) {
        return ResponseModel.succ(depositFacade.getDepositOfflineRefund(req));
    }

    /**
     * 线下保证金收到
     */
    @PostMapping(value = "/admin/deposit/offline/receive")
    public ResponseModel depositOfflineReceive(@Valid @RequestBody DepositReq.OfflineConfirmReq req) {
        req.setOperatorId(loadCurLoginId());
        return ResponseModel.succ(depositFacade.receiveDeposit(req));
    }

    /**
     * 线下保证金退回
     */
    @PostMapping(value = "/admin/deposit/offline/refund")
    public ResponseModel depositOfflineRefund(@Valid @RequestBody DepositReq.OfflineConfirmReq req) {
        req.setOperatorId(loadCurLoginId());
        return ResponseModel.succ(depositFacade.refundDeposit(req));
    }

    /**
     * 线下保证金退回
     */
    @PostMapping(value = "/admin/deposit/offline/transfer")
    public ResponseModel depositOfflineTransfer(@Valid @RequestBody DepositReq.OfflineConfirmReq req) {
        req.setOperatorId(loadCurLoginId());
        return ResponseModel.succ(depositFacade.transferDeposit(req));
    }


    @PostMapping(value = "/admin/searchOfflineFinanceList")
    public ResponseModel searchOfflineFinanceList(@RequestBody AuctionOfflineFinanceReq req) {
        //req.setOperatorId(loadCurLoginId());
        return auctionFacade.searchOfflineFinanceList(req);
    }

    @PostMapping(value = "/admin/getOfflineFinanceDetailById")
    public ResponseModel getOfflineFinanceDetailById(@RequestBody AuctionOfflineFinanceReq req) {
        //req.setOperatorId(loadCurLoginId());
        if(req.getId() == null || req.getId() <=0){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        return auctionFacade.getOfflineFinanceDetailById(req);
    }

    @PostMapping(value = "/admin/confirmOfflineFinance")
    public ResponseModel confirmOfflineFinance(@RequestBody AuctionOfflineFinanceReq req) {
        req.setOperatorId(loadCurLoginId());
        if(req == null
                || StringUtils.isEmpty(req.getReceiveCommissionType())){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        return auctionFacade.confirmOfflineFinance(req);
    }


    @GetMapping(value = "/admin/exportOfflineFinanceList")
    public void exportOfflineFinanceList(AuctionOfflineFinanceReq req, HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("开始调用 exportOfflineFinanceList,参数:{}", JSON.toJSONString(req));

        List<AuctionOfflineFinaceVo> list = auctionFacade.searchAllOfflineFinanceList(req);

        String[] columnNames = new String[]{"订单编号", "拍卖活动名称", "用户名称", "手机号码","角色类型","资金类型","应收金额合计(元)","实收金额合计(元)","尾款金额(元)","应收佣金金额(元)","实收佣金金额(元)","创建时间","操作人","状态"};

        String[] keys = new String[]{"orderId", "auctionName", "userName", "userMobile","roleType","financeType","shouldReceiveTotalAmount","actualReceiveTotalAmount","remainAmount","shouldReceiveCommissionAmount","actualReceiveCommissionAmount","createTime","operatorName","staus"};

        String fileName = "线下佣金管理列表";



        OutputStream outputStream = null;
        try {
            String userAgent = request.getHeader("user-agent");
            if (userAgent != null && !userAgent.contains("Edge") &&
                    (userAgent.contains("Firefox") || userAgent.contains("Chrome") || userAgent.contains("Safari"))) {
                fileName = new String((fileName).getBytes(), "ISO8859-1") + ".xls";
            } else {
                //其他浏览器
                fileName = URLEncoder.encode(fileName, "UTF8") + ".xls";
            }

            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            Workbook work = ExcelUtil.createWorkBook(JsonUtil.beanListToMapList(list), keys, columnNames, fileName);
            outputStream = response.getOutputStream();
            work.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("导出数据异常{}", e.getMessage());
        } finally {
            try {
                assert outputStream != null;
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("关闭输出流异常{}", e.getMessage());
            }
        }
    }

}
