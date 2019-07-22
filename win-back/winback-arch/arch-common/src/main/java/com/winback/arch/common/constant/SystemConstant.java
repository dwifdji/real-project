package com.winback.arch.common.constant;

/**
 * Created by RuQ on 2018/8/19 18:36
 */
public class SystemConstant {


    public static String ADMIN_COOKIE_ID_NAME = "_admin_id";
    public static String ADMIN_COOKIE_TICKET_NAME = "_admin_ticket";
    public static String ADMIN_SESSION_KEY = "_admin_session_key";

    public static String APP_COOKIE_ID_NAME = "_app_id";
    public static String APP_COOKIE_TICKET_NAME = "_app_ticket";
    public static String APP_SESSION_KEY = "_app_session_key";

    public static String APPLET_COOKIE_ID_NAME = "_applet_id";
    public static String APPLET_COOKIE_TICKET_NAME = "_applet_ticket";
    public static String APPLET_SESSION_KEY = "_applet_session_key";




    /**
     * 发送验证码间隔失效时间缓存key前缀
     */
    public static final String CACHE_KEY_PREFIX_SEND_VERIFY_INTERVAL_CODE = "_send_verify_interval_code_";

    /**
     * 手机验证码缓存key前缀
     */
    public static final String CACHE_KEY_PREFIX_PHONE_VERIFY_CODE = "_phone_verify_code_";

    /**
     * 发送验证码间隔失效时间 单位秒
     */
    public static final int CACHE_TIMEOUT_SEND_VERIFY_INTERVAL_CODE = 60;

    /**
     * 验证码失效时间 单位秒
     */
    public static final int CACHE_TIMEOUT_PHONE_VERIFY_CODE = 300;

    /**
     * 应用名称
     */
    public static final String APPLICATION_NAME_ADMIN = "admin";
    public static final String APPLICATION_NAME_APP = "app";
    public static final String APPLICATION_NAME_APPLET = "applet";

    /**
     * 排序参数
     */
    public static final String DESC = "desc";
    public static final String ASC = "asc";



    public static final String APP_KAPTCHA_SESSION_KEY = "_app_kaptcha_session_key_";
    public static final String ADMIN_KAPTCHA_SESSION_KEY = "_admin_kaptcha_session_key_";
    public static final String APPLET_KAPTCHA_SESSION_KEY = "_applet_kaptcha_session_key_";



    /**
     * 其他银行logo
     */
    public static final String DEFAULT_BANK_LOGO = "https://cdn-images.360pai.com/FnAXrmLxq1WMhpitRgFAp-FUkUgU";


    /**
     * 报警邮件key值
     */
    public static String EMAIL_WARNING_KEY= "_email_warning_key";

    /**
     * 默认头像
     */
    public static final String DEFAULT_HEAD_IMG_URL = "https://cdn-static.360yhl.com/Fss_Aubotr9vHGqU5-sJBWAqJWzn";


    public static final String WX_PAY_TOKEN_KEY ="WIN_WX_PAY_TOKEN_KEY";


    public static final Long WX_PAY_TOKEN_TIME_OUT = 3600L;



    /**
     * 业务邮件Mq队列
     */
    public static final String RABBITMQ_SEND_EMAIL_QUEUE = "win:emailQueue";

    /**
     * 异常邮件MQ队列
     */
    public static final String RABBITMQ_SEND_EXCEPTION_EMAIL_QUEUE = "win:emailExceptionQueue";
}
