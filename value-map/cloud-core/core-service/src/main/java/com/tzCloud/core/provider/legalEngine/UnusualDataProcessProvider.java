package com.tzCloud.core.provider.legalEngine;

import com.alibaba.dubbo.config.annotation.Service;
import com.tzCloud.core.facade.legalEngine.UnusualDataProcessFacade;
import com.tzCloud.core.service.UnusualDataProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xiaolei
 * @create 2019-05-07 09:07
 */
@Component
@Service(version = "1.0.0")
public class UnusualDataProcessProvider implements UnusualDataProcessFacade {

    @Autowired
    private UnusualDataProcessService unusualDataProcessService;

    @Override
    public void unusualLawyerDataProcess1() {
        unusualDataProcessService.unusualLawyerDataProcess1();
    }

    @Override
    public void unusualLawyerDataProcess2() {
        unusualDataProcessService.unusualLawyerDataProcess2();
    }


    @Override
    public void unusualLawyerDataProcess3() {
        unusualDataProcessService.unusualLawyerDataProcess3();
    }


}
