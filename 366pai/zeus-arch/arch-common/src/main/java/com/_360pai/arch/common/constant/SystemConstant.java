package com._360pai.arch.common.constant;

/**
 * Created by RuQ on 2018/8/19 18:36
 */
public class SystemConstant {

    public static final String ACCOUNT_COMMON_TYPE = "account";
    public static final String ACCOUNT_USER_TYPE = "user";
    public static final String ACCOUNT_AGENY_TYPE = "agency";
    public static final String ACCOUNT_COMPANY_TYPE = "company";
    public static final String ACCOUNT_FUNDER_TYPE = "funder";
    public static final String ACCOUNT_DISPOSER_TYPE = "disposer";

    public static final String ACCOUNT_COMMON_AGENCY_TYPE = "AA";
    public static final String ACCOUNT_AGENCY_TYPE = "AG";

    public static final String ACCOUNT_ADMIN_STAFF_TYPE = "staff";

    //public static final String SMS_REGISTER_TYPE = "0";
    //public static final String SMS_LOGIN_TYPE = "1";
    //public static final String SMS_FORGET_PWD_TYPE = "2";
    //public static final String SMS_MODIFY_PWD_TYPE = "3";
    //public static final String SMS_MODIFY_MOBILE_TYPE = "4";
    //public static final String SMS_SPV_APPLY_TYPE = "5";
    //public static final String SMS_BIND_ACCOUNT_TYPE = "6";
    //public static final String SMS_BIND_BANK_CARD_TYPE = "7";
    //public static final String SMS_WITHDRAW_DEPOSIT_TYPE = "8";

    //public static final String ADMIN_SMS_LOGIN_TYPE = "admin_login";

    //public static final String AGENCY_SMS_LOGIN_TYPE = "agency_login";


    public static String COOKIE_ACCOUNT_ID_NAME = "id";
    public static String COOKIE_ACCOUNT_TICKET_NAME = "ticket";
    public static String COOKIE_ACCOUNT_TYPE = "type";
    public static String MOBILE_AUTHEN_SESSION_CACHE_KEY = "session_key";

    public static String ADMIN_COOKIE_ACCOUNT_ID_NAME = "admin_id";
    public static String ADMIN_COOKIE_ACCOUNT_TYPE = "admin_type";
    public static String ADMIN_COOKIE_ACCOUNT_TICKET_NAME = "admin_ticket";
    public static String ADMIN_MOBILE_AUTHEN_SESSION_CACHE_KEY = "admin_session_key";

    public static String AGENCY_COOKIE_ACCOUNT_ID_NAME = "agency_id";
    public static String AGENCY_COOKIE_ACCOUNT_TICKET_NAME = "agency_ticket";
    public static String AGENCY_COOKIE_ACCOUNT_TYPE = "agency_type";
    public static String AGENCY_MOBILE_AUTHEN_SESSION_CACHE_KEY = "agency_session_key";

    public static String APPLET_COOKIE_ACCOUNT_ID_NAME = "applet_id";
    public static String APPLET_COOKIE_ACCOUNT_TYPE = "applet_type";
    public static String APPLET_COOKIE_ACCOUNT_TICKET_NAME = "applet_ticket";
    public static String APPLET_MOBILE_AUTHEN_SESSION_CACHE_KEY = "applet_session_key";

    public static String CALCULATOR_COOKIE_ACCOUNT_ID_NAME = "_calculator_id";
    public static String CALCULATOR_COOKIE_ACCOUNT_TYPE = "_calculator_type";
    public static String CALCULATOR_COOKIE_ACCOUNT_TICKET_NAME = "_calculator_ticket";
    public static String CALCULATOR_MOBILE_AUTHEN_SESSION_CACHE_KEY = "_calculator_session_key";

    public static final String DEFAULT_AGENCY_CODE = "SHBC";

    public static final String OPERATION_APPROVE = "1";
    public static final String OPERATION_REJECT = "2";


    public static final String PAY_TYPE_ONLINE = "ONLINE";
    public static final String PAY_TYPE_OFFLINE = "OFFLINE";

    public static final String PAY_BUSINESS_TYPE_LOCK = "锁定";
    public static final String PAY_BUSINESS_TYPE_PAID = "支付";
    public static final String PAY_BUSINESS_TYPE_REALEASE = "释放";

