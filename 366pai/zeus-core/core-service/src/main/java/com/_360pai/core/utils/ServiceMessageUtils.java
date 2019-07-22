package com._360pai.core.utils;

import com._360pai.core.aspact.ServiceEmailService;
import com._360pai.core.common.constants.AssetEnum;
import com._360pai.core.common.constants.DisposalEnum;
import com._360pai.core.common.constants.FastwayEnum;
import com._360pai.core.common.constants.ServiceMessageEnum;
import com._360pai.core.dao.comprador.TCompradorRequirementDao;
import com._360pai.core.dao.disposal.TDisposalBiddingDao;
import com._360pai.core.dao.disposal.TDisposalRequirementDao;
import com._360pai.core.dao.fastway.TFastwayAgencyApplyDao;
import com._360pai.core.dao.fastway.TFastwayDisposeApplyDao;
import com._360pai.core.dao.fastway.TFastwayFundApplyDao;
import com._360pai.core.dao.withfudig.TWithfudigRequirementDao;
import com._360pai.core.facade.account.resp.AccountBaseDto;
import com._360pai.core.model.account.TAccount;
import com._360pai.core.model.comprador.TCompradorRequirement;
import com._360pai.core.model.disposal.TDisposalBidding;
import com._360pai.core.model.disposal.TDisposalRequirement;
import com._360pai.core.model.fastway.TFastwayAgencyApply;
import com._360pai.core.model.fastway.TFastwayDisposeApply;
import com._360pai.core.model.fastway.TFastwayFundApply;
import com._360pai.core.model.withfudig.TWithfudigRequirement;
import com._360pai.core.service.account.AccountService;
import com._360pai.core.service.assistant.SmsHelperService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/10/15 13:25
 */
@Component
@Slf4j
public class ServiceMessageUtils {

    @Autowired
    private ServiceEmailService      serviceEmailService;
    @Autowired
    private SmsHelperService         smsHelperService;
    @Autowired
    private TWithfudigRequirementDao withfudigRequirementDao;
    @Autowired
    private TCompradorRequirementDao compradorRequirementDao;
    @Autowired
    private AccountService           accountService;
    @Autowired
    private TDisposalRequirementDao  disposalRequirementDao;
    @Autowired
    private TDisposalBiddingDao      disposalBiddingDao;
    @Autowired
    private TFastwayDisposeApplyDao tFastwayDisposeApplyDao;
    @Autowired
    private TFastwayAgencyApplyDao tFastwayAgencyApplyDao;
    @Autowired
    private TFastwayFundApplyDao tFastwayFundApplyDao;

    /**
     * 资产大买办	提交置业需求	需求方	平台审核人	"邮件主题：资产大买办需求**（需求号）需要及时审核
     * 内容：资产大买办需求****（需求号）需要及时审核，请尽快到360PAI系统后台操作~"	发邮件
     *
     * @author : whisky_vip
     * @date : 2018/10/17 14:15
     */
    public void compradorAdd(Integer requirementId) {
        serviceEmailService.sendServiceEmail(requirementId.toString(), ServiceMessageEnum.COMPRADOR_ADD);
    }

    /**
     * 资产大买办	确认投标	资产推介方	平台客服	"邮件主题：资产大买办需求**（需求号）有资产推介方确认投标
     * 内容：资产大买办需求**（需求号）有资产推介方确认投标，请尽快到360PAI系统后台操作并跟进，评估是否告知资产需求方相关信息~"	发邮件
     *
     * @author : whisky_vip
     * @date : 2018/10/17 14:16
     */
    public void compradorRecommenderAdd(Integer requirementId) {
        serviceEmailService.sendServiceEmail(requirementId.toString(), ServiceMessageEnum.COMPRADOR_RECOMMENDER_ADD);
        TCompradorRequirement tCompradorRequirement = compradorRequirementDao.selectById(requirementId);
        AccountBaseDto        baseDto               = accountService.getAccountBaseByPartyId(tCompradorRequirement.getPartyId());
        smsHelperService.compradorRecomenderAdd(baseDto.getMobile(), tCompradorRequirement.getRequirementNo());
    }

    /**
     * 资产大买办	提交资产推荐需求	资产推介方	平台审核人	"邮件主题：资产大买办需求**（需求号）需要审核
     * 内容：资产大买办需求**（需求号）需要审核，请尽快到360PAI系统后台操作并跟进~"	发邮件
     *
     * @author : whisky_vip
     * @date : 2018/10/17 14:16
     */
    public void compradorRecommendAdd(Integer dataId) {
        serviceEmailService.sendServiceEmail(dataId.toString(), ServiceMessageEnum.COMPRADOR_RECOMMEND_ADD);
    }

