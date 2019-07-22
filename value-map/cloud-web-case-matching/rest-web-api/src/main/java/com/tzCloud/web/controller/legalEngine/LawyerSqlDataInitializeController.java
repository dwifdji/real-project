package com.tzCloud.web.controller.legalEngine;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tzCloud.core.facade.legalEngine.LawyerSqlDataInitializeFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xiaolei
 * @create 2019-07-10 14:37
 */
@RestController
public class LawyerSqlDataInitializeController {

    @Reference(version="1.0.0")
    private LawyerSqlDataInitializeFacade lawyerSqlDataInitializeFacade;

    AtomicInteger poolNumber = new AtomicInteger(1);
    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 600,
            TimeUnit.SECONDS, new ArrayBlockingQueue<>(25), r -> new Thread(r,"sql_initialize_pool" + poolNumber.getAndIncrement()));

    @GetMapping("/open/sqlInitialize/dsrxxParseStatusDataInit")
    public void dsrxxParseStatusDataInit() {
        threadPoolExecutor.submit(() -> lawyerSqlDataInitializeFacade.dsrxxParseStatusDataInit());
    }

    @GetMapping("/open/sqlInitialize/caseHtmlDsrxxInit")
    public void caseHtmlDsrxxInit() {
        threadPoolExecutor.submit(() -> lawyerSqlDataInitializeFacade.caseHtmlDsrxxInit());
    }

    @GetMapping("/open/sqlInitialize/lawyerDataIdentityInit")
    public void lawyerDataIdentityInit() {
        threadPoolExecutor.submit(() -> lawyerSqlDataInitializeFacade.lawyerDataIdentityInit());
    }

    @GetMapping("/open/sqlInitialize/lawyerDataWinFlagInit")
    public void lawyerDataWinFlagInit() {
        threadPoolExecutor.submit(() -> lawyerSqlDataInitializeFacade.lawyerDataWinFlagInit());
    }

    @GetMapping("/open/sqlInitialize/parseLawyerInfoInit")
    public void parseLawyerInfoInit() {
        threadPoolExecutor.submit(() -> lawyerSqlDataInitializeFacade.parseLawyerInfoInit());
    }

    @GetMapping("/open/sqlInitialize/lawyerDataLawyerIdInit")
    public void lawyerDataLawyerIdInit() {
        threadPoolExecutor.submit(() -> lawyerSqlDataInitializeFacade.lawyerDataLawyerIdInit());
    }

    @GetMapping("/open/sqlInitialize/parseLawyerLawFirmIdInit")
    public void parseLawyerLawFirmIdInit() {
        threadPoolExecutor.submit(() -> lawyerSqlDataInitializeFacade.parseLawyerLawFirmIdInit());
    }

}
