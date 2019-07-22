package com.tzCloud.core.facade.legalEngine;

/**
 * @author xiaolei
 * @create 2019-07-05 11:22
 *
 * 文书新增数据  更新 case_html_dsrxx 、t_lawyer_data 、 t_parse_lawyer_info 数据
 * 更新到对应得es数据
 */
public interface LawyerSqlDataUpdateFacade {

    /**
     * 新增文书数据处理
     */
    void newCaseDataProcess();

}
