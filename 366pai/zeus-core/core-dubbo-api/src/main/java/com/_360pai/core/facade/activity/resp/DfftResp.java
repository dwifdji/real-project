package com._360pai.core.facade.activity.resp;

import java.io.Serializable;

/**
 * @author RuQ
 * @Title: DfftResp
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/13 20:32
 */
public class DfftResp implements Serializable {
    private String param;//请求手动签章的参数

    private String url;//请求跳转的参数

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
