package com._360pai.gateway.resp.wx;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: TemplateResp
 * @ProjectName dev2-zeus
 * @Description:
 * @date 2019-05-05 11:00
 */
@Data
public class TemplateResp implements Serializable {
    private String code;

    private String desc;

    private String msgid;//消息id
}
