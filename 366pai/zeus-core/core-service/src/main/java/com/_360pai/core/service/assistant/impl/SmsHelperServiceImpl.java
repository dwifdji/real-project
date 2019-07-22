package com._360pai.core.service.assistant.impl;

import com._360pai.core.aspact.GatewayMqSender;
import com._360pai.core.service.assistant.SmsHelperService;
import com._360pai.gateway.common.alisms.AliSmsTemplateEnums;
import com._360pai.gateway.controller.req.alisms.FAliSmsSendReq;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author xdrodger
 * @Title: SendSmsServiceImpl
 * @ProjectName zeus
 * @Description:
 * @date 2018/10/16 18:58
 */
@Slf4j
@Service
public class SmsHelperServiceImpl implements SmsHelperService {

    @Autowired
    private GatewayMqSender gatewayMqSender;

    @Override
    public void openElectronicSignatureNotify(String mobile) {
        doNotify(mobile, AliSmsTemplateEnums.OPEN_ELECTRONIC_SIGNATURE.getCode());
    }

    @Override
    public void openEasternPayNotify(String mobile) {
        doNotify(mobile, AliSmsTemplateEnums.PAYMENT_OPENING_SUCCESSFUL.getCode());
    }

    @Override
    public void accountRegisterNotify(String mobile) {
        doNotify(mobile, AliSmsTemplateEnums.ACCOUNT_REGISTER_SUCCESS.getCode());
    }

