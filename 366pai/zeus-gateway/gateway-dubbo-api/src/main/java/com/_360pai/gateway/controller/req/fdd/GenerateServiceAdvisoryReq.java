package com._360pai.gateway.controller.req.fdd;

import lombok.Data;

import java.io.Serializable;

/**
 * 描述 服务咨询合同 请求参数
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/12/13 14:30
 */
@Data
public class GenerateServiceAdvisoryReq implements Serializable {

    private String user;//甲方
    
    private String userAddress;//地址

    private String userPhone;//联系电话

    private String amount;//金额

    private String amountUpper;//大写金额


}
