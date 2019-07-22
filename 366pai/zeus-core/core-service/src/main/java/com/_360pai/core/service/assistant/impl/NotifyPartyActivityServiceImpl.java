package com._360pai.core.service.assistant.impl;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.constant.RedisKeyConstant;
import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.arch.core.redis.RedisCachemanager;
import com._360pai.arch.core.redis.RedisLock;
import com._360pai.core.common.constants.ActivityEnum;
import com._360pai.core.condition.account.AgencyPortalCondition;
import com._360pai.core.condition.account.TAgencyCondition;
import com._360pai.core.condition.activity.AgencyPortalActivityCondition;
import com._360pai.core.condition.assistant.NotifyPartyActivityCondition;
import com._360pai.core.dao.account.AgencyPortalDao;
import com._360pai.core.dao.account.TAccountDao;
import com._360pai.core.dao.account.TAgencyDao;
import com._360pai.core.dao.activity.AgencyPortalActivityDao;
import com._360pai.core.dao.activity.AuctionActivityDao;
import com._360pai.core.dao.assistant.NotifyPartyActivityDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.account.vo.PartyAccount;
import com._360pai.core.facade.activity.req.ActivityReq;
import com._360pai.core.model.account.AgencyPortal;
import com._360pai.core.model.account.TAgency;
import com._360pai.core.model.activity.AgencyPortalActivity;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.assistant.NotifyPartyActivity;
import com._360pai.core.service.account.AccountService;
import com._360pai.core.service.assistant.NotifyPartyActivityService;
import com._360pai.core.service.assistant.SmsHelperService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 描述:
 *
 * @author : whisky_vip
 * @date : 2018/8/16 14:24
 */
@Service
public class NotifyPartyActivityServiceImpl implements NotifyPartyActivityService {

    private static Logger logger = LoggerFactory.getLogger(NotifyPartyActivityServiceImpl.class);

    @Autowired
    private NotifyPartyActivityDao notifyPartyActivityDao;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private TAgencyDao agencyDao;
    @Autowired
    private AgencyPortalDao agencyPortalDao;
    @Autowired
    private AgencyPortalActivityDao agencyPortalActivityDao;
    @Autowired
    private AccountService accountService;
    @Autowired
    private SmsHelperService smsHelperService;
    @Resource
    private RedisCachemanager redisCachemanager;
    @Autowired
    private AuctionActivityDao auctionActivityDao;
    @Autowired
    private TAccountDao accountDao;

    @Override
    public PageInfo getAllByActivityId(int page, int perPage, NotifyPartyActivityCondition condition, String orderBy) {
        PageHelper.startPage(page, perPage);
        if (StringUtils.isNotBlank(orderBy)) {
            PageHelper.orderBy(orderBy);
        }
        return new PageInfo<>(notifyPartyActivityDao.selectList(condition));
    }

