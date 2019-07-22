package com._360pai.core.facade.assistant.req;

import com._360pai.arch.common.RequestModel;

/**
 * 描述：公共请求参数
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/20 11:13
 */
public class CommonReq {


    
    public static class propertyReq extends RequestModel {
    	
    	private String type;		//属性类型 1 热门拍品 2 拍卖大厅 3 抵押物


        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }



    public static class agencyInfo extends RequestModel {

        private String agencyCode;		//机构的code

        public String getAgencyCode() {
            return agencyCode;
        }

        public void setAgencyCode(String agencyCode) {
            this.agencyCode = agencyCode;
        }
    }
}