    @Override
    public void accountRegisterNotify(String mobile, String defaultPassword) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("default_password", defaultPassword);
        doNotify(mobile, jsonObject, AliSmsTemplateEnums.REGISTRATION_SUCCESS.getCode());
    }

    @Override
    public void agencySuccessfulReview(String mobile, String assetName, String agencyName) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("agency", agencyName);
        jsonObject.put("asset_name", assetName);
        doNotify(mobile, jsonObject, AliSmsTemplateEnums.AGENCY_SUCCESSFUL_REVIEW.getCode());
    }

    @Override
    public void platformReviewNotify(String mobile, String assetName, String agencyName) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("agency", agencyName);
        jsonObject.put("asset_name", assetName);
        doNotify(mobile, jsonObject, AliSmsTemplateEnums.PLATFORM_REVIEW.getCode());
    }

    @Override
    public void platformReviewPassedNotify(String mobile, String assetName) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("asset_name", assetName);
        doNotify(mobile, jsonObject, AliSmsTemplateEnums.PLATFORM_REVIEW_PASSED.getCode());
    }

    @Override
    public void delegationAgreementAllSignedNotify(String mobile, String assetName) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("asset_name", assetName);
        doNotify(mobile, jsonObject, AliSmsTemplateEnums.SUCCESSFUL_SIGNING_AGREEMENT.getCode());
    }

    @Override
    public void releaseLotNotify(String mobile, String assetName, BigDecimal reservePrice) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("asset_name", assetName);
        jsonObject.put("price", reservePrice.toPlainString());
        doNotify(mobile, jsonObject, AliSmsTemplateEnums.RELEASE_LOT.getCode());
    }

    @Override
    public void releaseLotToAgencyNotify(String mobile, String assetName, String userName) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("asset_name", assetName);
        jsonObject.put("user_name", userName);
        doNotify(mobile, jsonObject, AliSmsTemplateEnums.RELEASE_LOT_NOTIFICATION_AGENCY.getCode());
    }

    @Override
    public void agencyApplyNotify(String mobile, String agencyName) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("company_name", agencyName);
        doNotify(mobile, jsonObject, AliSmsTemplateEnums.AGENCY_CERTIFICATION_APPLICATION.getCode());
    }

    @Override
    public void agencyApplyPassNotify(String mobile, String agencyName, String code) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("company_name", agencyName);
        jsonObject.put("code", code);
        doNotify(mobile, jsonObject, AliSmsTemplateEnums.AGENCY_CERTIFICATION_PASS_TWO.getCode());
    }

    @Override
    public void userApplyNotify(String mobile, String name) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("personal_name", name);
        doNotify(mobile, jsonObject, AliSmsTemplateEnums.USER_APPLY_FOR_USER.getCode());
    }

    @Override
    public void userApplyToPlatformNotify(String mobile, String name) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("personal_name", name);
        doNotify(mobile, jsonObject, AliSmsTemplateEnums.USER_APPLY__FOR_CUSTOMER_SERVICE.getCode());
    }

    @Override
    public void userApplyPassNotify(String mobile, String name) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("personal_name", name);
        doNotify(mobile, jsonObject, AliSmsTemplateEnums.PERSION_APPLICANT_SUCCESSFUL.getCode());
    }

    @Override
    public void companyApplyNotify(String mobile, String name) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("company_name", name);
        doNotify(mobile, jsonObject, AliSmsTemplateEnums.COMPANY_APPLY_FOR_COMPANY.getCode());
    }

    @Override
    public void companyApplyToPlatformNotify(String mobile, String name) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("company_name", name);
        doNotify(mobile, jsonObject, AliSmsTemplateEnums.COMPANY_APPLY_FOR_CUSTOMER_SERVICE.getCode());
    }

    @Override
    public void companyApplyPassNotify(String mobile, String name) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("personal_name", name);
        doNotify(mobile, jsonObject, AliSmsTemplateEnums.BUSINESS_APPLICANT_SUCCESSFUL.getCode());
    }

    @Override
    public void auctionShipNotify(String mobile, String assetName) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("asset_name", assetName);
        doNotify(mobile, jsonObject, AliSmsTemplateEnums.AUCTION_SHIP.getCode());
    }

    @Override
    public void auctionReceiptNotify(String mobile, String assetName) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("asset_name", assetName);
        doNotify(mobile, jsonObject, AliSmsTemplateEnums.AUCTION_RECEIPT.getCode());
    }

    @Override
    public void toSignDealAgreementNotify(String mobile, String buyer, String assetName) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("buyer", buyer);
        jsonObject.put("asset_name", assetName);
        doNotify(mobile, jsonObject, AliSmsTemplateEnums.SIGNING_CONFIRMATION_SUCCESSFUL.getCode());
    }

    @Override
    public void auctionSuccessfulNotify(String mobile, String buyer, String assetName) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("buyer", buyer);
        jsonObject.put("asset_name", assetName);
        doNotify(mobile, jsonObject, AliSmsTemplateEnums.SUCCESSFUL_AUCTION.getCode());
    }

    @Override
    public void auctionSetRemindNotify(String mobile) {
        doNotify(mobile, AliSmsTemplateEnums.AUCTION_SET_REMINDE.getCode());
    }

    @Override
    public void auctionBeAboutToBeginNotify(String mobile, String assetName) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("asset_name", assetName);
        doNotify(mobile, jsonObject, AliSmsTemplateEnums.START_AUCTION_REMINDE.getCode());
    }

    @Override
    public void auctionBeAboutToEndNotify(String mobile, String assetName) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("asset_name", assetName);
        doNotify(mobile, jsonObject, AliSmsTemplateEnums.AUCTION_ACTIVITY_END.getCode());
    }

    @Override
    public void agencyUnionActivityNotify(String mobile, String assetName) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("asset_name", assetName);
        doNotify(mobile, jsonObject, AliSmsTemplateEnums.AGENCY_UNION_ACTIVITY.getCode());
    }

    @Override
    public void withfudigRequirementAudit(String mobile, String disposalCode) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("disposal_code", disposalCode);
        doNotify(mobile, jsonObject, AliSmsTemplateEnums.CONFIGURING_ASSETS_PASS.getCode());
    }

    @Override
    public void withfudigNotplatformInvest(String mobile, String disposalCode) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("disposal_code", disposalCode);
        doNotify(mobile, jsonObject, AliSmsTemplateEnums.CONFIGURING_ASSETS_ADDITIONAL_CAPITAL_REQUIREMENT.getCode());
    }

    @Override
    public void withfudigSupplementalInformationAudit(String mobile, String disposalCode) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("disposal_code", disposalCode);
        doNotify(mobile, jsonObject, AliSmsTemplateEnums.CONFIGURING_ASSETS_ADDITIONAL_MATERIALS_PASS.getCode());
    }

    @Override
    public void compradorRequirementAudit(String mobile, String disposalCode) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("disposal_code", disposalCode);
        doNotify(mobile, jsonObject, AliSmsTemplateEnums.ASSET_BUYING_OFFICE_PASS.getCode());
    }

    @Override
    public void compradorRecomenderAdd(String mobile, String disposalCode) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("disposal_code", disposalCode);
        doNotify(mobile, jsonObject, AliSmsTemplateEnums.ASSET_BUYING_OFFICE_INTENTION.getCode());
    }

    @Override
    public void disposalRequirementApplyPassNotify(String mobile, String disposal_code, String disposal_type) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("disposal_code", disposal_code);
        jsonObject.put("disposal_type", disposal_type);
        doNotify(mobile, jsonObject, AliSmsTemplateEnums.DISPOSAL_SERVICE_BID_PASS.getCode());
    }

    @Override
    public void disposalRequirementPlatformBid(String mobile, String disposal_code, String disposal_type) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("disposal_code", disposal_code);
        jsonObject.put("disposal_type", disposal_type);
        doNotify(mobile, jsonObject, AliSmsTemplateEnums.DISPOSAL_SERVICE_BID_PLATFORM_PASS.getCode());
    }

    @Override
    public void disposalRequirementNotPlatformBid(String mobile, String disposal_code, String disposal_type) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("disposal_code", disposal_code);
        jsonObject.put("disposal_type", disposal_type);
        doNotify(mobile, jsonObject, AliSmsTemplateEnums.DISPOSAL_SERVICE_BID.getCode());
    }

    @Override
    public void disposalSuccessToBid(String mobile, String disposal_code) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("disposal_code", disposal_code);
        doNotify(mobile, jsonObject, AliSmsTemplateEnums.BID_REQUEST_SUCCESSFUL.getCode());
    }

    @Override
    public void disposalBiddingFail(String mobile, String disposal_code) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("disposal_code", disposal_code);
        doNotify(mobile, jsonObject, AliSmsTemplateEnums.BID_REQUEST_FAILED.getCode());
    }

    @Override
    public void disposalDoneToRequirement(String mobile, String disposal_code, String disposal_type) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("disposal_code", disposal_code);
        jsonObject.put("disposal_type", disposal_type);
        doNotify(mobile, jsonObject, AliSmsTemplateEnums.DEMAND_SIDE_BIDDING_FAILED.getCode());
    }

    @Override
    public void disposalDoneToProvider(String mobile, String disposal_code) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("disposal_code", disposal_code);
        doNotify(mobile, jsonObject, AliSmsTemplateEnums.BID_REQUEST_FAILED.getCode());
    }

    @Override
    public void disposalRequirementApplyNotPassNotify(String mobile, String disposal_code, String disposal_type) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("disposal_code", disposal_code);
        jsonObject.put("disposal_type", disposal_type);
        doNotify(mobile, jsonObject, AliSmsTemplateEnums.DISPOSAL_SERVICE_BID_NOT_PASS.getCode());
    }

    @Override
    public void disposalRequirementApplyPassOrderNotify(String mobile, String disposal_code, String disposal_type) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("disposal_code", disposal_code);
        jsonObject.put("disposal_type", disposal_type);
        doNotify(mobile, jsonObject, AliSmsTemplateEnums.DISPOSAL_SERVICE_BID_PASS_PAY_NOTIFY.getCode());
    }

    @Override
    public void fastwayDisposeApplyAccessToSms(String mobile) {
        doNotify(mobile, AliSmsTemplateEnums.DISPOSAL_APPLY_SUCCESS.getCode());
    }

    @Override
    public void fastwayDisposeApplyDenyToSms(String mobile) {
        doNotify(mobile, AliSmsTemplateEnums.DISPOSAL_APPLY_FAILURE.getCode());
    }

    @Override
    public void fastwayAgencyApplyAccessToSms(String mobile) {
        doNotify(mobile, AliSmsTemplateEnums.AGENCY_APPLY_SUCCESS.getCode());
    }

    @Override
    public void fastwayAgencyApplyDenyToSms(String mobile) {
        doNotify(mobile, AliSmsTemplateEnums.AGENCY_APPLY_FAILURE.getCode());
    }

    @Override
    public void offlineDepositReceivedNotify(String mobile, String assetName) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("asset_name", assetName);
        doNotify(mobile, jsonObject, AliSmsTemplateEnums.AUCTION_OFFLINE_REMIND.getCode());
    }

    @Override
    public void userWithdrewNotify(String mobile, String name) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user_name", name);
        doNotify(mobile, jsonObject, AliSmsTemplateEnums.USER_WITHDRAWAL.getCode());
    }

    @Override
    public void commissionReminderNotify(String mobile) {
        doNotify(mobile, AliSmsTemplateEnums.COMMISSION_REMINDER.getCode());
    }

    @Override
    public void arrivalReminderNotify(String mobile) {
        doNotify(mobile, AliSmsTemplateEnums.ARRIVAL_REMINDER.getCode());
    }

    @Override
    public void fastwayFundApplyAccessToSms(String mobile) {
        doNotify(mobile, AliSmsTemplateEnums.FUND_SERVICE_PROVIDER_APPLY_SUCCESS.getCode());
    }

    @Override
    public void fastwayFundApplyDenyToSms(String mobile) {
        doNotify(mobile, AliSmsTemplateEnums.FUND_SERVICE_PROVIDER_APPLY_FAILURE.getCode());
    }

    @Override
    public void appletApplyPassNotify(String mobile, String name) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user_name", name);
        doNotify(mobile, jsonObject, AliSmsTemplateEnums.APPLET_APPLY_SUCCESS.getCode());
    }

    @Override
    public void disposeAdminCreate(String mobile) {
        doNotify(mobile, AliSmsTemplateEnums.DISPOSE_ADMIN_CREATE.getCode());
    }

    @Override
    public void shopUpdateApplyReject(String mobile) {
        doNotify(mobile, AliSmsTemplateEnums.APPLET_AVATAR_REVIEW.getCode());
    }


    @Override
    public void leaseSubmitAudit(String mobile, String name) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("activity_name", name);
        //租赁权 删除
        //doNotify(mobile, jsonObject, AliSmsTemplateEnums.LEASE_SUBMIT_AUDIT.getCode());
    }


    @Override
    public void leasePeriod(String mobile, String userName, String name) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("activity_name", name);
        jsonObject.put("user_name", userName);

        doNotify(mobile, jsonObject, AliSmsTemplateEnums.LEASE_PERIOD.getCode());
    }


    @Override
    public void leasePeriodPass(String mobile, String name) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("activity_name", name);

        doNotify(mobile, jsonObject, AliSmsTemplateEnums.LEASE_PERIOD_PASS.getCode());
    }

    @Override
    public void leasePeriodRejection(String mobile, String name) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("activity_name", name);

        doNotify(mobile, jsonObject, AliSmsTemplateEnums.LEASE_PERIOD_REJECTION.getCode());
    }

    @Override
    public void leaseFirstPush(String mobile, String name) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("activity_name", name);

        //doNotify(mobile, jsonObject, AliSmsTemplateEnums.LEASE_FIRST_PUSH.getCode());

    }

    @Override
    public void firstReviewRejection(String mobile, String name) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("activity_name", name);

        doNotify(mobile, jsonObject, AliSmsTemplateEnums.FIRST_REVIEW_REJECTION.getCode());
    }

    @Override
    public void waitingRelease(String mobile, String name) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("activity_name", name);

        doNotify(mobile, jsonObject, AliSmsTemplateEnums.WAITING_RELEASE.getCode());
    }

    @Override
    public void secondReviewRejection(String mobile, String name) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("activity_name", name);

        //doNotify(mobile, jsonObject, AliSmsTemplateEnums.SECOND_REVIEW_REJECTION.getCode());
    }

    private void doNotify(String mobile, String templateCode) {
        doNotify(mobile, new JSONObject(), templateCode);
    }

    private void doNotify(String mobile, JSONObject jsonObject, String templateCode) {
        try {
            log.info("发送短信，mobile={}，data={}，templateCode={}", mobile, jsonObject.toJSONString(), templateCode);
            if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(templateCode)) {
                return;
            }
            FAliSmsSendReq smsSendReq = new FAliSmsSendReq();
            smsSendReq.setPhoneNumber(mobile);
            smsSendReq.setTemplateCode(templateCode);
            smsSendReq.setTemplateParam(jsonObject.toJSONString());
            gatewayMqSender.sendSms(smsSendReq);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("短信发送失败，mobile={}，templateCode={}", mobile, templateCode);
        }
    }


    @Override
    public void platformReviewLeasePassedNotify(String mobile, String name) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("asset_name", name);
        doNotify(mobile, jsonObject, AliSmsTemplateEnums.LEASE_WEB_PASS.getCode());
    }
}
