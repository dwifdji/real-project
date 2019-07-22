package com._360pai.core.facade.assistant.resp;

import com._360pai.arch.common.BaseResp;
import com._360pai.core.facade.assistant.vo.DepositVo;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xdrodger
 * @Title: DepositResp
 * @ProjectName zeus
 * @Description:
 * @date 2018/10/6 14:05
 */
public class DepositResp extends BaseResp {

    @Getter
    @Setter
    public static class DetailResp extends BaseResp {
        private DepositVo deposit;
    }
}
