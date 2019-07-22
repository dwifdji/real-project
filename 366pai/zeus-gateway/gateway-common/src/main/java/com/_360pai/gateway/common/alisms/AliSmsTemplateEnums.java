package com._360pai.gateway.common.alisms;

/**
 *阿里现有短信模板枚举类
 */
public enum AliSmsTemplateEnums {
    SMS_SEND_CODE("SMS_133964282", "发送短信验证码"),//{"code":验证码字段,"duration":验证码有效时间}
    ACCOUNT_REGISTER_SUCCESS("SMS_133963091", "用户注册成功发送"),//null
    USER_APPLY__FOR_CUSTOMER_SERVICE("SMS_133978670", "个人申请验证(发送给客服)"),//{"personal_name":用户姓名}
    USER_APPLY_FOR_USER("SMS_133978135", "个人申请验证(to个人)"),//{"personal_name":用户姓名}
    USER_APPLY_AUTHENTICATION_SUCCESS("SMS_133973655", "个人认证成功"),//{"personal_name":用户姓名}
    COMPANY_APPLY_FOR_COMPANY("SMS_133973662","企业申请认证（to公司）"),//{"company_name":公司名称}
    COMPANY_APPLY_FOR_CUSTOMER_SERVICE("SMS_133978718","企业申请认证（to客服）"),//{"company_name":公司名称}
    COMPANY_APPLY_AUTHENTICATION_SUCCESS("SMS_133978723","企业申请认证成功"),//{"company_name":公司名称}
    PARTY_SUBMIT_ASSET_SUCCESS("SMS_133973782","用户上传拍品(to用户)"),//asset_name:拍品名称 price：价格
    ASSET_NEEDING_APPROVED_FOR_AGENCY("SMS_133973781","用户上传拍品(to机构)"),//asset_name:拍品名称 price：价格
    DFFT_PAY_BIND_SUCCESS("SMS_133963771","个人绑定东方付通账号"),//null
    FADADA_BIND_SUCCESS("SMS_133963688","法大大绑定成功"),//null
    AGENCY_APPROVE_ASSET_AND_SUBMIT_ACTIVITY("SMS_135801179","送拍机构审核完成,等待平台审核(to: 送拍机构,委托人)"),//agency:机构名称 asset_name：债权名称
    PLATFORM_NEED_APPROVE_ACTIVITY("SMS_135801176","送拍机构审核完成,等待平台审核(to: 客服)"),//agency:机构名称 asset_name：债权名称

    PLATFORM_APPROVED_ACTIVITY("SMS_136380150","平台审核成功，需要签署委托协议(to: 送拍机构、委托人)"),// asset_name：债权名称
    ALL_SIGN_ACTIVITY_AGREEMENT("SMS_135796237","协议签署完成, 活动上线(to: 委托人、送拍机构、平台)"),//asset_name：债权名称
    AGENCY_UNION_ACTIVITY("SMS_135796243"," 联拍机构发布通知(to: 联拍机构)"),// asset_name：债权名称
    ACTIVITY_DEAL_NOTIFICATION("","成交信息通知（to买受人、联拍机构、平台、送拍机构、委托人）"),//
    DEAL_AGREE_NEED_CONFIRMATION("SMS_135797431","需签署成交确认书通知(to: 买受人、委托人)"),// asset_name：债权名称 buyer：购买人 saler：委托人
    PARTY_SETTING_NOTIFY_ON_ACTIVITY("SMS_138072109"," 用户设置拍卖提醒(to:设置为通知的用户)"),//params none
    AGENCY_APPLY_AUTHENTICATION("SMS_135791362","机构认证申请通知（to：申请人、平台）"),


    PARTY_PARTICIPATING_ACTIVITY("SMS_135807420","参拍信息通知, 即参拍lock_deposit(to: 联拍机构、委托人)"),// asset_name：债权名称 date: 上拍时间



    AGENCY_APPLY_AUTHENTICATION_SUCCESS("SMS_135796358","机构认证申请成功通知（to：申请人、平台）"),

    //V2版本新版信息模板发送
    //-----------------预招商短信提醒------------------

    /**
     *
     * 预招商设置提醒
     * 没有参数
     *
     */
    SET_ENROLLING_REMINDE("SMS_147196359", "用户设置预招商活动提醒成功（to: 设置提醒人）"),

    /**
     * 预招商活动结束前一个小时短信提醒
     * enrolling_name 预招商名称
     */
    ENROLLING_REMINDE_END("SMS_147418988", "预招商活动结束前一个小时提醒（to: 活动提醒人）"),

    /**
     * 提交预招商
     * enrolling_code 预招商编码
     */
    INVESTMENT_SUBMISSION("SMS_147437626", "预招商活动提交（to: 送拍机构）"),



