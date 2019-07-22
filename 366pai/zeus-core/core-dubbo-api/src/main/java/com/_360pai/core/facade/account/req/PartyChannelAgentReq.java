package com._360pai.core.facade.account.req;

import com._360pai.arch.common.RequestModel;

import java.math.BigDecimal;

/**
 * @author xdrodger
 * @Title: PartyChannelAgentReq
 * @ProjectName zeus
 * @Description:
 * @date 12/09/2018 19:35
 */
public class PartyChannelAgentReq extends RequestModel {
    public static class BaseReq extends RequestModel {
        private Integer partyId;
        private Boolean isChannelAgent;
        private Integer channelAgentPartyId;
        private BigDecimal channelAgentCommissionPercent;

        public Integer getPartyId() {
            return partyId;
        }

        public void setPartyId(Integer partyId) {
            this.partyId = partyId;
        }

        public Boolean getIsChannelAgent() {
            return isChannelAgent;
        }

        public void setIsChannelAgent(Boolean isChannelAgent) {
            this.isChannelAgent = isChannelAgent;
        }

        public Integer getChannelAgentPartyId() {
            return channelAgentPartyId;
        }

        public void setChannelAgentPartyId(Integer channelAgentPartyId) {
            this.channelAgentPartyId = channelAgentPartyId;
        }

        public BigDecimal getChannelAgentCommissionPercent() {
            return channelAgentCommissionPercent;
        }

        public void setChannelAgentCommissionPercent(BigDecimal channelAgentCommissionPercent) {
            this.channelAgentCommissionPercent = channelAgentCommissionPercent;
        }
    }
}
