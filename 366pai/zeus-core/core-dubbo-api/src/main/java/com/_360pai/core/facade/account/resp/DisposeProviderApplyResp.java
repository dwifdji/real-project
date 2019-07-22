package com._360pai.core.facade.account.resp;

import com._360pai.arch.common.BaseResp;
import com._360pai.core.facade.account.vo.DisposeProviderApplyVo;

/**
 * @author xdrodger
 * @Title: DisposeProviderApplyResp
 * @ProjectName zeus
 * @Description:
 * @date 18/09/2018 18:42
 */
public class DisposeProviderApplyResp extends BaseResp {

    public static class DetailResp extends BaseResp {
        private DisposeProviderApplyVo applyRecord;

        public DisposeProviderApplyVo getApplyRecord() {
            return applyRecord;
        }

        public void setApplyRecord(DisposeProviderApplyVo applyRecord) {
            this.applyRecord = applyRecord;
        }
    }
}
