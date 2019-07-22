package com._360pai.core.facade.applet.req;

import com._360pai.arch.common.RequestModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
public class AssistantReq implements Serializable {


    @Getter
    @Setter
    public static class comReq extends RequestModel {
        private String openId;//微信的openID
        private String shopId;//店铺id
        private String auctionId;//标的id
        private String type;//类型
        private Integer partyId;
        private Integer reqShopId;//请求的店铺id

        private String timeType;//时间类型

        private Integer accountId;
    }


    @Getter
    @Setter
    public static class payCallBackReq extends RequestModel {
        private String orderId;//支付的订单id
        private String code;//支付code
        private String desc;//支付备注
        private Integer partyId;
        private String openId;


    }



}
