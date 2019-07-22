package com.tzCloud.core.service;

import com.tzCloud.arch.common.PageInfoResp;
import com.tzCloud.core.service.impl.LawyerDataServiceImpl;

import java.util.Map;

/**
 * @author xiaolei
 * @create 2019-03-04 19:21
 */
public interface LawyerDataService
{
    /**
     * 更新 t_lawyer_data 表胜负场
     */
    void winFlagUpdate();

    /**
     * t_lawyer_data 全量表胜负场
     */
    void winFlagUpdateFull();

    /**
     * t_lawyer_data 增量表胜负场
     */
    void winFlagUpdateIncrement();
    void winFlagUpdateIncrementPage();

    /**
     * 更新 t_lawyer_data 全量律师身份
     */
    void lawyerIdentityFull();

    /**
     * t_lawyer_data 增量律师身份
     */
    void lawyerIdentityIncrement();

    /**
     * t_lawyer_data 增量律师身份
     */
    PageInfoResp lawyerIdentityIncrement(int pageNum, int pageSize);

    /**
     * test 调用
     */
    Map<String, LawyerDataServiceImpl.TextPredict> allCaseSPCXInfo();

    /**
     * test 调用
     */
    void lawyerWinnerRate();
}
