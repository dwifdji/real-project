package com._360pai.core.provider.disposal;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.utils.NumberValidationUtils;
import com._360pai.core.aspact.GatewayMqSender;
import com._360pai.core.common.constants.AccountEnum;
import com._360pai.core.common.constants.AssetEnum;
import com._360pai.core.common.constants.DisposalEnum;
import com._360pai.core.common.constants.ServiceFollowEnum;
import com._360pai.core.condition.disposal.TDisposalRequirementCondition;
import com._360pai.core.facade.account.resp.AccountBaseDto;
import com._360pai.core.facade.asset.vo.AssetVo;
import com._360pai.core.facade.disposal.DisposalRequirementFacade;
import com._360pai.core.facade.disposal.req.City;
import com._360pai.core.facade.disposal.req.DisposalBiddingReq;
import com._360pai.core.facade.disposal.req.DisposalRequirementReq;
import com._360pai.core.facade.disposal.resp.DisposalBidInfoResp;
import com._360pai.core.model.account.TDisposeProvider;
import com._360pai.core.model.asset.Asset;
import com._360pai.core.model.disposal.TDisposalBidding;
import com._360pai.core.model.disposal.TDisposalRequirement;
import com._360pai.core.service.account.AccountService;
import com._360pai.core.service.account.DisposeService;
import com._360pai.core.service.account.TAccountViewRecordService;
import com._360pai.core.service.asset.AssetService;
import com._360pai.core.service.assistant.CityService;
import com._360pai.core.service.assistant.TServiceFollowService;
import com._360pai.core.service.disposal.DisposalBiddingService;
import com._360pai.core.service.disposal.DisposalRequirementService;
import com._360pai.core.utils.ServiceMessageUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * @author xiaolei
 * @create 2018-09-14 13:39
 */
@Component
@Slf4j
@Service(version = "1.0.0")
public class DisposalRequirementProvider implements DisposalRequirementFacade {

    @Autowired
    private DisposalRequirementService disposalRequirementService;
    @Autowired
    private TServiceFollowService      tServiceFollowService;
    @Autowired
    private AssetService               assetService;
    @Autowired
    private CityService                cityService;
    @Autowired
    private DisposeService             disposeService;
    @Autowired
    private DisposalBiddingService     disposalBiddingService;
    @Autowired
    private ServiceMessageUtils        serviceMessageUtils;
    @Autowired
    private GatewayMqSender            mqSender;
    @Autowired
    private AccountService             accountService;
    @Autowired
    private TAccountViewRecordService tAccountViewRecordService;

    @Override
    public PageInfoResp findPublishedListPage(DisposalRequirementReq.GetPublishInfoReq req) {

        return disposalRequirementService.findPublishedListPage(req);
    }

    @Override
    public Object addDisposalRequirement(DisposalRequirementReq.AddRequirementInfo req) {


        /*
            获取asset 类型
         */
        Asset asset = assetService.getAsset(req.getAssetId());
        if (null == asset.getBusType()) {
            req.setIsPlatform(1);
        } else {
            req.setIsPlatform(AssetEnum.BusType.DISPOSAL.getKey().equals(asset.getBusType().toString()) ? 0 : 1);
        }

        /*
         *  所在地处理
         */
        cityDeal(req, asset);

        /*
         * 同个支付单需生成多个处置单
         */
        List<Integer> ids = disposalRequirementService.addDisposalRequirement(req);

        /*
         调用支付生成订单
         */
//        ServiceOrderReq.DisposalRequirementPay payReq = new ServiceOrderReq.DisposalRequirementPay();
//        payReq.setDisposalRequirementList(ids);
//        payReq.setAccountId(req.getAccountId());
//        payReq.setPartyId(req.getPartyId());
//        ServiceOrderResp serviceOrderResp = serviceOrderFacade.disposalRequirementPay(payReq);

        // 发信息
        addDisposalSendMessage(ids);
        return ids;
    }

