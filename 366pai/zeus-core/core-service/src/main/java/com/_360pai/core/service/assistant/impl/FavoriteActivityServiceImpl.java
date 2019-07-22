package com._360pai.core.service.assistant.impl;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.arch.core.redis.RedisLock;
import com._360pai.core.common.constants.ActivityEnum;
import com._360pai.core.common.constants.AssetEnum;
import com._360pai.core.common.constants.ServiceFollowEnum;
import com._360pai.core.common.constants.ShopEnum;
import com._360pai.core.condition.account.TPartyCondition;
import com._360pai.core.condition.asset.AssetLeaseDataCondition;
import com._360pai.core.condition.assistant.FavoriteActivityCondition;
import com._360pai.core.dao.account.TAccountDao;
import com._360pai.core.dao.account.TPartyDao;
import com._360pai.core.dao.asset.AssetLeaseDataDao;
import com._360pai.core.dao.assistant.DepositDao;
import com._360pai.core.dao.assistant.FavoriteActivityDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.account.vo.PartyAccount;
import com._360pai.core.facade.activity.req.ActivityReq;
import com._360pai.core.facade.activity.req.FavoriteActivityReq;
import com._360pai.core.facade.applet.req.AuctionReq;
import com._360pai.core.model.account.TParty;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.asset.AssetLeaseData;
import com._360pai.core.model.assistant.FavoriteActivity;
import com._360pai.core.service.account.AccountService;
import com._360pai.core.service.assistant.FavoriteActivityService;
import com._360pai.core.service.assistant.NotifyPartyActivityService;
import com._360pai.core.service.assistant.TServiceFollowService;
import com._360pai.core.service.enrolling.EnrollingActivityService;
import com._360pai.core.utils.RespConvertUtil;
import com._360pai.core.vo.activity.FavoriteActivityVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.util.*;

/**
 * 描述:
 *
 * @author : whisky_vip
 * @date : 2018/8/16 13:57
 */
@Service
public class FavoriteActivityServiceImpl implements FavoriteActivityService {

    private static Logger logger = LoggerFactory.getLogger(FavoriteActivityServiceImpl.class);

    @Resource
    private FavoriteActivityDao favoriteActivityDao;
    @Resource
    private TPartyDao partyDao;
    @Resource
    private DepositDao depositDao;
    @Resource
    private NotifyPartyActivityService notifyPartyActivityService;
    @Resource
    private RedisTemplate<Object, Object> redisTemplate;
    @Resource
    private AccountService accountService;
    @Resource
    private TServiceFollowService tServiceFollowService;
    @Resource
    private EnrollingActivityService enrollingActivityService;
    @Autowired
    private TAccountDao accountDao;
    @Autowired
    private AssetLeaseDataDao assetLeaseDataDao;

    @Override
    public PageInfo getAllByActivityId(int page, int perPage, FavoriteActivityCondition condition, String orderBy) {
        PageHelper.startPage(page, perPage);
        if (StringUtils.isNotBlank(orderBy)) {
            PageHelper.orderBy(orderBy);
        }
        return new PageInfo<>(favoriteActivityDao.selectList(condition));
    }

