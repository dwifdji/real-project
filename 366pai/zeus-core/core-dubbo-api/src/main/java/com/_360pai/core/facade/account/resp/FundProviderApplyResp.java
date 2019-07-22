package com._360pai.core.facade.account.resp;

import com._360pai.arch.common.BaseResp;
import com._360pai.core.facade.account.vo.FundProviderApplyVo;

/**
 * @author xdrodger
 * @Title: FundApplyResp
 * @ProjectName zeus
 * @Description:
 * @date 18/09/2018 18:38
 */
public class FundProviderApplyResp extends BaseResp {


    public static class DetailResp extends BaseResp {
        private FundProviderApplyVo applyRecord;

        public FundProviderApplyVo getApplyRecord() {
            return applyRecord;
        }

        public void setApplyRecord(FundProviderApplyVo applyRecord) {
            this.applyRecord = applyRecord;
        }
    }

}
