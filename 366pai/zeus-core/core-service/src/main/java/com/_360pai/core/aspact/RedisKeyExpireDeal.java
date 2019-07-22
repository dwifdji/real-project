package com._360pai.core.aspact;

import com._360pai.arch.common.constant.RedisKeyConstant;
import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.core.redis.AbstractRedisLock;
import com._360pai.core.common.constants.DisposalEnum;
import com._360pai.core.dao.disposal.TDisposalBiddingDao;
import com._360pai.core.dao.disposal.TDisposalRequirementDao;
import com._360pai.core.facade.activity.AuctionFacade;
import com._360pai.core.facade.disposal.DisposeLevelFacade;
import com._360pai.core.facade.enrolling.EnrollingWebFacade;
import com._360pai.core.model.disposal.TDisposeLevel;
import com._360pai.core.model.disposal.TDisposeRefuseRecord;
import com._360pai.core.model.disposal.TDisposeSurvey;
import com._360pai.core.service.assistant.NotifyPartyActivityService;
import com._360pai.core.service.disposal.DisposalRequirementService;
import com._360pai.core.service.disposal.DisposeLevelService;
import com._360pai.core.service.disposal.DisposeRefuseRecordService;
import com._360pai.core.service.disposal.DisposeSurveyService;
import com._360pai.core.utils.ServiceMessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author RuQ
 * @Title: RedisKeyExpireDel
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/11 10:14
 */
