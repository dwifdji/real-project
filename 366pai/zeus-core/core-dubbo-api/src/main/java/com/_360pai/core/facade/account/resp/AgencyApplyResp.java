package com._360pai.core.facade.account.resp;

import com._360pai.arch.common.BaseResp;
import com._360pai.core.facade.account.vo.AgencyApplyRecordVo;

/**
 * @author xdrodger
 * @Title: AgencyApplyResp
 * @ProjectName zeus
 * @Description:
 * @date 15/09/2018 19:44
 */
public class AgencyApplyResp extends BaseResp {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static class DetailResp extends BaseResp {
        private AgencyApplyRecordVo applyRecord;

        public AgencyApplyRecordVo getApplyRecord() {
            return applyRecord;
        }

        public void setApplyRecord(AgencyApplyRecordVo applyRecord) {
            this.applyRecord = applyRecord;
        }
    }
}
