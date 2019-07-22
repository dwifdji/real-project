package com._360pai.core.job.jobHandler;

import com._360pai.core.job.jobUtils.FocusAndReminderUtils;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 关注 和 提醒人数 自增
 * <p>
 * 规则：
 * 1.浏览数 大于 关注数 ， 关注数 大于 提醒数
 * 2.第一次 0 -- 26个 针对 70%的在线数据
 * 3.后面陆续用随机数 -2 -- 2
 *
 * @author : whisky_vip
 * @date : 2019/4/3 16:20
 */
@JobHandler(value = "focusAndReminderAutoIncrementHandler")
@Component
@Slf4j
public class FocusAndReminderAutoIncrementHandler extends IJobHandler {

    @Autowired
    private FocusAndReminderUtils focusAndReminderUtils;

    @Override
    public ReturnT<String> execute(String param) throws Exception {
        try {
            focusAndReminderUtils.focusAndReminderAutoIncrement();
        } catch (Exception e) {
            log.error("执行job focusAndReminderAutoIncrementHandler 报错", e);
            XxlJobLogger.log("error execute job focusAndReminderAutoIncrementHandler focusAndReminderAutoIncrement");
        }
        return SUCCESS;
    }
}

