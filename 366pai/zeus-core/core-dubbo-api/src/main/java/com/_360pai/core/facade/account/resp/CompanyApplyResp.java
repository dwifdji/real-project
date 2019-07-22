package com._360pai.core.facade.account.resp;

import com._360pai.arch.common.BaseResp;
import com._360pai.core.facade.account.vo.CompanyApplyRecordVo;

/**
 * @author xdrodger
 * @Title: CompanyApplyResp
 * @ProjectName zeus
 * @Description:
 * @date 11/09/2018 19:56
 */
public class CompanyApplyResp extends BaseResp {
    public static class DetailResp extends BaseResp {
        private CompanyApplyRecordVo applyRecord;

        public CompanyApplyRecordVo getApplyRecord() {
            return applyRecord;
        }

        public void setApplyRecord(CompanyApplyRecordVo applyRecord) {
            this.applyRecord = applyRecord;
        }
    }
}
