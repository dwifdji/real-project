package com._360pai.core.facade.account.req;

import com._360pai.arch.common.RequestModel;

/**
 * @author xdrodger
 * @Title: PartyBlackListActionReq
 * @ProjectName zeus
 * @Description:
 * @date 12/09/2018 16:25
 */
public class PartyBlackListActionReq extends RequestModel {

    public static class BaseReq extends RequestModel {
        private Integer partyId;
        private String reason;
        private Integer operatorId;

        public Integer getPartyId() {
            return partyId;
        }

        public void setPartyId(Integer partyId) {
            this.partyId = partyId;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public Integer getOperatorId() {
            return operatorId;
        }

        public void setOperatorId(Integer operatorId) {
            this.operatorId = operatorId;
        }
    }


}
