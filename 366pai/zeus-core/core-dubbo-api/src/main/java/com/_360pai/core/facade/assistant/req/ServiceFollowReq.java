package com._360pai.core.facade.assistant.req;

import com._360pai.arch.common.RequestModel;
import lombok.Data;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/17 11:18
 */
public class ServiceFollowReq {

    @Data
    public static class Add extends RequestModel {
        private String  requirementId;
        private Integer disposalId;

        private String  relativeType;
        private Integer accountId;
        private Integer partyId;
    }

    @Data
    public static class Remove extends RequestModel {
        private String    requirementId;
        private String[]  requirementIds;
        private Integer   disposalId;
        private Integer[] disposalIds;


        private String  relativeType;
        private Integer partyId;
        private Integer accountId;

    }

    public static class List extends RequestModel {
        private String requirementId;

        private String relativeType;

        public String getRelativeType() {
            return relativeType;
        }

        public void setRelativeType(String relativeType) {
            this.relativeType = relativeType;
        }

        public String getRequirementId() {
            return requirementId;
        }

        public void setRequirementId(String requirementId) {
            this.requirementId = requirementId;
        }

    }
}
