package com._360pai.core.facade.shop.req;


import com._360pai.arch.common.RequestModel;
import lombok.Getter;
import lombok.Setter;

public class ShopEnrollingReq {



    /**
     * 首页展示设置请求参数
     */
    @Getter
    @Setter
    public static class comReq extends RequestModel{

        private String activityId;

        private String shopId;


        private Integer accountId;


        private Integer partyId;

        private String type;


    }





}
 