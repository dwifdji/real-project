package com._360pai.gateway.common.dfftpay;

/**
 *支付返回码枚举
 */
public enum PayResultEnums {
    PAY_SUCCESS("000000", "交易成功（同步返回）"),
    PAY_NOTICE("100000", "请求成功（异步通知）"),
    REQ_SIGN_ERROR("100001","请求参数签名失败"),
    RESP_SIGN_ERROR("100002","返回参数验签失败"),
    BUS_ERROR("100003","支付系统返回业务错误"),

    INIT_ERROR("100004","初始化支付参数失败"),
    PARSING_EXCEPTION("101001", "参数解析异常"),
    PARAM_FORMAT_EXCEPTION("101002", "参数格式不正确"),
    PAYEE_MEM_ERROR("101003", "收款方代码错误"),
    PAYER_MEM_ERROR("101004", "付款方代码错误"),
    SIGN_ERROR("101005", "验证签名失败"),
    AUTHORITY_ERROR("101006", "商城权限不足"),
    BALANCE_DEFICIENCY("101007", "付款方余额不足"),
    ORDER_NUMBER_ERROR("101008", "订单号错误"),
    ORDER_PAID("101009", "订单已支付"),
    ORDER_REPEAT("101010", "指令号重复"),
    ORDER_CLOSED("101011", "订单已经关闭"),
    MEM_UNBOUNDED("101032", "会员未绑定"),
    SERVICE_ERROR("101033", "接口服务错误"),
    MEM_BOUND("101034", "会员已绑定"),
    QR_CODE_FAILURE("102011", "二维码已失效"),
    PAY_INFO_NO_SAME("102012", "匹配支付信息不一致"),
    REQUEST_SUCCESS("200000","请求成功"),
    WEB_RESP_ERROR("200001","网页返回错误码匹配错误"),
    QUERY_SUCCESS("200200","查询成功"),
    QUERY_EMPTY("300000","查询数据为空！"),
    QUERY_LOCK_EMPTY("300001","锁定数据为空！"),
    ORDER_EXIST("300002","订单已存在，请勿重复调用！"),

    PARAM_FAILURE("300003", "参数校验不通过"),



    PAY_FAILURE("111111","最终支付失败"),

    NOT_TYPE("400000","未定义支付类型"),

    REFUND("500000","转入退款"),
    NOTPAY("500001","未支付"),
    CLOSED("500002","订单已关闭"),
    REVOKED("500003","未定义支付类型"),
    USERPAYING("500004","用户支付中"),

    SYS_EXCEPTION("999999","支付系统异常，请稍后重试");

    private String code;
    private String desc;

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

    //根据code 获取描述
    public static String getDesc(String code) {
        for (PayResultEnums c : PayResultEnums.values()) {
            if (c.getCode().equals(code)) {
                return c.getDesc();
            }
        }
        return null;
    }

    PayResultEnums(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
