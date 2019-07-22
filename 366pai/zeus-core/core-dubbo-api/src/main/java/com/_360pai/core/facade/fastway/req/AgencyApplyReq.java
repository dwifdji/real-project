package com._360pai.core.facade.fastway.req;

import com._360pai.arch.common.RequestModel;
import com._360pai.core.facade.fastway.resp.AgencyAuctionDetailVO;
import com._360pai.core.facade.fastway.vo.AuctionVO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xiaolei
 * @create 2018-11-29 14:27
 */
public class AgencyApplyReq {

    @Data
    public static class AuctionApply implements Serializable {
        private AuctionVO agencyVO;
        private String    source;
    }

    @Data
    public static class AgencyFindReq extends RequestModel {
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
    public static class AuctionUpdateReq extends RequestModel {
        private AgencyAuctionDetailVO detailVO;
        private Integer applyId;
        private String refuseReason;
    }
}
