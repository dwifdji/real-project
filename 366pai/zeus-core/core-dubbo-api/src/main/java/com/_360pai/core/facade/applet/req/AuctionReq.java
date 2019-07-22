package com._360pai.core.facade.applet.req;

import com._360pai.arch.common.RequestModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
public class AuctionReq implements Serializable {

    @Getter
    @Setter
    public static class AuctionInfoReq extends RequestModel {
        private String openId;//微信的openID
        private String shopId;//店铺id
        private String auctionId;//标的id
        private String type;//关注/取消关注
        private String assetId;//债权id
        private Integer partyId;//用户partyId
        private Integer accountId;


    }







}