    /**
     * 预招商平台审核通过
     * enrolling_code 预招商编码
     */
    WEB_APPROVED("SMS_151576732", "平台审核通过（to: 委托人）"),


    /**
     * 平台审核不通过
     * enrolling_code 预招商编码
     */
    WEB_AUDIT_FAIL("SMS_151576736", "平台审核不通过（to: 委托人）"),


    /**
     * 平台审核通过 支付提示
     * enrolling_code 预招商编码
     */
    WEB_APPROVED_PAY_NOTIFY("SMS_151576741", "支付提示（to: 委托人）"),


    /**
     * 预招商活动结束通知
     * enrolling_code 预招商编码
     */
    ACTIVITY_END("SMS_151576754", "预招商活动结束通知（to: 委托人）"),




    //-----------------用户模块短信------------------
    /**
     *  联拍机构认证通过
     *  company_name 机构名称
     */
    AGENCY_CERTIFICATION_PASS("SMS_147201403", "联拍机构认证完成（to: 拍卖行）"),


    /**
     *  联拍机构认证通过
     *  company_name 机构名称
     *  code 机构子站码
     */
    AGENCY_CERTIFICATION_PASS_TWO("SMS_163525543", "${company_name}，联拍机构认证完成，子站已开通，子站简码为：${code}。您可以直接登录拍卖机构后台进行下一步操作。"),


    /**
     *  机构认证申请
     *  company_name 机构名称
     */
    AGENCY_CERTIFICATION_APPLICATION("SMS_147196377", "联拍机构认证申请（to: 拍卖行）"),

    /**
     *  开通签章通知
     *
     */
    OPEN_ELECTRONIC_SIGNATURE("SMS_147201409", "成功绑定电子签章（to: 每个开通者）"),

    /**
     *  第三方支付开通成功通知
     *
     */
    PAYMENT_OPENING_SUCCESSFUL("SMS_147196385", "第三方支付开通成功通知（to: 申请第三方支付人）"),

    /**
     *  企业认证申请审核通过
     * personal_name 企业认证申请人
     */
    BUSINESS_APPLICANT_SUCCESSFUL("SMS_147196408", "企业认证申请审核通过（to: 企业申请人）"),

    /**
     *  个人认证申请审核通过
     * personal_name 个人认证申请人
     */
    PERSION_APPLICANT_SUCCESSFUL("SMS_147418983", "个人认证申请审核通过（to: 申请人）"),




    //-----------------处置服务相关短信------------------
    /**
     *  处置服务投标（非平台）
     * disposal_type 处置服务类型
     * disposal_code 需求号
     */
    DISPOSAL_SERVICE_BID("SMS_147417822", "处置服务投标（非平台）（to: 需求方）"),

    /**
     *  处置服务投标（平台类标的）
     * disposal_code 需求号
     * disposal_type 处置服务类型
     */
    DISPOSAL_SERVICE_BID_PLATFORM_PASS("SMS_147418437", "to: 需求方 ）"),

    /**
     *  处置发布时间结束后的一刻 to: 处置服务商（非中标方）
     * disposal_code 需求号
     */
    BID_REQUEST_FAILED("SMS_147417825", "处置发布时间结束后的一刻（to: 处置服务商（非中标方） ）"),

    /**
     *  处置发布时间结束后的一刻 to: 处置服务商（中标方）
     * disposal_code 需求号
     */
    BID_REQUEST_SUCCESSFUL("SMS_147437597", "处置发布时间结束后的一刻（to: 处置服务商（中标方） ）"),

    /**
     *  处置发布时间结束后的一刻 to: 需求方
     * disposal_code 需求号
     * disposal_type 处置服务类型
     *
     */
    DEMAND_SIDE_BIDDING_FAILED("SMS_148591572", "处置发布时间结束后的一刻（to: 需求方 ）"),

    /**
     *  处置服务审核通过
     * disposal_code 需求号
     * disposal_type 处置服务类型
     *
     * 原来  SMS_147438201
     */
    DISPOSAL_SERVICE_BID_PASS("SMS_151546793", "处置发布时间结束后的一刻（to: 需求方 ）"),



    /**
     *  处置服务审核不通过
     * disposal_code 需求号
     * disposal_type 处置服务类型
     */
    DISPOSAL_SERVICE_BID_NOT_PASS("SMS_151546799", "处置服务审核不通过（to: 需求方 ）"),




    /**
     *  处置服务审核通过支付提示
     * disposal_code 需求号
     * disposal_type 处置服务类型
     */
    DISPOSAL_SERVICE_BID_PASS_PAY_NOTIFY("SMS_151576770", "处置服务审核通过支付提示（to: 需求方 ）"),






    //-----------------配资乐相关短信------------------
    /**
     *  配资乐审核通过
     * disposal_code 需求号
     */
    CONFIGURING_ASSETS_PASS("SMS_147437612", "处置发布时间结束后的一刻（to: 需求方 ）"),

