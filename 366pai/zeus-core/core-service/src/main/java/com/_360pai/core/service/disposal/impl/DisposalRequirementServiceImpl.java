package com._360pai.core.service.disposal.impl;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.enums.SideType;
import com._360pai.arch.common.utils.NumberValidationUtils;
import com._360pai.arch.common.utils.OrderCodeGenerator;
import com._360pai.core.common.constants.AssetEnum;
import com._360pai.core.common.constants.BackstageOperationEnum;
import com._360pai.core.common.constants.DisposalEnum;
import com._360pai.core.common.constants.ServiceFollowEnum;
import com._360pai.core.condition.assistant.TServiceFollowCondition;
import com._360pai.core.condition.disposal.TDisposalBiddingCondition;
import com._360pai.core.condition.disposal.TDisposalRequirementCondition;
import com._360pai.core.dao.assistant.AreaDao;
import com._360pai.core.dao.assistant.CityDao;
import com._360pai.core.dao.assistant.ProvinceDao;
import com._360pai.core.dao.assistant.TServiceFollowDao;
import com._360pai.core.dao.disposal.TDisposalBiddingDao;
import com._360pai.core.dao.disposal.TDisposalRequirementDao;
import com._360pai.core.facade.account.req.AcctReq;
import com._360pai.core.facade.disposal.req.City;
import com._360pai.core.facade.disposal.req.DisposalBiddingReq;
import com._360pai.core.facade.disposal.req.DisposalRequirementReq;
import com._360pai.core.facade.disposal.resp.DisposalServiceInvestResp;
import com._360pai.core.facade.disposal.resp.HotRequirementResp;
import com._360pai.core.facade.disposal.resp.RequirementProgressResp;
import com._360pai.core.facade.disposal.resp.SimilarRecommendResp;
import com._360pai.core.model.assistant.Area;
import com._360pai.core.model.assistant.Province;
import com._360pai.core.model.assistant.TServiceFollow;
import com._360pai.core.model.disposal.TDisposalBidding;
import com._360pai.core.model.disposal.TDisposalRequirement;
import com._360pai.core.service.assistant.CityService;
import com._360pai.core.service.assistant.TBackstageOperationRecodService;
import com._360pai.core.service.assistant.TServiceConfigService;
import com._360pai.core.service.disposal.DisposalRequirementService;
import com._360pai.core.utils.Constant;
import com._360pai.core.utils.ServiceMessageUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author xiaolei
 * @create 2018-09-14 12:49
 */
@Slf4j
@Service
public class DisposalRequirementServiceImpl implements DisposalRequirementService {
    private final static String REQUIREMENT_AUDIT_PASS   = "服务处置审核通过";
    private final static String REQUIREMENT_AUDIT_NOPASS = "服务处置审核拒绝";
    @Autowired
    TDisposalRequirementDao disposalRequirementDao;
    @Autowired
    TServiceFollowDao       tServiceFollowDao;
    @Autowired
    TDisposalBiddingDao     disposalBiddingDao;

    @Autowired
    private TServiceConfigService tServiceConfigService;

    @Autowired
    private ServiceMessageUtils             serviceMessageUtils;
    @Autowired
    private TBackstageOperationRecodService tBackstageOperationRecodService;
    @Autowired
    private ProvinceDao provinceDao;
    @Autowired
    private CityDao cityDao;
    @Autowired
    private AreaDao areaDao;
    @Autowired
    private CityService cityService;


