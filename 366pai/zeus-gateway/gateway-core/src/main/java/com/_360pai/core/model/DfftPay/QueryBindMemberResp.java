package com._360pai.core.model.DfftPay;

/**
 * 描述：会员关系绑定查询
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/7 16:13
 */
public class QueryBindMemberResp extends CommResp{

    private String memCode;//会员名称

    private String memName;//会员名称

    public String getMemCode() {
        return memCode;
    }

    public void setMemCode(String memCode) {
        this.memCode = memCode;
    }

    public String getMemName() {
        return memName;
    }

    public void setMemName(String memName) {
        this.memName = memName;
    }
}
