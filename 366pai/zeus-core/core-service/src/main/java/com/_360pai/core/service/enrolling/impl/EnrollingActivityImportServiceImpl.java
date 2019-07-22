package com._360pai.core.service.enrolling.impl;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.arch.common.utils.RandomNumberGenerator;
import com._360pai.arch.common.utils.ToolUtil;
import com._360pai.arch.core.sysconfig.properties.GatewayProperties;
import com._360pai.core.common.constants.ActivityServiceProviderEnum;
import com._360pai.core.common.constants.EnrollingEnum;
import com._360pai.core.condition.assistant.StaffCondition;
import com._360pai.core.condition.enrolling.EnrollingActivityImportDataCondition;
import com._360pai.core.dao.account.TDisposeProviderDao;
import com._360pai.core.dao.assistant.*;
import com._360pai.core.dao.enrolling.EnrollingActivityDao;
import com._360pai.core.dao.enrolling.EnrollingActivityImportDataDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.account.vo.DisposeProvider;
import com._360pai.core.facade.enrolling.req.*;
import com._360pai.core.facade.enrolling.resp.EnrollingActivityImportDataResp;
import com._360pai.core.model.account.TDisposeProvider;
import com._360pai.core.model.assistant.Staff;
import com._360pai.core.model.assistant.TActivityServiceProvider;
import com._360pai.core.model.enrolling.EnrollingActivity;
import com._360pai.core.model.enrolling.EnrollingActivityImportData;
import com._360pai.core.model.enrolling.EnrollingActivityImportRealData;
import com._360pai.core.service.account.DisposeService;
import com._360pai.core.service.assistant.AssistantService;
import com._360pai.core.service.enrolling.EnrollingActivityImportService;
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
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class EnrollingActivityImportServiceImpl implements EnrollingActivityImportService {

	@Autowired
	private EnrollingActivityImportDataDao enrollingActivityImportDataDao;


	@Autowired
	private EnrollingActivityDao enrollingActivityDao;


    @Autowired
    private EnrollingActivityRealImportService enrollingActivityRealImportService;


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
    public PageInfo getUserList(EnrollingImportReq.getUserListReq req) {

        PageHelper.startPage(req.getPage(), req.getPerPage());


        //获取上传的id
        EnrollingActivityImportUserReq  userReq = new EnrollingActivityImportUserReq();
        userReq.setName(req.getName());

        List<EnrollingActivityImportUserVo> userList = enrollingActivityImportDataDao.getApplyUserList(userReq);



        return new PageInfo<>(userList);
    }

    @Override
    public PageInfo getUploadActivityList(EnrollingImportReq.getUploadActivityListReq req) {
        //审核列表看看 当前登录人是否能看到全部的信息
        if("1".equals(req.getType())||("2".equals(req.getType())&&checkOper(req))){
            req.setOperatorId(null);
        }

        PageHelper.startPage(req.getPage(), req.getPerPage());

        EnrollingActivityImportDataListReq dataListReq = new EnrollingActivityImportDataListReq();

        dataListReq.setStatus("1".equals(req.getType())?EnrollingEnum.STATUS.IMPORT.getType():EnrollingEnum.STATUS.AGENCY_APPROVED.getType());
        dataListReq.setOperatorId(req.getOperatorId());
        dataListReq.setUserName(req.getUserName());
        dataListReq.setName(req.getName());
        dataListReq.setContactPerson(req.getContactPerson());


        List<EnrollingActivityImportDataListVo> listVoList = enrollingActivityImportDataDao.getImportDateList(dataListReq);

        return new PageInfo<>(listVoList);
    }

    private boolean checkOper(EnrollingImportReq.getUploadActivityListReq req) {

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
    public ResponseModel uploadActivity(List<EnrollingActivityImportDataVo> activityList) {

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
                    List<EnrollingActivityImportDataVo> listPage = activityList.subList(0, pointsDataLimit);

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


    private void batchSave(List<EnrollingActivityImportDataVo> dataList,List<ProvinceCityVo> cityVos,List<Staff> staff) {
        List<EnrollingActivity> list = new ArrayList<>();

        int count=0;
        for(EnrollingActivityImportDataVo vo :dataList){
            vo.setDebtorProId(getProvinceId(vo.getDebtorPro(),cityVos));
            vo.setDebtorCityId(getCityId(vo.getDebtorCity(),cityVos));
            vo.setDebtorAreaId(getAreaId(vo.getDebtorArea(),cityVos));
            vo.setPawnProId(getProvinceId(vo.getPawnPro(),cityVos));
            vo.setPawnCityId(getCityId(vo.getPawnCity(),cityVos));
            vo.setPawnAreaId(getAreaId(vo.getPawnArea(),cityVos));

            EnrollingActivity enrollingActivity = new EnrollingActivity();
            enrollingActivity.setDeleteFlag(false);
            enrollingActivity.setCreatedAt(DateUtil.getSysTime());
            enrollingActivity.setPartyId(Integer.valueOf(vo.getPartyId()));
            enrollingActivity.setVisibilityLevel(EnrollingEnum.VISIBILITY_LEVEL.PLATFORM_DEFAULT.getType());
            enrollingActivity.setCode(RandomNumberGenerator.generatorMarkEnrollingCode("ZQZS-",4)+String.valueOf(count));
            enrollingActivity.setReminderNumber(0);
            enrollingActivity.setFocusNumber(0);
            enrollingActivity.setBrowseNumber(0);
            enrollingActivity.setApplyNumber(0);
            enrollingActivity.setName(vo.getName());
            enrollingActivity.setContactPhone(vo.getContactPhone());
            enrollingActivity.setContactName(vo.getContactPerson());
            enrollingActivity.setType("2");//债券招商
            enrollingActivity.setProvinceId(vo.getDebtorProId());
            enrollingActivity.setCityId(vo.getDebtorCityId());
            enrollingActivity.setAreaId(vo.getDebtorAreaId());
            enrollingActivity.setCityName(vo.getDebtorCity());
            enrollingActivity.setRefPrice(StringUtils.isNotEmpty(vo.getOutstandingPrincipal())?new BigDecimal(vo.getOutstandingPrincipal()).multiply(new BigDecimal("10000")):null);
            enrollingActivity.setBrightSpot(vo.getProjectWindow());
            enrollingActivity.setDeposit(StringUtils.isNotEmpty(vo.getOutstandingInterest())?new BigDecimal(vo.getOutstandingInterest()).multiply(new BigDecimal("10000")):null);
            enrollingActivity.setThirdPartyStatus(EnrollingEnum.ENROLLING_THIRD_PARTY_STATUS.IMPORT.getType());
            enrollingActivity.setStatus(EnrollingEnum.STATUS.IMPORT.getType());
            enrollingActivity.setOptions(getOperId(vo.getContactPerson(),staff));
            enrollingActivity.setGuarantee(StringUtils.isEmpty(vo.getSpecificAssureMeans())?0:1);
            enrollingActivity.setBusTypeName(StringUtils.isEmpty(vo.getSpecificAssureMeans())?null:vo.getSpecificAssureMeans().replaceAll("、",","));
            enrollingActivity.setBranchComName(vo.getBranchComName());
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
        }
        enrollingActivityImportDataDao.batchSaveImportData(dataList);

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
    public ResponseModel getUploadActivityDetails(EnrollingImportReq.getUploadActivityDetailsReq req) {

        EnrollingActivityImportDataResp data = enrollingActivityImportDataDao.getImportDataDetail(req.getActivityId());

        if(data==null){
            return ResponseModel.fail(ApiCallResult.DATA_IS_EMPTY);
        }

        //格式化金额
        data = fromData(data);
        data.setDisposalService(ToolUtil.maskDisposeProviderName(data.getDisposalService()));
        List<TDisposeProvider> disposeProviders = activityServiceProviderDao.getEnrollingDisposeProvider(Integer.parseInt(req.getActivityId()));
        if (disposeProviders.size() > 0) {
            DisposeProvider disposeProvider = disposeService.getDisposeProvider(disposeProviders.get(0));
            data.setDisposalService(disposeProvider.getName());
        }
        data.setDisposalPhone(gatewayProperties.getEnrollingImportDisposalService() +"  "+gatewayProperties.getEnrollingImportDisposalPhone());

        if("web".equals(req.getType())){

            if(StringUtils.isNotEmpty(data.getFundProvider())){
                data.setFundProvider(gatewayProperties.getEnrollingImportFundProvider());
                data.setFundPhone(gatewayProperties.getEnrollingImportFundPhone());
            }
        }
        return ResponseModel.succ(data);
    }

    private EnrollingActivityImportDataResp fromData(EnrollingActivityImportDataResp data) {


        data.setOutstandingInterest(formatMoney(data.getOutstandingInterest()));
        data.setOutstandingPrincipal(formatMoney(data.getOutstandingPrincipal()));
        data.setDedit(formatMoney(data.getDedit()));
        data.setTotalDebt(formatMoney(data.getTotalDebt()));
        data.setAssetValue(formatMoney(data.getAssetValue()));
        data.setOtherInfo(formatMoney(data.getOtherInfo()));

        return data;
    }

    private String formatMoney(String money) {

        if(StringUtils.isNotEmpty(money)){
            return money+"万元";
        }

        return money;
    }

    @Override
    public ResponseModel updateActivity(EnrollingActivity req) {



        enrollingActivityDao.updateById(req);

        return ResponseModel.succ();
    }

    @Override
    @Transactional
    public ResponseModel updateImportActivity(EnrollingImportReq.updateActivityReq req) {

        EnrollingActivity enrollingActivity =  enrollingActivityDao.selectById(req.getActivityId());

        if(enrollingActivity==null){
            return ResponseModel.fail(ApiCallResult.DATA_IS_EMPTY);
        }


        EnrollingActivityImportData data = new EnrollingActivityImportData();



        BeanUtils.copyProperties(req,data);

        data.setDebtorPro(getProvinceName(data.getDebtorProId()));
        data.setDebtorCity(getCityName(data.getDebtorCityId()));
        data.setDebtorArea(getAreaName(data.getDebtorAreaId()));


        data.setPawnPro(getProvinceName(data.getPawnProId()));
        data.setPawnCity(getCityName(data.getPawnCityId()));
        data.setPawnArea(getAreaName(data.getPawnAreaId()));

        enrollingActivityImportDataDao.updateById(data);
        StaffCondition condition = new StaffCondition();
        condition.setStatus("1");
        List<Staff> staff = staffDao.selectList(condition);
        enrollingActivity.setName(data.getName());
        enrollingActivity.setRefPrice(StringUtils.isNotEmpty(data.getOutstandingPrincipal())?new BigDecimal(data.getOutstandingPrincipal()).multiply(new BigDecimal("10000")):null);
        enrollingActivity.setBrightSpot(data.getProjectWindow());
        enrollingActivity.setDeposit(StringUtils.isNotEmpty(data.getOutstandingInterest())?new BigDecimal(data.getOutstandingInterest()).multiply(new BigDecimal("10000")):null);
        enrollingActivity.setProvinceId(data.getDebtorProId());
        enrollingActivity.setCityId(data.getDebtorCityId());
        enrollingActivity.setCityName(data.getDebtorCity());
        enrollingActivity.setAreaId(data.getDebtorAreaId());
        enrollingActivity.setBusTypeName(StringUtils.isEmpty(data.getSpecificAssureMeans())?"":data.getSpecificAssureMeans().replaceAll("、",","));
        enrollingActivity.setGuarantee(StringUtils.isEmpty(data.getSpecificAssureMeans())?0:1);
        enrollingActivity.setOptions(getOperId(data.getContactPerson(),staff));
        enrollingActivity.setBranchComName(data.getBranchComName());
        enrollingActivity.setFundProvider(data.getFundProvider());
        enrollingActivity.setDisposalService(data.getDisposalService());
        enrollingActivityDao.updateById(enrollingActivity);
        return ResponseModel.succ();
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
            assistantService.batchEnrollingActivityExpireTime(list, DateUtil.strToDateLong(req.getEndAt()));
        }

    }

    @Override
    @Transactional
    public ResponseModel saveFundServiceInfo(EnrollingImportReq.getFundServiceReq req) {

        EnrollingActivity activity =new  EnrollingActivity();
        EnrollingActivityImportData data = new EnrollingActivityImportData();


        EnrollingActivityImportRealData realData = new EnrollingActivityImportRealData();


        if("1".equals(req.getType())){
            data.setDisposalService(req.getName());
            activity.setDisposalService(req.getName());
            realData.setDisposalService(req.getName());

        }
        if("2".equals(req.getType())){
            data.setFundProvider(req.getName());
            activity.setFundProvider(req.getName());
            realData.setFundProvider(req.getName());
        }
        activity.setId(Integer.valueOf(req.getActivityId()));
        enrollingActivityDao.updateById(activity);
        EnrollingActivityImportDataCondition condition = new EnrollingActivityImportDataCondition();
        condition.setActivityId(Integer.valueOf(req.getActivityId()));
        EnrollingActivityImportData importData = enrollingActivityImportDataDao.selectFirst(condition);
        if(importData !=null){
            data.setId(importData.getId());
            enrollingActivityImportDataDao.updateById(data);
        }


        //更新物权导入信息
        realData.setActivityId(Integer.valueOf(req.getActivityId()));
        enrollingActivityRealImportService.updateImportActivityByActivityId(realData);







        return ResponseModel.succ();
    }

    @Override
    @Transactional
    public ResponseModel deleteImportActivity(EnrollingImportReq.activityIdReq req) {


        //删除主表信息
        EnrollingActivity activity = new EnrollingActivity();
        activity.setId(Integer.valueOf(req.getActivityId()));
        activity.setDeleteFlag(true);

        enrollingActivityDao.updateById(activity);
        EnrollingActivityImportDataCondition condition = new EnrollingActivityImportDataCondition();
        condition.setActivityId(Integer.valueOf(req.getActivityId()));
        EnrollingActivityImportData data = enrollingActivityImportDataDao.selectFirst(condition);

        if(data==null){
            return ResponseModel.fail(ApiCallResult.DATA_EMPTY);
        }

        data.setDeleteFlag(true);
        enrollingActivityImportDataDao.updateById(data);


        return ResponseModel.succ();
    }

    @Override
    public void updateImportActivityByActivityId(EnrollingActivityImportData data) {


        EnrollingActivityImportDataCondition condition = new EnrollingActivityImportDataCondition();
        condition.setActivityId(data.getActivityId());
        EnrollingActivityImportData importData = enrollingActivityImportDataDao.selectFirst(condition);

        if(importData==null){
            return;
        }

        data.setId(importData.getId());
        enrollingActivityImportDataDao.updateById(data);

    }
}