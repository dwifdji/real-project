package com.tzCloud.core.facade.legalEngine;

/**
 * @author xiaolei
 * @create 2019-06-17 09:53
 */
public interface LawyerSqlDataInitializeFacade {

    /**
     * dsrxx_parse_status 初始化doc_id
     */
    void dsrxxParseStatusDataInit();

    /**
     * case_html_dsrxx表   需要 case_html_data 表
     */
    void caseHtmlDsrxxInit();

    /**
     * t_lawyer_data表身份信息   需要 case_html_data 表
     *
     * 新增数据时可以直接跑
     */
    void lawyerDataIdentityInit();

    /**
     * t_lawyer_data表输赢标识   需要 case_html_data 表
     * 新增数据时可以直接跑
     */
    void lawyerDataWinFlagInit();

    /**
     * t_lawyer_data 表 lawyer_id 初始化  需要t_parse_lawyer_info表
     */
    void lawyerDataLawyerIdInit();

    /**
     * t_parse_lawyer_info表  需要 t_lawyer_data、t_lawyer_info、t_lawyer_firm_info 表
     */
    void parseLawyerInfoInit();

    /**
     * t_parse_lawyer_info表 更新 lawFirmId
     * 新增数据时可以直接跑
     */
    void parseLawyerLawFirmIdInit();
}
