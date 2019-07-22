package com._360pai.core.facade.withfudig.req;

import com._360pai.arch.common.RequestModel;
import lombok.Data;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/10 09:44
 */
public class WithfudigRequirementInvestReq {

    public static class RequirementInvestList extends RequestModel {

        private Integer requirementId;

        private String remarked;

        private String createdTime;
        /**
         * 联系人姓名或手机号
         */
        private String contact;

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }

        public Integer getRequirementId() {
            return requirementId;
        }

        public void setRequirementId(Integer requirementId) {
            this.requirementId = requirementId;
        }

        public String getRemarked() {
            return remarked;
        }

        public void setRemarked(String remarked) {
            this.remarked = remarked;
        }
    }

    public static class RequirementInvestRemark extends RequestModel {
        /**
         * 审核数据内容
         */
        private String  remark;
        /**
         * 审核对应的 数据id
         */
        private Integer requirementInvestId;


        private Integer operatorId;

        public Integer getOperatorId() {
            return operatorId;
        }

        public void setOperatorId(Integer operatorId) {
            this.operatorId = operatorId;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public Integer getRequirementInvestId() {
            return requirementInvestId;
        }

        public void setRequirementInvestId(Integer requirementInvestId) {
            this.requirementInvestId = requirementInvestId;
        }
    }

    @Data
    public static class RequirementInvestAdd extends RequestModel {

        private Integer requirementId;
        private Integer partyId;
        private Integer accountId;

    }

    @Data
    public static class MyRequirementInvestList extends RequestModel {
        private Integer partyId;

    }

}
