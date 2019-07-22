package com._360pai.core.aspact;

import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.core.common.constants.*;

import com._360pai.core.dao.comprador.TCompradorRequirementDao;
import com._360pai.core.dao.disposal.TDisposalRequirementDao;
import com._360pai.core.dao.withfudig.TWithfudigRequirementDao;
import com._360pai.core.facade.activity.AuctionFacade;
import com._360pai.core.facade.enrolling.EnrollingWebFacade;
import com._360pai.core.model.account.TCompanyApplyRecord;
import com._360pai.core.model.account.TUserApplyRecord;
import com._360pai.core.model.comprador.TCompradorRequirement;
import com._360pai.core.model.disposal.TDisposalRequirement;
import com._360pai.core.model.disposal.TDisposeLevel;
import com._360pai.core.model.disposal.TDisposeRefuseRecord;
import com._360pai.core.model.disposal.TDisposeSurvey;
import com._360pai.core.model.enrolling.EnrollingActivity;
import com._360pai.core.service.account.AccountService;
import com._360pai.core.service.account.AcctService;
import com._360pai.core.service.account.CompanyVerifyApplicationService;
import com._360pai.core.service.account.UserVerifyApplicationService;
import com._360pai.core.service.applet.TAppletMessageService;
import com._360pai.core.service.assistant.FavoriteActivityService;
import com._360pai.core.service.assistant.NotifyPartyActivityService;
import com._360pai.core.service.assistant.SmsHelperService;
import com._360pai.core.model.withfudig.TWithfudigRequirement;
import com._360pai.core.service.disposal.DisposalRequirementService;
import com._360pai.core.service.disposal.DisposeLevelService;
import com._360pai.core.service.disposal.DisposeRefuseRecordService;
import com._360pai.core.service.disposal.DisposeSurveyService;
import com._360pai.core.service.enrolling.EnrollingActivityService;
import com._360pai.core.service.enrolling.FavoriteEnrollingActivityService;
import com._360pai.core.service.enrolling.NotifyPartyEnrollingActivityService;
import com._360pai.core.utils.ServiceMessageUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author xdrodger
 * @Title: DelayMqReceiver
 * @ProjectName zeus
 * @Description: 延迟队列出队
 * @date 2018/11/5 16:48
 */
@Component
@Slf4j
public class DelayMqReceiver {
    @Autowired
    private AuctionFacade auctionFacade;
    @Autowired
    private EnrollingWebFacade enrollingWebFacade;
    @Autowired
    private DisposalRequirementService disposalRequirementService;
    @Autowired
    private DisposeLevelService disposeLevelService;
    @Autowired
    private DisposeRefuseRecordService disposeRefuseRecordService;
    @Autowired
    private DisposeSurveyService disposeSurveyService;

    @Autowired
    private EnrollingActivityService enrollingActivityService;
    @Autowired
    private ServiceMessageUtils serviceMessageUtils;
    @Autowired
    private ExceptionService exceptionService;
    @Autowired
    private SmsHelperService smsHelperService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TAppletMessageService appletMessageService;
    @Resource
    private UserVerifyApplicationService userVerifyApplicationService;
    @Resource
    private CompanyVerifyApplicationService companyVerifyApplicationService;
    @Autowired
    private TDisposalRequirementDao disposalRequirementDao;
    @Autowired
    private TWithfudigRequirementDao withfudigRequirementDao;
    @Autowired
    private TCompradorRequirementDao compradorRequirementDao;
    @Autowired
    private AcctService acctService;
    @Autowired
    private FavoriteEnrollingActivityService favoriteEnrollingActivityService;
    @Autowired
    private NotifyPartyEnrollingActivityService notifyPartyEnrollingActivityService;
    @Autowired
    private FavoriteActivityService favoriteActivityService;
    @Autowired
    private NotifyPartyActivityService notifyPartyActivityService;

