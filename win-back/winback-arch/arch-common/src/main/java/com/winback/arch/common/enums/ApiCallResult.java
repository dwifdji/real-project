package com.winback.arch.common.enums;


/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/8/31 13:16
 */
public enum ApiCallResult {
    /**
     * 操作成功
     */
    SUCCESS("000", "操作成功"),
    EMPTY("001", "参数缺失"),
    DBERROR("002", "持久化异常"),
    UNSUPPORTED("003", "格式不支持"),
    SIGNERROR("004", "签名错误"),
    OUTRANGE("005", "文件过大"),
    HASREGISTER("006", "该手机号已被注册"),
    VERIFICATION("007", "验证码不正确"),
    CAN_NOT_SEND_VERIFY_CODE("008", "60秒内不可重复获取验证码"),
    NOREGISTER("009", "该手机号未注册"),
    NOAGENCY("010", "机构不存在"),
    NO_AUTH_FUNDER("010", "您还不是资金服务商，请去认证"),
    NO_AUTH_DISPOSAL("010", "您还不是处置服务商，请去认证"),
    ERROR_PASSWORD("011", "密码错误"),
    NO_AUTH_ACCOUNT("012", "账户未认证"),
    PARAMETER_INVALID("013", "参数非法"),
    DATA_EXCEPTION("014", "数据异常"),
    PERMISSION_DENIED("015", "权限不足"),
    LOGIN_FAIL("016", "登录失败，用户名或密码错误"),
    NOT_LOGIN("017", "请登录后操作"),
    REPEAT_OPERATION("018", "请勿重复操作"),
    ACCOUNT_DISABLED("019", "账号不可用"),
    NOT_COMPANY_ADMIN_ERRPR("020", "非企业管理员无法进行该操作"),
    NOT_AGENCY_ADMIN_ERRPR("021", "非机构管理员无法进行该操作"),
    KAPTCHA_ERRPR("022", "图形验证码错误"),
    SEND_SMS_CODE_FAIL("023", "短信发送失败"),
    SIGN_FAILURE("111", "签章失败！"),

    NORIGHT("995", "没有权限"),
    PHANTOM("996", "数据幻读"),
    DEFECT("997", "缺失票据"),
    OVERDUE("998", "票据过期"),
    FAILURE("999", "操作失败"),
    DATA_EMPTY("100", "查询参数为空"),
    EXCEPTION("1001", "系统异常"),
    SPV_CAN_NOT_SEND_SMS("1002", "该手机号正在等待或者已通过审核,请勿重复提交"),
    ONLY_COMPANY_CAN_APPLY_SPV("1003", "非企业用户不能申请SPV"),
    NO_AUTH_CAN_NOT_UPLOAD_ERROR("1004", "请先开通个人或企业认证"),
    NO_FDD_CAN_NOT_UPLOAD_ERROR("1005", "请先开通法大大"),
    NO_DFFT_CAN_NOT_UPLOAD_ERROR("1006", "请先开通东方付通"),

 	NO_BANK_ACCOUNT_ERROR("1007", "请先添加银行账户"),
    DATA_IS_EMPTY("300", "查询数据为空"),
    NO_NEED_PAY("200", "不需要支付"),
    NO_PAY("201", "订单未支付"),
    PAY_REPEAT("202", "您已支付成功啦，请务重复支付！"),
    ROOM_EXIT("400", "聊天房间已存在！"),

    CONTRACT_ORDER_PAY_FAIL("203", "订单已失效");


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

    ApiCallResult(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