@Service
public class RedisKeyExpireDeal implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisKeyExpireDeal.class);

    @Resource
    private RedisTemplate redisTemplate;
    @Autowired
    private AuctionFacade auctionFacade;
    @Autowired
    private EnrollingWebFacade enrollingWebFacade;
    @Autowired
    private NotifyPartyActivityService notifyPartyActivityService;
    @Autowired
    private DisposeSurveyService disposeSurveyService;
    @Autowired
    private ServiceMessageUtils serviceMessageUtils;
    @Autowired
    private TDisposalRequirementDao disposalRequirementDao;
    @Autowired
    private TDisposalBiddingDao biddingDao;
    @Autowired
    private DisposalRequirementService disposalRequirementService;
    @Autowired
    private DisposeLevelService disposeLevelService;
    @Autowired
    private DisposeRefuseRecordService disposeRefuseRecordService;

    @Override
    public void onMessage(Message message, byte[] bytes) {
        String redisKey = new String(message.getBody());
        LOGGER.info("channel:{},message:{}", new String(message.getChannel()), redisKey);
        doBusiness(redisKey);
    }

    private void doBusiness(String redisKey) {
        if (redisKey.startsWith(SystemConstant.AUCTION_EXPIRE_PRE_KEY)) {
            Integer activityId = Integer.parseInt(redisKey.split("_")[1]);
            String limitKey = "expire_massage_" + redisKey;
            new AbstractRedisLock(limitKey,10,redisTemplate) {
                @Override
                public void dealBusiness() {
                    auctionFacade.insertAuctionStep(activityId,null,null,"redis活动结束处理",null,null,null,null);
                    auctionFacade.activityEndDeal(activityId);
                }
            }.redisLock();
        }

        if(redisKey.startsWith(SystemConstant.AUCTION_SIGN_EXPIRE_PRE_KEY)){
            Integer activityId = Integer.parseInt(redisKey.split("_")[1]);
            String limitKey = "expire_massage_" + redisKey;
            new AbstractRedisLock(limitKey,10,redisTemplate) {
                @Override
                public void dealBusiness() {
                    auctionFacade.insertAuctionStep(activityId,null,null,"redis签订成交协议过期处理",null,null,null,null);
                    auctionFacade.signTimeourDeal(activityId);
                }
            }.redisLock();
        }

        if (redisKey.startsWith(SystemConstant.AUCTION_PAY_TIMEOUT_PRE_KEY)) {
            Integer activityId = Integer.parseInt(redisKey.split("_")[1]);
            String limitKey = "expire_massage_" + redisKey;
            new AbstractRedisLock(limitKey,10,redisTemplate) {
                @Override
                public void dealBusiness() {
                    auctionFacade.insertAuctionStep(activityId,null,null,"redis支付时间过期处理",null,null,null,null);
                    auctionFacade.payTimeoutDeal(activityId);
                }
            }.redisLock();
        }

        if(redisKey.startsWith(SystemConstant.AUCTION_DUTCH_PRICE_PRE_KEY)){
            String limitKey = "expire_massage_" + redisKey;
            Integer activityId = Integer.parseInt(redisKey.split("_")[1]);
            LOGGER.info("开始执行降价拍自动降价处理,活动id:{}" + activityId );
            new AbstractRedisLock(limitKey,10,redisTemplate) {
                @Override
                public void dealBusiness() {
                    auctionFacade.dutchPriceDeal(activityId);
                }
            }.redisLock();
        }

        if(redisKey.startsWith(SystemConstant.ENROLLING_SIGN_UP_KEY)) {
            Integer activityId = Integer.parseInt(redisKey.split("_")[1]);
            String limitKey = "expire_massage_" + redisKey;
            LOGGER.info("开始回调执行失效修改预招商,预招商的id是：" + activityId );
            new AbstractRedisLock(limitKey,10,redisTemplate) {
                @Override
                public void dealBusiness() {
                    enrollingWebFacade.updateActivityStatus(activityId);
                }
            }.redisLock();
        }

        if(redisKey.startsWith(SystemConstant.ENROLLING_REMIND_KEY)) {
            Integer noticeModelId = Integer.parseInt(redisKey.split("_")[1]);
            String limitKey = "expire_massage_" + redisKey;
            LOGGER.info("开始回调执行预招商提醒,预招商的id是：" + noticeModelId );
            new AbstractRedisLock(limitKey,10,redisTemplate) {
                @Override
                public void dealBusiness() {
                    //短信提醒活动还剩一个小时就要结束
                    enrollingWebFacade.sentNoticeMessage(noticeModelId);
                }
            }.redisLock();
        }

        if (redisKey.startsWith(RedisKeyConstant.AUCTION_BE_ABOUT_TO_BEGIN_)) {
            String[] arr = redisKey.split("_");
            String activityId = arr[arr.length - 1];
            String limitKey = "expire_massage_" + redisKey;
            LOGGER.info("开始回调执行拍卖即将开始提醒,activityId={}", activityId);
            new AbstractRedisLock(limitKey,10,redisTemplate) {
                @Override
                public void dealBusiness() {
                    notifyPartyActivityService.auctionBeAboutToBeginNotify(Integer.parseInt(activityId));
                }
            }.redisLock();
        }

        if (redisKey.startsWith(RedisKeyConstant.AUCTION_BE_ABOUT_TO_END_)) {
            String[] arr = redisKey.split("_");
            String activityId = arr[arr.length - 1];
            String limitKey = "expire_massage_" + redisKey;
            LOGGER.info("开始回调执行拍卖即将结束提醒,activityId={}", activityId);
            new AbstractRedisLock(limitKey,10,redisTemplate) {
                @Override
                public void dealBusiness() {
                    notifyPartyActivityService.auctionBeAboutToEndNotify(Integer.parseInt(activityId));
                }
            }.redisLock();
        }

        if(redisKey.startsWith(SystemConstant.DISPOSAL_DEADLINE_SMS)) {
            Integer noticeModelId = Integer.parseInt(redisKey.split("_")[2]);
            String limitKey = "expire_massage_" + redisKey;
            LOGGER.info("处置需求到期提醒，需求的id是: " + noticeModelId );
            new AbstractRedisLock(limitKey,10,redisTemplate) {
                @Override
                public void dealBusiness() {
                    try {
                        disposalRequirementService.updateRequirementByDeadline(noticeModelId);
                    } catch (Exception e) {
                        LOGGER.error("处置需求达到截至日期更新流标状态异常，disposalId:{}, 异常信息:{}", noticeModelId, e);
                    }

                    try {
                        disposalRequirementService.sendMessageByDeadline(noticeModelId);
                    } catch (Exception e) {
                        LOGGER.error("处置需求达到截至日期发送短信异常，disposalId:{}, 异常信息:{}", noticeModelId, e);
                    }
                }
            }.redisLock();
        }

        if (redisKey.startsWith(SystemConstant.PROVIDER_SURVEY_KEY)) {
//            Integer noticeModelId = Integer.parseInt(redisKey.split("_")[3]);
            String noticeModelId = redisKey.split("_")[3];
            String limitKey = "expire_massage_" + redisKey;
            LOGGER.info("尽调接单到期处理，尽调单的no是: " + noticeModelId );
            new AbstractRedisLock(limitKey,10,redisTemplate) {
                @Override
                public void dealBusiness() {
                    try {
                        TDisposeSurvey survey = disposeSurveyService.getDisposeSurveyBySurveyNo(noticeModelId);
                        // 达到deadline仍为未接受则置为失效、生成 t_dispose_refuse_record 记录、将服务商违约次数+1
                        if (survey.getSurveyStatus().equals(DisposalEnum.SurveyStatus.PENDING_ACCESS.getKey())) {
                            survey.setSurveyStatus(DisposalEnum.SurveyStatus.EXPIRED.getKey());
                            survey.setUpdateTime(new Date());
                            disposeSurveyService.updateSurveyById(survey);

                            String[] split = survey.getCityId().split(",");
                            TDisposeLevel level = disposeLevelService.getByProviderIdAndCityId(survey.getProviderId(), Integer.valueOf(split[0]));

                            if (level != null) {
                                // 生成 t_dispose_refuse_record 记录
                                createRefuseRecord(survey, level);
                                // 将服务商违约次数+1
                                level.setSurveyRefuseNum(level.getSurveyRefuseNum()+1);
                                disposeLevelService.updateById(level);
                            }
                        }
                    } catch (Exception e) {
                        LOGGER.error("尽调接单到期处理异常，异常信息为：", e);
                    }
                }
            }.redisLock();
        }
    }

    private void createRefuseRecord(TDisposeSurvey survey, TDisposeLevel level) {
        TDisposeRefuseRecord record = new TDisposeRefuseRecord();
        record.setLevelId(level.getId());
        record.setProviderId(record.getProviderId());
        record.setSurveyId(survey.getId());
        record.setRefuseTime(new Date());
        record.setAssignTime(survey.getAssignTime());
        disposeRefuseRecordService.addRefuseRecord(record);
    }
}
