package com._360pai.core.common.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaolei
 * @create 2018-11-05 17:00
 */
public class ProtocolTypeConstants {

    /**********************协议********************************/

    // 尽调报告销售授权委托书
    public static final String JD_REPORT_SALE = "jd_report_sale";
    // 三方协议
    public static final String THIRD_PROTOCOL = "third_protocol";
    //尽调报告授权委托书
    public static final String JD_REPORT_COMMISSION = "jd_report_commission";

    /***********************协议类型********************************/

    // 尽调销售类型
    public static final String SURVEY_REPORT_SALE = "survey_report_sale";
    // 三方协议类型
    public static final String THIRD_AUTH         = "third_auth";


    // 尽调协议需签列表
    private static final String[] SURVEY_REPORT_SALE_LIST = {JD_REPORT_SALE};
    // 授权第三方协议需签列表
    private static final String[] THIRD_AUTH_LIST         = {THIRD_PROTOCOL,JD_REPORT_COMMISSION};


    private static Map<String, String[]> protocolType = new HashMap<>();

    static {
        protocolType.put(SURVEY_REPORT_SALE, SURVEY_REPORT_SALE_LIST);
        protocolType.put(THIRD_AUTH, THIRD_AUTH_LIST);
    }

    public static Map<String, String[]> getProtocolType() {
        return protocolType;
    }

}


