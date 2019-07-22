package com.tzCloud.gateway.controller.req.pay;

import com.tzCloud.gateway.common.constants.PayResultEnums;

import java.io.Serializable;

/**
 * 描述：统一支付返回
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/13 13:14
 */
public class UnifiedPayResp implements Serializable {


    private String code; //返回码见 PayResultEnums 类

    private String desc;//返回参数

    private String busId;//请求的业务参数

    private String payOrder;//订单id

    private String url;//请求跳转url 参数

    private String param;//跳转url 的请求参数

    private String payBusType;//业务类型



    public String getPayBusType() {
        return payBusType;
    }

    public void setPayBusType(String payBusType) {
        this.payBusType = payBusType;
    }

    /**
     *
     *异步成功返回
     */
    public static UnifiedPayResp asynSucc(String busId, String payOrder, String url, String param) {
        UnifiedPayResp ret = new UnifiedPayResp();
        ret.setCode(PayResultEnums.PAY_NOTICE.getCode());
        ret.setDesc(PayResultEnums.PAY_NOTICE.getDesc());
        ret.setBusId(busId);
        ret.setPayOrder(payOrder);
        ret.setUrl(url);
        ret.setParam(param);
        return ret;
    }


    /**
     *
     *同步成功返回
     */
    public static UnifiedPayResp lastSucc(String busId, String payOrder) {
        UnifiedPayResp ret = new UnifiedPayResp();
        ret.setCode(PayResultEnums.PAY_SUCCESS.getCode());
        ret.setDesc(PayResultEnums.PAY_SUCCESS.getDesc());
        ret.setBusId(busId);
        ret.setPayOrder(payOrder);
        return ret;
    }


    /**
     *
     *返回码输入
     */
    public static UnifiedPayResp payResp(String code , String desc , String busId, String payOrder,String url, String param) {
        UnifiedPayResp ret = new UnifiedPayResp();
        ret.setCode(code);
        ret.setDesc(desc);
        ret.setBusId(busId);
        ret.setPayOrder(payOrder);
        ret.setUrl(url);
        ret.setParam(param);

        return ret;
    }


    /**
     *
     *返回码输入
     */
    public static UnifiedPayResp fail(String code , String desc) {
        UnifiedPayResp ret = new UnifiedPayResp();
        ret.setCode(code);
        ret.setDesc(desc);


        return ret;
    }


    /**
     *
     *支付系统异常
     */
    public static UnifiedPayResp sysException() {
        UnifiedPayResp ret = new UnifiedPayResp();
        ret.setCode(PayResultEnums.SYS_EXCEPTION.getCode());
        ret.setDesc(PayResultEnums.SYS_EXCEPTION.getDesc());

        return ret;
    }


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

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getPayOrder() {
        return payOrder;
    }

    public void setPayOrder(String payOrder) {
        this.payOrder = payOrder;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
