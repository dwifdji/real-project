package com.winback.gateway.resp;

import java.io.Serializable;

/**
 * 描述：会员账号查询
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/7 16:13
 */
public class QueryBalanceResp extends DfftPayResp implements Serializable {

    private String totalAmt;//全部金额

    private String freeAmt;//可用金额


    private String lockedAmt;//锁定金额

    public String getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(String totalAmt) {
        this.totalAmt = totalAmt;
    }

    public String getFreeAmt() {
        return freeAmt;
    }

    public void setFreeAmt(String freeAmt) {
        this.freeAmt = freeAmt;
    }

    public String getLockedAmt() {
        return lockedAmt;
    }

    public void setLockedAmt(String lockedAmt) {
        this.lockedAmt = lockedAmt;
    }
}
