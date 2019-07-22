package com._360pai.core.facade.account.resp;

import com._360pai.arch.common.BaseResp;
import com._360pai.core.facade.account.vo.UserApplyRecordVo;

/**
 * @author xdrodger
 * @Title: UserApplyResp
 * @ProjectName zeus
 * @Description:
 * @date 11/09/2018 16:58
 */
public class UserApplyResp extends BaseResp {
    public static class DetailResp extends BaseResp {
        private UserApplyRecordVo applyRecord;

        public UserApplyRecordVo getApplyRecord() {
            return applyRecord;
        }

        public void setApplyRecord(UserApplyRecordVo applyRecord) {
            this.applyRecord = applyRecord;
        }
    }
}