    @Override
    public void activityFavor(Integer activityId, Integer partyId, String agencyCode, String type,Integer resourceId, Integer accountId) {
//        if (partyId == null) {
//            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "请前往首页登录");
//        }
        String key = "favor_activity:{" + activityId + "}-{" + accountId + "}";
        RedisLock lock = new RedisLock(redisTemplate, key, 3000, 1000);
        try {
            if (lock.lock()) {
                logger.info("进入redis锁，key为{}", key);

                FavoriteActivityCondition condition1 = new FavoriteActivityCondition();
                condition1.setPartyId(partyId);
                condition1.setActivityId(activityId);
                condition1.setResourceId(resourceId);
                condition1.setType(type);

                condition1.setAccountId(accountId);
                FavoriteActivity favoriteActivity1 = favoriteActivityDao.selectOneResult(condition1);
                if (favoriteActivity1 != null) {
                    throw new BusinessException(ApiCallResult.FAILURE.getCode(), "已关注");
                }

//                TPartyCondition condition = new TPartyCondition();
//                condition.setId(partyId);
//                TParty party = partyDao.selectOneResult(condition);
//                if (party == null) {
//                    throw new BusinessException(ApiCallResult.FAILURE.getCode(), "用户信息错误");
//                }
                //加入关注表
                FavoriteActivity favoriteActivity = new FavoriteActivity();
                favoriteActivity.setActivityId(activityId);
                favoriteActivity.setPartyId(partyId);
                favoriteActivity.setResourceId(resourceId);
                favoriteActivity.setType(type);
                favoriteActivity.setAccountId(accountId);
                favoriteActivityDao.insert(favoriteActivity);

                //如果二级机构未联拍，则联拍之
                if (StringUtils.isNotBlank(agencyCode)) {
                    notifyPartyActivityService.insertAgencyPortalActivity(activityId, agencyCode);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            lock.unlock();
            logger.info("出现异常，释放redis锁，key为{}", key);
        } finally {
            //为了让分布式锁的算法更稳键些，持有锁的客户端在解锁之前应该再检查一次自己的锁是否已经超时，再去做DEL操作，因为可能客户端因为某个耗时的操作而挂起，
            //操作完的时候锁因为超时已经被别人获得，这时就不必解锁了。 ————这里没有做
            lock.unlock();
            logger.info("释放redis锁，key为{}", key);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int cancelFavor(List<Integer> ids, Integer partyId) {
        int i = 0;
        for (Integer id : ids) {
            logger.info("活动取消接口数据id为：{}，用户ID为：{}", id, partyId);
            int i1 = favoriteActivityDao.cancelFavor(id, partyId);
            if (i1 > 0) {
                i++;
            }
        }
        if (ids.size() != i) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "取消失败");
        }
        return i;
    }

    @Override
    public PageInfo myFavor(FavoriteActivityReq.Query req) {
        PageHelper.startPage(req.getPage(), req.getPerPage());

        Map<String, Object> params = new HashMap<>(16);
        if (StringUtils.isNotEmpty(req.getActivityMode())) {
            params.put("activityMode", req.getActivityMode());
        }
        if (StringUtils.isNotEmpty(req.getActivityStatus())) {
            params.put("activityStatus", req.getActivityStatus());
        }
        if (req.getCategoryId() != null) {
            params.put("categoryId", req.getCategoryId());
        }
        if (req.getPartyId() != null) {
            params.put("partyId", req.getPartyId());
        }
        if (StringUtils.isNotEmpty(req.getQ())) {
            params.put("activityName", req.getQ());
        }
        if (StringUtils.isNotEmpty(req.getName())) {
            params.put("activityName", req.getName());
        }
        if (StringUtils.isNotEmpty(req.getAgencyCode())) {
            params.put("agencyCode", req.getAgencyCode());
        }
        params.put("accountId", req.getAccountId());

        List<FavoriteActivityVo> list = favoriteActivityDao.myFavor(params);
        for (FavoriteActivityVo vo : list) {
            Integer count = depositDao.getDepositCount(vo.getActivityId());
            vo.setDepositCount(count);
            if("-1".equals(vo.getCategoryId())){
                vo.setCategoryName("租赁权拍卖");
                vo.setEndAt(vo.getApplyEndTime());
            }
            ShopEnum.NewOnlineStatus onlineStatus = RespConvertUtil.getOnlineStatus(vo.getStatus(), vo.getBeginAt());

            if (onlineStatus != null) {
                vo.setActivityStatus(onlineStatus.getKey());
            }
            // 如果为租赁权的状态就新增报名中以及资质审核中
            if("-1".equals(vo.getCategoryId())) {
                changeActivityStatus(vo);
            }
        }


        return new PageInfo<>(list);
    }

    // 设置不同状态
    private void changeActivityStatus(FavoriteActivityVo favoriteActivityVo) {

        AssetLeaseDataCondition assetLeaseDataCondition = new AssetLeaseDataCondition();
        assetLeaseDataCondition.setDeleteFlag(false);
        assetLeaseDataCondition.setAssetId(favoriteActivityVo.getAssetId());
        AssetLeaseData assetLeaseData = assetLeaseDataDao.selectOneResult(assetLeaseDataCondition);

        Date nowDate = new Date();
        if(ActivityEnum.Status.ONLINE.getKey().equals(favoriteActivityVo.getStatus())&& DateUtil.getMarginMilliseconds(assetLeaseData.getPreviewAt(), nowDate) < 0 &&
                DateUtil.getMarginMilliseconds(assetLeaseData.getApplyEndTime(), nowDate) > 0) {
            favoriteActivityVo.setStatus(AssetEnum.LeaseStatus.REGISTRATION_PERIOD.getKey());
            favoriteActivityVo.setStatusStr(AssetEnum.LeaseStatus.REGISTRATION_PERIOD.getValue());

        }else if(ActivityEnum.Status.ONLINE.getKey().equals(favoriteActivityVo.getStatus())&&DateUtil.getMarginMilliseconds(assetLeaseData.getApplyEndTime(), nowDate) < 0 &&
                DateUtil.getMarginMilliseconds(assetLeaseData.getApprovalEndTime(), nowDate) > 0) {
            favoriteActivityVo.setStatus(AssetEnum.LeaseStatus.QUALIFICATION_REVIEW.getKey());
            favoriteActivityVo.setStatusStr(AssetEnum.LeaseStatus.QUALIFICATION_REVIEW.getValue());
        }
    }

    @Override
    public int unFavor(Integer partyId, Integer activityId, String type ,Integer resourceId, Integer accountId) {
        return favoriteActivityDao.unFavor(partyId,activityId,type,resourceId, accountId);
    }

    @Override
    public PageInfoResp<PartyAccount> getFavoredPartyAccountsByPage(ActivityReq.ActivityId req) {
        PageInfoResp<PartyAccount> resp = new PageInfoResp<>();
        FavoriteActivityCondition condition = new FavoriteActivityCondition();
        condition.setActivityId(req.getActivityId());
        PageInfo pageInfo = getAllByActivityId(req.getPage(), req.getPerPage(), condition, "");
        List<PartyAccount> partyVos = new ArrayList<>();
        List<FavoriteActivity> list = pageInfo.getList();
        for (FavoriteActivity item : list) {
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
    public Map<String, Object> favoriteCount(Integer accountId, Integer partyId) {
        Map<String, Object> map = new HashMap<>(16);
        int auctionCount = 0;
        Integer foucseCount = 0;
//        if (partyId != null && partyId != 0) {
            Map<String, Object> params = new HashMap<>(16);
            if (partyId != null)
                params.put("partyId", partyId);
            params.put("accountId", accountId);
            List<FavoriteActivityVo> list = favoriteActivityDao.myFavor(params);
            if (list == null) {
                auctionCount = 0;
            } else {
                auctionCount = list.isEmpty() ? 0 : list.size();
            }

            foucseCount = enrollingActivityService.getFoucseCount(partyId, accountId);
//        }
        Map<String, Integer> mapCount = tServiceFollowService.getFollowNumByAccountId(accountId, partyId);
        //拍卖活动
        map.put("auctionCount", auctionCount);
        //预招商活动
        map.put("foucseCount", foucseCount);
        //处置服务
        map.put(ServiceFollowEnum.RelativeType.DIPOSAL.getKey(), mapCount.get(ServiceFollowEnum.RelativeType.DIPOSAL.getKey()));

        return map;
    }

    @Override
    public List<FavoriteActivity> getConcernedFlag(String shopId, String auctionId) {

        FavoriteActivityCondition favoriteActivityCondition = new FavoriteActivityCondition();
        favoriteActivityCondition.setResourceId(Integer.parseInt(shopId));
        favoriteActivityCondition.setActivityId(Integer.parseInt(auctionId));

        return  favoriteActivityDao.selectList(favoriteActivityCondition);
    }

    @Override
    public PageInfo getMyShopFavor(AuctionReq.AuctionInfoReq req) {
        PageHelper.startPage(req.getPage(), req.getPerPage());
        List<FavoriteActivityVo> list = favoriteActivityDao.getMyShopFavor(req.getPartyId(), req.getAccountId());
        for (FavoriteActivityVo vo : list) {
            vo.setShopName(StringEscapeUtils.unescapeJava(vo.getShopName()));
        }
        return new PageInfo<>(list);
    }

    @Override
    public int partyIdBind(Integer accountId, Integer partyId) {
        if (partyId != null) {
            FavoriteActivityCondition condition = new FavoriteActivityCondition();
            condition.setAccountId(accountId);
            List<FavoriteActivity> favoriteActivities = favoriteActivityDao.selectList(condition);
            int count = 0;
            for (int i = 0, size = favoriteActivities.size(); i < size ; i++) {
                FavoriteActivity favoriteActivity = favoriteActivities.get(i);
                if (favoriteActivity.getPartyId() == null) {
                    favoriteActivity.setPartyId(partyId);
                    count += favoriteActivityDao.updateById(favoriteActivity);
                }
            }
            return count;
        }
        return 0;
    }

    @Override
    public List<AuctionActivity> getBeginIn5MinuteList(Integer accountId, Integer partyId) {
        return favoriteActivityDao.getBeginIn5MinuteList(accountId, partyId);
    }

    @Override
    public List<AuctionActivity> getEndIn5MinuteList(Integer accountId, Integer partyId) {
        return favoriteActivityDao.getEndIn5MinuteList(accountId, partyId);
    }
}