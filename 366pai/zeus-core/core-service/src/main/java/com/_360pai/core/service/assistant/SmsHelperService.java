package com._360pai.core.service.assistant;

import java.math.BigDecimal;

/**
 * @author xdrodger
 * @Title: SendSmsService
 * @ProjectName zeus
 * @Description:
 * @date 2018/10/16 18:58
 */
public interface SmsHelperService {
    /**
     * 开通电子签章
     */
    void openElectronicSignatureNotify(String mobile);

    /**
     * 开通东方富通
     */
    void openEasternPayNotify(String mobile);

    /**
     * 注册成功通知
     */
    void accountRegisterNotify(String mobile);

    /**
     * 注册成功通知
     */
    void accountRegisterNotify(String mobile, String defaultPassword);

    /**
     * 机构审核成功通知
     */
    void agencySuccessfulReview(String mobile, String assetName, String agencyName);

    /**
     * 平台审核通知
     */
    void platformReviewNotify(String mobile, String assetName, String agencyName);

    /**
     * 平台审核成功通知
     */
    void platformReviewPassedNotify(String mobile, String assetName);

    /**
     * 委托协议签署成功通知
     */
    void delegationAgreementAllSignedNotify(String mobile, String assetName);

    /**
     * 发布拍品 通知委托人
     */
    void releaseLotNotify(String mobile, String assetName, BigDecimal reservePrice);

    /**
     * 发布拍品 通知机构
     */
    void releaseLotToAgencyNotify(String mobile, String assetName, String userName);

    /**
     * 机构申请
     */
    void agencyApplyNotify(String mobile, String agencyName);

    /**
     * 机构申请通过
     */
    void agencyApplyPassNotify(String mobile, String agencyName, String code);

    /**
     * 用户认证申请
     */
    void userApplyNotify(String mobile, String name);

    /**
     * 用户认证申请
     */
    void userApplyToPlatformNotify(String mobile, String name);

    /**
     * 用户认证申请通过
     */
    void userApplyPassNotify(String mobile, String name);

    /**
     * 企业认证申请
     */
    void companyApplyNotify(String mobile, String name);

    /**
     * 企业认证申请
     */
    void companyApplyToPlatformNotify(String mobile, String name);

    /**
     * 企业认证申请通过
     */
    void companyApplyPassNotify(String mobile, String name);

    /**
     * 发货通知 委托人
     */
    void auctionShipNotify(String mobile, String assetName);
    /**
     * 收货通知 买受人
     */
    void auctionReceiptNotify(String mobile, String assetName);

    /**
     * 签署成交确认书通知
     */
    void toSignDealAgreementNotify(String mobile, String buyer, String assetName);

    /**
     * 竞拍成功通知 买受人
     */
    void auctionSuccessfulNotify(String mobile, String buyer, String assetName);
    /**
     * 拍品设置提醒短信通知
     */
    void auctionSetRemindNotify(String mobile);
    /**
     * 拍卖活动即将开始提醒短信通知
     */
    void auctionBeAboutToBeginNotify(String mobile, String assetName);
    /**
     * 拍卖活动即将结束提醒短信通知
     */
    void auctionBeAboutToEndNotify(String mobile, String assetName);

    /**
     * 联拍机构发布通知
     */
    void agencyUnionActivityNotify(String mobile, String assetName);

    /**
     * 配资乐	审核通过	平台审核人	需求方	【360PAI】您的配资需求***（需求号）已经通过审核，请及时到会员中心查阅
     *
     * @author : whisky_vip
     * @date : 2018/10/17 14:40
     */
    void withfudigRequirementAudit(String mobile, String disposalCode);

    /**
     * 配资乐	我要意向（非平台类的配资需求）	出资方	需求方	【360PAI】您的配资需求***（需求号）已经有出资方意向配资，请在会员中心提交平台所需的补充资料，否则无法配资成功
     *
     * @author : whisky_vip
     * @date : 2018/10/17 14:41
     */
    void withfudigNotplatformInvest(String mobile, String disposalCode);

    /**
     * 配资乐	审核补充资料完成	平台审核人	需求方	【360PAI】您的配资需求***（需求号）所需的补充资料已经通过平台审核
     *
     * @author : whisky_vip
     * @date : 2018/10/17 14:42
     */
    void withfudigSupplementalInformationAudit(String mobile, String disposalCode);

