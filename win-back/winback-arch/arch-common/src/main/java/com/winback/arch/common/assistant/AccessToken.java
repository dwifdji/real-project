package com._360pai.core.model.assistant;

import lombok.Data;

/**
 * @author zxiao
 * @Title: AccessToken
 * @ProjectName winback-parent
 * @Description:
 * @date 2018/11/1 15:14
 */
@Data
public class AccessToken {
    // 接口访问凭证
    private String accessToken;
    // 凭证有效期，单位：秒
    private int expiresIn;
}
