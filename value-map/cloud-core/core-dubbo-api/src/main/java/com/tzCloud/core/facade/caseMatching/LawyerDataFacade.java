package com.tzCloud.core.facade.caseMatching;

import com.tzCloud.arch.common.PageInfoResp;

/**
 * @author xiaolei
 * @create 2019-03-26 11:05
 */
public interface LawyerDataFacade
{
    /**
     * t_lawyer_data 增量律师身份
     */
    PageInfoResp lawyerIdentityIncrement(int pageNum, int pageSize);

    /**
     * t_lawyer_data 增量胜率更新
     */
    void winFlagUpdateIncrement();
}
