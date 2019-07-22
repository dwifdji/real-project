package com._360pai.core.service.enrolling.impl;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.arch.common.utils.NumberFormatUtils;
import com._360pai.arch.common.utils.RandomNumberGenerator;
import com._360pai.arch.core.sysconfig.properties.GatewayProperties;
import com._360pai.core.common.constants.ActivityServiceProviderEnum;
import com._360pai.core.common.constants.EnrollingEnum;
import com._360pai.core.condition.assistant.StaffCondition;
import com._360pai.core.condition.enrolling.EnrollingActivityImportRealDataCondition;
import com._360pai.core.dao.account.TDisposeProviderDao;
import com._360pai.core.dao.assistant.*;
import com._360pai.core.dao.enrolling.EnrollingActivityDao;
import com._360pai.core.dao.enrolling.EnrollingActivityImportDataDao;
import com._360pai.core.dao.enrolling.EnrollingActivityImportRealDataDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.enrolling.req.EnrollingActivityImportDataListReq;
import com._360pai.core.facade.enrolling.req.EnrollingActivityImportDataListVo;
import com._360pai.core.facade.enrolling.req.EnrollingActivityImportRealDataVo;
import com._360pai.core.facade.enrolling.req.EnrollingImportReq;
import com._360pai.core.model.account.TDisposeProvider;
import com._360pai.core.model.assistant.Staff;
import com._360pai.core.model.assistant.TActivityServiceProvider;
import com._360pai.core.model.enrolling.EnrollingActivity;
import com._360pai.core.model.enrolling.EnrollingActivityImportRealData;
import com._360pai.core.service.account.DisposeService;
import com._360pai.core.service.assistant.AssistantService;
import com._360pai.core.service.enrolling.EnrollingActivityRealImportService;
import com._360pai.core.vo.assistant.ProvinceCityVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class EnrollingActivityRealImportServiceImpl implements EnrollingActivityRealImportService {

	@Autowired
	private EnrollingActivityImportDataDao enrollingActivityImportDataDao;


    @Autowired
    private EnrollingActivityImportRealDataDao enrollingActivityImportRealDataDao;


	@Autowired
	private EnrollingActivityDao enrollingActivityDao;


    @Autowired
    private CityDao cityDao;

    @Autowired
    private AreaDao areaDao;


    @Autowired
    private ProvinceDao provinceDao;


    @Autowired
    private StaffDao staffDao;


    @Autowired
    private GatewayProperties gatewayProperties;

    @Resource
    public StringRedisTemplate stringRedisTemplate;
    @Autowired
    private TActivityServiceProviderDao activityServiceProviderDao;
    @Autowired
    private DisposeService disposeService;
    @Autowired
    private TDisposeProviderDao disposeProviderDao;
    @Autowired
    private AssistantService assistantService;


    @Override
    public PageInfo getUploadRealActivityList(EnrollingImportReq.getUploadActivityListReq req) {
        //审核列表看看 当前登录人是否能看到全部的信息
        if("1".equals(req.getType())||("2".equals(req.getType())&&checkOperator(req))){
            req.setOperatorId(null);
        }

        PageHelper.startPage(req.getPage(), req.getPerPage());

        EnrollingActivityImportDataListReq dataListReq = new EnrollingActivityImportDataListReq();

        dataListReq.setStatus("1".equals(req.getType())?EnrollingEnum.STATUS.IMPORT.getType():EnrollingEnum.STATUS.AGENCY_APPROVED.getType());
        dataListReq.setOperatorId(req.getOperatorId());
        dataListReq.setUserName(req.getUserName());
        dataListReq.setName(req.getName());
        dataListReq.setContactPerson(req.getContactPerson());


        List<EnrollingActivityImportDataListVo> listVoList = enrollingActivityImportRealDataDao.getRealImportDateList(dataListReq);

        return new PageInfo<>(listVoList);
    }

    private boolean checkOperator(EnrollingImportReq.getUploadActivityListReq req) {

        try{
            String partyIds = gatewayProperties.getEnrollingImportAuditAll();

            String[] ids = partyIds.split(",");

            for(int i=0;i<ids.length;i++){

                if(ids[i].equals(String.valueOf(req.getOperatorId()))){
                    return true;
                }
            }

        }catch (Exception e){
            log.error("获取能查看全部审核信息staffId异常，异常信息为{}",e);
        }
        return false;


     }

    @Override
    @Transactional
    public ResponseModel uploadActivity(List<EnrollingActivityImportRealDataVo> activityList) {

        //分批处理
        if(null!=activityList&&activityList.size()>0){

            List<ProvinceCityVo> cityVos = cityDao.getAllProvinceCityAreaList();

            StaffCondition condition = new StaffCondition();
            condition.setStatus("1");

            List<Staff> staff = staffDao.selectList(condition);

            int pointsDataLimit = 1000;//限制条数
            Integer size = activityList.size();
            //判断是否有必要分批
            if(pointsDataLimit<size){
                int part = size/pointsDataLimit;//分批数
                 for (int i = 0; i < part; i++) {
                    //1000条
                    List<EnrollingActivityImportRealDataVo> listPage = activityList.subList(0, pointsDataLimit);

                    batchSave(listPage,cityVos,staff);

                    activityList.subList(0, pointsDataLimit).clear();
                }
                if(!activityList.isEmpty()){

                    batchSave(activityList,cityVos,staff);

                }
             }else{

                batchSave(activityList,cityVos,staff);

              }

        }

        return ResponseModel.succ();
    }


    private void batchSave(List<EnrollingActivityImportRealDataVo> dataList,List<ProvinceCityVo> cityVos,List<Staff> staff) {
        List<EnrollingActivity> list = new ArrayList<>();

        int count=0;
        for(EnrollingActivityImportRealDataVo vo :dataList){
            vo.setPawnProId(getProvinceId(vo.getPawnPro(),cityVos));
            vo.setPawnCityId(getCityId(vo.getPawnCity(),cityVos));
            vo.setPawnAreaId(getAreaId(vo.getPawnArea(),cityVos));
            EnrollingActivity enrollingActivity = new EnrollingActivity();
            enrollingActivity.setDeleteFlag(false);
            enrollingActivity.setCreatedAt(DateUtil.getSysTime());
            enrollingActivity.setPartyId(Integer.valueOf(vo.getPartyId()));
            enrollingActivity.setVisibilityLevel(EnrollingEnum.VISIBILITY_LEVEL.PLATFORM_DEFAULT.getType());
            enrollingActivity.setCode(RandomNumberGenerator.generatorMarkEnrollingCode("WQZS-",4)+String.valueOf(count));
            enrollingActivity.setReminderNumber(0);
            enrollingActivity.setFocusNumber(0);
            enrollingActivity.setBrowseNumber(0);
            enrollingActivity.setApplyNumber(0);
            enrollingActivity.setName(vo.getName());
            enrollingActivity.setContactPhone(vo.getContactPhone());
            enrollingActivity.setContactName(vo.getContactName());
            enrollingActivity.setType("3");//物权招商
            enrollingActivity.setProvinceId(vo.getPawnProId());
            enrollingActivity.setCityId(vo.getPawnCityId());
            enrollingActivity.setAreaId(vo.getPawnAreaId());
            enrollingActivity.setCityName(vo.getPawnCity());
            enrollingActivity.setRefPrice(StringUtils.isNotEmpty(vo.getRefPrice())?new BigDecimal(vo.getRefPrice()):null);
            enrollingActivity.setThirdPartyStatus(EnrollingEnum.ENROLLING_THIRD_PARTY_STATUS.IMPORT_REAL.getType());
            enrollingActivity.setStatus(EnrollingEnum.STATUS.IMPORT.getType());
            enrollingActivity.setOptions(getOperId(vo.getContactName(),staff));
            enrollingActivity.setGuarantee("是".equals(vo.getIfMortgage())?1:0);
            enrollingActivity.setBusTypeName(StringUtils.isEmpty(vo.getBusTypeName())?null:vo.getBusTypeName().replaceAll("、",","));
            enrollingActivity.setDisposalService(vo.getDisposalService());
            enrollingActivity.setFundProvider(vo.getFundProvider());
            list.add(enrollingActivity);
            count++;
        }


        //批量添加activity表
        enrollingActivityImportDataDao.batchSaveEnrollingActivity(list);


        for(int i=0;i<list.size();i++){
            EnrollingActivity activity = list.get(i);
            dataList.get(i).setActivityId(activity.getId());
            dataList.get(i).setDeleteFlag(false);

        }
        enrollingActivityImportRealDataDao.batchSaveRealImportData(dataList);

    }

    private String getOperId(String contactPerson,List<Staff> staff) {
        //获取操作人id
        for(Staff s: staff){

            if(StringUtils.isNotEmpty(s.getName())&&s.getName().equals(contactPerson)){
                return String.valueOf(s.getId());
            }

        }


        return null;
    }

    private String getAreaId(String debtorArea,List<ProvinceCityVo> cityVos) {
        if(StringUtils.isEmpty(debtorArea)){
            return null;
        }

        for(ProvinceCityVo provinceCityVo :cityVos){
            if (StringUtils.isEmpty(provinceCityVo.getAreaName())) {
                continue;
            }
            if(provinceCityVo.getAreaName().contains(debtorArea)){
                return String.valueOf(provinceCityVo.getAreaId());

            }
        }

        return null;
    }

    private String getCityId(String debtorCity,List<ProvinceCityVo> cityVos) {
        if(StringUtils.isEmpty(debtorCity)){
            return null;
        }

        for(ProvinceCityVo provinceCityVo :cityVos){
            if (StringUtils.isEmpty(provinceCityVo.getCityName())) {
                continue;
            }
            if(provinceCityVo.getCityName().contains(debtorCity)){
                return String.valueOf(provinceCityVo.getCityId());

            }
        }

        return null;
    }

    private String getProvinceId(String debtorPro,List<ProvinceCityVo> cityVos) {

        if(StringUtils.isEmpty(debtorPro)){
            return null;
        }

        for(ProvinceCityVo provinceCityVo :cityVos){
            if(provinceCityVo.getProvinceName().contains(debtorPro)){

                return String.valueOf(provinceCityVo.getProvinceId());

             }

        }


        return  null;
    }

    @Override
    public ResponseModel getRealUploadActivityDetails(EnrollingImportReq.getUploadActivityDetailsReq req) {
        EnrollingActivityImportRealDataCondition condition = new EnrollingActivityImportRealDataCondition();
        condition.setActivityId(Integer.valueOf(req.getActivityId()));
        condition.setDeleteFlag(false);
        EnrollingActivityImportRealData data = enrollingActivityImportRealDataDao.selectFirst(condition);
        data.setPawnLocation(getLocation(data));
        data.setRefPrice(NumberFormatUtils.getFormatNumStr(data.getRefPrice(),2));
        if("web".equals(req.getType())){
            data.setRefPrice(StringUtils.isEmpty(data.getRefPrice())?"暂无":data.getRefPrice()+"元");
            if(StringUtils.isNotEmpty(data.getFundProvider())){
                data.setFundProvider(gatewayProperties.getEnrollingImportFundProvider());
                data.setFundPhone(gatewayProperties.getEnrollingImportFundPhone());
            }
        }

        return ResponseModel.succ(data);
    }

    private String getLocation(EnrollingActivityImportRealData data) {


        return data.getPawnPro()+data.getPawnCity();
    }


    @Override
    public ResponseModel updateRealActivity(EnrollingActivity req) {


        enrollingActivityDao.updateById(req);

        return ResponseModel.succ();
    }

    @Override
    @Transactional
    public ResponseModel updateRealImportActivity(EnrollingImportReq.updateRealActivityReq req) {

        EnrollingActivity enrollingActivity =  enrollingActivityDao.selectById(req.getActivityId());
        if(enrollingActivity==null){
            return ResponseModel.fail(ApiCallResult.DATA_IS_EMPTY);
        }


        EnrollingActivityImportRealData data = new EnrollingActivityImportRealData();

        BeanUtils.copyProperties(req,data);

        data.setPawnPro(getProvinceName(data.getPawnProId()));
        data.setPawnCity(getCityName(data.getPawnCityId()));
        data.setPawnArea(getAreaName(data.getPawnAreaId()));


        data.setPawnPro(getProvinceName(data.getPawnProId()));
        data.setPawnCity(getCityName(data.getPawnCityId()));
        data.setPawnArea(getAreaName(data.getPawnAreaId()));
        data.setRefPrice(getStrRefPrice(data.getRefPrice()));
        enrollingActivityImportRealDataDao.updateById(data);
        StaffCondition condition = new StaffCondition();
        condition.setStatus("1");
        List<Staff> staff = staffDao.selectList(condition);
        enrollingActivity.setName(data.getName());
        enrollingActivity.setRefPrice(getRefPriceInfo(data));
        //为空时特殊处理
        if(enrollingActivity.getRefPrice()==null){
            enrollingActivity.setRealRefPrice("1");
        }
        enrollingActivity.setProvinceId(data.getPawnProId());
        enrollingActivity.setCityId(data.getPawnCityId());
        enrollingActivity.setCityName(data.getPawnCity());
        enrollingActivity.setAreaId(data.getPawnAreaId());
        enrollingActivity.setBusTypeName(StringUtils.isEmpty(data.getBusTypeName())?"":data.getBusTypeName().replaceAll("、",","));
        enrollingActivity.setOptions(getOperId(data.getContactName(),staff));
        enrollingActivity.setFundProvider(data.getFundProvider());
        enrollingActivity.setDisposalService(data.getDisposalService());
        enrollingActivity.setBeginAt(data.getBeginAt());
        enrollingActivity.setEndAt(data.getEndAt());
        enrollingActivity.setGuarantee("是".equals(data.getIfMortgage())?1:0);
        enrollingActivity.setContactName(data.getContactName());
        enrollingActivity.setContactPhone(data.getContactPhone());
        enrollingActivityDao.updateById(enrollingActivity);
        return ResponseModel.succ();
    }

    private String getStrRefPrice(String refPrice) {

        if(StringUtils.isEmpty(refPrice)||"暂无".equals(refPrice)||"元".equals(refPrice)){

            return "";
        }

        return refPrice.replace("元","");

    }

    private BigDecimal getRefPriceInfo(EnrollingActivityImportRealData data) {

        if(StringUtils.isEmpty(data.getRefPrice())||"暂无".equals(data.getRefPrice())||"元".equals(data.getRefPrice())){

            return null;
        }

        return new BigDecimal(data.getRefPrice().replace("元",""));
     }

    private String getAreaName(String areaId) {

        if(StringUtils.isNotEmpty(areaId)){
            return areaDao.getName(areaId);
        }

        return null;
    }

    private String getCityName(String cityId) {

        if(StringUtils.isNotEmpty(cityId)){
            return cityDao.getName(cityId);
        }

        return null;
    }

    private String getProvinceName(String proId) {

        if(StringUtils.isNotEmpty(proId)){

            return  provinceDao.getName(proId);

        }

        return null;
    }


    @Override
    public void batchAuditActivity(EnrollingImportReq.auditActivityReq req) {

        long timeout =0L;

        if("1".equals(req.getType())){
            Date endDate = DateUtil.strToDateLong(req.getEndAt());

             timeout = (endDate.getTime() - System.currentTimeMillis()) / 1000;

             if(timeout<0){
                 throw new BusinessException("预招商结束时间不能小于当前时间！");
             }

        }

        req.setStatus("1".equals(req.getType())?EnrollingEnum.STATUS.ONLINE.getType():EnrollingEnum.STATUS.IMPORT.getType());
        List<String> list = Arrays.asList(req.getActivityIds());

        req.setActivityIdList(list);
        req.setOperatorAt(DateUtil.formatDate2Str(new Date(),DateUtil.NORM_DATETIME_PATTERN));
        enrollingActivityImportDataDao.batchAuditActivity(req);
        for (String activityId : req.getActivityIds()) {
            EnrollingActivity activity = enrollingActivityDao.selectById(activityId);
            if (StringUtils.isNotBlank(activity.getDisposalService())) {
                TDisposeProvider disposeProvider = disposeProviderDao.findByName(activity.getDisposalService().trim());
                if (disposeProvider != null) {
                    TActivityServiceProvider activityServiceProvider = new TActivityServiceProvider();
                    activityServiceProvider.setActivityId(Integer.parseInt(activityId));
                    activityServiceProvider.setActivityType(ActivityServiceProviderEnum.ActivityType.Enrolling.getKey());
                    activityServiceProvider.setProviderId(disposeProvider.getId());
                    activityServiceProvider.setProviderType(ActivityServiceProviderEnum.ProviderType.Dispose.getKey());
                    activityServiceProvider.setSource(ActivityServiceProviderEnum.Source.Admin.getKey());
                    activityServiceProvider.setOrderNum(0);
                    activityServiceProviderDao.insert(activityServiceProvider);
                }
            }
        }


        //审核通过设置与招商结束时间
        if("1".equals(req.getType())){

            //将开始时间以及结束时间更新到real_data表

            updateDataTimeInfo(list,req.getBeginAt(),req.getEndAt(),req.getRefuseReason());


            assistantService.batchEnrollingActivityExpireTime(list, DateUtil.strToDateLong(req.getEndAt()));
        }

    }

    private void updateDataTimeInfo(List<String> list, String beginAt, String endAt,String refuseReason) {


        enrollingActivityImportRealDataDao.batchUpdateRealImportData(list,beginAt,endAt, refuseReason);
    }

    @Override
    public void updateImportActivityByActivityId(EnrollingActivityImportRealData data) {


        EnrollingActivityImportRealDataCondition condition = new EnrollingActivityImportRealDataCondition();
        condition.setActivityId(data.getActivityId());
        EnrollingActivityImportRealData importData = enrollingActivityImportRealDataDao.selectFirst(condition);

        if(importData==null){
            return;
        }

        data.setId(importData.getId());
        enrollingActivityImportRealDataDao.updateById(data);

    }
}