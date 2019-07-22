package com._360pai.gateway.controller.req.dfftpay;

import com._360pai.gateway.resp.DfftPayResp;

import java.io.Serializable;

/**
 * 描述：会员关系绑定查询
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/7 16:13
 */
public class FQueryBindMemberResp extends DfftPayResp implements Serializable {

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