    @Override
    public void notifyMe(Integer activityId, String agency_code, Integer party_id, Integer accountId,short pathType) {

        String key = "notify_me_activity:{" + activityId + "}-{" + accountId + "}";
        RedisLock lock = new RedisLock(redisTemplate, key, 3000, 1000);
        try {
            if (lock.lock()) {
                logger.info("进入redis锁 key是{}", key);
                NotifyPartyActivity notifyPartyActivity = new NotifyPartyActivity();
                notifyPartyActivity.setActivityId(activityId);
                AuctionActivity byId = auctionActivityDao.getById(activityId);
                if (byId == null) {
                    throw new BusinessException(ApiCallResult.FAILURE.getCode(), "活动不存在");
                }
                String status = byId.getStatus().getKey();
                if (ActivityEnum.Status.FAILED.getKey().equals(status)) {
                    throw new BusinessException(ApiCallResult.FAILURE.getCode(), "活动已结束或者距离结束时间已不足一小时");
                }
                Date endAt = byId.getEndAt();
                int margin = DateUtil.getMarginMin(endAt, new Date());
                if (margin < 60) {
                    throw new BusinessException(ApiCallResult.FAILURE.getCode(), "活动已结束或者距离结束时间已不足一小时");
                }

                NotifyPartyActivityCondition condition = new NotifyPartyActivityCondition();
                condition.setPartyId(party_id);
                condition.setAccountId(accountId);
                condition.setActivityId(activityId);
                NotifyPartyActivity notifyPartyActivity1 = notifyPartyActivityDao.selectOneResult(condition);
                if (notifyPartyActivity1 != null) {
                    throw new BusinessException(ApiCallResult.FAILURE.getCode(), "请不要重复提醒");
                }
                notifyPartyActivity.setPartyId(party_id);
                notifyPartyActivity.setAccountId(accountId);
                notifyPartyActivity.setBeginNotified(byId.getBeginNotified());
                notifyPartyActivity.setEndNotified(byId.getEndNotified());
                notifyPartyActivity.setPathType(pathType);
                notifyPartyActivityDao.insert(notifyPartyActivity);

                //活动状态为可联拍的才可以联拍
                if (byId.getStatus().getKey().equals("ONLINE")) {
                    //如果二级机构未联拍，则联拍之
                    if (StringUtils.isNotBlank(agency_code)) {
                        insertAgencyPortalActivity(activityId, agency_code);
                    }
                }
                String mobile = accountService.selectByPrimaryKey(accountId).getMobile();
                smsHelperService.auctionSetRemindNotify(mobile);
                setNotifyMeRedisExpireTime(byId, mobile);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            logger.info("释放redis锁 key是{}", key);
        }
    }

    private void setNotifyMeRedisExpireTime(AuctionActivity activity, String mobile) {
        try {
            // 开始前1小时提醒
            Calendar cal = Calendar.getInstance();
            cal.setTime(activity.getBeginAt());
            cal.add(Calendar.HOUR_OF_DAY, -1);
            long timeout = (cal.getTime().getTime() - System.currentTimeMillis()) / 1000;
            if (timeout > 0) {
                redisCachemanager.set(RedisKeyConstant.AUCTION_BE_ABOUT_TO_BEGIN_ + activity.getId(), activity.getId(), timeout);
            }
            // 结束前1小时提醒
            cal.setTime(activity.getEndAt());
            cal.add(Calendar.HOUR_OF_DAY, -1);
            timeout = (cal.getTime().getTime() - System.currentTimeMillis()) / 1000;
            if (timeout > 0) {
                redisCachemanager.set(RedisKeyConstant.AUCTION_BE_ABOUT_TO_END_ + activity.getId(), activity.getId(), timeout);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertAgencyPortalActivity(Integer activityId, String agency_code) {
        TAgencyCondition agencyCondition = new TAgencyCondition();
        agencyCondition.setCode(agency_code);
        TAgency agency = agencyDao.selectOneResult(agencyCondition);
        if (agency != null) {
            //通过机构查询portal
            AgencyPortalCondition portal = new AgencyPortalCondition();
            portal.setAgencyId(agency.getId());
            AgencyPortal agencyPortal = agencyPortalDao.selectOneResult(portal);
            AgencyPortalActivityCondition condition = new AgencyPortalActivityCondition();
            condition.setAgencyId(agency.getId());
            condition.setAgencyPortalId(agencyPortal.getId());
            condition.setActivityId(activityId);
            List<AgencyPortalActivity> agencyPortalActivities = agencyPortalActivityDao.selectList(condition);
            //查询为空的时候则需要联拍
            if (agencyPortalActivities != null && agencyPortalActivities.isEmpty()) {
                //设置机构 - 拍卖活动 - 联拍表
                AgencyPortalActivity agencyPortalActivity = new AgencyPortalActivity();
                agencyPortalActivity.setViewCount(0);
                agencyPortalActivity.setAgencyId(agency.getId());
                agencyPortalActivity.setAgencyPortalId(agencyPortal.getId());
                agencyPortalActivity.setActivityId(activityId);
                //插入数据
                agencyPortalActivityDao.insert(agencyPortalActivity);
            }
        }
    }

    @Override
    public PageInfoResp<PartyAccount> getNotifiedPartyAccountsByPage(ActivityReq.ActivityId req) {
        PageInfoResp<PartyAccount> resp = new PageInfoResp<>();
        NotifyPartyActivityCondition condition = new NotifyPartyActivityCondition();
        condition.setActivityId(req.getActivityId());
        PageInfo pageInfo = getAllByActivityId(req.getPage(), req.getPerPage(), condition, "");
        List<NotifyPartyActivity> list = pageInfo.getList();
        List<PartyAccount> partyVos = new ArrayList<>();
        for (NotifyPartyActivity item : list) {
            PartyAccount partyVo;
            if (item.getPartyId() == null) {
                partyVo = new PartyAccount();
                partyVo.setMobile(accountDao.getMobile(item.getAccountId()));
                partyVo.setType(SystemConstant.ACCOUNT_COMMON_TYPE);
            } else {
                partyVo = accountService.getPartyAccountById(item.getPartyId());
            }
            partyVo.setRecordAt(item.getCreatedAt());
            partyVos.add(partyVo);
        }
        resp.setList(partyVos);
        resp.setHasNextPage(pageInfo.isHasNextPage());
        resp.setTotal(pageInfo.getTotal());
        return resp;
    }

    @Override
    public PageInfo myNotify(int page, int perPage, Integer partyId, Integer accountId,String categoryId,String name) {
        PageHelper.startPage(page, perPage);
        List<Map> maps = notifyPartyActivityDao.myNotify(partyId, accountId,categoryId,name);
        for(Map m :maps){
            if(m.get("categoryId")!=null&&"-1".equals(String.valueOf(m.get("categoryId")))){
                m.put("categoryName","租赁权拍卖");
            }

        }

        return new PageInfo<>(maps);
    }

    @Override
    @Transactional
    public int cancel(Integer activityId, Integer partyPrimaryId, Integer accountId) {
        int cancel = notifyPartyActivityDao.cancel(activityId, partyPrimaryId, accountId);
        if (cancel <= 0) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "取消提醒失败");
        }
        return cancel;
    }

    @Override
    public void auctionBeAboutToBeginNotify(Integer activityId) {
        AuctionActivity activity = auctionActivityDao.selectById(activityId);
        List<NotifyPartyActivity> list = notifyPartyActivityDao.getByActivityId(activityId);
        for (NotifyPartyActivity item : list) {
            String mobile = accountService.getNotifierMobile(item.getPartyId());
            smsHelperService.auctionBeAboutToBeginNotify(mobile, activity.getAssetName());
        }
    }

    @Override
    public void auctionBeAboutToEndNotify(Integer activityId) {
        AuctionActivity activity = auctionActivityDao.selectById(activityId);
        List<NotifyPartyActivity> list = notifyPartyActivityDao.getByActivityId(activityId);
        for (NotifyPartyActivity item : list) {
            String mobile = accountService.getNotifierMobile(item.getPartyId());
            smsHelperService.auctionBeAboutToEndNotify(mobile, activity.getAssetName());
        }
    }

    @Override
    public int partyIdBind(Integer partyId, Integer accountId) {
        if (partyId != null) {
            NotifyPartyActivityCondition condition = new NotifyPartyActivityCondition();
            condition.setAccountId(accountId);
            List<NotifyPartyActivity> notifyPartyActivities = notifyPartyActivityDao.selectList(condition);
            int count = 0;
            for (int i = 0, size = notifyPartyActivities.size(); i < size; i++) {
                NotifyPartyActivity notifyPartyActivity = notifyPartyActivities.get(i);
                if (notifyPartyActivity.getPartyId() == null) {
                    notifyPartyActivity.setPartyId(partyId);
                    count += notifyPartyActivityDao.updateById(notifyPartyActivity);
                }
            }
            return count;
        }
        return 0;
    }

    @Override
    public List<AuctionActivity> getBeginIn5MinuteList(Integer accountId, Integer partyId) {
        return notifyPartyActivityDao.getBeginIn5MinuteList(accountId, partyId);
    }

    @Override
    public List<AuctionActivity> getEndIn5MinuteList(Integer accountId, Integer partyId) {
        return notifyPartyActivityDao.getEndIn5MinuteList(accountId, partyId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int cancelNotifyMe(List<Integer> ids) {
        int m = 0;
        for (Integer id : ids) {
            int i = notifyPartyActivityDao.deleteNotify(id);
            if (i > 0) {
                logger.info("取消提醒删除id{}成功", id);
                m++;
            }
            logger.info("取消提醒删除id{}失败", id);
        }
        logger.info("取消提醒删除{}个", m);
        if (m != ids.size()) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "取消提醒失败");
        }
        return m;
    }
}