    /**
     * 配资乐	补充资料完成，后跳出审核对话框	需求方	平台审核人	"邮件主题：配资需求**（需求号）已补充配资所需资料
     * 内容：配资需求****（需求号)已补充配资所需资料,请需要及时审核，尽快到360PAI系统后台操作~
     * 有资金服务商有意向，请及时跟进"	发邮件
     *
     * @author : whisky_vip
     * @date : 2018/10/17 14:15
     */
    public void withfudigSupplementalInformation(Integer requirementId) {
        serviceEmailService.sendServiceEmail(requirementId.toString(), ServiceMessageEnum.WITHFUDIG_SUPPLEMENTAL_INFORMATION);
    }

    /**
     * 配资乐	点击提交	需求方	平台审核人	"邮件主题：配资需求**（需求号）需要及时审核
     * 内容：配资需求****（需求号）需要及时审核，请尽快到360PAI系统后台操作~"	发邮件
     *
     * @author : whisky_vip
     * @date : 2018/10/17 14:14
     */
    public void withfudigAdd(Integer requirementId) {
        serviceEmailService.sendServiceEmail(requirementId.toString(), ServiceMessageEnum.WITHFUDIG_ADD);
    }

    /**
     * 配资乐	我要意向（非平台类的配资需求）	出资方	平台客服	"邮件主题：配资需求**（需求号）有出资方意向配资
     * 内容：配资需求****（需求号)已有资金服务商意向配资，请尽快到360PAI系统后台操作，并撮合服务~"	发邮件
     *
     * @author : whisky_vip
     * @date : 2018/10/17 14:14
     */
    public void withfudigNotplatformInvest(Integer requirementId) {
//        serviceEmailService.sendServiceEmail(requirementId.toString(), ServiceMessageEnum.WITHFUDIG_NOTPLATFORM_INVEST);
        TWithfudigRequirement tWithfudigRequirement = withfudigRequirementDao.selectById(requirementId);
        AccountBaseDto        baseDto               = accountService.getAccountBaseByPartyId(tWithfudigRequirement.getPartyId());
        smsHelperService.withfudigNotplatformInvest(baseDto.getMobile(), tWithfudigRequirement.getRequirementNo());
    }

    /**
     * 配资乐	审核通过	平台审核人	需求方	【360PAI】您的配资需求***（需求号）已经通过审核，请及时到会员中心查阅
     *
     * @author : whisky_vip
     * @date : 2018/10/17 15:31
     */
    public void withfudigRequirementAudit(Integer requirementId) {
        TWithfudigRequirement tWithfudigRequirement = withfudigRequirementDao.selectById(requirementId);
        AccountBaseDto        baseDto               = accountService.getAccountBaseByPartyId(tWithfudigRequirement.getPartyId());
        smsHelperService.withfudigRequirementAudit(baseDto.getMobile(), tWithfudigRequirement.getRequirementNo());
    }

    /**
     * 配资乐	审核补充资料完成	平台审核人	需求方	【360PAI】您的配资需求***（需求号）所需的补充资料已经通过平台审核
     *
     * @author : whisky_vip
     * @date : 2018/10/17 15:32
     */
    public void withfudigSupplementalInformationAudit(Integer requirementId) {
        TWithfudigRequirement tWithfudigRequirement = withfudigRequirementDao.selectById(requirementId);
        AccountBaseDto        baseDto               = accountService.getAccountBaseByPartyId(tWithfudigRequirement.getPartyId());
        smsHelperService.withfudigSupplementalInformationAudit(baseDto.getMobile(), tWithfudigRequirement.getRequirementNo());
    }

    /**
     * 资产大买办	审核通过	平台审核人	需求方	【360PAI】您的资产大买办需求***（需求号）已经通过审核，请及时到会员中心查阅
     *
     * @author : whisky_vip
     * @date : 2018/10/17 15:32
     */
    public void compradorRequirementAudit(Integer requirementId) {
        TCompradorRequirement tCompradorRequirement = compradorRequirementDao.selectById(requirementId);
        AccountBaseDto        baseDto               = accountService.getAccountBaseByPartyId(tCompradorRequirement.getPartyId());
        smsHelperService.compradorRequirementAudit(baseDto.getMobile(), tCompradorRequirement.getRequirementNo());
    }


