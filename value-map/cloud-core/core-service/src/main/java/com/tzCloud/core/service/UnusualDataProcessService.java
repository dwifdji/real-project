package com.tzCloud.core.service;

/**
 * @author xiaolei
 * @create 2019-05-07 09:14
 */
public interface UnusualDataProcessService {

    /**
     * 异常数据处理1
     */
    void unusualLawyerDataProcess1();

    /**
     * 异常数据处理2
     * 更新t_lawyer_data 表的 lawyer_id
     */
    void unusualLawyerDataProcess2();

    /**
     * 异常数据处理3
     * t_parse_lawyer_info 新增记录
     */
    void unusualLawyerDataProcess3();

    /**
     * 异常数据处理4
     * t_parse_lawyer_info  law_firm_short 新解析和之前解析不一致的情况
     */
    void unusualLawyerDataProcess4();

    /**
     * 异常数据处理5
     * t_parse_lawyer_info 律所名称包含 '系', '指派的' 问题 修改
     */
    void unusualLawyerDataProcess5();

    /**
     * 异常数据处理6
     *  t_parse_lawyer_info 更新没有years, lawFirmId的数据
     */
    void unusualLawyerDataProcess6();

    /**
     * 异常数据处理7
     *  t_parse_lawyer_info 更新没有 lawFirmId的数据
     */
    void unusualLawyerDataProcess7();

    /**
     * 异常数据处理7 page
     *  t_parse_lawyer_info 更新没有 lawFirmId的数据
     */
    void unusualLawyerDataProcess7Page();

}