    public static final String DEPOSIT_PAY_STATUS_INIT = "INIT";
    public static final String DEPOSIT_PAY_STATUS_ONLINE_LOCKED = "LOCKED";
    public static final String DEPOSIT_PAY_STATUS_ONLINE_SEND_BACK = "SEND_BACK";
    public static final String DEPOSIT_PAY_STATUS_ONLINE_TRANSFERRED = "TRANSFERRED";
    public static final String DEPOSIT_PAY_STATUS_ONLINE_RELEASED = "RELEASED";
    public static final String DEPOSIT_PAY_STATUS_ONLINE_TRANSFER_RELEASED = "TRANSFER_RELEASED";
    public static final String DEPOSIT_PAY_STATUS_OFFLINE_RECEIVED = "OFFLINE_RECEIVED";
    public static final String DEPOSIT_PAY_STATUS_OFFLINE_FINISHED = "OFFLINE_FINISHED";
    public static final String DEPOSIT_PAY_STATUS_OFFLINE_RETURNED = "OFFLINE_RETURNED";

    public static final String AUCTION_ORDER_STATUS_NOT_SIGNED = "NOT_SIGNED";
    public static final String AUCTION_ORDER_STATUS_NOT_PAID = "NOT_PAID";
    public static final String AUCTION_ORDER_STATUS_PAID = "PAID";
    public static final String AUCTION_ORDER_STATUS_NOT_SIGNED_LEASE = "NOT_SIGNED_LEASE";
    public static final String AUCTION_ORDER_STATUS_DELIVERED = "DELIVERED";
    public static final String AUCTION_ORDER_STATUS_RECEIVED = "RECEIVED";
    public static final String AUCTION_ORDER_STATUS_CLOSED = "CLOSED";

    public static final String BUYER_REMAIN_PRE_KEY = "br";
    public static final String BUYER_COMMISSION_PRE_KEY = "bc";
    public static final String SELLER_COMMISSION_PRE_KEY = "sc";
    public static final String RELEASE_BUYER_COMMISSION_TO_BC_PRE_KEY = "rebc";
    public static final String RELEASE_SELLER_COMMISSION_TO_BC_PRE_KEY = "resc";
    public static final String RETURN_BUYER_DEPOSIT_PRE_KEY = "rtbd";
    public static final String RETURN_BUYER_REMAIN_PRE_KEY = "rtbr";

    public static final String RELEASE_BUYER_DEPOSIT = "rebd";
    public static final String RELEASE_BUYER_REMAIN = "rer";


    public static final String SELLER_AGENCY_COMMISSION_PRE_KEY = "sa";
    public static final String BUYER_AGENCY_COMMISSION_PRE_KEY = "ba";
    public static final String BUYER_CHANNEL_COMMISSION_PRE_KEY = "bcc";
    public static final String SELLER_CHANNEL_COMMISSION_PRE_KEY = "scc";

    public static final String RELEASE_SELLER_AGENCY_COMMISSION_PRE_KEY = "resa";
    public static final String RELEASE_BUYER_AGENCY_COMMISSION_PRE_KEY = "reba";
    public static final String RELEASE_BUYER_CHANNEL_COMMISSION_PRE_KEY = "rebcc";
    public static final String RELEASE_SELLER_CHANNEL_COMMISSION_PRE_KEY = "rescc";


    public static final String PAY_ORDER_STATUS_NO_PAID = "NOT_PAID";
    public static final String PAY_ORDER_STATUS__PAYING = "PAYING";
    public static final String PAY_ORDER_STATUS_PAID_SUCCESS = "PAID_SUCCESS";
    public static final String PAY_ORDER_STATUS_PAID_FAIL = "PAID_FAIL";

    public static final String AUCTION_EXPIRE_PRE_KEY = "AUCEXPIRE_";
    public static final String AUCTION_PAY_TIMEOUT_PRE_KEY = "AUCTIMEOUT_";
    public static final String AUCTION_INSERT_BID_PRE_KEY = "AUCBID_";
    public static final String AUCTION_SIGN_EXPIRE_PRE_KEY = "AUCSIGNEXPIRE_";
    public static final String AUCTION_DUTCH_PRICE_PRE_KEY = "AUCDUTCH_";


    /**
     * 预招商报名结束标识
     */
    public static final String ENROLLING_SIGN_UP_KEY = "SIGNUPEND_";


    /**
     * 预招商提醒到期时间标识
     */
    public static final String ENROLLING_REMIND_KEY = "REMIND_";

    /**
     * 发送验证码间隔失效时间缓存key前缀
     */
    public static final String CACHE_KEY_PREFIX_SEND_VERIFY_INTERVAL_CODE = "send_verify_interval_code_";

    /**
     * 手机验证码缓存key前缀
     */
    public static final String CACHE_KEY_PREFIX_PHONE_VERIFY_CODE = "phone_verify_code_";

