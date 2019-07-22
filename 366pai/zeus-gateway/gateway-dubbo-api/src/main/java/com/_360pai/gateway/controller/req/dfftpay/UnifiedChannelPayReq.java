package com._360pai.gateway.controller.req.dfftpay;

import java.io.Serializable;

/**
 * 描述：锁通道支付时的请求参数
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/7 16:19
 */
public class UnifiedChannelPayReq implements Serializable {


    private String payMemCode;//付款方会员号

    private String payMemName;//付款会员名称

    private String  bankCode;//收款方银行code

    private String accName;//收款方开户名称

    private String accNO;//收款方银行账户

    public String getPayMemCode() {
        return payMemCode;
    }

    public void setPayMemCode(String payMemCode) {
        this.payMemCode = payMemCode;
    }

    public String getPayMemName() {
        return payMemName;
    }

    public void setPayMemName(String payMemName) {
        this.payMemName = payMemName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public String getAccNO() {
        return accNO;
    }

    public void setAccNO(String accNO) {
        this.accNO = accNO;
    }
}
