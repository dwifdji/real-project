package com._360pai.gateway.controller.req.fdd;

import java.io.Serializable;

/**
 * 描述：签署合同返回
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/13 13:14
 */
public class ExtSignContractResp implements Serializable {


    private String code;

    private String desc;

    private String param;//请求手动签章的参数

    private String url;//请求跳转的参数

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

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
