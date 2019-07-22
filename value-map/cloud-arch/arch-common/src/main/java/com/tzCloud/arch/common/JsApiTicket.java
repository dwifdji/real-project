package com.tzCloud.arch.common;

import lombok.Data;

/**
 * @author zxiao
 * @Title: JsApiTicket
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/11/1 15:15
 */
@Data
public class JsApiTicket {
    private String ticket;
    // 凭证有效期，单位：秒
    private int expiresIn;

}