    /**
     *  配资乐补充资料完成审核通过
     * disposal_code 需求号
     */
    CONFIGURING_ASSETS_ADDITIONAL_MATERIALS_PASS("SMS_147417854", "处置发布时间结束后的一刻（to: 需求方 ）"),

    /**
     *  我要意向（非平台类的配资需求）
     * disposal_code 需求号
     */
    CONFIGURING_ASSETS_ADDITIONAL_CAPITAL_REQUIREMENT("SMS_147437691", "我要意向（非平台类的配资需求）（to: 需求方 ）"),




    //-----------------资产大买办相关短信------------------
    /**
     *  资产大买办审核通过
     * disposal_code 需求号
     *
     */
    ASSET_BUYING_OFFICE_PASS("SMS_148611608", "处置发布时间结束后的一刻（to: 需求方 ）"),

    /**
     *  资产大买办我有意向
     * disposal_code 需求号
     */
    ASSET_BUYING_OFFICE_INTENTION("SMS_147417869", "资产大买办我有意向（to: 需求方 ）"),








    //-----------------拍品相关短信------------------

    /**
     * 竞拍人拍得物品成功发送短信
     *  buyer 竞拍人
     *  asset_name 拍卖活动名称
     */
    SUCCESSFUL_AUCTION("SMS_146808545", "拍卖成功短信通知（to 竞拍人）"),

    /**
     *拍品设置提醒短信通知
     *
     */
    AUCTION_SET_REMINDE("SMS_147201379", "拍品设置提醒短信通知（to 当前设置提醒人）"),

    /**
     * 拍品开拍前提醒
     * asset_name 拍品名称
     */
    START_AUCTION_REMINDE("SMS_147196361", "拍卖活动开拍前一个小时短信通知（to 当前设置提醒人）"),

    /**
     * 平台审核成功，签署委托协议
     * asset_name 拍品名称
     */
    PLATFORM_REVIEW_PASSED("SMS_147201392", "拍品审核通过（to 委托人）"),

    /**
     *  拍品发货通知
     *  asset_name 拍卖名称
     */
    AUCTION_SHIP("SMS_147201404", "拍品发货通知短信（to: 委托人）"),

    /**
     *  拍品收货通知
     *  asset_name 拍卖名称
     */
    AUCTION_RECEIPT("SMS_147196379", "拍品收货通知短信（to: 委托人）"),

    /**
     *  协议签署完成通知
     *  asset_name 拍卖名称
     */
    SUCCESSFUL_SIGNING_AGREEMENT("SMS_147196382", "协议签署完成通知短信（to: 委托人）"),

    /**
     *  送拍机构审核完成
     *  asset_name 拍卖名称
     *  agency 送拍机构
     */
    AGENCY_SUCCESSFUL_REVIEW("SMS_147201411", "送拍机构审核完成通知短信（to: 委托人）"),

    /**
     *  平台审核通知
     *  asset_name 拍卖名称
     *  agency 送拍机构
     */
    PLATFORM_REVIEW("SMS_147201413", "平台审核通知短信（to: 平台审核人）"),

    /**
     *  委托人发布拍品的通知
     *  asset_name 拍卖名称
     *  price 最低成交价
     */
    RELEASE_LOT("SMS_147201416", "委托人发布拍品短信通知（to: 委托人）"),

    /**
     *  发布拍品通知机构
     *  user_name 拍品发布者
     *  asset_name 拍品名称
     */
    RELEASE_LOT_NOTIFICATION_AGENCY("SMS_147201422", "发布拍品通知机构（to: 送拍机构）"),

    /**
     *  拍卖活动结束
     *  asset_name 拍品名称
     */
    AUCTION_ACTIVITY_END("SMS_147438583", "拍卖活动结束（to: 设置提醒人）"),

    /**
     *  签署成交确认书通知
     *  buyer 买受人
     *  asset_name 拍品名称
     */
    SIGNING_CONFIRMATION_SUCCESSFUL("SMS_147438623", "签署成交确认书通知（to: 委托人+竞买人）"),



    /**
     *  线下参拍资格提醒
     *  asset_name 拍品名称
     *  【360PAI】您线下缴纳标的${asset_name}的保证金已收到，您已获得参拍资格，请火速前往拍卖页面进行拍卖
     */
    AUCTION_OFFLINE_REMIND("SMS_152288271", "下线获得参拍资格（to: 竞买人）"),



    //-----------------快速通道短信------------------
    /**
     *  处置服务商认证通过
     *
     *  恭喜您，您的处置服务商认证已通过。
     */
    DISPOSAL_APPLY_SUCCESS("SMS_151830049", "处置服务商认证通过（to: 申请人）"),


