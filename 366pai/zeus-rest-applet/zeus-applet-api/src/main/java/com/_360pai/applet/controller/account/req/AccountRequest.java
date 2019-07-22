package com._360pai.applet.controller.account.req;

import com._360pai.arch.common.RequestModel;
import lombok.Data;

/**
 * Created by RuQ on 2018/8/17 9:32
 */
@Data
public class AccountRequest extends RequestModel {
    private String mobile;
    private String default_agency_id;
    private String agencyCode;
    private String smsCode;
    //验证码类型，6:申请开店，7:绑定银行卡，8:提现
    private String smsType;
    private String password;
    private String register_source;
    private String source;
    private String type;
    private String partyId;
    private String captcha;
    // 邀请码
    private String inviteCode;
}
