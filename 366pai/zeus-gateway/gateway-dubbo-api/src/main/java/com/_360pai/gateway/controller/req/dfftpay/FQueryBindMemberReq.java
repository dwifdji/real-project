package com._360pai.gateway.controller.req.dfftpay;

import java.io.Serializable;

/**
 * 描述：会员关系查询接口请求参数
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/7 16:12
 */
public class FQueryBindMemberReq implements Serializable {

    private String memCode;//会员的code 对应user表的 mem_code 字段

    private String memName;//会员的名称 对应name;

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
