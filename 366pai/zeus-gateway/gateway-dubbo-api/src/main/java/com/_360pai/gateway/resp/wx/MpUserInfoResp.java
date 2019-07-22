package com._360pai.gateway.resp.wx;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: SubscribeResp
 * @ProjectName dev2-zeus
 * @Description:
 * @date 2019-05-05 16:00
 */
@Data
public class MpUserInfoResp implements Serializable {
    private String code;

    private String desc;

    private String openId;
    private String unionId;
    private String nickName;
    private String headImgUrl;

    private String subscribe;
}
