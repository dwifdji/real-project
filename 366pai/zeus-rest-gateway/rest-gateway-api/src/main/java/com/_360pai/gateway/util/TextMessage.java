package com._360pai.gateway.util;

import lombok.Data;

/**
 * @author xdrodger
 * @Title: TextMessage
 * @ProjectName dev2-zeus
 * @Description:
 * @date 2019-05-05 10:03
 */
@Data
public class TextMessage {
    private String ToUserName;
    private String FromUserName;
    private String CreateTime;
    private String MsgType;
    private String Content;
    private String MsgId;
}