    /**
     * 处置服务   新增需求单  平台审核人
     * 邮件主题：处置需求**（需求号）需要及时审核
     * 内容：处置需求****需要及时审核，请尽快到360I系统后台操作~
     *
     * @param requirementId
     */
    public void disposalRequirementAdd(Integer requirementId) {
        serviceEmailService.sendServiceEmail(requirementId.toString(), ServiceMessageEnum.DISPOSAL_REQUIREMENT_ADD);
    }

    /**
     * 处置服务审核通过   发送需求方短信
     * 【360PAI】您的**（尽调/评估/执行等）处置需求***（需求号）已经通过审核，请及时到会员中心查阅
     */
    public void disposalRequirementApplyPassNotify(Integer requirementId) {
        TDisposalRequirement tDisposalRequirement = disposalRequirementDao.selectById(requirementId);
        String notifierMobile;
        if (AssetEnum.ComeFrom.AGENCY.getKey().equals(tDisposalRequirement.getComeFrom())) {
            notifierMobile = accountService.getAgencyNotifierMobile(tDisposalRequirement.getPartyId());
        } else {
            notifierMobile = accountService.getNotifierMobile(tDisposalRequirement.getPartyId());
        }
        // 发布通知
        smsHelperService.disposalRequirementApplyPassNotify(notifierMobile, tDisposalRequirement.getDisposalNo(),
                DisposalEnum.RequirementType.getDescByKey(tDisposalRequirement.getDisposalType()));
        // 订单通知
        smsHelperService.disposalRequirementApplyPassOrderNotify(notifierMobile, tDisposalRequirement.getDisposalNo(),
                DisposalEnum.RequirementType.getDescByKey(tDisposalRequirement.getDisposalType()));
    }

    /**
     * 处置服务平台类投标   发送需求方短信
     * 【360PAI】您的**（尽调/评估/执行等）处置需求***（需求号）已由处置服务商投标，请及时进行参拍 。
     */
    public void disposalRequirementPlatformBid(Integer requirementId) {
//        serviceEmailService.sendServiceEmail(requirementId.toString(), ServiceMessageEnum.DISPOSAL_BIDDING_SUCCESS_TO_OPERATOR);
        TDisposalRequirement tDisposalRequirement = disposalRequirementDao.selectById(requirementId);
        String notifierMobile;
        if (AssetEnum.ComeFrom.AGENCY.getKey().equals(tDisposalRequirement.getComeFrom())) {
            notifierMobile = accountService.getAgencyNotifierMobile(tDisposalRequirement.getPartyId());
        } else {
            notifierMobile = accountService.getNotifierMobile(tDisposalRequirement.getPartyId());
        }
//        AccountBaseDto       baseDto              = accountService.getAccountBaseByPartyId(tDisposalRequirement.getPartyId());
        smsHelperService.disposalRequirementPlatformBid(notifierMobile, tDisposalRequirement.getDisposalNo(),
                DisposalEnum.RequirementType.getDescByKey(tDisposalRequirement.getDisposalType()));
    }

    /**
     * 处置服务非平台类投标   发送需求方短信
     * 【360PAI】您的**（尽调/评估/执行等）处置需求***（需求号）已由处置服务商投标，请及时到会员中心查阅
     */
    public void disposalRequirementNotPlatformBid(Integer requirementId) {
//        serviceEmailService.sendServiceEmail(requirementId.toString(), ServiceMessageEnum.DISPOSAL_BIDDING_SUCCESS_TO_OPERATOR);
        TDisposalRequirement tDisposalRequirement = disposalRequirementDao.selectById(requirementId);
        String notifierMobile;
        if (AssetEnum.ComeFrom.AGENCY.getKey().equals(tDisposalRequirement.getComeFrom())) {
            notifierMobile = accountService.getAgencyNotifierMobile(tDisposalRequirement.getPartyId());
        } else {
            notifierMobile = accountService.getNotifierMobile(tDisposalRequirement.getPartyId());
        }
//        AccountBaseDto       baseDto              = accountService.getAccountBaseByPartyId(tDisposalRequirement.getPartyId());
        smsHelperService.disposalRequirementNotPlatformBid(notifierMobile, tDisposalRequirement.getDisposalNo(),
                DisposalEnum.RequirementType.getDescByKey(tDisposalRequirement.getDisposalType()));
    }

