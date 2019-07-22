package com._360pai.core.facade.fastway.req;

import com._360pai.arch.common.RequestModel;

import com._360pai.core.facade.fastway.resp.CompanyFundDetailVO;
import com._360pai.core.facade.fastway.resp.UserFundDetailVO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xiaolei
 * @create 2018-12-07 09:23
 */
public class FundApplyReq {

    @Data
    public static class UserApplyReq implements Serializable {
        private UserFundDetailVO userFundVO;
        private String     source;
        private Integer partyId;
    }

    @Data
    public static class CompanyApplyReq implements Serializable {
        private CompanyFundDetailVO companyFundVO;
        private String        source;
        private Integer partyId;
    }

    @Data
    public static class FundFindReq extends RequestModel {
        /**
         * 申请状态
         */
        private String applyStatus;
        /**
         * 手机号/用户名
         */
        private String query;
    }

    @Data
    public static class UserUpdateReq extends RequestModel {
        private UserFundDetailVO userFundDetailVO;
        private Integer applyId;
        private String refuseReason;
    }

    @Data
    public static class CompanyUpdateReq extends RequestModel {
        private CompanyFundDetailVO companyFundDetailVO;
        private Integer applyId;
        private String refuseReason;
    }

}
