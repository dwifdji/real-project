package com.tzCloud.arch.common.constant;

/**
 * Created by RuQ on 2018/8/19 18:36
 */
public class SystemConstant {

    public static final String BAIDU_MAP_STATUS = "0";


    public static final String BAIDU_MAP_FACTOR_INFO_DETAIL = "_factor_info_detail";



    public static String tzCloud = "tzCloud";

    public static String PLATFORM_COOKIE_ID_NAME = tzCloud + "_id";
    public static String PLATFORM_COOKIE_TICKET_NAME = tzCloud + "_ticket";
    public static String PLATFORM_SESSION_KEY = tzCloud + "_session_key";

    public static final String PLATFORM_KAPTCHA_SESSION_KEY = tzCloud + "_platform_kaptcha_session_key_";

    /**
     * 日志队列名称
     */
    public static final String RABBITMQ_ROUTING_KEY_OPERATOR_QUEUE = "tzCloud:operatorLogQueue";
    /**
     * 短信队列名称
     */
    public static final String RABBITMQ_SEND_SMS_QUEUE = "tzCloud:aliSmsQueue";
    /**
     * 邮件队列名称
     */
    public static final String RABBITMQ_SEND_EMAIL_QUEUE = "tzCloud:emailQueue";
    /**
     * 延迟队列exchange名称
     */
    public static final String RABBITMQ_DELAY_EXCHANGE = "tzCloud:delayExchange";
    /**
     * 异常监控邮件队列名称
     */
    public static final String RABBITMQ_SEND_EXCEPTION_EMAIL_QUEUE = "tzCloud:emailExceptionQueue";
    /**
     * 异常信息队列名称
     */
    public static final String RABBITMQ_EXCEPTION_QUEUE = "tzCloud:exceptionQueue";



    /**
     * 应用名称
     */
    public static final String APPLICATION_NAME_PLATFORM = "platform";

    /**
     * 排序参数
     */
    public static final String DESC = "desc";
    public static final String ASC = "asc";


    /**
     * 发送验证码间隔失效时间缓存key前缀
     */
    public static final String CACHE_KEY_PREFIX_SEND_VERIFY_INTERVAL_CODE = tzCloud + "_send_verify_interval_code_";

    /**
     * 手机验证码缓存key前缀
     */
    public static final String CACHE_KEY_PREFIX_PHONE_VERIFY_CODE = tzCloud + "_phone_verify_code_";

    /**
     * 发送验证码间隔失效时间 单位秒
     */
    public static final int CACHE_TIMEOUT_SEND_VERIFY_INTERVAL_CODE = 60;

    /**
     * 验证码失效时间 单位秒
     */
    public static final int CACHE_TIMEOUT_PHONE_VERIFY_CODE = 300;

    public static final String WX_ACCESS_TOKEN = "wx_access_token_";
    public static final String WX_JSAPI_TICKET = "wx_jsapi_ticket_";

    public static final Integer WX_TIME_OUT = 7000;




    /**
     * 微信支付token 的缓存key值
     */
    public static final String WX_PAY_TOKEN_KEY = "wx_pay_token_key";


    /**
     * 微信支付token 的缓存过期时间
     */
    public static final Long WX_PAY_TOKEN_TIME_OUT = 3600L;


    /**
     * 默认头像
     */
    public static final String DEFAULT_HEAD_IMG_URL = "https://cdn-static.360yhl.com/Fss_Aubotr9vHGqU5-sJBWAqJWzn";
}
