package com._360pai.core.service.agreement.impl;

import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.core.common.constants.ActivityEnum;
import com._360pai.core.common.constants.AssetEnum;
import com._360pai.core.dao.account.TAgencyDao;
import com._360pai.core.dao.activity.AuctionActivityDao;
import com._360pai.core.dao.agreement.AdditionalAgreementDao;
import com._360pai.core.dao.asset.AssetDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.account.resp.AccountBaseDto;
import com._360pai.core.facade.activity.req.ActivityReq;
import com._360pai.core.facade.activity.resp.ActivityResp;
import com._360pai.core.model.account.TAgency;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.agreement.AdditionalAgreement;
import com._360pai.core.model.asset.Asset;
import com._360pai.core.service.account.AccountService;
import com._360pai.core.service.agreement.AdditionalAgreementService;
import com._360pai.core.service.assistant.AssistantService;
import com._360pai.gateway.controller.req.fdd.*;
import com._360pai.gateway.facade.FddSignatureFacade;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class AdditionalAgreementServiceImpl	implements AdditionalAgreementService {
	private static Logger logger = LoggerFactory.getLogger(AdditionalAgreementServiceImpl.class);
	@Autowired
	private AdditionalAgreementDao additionalAgreementDao;
	@Autowired
	private AuctionActivityDao auctionActivityDao;
	@Autowired
	private AssetDao assetDao;
	@Autowired
	private TAgencyDao agencyDao;
	@Reference(version = "1.0.0")
	private FddSignatureFacade fddSignatureFacade;
	@Autowired
	private AccountService accountService;
	private static SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.NORM_DATETIME_PATTERN);
	@Autowired
	private AssistantService assistantService;

	@Override
	public boolean generateContract(Integer activityId, BigDecimal newReservePrice) {
		logger.info("生成补充协议，activityId={}", activityId);
		AuctionActivity auctionActivity = auctionActivityDao.selectById(activityId);
		GenerateContractResp contractResp = invokeGatewayGenerateContract(auctionActivity, newReservePrice);
		AdditionalAgreement agreement = new AdditionalAgreement();
		agreement.setActivityId(auctionActivity.getId());
		agreement.setContractId(contractResp.getContractId());
		agreement.setDownloadUrl(contractResp.getDownloadUrl());
		agreement.setViewpdfUrl(contractResp.getViewPdfUrl());
		agreement.setOldReservePrice(auctionActivity.getReservePrice());
		agreement.setNewReservePrice(newReservePrice);
		agreement.setSigned(false);
		agreement.setAllSigned(false);
		agreement.setCreatedAt(new Date());
		int result = additionalAgreementDao.insert(agreement);
		if (result == 0) {
			throw new BusinessException(ApiCallResult.FAILURE);
		}
		return true;
	}

	private GenerateContractResp invokeGatewayGenerateContract(AuctionActivity auctionActivity, BigDecimal newReservePrice) {
		Asset asset = assetDao.selectById(auctionActivity.getAssetId());
		TAgency agency = agencyDao.selectById(asset.getAgencyId());
		GenerateContractComReq contractComReq = new GenerateContractComReq();
		contractComReq.setActivityId(auctionActivity.getId() + "");
		contractComReq.setPartyId(asset.getPartyId() + "");
		contractComReq.setType("2");
		GenerateAdditionalReq additionalReq = new GenerateAdditionalReq();
		if (AssetEnum.ComeFrom.AGENCY.getKey().equals(asset.getComeFrom())) {
			contractComReq.setFddId(agency.getFadadaId());
			additionalReq.setAuctionFirm(agency.getName());
			additionalReq.setAuctionFirmAddress(agency.getAddress());
			additionalReq.setAuctionFirmLegalRep(agency.getLegalPerson());
			additionalReq.setAuctionFirmPhone(agency.getMobile());
			additionalReq.setSignTime(sdf.format(new Date()));
			additionalReq.setActivityCode(auctionActivity.getCode());
			additionalReq.setReservePrice(auctionActivity.getReservePrice().toPlainString());
			additionalReq.setNewReservePrice(newReservePrice.toPlainString());
			additionalReq.setSeller(agency.getName());
			additionalReq.setSellerIdNumber(agency.getIdCard());
			additionalReq.setSellerAddress(agency.getAddress());
			additionalReq.setSellerPhone(agency.getMobile());
			additionalReq.setSellerLegalRep(agency.getLegalPerson());
		} else {
			AccountBaseDto accountBaseDto = accountService.getAccountBaseByPartyId(asset.getPartyId());
			contractComReq.setFddId(accountBaseDto.getFadadaId());
			additionalReq.setAuctionFirm(agency.getName());
			additionalReq.setActivityCode(auctionActivity.getCode());
			additionalReq.setAuctionFirmAddress(agency.getAddress());
			additionalReq.setAuctionFirmLegalRep(agency.getLegalPerson());
			additionalReq.setAuctionFirmPhone(agency.getMobile());
			additionalReq.setSignTime(sdf.format(new Date()));
			additionalReq.setReservePrice(auctionActivity.getReservePrice().toPlainString());
			additionalReq.setNewReservePrice(newReservePrice.toPlainString());
			additionalReq.setSeller(accountBaseDto.getName());
			additionalReq.setSellerIdNumber(accountBaseDto.getIdOrLicenceNo());
			additionalReq.setSellerAddress(accountBaseDto.getAddress());
			additionalReq.setSellerPhone(accountBaseDto.getMobile());
			additionalReq.setSellerLegalRep(accountBaseDto.getLegalPerson());
		}
		GenerateContractResp contractResp = fddSignatureFacade.generateContract(contractComReq, additionalReq);
		if (contractResp == null || !contractResp.getCode().equals("000")) {
			logger.error("生成补充协议失败，入参：{}，{}，出参：{}", JSON.toJSONString(contractComReq), JSON.toJSONString(additionalReq), JSON.toJSONString(contractResp));
			throw new BusinessException(ApiCallResult.FAILURE);
		}
		logger.info("生成补充协议成功，activityId={}，resp={}", auctionActivity.getId(), JSON.toJSONString(contractResp));
		return contractResp;
	}

	@Override
	public ActivityResp additionalSignatureUrl(ActivityReq.ActivityId req) {
		ActivityResp resp = new ActivityResp();
		AuctionActivity auctionActivity = auctionActivityDao.selectById(req.getActivityId());
		if (!ActivityEnum.Status.PLATFORM_APPROVED.equals(auctionActivity.getStatus())) {
			throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
		}
		if (auctionActivity.getBeginAt().before(new Date())) {
			throw new BusinessException("活动已经开始, 不能签订补充协议");
		}
		Asset asset = assetDao.selectById(auctionActivity.getAssetId());
		if (!req.getPartyId().equals(asset.getPartyId())) {
			throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
		}
		TAgency agency = agencyDao.selectById(asset.getAgencyId());
		AdditionalAgreement agreement = additionalAgreementDao.getByActivityId(req.getActivityId());
		if (agreement == null) {
			throw new BusinessException(ApiCallResult.FAILURE);
		}
		ExtSignContractReq contractReq = new ExtSignContractReq();
		contractReq.setActivity_id(agreement.getActivityId() + "");
		contractReq.setContract_id(agreement.getContractId());
		contractReq.setType("2");
		List<FddSignInfo> sign_list = new ArrayList<>();
		// 委托人
		FddSignInfo sellerFddSignInfo = new FddSignInfo();
		sellerFddSignInfo.setIs_auto("2");
		sellerFddSignInfo.setSign_role("1");
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

		// 送拍机构
		FddSignInfo agencyFddSignInfo = new FddSignInfo();
		agencyFddSignInfo.setIs_auto("1");
		agencyFddSignInfo.setSign_role("3");
		agencyFddSignInfo.setMem_role("2");
		agencyFddSignInfo.setFdd_id(agency.getFadadaId());
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

	@Transactional
	@Override
	public ActivityResp signCallBack(ActivityReq.SignAdditionalAgreementCallBackReq req) {
		logger.info("法大大签章回调：{}", JSON.toJSONString(req));
		ActivityResp resp = new ActivityResp();
		AdditionalAgreement agreement = additionalAgreementDao.getByContractId(req.getContractId());
		if (!req.isHasSuccess()) {
			return resp;
		}
		AuctionActivity activity = auctionActivityDao.selectById(req.getActivityId());
		if (!ActivityEnum.Status.PLATFORM_APPROVED.equals(activity.getStatus())) {
			logger.error("活动{}的状态是{}，不能处理委托补充签章回调", activity.getId(), activity.getStatus().getKey());
			throw new BusinessException(ApiCallResult.FAILURE, "");
		}
		agreement.setSigned(true);
		agreement.setAllSigned(true);
		int result = additionalAgreementDao.updateById(agreement);
		if (result == 0) {
			throw new BusinessException(ApiCallResult.FAILURE);
		}
		// 修改活动、标的状态
		//活动上线
		activity.setStatus(ActivityEnum.Status.ONLINE);
		activity.setUpdatedAt(new Date());
		result = auctionActivityDao.updateById(activity);
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
		// 设置活动过期时间
		assistantService.setActivityExpireTime(activity);
		return resp;
	}
}