package com.tzCloud.gateway.resp.wxpay;

import java.io.Serializable;

/**
 * app支付相应实体
 */
public class AppPayResp implements Serializable {

    private String code;

    private String desc;

    private String prepayId;//预支付订单id


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public static AppPayResp failure(String code , String desc){

        AppPayResp resp = new AppPayResp();

        resp.setCode(code);
        resp.setDesc(desc);
        return resp;
    }

    public static AppPayResp successful(String code, String desc, String prepayId){
        AppPayResp appPayResp = new AppPayResp();
        appPayResp.setCode(code);
        appPayResp.setDesc(desc);
        appPayResp.setPrepayId(prepayId);

        return appPayResp;
    };


    public static AppPayResp successfulQuery(String code, String desc){
        AppPayResp appPayResp = new AppPayResp();
        appPayResp.setCode(code);
        appPayResp.setDesc(desc);

        return appPayResp;
    };
}