    /**
     * 处置确认 中标方   发送需求方短信
     * 【360PAI】您所投标的需求**（需求号）已中标，请持续关注360PAI.COM
     */
    public void disposalSuccessToBid(Integer biddingId) {
        TDisposalBidding     tDisposalBidding     = disposalBiddingDao.selectById(biddingId);
        TDisposalRequirement tDisposalRequirement = disposalRequirementDao.selectById(tDisposalBidding.getDisposalId());
        AccountBaseDto       baseDto              = accountService.getAccountBaseByPartyId(tDisposalBidding.getPartyId());
        smsHelperService.disposalSuccessToBid(baseDto.getMobile(), tDisposalRequirement.getDisposalNo());
    }

    /**
     * 处置确认 非中标方   发送需求方短信
     * 【360PAI】您所投标的需求**（需求号）没有中标，请持续关注360PAI.COM
     */
    public void disposalBiddingFail(Integer biddingId) {
        TDisposalBidding     tDisposalBidding     = disposalBiddingDao.selectById(biddingId);
        TDisposalRequirement tDisposalRequirement = disposalRequirementDao.selectById(tDisposalBidding.getDisposalId());
        AccountBaseDto       baseDto              = accountService.getAccountBaseByPartyId(tDisposalBidding.getPartyId());
        smsHelperService.disposalBiddingFail(baseDto.getMobile(), tDisposalRequirement.getDisposalNo());
    }

    /**
     * 处置结束 需求方  发送需求方短信
     * 【360PAI】您的**（尽调/评估/执行等）处置需求***（需求号）招标已经失败，请持续关注360PAI.COM
     */
    public void disposalDoneToRequirement(Integer requirementId) {
        TDisposalRequirement tDisposalRequirement = disposalRequirementDao.selectById(requirementId);
        String notifierMobile;
        if (AssetEnum.ComeFrom.AGENCY.getKey().equals(tDisposalRequirement.getComeFrom())) {
            notifierMobile = accountService.getAgencyNotifierMobile(tDisposalRequirement.getPartyId());
        } else {
            notifierMobile = accountService.getNotifierMobile(tDisposalRequirement.getPartyId());
        }
//        AccountBaseDto       baseDto              = accountService.getAccountBaseByPartyId(tDisposalRequirement.getPartyId());
        smsHelperService.disposalDoneToRequirement(notifierMobile, tDisposalRequirement.getDisposalNo(),
                DisposalEnum.RequirementType.getDescByKey(tDisposalRequirement.getDisposalType()));
    }

    /**
     * 处置结束 服务商  发送服务商短信
     * 【360PAI】您所投标的需求**（需求号）没有中标，请持续关注360PAI.COM
     */
    public void disposalDoneToProvider(Integer biddingId) {
        TDisposalBidding     tDisposalBidding     = disposalBiddingDao.selectById(biddingId);
        TDisposalRequirement tDisposalRequirement = disposalRequirementDao.selectById(tDisposalBidding.getDisposalId());
        AccountBaseDto       baseDto              = accountService.getAccountBaseByPartyId(tDisposalBidding.getPartyId());
        smsHelperService.disposalDoneToProvider(baseDto.getMobile(), tDisposalRequirement.getDisposalNo());
    }

    /**
     * 第三方尽调  发送邮件
     * 【尽调需求】+XX省XX城市
     */
    public void thirdConfirmToOperator(Integer activityId) {
        serviceEmailService.sendServiceEmail(activityId.toString(), ServiceMessageEnum.THIRD_CONFIRM_REPORT);
    }

    /**
     * 一级合伙人违约三次发送邮件
     * 标题：【违约3次】XX城市+XX律所
     * <p>
     * 正文：XX城市+XX律所+已违约3次+每次违约的时间
     */
    public void breakContract3ToOperator(Integer levelId) {
        serviceEmailService.sendServiceEmail(levelId.toString(), ServiceMessageEnum.BREAK_CONTRACT_3);
    }

    /**
     * 处置服务审核通过   发送需求方短信
     * 【360PAI】您的**（尽调/评估/执行等）处置需求***（需求号）已经通过审核，请及时到会员中心查阅
     */
    public void disposalRequirementApplyNotPassNotify(Integer requirementId) {
        TDisposalRequirement tDisposalRequirement = disposalRequirementDao.selectById(requirementId);
        String notifierMobile;
        if (AssetEnum.ComeFrom.AGENCY.getKey().equals(tDisposalRequirement.getComeFrom())) {
            notifierMobile = accountService.getAgencyNotifierMobile(tDisposalRequirement.getPartyId());
        } else {
            notifierMobile = accountService.getNotifierMobile(tDisposalRequirement.getPartyId());
        }
        smsHelperService.disposalRequirementApplyNotPassNotify(notifierMobile, tDisposalRequirement.getDisposalNo(),
                DisposalEnum.RequirementType.getDescByKey(tDisposalRequirement.getDisposalType()));
    }

