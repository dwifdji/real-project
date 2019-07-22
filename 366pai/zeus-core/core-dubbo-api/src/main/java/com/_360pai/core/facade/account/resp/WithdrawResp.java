package com._360pai.core.facade.account.resp;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author RuQ
 * @Title: WithdrawResp
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/12/11 17:18
 */
@Setter
@Getter
public class WithdrawResp implements Serializable {
    private String applyTime;
    private Long acctDetailId;
}
