package com.tzCloud.core.provider.legalEngine;

import com.alibaba.dubbo.config.annotation.Service;
import com.tzCloud.arch.common.PageInfoResp;
import com.tzCloud.core.facade.legalEngine.LawyerSqlDataInitializeFacade;
import com.tzCloud.core.service.CaseHtmlDsrxxService;
import com.tzCloud.core.service.DsrxxParseStatusService;
import com.tzCloud.core.service.LawyerDataService;
import com.tzCloud.core.service.UnusualDataProcessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xiaolei
 * @create 2019-06-17 09:57
 */
@Slf4j
@Service(version = "1.0.0")
@Component
public class LawyerSqlDataInitializeProvider implements LawyerSqlDataInitializeFacade {

    @Autowired
    private CaseHtmlDsrxxService caseHtmlDsrxxService;
    @Autowired
    private LawyerDataService lawyerDataService;
    @Autowired
    UnusualDataProcessService unusualDataProcessService;
    @Autowired
    private DsrxxParseStatusService dsrxxParseStatusService;

    @Override
    public void dsrxxParseStatusDataInit() {
        try {
            log.info("开始初始化当事人解析状态信息");
            long begin = System.currentTimeMillis();
            dsrxxParseStatusService.initDsrxxParseStatusData();
            log.info("初始化当事人解析状态信息结束， 花费时间: {}", (System.currentTimeMillis()-begin)/1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void caseHtmlDsrxxInit() {
        try {
            log.info("开始初始化案例当事人信息");
            long begin = System.currentTimeMillis();
            caseHtmlDsrxxService.parseDsrxxIncrementThread();
            log.info("初始化案例当事人信息结束， 花费时间: {}", (System.currentTimeMillis()-begin)/1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void lawyerDataIdentityInit() {
        try {
            log.info("开始初始化律师身份信息");
            long begin = System.currentTimeMillis();
            int pageNum=0, pageSize=10000;
            PageInfoResp pageInfoResp;
            int i = 0;
            while (true)
            {
                pageInfoResp = lawyerDataService.lawyerIdentityIncrement(pageNum, pageSize);
                if (!pageInfoResp.isHasNextPage())
                {
                    break;
                }
                i++;
                pageNum++;
                log.info("totalSize:{}, i:{}", pageInfoResp.getTotal(), i);
            }
            log.info("初始化律师身份信息结束， 花费时间: {}", (System.currentTimeMillis()-begin)/1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void lawyerDataWinFlagInit() {
        log.info("开始初始化律师胜负标识信息");
        long begin = System.currentTimeMillis();
        lawyerDataService.winFlagUpdateIncrementPage();
        log.info("初始化律师胜负标识信息结束， 花费时间: {}", (System.currentTimeMillis()-begin)/1000);
    }

    @Override
    public void lawyerDataLawyerIdInit() {
        log.info("开始初始化律师id信息");
        long begin = System.currentTimeMillis();
        unusualDataProcessService.unusualLawyerDataProcess2();
        log.info("初始化律师id信息结束， 花费时间: {}", (System.currentTimeMillis()-begin)/1000);
    }

    @Override
    public void parseLawyerInfoInit() {
        log.info("开始初始化律师信息");
        long begin = System.currentTimeMillis();
        unusualDataProcessService.unusualLawyerDataProcess3();
        log.info("初始化律师信息结束， 花费时间: {}", (System.currentTimeMillis()-begin)/1000);
    }

    @Override
    public void parseLawyerLawFirmIdInit() {
        log.info("开始初始化律所id信息");
        long begin = System.currentTimeMillis();
        unusualDataProcessService.unusualLawyerDataProcess7Page();
        log.info("初始化律所id信息结束， 花费时间: {}", (System.currentTimeMillis()-begin)/1000);
    }
}
