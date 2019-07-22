package com._360pai.test;

import com._360pai.core.job.JobUtils;
import com._360pai.core.job.jobUtils.FocusAndReminderUtils;
import com._360pai.core.service.assistant.AssistantService;
import com._360pai.gateway.facade.FddSignatureFacade;
import com.alibaba.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/12/4 15:48
 */
public class JobTest extends TestBase {
    @Autowired
    private JobUtils jobUtils;

    @Autowired
    private FocusAndReminderUtils focusAndReminderUtils;

    @Autowired
    private AssistantService assistantService;

    @Test
    public void Test3() {
        focusAndReminderUtils.focusAndReminderAutoIncrement();

    }

    @Reference(version = "1.0.0")
    private FddSignatureFacade fddSignatureFacade;

    @Test
    public void Test() {
            jobUtils.activityViewCountAutoIncrement();
    }

    @Test
    public void test2() {
        jobUtils.thirdEnrollingActivityStatusProcess();
    }


    @Test
    public void testQuerySignQuartz() {
        try {
            fddSignatureFacade.querySignQuartz();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--");
    }

    @Test
    public void testSetActivityExpireTime() {
        try {
            assistantService.setActivityExpireTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--");
    }

}
