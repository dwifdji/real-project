package com._360pai.core.provider.applet;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.arch.core.sysconfig.properties.GatewayProperties;
import com._360pai.core.common.constants.EnrollingEnum;
import com._360pai.core.condition.applet.TAppletEnrollingMapCondition;
import com._360pai.core.condition.enrolling.FavoriteEnrollingActivityCondition;
import com._360pai.core.dao.applet.TAppletEnrollingMapDao;
import com._360pai.core.dao.enrolling.FavoriteEnrollingActivityDao;
import com._360pai.core.dto.enrolling.ActivityIdReqDto;
import com._360pai.core.facade.enrolling.EnrollingAppletFacade;
import com._360pai.core.facade.enrolling.req.EnrollingReq;
import com._360pai.core.facade.shop.req.ShopEnrollingReq;
import com._360pai.core.model.enrolling.EnrollingActivity;
import com._360pai.core.model.enrolling.EnrollingDeposit;
import com._360pai.core.model.enrolling.FavoriteEnrollingActivity;
import com._360pai.core.model.enrolling.NotifyPartyEnrollingActivity;
import com._360pai.core.service.assistant.CityService;
import com._360pai.core.service.assistant.DetailViewRecodeService;
import com._360pai.core.service.assistant.InstructionsContentService;
import com._360pai.core.service.enrolling.EnrollingActivityService;
import com._360pai.core.service.enrolling.EnrollingDepositService;
import com._360pai.core.service.enrolling.FavoriteEnrollingActivityService;
import com._360pai.core.service.enrolling.NotifyPartyEnrollingActivityService;
import com._360pai.core.vo.applet.EnrollingAppletAnnouncementVo;
import com._360pai.core.vo.applet.EnrollingAppletApplyVo;
import com._360pai.core.vo.applet.EnrollingAppletDetailVo;
import com._360pai.core.vo.assistant.ProvinceCityVo;
import com._360pai.core.vo.enrolling.EnrollingDepositListVo;
import com._360pai.core.vo.enrolling.web.EnrollingDetailInfoVo;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
@Service(version = "1.0.0")
@Slf4j
public class AppletEnrollingProvider implements EnrollingAppletFacade {

    @Autowired
    private EnrollingActivityService enrollingActivityService;

    @Autowired
    private EnrollingDepositService enrollingDepositService;


    @Autowired
    private InstructionsContentService instructionsContentService;

    @Autowired
    private NotifyPartyEnrollingActivityService notifyPartyEnrollingActivityService;


    @Autowired
    private FavoriteEnrollingActivityService favoriteEnrollingActivityService;


    @Autowired
    private TAppletEnrollingMapDao appletEnrollingMapDao;


    @Autowired
    private GatewayProperties gatewayProperties;

    @Autowired
    private CityService cityService;



    @Autowired
    private FavoriteEnrollingActivityDao favoriteEnrollingActivityDao;

    @Autowired
    private DetailViewRecodeService detailViewRecodeService;

