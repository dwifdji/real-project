package com.tzCloud.core.provider.caseMatching;

import com.alibaba.dubbo.config.annotation.Service;
import com.tzCloud.arch.common.PageInfoResp;
import com.tzCloud.core.facade.caseMatching.LawyerDataFacade;
import com.tzCloud.core.service.LawyerDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xiaolei
 * @create 2019-03-26 11:05
 */
@Service(version = "1.0.0")
@Component
public class LawyerDataProvider implements LawyerDataFacade
{

    @Autowired
    private LawyerDataService lawyerDataService;

    @Override
    public PageInfoResp lawyerIdentityIncrement(int pageNum, int pageSize)
    {
        return lawyerDataService.lawyerIdentityIncrement(pageNum, pageSize);
    }

    @Override
    public void winFlagUpdateIncrement() {
        lawyerDataService.winFlagUpdateIncrement();
    }

}