    /**
     * 测试延迟队列出队
     */
    @RabbitListener(queues = SystemConstant.RABBITMQ_TEST_DELAY_QUEUE)
    public void processTestDequeue(String message) {
        log.info("delay_queue={}, message={}, dequeue",SystemConstant.RABBITMQ_TEST_DELAY_QUEUE, message);
        try {
            JSONObject data = JSON.parseObject(message);
            long dequeueTime = System.currentTimeMillis();
            long delayTime = (dequeueTime - data.getLongValue("enqueueTime"));
            log.info("delay_queue={}, message={}, delay time={}",SystemConstant.RABBITMQ_TEST_DELAY_QUEUE, message, delayTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 拍卖活动结束队列出队
     */
    @RabbitListener(queues = SystemConstant.RABBITMQ_AUCTION_ACTIVITY_END_QUEUE)
    public void processAuctionActivityEndDelayDequeue(String message) {
        log.info("delay_queue={}, message={}, dequeue",SystemConstant.RABBITMQ_AUCTION_ACTIVITY_END_QUEUE, message);

    }

    /**
     * 拍卖活动降价队列出队
     */
    @RabbitListener(queues = SystemConstant.RABBITMQ_AUCTION_ACTIVITY_DUTCH_PRICE_QUEUE)
    public void processAuctionActivityDutchPriceDelayDequeue(String message) {
        log.info("delay_queue={}, message={}, dequeue",SystemConstant.RABBITMQ_AUCTION_ACTIVITY_DUTCH_PRICE_QUEUE, message);

    }

    /**
     * 拍卖订单支付超时队列出队
     */
    @RabbitListener(queues = SystemConstant.RABBITMQ_AUCTION_ORDER_PAY_TIMEOUT_QUEUE)
    public void processAuctionOrderPayTimeoutDelayDequeue(String message) {
        log.info("delay_queue={}, message={}, dequeue",SystemConstant.RABBITMQ_AUCTION_ORDER_PAY_TIMEOUT_QUEUE, message);
        try {
            Integer activityId = Integer.parseInt(message);
            if (activityId.intValue() == 815) {
                return;
            }
            auctionFacade.insertAuctionStep(activityId,null,null,"redis支付时间过期处理",null,null,null,null);
            auctionFacade.payTimeoutDeal(activityId);
        } catch (Exception e) {
            e.printStackTrace();
            exceptionService.processDequeueException(SystemConstant.RABBITMQ_AUCTION_ORDER_PAY_TIMEOUT_QUEUE, message, e.getMessage());
        }
    }

    /**
     * 拍卖活动签署成交协议超时队列出队
     */
    @RabbitListener(queues = SystemConstant.RABBITMQ_AUCTION_ACTIVITY_SIGN_TIMEOUT_KEY)
    public void processAuctionActivitySignTimeoutDelayDequeue(String message) {
        log.info("delay_queue={}, message={}, dequeue",SystemConstant.RABBITMQ_AUCTION_ACTIVITY_SIGN_TIMEOUT_KEY, message);
        try {
            Integer activityId = Integer.parseInt(message);
            auctionFacade.insertAuctionStep(activityId,null,null,"redis签订成交协议过期处理",null,null,null,null);
            auctionFacade.signTimeourDeal(activityId);
        } catch (Exception e) {
            e.printStackTrace();
            exceptionService.processDequeueException(SystemConstant.RABBITMQ_AUCTION_ACTIVITY_SIGN_TIMEOUT_KEY, message, e.getMessage());
        }
    }

    /**
     * 预招商活动报名结束队列出队
     */
    @RabbitListener(queues = SystemConstant.RABBITMQ_ENROLLING_ACTIVITY_SIGN_UP_END_QUEUE)
    public void processEnrollingActivitySignUpEndDelayDequeue(String message) {

        log.info("delay_queue={}, message={}, dequeue",SystemConstant.RABBITMQ_ENROLLING_ACTIVITY_SIGN_UP_END_QUEUE, message);
        try {
            Integer activityId = Integer.parseInt(message);
            enrollingWebFacade.updateActivityStatus(activityId);
        } catch (Exception e) {
            e.printStackTrace();
            exceptionService.processDequeueException(SystemConstant.RABBITMQ_ENROLLING_ACTIVITY_SIGN_UP_END_QUEUE, message, e.getMessage());
        }
    }

    /**
     * 预招商支付过期时间
     */
    @RabbitListener(queues = SystemConstant.ENROLLING_END_PAY_TIME_QUEUE)
    public void processEnrollingActivityPayTimeDequeue(String message) {
        log.info("delay_queue={}, message={}, dequeue",SystemConstant.ENROLLING_END_PAY_TIME_QUEUE, message);
        try {
            EnrollingActivity activity = enrollingActivityService.getEnrollingActivityById(message);
            if(activity==null||!EnrollingEnum.STATUS.WAIT_PAY.getType().equals(activity.getStatus())){
                return;
            }
            activity.setStatus(EnrollingEnum.STATUS.END_PAY.getType());
            enrollingActivityService.updateEnrollingActivityById(activity);
            log.info("修改支付过期成功，id:{}",message);
        } catch (Exception e) {
            e.printStackTrace();
            exceptionService.processDequeueException(SystemConstant.ENROLLING_END_PAY_TIME_QUEUE, message, e.getMessage());
        }
     }

    /**
     * 处置服务商提醒到期时间队列出队
     */
    @RabbitListener(queues = SystemConstant.RABBITMQ_DISPOSAL_DEADLINE_QUEUE)
    public void processDisposalDeadlineDelayDequeue(String message) {
        log.info("delay_queue={}, message={}, dequeue",SystemConstant.RABBITMQ_DISPOSAL_DEADLINE_QUEUE, message);
        try {
            Integer noticeModelId = Integer.parseInt(message);
            disposalRequirementService.updateRequirementByDeadline(noticeModelId);
        } catch (Exception e) {
            log.error("处置需求达到截至日期更新流标状态异常，disposalId:{}, 异常信息:{}", message, e);
            exceptionService.processDequeueException(SystemConstant.RABBITMQ_DISPOSAL_DEADLINE_QUEUE, message, e.getMessage());
        }
        try {
            Integer noticeModelId = Integer.parseInt(message);
            disposalRequirementService.sendMessageByDeadline(noticeModelId);
        } catch (Exception e) {
            log.error("处置需求达到截至日期发送短信异常，disposalId:{}, 异常信息:{}", message, e);
            exceptionService.processDequeueException(SystemConstant.RABBITMQ_DISPOSAL_DEADLINE_QUEUE, message, e.getMessage());
        }
    }

    /**
     * 一级合伙人接单到期队列出队
     */
    @RabbitListener(queues = SystemConstant.RABBITMQ_PROVIDER_SURVEY_QUEUE)
    public void processProviderSurveyDeadlineDelayDequeue(String message) {
        log.info("delay_queue={}, message={}, dequeue",SystemConstant.RABBITMQ_PROVIDER_SURVEY_QUEUE, message);
        try {
            TDisposeSurvey survey = disposeSurveyService.getDisposeSurveyBySurveyNo(message);
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
                    if (level.getSurveyRefuseNum() % 3 == 0) {
                        serviceMessageUtils.breakContract3ToOperator(level.getId());
                    }
                }
            }
        } catch (Exception e) {
            log.error("尽调接单到期处理异常，异常信息为：", e);
            exceptionService.processDequeueException(SystemConstant.RABBITMQ_PROVIDER_SURVEY_QUEUE, message, e.getMessage());
        }
    }

    /**
     *  处置支付到期队列出队
     */
    @RabbitListener(queues = SystemConstant.RABBITMQ_DISPOSAL_PAY_EXPIRED)
    public void disposalPayExpiredDequeue(String message) {
        log.info("delay_queue={}, message={}, dequeue",SystemConstant.RABBITMQ_DISPOSAL_PAY_EXPIRED, message);
        try {
            Integer noticeModelId = Integer.parseInt(message);
            TDisposalRequirement tDisposalRequirement = disposalRequirementDao.selectById(noticeModelId);
            if (tDisposalRequirement != null && tDisposalRequirement.getDisposalStatus().equals(DisposalEnum.RequirementStatus.PASS_FOR_PAY.getValue())) {
                tDisposalRequirement.setDisposalStatus(DisposalEnum.RequirementStatus.PAY_EXPIRED.getValue());
                tDisposalRequirement.setUpdateTime(new Date());
                disposalRequirementDao.updateById(tDisposalRequirement);
            }
        } catch (Exception e) {
            log.error("处置服务支付失效到期处理异常，异常信息为：", e);
            exceptionService.processDequeueException(SystemConstant.RABBITMQ_DISPOSAL_PAY_EXPIRED, message, e.getMessage());
        }
    }

    /**
     *  配资支付到期队列出队
     */
    @RabbitListener(queues = SystemConstant.RABBITMQ_WITHFUDIG_PAY_EXPIRED)
    public void withfudigPayExpiredDequeue(String message) {
        log.info("delay_queue={}, message={}, dequeue",SystemConstant.RABBITMQ_WITHFUDIG_PAY_EXPIRED, message);
        try {
            Integer noticeModelId = Integer.parseInt(message);
            TWithfudigRequirement tWithfudigRequirement = withfudigRequirementDao.selectById(noticeModelId);
            if (tWithfudigRequirement != null && tWithfudigRequirement.getRequirementStatus().equals(WithfudigEnum.RequirementStatus.PASS_FOR_PAY.getValue().toString())) {
                tWithfudigRequirement.setRequirementStatus(WithfudigEnum.RequirementStatus.PAY_EXPIRED.getValue().toString());
                tWithfudigRequirement.setUpdateTime(new Date());
                withfudigRequirementDao.updateById(tWithfudigRequirement);
            }
        } catch (Exception e) {
            log.error("配资服务支付失效到期处理异常，异常信息为：", e);
            exceptionService.processDequeueException(SystemConstant.RABBITMQ_WITHFUDIG_PAY_EXPIRED, message, e.getMessage());
        }
    }

    /**
     *  置业支付到期队列出队
     */
    @RabbitListener(queues = SystemConstant.RABBITMQ_COMPRADOR_PAY_EXPIRED)
    public void compradorPayExpiredDequeue(String message) {
        log.info("delay_queue={}, message={}, dequeue",SystemConstant.RABBITMQ_COMPRADOR_PAY_EXPIRED, message);
        try {
            Integer noticeModelId = Integer.parseInt(message);
            TCompradorRequirement tCompradorRequirement = compradorRequirementDao.selectById(noticeModelId);
            if (tCompradorRequirement != null && tCompradorRequirement.getRequirementStatus().equals(CompradorEnum.RequirementStatus.PASS_FOR_PAY.getValue().toString())) {
                tCompradorRequirement.setRequirementStatus(CompradorEnum.RequirementStatus.PAY_EXPIRED.getValue().toString());
                tCompradorRequirement.setUpdateTime(new Date());
                compradorRequirementDao.updateById(tCompradorRequirement);
            }
        } catch (Exception e) {
            log.error("置业服务支付失效到期处理异常，异常信息为：", e);
            exceptionService.processDequeueException(SystemConstant.RABBITMQ_COMPRADOR_PAY_EXPIRED, message, e.getMessage());
        }
    }

    private void createRefuseRecord(TDisposeSurvey survey, TDisposeLevel level) {
        TDisposeRefuseRecord record = new TDisposeRefuseRecord();
        record.setLevelId(level.getId());
        record.setProviderId(survey.getProviderId());
        record.setSurveyId(survey.getId());
        record.setRefuseTime(new Date());
        record.setAssignTime(survey.getAssignTime());
        disposeRefuseRecordService.addRefuseRecord(record);
    }

    /**
     * 异常信息队列出队
     */
    @RabbitListener(queues = SystemConstant.RABBITMQ_EXCEPTION_QUEUE)
    public void processExceptionDequeue(String message) {
        log.info("queue={}, message={}, dequeue",SystemConstant.RABBITMQ_EXCEPTION_QUEUE, message);
        exceptionService.processGlobalException(message);
    }

    /**
     * 账户注册队列出队
     */
    @RabbitListener(queues = SystemConstant.RABBITMQ_ACCOUNT_REGISTER_QUEUE)
    public void processAccountRegisterDequeue(String message) {
        log.info("queue={}, message={}, dequeue",SystemConstant.RABBITMQ_ACCOUNT_REGISTER_QUEUE, message);
        try {
            JSONObject data = JSONObject.parseObject(message);
            String mobile = data.getString("mobile");
            if (data.containsKey("password")) {
                smsHelperService.accountRegisterNotify(mobile, data.getString("password"));
            } else {
                smsHelperService.accountRegisterNotify(mobile);
            }
        } catch (Exception e) {
            e.printStackTrace();
            exceptionService.processDequeueException(SystemConstant.RABBITMQ_ACCOUNT_REGISTER_QUEUE, message, e.getMessage());
        }
    }

    /**
     * 用户认证申请通过队列出队
     */
    @RabbitListener(queues = SystemConstant.RABBITMQ_USER_APPLY_APPROVE_QUEUE)
    public void processUserApplyApproveDequeue(String message) {
        log.info("queue={}, message={}, dequeue",SystemConstant.RABBITMQ_USER_APPLY_APPROVE_QUEUE, message);
        try {
            JSONObject data = JSON.parseObject(message);
            Long applyId = data.getLong("applyId");
            Integer partyId = data.getInteger("partyId");
            TUserApplyRecord applyRecord = userVerifyApplicationService.getUserApplyRecordById(applyId);
            accountService.setExtBindCurrentPartyIdIfNeed(applyRecord.getAccountId(), partyId, PartyEnum.Type.user);
            if (PartyEnum.ApplySource.APPLET.getKey().equals(applyRecord.getApplySource())) {
                appletMessageService.sendAuthApplyApproveMessage(applyRecord.getAccountId());
                smsHelperService.appletApplyPassNotify(applyRecord.getMobile(), applyRecord.getName());
            } else {
                smsHelperService.userApplyPassNotify(applyRecord.getMobile(), applyRecord.getName());
            }
            accountService.syncShopIfNeed(applyRecord.getAccountId(), partyId, PartyEnum.Type.user);
            acctService.saveAcctIfNeed(partyId, AccountEnum.AcctType.USER.getKey());
            partyBind(applyRecord.getAccountId(), partyId);
        } catch (Exception e) {
            e.printStackTrace();
            exceptionService.processDequeueException(SystemConstant.RABBITMQ_USER_APPLY_APPROVE_QUEUE, message, e.getMessage());
        }
    }

    /**
     *  企业认证申请通过队列出队
     */
    @RabbitListener(queues = SystemConstant.RABBITMQ_COMPANY_APPLY_APPROVE_QUEUE)
    public void processCompanyApplyApproveDequeue(String message) {
        log.info("queue={}, message={}, dequeue",SystemConstant.RABBITMQ_COMPANY_APPLY_APPROVE_QUEUE, message);
        try {
            JSONObject data = JSON.parseObject(message);
            Long applyId = data.getLong("applyId");
            Integer partyId = data.getInteger("partyId");
            TCompanyApplyRecord applyRecord = companyVerifyApplicationService.findCompanyApplyRecordById(applyId);
            accountService.setExtBindCurrentPartyIdIfNeed(applyRecord.getAccountId(), partyId, PartyEnum.Type.company);
            if (PartyEnum.ApplySource.APPLET.getKey().equals(applyRecord.getApplySource())) {
                appletMessageService.sendAuthApplyApproveMessage(applyRecord.getAccountId());
                smsHelperService.appletApplyPassNotify(applyRecord.getMobile(), applyRecord.getName());
            } else {
                smsHelperService.companyApplyPassNotify(applyRecord.getMobile(), applyRecord.getName());
            }
            accountService.syncShopIfNeed(applyRecord.getAccountId(), partyId, PartyEnum.Type.company);
            acctService.saveAcctIfNeed(partyId, AccountEnum.AcctType.COMPANY.getKey());
            partyBind(applyRecord.getAccountId(), partyId);
        } catch (Exception e) {
            e.printStackTrace();
            exceptionService.processDequeueException(SystemConstant.RABBITMQ_COMPANY_APPLY_APPROVE_QUEUE, message, e.getMessage());
        }
    }

    private void partyBind(Integer accountId, Integer partyId) {
        try {
            favoriteEnrollingActivityService.partyIdBind(accountId + "", partyId + "");
            notifyPartyEnrollingActivityService.partyIdBind(accountId + "", partyId + "");
            favoriteActivityService.partyIdBind(accountId, partyId);
            notifyPartyActivityService.partyIdBind(accountId, partyId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  下线注册完成 （第二次，3天内并未完成认证）队列出队
     */
    @RabbitListener(queues = SystemConstant.RABBITMQ_SUBORDINATE_ACCOUNT_REGISTER_SECOND_QUEUE)
    public void processSubordinateAccountRegisterSecondDequeue(String message) {
        log.info("queue={}, message={}, dequeue",SystemConstant.RABBITMQ_SUBORDINATE_ACCOUNT_REGISTER_SECOND_QUEUE, message);
        try {
            appletMessageService.sendSubordinateAccountRegisterSecondMessageIfNeed(Integer.parseInt(message));
        } catch (Exception e) {
            e.printStackTrace();
            exceptionService.processDequeueException(SystemConstant.RABBITMQ_SUBORDINATE_ACCOUNT_REGISTER_SECOND_QUEUE, message, e.getMessage());
        }
    }

    /**
     *  下线认证完成（第二次）队列出队
     */
    @RabbitListener(queues = SystemConstant.RABBITMQ_SUBORDINATE_AUTH_FINISH_SECOND_QUEUE)
    public void processSubordinateAuthFinishSecondDequeue(String message) {
        log.info("queue={}, message={}, dequeue",SystemConstant.RABBITMQ_SUBORDINATE_AUTH_FINISH_SECOND_QUEUE, message);
        try {
            appletMessageService.sendSubordinateAuthFinishSecondMessageIfNeed(Integer.parseInt(message));
        } catch (Exception e) {
            e.printStackTrace();
            exceptionService.processDequeueException(SystemConstant.RABBITMQ_SUBORDINATE_AUTH_FINISH_SECOND_QUEUE, message, e.getMessage());
        }
    }
}