    private void addDisposalSendMessage(List<Integer> ids) {
        for (Integer id : ids) {
//            TDisposalRequirement requirementDetail = disposalRequirementService.findRequirementDetail(id);
            // 需求单生成发信息
            serviceMessageUtils.disposalRequirementAdd(id);
            // 设置过期时间处置key
//            long timeout = (requirementDetail.getDeadline().getTime() - System.currentTimeMillis()) / 1000;
//            mqSender.disposalDeadlineEnqueue(id + "", timeout);
        }
    }

    private void cityDeal(DisposalRequirementReq.AddRequirementInfo req, Asset asset) {
        // 本平台处理城市
        if (req.getIsPlatform() == 1) {
            if (StringUtils.isNotBlank(asset.getCityId())) {
                String[] split         = asset.getCityId().split(",");
                City[]   providerAreas = new City[split.length];
                for (int i = 0; i < split.length; i++) {
                    Integer cityId = Integer.parseInt(split[i]);
                    City target = new City();
                    target.setId(cityId + "");
                    target.setName(cityService.getCityName(cityId));
                    providerAreas[i] = target;
                }
                req.setProviderAreas(providerAreas);
            }
            req.setCityId(asset.getCityId());
            req.setProvinceId(asset.getProvinceId());
            req.setAreaId(asset.getAreaId());
        }

        // 非本平台获取cityId
        if (req.getIsPlatform() == 0 && null != req.getProviderAreas()) {
            City[]        citys = req.getProviderAreas();
            Set<String> cityIds = new HashSet<>();
            Set<String> provinceIds = new HashSet<>();
            Set<String> areaIds = new HashSet<>();
            for (int i = 0;  i < citys.length; i ++) {
                City tmp = citys[i];
                if (tmp.getProvinceId() != null) {
                    provinceIds.add(tmp.getProvinceId() + "");
                } else {
                    provinceIds.add(cityService.getProvinceId(Integer.parseInt(tmp.getId())) + "");
                }
                cityIds.add(tmp.getId() + "");
                if (tmp.getAreaId() != null) {
                    areaIds.add(tmp.getAreaId() + "");
                }
            }
            req.setCityId(String.join(",", cityIds));
            req.setProvinceId(String.join(",", provinceIds));
            req.setAreaId(String.join(",", areaIds));
        }
    }

    @Override
    public PageInfoResp findBiddingSituation(DisposalRequirementReq.GetBiddingList req) {

        PageHelper.startPage(req.getPage(), req.getPerPage());
        List<TDisposalBidding> biddingSituation =
                disposalRequirementService.findBiddingSituation(req.getDisposalId());
        PageInfo<TDisposalBidding> pageInfo = new PageInfo<>(biddingSituation);

        List<DisposalBidInfoResp> list = new LinkedList<>();
        for (TDisposalBidding tmp : biddingSituation) {
            DisposalBidInfoResp resp = new DisposalBidInfoResp();
            BeanUtils.copyProperties(tmp, resp);
            resp.setBidCost(NumberValidationUtils.formatPrice(tmp.getBidCost()));
            resp.setBiddingId(tmp.getId());
            resp.setRegisterAddress(getRegion(tmp.getRegion()));
            resp.setContactName(Optional.ofNullable(tmp.getContactName()).orElse(getName(tmp.getPartyId())));
            resp.setCompanyName(Optional.ofNullable(tmp.getCompanyName()).orElse(resp.getContactName()));
            resp.setProviderType(DisposalEnum.DisposeType.getValueByKey(tmp.getDisposeType()));
//            if (StringUtils.isBlank(tmp.getCompanyName())) {
//                TDisposeProviderApply apply = disposeService.getDisposeProviderApplyByPartyId(tmp.getPartyId());
//                resp.setCompanyName(apply.getLawOffice());
//            }
            list.add(resp);
        }
        PageInfoResp<DisposalBidInfoResp> pageInfoResp = new PageInfoResp<>();
        pageInfoResp.setList(list);
        pageInfoResp.setTotal(pageInfo.getTotal());
        pageInfoResp.setHasNextPage(pageInfoResp.isHasNextPage());
        return pageInfoResp;
    }

    @Override
    public int updateFollowCount(String requirementId) {
        return disposalRequirementService.updateFollowCount(requirementId);
    }


