package com._360pai.core.service.assistant.impl;

import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.arch.core.redis.RedisCachemanager;
import com._360pai.core.aspact.GatewayMqSender;
import com._360pai.core.common.constants.ActivityEnum;
import com._360pai.core.dao.activity.AuctionActivityDao;
import com._360pai.core.dao.enrolling.EnrollingActivityDao;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.enrolling.EnrollingActivity;
import com._360pai.core.service.assistant.AssistantService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author xdrodger
 * @Title: AssistantServiceImpl
 * @ProjectName zeus-parent
 * @Description:
 * @date 2019/7/4 10:05
 */
@Slf4j
@Service
public class AssistantServiceImpl implements AssistantService {
    @Resource
    private RedisCachemanager redisCachemanager;
    @Resource
    public StringRedisTemplate stringRedisTemplate;
    private static SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.NORM_DATETIME_PATTERN);
    @Autowired
    private GatewayMqSender mqSender;
    @Autowired
    private AuctionActivityDao auctionActivityDao;
    @Autowired
    private EnrollingActivityDao enrollingActivityDao;


    @Override
    public void setActivityExpireTime(AuctionActivity activity) {
        try {
            Date now = new Date();
            // 活动已经结束
            if (activity.getEndAt().before(now)) {
                log.error("activityId={}，mode={}，end at {}，activity already finish", activity.getId(), activity.getMode(), sdf.format(activity.getEndAt()));
                return;
            }
            long timeout = (activity.getEndAt().getTime() - System.currentTimeMillis()) / 1000;
            long differentDays = DateUtil.differentDaysByDate(now, activity.getEndAt());
            log.info("activityId={}，mode={}，end at {}，remaining seconds={}，different days={}", activity.getId(), activity.getMode(), sdf.format(activity.getEndAt()), timeout, differentDays);
            if (differentDays <= 1) {
                redisCachemanager.set(SystemConstant.AUCTION_EXPIRE_PRE_KEY + activity.getId(), activity.getId() + "", timeout);
            }
            if (ActivityEnum.ActivityMode.DUTCH.getKey().equals(activity.getMode())) {
                // 活动已经开始
                if (activity.getBeginAt().before(new Date())) {
                    timeout = activity.getReductionPeriod() * 60;
                } else {
                    timeout = ((activity.getBeginAt().getTime() - System.currentTimeMillis()) / 1000) + activity.getReductionPeriod() * 60;

                }
                log.info("activityId={}，mode={}，reduction period={}，begin at {}，remaining seconds plus reduction period={}", activity.getId(), activity.getMode(), activity.getReductionPeriod(), sdf.format(activity.getBeginAt()), timeout);
                redisCachemanager.set(SystemConstant.AUCTION_DUTCH_PRICE_PRE_KEY + activity.getId(), activity.getId() + "", timeout);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mqSender.sendTryCatchExceptionEmail(activity.getId(), e);
        }
    }

    @Override
    public void setEnrollingActivityExpireTime(EnrollingActivity activity) {
        //设置活动过期时间当时间过期报名活动结束
        Date now = new Date();
        long timeout = (activity.getEndAt().getTime() - System.currentTimeMillis()) / 1000;
        long differentDays = DateUtil.differentDaysByDate(now, activity.getEndAt());
        log.info("enrollingActivityId={}，end at {}，remaining seconds={}，different days={}", activity.getId(), sdf.format(activity.getEndAt()), timeout, differentDays);
        if (differentDays <= 1) {
            redisCachemanager.set(SystemConstant.ENROLLING_SIGN_UP_KEY + activity.getId(), "finish", timeout);
        }
    }

    @Override
    public void batchEnrollingActivityExpireTime(List<String> list, Date endAt) {
        Date now = new Date();
        long timeout = (endAt.getTime() - System.currentTimeMillis()) / 1000;
        long differentDays = DateUtil.differentDaysByDate(now, endAt);
        log.info("批量导入设置招商活动结束时间={}，end at {}，remaining seconds={}，different days={}", list.get(0), sdf.format(endAt), timeout, differentDays);
        if (differentDays <= 1) {
            for (String id :list){
                //设置活动过期时间当时间过期报名活动结束
                redisCachemanager.set(SystemConstant.ENROLLING_SIGN_UP_KEY + id, "finish", timeout);
            }
        }
    }

    @Override
    public void setActivityExpireTime() {
        Date now = new Date();
        Calendar beginTime = Calendar.getInstance();
        beginTime.setTime(now);
        beginTime.set(Calendar.HOUR_OF_DAY, 0);
        beginTime.set(Calendar.MINUTE, 0);
        beginTime.set(Calendar.SECOND, 0);
        beginTime.set(Calendar.MILLISECOND, 0);

        Calendar endTime = Calendar.getInstance();
        endTime.setTime(now);
        endTime.add(Calendar.DAY_OF_MONTH, 1);
        endTime.set(Calendar.HOUR_OF_DAY, 23);
        endTime.set(Calendar.MINUTE, 59);
        endTime.set(Calendar.SECOND, 59);
        endTime.set(Calendar.MILLISECOND, 0);

        setAuctionActivityExpireTime(beginTime.getTime(), endTime.getTime());
        setEnrollingActivityExpireTime(beginTime.getTime(), endTime.getTime());
    }

    @Override
    public void removeActivityExpireKeyInRedisEndAtOver2Days() {
        Date now = new Date();
        Calendar endTime = Calendar.getInstance();
        endTime.setTime(now);
        endTime.add(Calendar.DAY_OF_MONTH, 1);
        endTime.set(Calendar.HOUR_OF_DAY, 23);
        endTime.set(Calendar.MINUTE, 59);
        endTime.set(Calendar.SECOND, 59);
        endTime.set(Calendar.MILLISECOND, 0);

        removeAuctionActivityExpireKeyInRedisEndAtOver2Days(endTime.getTime());
        removeEnrollingActivityExpireKeyInRedisEndAtOver2Days(endTime.getTime());
    }

    private void removeAuctionActivityExpireKeyInRedisEndAtOver2Days(Date endTime) {
        log.info("job remove auction activity redis expire key, endTime={}", DateUtil.getNormDateStr(endTime));
        int page = 1;
        int perPage = 200;
        Map<String, Object> params = new HashMap<>();
        params.put("endTime", DateUtil.getNormDateStr(endTime));
        while (true) {
            PageInfo<AuctionActivity> pageInfo = auctionActivityDao.getListWillEndAtOver2Days(page, perPage, params);
            log.info("job remove auction activity redis expire key, page={}, total={}", page, pageInfo.getTotal());
            for (AuctionActivity activity : pageInfo.getList()) {
                try {
                    Long ttl = stringRedisTemplate.getExpire(SystemConstant.AUCTION_EXPIRE_PRE_KEY + activity.getId());
                    log.info("job remove auction activity redis expire key, page={}, activityId={}, ttl={}", page, activity.getId(), ttl);
                    redisCachemanager.del(SystemConstant.AUCTION_EXPIRE_PRE_KEY + activity.getId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (pageInfo.isHasNextPage()) {
                page ++;
            } else {
                break;
            }
        }
    }

    private void removeEnrollingActivityExpireKeyInRedisEndAtOver2Days(Date endTime) {
        log.info("job remove enrolling activity redis expire key, endTime={}", DateUtil.getNormDateStr(endTime));
        int page = 1;
        int perPage = 200;
        Map<String, Object> params = new HashMap<>();
        params.put("endTime", DateUtil.getNormDateStr(endTime));
        while (true) {
            PageInfo<EnrollingActivity> pageInfo = enrollingActivityDao.getListWillEndAtOver2Days(page, perPage, params);
            log.info("job remove enrolling activity redis expire key, page={}, total={}", page, pageInfo.getTotal());
            for (EnrollingActivity activity : pageInfo.getList()) {
                try {
                    Long ttl = stringRedisTemplate.getExpire(SystemConstant.ENROLLING_SIGN_UP_KEY + activity.getId());
                    log.info("job remove enrolling activity redis expire key, page={}, activityId={}, ttl={}", page, activity.getId(), ttl);
                    redisCachemanager.del(SystemConstant.ENROLLING_SIGN_UP_KEY + activity.getId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (pageInfo.isHasNextPage()) {
                page ++;
            } else {
                break;
            }
        }
    }

    private void setAuctionActivityExpireTime(Date beginTime, Date endTime) {
        log.info("job set auction activity redis expire time, beginTime={}, endTime={}", DateUtil.getNormDateStr(beginTime), DateUtil.getNormDateStr(endTime));
        int page = 1;
        int perPage = 200;
        Map<String, Object> params = new HashMap<>();
        params.put("beginTime", DateUtil.getNormDateStr(beginTime));
        params.put("endTime", DateUtil.getNormDateStr(endTime));
        while (true) {
            PageInfo<AuctionActivity> pageInfo = auctionActivityDao.getListWillEndAt2Days(page, perPage, params);
            log.info("job set auction activity redis expire time, page={}, total={}", page, pageInfo.getTotal());
            for (AuctionActivity activity : pageInfo.getList()) {
                try {
                    // 活动已经结束
                    Date now = new Date();
                    if (activity.getEndAt().before(now)) {
                        continue;
                    }
                    Long ttl = stringRedisTemplate.getExpire(SystemConstant.AUCTION_EXPIRE_PRE_KEY + activity.getId());
                    log.info("job set auction activity redis expire time, page={}, activityId={}, ttl={}", page, activity.getId(), ttl);
                    if (ttl == -2) {
                        long differentDays = DateUtil.differentDaysByDate(now, activity.getEndAt());
                        long timeout = (activity.getEndAt().getTime() - System.currentTimeMillis()) / 1000;
                        log.info("activityId={}，mode={}，end at {}，remaining seconds={}，different days={}", activity.getId(), activity.getMode(), sdf.format(activity.getEndAt()), timeout, differentDays);
                        redisCachemanager.set(SystemConstant.AUCTION_EXPIRE_PRE_KEY + activity.getId(), activity.getId() + "", timeout);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (pageInfo.isHasNextPage()) {
                page ++;
            } else {
                break;
            }
        }
    }

    private void setEnrollingActivityExpireTime(Date beginTime, Date endTime) {
        log.info("job set enrolling activity redis expire time, beginTime={}, endTime={}", DateUtil.getNormDateStr(beginTime), DateUtil.getNormDateStr(endTime));
        int page = 1;
        int perPage = 200;
        Map<String, Object> params = new HashMap<>();
        params.put("beginTime", DateUtil.getNormDateStr(beginTime));
        params.put("endTime", DateUtil.getNormDateStr(endTime));
        while (true) {
            PageInfo<EnrollingActivity> pageInfo = enrollingActivityDao.getListWillEndAt2Days(page, perPage, params);
            log.info("job set enrolling activity redis expire time, page={}, total={}", page, pageInfo.getTotal());
            for (EnrollingActivity activity : pageInfo.getList()) {
                try {
                    // 活动已经结束
                    Date now = new Date();
                    if (activity.getEndAt().before(now)) {
                        continue;
                    }
                    Long ttl = stringRedisTemplate.getExpire(SystemConstant.ENROLLING_SIGN_UP_KEY + activity.getId());
                    log.info("job set enrolling activity redis expire time, page={}, activityId={}, ttl={}", page, activity.getId(), ttl);
                    if (ttl == -2) {
                        long differentDays = DateUtil.differentDaysByDate(now, activity.getEndAt());
                        long timeout = (activity.getEndAt().getTime() - System.currentTimeMillis()) / 1000;
                        log.info("enrollingActivityId={}，end at {}，remaining seconds={}，different days={}", activity.getId(), sdf.format(activity.getEndAt()), timeout, differentDays);
                        redisCachemanager.set(SystemConstant.ENROLLING_SIGN_UP_KEY + activity.getId(), "finish", timeout);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (pageInfo.isHasNextPage()) {
                page ++;
            } else {
                break;
            }
        }
    }
}
