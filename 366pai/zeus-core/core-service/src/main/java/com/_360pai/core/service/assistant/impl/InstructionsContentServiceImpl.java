package com._360pai.core.service.assistant.impl;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.AnnouncementUtil;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.arch.common.utils.JsonUtil;
import com._360pai.arch.common.utils.NumberFormatUtils;
import com._360pai.core.common.constants.AppletEnum;
import com._360pai.core.common.constants.AssetEnum;
import com._360pai.core.common.constants.EnrollingEnum;
import com._360pai.core.common.constants.ServiceConfigEnum;
import com._360pai.core.condition.asset.AssetLeaseDataCondition;
import com._360pai.core.condition.assistant.InstructionsContentCondition;
import com._360pai.core.dao.account.TAgencyDao;
import com._360pai.core.dao.asset.AssetLeaseDataDao;
import com._360pai.core.dao.assistant.AreaDao;
import com._360pai.core.dao.assistant.CityDao;
import com._360pai.core.dao.assistant.InstructionsContentDao;
import com._360pai.core.dao.assistant.TownDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.account.resp.AccountBaseDto;
import com._360pai.core.facade.activity.req.InstructionsContentReq;
import com._360pai.core.facade.activity.vo.InstructionsContentListVo;
import com._360pai.core.model.account.TAgency;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.asset.Asset;
import com._360pai.core.model.asset.AssetLeaseData;
import com._360pai.core.model.assistant.InstructionsContent;
import com._360pai.core.model.assistant.TServiceConfig;
import com._360pai.core.service.account.AccountService;
import com._360pai.core.service.activity.AuctionActivityService;
import com._360pai.core.service.asset.AssetService;
import com._360pai.core.service.assistant.InstructionsContentService;
import com._360pai.core.service.assistant.TServiceConfigService;
import com._360pai.core.service.enrolling.EnrollingActivityService;
import com._360pai.core.vo.enrolling.web.EnrollingAnnouncementVO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class InstructionsContentServiceImpl implements InstructionsContentService {

    @Autowired
    private InstructionsContentDao instructionsContentDao;
    @Autowired
    private AuctionActivityService auctionActivityService;
    @Autowired
    private EnrollingActivityService enrollingActivityService;
    @Autowired
    private AssetService assetService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TServiceConfigService serviceConfigService;
    @Autowired
    private AssetLeaseDataDao assetLeaseDataDao;
    @Autowired
    private TownDao townDao;
    @Autowired
    private AreaDao areaDao;
    @Autowired
    private TAgencyDao agencyDao;


    @Override
    public PageInfoResp<InstructionsContentListVo> selectInstructionsContent(int page, int perPage) {
        PageHelper.startPage(page, perPage);
        PageInfo pageInfo = new PageInfo<>(instructionsContentDao.selectAll());
        List<InstructionsContentListVo> list = new ArrayList<>();
        List<InstructionsContent> instructionsContents = pageInfo.getList();
        for (InstructionsContent item : instructionsContents) {
            InstructionsContentListVo vo = new InstructionsContentListVo();
            BeanUtils.copyProperties(item, vo);
            list.add(vo);
        }
        PageInfoResp pageInfoResp = new PageInfoResp();
        pageInfoResp.setList(list);
        pageInfoResp.setHasNextPage(pageInfo.isHasNextPage());
        pageInfoResp.setTotal(pageInfo.getTotal());
        return pageInfoResp;
    }

    @Override
    public int addInstructionsContent(InstructionsContent params) {
        if (StringUtils.isBlank(params.getName())) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "标题不能为空");
        }
        InstructionsContent instructionsContentByName = findInstructionsContentByName(params);
        if (instructionsContentByName != null) {
            throw new BusinessException("标题已存在");
        }
        return instructionsContentDao.insert(params);
    }

    @Override
    public int editInstructionsContent(InstructionsContent params) {
        if (StringUtils.isBlank(params.getName())) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "标题不能为空");
        }
        InstructionsContent instructionsContentById = findInstructionsContentById(params);
        if (instructionsContentById == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "修改的公告不存在");
        }
        return instructionsContentDao.updateById(params);
    }

    @Override
    public int deleteInstructionsContent(InstructionsContent params) {
        InstructionsContent instructionsContentById = findInstructionsContentById(params);
        if (instructionsContentById == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "删除的公告不存在");
        }
        return instructionsContentDao.deleteInstructionsContent(params.getId());
    }

    @Override
    public String announcement(Integer activityId) {

        String commonTitle = "拍卖公告-通用模板";
        String bankTitle = "拍卖公告-银行模板";
        String downTitle = "拍卖公告-线下模板";
        String bankDownTitle = "拍卖公告-银行全线下模版";

        String leaseTitle = "拍卖公告-租赁权购买公告";


        AuctionActivity activity = auctionActivityService.getById(activityId);
        log.info("查询的活动信息为：{}", JSON.toJSONString(activity));
        Asset asset = assetService.getAsset(activity.getAssetId());
        log.info("查询的标的信息为：{}", JSON.toJSONString(asset));
        boolean bank;
        if (AssetEnum.ComeFrom.AGENCY.getKey().equals(asset.getComeFrom())) {
            bank = false;
        } else {
            AccountBaseDto accountBaseDto = accountService.getAccountBaseByPartyId(asset.getPartyId());
            log.info("查询委托人信息为：{}", JSON.toJSONString(accountBaseDto));
            bank = accountBaseDto.isBank();
        }
        String title;
        //校验线下和银行类
        Integer onlined = asset.getOnlined();
        if (onlined == 0) {
            if (asset.getBankOfflineFlag()) {
                title = bankDownTitle;
            } else {
                title = downTitle;
            }
        } else if("-1".equals(String.valueOf(activity.getCategoryId()))){
            title = leaseTitle;
        } else {
            if (bank) {
                title = bankTitle;
            } else {
                title = commonTitle;
            }
        }

        if("-1".equals(String.valueOf(activity.getCategoryId()))){
            title = leaseTitle;
        }

        log.info("查询模板的条件为：{}", title);
        InstructionsContentCondition contentCondition = new InstructionsContentCondition();
        contentCondition.setName(title);
        InstructionsContent instructionsContent = instructionsContentDao.selectOneResult(contentCondition);
        log.info("查询模板数据为：【】", JSON.toJSONString(instructionsContent));
        return instructionsContent.getContent();
    }

    @Override
    public String buyerMustKnow(Integer activityId) {

        String commonTitle = "拍卖竞买须知-通用模板";
        String bankTitle = "拍卖竞买须知-银行模板";
        String downTitle = "拍卖竞买须知-线下模板";
        String bankDownTitle = "拍卖竞买须知-银行全线下模板";
        String leaseTitle = "拍卖竞买须知-租赁权购买须知";

        AuctionActivity activity = auctionActivityService.getById(activityId);
        log.info("查询的活动信息为：{}", JSON.toJSONString(activity));
        Asset asset = assetService.getAsset(activity.getAssetId());
        log.info("查询的标的信息为：{}", JSON.toJSONString(asset));
        boolean bank;
        if (AssetEnum.ComeFrom.AGENCY.getKey().equals(asset.getComeFrom())) {
            bank = false;
        } else {
            AccountBaseDto accountBaseDto = accountService.getAccountBaseByPartyId(asset.getPartyId());
            log.info("查询委托人信息为：{}", JSON.toJSONString(accountBaseDto));
            bank = accountBaseDto.isBank();
        }
        String title;
        //校验线下和银行类
        Integer onlined = asset.getOnlined();
        if (onlined == 0) {
            if (asset.getBankOfflineFlag()) {
                title = bankDownTitle;
            } else {
                title = downTitle;
            }
        } else{
            if (bank) {
                title = bankTitle;
            } else {
                title = commonTitle;
            }
        }
        //判断是否为租赁权拍卖须知
        if("-1".equals(String.valueOf(activity.getCategoryId()))) {
            title = leaseTitle;
        }
        log.info("查询模板的条件为：{}", title);
        InstructionsContentCondition contentCondition = new InstructionsContentCondition();
        contentCondition.setName(title);
        InstructionsContent instructionsContent = instructionsContentDao.selectOneResult(contentCondition);
        log.info("查询模板数据为：【】", JSON.toJSONString(instructionsContent));
        return instructionsContent.getContent();
    }

    @Override
    public String disposalAnnouncement(Integer disposalId) {
        String title = "处置招标公告";
        InstructionsContentCondition contentCondition = new InstructionsContentCondition();
        contentCondition.setName(title);
        InstructionsContent instructionsContent = instructionsContentDao.selectOneResult(contentCondition);
        log.info("查询模板数据为：【】", JSON.toJSONString(instructionsContent));
        return instructionsContent.getContent();
    }

    @Override
    public String enrollingBuyerMustKnow(Integer activityId) {
        return enrollingBuyerMustKnow(activityId, null);
    }

    @Override
    public String enrollingBuyerMustKnow(Integer activityId, String agencyCode) {
        EnrollingAnnouncementVO activityModelInfo = enrollingActivityService.getActivityModelInfo(activityId.toString());
        setPublicUnionAgencyName(agencyCode, activityModelInfo);

        JSONObject json = JSONObject.parseObject(JSON.toJSONString(activityModelInfo));
        String title = "抵押物预招商须知";
        InstructionsContentCondition contentCondition = new InstructionsContentCondition();
        contentCondition.setName(title);
        InstructionsContent instructionsContent = instructionsContentDao.selectOneResult(contentCondition);
        String content = instructionsContent.getContent();
        log.info("查询模板数据为：【】", JSON.toJSONString(instructionsContent));
        json.put("previewAt", DateUtil.format(DateUtil.parse(activityModelInfo.getBeginAt(),
                DateUtil.NORM_DATETIME_PATTERN), DateUtil.NORM_DATE_PATTERN));
        return AnnouncementUtil.parse("{{", "}}", content, json);
    }


    @Override
    public String enrollingAnnouncement(Integer activityId) {
        return enrollingAnnouncement(activityId, null);
    }

    @Override
    public String enrollingAnnouncement(Integer activityId, String agencyCode) {
        EnrollingAnnouncementVO activityModelInfo = enrollingActivityService.getActivityModelInfo(activityId.toString());

        activityModelInfo.setWebName("上海百昌网络拍卖科技有限公司");
        if(EnrollingEnum.ENROLLING_THIRD_PARTY_STATUS.AGENCY.getType()==activityModelInfo.getThirdPartyStatus()){
            activityModelInfo.setWebName(activityModelInfo.getUserAgencyName());
        }
        setPublicUnionAgencyName(agencyCode, activityModelInfo);

        JSONObject json = JSONObject.parseObject(JSON.toJSONString(activityModelInfo));
        String title = "抵押物预招商公告";
        InstructionsContentCondition contentCondition = new InstructionsContentCondition();
        contentCondition.setName(title);
        InstructionsContent instructionsContent = instructionsContentDao.selectOneResult(contentCondition);
        log.info("查询模板数据为：【】", JSON.toJSONString(instructionsContent));
        String content = instructionsContent.getContent();
        json.put("previewAt", DateUtil.format(DateUtil.parse(activityModelInfo.getBeginAt(),
                DateUtil.NORM_DATETIME_PATTERN), DateUtil.NORM_DATE_PATTERN));

        return AnnouncementUtil.parse("{{", "}}", content, json);
    }

    @Override
    public String realAnnouncement(Integer activityId) {
        return realAnnouncement(activityId, null);
    }

    @Override
    public String realAnnouncement(Integer activityId, String agencyCode) {
        EnrollingAnnouncementVO activityModelInfo = enrollingActivityService.getActivityModelInfo(activityId.toString());
        activityModelInfo.setWebName("上海百昌网络拍卖科技有限公司");
        if(EnrollingEnum.ENROLLING_THIRD_PARTY_STATUS.AGENCY.getType()==activityModelInfo.getThirdPartyStatus()){
            activityModelInfo.setWebName(activityModelInfo.getUserAgencyName());
        }
        setPublicUnionAgencyName(agencyCode, activityModelInfo);

        JSONObject json = JSONObject.parseObject(JSON.toJSONString(activityModelInfo));
        String title = "物权招商公告";
        InstructionsContentCondition contentCondition = new InstructionsContentCondition();
        contentCondition.setName(title);
        InstructionsContent instructionsContent = instructionsContentDao.selectOneResult(contentCondition);
        log.info("查询模板数据为：【】", JSON.toJSONString(instructionsContent));
        String content = instructionsContent.getContent();
        json.put("previewAt", DateUtil.format(DateUtil.parse(activityModelInfo.getBeginAt(),
                DateUtil.NORM_DATETIME_PATTERN), DateUtil.NORM_DATE_PATTERN));
        return AnnouncementUtil.parse("{{", "}}", content, json);
    }

    @Override
    public String obligatoryAnnouncement(Integer activityId) {
        return obligatoryAnnouncement(activityId, null);
    }

    @Override
    public String obligatoryAnnouncement(Integer activityId, String agencyCode) {
        EnrollingAnnouncementVO activityModelInfo = enrollingActivityService.getActivityModelInfo(activityId.toString());
        activityModelInfo.setWebName("上海百昌网络拍卖科技有限公司");

        if(EnrollingEnum.ENROLLING_THIRD_PARTY_STATUS.AGENCY.getType()==activityModelInfo.getThirdPartyStatus()){
            activityModelInfo.setWebName(activityModelInfo.getUserAgencyName());
        }
        if(EnrollingEnum.ENROLLING_THIRD_PARTY_STATUS.AGENCY.getType()==activityModelInfo.getThirdPartyStatus()){
            activityModelInfo.setWebName(activityModelInfo.getUserAgencyName());
        }
        setPublicUnionAgencyName(agencyCode, activityModelInfo);

        JSONObject json = JSONObject.parseObject(JSON.toJSONString(activityModelInfo));
        String title = "债权招商公告";
        InstructionsContentCondition contentCondition = new InstructionsContentCondition();
        contentCondition.setName(title);
        InstructionsContent instructionsContent = instructionsContentDao.selectOneResult(contentCondition);
        log.info("查询模板数据为：【】", JSON.toJSONString(instructionsContent));
        String content = instructionsContent.getContent();
        json.put("previewAt", DateUtil.format(DateUtil.parse(activityModelInfo.getBeginAt(),
                DateUtil.NORM_DATETIME_PATTERN), DateUtil.NORM_DATE_PATTERN));
        return AnnouncementUtil.parse("{{", "}}", content, json);
    }

    private void setPublicUnionAgencyName(String agencyCode, EnrollingAnnouncementVO activityModelInfo) {
        if (StringUtils.isNotBlank(agencyCode)) {
            TAgency unionAgency = agencyDao.getByCode(agencyCode);
            if (unionAgency != null) {
                activityModelInfo.setUnionAgencyName(unionAgency.getName());
            }
        } else {
            activityModelInfo.setUnionAgencyName("360PAI.COM全网联拍共享拍卖平台");
        }

        if(StringUtils.isNotBlank(activityModelInfo.getAgencyName())) {
            if(activityModelInfo.getAgencyName().equals(activityModelInfo.getUnionAgencyName())) {
                activityModelInfo.setAgencyName("");
            }
        }else {
            activityModelInfo.setAgencyName("");
        }

    }

    @Override
    public ResponseModel getAppletAgreement(String type) {
        Map<String, Object> data = new HashMap<>();
        String name = AppletEnum.AppletAgreementType.getValueByKey(type);
        if (StringUtils.isNotEmpty(name)) {
            InstructionsContent instructionsContent = instructionsContentDao.getAppletAgreement(name);
            if (instructionsContent != null) {
                data.put("name", instructionsContent.getName());
                String content = instructionsContent.getContent();
                if (AppletEnum.AppletAgreementType.OPEN_SHOP.getKey().equals(type)) {
                    JSONObject json = new JSONObject();
                    TServiceConfig serviceConfig = serviceConfigService.selectByConfigType(ServiceConfigEnum.OPEN_SHOP_PRICE);
                    if (serviceConfig != null) {
                        json.put("amount", serviceConfig.getConfigValue());
                    } else {
                        json.put("amount", ServiceConfigEnum.OPEN_SHOP_PRICE.getValue());
                    }
                    content = AnnouncementUtil.parse("{{", "}}", content, json);
                }

                data.put("content", content);
            }
        }
        return ResponseModel.succ(data);
    }

    @Override
    public Object getLeaseAnnouncement(Integer assetId) {
        AssetLeaseDataCondition assetLeaseDataCondition = new AssetLeaseDataCondition();
        assetLeaseDataCondition.setAssetId(assetId);
        assetLeaseDataCondition.setDeleteFlag(false);
        AssetLeaseData assetLeaseData = assetLeaseDataDao.selectOneResult(assetLeaseDataCondition);

        JSONObject json = new JSONObject();
        json.put("beginAt", assetLeaseData.getBeginAt());
        json.put("endAt", assetLeaseData.getEndAt());
        json.put("years", DateUtil.yearCompare(assetLeaseData.getLeaseStartTime(), assetLeaseData.getLeaseEndTime()));
        json.put("leaseArea", assetLeaseData.getLeaseArea());


        JSONObject jsonObject = assetLeaseData.getAssetLeaseArea().getJSONArray("areaArray").getJSONObject(0);
        if(json != null) {
            String provinceName = jsonObject.getString("provinceName") ;
            String cityName = jsonObject.getString("name") == null ? "" : jsonObject.getString("name");
            String areaName = jsonObject.getString("areaName") == null ? "" : jsonObject.getString("areaName");
            String townName = jsonObject.getString("townName") == null ? "" : jsonObject.getString("townName");
            json.put("area", provinceName + " " + cityName + " " + areaName + " " + townName);
         }

        json.put("leaseType", AssetEnum.LeaseAssetType.getValueByKey(assetLeaseData.getLeaseType()));
        json.put("businessAgreement", assetLeaseData.getBusinessAgreement());
        json.put("deposit", NumberFormatUtils.getFormatTenThousand(assetLeaseData.getDeposit()));
        json.put("startingPrice", NumberFormatUtils.getFormatTenThousand(assetLeaseData.getStartingPrice()));
        json.put("monthPrice", NumberFormatUtils.getFormatMonth(assetLeaseData.getStartingPrice()));
        json.put("annualIncrementRate", assetLeaseData.getAnnualIncrementRate());
        json.put("applyEndTime", assetLeaseData.getApplyEndTime());
        json.put("approvalEndTime", assetLeaseData.getApprovalEndTime());
        json.put("lessorContactNumber", assetLeaseData.getLessorContactNumber());
        JSONArray recruitmentDocument = assetLeaseData.getExtra().getJSONArray("recruitmentDocument");
        json.put("fileUrl", recruitmentDocument.getJSONObject(0).getString("fileUrl"));
        json.put("fileName", recruitmentDocument.getJSONObject(0).getString("fileName"));
        json.put("previewAt", DateUtil.formatNormDate(assetLeaseData.getPreviewAt()));



        return json;
    }

    @Override
    public Object getLeaseBuyerMustKnow(InstructionsContentReq req) {
        AssetLeaseDataCondition assetLeaseDataCondition = new AssetLeaseDataCondition();
        assetLeaseDataCondition.setAssetId(Integer.parseInt(req.getAssetId()));
        assetLeaseDataCondition.setDeleteFlag(false);
        AssetLeaseData assetLeaseData = assetLeaseDataDao.selectOneResult(assetLeaseDataCondition);


        JSONObject json = new JSONObject();
        json.put("payDays", assetLeaseData.getPayDays());
        json.put("name", assetLeaseData.getName());
        json.put("leaseArea", assetLeaseData.getLeaseArea());
        json.put("years", DateUtil.yearCompare(assetLeaseData.getLeaseStartTime(), assetLeaseData.getLeaseEndTime()));
        json.put("startingPrice", assetLeaseData.getStartingPrice());
        json.put("monthPrice", NumberFormatUtils.getFormatMonth(assetLeaseData.getStartingPrice()));
        json.put("annualIncrementRate", assetLeaseData.getAnnualIncrementRate());
        json.put("currentDesc", assetLeaseData.getCurrentDesc());
        json.put("businessAgreement", assetLeaseData.getBusinessAgreement());
        json.put("previewAt", DateUtil.formatNormDate(assetLeaseData.getPreviewAt()));
        json.put("paymentCycle", AssetEnum.PaymentCycle.getShowValueByKey(assetLeaseData.getPaymentCycle()));

        return json;
    }

    @Override
    public InstructionsContent findInstructionsContentByName(InstructionsContent params) {
        InstructionsContentCondition contentCondition = new InstructionsContentCondition();
        contentCondition.setName(params.getName());
        return instructionsContentDao.selectOneResult(contentCondition);
    }

    @Override
    public InstructionsContent findInstructionsContentById(InstructionsContent params) {
        InstructionsContentCondition contentCondition = new InstructionsContentCondition();
        contentCondition.setId(params.getId());
        return instructionsContentDao.selectOneResult(contentCondition);
    }

}