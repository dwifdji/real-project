package com._360pai.core.facade.applet.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xdrodger
 * @Title: InviteRecord
 * @ProjectName zeus
 * @Description:
 * @date 2018/11/26 14:31r
 */
@Data
public class InviteRecord implements Serializable {
    private String mobile;
    private String nickName;
    private String headImgUrl;
    @JSONField(format= "yyyy/MM/dd")
    private Date registerTime;
    private Boolean isOpenShop;
    private Boolean isDeal;
}