    /**
     * 发送验证码间隔失效时间 单位秒
     */
    public static final int CACHE_TIMEOUT_SEND_VERIFY_INTERVAL_CODE = 60;

    /**
     * 验证码失效时间 单位秒
     */
    public static final int CACHE_TIMEOUT_PHONE_VERIFY_CODE = 300;

    /**
     * 日志队列名称
     */
    public static final String RABBITMQ_ROUTING_KEY_OPERATOR_QUEUE = "zeus:operatorLogQueue";
    /**
     * 短信队列名称
     */
    public static final String RABBITMQ_SEND_SMS_QUEUE = "zeus:aliSmsQueue";
    /**
     * 邮件队列名称
     */
    public static final String RABBITMQ_SEND_EMAIL_QUEUE = "zeus:emailQueue";
    /**
     * 延迟队列exchange名称
     */
    public static final String RABBITMQ_DELAY_EXCHANGE = "zeus:delayExchange";
    /**
     * 测试延迟队列名称
     */
    public static final String RABBITMQ_TEST_DELAY_QUEUE = "zeus:testDelayQueue";
    /**
     * 测试队列名称
     */
    public static final String RABBITMQ_TEST_QUEUE = "zeus:testQueue";
    /**
     * 拍卖活动结束队列名称
     */
    public static final String RABBITMQ_AUCTION_ACTIVITY_END_QUEUE = "zeus:auctionActivityEndQueue";
    /**
     * 拍卖活动降价队列名称
     */
    public static final String RABBITMQ_AUCTION_ACTIVITY_DUTCH_PRICE_QUEUE = "zeus:auctionActivityDutchPriceQueue";
    /**
     * 拍卖订单支付超时队列名称
     */
    public static final String RABBITMQ_AUCTION_ORDER_PAY_TIMEOUT_QUEUE = "zeus:auctionOrderPayTimeoutQueue";
    /**
     * 拍卖活动签署成交协议超时队列名称
     */
    public static final String RABBITMQ_AUCTION_ACTIVITY_SIGN_TIMEOUT_KEY = "zeus:auctionActivitySignTimeoutQueue";
    /**
     * 预招商活动报名结束队列名称
     */
    public static final String RABBITMQ_ENROLLING_ACTIVITY_SIGN_UP_END_QUEUE = "zeus:enrollingActivitySignUpEndQueue";
    /**
     * 异常监控邮件队列名称
     */
    public static final String RABBITMQ_SEND_EXCEPTION_EMAIL_QUEUE = "zeus:emailExceptionQueue";
    /**
     * 异常信息队列名称
     */
    public static final String RABBITMQ_EXCEPTION_QUEUE = "zeus:exceptionQueue";
    /**
     * 处置服务商提醒到期时间队列名称
     */
    public static final String RABBITMQ_DISPOSAL_DEADLINE_QUEUE = "zeus:disposalDeadlineQueue";
    /**
     * 一级合伙人接单到期队列名称
     */
    public static final String RABBITMQ_PROVIDER_SURVEY_QUEUE = "zeus:providerSurveyDeadlineQueue";
    /**
     * 处置服务支付过期队列名称
     */
    public static final String RABBITMQ_DISPOSAL_PAY_EXPIRED = "zeus:disposalPayExpiredQueue";
    /**
     * 配资服务支付过期队列名称
     */
    public static final String RABBITMQ_WITHFUDIG_PAY_EXPIRED = "zeus:withfudigPayExpiredQueue";
    /**
     * 置业服务支付过期队列名称
     */
    public static final String RABBITMQ_COMPRADOR_PAY_EXPIRED = "zeus:compradorPayExpiredQueue";
    /**
     * 处置服务商提醒到期时间标识
     */
    public static final String DISPOSAL_DEADLINE_SMS = "disposal_deadline_";

    /**
     * 一级合伙人接单到期标识
     */
    public static final String PROVIDER_SURVEY_KEY = "provider_survey_deadline_";


    /**
     * 预招商支付到期时间
     */
    public static final String ENROLLING_END_PAY_TIME_QUEUE = "zeus:enrollingEndPayEnqueue";

