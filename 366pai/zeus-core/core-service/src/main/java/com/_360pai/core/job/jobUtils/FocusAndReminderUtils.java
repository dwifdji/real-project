package com._360pai.core.job.jobUtils;

import com._360pai.arch.common.utils.DateUtil;
import com._360pai.arch.core.sysconfig.properties.SystemProperties;
import com._360pai.core.condition.assistant.FavoriteActivityCondition;
import com._360pai.core.condition.assistant.NotifyPartyActivityCondition;
import com._360pai.core.condition.assistant.TJobFocusReminderCountRecordCondition;
import com._360pai.core.dao.activity.AuctionActivityDao;
import com._360pai.core.dao.assistant.FavoriteActivityDao;
import com._360pai.core.dao.assistant.NotifyPartyActivityDao;
import com._360pai.core.dao.assistant.TJobFocusReminderCountRecordDao;
import com._360pai.core.dao.enrolling.EnrollingActivityDao;
import com._360pai.core.dto.enrolling.EnrollingListReqDto;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.assistant.FavoriteActivity;
import com._360pai.core.model.assistant.NotifyPartyActivity;
import com._360pai.core.model.assistant.TJobFocusReminderCountRecord;
import com._360pai.core.model.enrolling.EnrollingActivity;
import com._360pai.core.service.activity.AuctionActivityViewCountService;
import com._360pai.core.service.enrolling.EnrollingActivityService;
import com._360pai.core.utils.ServiceActivityUtils;
import com._360pai.core.vo.enrolling.web.MyEnrollingActivityVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

import static org.apache.commons.lang3.RandomUtils.nextInt;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2019/4/3 16:25
 */
@Component
@Slf4j
public class FocusAndReminderUtils {

    private static final float                           LIST_PERCENT = 0.7f;
    @Autowired
    private              SystemProperties                systemProperties;
    @Autowired
    private              AuctionActivityDao              auctionActivityDao;
    @Autowired
    private              EnrollingActivityDao            enrollingActivityDao;
    @Autowired
    private              FavoriteActivityDao             favoriteActivityDao;
    @Autowired
    private              NotifyPartyActivityDao          notifyPartyActivityDao;
    @Autowired
    private              TJobFocusReminderCountRecordDao tJobFocusReminderCountRecordDao;
    @Autowired
    private              EnrollingActivityService        enrollingActivityService;
    @Autowired
    private              AuctionActivityViewCountService auctionActivityViewCountService;
    @Autowired
    private              ServiceActivityUtils            serviceActivityUtils;

    public static final Integer ACTIVITY_TYPE           = 1;
    public static final Integer ENROLLING_ACTIVITY_TYPE = 2;

    public static final Integer FOCUS_RECODE_TYPE    = 1;
    public static final Integer REMINDER_RECODE_TYPE = 2;

    public void focusAndReminderAutoIncrement() {
        if (StringUtils.isNotBlank(systemProperties.getFocusReminderCountIncrementFlag()) && Boolean.valueOf(systemProperties.getFocusReminderCountIncrementFlag())) {
            // 执行拍卖
            doActivity();
            // 执行预招商
            doEnrollingActivity();
        }
    }

    public static void main(String[] args){
        int needProcessSize = (int) Math.ceil(5 * LIST_PERCENT);

        Integer focusNum = getRandomValue(false);
        System.out.println("返回参数为。。。"+focusNum);
    }

