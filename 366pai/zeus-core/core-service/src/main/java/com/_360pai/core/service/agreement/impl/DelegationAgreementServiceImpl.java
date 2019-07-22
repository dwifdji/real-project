package com._360pai.core.service.agreement.impl;

import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.arch.core.sysconfig.properties.SystemProperties;
import com._360pai.core.aspact.GatewayMqSender;
import com._360pai.core.common.constants.ActivityEnum;
import com._360pai.core.common.constants.AssetEnum;
import com._360pai.core.condition.agreement.DelegationAgreementCondition;
import com._360pai.core.condition.asset.AssetLeaseDataCondition;
import com._360pai.core.condition.lease.TLeaseStaffCondition;
import com._360pai.core.dao.account.AgencyPortalDao;
import com._360pai.core.dao.account.BankAccountDao;
import com._360pai.core.dao.account.TAgencyDao;
import com._360pai.core.dao.activity.AgencyPortalActivityDao;
import com._360pai.core.dao.activity.AuctionActivityDao;
import com._360pai.core.dao.activity.PlatformBroadcastDao;
import com._360pai.core.dao.agreement.DelegationAgreementDao;
import com._360pai.core.dao.asset.AssetDao;
import com._360pai.core.dao.asset.AssetLeaseDataDao;
import com._360pai.core.dao.assistant.BankDao;
import com._360pai.core.dao.lease.TLeaseStaffDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.account.resp.AccountBaseDto;
import com._360pai.core.facade.activity.req.ActivityReq;
import com._360pai.core.facade.activity.resp.ActivityResp;
import com._360pai.core.model.account.AgencyPortal;
import com._360pai.core.model.account.BankAccount;
import com._360pai.core.model.account.TAgency;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.activity.PlatformBroadcast;
import com._360pai.core.model.agreement.DelegationAgreement;
import com._360pai.core.model.asset.Asset;
import com._360pai.core.model.asset.AssetLeaseData;
import com._360pai.core.model.assistant.Bank;
import com._360pai.core.model.lease.TLeaseStaff;
import com._360pai.core.service.account.AccountService;
import com._360pai.core.service.agreement.DelegationAgreementService;
import com._360pai.core.service.assistant.AssistantService;
import com._360pai.core.service.assistant.SmsHelperService;
import com._360pai.gateway.common.fddSignature.FddEnums;
import com._360pai.gateway.controller.req.fdd.*;
import com._360pai.gateway.facade.FddSignatureFacade;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class DelegationAgreementServiceImpl	implements DelegationAgreementService{
	private static Logger logger = LoggerFactory.getLogger(DelegationAgreementServiceImpl.class);
	@Autowired
	private DelegationAgreementDao delegationAgreementDao;
	@Autowired
	private AssetDao assetDao;
	@Autowired
	private TAgencyDao agencyDao;
	@Autowired
	private AccountService accountService;
	@Autowired
	private AuctionActivityDao auctionActivityDao;
	@Autowired
	private BankDao bankDao;
	@Autowired
	private PlatformBroadcastDao platformBroadcastDao;
	@Autowired
	private AgencyPortalActivityDao agencyPortalActivityDao;
	@Autowired
	private AgencyPortalDao agencyPortalDao;
	@Autowired
	private SmsHelperService smsHelperService;
	@Autowired
	private SystemProperties systemProperties;
	@Reference(version = "1.0.0")
	private FddSignatureFacade fddSignatureFacade;
	@Autowired
	private GatewayMqSender mqSender;
	@Autowired
	private BankAccountDao bankAccountDao;
	@Autowired
	private TLeaseStaffDao leaseStaffDao;

	@Autowired
	private AssetLeaseDataDao assetLeaseDataDao;
	@Autowired
	private AssistantService assistantService;

	@Override
	public DelegationAgreement getByActivityId(Integer activityId) {
		DelegationAgreementCondition condition = new DelegationAgreementCondition();
		condition.setActivityId(activityId);
		return delegationAgreementDao.selectFirst(condition);
	}

	@Override
	public boolean generateContract(Integer activityId) {
		logger.info("生成委托协议，activityId={}", activityId);
		GenerateContractResp resp = invokeGatewayGenerateContract(activityId);
		DelegationAgreement agreement = delegationAgreementDao.getByActivityId(activityId);
		if (agreement == null) {
			agreement = new DelegationAgreement();
			agreement.setActivityId(activityId);
			agreement.setContractId(resp.getContractId());
			agreement.setDownloadUrl(resp.getDownloadUrl());
			agreement.setViewpdfUrl(resp.getViewPdfUrl());
			agreement.setSigned(false);
			agreement.setAllSigned(false);
			agreement.setCreatedAt(new Date());
			int result = delegationAgreementDao.insert(agreement);
			if (result == 0) {
				throw new BusinessException(ApiCallResult.FAILURE);
			}
		} else {
			agreement.setAllSignedAt(null);
			agreement.setContractId(resp.getContractId());
			agreement.setDownloadUrl(resp.getDownloadUrl());
			agreement.setViewpdfUrl(resp.getViewPdfUrl());
			agreement.setSigned(false);
			agreement.setAllSigned(false);
			agreement.setCreatedAt(new Date());
			int result = delegationAgreementDao.updateById(agreement);
			if (result == 0) {
				throw new BusinessException(ApiCallResult.FAILURE);
			}
		}
		return true;
	}

	private GenerateContractResp invokeGatewayGenerateContract(Integer activityId) {
		AuctionActivity auctionActivity = auctionActivityDao.selectById(activityId);
		Asset asset = assetDao.selectById(auctionActivity.getAssetId());
		TAgency agency = agencyDao.selectById(asset.getAgencyId());
		GenerateContractResp contractResp;
		GenerateContractComReq contractComReq = new GenerateContractComReq();
		contractComReq.setActivityId(auctionActivity.getId() + "");
		contractComReq.setPartyId(asset.getPartyId() + "");
		//租赁权拍卖判断
		if("-1".equals(String.valueOf(auctionActivity.getCategoryId()))){
			contractComReq.setFddId(agency.getFadadaId());
			contractComReq.setType(FddEnums.SING_TYPE.LEASE_DELEGATION.getType());
			contractResp = fddSignatureFacade.generateContract(contractComReq, getLeaseInfo(auctionActivity,asset));

			return contractResp;
		}


		if (AssetEnum.ComeFrom.AGENCY.getKey().equals(asset.getComeFrom())) {
			contractComReq.setFddId(agency.getFadadaId());
			contractComReq.setType(FddEnums.SING_TYPE.DELEGATION.getType());
			GenerateDelegationReq delegationReq = new GenerateDelegationReq();
			delegationReq.setActivityName(auctionActivity.getAssetName());
			delegationReq.setActivityCode(auctionActivity.getCode());
			delegationReq.setAgencyName(agency.getName());
			delegationReq.setAuctionMethod(ActivityEnum.ActivityMode.getKeyByValue(auctionActivity.getMode()));
			delegationReq.setAuctionFirmAddress(agency.getAddress());
			delegationReq.setAuctionFirmLegalRep(agency.getLegalPerson());
			delegationReq.setAuctionFirmPhone(agency.getMobile());
			delegationReq.setBeginTime(DateUtil.formatDate2Str(auctionActivity.getBeginAt(), DateUtil.NORM_DATETIME_PATTERN));
			delegationReq.setBeginYear(DateUtil.formatDate2Str(auctionActivity.getBeginAt(), DateUtil.YEAY));
			delegationReq.setEndTime(DateUtil.formatDate2Str(auctionActivity.getEndAt(), DateUtil.NORM_DATETIME_PATTERN));
			delegationReq.setPaymentDuration(asset.getPayDays() + "");
			delegationReq.setDeliveryDuration(asset.getHandoverDays() + "");
			if (auctionActivity.getRefPrice() != null) {
				delegationReq.setRefPrice(auctionActivity.getRefPrice().toPlainString());
			}
			delegationReq.setReservePrice(auctionActivity.getReservePrice().toPlainString());
			delegationReq.setPartyName(agency.getName());
			delegationReq.setSellerPhone(agency.getMobile());
			delegationReq.setSellerAddress(agency.getAddress());
			delegationReq.setSellerCommissionRate(auctionActivity.getCommissionPercentSeller().toPlainString());
			delegationReq.setSellerIdNumber(agency.getIdCard());
			delegationReq.setSellerLegalRep(agency.getLegalPerson());
			contractResp = fddSignatureFacade.generateContract(contractComReq, delegationReq);
			if (contractResp == null || !contractResp.getCode().equals("000")) {
				logger.error("生成委托协议失败，入参：{}，{}，出参：{}", JSON.toJSONString(contractComReq), JSON.toJSONString(delegationReq), JSON.toJSONString(contractResp));
				throw new BusinessException(ApiCallResult.FAILURE);
			}
		} else {
			AccountBaseDto accountBaseDto = accountService.getAccountBaseByPartyId(asset.getPartyId());
			contractComReq.setFddId(accountBaseDto.getFadadaId());
			if (asset.getOnlined() != null && asset.getOnlined().equals(0)) {
				GenerateOfflineDelegationReq delegationReq = new GenerateOfflineDelegationReq();
				if (asset.getBankOfflineFlag()) {
					contractComReq.setType(FddEnums.SING_TYPE.BANK_OFFLINE_DELEGATION.getType());
					BankAccount bankAccount = bankAccountDao.getLatestByPartyId(asset.getPartyId());
					if (bankAccount == null) {
						throw new BusinessException("请先设置委托人银行账户");
					}
					Bank bank = bankDao.selectById(bankAccount.getBankId());
					delegationReq.setBankName(bank.getName());
					delegationReq.setBankAccName(bankAccount.getName());
					delegationReq.setBankAccNo(bankAccount.getNumber());
				} else {
					contractComReq.setType(FddEnums.SING_TYPE.OFFLINE_DELEGATION.getType());
					delegationReq.setBankName(systemProperties.getOfflineBankName());
					delegationReq.setBankAccName(systemProperties.getOfflineBankAccountName());
					delegationReq.setBankAccNo(systemProperties.getOfflineBankAccountNumber());
				}
				delegationReq.setLotName(auctionActivity.getAssetName());
				delegationReq.setActivityCode(auctionActivity.getCode());
				delegationReq.setAuctionFirm(agency.getName());
				delegationReq.setAuctionMethod(ActivityEnum.ActivityMode.getKeyByValue(auctionActivity.getMode()));
				delegationReq.setAuctionFirmAddress(agency.getAddress());
				delegationReq.setAuctionFirmLegalRep(agency.getLegalPerson());
				delegationReq.setAuctionFirmPhone(agency.getMobile());
				delegationReq.setBeginTime(DateUtil.formatDate2Str(auctionActivity.getBeginAt(), DateUtil.NORM_DATETIME_PATTERN));
				delegationReq.setEndTime(DateUtil.formatDate2Str(auctionActivity.getEndAt(), DateUtil.NORM_DATETIME_PATTERN));
				delegationReq.setDealDate(asset.getHandoverDays() + "");
				delegationReq.setSignDate(asset.getHandoverDays() + "");
				delegationReq.setConfirmDate(asset.getPayDays() + "");

				delegationReq.setReservePrice(auctionActivity.getReservePrice().toPlainString());
				delegationReq.setSeller(accountBaseDto.getName());
				delegationReq.setSellerPhone(accountBaseDto.getMobile());
				delegationReq.setSellerAddress(accountBaseDto.getAddress());
				delegationReq.setSellerCommissionRate(auctionActivity.getCommissionPercentSeller().toPlainString());
				delegationReq.setSellerIdNumber(accountBaseDto.getIdOrLicenceNo());
				delegationReq.setSellerLegalRep(accountBaseDto.getLegalPerson());
				contractResp = fddSignatureFacade.generateContract(contractComReq, delegationReq);
				if (contractResp == null || !contractResp.getCode().equals("000")) {
					logger.error("生成委托协议失败，入参：{}，{}，出参：{}", JSON.toJSONString(contractComReq), JSON.toJSONString(delegationReq), JSON.toJSONString(contractResp));
					throw new BusinessException(ApiCallResult.FAILURE);
				}

			} else {
				if (accountBaseDto.isBank()) {
					contractComReq.setType(FddEnums.SING_TYPE.BANK_DELEGATION.getType());
					GenerateBankDelegationReq bankDelegationReq = new GenerateBankDelegationReq();
					bankDelegationReq.setActivityCode(auctionActivity.getCode());
					bankDelegationReq.setAuctionFirm(agency.getName());
					bankDelegationReq.setAuctionFirmAddress(agency.getAddress());
					bankDelegationReq.setAuctionFirmLegalRep(agency.getLegalPerson());
					bankDelegationReq.setAuctionFirmPhone(agency.getMobile());
					bankDelegationReq.setAuctionMethod(ActivityEnum.ActivityMode.getKeyByValue(auctionActivity.getMode()));
					bankDelegationReq.setBankAccountName(accountBaseDto.getBankAccountName());
					bankDelegationReq.setBankAccount(accountBaseDto.getBankAccountNo());
					if (accountBaseDto.getBankId() != null) {
						Bank bank = bankDao.selectById(accountBaseDto.getBankId());
						bankDelegationReq.setBankName(bank.getName());
					}
					bankDelegationReq.setBeginTime(DateUtil.formatDate2Str(auctionActivity.getBeginAt(), DateUtil.NORM_DATETIME_PATTERN));
					bankDelegationReq.setEndTime(DateUtil.formatDate2Str(auctionActivity.getEndAt(), DateUtil.NORM_DATETIME_PATTERN));
					bankDelegationReq.setLotName(auctionActivity.getAssetName());
					bankDelegationReq.setPaymentDuration(asset.getPayDays() + "");
					bankDelegationReq.setDeliveryDuration(asset.getHandoverDays() + "");
					bankDelegationReq.setReservePrice(auctionActivity.getReservePrice().toPlainString());
					bankDelegationReq.setSeller(accountBaseDto.getName());
					bankDelegationReq.setSellerAddress(accountBaseDto.getAddress());
					bankDelegationReq.setSellerCommissionRate(auctionActivity.getCommissionPercentSeller().toPlainString());
					bankDelegationReq.setSellerIdNumber(accountBaseDto.getIdOrLicenceNo());
					bankDelegationReq.setSellerLegalRep(accountBaseDto.getLegalPerson());
					bankDelegationReq.setSellerPhone(accountBaseDto.getMobile());
					contractResp = fddSignatureFacade.generateContract(contractComReq, bankDelegationReq);
					if (contractResp == null || !contractResp.getCode().equals("000")) {
						logger.error("生成委托协议失败，入参：{}，{}，出参：{}", JSON.toJSONString(contractComReq), JSON.toJSONString(bankDelegationReq), JSON.toJSONString(contractResp));
						throw new BusinessException(ApiCallResult.FAILURE);
					}
				} else {
					contractComReq.setType(FddEnums.SING_TYPE.DELEGATION.getType());
					GenerateDelegationReq delegationReq = new GenerateDelegationReq();
					delegationReq.setActivityName(auctionActivity.getAssetName());
					delegationReq.setActivityCode(auctionActivity.getCode());
					delegationReq.setAgencyName(agency.getName());
					delegationReq.setAuctionMethod(ActivityEnum.ActivityMode.getKeyByValue(auctionActivity.getMode()));
					delegationReq.setAuctionFirmAddress(agency.getAddress());
					delegationReq.setAuctionFirmLegalRep(agency.getLegalPerson());
					delegationReq.setAuctionFirmPhone(agency.getMobile());
					delegationReq.setBeginTime(DateUtil.formatDate2Str(auctionActivity.getBeginAt(), DateUtil.NORM_DATETIME_PATTERN));
					delegationReq.setBeginYear(DateUtil.formatDate2Str(auctionActivity.getBeginAt(), DateUtil.YEAY));
					delegationReq.setEndTime(DateUtil.formatDate2Str(auctionActivity.getEndAt(), DateUtil.NORM_DATETIME_PATTERN));
					delegationReq.setPaymentDuration(asset.getPayDays() + "");
					delegationReq.setDeliveryDuration(asset.getHandoverDays() + "");
					if (auctionActivity.getRefPrice() != null) {
						delegationReq.setRefPrice(auctionActivity.getRefPrice().toPlainString());
					}
					delegationReq.setReservePrice(auctionActivity.getReservePrice().toPlainString());
					delegationReq.setPartyName(accountBaseDto.getName());
					delegationReq.setSellerPhone(accountBaseDto.getMobile());
					delegationReq.setSellerAddress(accountBaseDto.getAddress());
					delegationReq.setSellerCommissionRate(auctionActivity.getCommissionPercentSeller().toPlainString());
					delegationReq.setSellerIdNumber(accountBaseDto.getIdOrLicenceNo());
					delegationReq.setSellerLegalRep(accountBaseDto.getLegalPerson());
					contractResp = fddSignatureFacade.generateContract(contractComReq, delegationReq);
					if (contractResp == null || !contractResp.getCode().equals("000")) {
						logger.error("生成委托协议失败，入参：{}，{}，出参：{}", JSON.toJSONString(contractComReq), JSON.toJSONString(delegationReq), JSON.toJSONString(contractResp));
						throw new BusinessException(ApiCallResult.FAILURE);
					}
				}
			}
		}
		logger.info("生成委托协议成功，activityId={}，resp={}", activityId, JSON.toJSONString(contractResp));
		return contractResp;
	}


	/**
	 *
	 *租赁权签章参数
	 */
	private Object getLeaseInfo(AuctionActivity auctionActivity, Asset asset) {
		//获取员工信息
		TLeaseStaffCondition condition = new TLeaseStaffCondition();
		condition.setPartId(asset.getPartyId());
		TLeaseStaff staff = leaseStaffDao.selectFirst(condition);
		if(staff==null){
			throw new BusinessException(ApiCallResult.FAILURE);
		}


		AccountBaseDto accountBaseDto = accountService.getAccountBaseByPartyId(staff.getComId());

		if(StringUtils.isEmpty(accountBaseDto.getBankAccountNo())){
			throw new BusinessException("公司银行信息为空！");

		}

		AssetLeaseDataCondition condition1 = new AssetLeaseDataCondition();
		condition1.setAssetId(asset.getId());
		condition1.setDeleteFlag(false);
		AssetLeaseData assetLeaseData = assetLeaseDataDao.selectFirst(condition1);


		GenerateLeaseDelegationReq req = new GenerateLeaseDelegationReq();
		req.setActivityCode(auctionActivity.getCode());
		req.setAuctionMethod(ActivityEnum.ActivityMode.getKeyByValue(auctionActivity.getMode()));
		req.setBeginTime(DateUtil.formatDate2Str(auctionActivity.getBeginAt(), DateUtil.NORM_DATETIME_PATTERN));
		req.setEndTime(DateUtil.formatDate2Str(auctionActivity.getEndAt(), DateUtil.NORM_DATETIME_PATTERN));
		req.setLotName(auctionActivity.getAssetName());
		req.setReservePrice(auctionActivity.getReservePrice()+"");
		req.setSeller(accountBaseDto.getName());
		req.setSellerPhone(accountBaseDto.getMobile());
		req.setSellerAddress(accountBaseDto.getAddress());
 		req.setSellerIdNumber(accountBaseDto.getIdOrLicenceNo());
		req.setSellerLegalRep(accountBaseDto.getLegalPerson());
		req.setBankName(accountBaseDto.getBankName());
		req.setBankAccount(accountBaseDto.getBankAccountNo());
		req.setBankAccountName(accountBaseDto.getBankAccountName());
		req.setLeaseStartTime(assetLeaseData.getLeaseStartTime());
		req.setLeaseEndTime(assetLeaseData.getLeaseEndTime());
		req.setEntrustName(staff.getName());
		req.setEntrustPhone(staff.getMobile());
 		return req;
	}

	@Override
	public ActivityResp delegationSignatureUrl(ActivityReq.ActivityId req) {
		ActivityResp resp = new ActivityResp();
		AuctionActivity auctionActivity = auctionActivityDao.selectById(req.getActivityId());
		if (!ActivityEnum.Status.PLATFORM_APPROVED.equals(auctionActivity.getStatus())) {
			throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
		}
		if (auctionActivity.getEndAt().before(new Date())) {
			throw new BusinessException("活动已经结束");
		}
		Asset asset = assetDao.selectById(auctionActivity.getAssetId());
		if (!req.getPartyId().equals(asset.getPartyId())) {
			throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
		}
		TAgency agency = agencyDao.selectById(asset.getAgencyId());
		DelegationAgreement agreement = delegationAgreementDao.getByActivityId(req.getActivityId());
		if (agreement == null) {
			throw new BusinessException(ApiCallResult.FAILURE);
		}
		if (DateUtil.getMarginSeconds(new Date(), agreement.getCreatedAt()) > 7 * 24 * 3600) {
			throw new BusinessException("送拍协议签署时间已超过七天");
		}
		ExtSignContractReq contractReq = new ExtSignContractReq();
		contractReq.setActivity_id(agreement.getActivityId() + "");
		contractReq.setContract_id(agreement.getContractId());
		contractReq.setType(getContractType(asset));


		List<FddSignInfo> sign_list = new ArrayList<>();
		// 委托人
		FddSignInfo sellerFddSignInfo = getSellerFddSignInfo(asset,agency);


		// 送拍机构
		FddSignInfo agencyFddSignInfo =getAgencyFddSignInfo(asset,agency);

		sign_list.add(sellerFddSignInfo);
		sign_list.add(agencyFddSignInfo);
		contractReq.setSign_list(sign_list);
		ExtSignContractResp contractResp = fddSignatureFacade.extSignContract(contractReq);
		if (contractResp == null || !contractResp.getCode().equals("000")) {
			throw new BusinessException(ApiCallResult.FAILURE);
		}
		StringBuilder url = new StringBuilder(contractResp.getUrl());
		url.append("?");
		JSONObject param = JSON.parseObject(contractResp.getParam());
		Iterator<String> itr = param.keySet().iterator();
		while (itr.hasNext()) {
			String key = itr.next();
			url.append(key);
			url.append("=");
			url.append(param.getString(key));
			if (itr.hasNext()) {
				url.append("&");
			}
		}
		resp.setUrl(url.toString());
		return resp;
	}


	/**
	 *
	 *获取送拍机构信息
	 */
	private FddSignInfo getAgencyFddSignInfo(Asset asset, TAgency agency) {
		FddSignInfo agencyFddSignInfo =new FddSignInfo();
		String fddId =agency.getFadadaId();
		//租赁权拍卖
		if(asset.getCategoryId()!=null&&"-1".equals(String.valueOf(asset.getCategoryId()))){
			fddId = getFddId(asset.getPartyId());
		}
		agencyFddSignInfo.setIs_auto("1");
		agencyFddSignInfo.setSign_role("3");
		agencyFddSignInfo.setMem_role("2");
		agencyFddSignInfo.setFdd_id(fddId);

		return agencyFddSignInfo;
	}


	/**
	 *
	 *获取委托人 签章信息
	 */
	private FddSignInfo getSellerFddSignInfo(Asset asset, TAgency agency) {

		FddSignInfo sellerFddSignInfo = new FddSignInfo();
		sellerFddSignInfo.setIs_auto("2");
		sellerFddSignInfo.setSign_role("1");

		//租赁权拍卖
		if(asset.getCategoryId()!=null&&"-1".equals(String.valueOf(asset.getCategoryId()))){
			sellerFddSignInfo.setMem_role("2");
			sellerFddSignInfo.setFdd_id(getPartyFddId(asset.getPartyId()));
			sellerFddSignInfo.setParty_id(asset.getPartyId() + "");

			return sellerFddSignInfo;

		}

		if (AssetEnum.ComeFrom.AGENCY.getKey().equals(asset.getComeFrom())) {
			sellerFddSignInfo.setMem_role("2");
			sellerFddSignInfo.setFdd_id(agency.getFadadaId());
			sellerFddSignInfo.setParty_id(agency.getId() + "");
		} else {
			AccountBaseDto accountBaseDto = accountService.getAccountBaseByPartyId(asset.getPartyId());
			if (SystemConstant.ACCOUNT_USER_TYPE.equals(accountBaseDto.getType())) {
				sellerFddSignInfo.setMem_role("1");
			} else if (SystemConstant.ACCOUNT_COMPANY_TYPE.equals(accountBaseDto.getType())) {
				sellerFddSignInfo.setMem_role("2");
			}
			sellerFddSignInfo.setFdd_id(accountBaseDto.getFadadaId());
			sellerFddSignInfo.setParty_id(accountBaseDto.getPartyPrimaryId() + "");
		}


		return sellerFddSignInfo;
	}

	private String getPartyFddId(Integer partyId) {

		AccountBaseDto baseDto = accountService.getAccountBaseByPartyId(partyId);

		return baseDto.getFadadaId();
	}


	/**
	 *
	 *获取经办人的法大大id
	 */
	private String getFddId(Integer partyId) {

		TLeaseStaffCondition condition = new TLeaseStaffCondition();
		condition.setPartId(partyId);
		TLeaseStaff staff = leaseStaffDao.selectFirst(condition);

		return staff.getFadadaId();
	}


	/**
	 *
	 *获取委托合同 类型
	 */
	private String getContractType(Asset asset) {

		//租赁权拍卖
		if(asset.getCategoryId()!=null&&"-1".equals(String.valueOf(asset.getCategoryId()))){
			return FddEnums.SING_TYPE.LEASE_DELEGATION.getType();
		}

		if (asset.getOnlined() != null && asset.getOnlined().equals(0)) {
			if (asset.getBankOfflineFlag()) {
				return  FddEnums.SING_TYPE.BANK_OFFLINE_DELEGATION.getType();
			} else {
				return FddEnums.SING_TYPE.OFFLINE_DELEGATION.getType();
			}

		} else {
			return FddEnums.SING_TYPE.DELEGATION.getType();
		}
 	}


	@Transactional
	@Override
	public ActivityResp signCallBack(ActivityReq.SignDelegationAgreementCallBackReq req) {
		logger.info("法大大签章回调：{}", JSON.toJSONString(req));
		ActivityResp resp = new ActivityResp();
		DelegationAgreement agreement = delegationAgreementDao.getByContractId(req.getContractId());
		if (!req.isHasSuccess()) {
			return resp;
		}
		AuctionActivity activity = auctionActivityDao.selectById(req.getActivityId());
		if (!ActivityEnum.Status.PLATFORM_APPROVED.equals(activity.getStatus())) {
			logger.error("活动{}的状态是{}，不能处理委托签章回调", activity.getId(), activity.getStatus().getKey());
			throw new BusinessException(ApiCallResult.FAILURE, "");
		}
		agreement.setSigned(true);
		agreement.setAllSigned(true);
		agreement.setAllSignedAt(new Date());
		int result = delegationAgreementDao.updateById(agreement);
		if (result == 0) {
			throw new BusinessException(ApiCallResult.FAILURE);
		}
		autoSignDelegation(agreement, activity);
		// 设置活动过期时间
		assistantService.setActivityExpireTime(activity);
		return resp;
	}

	@Override
	public List<Integer> getAllSignedTimeout() {
		return delegationAgreementDao.getAllSignedTimeout();
	}

	@Transactional
	@Override
	public boolean processDelegationAgreementSignedTimeout(Integer activityId) {
		DelegationAgreement agreement = delegationAgreementDao.getByActivityId(activityId);
		if (agreement == null || agreement.getAllSigned()) {
			return false;
		}
		long timeout = DateUtil.getMarginSeconds(new Date(), agreement.getCreatedAt());
		if (timeout < 7 * 24 * 3600) {
			return false;
		}
		AuctionActivity activity = auctionActivityDao.selectById(activityId);
		if (activity == null) {
			return false;
		}
		Asset asset = assetDao.selectById(activity.getAssetId());
		if (asset == null) {
			return false;
		}
		logger.info("签署送拍协议超时，activityId={}，status={}, timeout={}", activity.getId(), activity.getStatus().getKey(), timeout);
		int result;
		activity.setStatus(ActivityEnum.Status.CANCELLED);
		activity.setUpdatedAt(new Date());
		result = auctionActivityDao.updateById(activity);
		if (result == 0) {
			throw new BusinessException(ApiCallResult.FAILURE);
		}
		asset.setStatus(AssetEnum.Status.WITHDRAW);
		asset.setUpdatedAt(new Date());
		result = assetDao.updateById(asset);
		if (result == 0) {
			throw new BusinessException(ApiCallResult.FAILURE);
		}
		return true;
	}

	private void autoSignDelegation(DelegationAgreement agreement, AuctionActivity activity) {
		agreement.setAllSigned(true);
		agreement.setAllSignedAt(new Date());
		//活动上线
		activity.setStatus(ActivityEnum.Status.ONLINE);
		activity.setUpdatedAt(new Date());
		int result = auctionActivityDao.updateById(activity);
		if (result == 0) {
			throw new BusinessException(ApiCallResult.FAILURE);
		}
		PlatformBroadcast platformBroadcast = new PlatformBroadcast();
		platformBroadcast.setActivityId(activity.getId());
		platformBroadcast.setStatus(ActivityEnum.ActivityBroadcastStatus.UP_FOR_AUCTION.getKey());
		platformBroadcast.setCreatedAt(new Date());
		result = platformBroadcastDao.insert(platformBroadcast);
		if (result == 0) {
			throw new BusinessException(ApiCallResult.FAILURE);
		}
		// 标的状态为正在拍卖
		Asset asset = assetDao.selectById(activity.getAssetId());
		asset.setStatus(AssetEnum.Status.SELLING);
		asset.setUpdatedAt(new Date());
		result = assetDao.updateById(asset);
		if (result == 0) {
			throw new BusinessException(ApiCallResult.FAILURE);
		}
		sendOnAllSignActivityDelegationNotify(asset);
		//# 双方签署送拍协议，自动联拍
		AgencyPortal agencyPortal = agencyPortalDao.getByAgencyId(asset.getAgencyId());
		if (agencyPortal != null) {
			agencyPortalActivityDao.getOrCreateInstance(activity.getId(), agencyPortal.getId(), asset.getAgencyId());
		}
		sendOnAgencyUnionActivity(activity);
	}

	private void sendOnAllSignActivityDelegationNotify(Asset asset) {
		try {
			List<String> notifierMobiles = new ArrayList<>();
			if (AssetEnum.ComeFrom.PLATFORM.getKey().equals(asset.getComeFrom())) {
				notifierMobiles.add(accountService.getNotifierMobile(asset.getPartyId()));
			} else {
				notifierMobiles.add(accountService.getAgencyNotifierMobile(asset.getPartyId()));
			}
			for (String notifierMobile : notifierMobiles) {
				smsHelperService.delegationAgreementAllSignedNotify(notifierMobile, asset.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
			mqSender.sendTryCatchExceptionEmail(asset.getId(), e);
		}
	}

	private void sendOnAgencyUnionActivity(AuctionActivity activity) {
		try {
			String notifierMobile = accountService.getAgencyNotifierMobile(activity.getAgencyId());
			smsHelperService.agencyUnionActivityNotify(notifierMobile, activity.getAssetName());
		} catch (Exception e) {
			e.printStackTrace();
			mqSender.sendTryCatchExceptionEmail(activity.getId(), e);
		}
	}
}