    @Override
    public PageInfoResp findHotBidList(DisposalBiddingReq.GetBiddingInfoReq req) {
        return disposalRequirementService.findHotBidList(req);
    }

    @Override
    public PageInfoResp findSimilarBidListPage(DisposalBiddingReq.GetBiddingInfoReq req) {
        return disposalRequirementService.findSimilarBidListPage(req);
    }


    @Override
    public PageInfoResp findDisposalRequirementListPage(DisposalRequirementReq.GetPublishInfoReq req) {
        TDisposalRequirementCondition condition = new TDisposalRequirementCondition();
        if (StringUtils.isNotBlank((req.getDisposalType()))) {
            condition.setDisposalType(req.getDisposalType());
        }
        if (StringUtils.isNotBlank(req.getOrderBy())) {
            condition.setOrderBy(req.getOrderBy());
        }
        if ("1".equals(req.getTodayFlag())) {
            condition.setPublishTime(new Date());
        } else {
            condition.setPublishTime(null);
        }
        condition.setAreaId(req.getAreaId());
        condition.setCityId(req.getCityId());
        condition.setProvinceId(req.getProvinceId());
        condition.setDisposalName(req.getQuery());
        PageInfoResp pageInfoResp = disposalRequirementService.findDisposalRequirementListPage(condition, req.getPage(), req.getPerPage());
        if ("1".equals(req.getTodayFlag()) && pageInfoResp.getList().size() == 0) {
            condition.setOrderBy("publish_time_desc");
            condition.setPublishTime(null);
            pageInfoResp = disposalRequirementService.findDisposalRequirementListPage(condition, 1, 10);
            pageInfoResp.setHasNextPage(false);
            pageInfoResp.setTotal(pageInfoResp.getList().size());
        }
        return pageInfoResp;
    }


    @Override
    public Map<String, Object> findDisposalRequirementDetail(Integer disposalId, Integer partyId, Integer accountId) {
        tServiceFollowService.partyIdBind(accountId, partyId);
        TDisposalRequirement requirementDetail =
                disposalRequirementService.findRequirementDetail(disposalId);
        Map<String, Object> result = new HashMap<>();
        result.put("disposalNo", requirementDetail.getDisposalNo());
        result.put("disposalName", requirementDetail.getDisposalName());
        result.put("disposalId", requirementDetail.getId());
        result.put("publishTime", requirementDetail.getPublishTime());
        result.put("biddingNum", requirementDetail.getBiddingNum());
        result.put("viewNum", requirementDetail.getViewNum());
        result.put("followNum", requirementDetail.getFollowNum());
        result.put("caseDescription", requirementDetail.getCaseDescription());
        result.put("requireDescription", requirementDetail.getRequireDescription());
        result.put("cost", new BigDecimal(requirementDetail.getCost()).setScale(2, RoundingMode.HALF_UP).toPlainString());
        result.put("period", requirementDetail.getPeriod());
        result.put("biddingNotice", requirementDetail.getBiddingNotice());
        AssetVo assetById = assetService.getAssetById(requirementDetail.getAssetId());
        result.put("debtInterest", assetById.getDebtInterest() == null ? null : assetById.getDebtInterest().setScale(2, RoundingMode.HALF_UP));
        result.put("debtPrincipal", assetById.getDebtPrincipal() == null ? null : assetById.getDebtPrincipal().setScale(2, RoundingMode.HALF_UP));
        result.put("extra", getExtraArray(requirementDetail.getExtra()));
        result.put("assetId", requirementDetail.getAssetId());
        result.put("biddingFlag", partyId != null && disposalBiddingService.biddingFlag(disposalId, partyId));
        Map<String, String> map = getDisposeProviderTypeWithLawyer(partyId, requirementDetail.getDisposalType());
        result.put("supportType", map.get("code"));
        result.put("supportTypeDesc", map.get("desc"));
        result.put("providerAreas", getProviderAreas(requirementDetail.getProviderAreas()));
        result.put("cityName", cityService.getCityName(requirementDetail));
        if (null == accountId) {
            result.put("hasFollow", 0);
        } else {
            boolean b = tServiceFollowService.followFlag(disposalId, ServiceFollowEnum.RelativeType.DIPOSAL.getKey(),
                    partyId, accountId);
            result.put("hasFollow", b ? 1 : 0);
        }

        //新增足迹浏览记录
        if(accountId != null) {
            String newPartyId = partyId == null? null:partyId.toString();
            tAccountViewRecordService.findAndSaveViewRecord(accountId.toString(),newPartyId , disposalId.toString(), AccountEnum.ViewType.DISPOSAL.getKey());
        }
        return result;
    }