    private void doActivity() {
        List<AuctionActivity> aheadAndBeginAuction = auctionActivityDao.getAheadAndBeginAuction();
        if (CollectionUtils.isEmpty(aheadAndBeginAuction)) {
            return;
        }
        // 打乱顺序
        Collections.shuffle(aheadAndBeginAuction);
        int needProcessSize = (int) Math.ceil(aheadAndBeginAuction.size() * LIST_PERCENT);

        Integer focusNum;
        Integer reminderNum;
        boolean focusFlag    = false;
        boolean reminderFlag = false;

        Integer processedNum = 0;
        for (AuctionActivity auctionActivity : aheadAndBeginAuction) {
            //如果达到需要处理的个数，则停止执行
            if (processedNum == needProcessSize) {
                break;
            }
            // 现有的真实关注数量
            FavoriteActivityCondition condition1 = new FavoriteActivityCondition();
            condition1.setActivityId(auctionActivity.getId());
            List<FavoriteActivity> favoriteActivities = favoriteActivityDao.selectList(condition1);

            // 现有的真实提醒数量
            NotifyPartyActivityCondition condition2 = new NotifyPartyActivityCondition();
            condition2.setActivityId(auctionActivity.getId());
            List<NotifyPartyActivity> notifyPartyActivities = notifyPartyActivityDao.selectList(condition2);

            // 生成 随机数
            focusNum = getRandomValue(checkIfFirstDay(auctionActivity.getId(), ACTIVITY_TYPE, FOCUS_RECODE_TYPE));
            reminderNum = getRandomValue(checkIfFirstDay(auctionActivity.getId(), ACTIVITY_TYPE, REMINDER_RECODE_TYPE));

            // 虚拟数量 + 真实数量 + 随机数
            Integer favoriteSize = serviceActivityUtils.getVirtualNum(auctionActivity.getId(), ACTIVITY_TYPE, FOCUS_RECODE_TYPE) + (favoriteActivities == null ? 0 : favoriteActivities.size()) + focusNum;
            Integer notifySize   = serviceActivityUtils.getVirtualNum(auctionActivity.getId(), ACTIVITY_TYPE, REMINDER_RECODE_TYPE) + (notifyPartyActivities == null ? 0 : notifyPartyActivities.size()) + reminderNum;

            // 获取浏览量
            Integer browseNum = auctionActivityViewCountService.viewCount(auctionActivity.getId());

            if (browseNum > favoriteSize && favoriteSize > 0) {
                focusFlag = true;
            }
            if (favoriteSize > notifySize && notifySize > 0) {
                reminderFlag = true;
            }

            boolean processed = false;
            if (focusNum != 0 && focusFlag) {
                insertTJobFocusReminderCountRecord(auctionActivity.getId(), ACTIVITY_TYPE, FOCUS_RECODE_TYPE, focusNum);
                processed = true;
            }
            if (reminderNum != 0 && reminderFlag) {
                insertTJobFocusReminderCountRecord(auctionActivity.getId(), ACTIVITY_TYPE, REMINDER_RECODE_TYPE, reminderNum);
                processed = true;
            }
            if (processed) {
                processedNum++;
            }
        }
    }

