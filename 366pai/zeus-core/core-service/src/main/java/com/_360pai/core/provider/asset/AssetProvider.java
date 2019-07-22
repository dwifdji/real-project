package com._360pai.core.provider.asset;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.core.common.constants.AssetEnum;
import com._360pai.core.common.constants.DisposalEnum;
import com._360pai.core.common.constants.LeaseEnum;
import com._360pai.core.condition.asset.AssetCategoryGroupCondition;
import com._360pai.core.facade.asset.AssetFacade;
import com._360pai.core.facade.asset.condition.AssetCGCondition;
import com._360pai.core.facade.asset.req.AssetAuthorizationReq;
import com._360pai.core.facade.asset.req.AssetReq;
import com._360pai.core.facade.asset.resp.AssetAuthorizationResp;
import com._360pai.core.facade.asset.resp.AssetListResp;
import com._360pai.core.facade.asset.resp.AssetResp;
import com._360pai.core.facade.disposal.req.City;
import com._360pai.core.facade.withfudig.req.WithfudigRequirementReq;
import com._360pai.core.model.asset.Asset;
import com._360pai.core.model.asset.AssetCategoryGroup;
import com._360pai.core.model.asset.AssetDataDrafts;
import com._360pai.core.model.asset.AssetLeaseData;
import com._360pai.core.model.disposal.TDisposalRequirement;
import com._360pai.core.service.asset.AssetAuthorizationService;
import com._360pai.core.service.asset.AssetCategoryGroupService;
import com._360pai.core.service.asset.AssetLeaseDataService;
import com._360pai.core.service.asset.AssetService;
import com._360pai.core.service.assistant.CityService;
import com._360pai.core.service.disposal.DisposalRequirementService;
import com._360pai.core.service.withfudig.WithfudigRequirementService;
import com._360pai.core.utils.ServiceMessageUtils;
import com._360pai.core.vo.asset.AssetLeaseDataVO;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 描述:
 *
 * @author : whisky_vip
 * @date : 2018/8/16 15:14
 */
@Component
@Service(version = "1.0.0")
public class AssetProvider implements AssetFacade {

    @Resource
    private AssetService assetService;
    @Resource
    private AssetCategoryGroupService assetCategoryGroupService;
    @Resource
    private WithfudigRequirementService withfudigRequirementService;
    @Resource
    private DisposalRequirementService disposalRequirementService;
    @Resource
    private ServiceMessageUtils serviceMessageUtils;
    @Autowired
    private AssetAuthorizationService assetauthorizationservice;
    @Autowired
    private CityService cityService;
    @Autowired
    private AssetLeaseDataService assetLeaseDataService;

    @Override
    public AssetResp getAsset(Integer id) {
        AssetResp resp = new AssetResp();
        resp.setAsset(assetService.getAssetById(id));
        return resp;
    }

    @Override
    public AssetResp getAsset(AssetReq.AssetIdReq req) {
        return assetService.getAsset(req);
    }

    @Override
    public PageInfo getAllAssetByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Asset> list = assetService.getAllAsset();
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo selectAssetCategoryGroup(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return assetCategoryGroupService.selectAssetCategoryGroup(pageNum, pageSize);
    }


    @Override
    public PageInfo selectAssetCategoryGroupByCondition(AssetCGCondition params, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        AssetCategoryGroupCondition assetCategoryGroupCondition = new AssetCategoryGroupCondition();
        BeanUtils.copyProperties(params, assetCategoryGroupCondition);
        List<AssetCategoryGroup> assetCategoryGroups = assetCategoryGroupService.selectAssetCategoryGroupByCondition(assetCategoryGroupCondition);
        return new PageInfo<>(assetCategoryGroups);
    }

    @Override
    public AssetListResp getAllAssetByPage(AssetReq.QueryReq req) {
        return assetService.getAllAssetByPage(req);
    }

    @Override
    public Object addAsset(AssetReq.AddReq req, Integer partyPrimaryId, String comeFrom) {
        return assetService.addAsset(req.getFields(), partyPrimaryId, comeFrom, req.getSpvId(),req);
    }