    /**
     *  处置服务商认证拒绝
     * 对不起，您的处置服务商认证被拒绝。电话咨询请致电：400-015-0005
     */
    DISPOSAL_APPLY_FAILURE("SMS_151835006", "处置服务商认证拒绝（to: 申请人）"),

    /**
     *  资金服务商认证通过
     *  恭喜您，您的资金服务商认证已通过。
     */
    FUND_SERVICE_PROVIDER_APPLY_SUCCESS("SMS_151830051", "资金服务商认证通过（to: 申请人）"),


    /**
     *  资金服务商认证拒绝
     *  对不起，您的资金服务商认证被拒绝。电话咨询请致电：400-015-0005
     */
    FUND_SERVICE_PROVIDER_APPLY_FAILURE("SMS_151835007", "资金服务商认证拒绝（to: 申请人）"),



    /**
     *  联拍机构认证通过
     *  恭喜您，您的联拍机构认证已通过。
     */
    AGENCY_APPLY_SUCCESS("SMS_151835009", "联拍机构认证通过（to: 申请人）"),


    /**
     *  联拍机构认证拒绝
     *  对不起，您的联拍机构认证被拒绝。电话咨询请致电：400-015-0005
     */
    AGENCY_APPLY_FAILURE("SMS_151835010", "联拍机构认证拒绝（to: 申请人）"),




    /**
     *
     * 用户申请提现成功后
     *  user_name 申请人
     */
    USER_WITHDRAWAL("SMS_152514734", "用户申请提现后(to: 财务)"),

    /**
     * 分佣提醒
     */
    COMMISSION_REMINDER("SMS_152514730", "分佣提醒"),

    /**
     * 到账提醒
     */
    ARRIVAL_REMINDER("SMS_152514724", "到账提醒"),

    /**
     * 注册完成后
     * default_password
     * SMS_152546455
     */
    REGISTRATION_SUCCESS("SMS_156895566", "注册完成后"),




    /**
     * 认证成功后短信通知f
     * user_name
     */
    APPLET_APPLY_SUCCESS("SMS_154594286", "亲爱的${user_name}，恭喜您认证成功！"),

    /**
     * 头像审核失败发送信息
     * to 申请者
     */
    APPLET_AVATAR_REVIEW("SMS_159627351", "头像审核失败发送信息"),


    /**
     * 处置服务商后台申请
     */
    DISPOSE_ADMIN_CREATE("SMS_156277333", "处置服务商认证通过（to: 申请人）"),



    //-----------------租赁权拍卖------------------

    /**
     * 一级审核项目，发送对象为终审；二级审核项目，发送对象为初审
     */
    LEASE_SUBMIT_AUDIT("SMS_164831079", "${activity_name}项目已提交审核，请尽快查阅审批。"),


    /**
     * 租赁权-初审通过通知
     */
    LEASE_FIRST_PUSH("SMS_164831099", "${activity_name}项目通过初审，请尽快查阅审批。"),



    /**
     *租赁权-终审不通过
     */
    SECOND_REVIEW_REJECTION("SMS_164831108", "${activity_name}项目终审未通过，请尽快查阅审批。"),



    /**
     * 租赁权-初审不通过通知
     */
    FIRST_REVIEW_REJECTION("SMS_165060747", "您提交的${activity_name}项目未通过审核。拒绝原因请登录个人中心查询。"),



    /**
     * 租赁权-终审通过
     */
    WAITING_RELEASE("SMS_165070470", "您提交的${activity_name}项目通过审核，请尽快到个人中心完成项目发布。"),




    /**
     * 租赁权-意向报名
     */
    LEASE_PERIOD("SMS_165060814", "${user_name}已对${activity_name}项目报名，请尽快到个人中心进行审核。"),





    /**
     * 租赁权-意向报名通过
     */
    LEASE_PERIOD_PASS("SMS_165060821", "您对${activity_name}项目的报名，已通过资质审核。请登录网站缴纳保证金参拍。"),



    /**
     * 租赁权-意向报名拒绝
     */
    LEASE_PERIOD_REJECTION("SMS_165060826", "您对${activity_name}项目的报名，未通过资质审核。拒绝原因请登录个人中心查询。"),



    /**
     * 租赁权-初审通过
     */
    WAITING_SECOND_REVIEW("SMS_165070539", "${activity_name}项目通过初审，请尽快查阅审批。"),


    /**
     * 租赁权-平台审核通过 通知
     */
    LEASE_WEB_PASS("SMS_166665186", "您上拍的${asset_name}已通过平台审核，到达公告开始时间自动上线，可在个人中心查看进度。"),


    ;




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

    AliSmsTemplateEnums(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }



    public static String getDesc(String code) {
        for (AliSmsTemplateEnums e : AliSmsTemplateEnums.values()) {
            if(e.getCode().equals(code)) {
                return e.getDesc();
            }
        }
        return null;
    }
}