    @Override
    public int updateFollowCount(Integer[] disposalIds) {
        return disposalRequirementService.updateFollowCountList(disposalIds);
    }

    @Override
    public PageInfoResp findDisposalFollowListPage(DisposalRequirementReq.GetPublishInfoReq req) {
        tServiceFollowService.partyIdBind(req.getAccountId(), req.getPartyId());
        TDisposalRequirementCondition condition = new TDisposalRequirementCondition();
        condition.setDisposalType(req.getDisposalType());
        if (StringUtils.isNotBlank(req.getCityId())) {
            condition.setCityId(req.getCityId());
        }
        condition.setOrderBy(req.getOrderBy());
        condition.setPartyId(req.getPartyId());
        condition.setAccountId(req.getAccountId());
        return disposalRequirementService.findDisposalFollowListPage(condition,
                req.getPage(), req.getPerPage());
    }

    @Override
    public Map<String, Object> findDisposalRequirementCenterDetail(Integer disposalId, Integer partyId) {
        TDisposalRequirement requirementDetail =
                disposalRequirementService.findRequirementDetail(disposalId);
        Map<String, Object> result = new HashMap<>();
        result.put("disposalNo", requirementDetail.getDisposalNo());
        result.put("disposalName", requirementDetail.getDisposalName());
        result.put("disposalId", requirementDetail.getId());
        result.put("caseDescription", requirementDetail.getCaseDescription());
        result.put("requireDescription", requirementDetail.getRequireDescription());
        result.put("cost", new BigDecimal(requirementDetail.getCost()).setScale(2, RoundingMode.HALF_UP).toPlainString());
        result.put("costShow", NumberValidationUtils.formatPrice(requirementDetail.getCost()));
        result.put("period", requirementDetail.getPeriod());
        result.put("disposalType", requirementDetail.getDisposalType());
        result.put("deadline", requirementDetail.getDeadline());
        result.put("assetId", requirementDetail.getAssetId());
        result.put("providerAreas", getProviderAreas(requirementDetail.getProviderAreas()));
        result.put("extra", getExtraArray(requirementDetail.getExtra()));
        result.put("isSelfAsset", assetService.isSelfAsset(requirementDetail.getAssetId(), partyId));
        return result;
    }

    @Override
    public Map<String, Object> findDisposalRequirementCenterDetailEdit(Integer disposalId, Integer partyId) {
        TDisposalRequirement requirementDetail =
                disposalRequirementService.findRequirementDetail(disposalId);
        Map<String, Object> result = new HashMap<>();
        result.put("disposalNo", requirementDetail.getDisposalNo());
        result.put("disposalId", requirementDetail.getId());
        if (!requirementDetail.getDisposalType().equals(DisposalEnum.RequirementType.JINDIAO.getKey())
                && !requirementDetail.getDisposalType().equals(DisposalEnum.RequirementType.PINGGU.getKey())) {
            result.put("caseDescription", requirementDetail.getCaseDescription());
            result.put("requireDescription", requirementDetail.getRequireDescription());
        }
        result.put("cost", requirementDetail.getCost());
        result.put("period", requirementDetail.getPeriod());
        result.put("disposalType", requirementDetail.getDisposalType());
        result.put("deadline", requirementDetail.getDeadline());
        result.put("assetId", requirementDetail.getAssetId());
        if (assetService.isSelfAsset(requirementDetail.getAssetId(), partyId)) {
            result.put("disposalName", requirementDetail.getDisposalName());
            result.put("providerAreas", getProviderAreas(requirementDetail.getProviderAreas()));
            result.put("extra", getExtraArray(requirementDetail.getExtra()));
        }
        return result;
    }

