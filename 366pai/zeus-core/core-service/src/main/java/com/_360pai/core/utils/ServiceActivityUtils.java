package com._360pai.core.utils;

import com._360pai.core.condition.assistant.FavoriteActivityCondition;
import com._360pai.core.condition.assistant.NotifyPartyActivityCondition;
import com._360pai.core.condition.assistant.TJobFocusReminderCountRecordCondition;
import com._360pai.core.dao.assistant.FavoriteActivityDao;
import com._360pai.core.dao.assistant.NotifyPartyActivityDao;
import com._360pai.core.dao.assistant.TJobFocusReminderCountRecordDao;
import com._360pai.core.model.assistant.FavoriteActivity;
import com._360pai.core.model.assistant.NotifyPartyActivity;
import com._360pai.core.model.assistant.TJobFocusReminderCountRecord;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

import static com._360pai.core.job.jobUtils.FocusAndReminderUtils.ACTIVITY_TYPE;
import static com._360pai.core.job.jobUtils.FocusAndReminderUtils.FOCUS_RECODE_TYPE;
import static com._360pai.core.job.jobUtils.FocusAndReminderUtils.REMINDER_RECODE_TYPE;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2019/4/4 13:30
 */
@Component
@Slf4j
public class ServiceActivityUtils {
    @Resource
    private FavoriteActivityDao             favoriteActivityDao;
    @Resource
    private NotifyPartyActivityDao          notifyPartyActivityDao;
    @Resource
    private TJobFocusReminderCountRecordDao tJobFocusReminderCountRecordDao;

    /**
     * 获取 拍卖 关注数
     *
     * @param activityId 拍品id
     * @return
     */
    public Integer getFocusNum(Integer activityId) {
        FavoriteActivityCondition condition1 = new FavoriteActivityCondition();
        condition1.setActivityId(activityId);
        List<FavoriteActivity> favoriteActivities = favoriteActivityDao.selectList(condition1);
        return getVirtualNum(activityId, ACTIVITY_TYPE, FOCUS_RECODE_TYPE)
                + (favoriteActivities == null ? 0 : favoriteActivities.size());

    }


    /**
     * 获取 拍卖 提醒数
     *
     * @param activityId 拍品id
     * @return
     */
    public Integer getReminderNum(Integer activityId) {
        NotifyPartyActivityCondition condition = new NotifyPartyActivityCondition();
        condition.setActivityId(activityId);
        List<NotifyPartyActivity> notifyPartyActivities = notifyPartyActivityDao.selectList(condition);
        return getVirtualNum(activityId, ACTIVITY_TYPE, REMINDER_RECODE_TYPE)
                + (notifyPartyActivities == null ? 0 : notifyPartyActivities.size());
    }


    /**
     * 获取 job 跑的虚拟数目
     *
     * @param activityId
     * @param activityType
     * @param recodeType
     * @return
     */
    public Integer getVirtualNum(Integer activityId, Integer activityType, Integer recodeType) {
        Integer                               sum       = 0;
        TJobFocusReminderCountRecordCondition condition = new TJobFocusReminderCountRecordCondition();
        condition.setActivityId(activityId);
        condition.setActivityType(activityType);
        condition.setRecodeType(recodeType);
        List<TJobFocusReminderCountRecord> reminderCountRecordList = tJobFocusReminderCountRecordDao.selectList(condition);
        if (CollectionUtils.isNotEmpty(reminderCountRecordList)) {
            sum = reminderCountRecordList.stream().mapToInt(TJobFocusReminderCountRecord::getCountNum).sum();
        }
        return sum;
    }


}
