package com._360pai.core.model.DfftPay;

import java.lang.reflect.Array;

/**
 * 描述：
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/7 16:22
 */
public class FenRunPayReq extends PayCommonReq{


    private String recMemCode;//收款会员号

    private String recMemName;//收款会员名称

    private Array profitShare;//分润信息



    public String getRecMemCode() {
        return recMemCode;
    }

    public void setRecMemCode(String recMemCode) {
        this.recMemCode = recMemCode;
    }

    public String getRecMemName() {
        return recMemName;
    }

    public void setRecMemName(String recMemName) {
        this.recMemName = recMemName;
    }


    public Array getProfitShare() {
        return profitShare;
    }

    public void setProfitShare(Array profitShare) {
        this.profitShare = profitShare;
    }
}