    @Override
    public ResponseModel getEnrollingDetail(ShopEnrollingReq.comReq req) {
        //添加浏览数
        //当type = 4 时 为前端轮询时 浏览量不添加
        if(!"4".equals(req.getType())){
            updateBrowseNumber(req);
            detailViewRecodeService.insertEnrollingAppletRecode(Integer.valueOf(req.getActivityId()),req.getAccountId(),req.getPartyId());
        }

        EnrollingDetailInfoVo base = enrollingActivityService.getEnrollingActivityBaseInfo(req.getActivityId());

        if(base==null){
            return ResponseModel.fail("未找到详情信息！");
        }
        EnrollingAppletDetailVo vo = new EnrollingAppletDetailVo();
        vo.setId(base.getId());
        vo.setName(base.getName());
        vo.setAmount(base.getRefPrice());
        vo.setBrowseNumber(base.getBrowse());
        vo.setApplyNumber(base.getApply());
        vo.setFocusNumber(base.getFocus());
        vo.setReminderNumber(base.getRemind());
        vo.setImage(base.getExtra());
        vo.setDeposit(base.getDeposit());
        vo.setCode(base.getCode());
        vo.setBeginAt(base.getBeginAt());
        vo.setEndAt(base.getEndAt());
        vo.setSource(base.getSource());
        vo.setPhone(StringUtils.isEmpty(base.getProjectPhone())?"400-015-0005":base.getProjectPhone());
        vo.setReleaseAt(DateUtil.format(DateUtil.strToDateLong(base.getBeginAt()),DateUtil.NORM_DATE_PATTERN));
        vo.setStatusDesc(EnrollingEnum.STATUS.getDesc(base.getStatus()));
        vo.setStatus(base.getStatus());
        vo.setCityName(getCityInfo(base.getCityId(),base.getCityName()));
        vo.setType(base.getType());
        vo.setTypeDesc(EnrollingEnum.ENROLLING_TYPE.getDesc(base.getType()));
        vo.setAgencyName(base.getAgencyName());
        vo.setBeginTimestamp(DateUtil.strToDateLong(vo.getBeginAt()).getTime());
        vo.setEndTimestamp(DateUtil.strToDateLong(vo.getEndAt()).getTime());
        vo.setCurrentTimestamp(DateUtil.getSysTime().getTime());
        vo.setDetail(base.getDetail());
        vo =setFlagInfo(vo,req);
        vo.setHasExist(getHasExistInfo(req));
        vo.setCompeteUrl(EnrollingEnum.ENROLLING_TYPE.MORTGAGED_PROPERTY.getType().equals(base.getType())?gatewayProperties.getAppletEnrollingMerchantUrl():gatewayProperties.getAppletEnrollingCompeteUrl());

        return ResponseModel.succ(vo);
    }

    private String getCityInfo(String cityId,String cityName) {

        String strCity="";

        List<String> ids = getCityIds(cityId);
        if(ids==null||ids.size()<1){
            return cityName;
        }
        //获取省份城市信息
        List<ProvinceCityVo> cityVoList = cityService.getProvinceCityList(ids);

        for(ProvinceCityVo city :cityVoList){

            strCity = strCity+city.getProvinceName()+" "+city.getCityName()+"、";
        }

        if(StringUtils.isEmpty(strCity)){
            return cityName;
        }


        return strCity.substring(0,strCity.length()-1);
    }


    private List<String> getCityIds(String cityId) {

        try{

            List list = Arrays.asList(cityId.split(","));

            return list;

        }catch (Exception e){

            return null;
        }

    }


    private void updateBrowseNumber(ShopEnrollingReq.comReq req) {
        // 浏览量加一
        EnrollingActivity enrollingActivity = enrollingActivityService.getEnrollingActivityById(req.getActivityId());
        if (enrollingActivity == null) {
            return;
        }
        enrollingActivity.setId(Integer.valueOf(req.getActivityId()));
        Integer browseNumber = enrollingActivity.getBrowseNumber() == null ? 0 : Integer.valueOf(enrollingActivity.getBrowseNumber());
        enrollingActivity.setBrowseNumber(browseNumber + 1);
        enrollingActivityService.updateEnrollingActivityById(enrollingActivity);

    }

    private Boolean getHasExistInfo(ShopEnrollingReq.comReq req) {

        TAppletEnrollingMapCondition condition = new TAppletEnrollingMapCondition();
        condition.setShopId(req.getShopId());
        condition.setEnrollingId(Integer.valueOf(req.getActivityId()));
        condition.setIsDel("0");

        return appletEnrollingMapDao.selectFirst(condition)!=null;


     }

    private EnrollingAppletDetailVo setFlagInfo(EnrollingAppletDetailVo vo, ShopEnrollingReq.comReq comReq) {
        vo.setFocusFlag(false);
        vo.setApplyFlag(false);
        vo.setReminderFlag(false);

        EnrollingReq.activityIdTypeReq req = new EnrollingReq.activityIdTypeReq();

        req.setAccountId(comReq.getAccountId()==null?null:String.valueOf(comReq.getAccountId()));
        req.setActivityId(comReq.getActivityId());
        req.setPartyId(comReq.getPartyId()==null?null:String.valueOf(comReq.getPartyId()));
        req.setFoucsType(EnrollingEnum.FOCUS_TYPE.APPLET.getType());
        req.setResourceId(StringUtils.isEmpty(comReq.getShopId())?null:Integer.valueOf(comReq.getShopId()));
        //获取关注情况
        FavoriteEnrollingActivity favoriteEnrollingActivity = favoriteEnrollingActivityService.getFilterModel(req);
        if(null != favoriteEnrollingActivity){
            vo.setFocusFlag(true);

        }

        //获取通知情况
        NotifyPartyEnrollingActivity notifyPartyEnrollingActivity = notifyPartyEnrollingActivityService.getFilterModel(req);
        if(null != notifyPartyEnrollingActivity){
            vo.setReminderFlag(true);
        }

        if (StringUtils.isEmpty(req.getAccountId()) || StringUtils.isEmpty(req.getPartyId())) {

            return vo;
        }
        //获取报名情况
        EnrollingDeposit enrollingDepositModel = enrollingDepositService.getFilterModel(req);

        if(null != enrollingDepositModel){
            vo.setApplyFlag(true);

        }
        return vo;
    }