    /**
     * 资产大买办	审核通过	平台审核人	需求方	【360PAI】您的资产大买办需求***（需求号）已经通过审核，请及时到会员中心查阅
     *
     * @author : whisky_vip
     * @date : 2018/10/17 14:43
     */
    void compradorRequirementAudit(String mobile, String disposalCode);

    /**
     * 资产大买办	我有意向	资产推介方	需求方	【360PAI】您的资产大买办需求***（需求号）已经有资产推介方意向匹配。我司客服会尽快联系您，为您一对一服务。
     *
     * @author : whisky_vip
     * @date : 2018/10/17 14:43
     */
    void compradorRecomenderAdd(String mobile, String disposalCode);

    /**
     * 处置服务需求单审核通过
     */
    void disposalRequirementApplyPassNotify(String mobile, String disposal_code, String disposal_type);

    /**
     * 处置服务平台类投标
     */
    void disposalRequirementPlatformBid(String mobile, String disposal_code, String disposal_type);

    /**
     * 处置服务非平台类投标
     */
    void disposalRequirementNotPlatformBid(String mobile, String disposal_code, String disposal_type);

    /**
     * 处置确认 中标方
     */
    void disposalSuccessToBid(String mobile, String disposal_code);

    /**
     * 处置确认 非中标方
     */
    void disposalBiddingFail(String mobile, String disposal_code);

    /**
     * 处置结束 需求方
     */
    void disposalDoneToRequirement(String mobile, String disposal_code, String disposal_type);

    /**
     * 处置结束 处置服务商 (与 处置确认 非中标方 模板一样)
     */
    void disposalDoneToProvider(String mobile, String disposal_code);

    /**
     * 处置服务需求单审核不通过
     */
    void disposalRequirementApplyNotPassNotify(String mobile, String disposal_code, String disposal_type);

    /**
     * 处置服务需求单审核通过，通知及时支付订单
     */
    void disposalRequirementApplyPassOrderNotify(String mobile, String disposal_code, String disposal_type);

    /**
     * 快速通道-处置服务商审核通过
     */
    void fastwayDisposeApplyAccessToSms(String mobile);

    /**
     * 快速通道-处置服务商审核拒绝
     */
    void fastwayDisposeApplyDenyToSms(String mobile);

    /**
     * 快速通道-机构审核通过
     */
    void fastwayAgencyApplyAccessToSms(String mobile);

    /**
     * 快速通道-机构审核拒绝
     */
    void fastwayAgencyApplyDenyToSms(String mobile);

    /**
     * 线下参拍资格提醒 竞买人
     */
    void offlineDepositReceivedNotify(String mobile, String assetName);

    /**
     * 用户提现成功 财务
     */
    void userWithdrewNotify(String mobile, String name);

    /**
     * 分佣提醒
     */
    void commissionReminderNotify(String mobile);

    /**
     * 到账提醒
     */
    void arrivalReminderNotify(String mobile);

    /**
     * 快速通道-资金服务商审核通过
     */
    void fastwayFundApplyAccessToSms(String mobile);

    /**
     * 快速通道-资金服务商审核拒绝
     */
    void fastwayFundApplyDenyToSms(String mobile);

    /**
     * 小程序认证通过
     */
    void appletApplyPassNotify(String mobile, String name);

    /**
     * 处置服务商后台认证通过
     */
    void disposeAdminCreate(String mobile);

    /**
     * 小程序店铺信息修改申请失败
     */
    void shopUpdateApplyReject(String mobile);



    /**
     * 租赁权提交审核
     */
    void leaseSubmitAudit(String mobile,String name);



    /**
     * 意向报名通知
     */
    void leasePeriod(String mobile,String userName ,String name);


    /**
     * 意向报名审核通过通知
     */
    void leasePeriodPass(String mobile,String name);


    /**
     * 意向报名审核拒绝通知
     */
    void leasePeriodRejection(String mobile,String name);


    /**
     * 租赁权-初审通过通知
     */
    void leaseFirstPush(String mobile,String name);


    /**
     * 租赁权-初审不通过通知
     */
    void firstReviewRejection(String mobile,String name);


    /**
     * 租赁权-终审通过
     */
    void waitingRelease(String mobile,String name);


    /**
     * 租赁权-终审不通过
     */
    void secondReviewRejection(String mobile,String name);



    /**
     * 租赁权-平台审核通过 通知
     */
    void platformReviewLeasePassedNotify(String mobile,String name);


}