    @Override
    public PageInfoResp findPublishedListPage(DisposalRequirementReq.GetPublishInfoReq req) {
        TDisposalRequirementCondition condition = new TDisposalRequirementCondition();
        condition.setPartyId(req.getPartyId());
        if (SideType.AGENCY.equals(req.getSideType())) {
            condition.setComeFrom(AssetEnum.ComeFrom.AGENCY.getKey());
        } else {
            condition.setComeFrom(AssetEnum.ComeFrom.PLATFORM.getKey());
        }
        PageHelper.startPage(req.getPage(), req.getPerPage());
        List<TDisposalRequirement>     requirements = disposalRequirementDao.mySelectList(condition);
        PageInfo<TDisposalRequirement> pageInfo     = new PageInfo<>(requirements);
        List<RequirementProgressResp>  respList     = new LinkedList<>();
        RequirementProgressResp        dto;
        for (TDisposalRequirement tmp : requirements) {
            dto = new RequirementProgressResp();
            BeanUtils.copyProperties(tmp, dto);
            dto.setDisposalId(tmp.getId());
            if (tmp.getIsPlatform() == 1) {
                dto.setDisposalName(tmp.getPlatformNo());
            } else {
                dto.setDisposalName(tmp.getDisposalName());
            }
            dto.setPayDeadlineTimestamp(tBackstageOperationRecodService.getPayDeadlineTimestamp(BackstageOperationEnum.Type.DISPOSAL_AUDIT, tmp.getId().longValue(), REQUIREMENT_AUDIT_PASS));
            respList.add(dto);
        }
        PageInfoResp<RequirementProgressResp> pageInfoResp = new PageInfoResp<>();
        pageInfoResp.setTotal(pageInfo.getTotal());
        pageInfoResp.setList(respList);
        pageInfoResp.setHasNextPage(pageInfo.isHasNextPage());
        return pageInfoResp;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Integer> addDisposalRequirement(DisposalRequirementReq.AddRequirementInfo req) {

        List<Integer>        result = new ArrayList<>();
        TDisposalRequirement model;
        for (String disposalType : req.getDisposalTypes()) {
            model = new TDisposalRequirement();
            BeanUtils.copyProperties(req, model);
            model.setDisposalType(disposalType);
            model.setDisposalNo(OrderCodeGenerator.INSTANCE.getOrderCode(Constant.DisposalCons.CZ_PREFIX));
            model.setPlatformNo(OrderCodeGenerator.INSTANCE.getOrderCode(Constant.DisposalCons.PT_PREFIX));
            model.setCost(req.getCost());
            model.setCreateTime(new Date());
            model.setUpdateTime(new Date());
            model.setProviderAreas(JSON.toJSONString(req.getProviderAreas()));
            model.setExtra(setJsonExtra(req.getExtra()));
            model.setDisposalStatus(DisposalEnum.RequirementStatus.WAITING_PASS.getValue());
            if (SideType.AGENCY.equals(req.getSideType())) {
                model.setComeFrom(AssetEnum.ComeFrom.AGENCY.getKey());
            } else {
                model.setComeFrom(AssetEnum.ComeFrom.PLATFORM.getKey());
            }
            disposalRequirementDao.insert(model);
            result.add(model.getId());
        }
        return result;
    }

    private JSONObject setJsonExtra(String[] extra) {
        if (extra != null) {
            JSONArray  image      = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            for (String tmp : extra) {
                image.add(tmp);
            }
            jsonObject.put("images", image);
            return jsonObject;
        }
        return null;
    }

    @Override
    public int updateRequirementStatusByBiddingId(String status, Integer biddingId, Integer operatorId) {
        return disposalRequirementDao.updateRequirementStatusByBiddingId(status, biddingId, operatorId);
    }

    @Override
    public int updateBiddingStatusByBiddingId(String status, Integer biddingId, Integer operatorId) {
        return disposalRequirementDao.updateBiddingStatusByBiddingId(status, biddingId, operatorId);
    }

    @Override
    public int updateRequirementVerifyStatusByDisposalId(String status, String verifyContent, Integer disposalId) {
        TDisposalRequirement condition = new TDisposalRequirement();
        condition.setVerifyContent(verifyContent);
        condition.setDisposalStatus(status);
        condition.setId(disposalId);
        return disposalRequirementDao.updateById(condition);
    }

    @Override
    public List<TDisposalBidding> findBiddingSituation(Integer disposalId) {
        return disposalRequirementDao.findBiddingSituation(disposalId);
    }

    @Override
    public int updateFollowCount(String requirementId) {
        TServiceFollowCondition condition = new TServiceFollowCondition();
        condition.setDelFlag(false);
        condition.setRelativeType(ServiceFollowEnum.RelativeType.DIPOSAL.getKey());
        condition.setRelativeId(Integer.valueOf(requirementId));

        List<TServiceFollow> list = tServiceFollowDao.selectList(condition);

        TDisposalRequirement model = disposalRequirementDao.selectById(requirementId);

        int count = 0;
        if (model != null) {
            model.setFollowNum(list.size());
            count += disposalRequirementDao.updateById(model);
        }
        return count;
    }

    @Override
    public PageInfoResp findSimilarBidListPage(DisposalBiddingReq.GetBiddingInfoReq req) {
        TDisposalRequirement condition = new TDisposalRequirement();
        condition.setDisposalType(req.getDisposalType());
        PageHelper.startPage(req.getPage(), req.getPerPage());
        List<TDisposalRequirement>     query    = disposalRequirementDao.findBySimilar(condition);
        PageInfo<TDisposalRequirement> pageInfo = new PageInfo<>(query);
        List<SimilarRecommendResp>     list     = new LinkedList<>();
        for (TDisposalRequirement tmp : query) {
            SimilarRecommendResp resp = new SimilarRecommendResp();
            BeanUtils.copyProperties(tmp, resp);
            resp.setCost(NumberValidationUtils.formatPrice(tmp.getCost()));
            resp.setDisposalId(tmp.getId());
            resp.setProviderAreas(JSONObject.parseObject(tmp.getProviderAreas(), City[].class));
            if (null != tmp.getExtra()) {
                JSONArray images = tmp.getExtra().getJSONArray("images");
                resp.setExtra(images.size() > 0 ? (String) images.get(0) : null);
            }
            list.add(resp);
        }
        PageInfoResp<SimilarRecommendResp> pageInfoResp = new PageInfoResp<>();
        pageInfoResp.setHasNextPage(pageInfo.isHasNextPage());
        pageInfoResp.setList(list);
        pageInfoResp.setTotal(pageInfoResp.getTotal());
        return pageInfoResp;
    }

    @Override
    public PageInfoResp findHotBidList(DisposalBiddingReq.GetBiddingInfoReq req) {
        PageHelper.startPage(req.getPage(), req.getPerPage());
        List<TDisposalRequirement> resultList =
                disposalRequirementDao.findByHotDisposalList(req.getDisposalType());
        PageInfo<TDisposalRequirement> pageInfo = new PageInfo<>(resultList);
        List<HotRequirementResp>       list     = new LinkedList<>();
        HotRequirementResp             dto;
        for (TDisposalRequirement tmp : resultList) {
            dto = new HotRequirementResp();
            BeanUtils.copyProperties(tmp, dto);
            dto.setDisposalId(tmp.getId());
            if (StringUtils.isNotBlank(tmp.getProviderAreas())) {
                dto.setProviderAreas(JSONObject.parseObject(tmp.getProviderAreas(), City[].class));
            }
            dto.setCityName(cityService.getCityName(tmp));
            list.add(dto);
        }
        PageInfoResp<HotRequirementResp> pageInfoResp = new PageInfoResp<>();
        pageInfoResp.setHasNextPage(pageInfo.isHasNextPage());
        pageInfoResp.setList(list);
        pageInfoResp.setTotal(pageInfo.getTotal());
        return pageInfoResp;
    }

    @Override
    public PageInfoResp findDisposalRequirementListPage(TDisposalRequirementCondition req, int pageNum, int pageSize) {

        //if (isOrderInfo(req.getOrderBy())) {
        //    req.setOrderVar1(req.getOrderBy().split("_")[0]);
        //    req.setOrderVar2(req.getOrderBy().split("_")[1]);
        //} else {
        //    req.setOrderVar1("create_time");
        //    req.setOrderVar2("desc");
        //}

        PageInfo<TDisposalRequirement>  pageInfo = disposalRequirementDao.findDisposalRequirementListPage(pageNum, pageSize, req);
        List<DisposalServiceInvestResp> list            = new LinkedList<>();
        DisposalServiceInvestResp       resp;
        for (TDisposalRequirement tmp : pageInfo.getList()) {
            resp = new DisposalServiceInvestResp();
            BeanUtils.copyProperties(tmp, resp);
            resp.setDisposalId(tmp.getId());
            resp.setExtra1(getExtra(tmp.getExtra()));

            resp.setCost(tmp.getCost());
            resp.setDisposalTypeDesc(DisposalEnum.RequirementType.getDescByKey(tmp.getDisposalType()));
            resp.setDisposalStatusDesc(DisposalEnum.RequirementStatus.getKeyByValue(tmp.getDisposalStatus()));
            if (StringUtils.isNotEmpty(tmp.getCityId())) {
                resp.setCityId(tmp.getCityId().split(","));
            }
            if (StringUtils.isNotBlank(tmp.getProviderAreas())) {
                resp.setProviderAreas(JSONObject.parseObject(tmp.getProviderAreas(), City[].class));
            } else {
                if (StringUtils.isNotEmpty(tmp.getProvinceId())) {
                    String[] provinceIds = tmp.getProvinceId().split(",");
                    List<City> providerAreas = new ArrayList<>();
                    List<String> cityIds = new ArrayList<>();
                    for (int i = 0; i < provinceIds.length; i ++) {
                        Integer provinceId = Integer.parseInt(provinceIds[i]);
                        City city = new City();
                        city.setId(provinceId + "");
                        city.setName(provinceDao.getName(provinceId));
                        providerAreas.add(city);

                        cityIds.add((-provinceId) + "");
                    }
                    resp.setCityId((String[]) cityIds.toArray());
                    resp.setProviderAreas((City[]) providerAreas.toArray());
                }
            }
            resp.setCityName(cityService.getCityName(tmp));
            list.add(resp);
        }
        PageInfoResp<DisposalServiceInvestResp> pageInfoResp = new PageInfoResp<>();
        pageInfoResp.setTotal(pageInfo.getTotal());
        pageInfoResp.setList(list);
        pageInfoResp.setHasNextPage(pageInfo.isHasNextPage());
        return pageInfoResp;
    }

    private String getExtra(JSONObject extra) {
        if (null != extra && extra.getJSONArray("images").size() > 0) {
            return (String) extra.getJSONArray("images").get(0);
        }
        return null;
    }

    private boolean isOrderInfo(String orderInfo) {
        String[] orderConstant = {
                Constant.DisposalCons.COST_ASC,
                Constant.DisposalCons.COST_DESC,
                Constant.DisposalCons.PERIOD_ASC,
                Constant.DisposalCons.PERIOD_DESC,
        };

        for (String tmp : orderConstant) {
            if (tmp.equals(orderInfo)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public TDisposalRequirement findRequirementDetail(Integer disposalId) {
        return disposalRequirementDao.findRequirementDetail(disposalId);
    }

    @Override
    public int updateFollowCountList(Integer[] disposalIds) {
        int count = 0;
        for (Integer tmp : disposalIds) {
            count += updateBiddingNumById(tmp);
        }
        return count;
    }

    @Override
    public int updateBiddingNumById(Integer disposalId) {
        return disposalRequirementDao.updateBiddingNumById(disposalId);
    }

    @Override
    public PageInfoResp findDisposalFollowListPage(TDisposalRequirementCondition req, int pageNum, int pageSize) {
        if (isOrderInfo(req.getOrderBy())) {
            req.setOrderVar1(req.getOrderBy().split("_")[0]);
            req.setOrderVar2(req.getOrderBy().split("_")[1]);
        } else {
            req.setOrderVar1("create_time");
            req.setOrderVar2("desc");
        }
        PageHelper.startPage(pageNum, pageSize);
        List<TDisposalRequirement>      requirementList = disposalRequirementDao.findDisposalFollowListPage(req);
        PageInfo<TDisposalRequirement>  pageInfo        = new PageInfo<>(requirementList);
        List<DisposalServiceInvestResp> list            = new LinkedList<>();
        setValueList(list, requirementList);
        PageInfoResp<DisposalServiceInvestResp> pageInfoResp = new PageInfoResp<>();
        pageInfoResp.setTotal(pageInfo.getTotal());
        pageInfoResp.setList(list);
        pageInfoResp.setHasNextPage(pageInfo.isHasNextPage());
        return pageInfoResp;
    }

    private void setValueList(List<DisposalServiceInvestResp> list, List<TDisposalRequirement>      requirementList) {
        DisposalServiceInvestResp       resp;
        for (TDisposalRequirement tmp : requirementList) {
            resp = new DisposalServiceInvestResp();
            BeanUtils.copyProperties(tmp, resp);
            resp.setDisposalId(tmp.getId());
            resp.setCost(tmp.getCost());
            resp.setCityId(tmp.getCityId().split(","));
            resp.setExtra1(getExtra(tmp.getExtra()));
            resp.setDisposalTypeDesc(DisposalEnum.RequirementType.getDescByKey(tmp.getDisposalType()));
            resp.setDisposalStatusDesc(DisposalEnum.RequirementStatus.getKeyByValue(tmp.getDisposalStatus()));
            if (StringUtils.isNotBlank(tmp.getProviderAreas())) {
                resp.setProviderAreas(JSONObject.parseObject(tmp.getProviderAreas(), City[].class));
            }
            list.add(resp);
        }
    }

    @Override
    public void disposalDeadlineUpdateSchedule() {
        /*
         * 定时更新达到截至日期的处置需求:
         * 1,遍历所有投标中的处置需求，判断是否达到截至日期
         * 2,达到截至日期的设为已完成状态
         * 3.截至日期单位为天，每天凌晨执行
         */
        TDisposalRequirementCondition condition = new TDisposalRequirementCondition();
        condition.setDisposalStatus(DisposalEnum.RequirementStatus.BEGINNING.getValue());
        condition.setIsDel(0);
        List<TDisposalRequirement> requirements = disposalRequirementDao.selectList(condition);

        for (TDisposalRequirement tmp : requirements) {
            // 超时
            if (tmp.getDeadline().compareTo(null) >= 0) {
                log.info("now: {}, 达到截至日期的需求单 id: {}", System.currentTimeMillis(), tmp.getId());
                updateDeadlineStatus(tmp);
            }
        }

    }

    @Override
    @Transactional
    public void updateDeadlineStatus(TDisposalRequirement tmp) {
        // 更新需求单和投标单的状态
        tmp.setDisposalStatus(DisposalEnum.RequirementStatus.FINISH.getValue());
        disposalRequirementDao.updateById(tmp);
        TDisposalBidding bidding = new TDisposalBidding();
        bidding.setDisposalId(tmp.getId());
        bidding.setBidStatus(DisposalEnum.BiddingStatus.DONE.getKey());
        bidding.setUpdateTime(new Date());
        disposalBiddingDao.updateManuallyFinished(bidding);
    }

    @Override
    public int disposalRequirementEdit(TDisposalRequirement tmp) {
        return disposalRequirementDao.updateById(tmp);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRequirementByDeadline(Integer disposalId) {
        TDisposalRequirement tDisposalRequirement = disposalRequirementDao.selectById(disposalId);
        if (null != tDisposalRequirement
                && tDisposalRequirement.getDisposalStatus().equals(DisposalEnum.RequirementStatus.BEGINNING.getValue())) {
            // 过期修改需求单状态
            tDisposalRequirement.setDisposalStatus(DisposalEnum.RequirementStatus.FINISH.getValue());
            tDisposalRequirement.setUpdateTime(new Date());
            disposalRequirementDao.updateById(tDisposalRequirement);
            TDisposalBiddingCondition condition = new TDisposalBiddingCondition();
            condition.setDisposalId(disposalId);
            List<TDisposalBidding> tDisposalBiddings = disposalBiddingDao.selectList(condition);
            for (TDisposalBidding tmp : tDisposalBiddings) {
                tmp.setUpdateTime(new Date());
                tmp.setBidStatus(DisposalEnum.BiddingStatus.DONE.getKey());
                disposalBiddingDao.updateById(tmp);
            }
        }
    }

    @Override
    public void sendMessageByDeadline(Integer disposalId) {
        TDisposalRequirement tDisposalRequirement = disposalRequirementDao.selectById(disposalId);
        // 对未竞标成功的处置发送短信
        if (null != tDisposalRequirement
                && DisposalEnum.RequirementStatus.FINISH.getValue().equals(tDisposalRequirement.getDisposalStatus())) {
            // 处置需求到期提醒
            serviceMessageUtils.disposalDoneToRequirement(disposalId);
            TDisposalBiddingCondition condition = new TDisposalBiddingCondition();
            condition.setDisposalId(disposalId);
            condition.setBidStatus(DisposalEnum.BiddingStatus.DONE.getKey());
            condition.setIsDel(0);
            List<TDisposalBidding> tDisposalBiddings = disposalBiddingDao.selectList(condition);
            for (TDisposalBidding tmp : tDisposalBiddings) {
                serviceMessageUtils.disposalDoneToProvider(tmp.getId());
            }
        }
    }

    @Override
    public PageInfo getDisposalActivityByAccountId(AcctReq.ViewRecordRequest viewRecordRequest) {

        PageInfo disposalActivitys = disposalRequirementDao.getDisposalActivityByAccountId(viewRecordRequest);
        List<DisposalServiceInvestResp> list = new LinkedList<>();
        setValueList(list, disposalActivitys.getList());

        disposalActivitys.setList(list);
        return disposalActivitys;
    }

}