    @Override
    public ResponseModel getEnrollingApplyList(ShopEnrollingReq.comReq req) {

        ActivityIdReqDto dto = new ActivityIdReqDto();
        dto.setActivityId(req.getActivityId());
        dto.setPage(req.getPage());
        dto.setPerPage(req.getPerPage());

        PageInfo info = enrollingDepositService.getEnrollingDepositList(dto);
        List<EnrollingDepositListVo> list =info.getList();

        PageInfoResp page   = new PageInfoResp<>();
        page.setList(getList(list));
        page.setTotal(info.getTotal());
        page.setHasNextPage(info.isHasNextPage());

        return ResponseModel.succ(page);
    }

    private List getList(List<EnrollingDepositListVo> list) {
        List<EnrollingAppletApplyVo> applyList = new ArrayList();
        int sequence= 1;
        for(EnrollingDepositListVo vo : list){
            EnrollingAppletApplyVo appletApplyVo = new EnrollingAppletApplyVo();
            appletApplyVo.setName(vo.getShowName());
            appletApplyVo.setCreateAt(vo.getCreatedAt());
            appletApplyVo.setSequence(String.valueOf(sequence));
            sequence++;
            applyList.add(appletApplyVo);

        }

        return applyList;
    }

    @Override
    public ResponseModel getEnrollingAnnouncement(ShopEnrollingReq.comReq req) {


        //获取招商信息
        EnrollingActivity activity = enrollingActivityService.getEnrollingActivityById(req.getActivityId());
        if(activity==null){
            return ResponseModel.fail(ApiCallResult.DATA_IS_EMPTY);
        }
        EnrollingAppletAnnouncementVo vo = new EnrollingAppletAnnouncementVo();

        //抵押物预招商
        if(EnrollingEnum.ENROLLING_TYPE.MORTGAGED_PROPERTY.getType().equals(activity.getType())){
            vo.setAnnouncement(replaceStr(instructionsContentService.enrollingAnnouncement(activity.getId())));
            vo.setInform(replaceStr(instructionsContentService.enrollingBuyerMustKnow(activity.getId())));
        }else if(EnrollingEnum.ENROLLING_TYPE.PROPERTY_RIGHTS.getType().equals(activity.getType())){
            vo.setAnnouncement(replaceStr(instructionsContentService.obligatoryAnnouncement(activity.getId())));

        }else if(EnrollingEnum.ENROLLING_TYPE.CREDITOR_RIGHTS.getType().equals(activity.getType())){
            vo.setAnnouncement(replaceStr(instructionsContentService.realAnnouncement(activity.getId())));

        }


        return ResponseModel.succ(vo);
    }

    private String replaceStr(String s) {

        return  s.replaceAll("font-size: \\d*px;", "font-size: 16px;");

    }


    @Override
    public ResponseModel getEnrollingConcernedFlag(ShopEnrollingReq.comReq req) {


        Map<String,String> focusFlag = new HashMap<>();


        focusFlag.put("concernedFlag",getFocusFlag(req));



        return ResponseModel.succ(focusFlag);
    }

    private String getFocusFlag(ShopEnrollingReq.comReq req) {

        FavoriteEnrollingActivityCondition condition = new FavoriteEnrollingActivityCondition();

        condition.setResourceId(Integer.valueOf(req.getShopId()));
        condition.setActivityId(Integer.valueOf(req.getActivityId()));

        return  favoriteEnrollingActivityDao.selectFirst(condition)==null?"0":"1";
    }
}