    @Override
    public String[] getDisposeProviderType(Integer partyId) {
        TDisposeProvider provider = disposeService.getDisposeProviderByPartyId(partyId);
        if (provider == null) {
            return new String[0];
        } else {
            return provider.getDisposeType().split(",");
        }
    }

    @Override
    public int updateViewCount(Integer disposalId) {
        TDisposalRequirement detail = disposalRequirementService.findRequirementDetail(disposalId);
        TDisposalRequirement tmp    = new TDisposalRequirement();
        tmp.setId(disposalId);
        tmp.setViewNum(detail.getViewNum() + 1);
        return disposalRequirementService.disposalRequirementEdit(tmp);
    }

    private Map<String, String> getDisposeProviderType(Integer partyId, String disposalType) {
        Map<String, String> result   = new HashMap<>(2);
        TDisposeProvider    provider = disposeService.getDisposeProviderByPartyId(partyId);
        if (provider == null) {
            result.put("code", "20");
            result.put("desc", "请认证处置服务商");
            return result;// 不支持该类型
        } else {
            String[] split = provider.getProvideService().split(",");
            for (String tmp : split) {
                if (disposalType.equals(tmp)) {
                    result.put("code", "10");
                    result.put("desc", "支持该类型");
                    return result;// 支持该类型
                }
            }

            if (DisposalEnum.DisposeType.LAW_OFFICE.getKey().equals(provider.getDisposeType())) {
                if (DisposalEnum.RequirementType.PINGGU.getKey().equals(disposalType)) {
                    result.put("desc", "当前您不是评估机构，无法投标");
                } else {
                    result.put("desc", "您不支持" + DisposalEnum.RequirementType.getDescByKey(disposalType) + "服务，无法投标");
                }
            }

            if (DisposalEnum.DisposeType.EVALUATE_AGENCY.getKey().equals(provider.getDisposeType())) {
                result.put("desc", "当前您不是律师事务所，无法投标");
            }

            result.put("code", "20");
            return result;// 不支持该类型
        }
    }

    private Map<String, String> getDisposeProviderTypeWithLawyer(Integer partyId, String disposalType) {
        Map<String, String> result   = new HashMap<>(2);
        TDisposeProvider    provider = disposeService.getDisposeProviderByPartyId(partyId);
        if (provider == null) {
            result.put("code", "20");
            result.put("desc", "请认证处置服务商");
            return result;// 不支持该类型
        } else {

            // 律师事务所不支持评估
            // 评估机构不支持处置
            if (DisposalEnum.DisposeType.LAW_OFFICE.getKey().equals(provider.getDisposeType())
                    || DisposalEnum.DisposeType.LAWYER.getKey().equals(provider.getDisposeType()) ) {
                if (DisposalEnum.RequirementType.PINGGU.getKey().equals(disposalType)) {
                    result.put("code", "20");
                    result.put("desc", "当前您不是评估机构，无法投标");
                } else {
                    result.put("code", "10");
                    result.put("desc", "支持该类型");
                }
            }

            if (DisposalEnum.DisposeType.EVALUATE_AGENCY.getKey().equals(provider.getDisposeType())) {
                result.put("code", "20");
                result.put("desc", "当前您不是律师事务所，无法投标");
            }

            return result;// 不支持该类型
        }
    }


    private City[] getProviderAreas(String providerAreas) {
        if (StringUtils.isNotBlank(providerAreas)) {
            return JSONObject.parseObject(providerAreas, City[].class);
        }
        return new City[0];
    }

    private JSONArray getExtraArray(JSONObject extra) {
        if (null != extra && extra.getJSONArray("images").size() > 0) {
            return extra.getJSONArray("images");
        }
        return new JSONArray();
    }
    private String getRegion(String cityId) {
        if (cityId != null) {
            com._360pai.core.model.assistant.City byCityId = cityService.getByCityId(Integer.valueOf(cityId));
            if (byCityId == null)
                return "-";
            else
                return byCityId.getName();
        }
        return "-";
    }

    private String getName(Integer partyId) {
        AccountBaseDto accountBaseByPartyId = accountService.getAccountBaseByPartyId(partyId);
        return accountBaseByPartyId.getName();
    }

}
