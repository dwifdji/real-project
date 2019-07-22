package com.tzCloud.gateway.controller.req.wx;

import java.io.Serializable;

/**
 * 描述：微信获取小程序二维码请求参数
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/11/23 10:42
 */
public class WXACodeUnLimitReq implements Serializable {

    private String scene;//二维码参数

    private String page;//网站地址

    private String type;//业务类型 备用字段

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