    @Override
    public PageInfo myAsset(AssetReq.QueryReq req) {
        return assetService.myAsset(req);
    }

    @Override
    public AssetResp update(AssetReq.UpdateReq req) {
        return assetService.update(req);
    }

    @Override
    public AssetResp withdrawAsset(AssetReq.AssetIdReq req) {
        return assetService.withdrawAsset(req);
    }

    @Override
    public ResponseModel assetEdit(AssetReq.AddReq req) {
        return assetService.assetEdit(req);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseModel disposalAssetEdit(AssetReq.AddDisposalReq req) {
        updateDisposalRequirement(req);
        return assetService.disposalAssetEdit(req.getAssetId(), req.getFields(), req.getPartyId());
    }

    @Override
    public ResponseModel withfudigAssetEdit(AssetReq.AddReq req) {
        return assetService.withfudigAssetEdit(req.getAssetId(), req.getFields());
    }

    @Override
    public Object assetDetail(AssetReq.AddReq req) {
        return assetService.assetDetail(req);
    }

    @Override
    public Object makeOldData(AssetReq.AddReq req) {
        return assetService.makeOldData(req);
    }

    @Override
    public Object addDisposalAsset(AssetReq.AddReq req, Integer partyPrimaryId) {
        return assetService.addDisposalAsset(req, partyPrimaryId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object addWithfudigAsset(AssetReq.AddReq req, Integer partyPrimaryId) {
        ResponseModel model = (ResponseModel) assetService.addWithfudigAsset(req.getFields(), partyPrimaryId);
        if (ApiCallResult.SUCCESS.getCode().equals(model.getCode())) {
            Integer assetId = (Integer) model.getContent();
            WithfudigRequirementReq.RequirementRelateAssertId updateReq = new WithfudigRequirementReq.RequirementRelateAssertId();
            updateReq.setAssetId(assetId);
            updateReq.setRequirementId(req.getRequirementId());
            withfudigRequirementService.requirementRelateAssertId(updateReq);

            // 发送邮件给平台人员
            serviceMessageUtils.withfudigSupplementalInformation(req.getRequirementId());
        }
        return model;
    }

    @Override
    public Object seeAssetDetail(AssetReq.AddReq req) {
        return assetService.seeAssetDetail(req);
    }

    @Override
    public Map<String, Object> productDetail(AssetReq.AddReq req) {
        return assetService.productDetail(req);
    }

    @Override
    public Object MyDetail(AssetReq.AddReq req) {
        return assetService.MyDetail(req);
    }

    @Override
    public void uploadSelfReport(AssetReq.UploadReportReq req) {
        assetService.uploadSelfReport(req.getAssetId(), req.getPrice(), req.getReports(), req.getPartyId());
    }

    @Override
    public AssetAuthorizationResp.PreSignTuneAuthProtocolResp preSignTuneAuthProtocol(AssetAuthorizationReq.PreSignTuneAuthProtocol req) {
        return assetauthorizationservice.preSignTuneAuthProtocol(req);
    }

    @Override
    public AssetAuthorizationResp signTuneAuthProtocol(AssetAuthorizationReq.SignTuneAuthProtocol req) {
        return assetauthorizationservice.signTuneAuthProtocol(req);
    }

    @Override
    public void draftsAsset(AssetReq.AddReq req, String partyPrimaryId) {
        assetService.draftsAsset(req, partyPrimaryId);
    }

    @Override
    public Object findDrafts(AssetReq.AddReq req, String partyPrimaryId) {
        return assetService.findDrafts(req, partyPrimaryId);
    }

    @Override
    public ResponseModel saveOrUpdateLeaseAsset(AssetReq.LeaseDataReq req) {
        //校验字段
        ResponseModel responseModel = checkField(req);
        if(responseModel != null) {
            return responseModel;
        }

        //封装实体
       Integer saveOrUpdateCount = assetLeaseDataService.saveOrUpdateLeaseAsset(req);
        if(saveOrUpdateCount == null) {
            return ResponseModel.fail();
        }

        return ResponseModel.succ(saveOrUpdateCount);
    }

    @Override
    public ResponseModel getLeaseAssetById(AssetReq.LeaseDataReq req) {

        AssetLeaseDataVO assetLeaseDataVO = assetLeaseDataService.getLeaseAssetById(req.getAssetId(), req.getWebFlag());
        if(assetLeaseDataVO == null) {
            return ResponseModel.fail(AssetEnum.FieldErrorMessage.FIND_NULL.getValue());
        }
        return ResponseModel.succ(assetLeaseDataVO);
    }

    @Override
    public ResponseModel saveOrUpdateLeaseDraft(AssetReq.LeaseDataReq req) {

        Integer saveOrUpdateCount = assetLeaseDataService.saveOrUpdateLeaseDraft(req);

        if(saveOrUpdateCount == null) {
            return ResponseModel.fail();
        }

        return ResponseModel.succ(saveOrUpdateCount);
    }

    @Override
    public ResponseModel getLeaseDraft(AssetReq.LeaseDataReq req) {

        AssetDataDrafts assetDataDrafts = assetLeaseDataService.getLeaseDraft(req.getPartyId());

        return ResponseModel.succ(assetDataDrafts);
    }

   

    private ResponseModel checkField(AssetReq.LeaseDataReq req) {

        // 校验必填字段
        if(req.getPowerSituation() == null) {
            return ResponseModel.fail(AssetEnum.FieldErrorMessage.POWER_SITUATION_NULL.getValue());
        }
        if(req.getLeaseArea() == null) {
            return ResponseModel.fail(AssetEnum.FieldErrorMessage.LEASE_AREA_NULL.getValue());
        }
        if(req.getLessorContact() == null) {
            return ResponseModel.fail(AssetEnum.FieldErrorMessage.LESSOR_CONTACT_NULL.getValue());
        }
        if(req.getLessorContactNumber() == null) {
            return ResponseModel.fail(AssetEnum.FieldErrorMessage.LESSOR_CONTACT_NUMBER_NULL.getValue());
        }
        if(StringUtils.isBlank(req.getAnnualIncrementRate())) {
            return ResponseModel.fail(AssetEnum.FieldErrorMessage.INCREMENT_RATE_NULL.getValue());
        }

        if(req.getReservePrice() == null) {
            return ResponseModel.fail(AssetEnum.FieldErrorMessage.RESERVE_PRICE_NULL.getValue());
        }

        if(req.getLeaseStartTime() == null || req.getLeaseEndTime() == null) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        if(req.getApplyEndTime() == null || req.getApprovalEndTime() == null) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        if(req.getBeginAt() == null || req.getEndAt() == null) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        if(req.getLessorCommissionRate() == null || req.getLesseeCommissionRate() == null) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        if(req.getBeginAt() == null || req.getEndAt() == null) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        if(req.getDeposit() == null || req.getReservePrice() == null) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }



        if(AssetEnum.ExpectedMode.ENGLISH.getKey().equals(req.getExpectedMode())) {
            if(req.getStartingPrice() == null) {
                return ResponseModel.fail(AssetEnum.FieldErrorMessage.START_PRICE_NULL.getValue());
            }
            if(req.getIncrement() == null) {
                return ResponseModel.fail(AssetEnum.FieldErrorMessage.INCREMENT_NULL.getValue());
            }

            if(req.getDeposit().compareTo(req.getStartingPrice()) == 1) {
                return ResponseModel.fail(AssetEnum.FieldErrorMessage.DEPOSIT_MORE_START.getValue());
            }
        }

        if(AssetEnum.ExpectedMode.DUTCH.getKey().equals(req.getExpectedMode())) {
            if(req.getStartingPrice() == null) {
                return ResponseModel.fail(AssetEnum.FieldErrorMessage.START_PRICE_NULL.getValue());
            }

            if(req.getReduction() == null) {
                return ResponseModel.fail(AssetEnum.FieldErrorMessage.REDUCTION_NULL.getValue());
            }

            if(req.getReductionPeriod() == null) {
                return ResponseModel.fail(AssetEnum.FieldErrorMessage.REDUCTION_PERIOD_NULL.getValue());
            }

            if(req.getDeposit().compareTo(req.getStartingPrice()) == 1) {
                return ResponseModel.fail(AssetEnum.FieldErrorMessage.DEPOSIT_MORE_START.getValue());
            }
        }



        // 校验时间
//        int marginSeven = DateUtil.getMargin(req.getApplyEndTime(), req.getPreviewAt());
//        if(marginSeven < AssetEnum.FieldErrorMessage.LESS_SEVEN_DAYS.getKey()) {
//            return ResponseModel.fail(AssetEnum.FieldErrorMessage.LESS_SEVEN_DAYS.getValue());
//        }
//
//        int marginTwo = DateUtil.getMargin(req.getApprovalEndTime(), req.getApplyEndTime());
//        if(marginTwo < AssetEnum.FieldErrorMessage.LESS_TWO_DAYS.getKey()) {
//            return ResponseModel.fail( AssetEnum.FieldErrorMessage.LESS_TWO_DAYS.getValue());
//        }
//
//        int marginOne = DateUtil.getMargin(req.getEndAt(), req.getBeginAt());
//        if(marginOne < AssetEnum.FieldErrorMessage.LESS_ONE_DAYS.getKey()) {
//            return ResponseModel.fail(AssetEnum.FieldErrorMessage.LESS_ONE_DAYS.getValue());
//        }

        return null;
    }

    private int updateDisposalRequirement(AssetReq.AddDisposalReq req) {
        if (req.getDisposalId() == null) {
            return 0;
        }
        AssetReq.Disposal disposal = req.getDisposal();
        TDisposalRequirement tmp = new TDisposalRequirement();
        tmp.setId(req.getDisposalId());
        tmp.setUpdateTime(new Date());
        tmp.setDisposalStatus(DisposalEnum.RequirementStatus.WAITING_PASS.getValue());
        tmp.setDeadline(DateUtil.getEndDate(disposal.getDeadline()));
        tmp.setPeriod(disposal.getPeriod());

        if (StringUtils.isNotBlank(disposal.getCost())) {
            tmp.setCost(disposal.getCost());
        }
        if (StringUtils.isNotBlank(disposal.getCaseDescription())) {
            tmp.setCaseDescription(disposal.getCaseDescription());
        }
        if (StringUtils.isNotBlank(disposal.getRequireDescription())) {
            tmp.setRequireDescription(disposal.getRequireDescription());
        }

        // 需要判断是否是本人才可修改的字段
        if (assetService.isSelfAsset(req.getAssetId(), req.getPartyId())) {
            if (StringUtils.isNotBlank(disposal.getDisposalName())) {
                tmp.setDisposalName(disposal.getDisposalName());
            }
            if (null != disposal.getProviderAreas()) {
                City[] citys = disposal.getProviderAreas();
                List<City> providerAreas = new ArrayList<>();
                List<String> cityIds = new ArrayList<>();
                List<String> provinceIds = new ArrayList<>();
                for (int i = 0; i < citys.length; i ++) {
                    City city = citys[i];
                    cityIds.add(city.getId() + "");
                    // todo
                    provinceIds.add(cityService.getProvinceId(Integer.parseInt(city.getId())) + "");
                    providerAreas.add(city);
                }
                tmp.setProviderAreas(JSON.toJSONString(providerAreas));
                tmp.setCityId(String.join(",", cityIds));
                tmp.setProvinceId(String.join(",", cityIds));
            }
            if (null != disposal.getExtra()) {
                // 前端数组最后一个为空需要处理
                String[] tmp2 = new String[disposal.getExtra().length - 1];
                System.arraycopy(disposal.getExtra(), 0, tmp2, 0, tmp2.length);
                tmp.setExtra(setJsonExtra(tmp2));
            }
        }

        int i = disposalRequirementService.disposalRequirementEdit(tmp);
        return i;
    }

    private JSONObject setJsonExtra(String[] extra) {
        if (extra != null) {
            JSONArray image = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            for (String tmp : extra) {
                image.add(tmp);
            }
            jsonObject.put("images", image);
            return jsonObject;
        }
        return null;
    }

}