    private void doEnrollingActivity() {

        // 获取在线的预招商数据
        EnrollingListReqDto params = new EnrollingListReqDto();
        params.setStatus("ONLINE");
        List<MyEnrollingActivityVO> enrollingActivityVOS = enrollingActivityDao.searchHomeActivity(params);
        if (CollectionUtils.isEmpty(enrollingActivityVOS)) {
            return;
        }
        Collections.shuffle(enrollingActivityVOS);
        int needProcessSize = (int) Math.ceil(enrollingActivityVOS.size() * LIST_PERCENT);

        Integer focusNum;
        Integer reminderNum;
        boolean focusFlag;
        boolean reminderFlag;
        Integer processedNum = 0;
        for (MyEnrollingActivityVO myEnrollingActivityVO : enrollingActivityVOS) {

            if (processedNum == needProcessSize) {
                break;
            }
            focusFlag = false;
            reminderFlag = false;

            EnrollingActivity enrollingActivity = enrollingActivityService.getEnrollingActivityById(myEnrollingActivityVO.getId());

            // 根据是否第一次条件来生成随机数
            focusNum = getRandomValue(checkIfFirstDay(Integer.valueOf(myEnrollingActivityVO.getId()), ENROLLING_ACTIVITY_TYPE, FOCUS_RECODE_TYPE));
            reminderNum = getRandomValue(checkIfFirstDay(Integer.valueOf(myEnrollingActivityVO.getId()), ENROLLING_ACTIVITY_TYPE, REMINDER_RECODE_TYPE));

            Integer afterFocusNum = enrollingActivity.getFocusNumber();
            if (enrollingActivity.getBrowseNumber() > enrollingActivity.getFocusNumber() + focusNum
                    && enrollingActivity.getFocusNumber() + focusNum > 0) {
                afterFocusNum = enrollingActivity.getFocusNumber() + focusNum;
                enrollingActivity.setFocusNumber(enrollingActivity.getFocusNumber() + focusNum);
                focusFlag = true;
            }

            if (afterFocusNum > enrollingActivity.getReminderNumber() + reminderNum
                    && enrollingActivity.getReminderNumber() + reminderNum > 0) {
                enrollingActivity.setReminderNumber(enrollingActivity.getReminderNumber() + reminderNum);
                reminderFlag = true;
            }
            enrollingActivity.setId(Integer.valueOf(myEnrollingActivityVO.getId()));
            enrollingActivityService.updateEnrollingActivityById(enrollingActivity);

            boolean processed = false;
            if (focusNum != 0 && focusFlag) {
                insertTJobFocusReminderCountRecord(enrollingActivity.getId(), ENROLLING_ACTIVITY_TYPE, FOCUS_RECODE_TYPE, focusNum);
                processed = true;
            }
            if (reminderNum != 0 && reminderFlag) {
                insertTJobFocusReminderCountRecord(enrollingActivity.getId(), ENROLLING_ACTIVITY_TYPE, REMINDER_RECODE_TYPE, reminderNum);
                processed = true;
            }
            if (processed) {
                processedNum++;
            }
        }
    }

    private void insertTJobFocusReminderCountRecord(Integer activityId, Integer activityType, Integer recodeType, Integer countNum) {
        TJobFocusReminderCountRecord model = new TJobFocusReminderCountRecord();
        model.setActivityId(activityId);
        model.setActivityType(activityType);
        model.setRecodeType(recodeType);
        model.setCreateAt(new Date());
        model.setCountNum(countNum);
        model.setCreateDate(DateUtil.format(new Date(), DateUtil.NORM_DATE_PATTERN));
        tJobFocusReminderCountRecordDao.insert(model);
    }

    private boolean checkIfFirstDay(Integer activityId, Integer activityType, Integer recodeType) {
        TJobFocusReminderCountRecordCondition condition = new TJobFocusReminderCountRecordCondition();
        condition.setActivityId(activityId);
        condition.setActivityType(activityType);
        condition.setRecodeType(recodeType);
        TJobFocusReminderCountRecord reminderCountRecord = tJobFocusReminderCountRecordDao.selectFirst(condition);
        return reminderCountRecord == null;
    }

//
//    private Integer selectSum(Integer activityId, Integer activityType, Integer recodeType) {
//        Integer                               sum       = 0;
//        TJobFocusReminderCountRecordCondition condition = new TJobFocusReminderCountRecordCondition();
//        condition.setActivityId(activityId);
//        condition.setActivityType(activityType);
//        condition.setRecodeType(recodeType);
//        List<TJobFocusReminderCountRecord> reminderCountRecordList = tJobFocusReminderCountRecordDao.selectList(condition);
//        if (CollectionUtils.isNotEmpty(reminderCountRecordList)) {
//            sum = reminderCountRecordList.stream().mapToInt(TJobFocusReminderCountRecord::getCountNum).sum();
//        }
//        return sum;
//    }


    public static <T> List<T> getListByPercent(List<T> givenList, float percent) {
        if (CollectionUtils.isEmpty(givenList)) {
            return givenList;
        }
        int randomSeriesLength = (int) Math.ceil(givenList.size() * percent);
        Collections.shuffle(givenList);
        return givenList.subList(0, randomSeriesLength);
    }


    public static Integer getRandomValue(boolean isFirstDay) {
        if (isFirstDay) {
            return nextInt(0, 26);
        } else {
            return (int) (Math.random() * (5)) - 2;
        }
    }

}