    /**
     * 账户注册队列名称
     */
    public static final String RABBITMQ_ACCOUNT_REGISTER_QUEUE = "zeus:accountRegisterQueue";
    /**
     * 用户认证申请通过队列名称
     */
    public static final String RABBITMQ_USER_APPLY_APPROVE_QUEUE = "zeus:userApplyApproveQueue";
    /**
     * 企业认证申请通过队列名称
     */
    public static final String RABBITMQ_COMPANY_APPLY_APPROVE_QUEUE = "zeus:companyApplyApproveQueue";
    /**
     * 下线注册完成 （第二次，3天内并未完成认证）队列名称
     */
    public static final String RABBITMQ_SUBORDINATE_ACCOUNT_REGISTER_SECOND_QUEUE = "zeus:subordinateAccountRegisterSecondQueue";
    /**
     * 下线认证完成（第二次）队列名称
     */
    public static final String RABBITMQ_SUBORDINATE_AUTH_FINISH_SECOND_QUEUE = "zeus:subordinateAuthFinishSecondQueue";


    /**
     * 应用名称
     */
    public static final String APPLICATION_NAME_ADMIN = "admin";
    public static final String APPLICATION_NAME_PARTNER = "partner";
    public static final String APPLICATION_NAME_PLATFORM = "platform";
    public static final String APPLICATION_NAME_APPLET = "applet";

    /**
     * 排序参数
     */
    public static final String DESC = "desc";
    public static final String ASC = "asc";


    public static final String WX_ACCESS_TOKEN = "wx_access_token_";
    public static final String WX_JSAPI_TICKET = "wx_jsapi_ticket_";

    public static final Integer WX_TIME_OUT = 7000;


    public static final String KAPTCHA_SESSION_KEY = "kaptcha_session_key_";
    public static final String ADMIN_KAPTCHA_SESSION_KEY = "admin_kaptcha_session_key_";
    public static final String AGENCY_KAPTCHA_SESSION_KEY = "agency_kaptcha_session_key_";


    public static final String SAVE_DRAFTS_KEY = "asset_drafts_key_";

    /**
     * 默认360PAI的小店
     */
    public static final Integer DEFAULT_APPLET_SHOP = 1;


    /**
     * 微信支付token 的缓存key值
     */
    public static final String WX_PAY_TOKEN_KEY = "wx_pay_token_key";



    /**
     * 计算微信支付token 的缓存key值
     */
    public static final String WX_PAY_CALCULATOR_TOKEN_KEY = "wx_pay_calculator_token_key";


    /**
     * 微信支付token 的缓存过期时间
     */
    public static final Long WX_PAY_TOKEN_TIME_OUT = 3600L;


    /**
     * 所有省份城市的缓存信息
     */
    public static final String ALL_PROVINCE_CITY_KEY = "all_province_token_key";

    /**
     * 所有省份城市区县的缓存信息
     */
    public static final String ALL_PROVINCE_CITY_AREA_KEY = "all_province__city_area_token_key";

    /**
     * 所有区县的缓存信息
     */
    public static final String ALL_AREA_KEY = "all_area_key";


    /**
     * 所有省份城市的缓存信息
     */
    public static final String ALL_PROVINCE_CITY_NAME_KEY = "all_province_name_token_key";


    /**
     * 所有省份城市的缓存信息过期时间
     */
    public static final Long ALL_PROVINCE_CITY_TIME_OUT = 7200L;



    public static final String AGENCY_DRAFTS_PREFIX_KEY = "A";

    /**
     * 其他银行logo
     */
    public static final String DEFAULT_BANK_LOGO = "https://cdn-images.360pai.com/FnAXrmLxq1WMhpitRgFAp-FUkUgU";


    /**
     * 报警邮件key值
     */
    public static String EMAIL_WARNING_KEY= "email_warning_key";


    /**
     * 计算器渠道key
     */
    public static final String WX_CHANNEL_KEY = "calculator";


    /**
     * 公众号获取token链接
     */
    public static final String MP_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    /**
     * 360拍公众号获取token 的缓存key值
     */
    public static final String MP_360PAI_TOKEN_KEY = "mp_360pai_token_key";
    /**
     * 360拍公众号债权实时播报模版id
     */
    public static final String MP_360PAI_DEBT_TEMPLATE_ID = "Lgtnz65VlpyDkRigASlg8__TCOg-zHujt6OWTrOQ56A";
    /**
     * 360拍公众号本息实时播报模版id
     */
    public static final String MP_360PAI_PRINCIPAL_INTEREST_TEMPLATE_ID = "Lgtnz65VlpyDkRigASlg8__TCOg-zHujt6OWTrOQ56A";
    /**
     * 发送公众号模版消息url
     */
    public static final String SEND_MP_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s";
    /**
     * 获取公众号用户信息url
     */
    public static final String GET_MP_USER_INFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN";
    /**
     * 获取公众号用户列表url
     */
    public static final String GET_MP_USER_LIST_URL = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=%s&next_openid=%s";
    /**
     * NONE
     */
    public static final  String NONE = "NONE";
}
