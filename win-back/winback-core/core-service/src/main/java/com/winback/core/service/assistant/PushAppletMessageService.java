package com.winback.core.service.assistant;

/**
 * @author xdrodger
 * @Title: PushAppletMessageService
 * @ProjectName winback
 * @Description:
 * @date 2019/3/13 16:00
 */
public interface PushAppletMessageService {
    /**
     * 发送合同上新消息
     */
    void pushContractNewArrivalMsg(String typeName);

    /**
     * 订单即将失效
     */
    void pushContractOrderBeAboutToPayTimeOutMsg(Integer extBindId, Integer orderId, String orderNo);

    /**
     * 订单失效
     */
    void pushContractOrderPayTimeOutMsg(Integer extBindId, Integer orderId, String orderNo);

    /**
     * 支付成功
     */
    void pushContractOrderPaySuccessMsg(Integer extBindId);

    /**
     * 合同下载成功
     */
    void pushContractDownloadSuccessMsg(Integer extBindId);

    /**
     * 合同发票申请成功
     */
    void pushContractInvoiceApplySuccessMsg(Integer extBindId);
}
