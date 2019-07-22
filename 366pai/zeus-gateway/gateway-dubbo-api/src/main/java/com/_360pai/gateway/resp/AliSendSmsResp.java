package com._360pai.gateway.resp;

import java.io.Serializable;

/**
 * 描述：发送短信返回信息
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/7 15:16
 */
public class AliSendSmsResp  implements Serializable {


    private String  code;

    private String  desc;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