    /**
     * 配资乐	我要意向发送客服邮件
     *
     * @author : whisky_vip
     * @date : 2018/10/17 14:14
     */
    public void withfudigNotplatformInvestToOperator(Integer requirementId) {
        serviceEmailService.sendServiceEmail(requirementId.toString(), ServiceMessageEnum.WITHFUDIG_NOTPLATFORM_INVEST);
    }

    /**
     * 快速通道  处置服务商申请
     * @param requirementId
     */
    public void fastwayDisposeApply(Integer requirementId) {
        serviceEmailService.sendServiceEmail(requirementId.toString(), ServiceMessageEnum.FASTWAY_DISPOSE_APPLY);
    }

    /**
     * 快速通道  审核通过发送短信
     * @param requirementId
     */
    public void fastwayDisposeApplyAccessToSms(Integer requirementId) {
        TFastwayDisposeApply disposeApply = tFastwayDisposeApplyDao.selectById(requirementId);
        TAccount tAccount = accountService.selectByPrimaryKey(disposeApply.getAccountId());
        smsHelperService.fastwayDisposeApplyAccessToSms(tAccount.getMobile());
    }

    /**
     * 快速通道  审核拒绝发送短信
     * @param requirementId
     */
    public void fastwayDisposeApplyDenyToSms(Integer requirementId) {
        TFastwayDisposeApply disposeApply = tFastwayDisposeApplyDao.selectById(requirementId);
        TAccount tAccount = accountService.selectByPrimaryKey(disposeApply.getAccountId());
        smsHelperService.fastwayDisposeApplyDenyToSms(tAccount.getMobile());
    }

    /**
     * 快速通道  机构审核通过发送短信
     * @param requirementId
     */
    public void fastwayAgencyApplyAccessToSms(Integer requirementId) {
        TFastwayAgencyApply agencyApply= tFastwayAgencyApplyDao.selectById(requirementId);
        TAccount tAccount = accountService.selectByPrimaryKey(agencyApply.getAccountId());
        smsHelperService.fastwayAgencyApplyAccessToSms(tAccount.getMobile());
    }

    /**
     * 快速通道  机构审核拒绝发送短信
     * @param requirementId
     */
    public void fastwayAgencyApplyDenyToSms(Integer requirementId) {
        TFastwayAgencyApply agencyApply = tFastwayAgencyApplyDao.selectById(requirementId);
        TAccount tAccount = accountService.selectByPrimaryKey(agencyApply.getAccountId());
        smsHelperService.fastwayAgencyApplyDenyToSms(tAccount.getMobile());
    }

    /**
     * 快速通道  拍卖行申请
     * @param requirementId
     */
    public void fastwayAgencyAuctionApply(Integer requirementId) {
        serviceEmailService.sendServiceEmail(requirementId.toString(), ServiceMessageEnum.FASTWAY_AGENCY_APPLY);
    }

    /**
     * 快速通道  资金服务商审核通过发送短信
     * @param requirementId
     */
    public void fastwayFundApplyAccessToSms(Integer requirementId) {
        TFastwayFundApply fundApply= tFastwayFundApplyDao.selectById(requirementId);
        TAccount tAccount = accountService.selectByPrimaryKey(fundApply.getAccountId());
        smsHelperService.fastwayFundApplyAccessToSms(tAccount.getMobile());
    }

    /**
     * 快速通道  资金服务商审核拒绝发送短信
     * @param requirementId
     */
    public void fastwayFundApplyDenyToSms(Integer requirementId) {
        TFastwayFundApply fundApply = tFastwayFundApplyDao.selectById(requirementId);
        TAccount tAccount = accountService.selectByPrimaryKey(fundApply.getAccountId());
        smsHelperService.fastwayFundApplyDenyToSms(tAccount.getMobile());
    }

    /**
     * 快速通道  资金服务商申请
     * @param requirementId
     */
    public void fastwayFundApply(Integer requirementId) {
        serviceEmailService.sendServiceEmail(requirementId.toString(), ServiceMessageEnum.FASTWAY_FUND_APPLY);
    }

    public void disposeAdminCreateToSms(Integer requirementId) {
        TAccount tAccount = accountService.selectByPrimaryKey(requirementId);
        smsHelperService.disposeAdminCreate(tAccount.getMobile());
    }
}
