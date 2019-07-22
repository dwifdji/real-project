package com._360pai.core.service.enrolling.impl;

import com._360pai.arch.common.utils.DateUtil;
import com._360pai.core.condition.enrolling.EnrollingActivityDataCondition;
import com._360pai.core.condition.enrolling.TEnrollingActivityDataCondition;
import com._360pai.core.dao.account.CompanyDao;
import com._360pai.core.dao.account.UserDao;
import com._360pai.core.dao.enrolling.*;
import com._360pai.core.dto.AssetDto;
import com._360pai.core.dto.AssetRes;
import com._360pai.core.model.account.Company;
import com._360pai.core.model.account.User;
import com._360pai.core.model.assistant.City;
import com._360pai.core.model.enrolling.*;
import com._360pai.core.service.account.AccountService;
import com._360pai.core.service.assistant.CityService;
import com._360pai.core.service.enrolling.EnrollingActivityDataService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections4.list.TreeList;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EnrollingActivityDataServiceImpl implements EnrollingActivityDataService{

	@Autowired
	private EnrollingActivityDataDao enrollingActivityDataDao;


	@Autowired
	private EnrollingActivityDao enrollingActivityDao;


	@Autowired
	private EnrollingActivityTestDao enrollingActivityTestDao;


	@Autowired
	private EnrollingActivityDataTestDao enrollingActivityDataTestDao;


	@Autowired
	private TEnrollingActivityDataDao tEnrollingActivityDataDao;


	@Autowired
	private TEnrollingActivityDao tEnrollingActivityDao;

    @Resource
    private CityService cityService;


    @Autowired
    private AccountService accountService;


    @Autowired
    private UserDao userDao;


    @Autowired
    private CompanyDao companyDao;







	@Override
	public int saveActivityData(EnrollingActivityData req) {
		return enrollingActivityDataDao.insert(req);
	}

	@Override
	public EnrollingActivityData getActivityData(EnrollingActivityDataCondition req) {

		return enrollingActivityDataDao.selectFirst(req);
	}

	@Override
	public Integer updateActivityData(EnrollingActivityData data) {
		return enrollingActivityDataDao.updateById(data);
	}

	@Override
	public EnrollingActivityData getActivityDataByActivityId(Integer activityId) {
		EnrollingActivityDataCondition enActivityDataCondition = new EnrollingActivityDataCondition();
		enActivityDataCondition.setActivityId(activityId);
		return enrollingActivityDataDao.selectOneResult(enActivityDataCondition);
	}

	@Override
	public void oldDataMigration(String ids,String templateId) {
		//获取老 数据信息
		List<TEnrollingActivity> oldList = tEnrollingActivityDao.getOldList(ids);


		for(TEnrollingActivity activity: oldList){

			//获取新的data content 字段
			JSONObject newContent= getNewDataInfo(activity,templateId);

			//保存入新的data 表

            saveNewData(activity,newContent);

            //保存入新的 activity表
            saveActivity(activity);

		}





	}

    private void saveActivity(TEnrollingActivity activity) {
	    //保存入新的
        EnrollingActivityTest activityTest = new EnrollingActivityTest();
        BeanUtils.copyProperties(activity, activityTest);

        activityTest.setCityId(String.valueOf(activity.getCityId()));

        activityTest.setCityName(cityService.getByCityId(activity.getCityId()).getName());


        enrollingActivityTestDao.insert(activityTest);
    }

    private void saveNewData(TEnrollingActivity activity, JSONObject newContent) {
        EnrollingActivityDataTest data = new EnrollingActivityDataTest();

        data.setActivityId(activity.getId());
        data.setContent(newContent);


	    enrollingActivityDataTestDao.insert(data);
    }

    private JSONObject getNewDataInfo(TEnrollingActivity activity,String templateId) {

		//组装参数
		List<AssetRes> assetDtos;

		if(templateId.equals("5")){
            assetDtos = convertOldData(activity);
        }else{
            assetDtos = convertOldData7(activity);
        }





        JSONObject jsonObject = new JSONObject();

		jsonObject.put("userWork", "");
        jsonObject.put("templateId", templateId);
        jsonObject.put("templateDate", assetDtos);
        jsonObject.put("assetAndActivity", "");

		return jsonObject;
	}

    private List<AssetRes> convertOldData7(TEnrollingActivity activity) {
        List<AssetRes> dtoArray = new TreeList<>();

        //招商名称
        String name = activity.getName();
        convertAssetDto(dtoArray, new String[]{name}, new String[]{}, new Integer[]{}, "招商名称", "招商信息", "TEXT", "23", "ename000");


        //-------------招商活动信息-------------
        //招商图片
        JSONArray extra = activity.getExtra().getJSONArray("images");
        Object[] strings = new Object[extra.size()];
        for (int i = 0; i < extra.size(); i++) {
            String string = extra.getString(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("url", string);
            jsonObject.put("name", UUID.randomUUID().toString().replace("-", ""));
            strings[i] = jsonObject;
        }
        convertAssetDto(dtoArray, strings, new String[]{}, new Integer[]{}, "招商图片", "招商活动信息", "FIlEIMG", "25", "eimages000");


        //所在地
        Integer assetCityId = activity.getCityId();
        City city = cityService.getByCityId(assetCityId);
        JSONObject json = new JSONObject();
        json.put("name", city.getName());
        json.put("id", city.getId());
        convertAssetDto(dtoArray, new Object[]{json}, new String[]{}, new Integer[]{}, "所在地", "招商活动信息", "CITY", "25", "ecity000");


        //展示时间
        convertAssetDto(dtoArray, new Object[]{DateUtil.format(activity.getBeginAt(), DateUtil.NORM_DATETIME_PATTERN), DateUtil.format(activity.getEndAt(), DateUtil.NORM_DATETIME_PATTERN)}, null, null, "展示时间", "招商活动信息", "DATE", "25", "epreviewAt000");

        //债权本金
        convertAssetDto(dtoArray, new Object[]{activity.getRefPrice()}, new String[]{}, new Integer[]{}, "市场参考价", "招商活动信息", "TEXT", "25", "ezqbj000");



        //-------------委托方信息-------------
        String uname =null;

        String uIdCard = null;
        //获取委托人
        User user = userDao.selectById(activity.getPartyId());
        if(user!=null){
            uname = user.getName();
            uIdCard = user.getCertificateNumber();
        }


        Company company = companyDao.selectById(activity.getPartyId());
        if(company!=null){
            uname = company.getName();
            uIdCard = company.getLicense();

        }


        //委托人姓名
        convertAssetDto(dtoArray, new Object[]{uname}, new String[]{}, new Integer[]{}, "委托人名称", "委托方信息", "USERNAME", "26", "euserName000");
        //委托人证件号码
        convertAssetDto(dtoArray, new Object[]{uIdCard}, new String[]{}, new Integer[]{}, "委托人证件号码", "委托方信息", "USERPHONE", "26", "euserPhone000");


        //项目联系人
        convertAssetDto(dtoArray, new Object[]{activity.getContactName()}, new String[]{}, new Integer[]{}, "项目联系人", "委托方信息", "TEXT", "26", "econtactName000");

        //联系电话
        convertAssetDto(dtoArray, new Object[]{activity.getContactPhone()}, new String[]{}, new Integer[]{}, "联系人联系方式", "委托方信息", "TEXT", "26", "econtactPhone000");


        return dtoArray;

    }


    private List<AssetRes> convertOldData(TEnrollingActivity activity) {

		List<AssetRes> dtoArray = new TreeList<>();

		//获取data信息
		TEnrollingActivityDataCondition condition = new TEnrollingActivityDataCondition();
		condition.setActivityId(activity.getId());
		TEnrollingActivityData data = tEnrollingActivityDataDao.selectFirst(condition);


		//------------招商信息--------------
		//招商名称
        String name = activity.getName();
        convertAssetDto(dtoArray, new String[]{name}, new String[]{}, new Integer[]{}, "招商名称", "招商信息", "TEXT", "23", "ename000");

        /*//企业名称
        convertAssetDto(dtoArray, new String[]{dataContent.getString("zwrmc")}, new String[]{}, new Integer[]{}, "企业名称", "招商信息", "TEXT", "23", "ecompany000");

        //营业执照
        convertAssetDto(dtoArray, new String[]{dataContent.getString("zwrsfzhm")}, new String[]{}, new Integer[]{}, "营业执照", "招商信息", "TEXT", "23", "eyyzz000");


        //抵押情况
        convertAssetDto(dtoArray, new String[]{"有抵押", "抵押", "不动产抵押物"}, new String[]{"94", "37", "6"}, new Integer[]{0, 0, 0}, "抵押情况", "标的信息", "SELECT", "32", "edyqk000");

        //产权证号
        convertAssetDto(dtoArray, new String[]{dataContent.getString("czbh")}, new String[]{}, new Integer[]{}, "产权证号", "招商信息", "TEXT", "32", "ecqzh000");

        //抵押物地址
        convertAssetDto(dtoArray, new String[]{dataContent.getString("dz")}, new String[]{}, new Integer[]{}, "抵押物地址", "招商信息", "TEXT", "32", "edywdz000");


        //标的物属性
        convertAssetDto(dtoArray, new String[]{"住宅"}, new String[]{"71"}, new Integer[]{0}, "标的物属性", "招商信息", "SELECT", "32", "ebdwlx000");

        //建筑面积
        convertAssetDto(dtoArray, new String[]{dataContent.getString("mj")}, new String[]{}, new Integer[]{}, "建筑面积", "招商信息", "TEXT", "32", "ejzmj000");*/



        //-------------招商活动信息-------------
        //招商图片
        JSONArray extra = activity.getExtra().getJSONArray("images");
        Object[] strings = new Object[extra.size()];
        for (int i = 0; i < extra.size(); i++) {
            String string = extra.getString(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("url", string);
            jsonObject.put("name", UUID.randomUUID().toString().replace("-", ""));
            strings[i] = jsonObject;
        }
        convertAssetDto(dtoArray, strings, new String[]{}, new Integer[]{}, "招商图片", "招商活动信息", "FIlEIMG", "25", "eimages000");


        //所在地
        Integer assetCityId = activity.getCityId();
        City city = cityService.getByCityId(assetCityId);
        JSONObject json = new JSONObject();
        json.put("name", city.getName());
        json.put("id", city.getId());
        convertAssetDto(dtoArray, new Object[]{json}, new String[]{}, new Integer[]{}, "所在地", "招商活动信息", "CITY", "25", "ecity000");


        //展示时间
        convertAssetDto(dtoArray, new Object[]{DateUtil.format(activity.getBeginAt(), DateUtil.NORM_DATETIME_PATTERN), DateUtil.format(activity.getEndAt(), DateUtil.NORM_DATETIME_PATTERN)}, null, null, "展示时间", "招商活动信息", "DATE", "25", "epreviewAt000");

        //债权本金
        convertAssetDto(dtoArray, new Object[]{activity.getRefPrice()}, new String[]{}, new Integer[]{}, "债权本金", "招商活动信息", "TEXT", "25", "ezqbj000");

        //债权利息
        convertAssetDto(dtoArray, new Object[]{activity.getDeposit()}, new String[]{}, new Integer[]{}, "债权利息", "招商活动信息", "TEXT", "25", "ezqlx000");




        //-------------委托方信息-------------
        String uname =null;

        String uIdCard = null;
        //获取委托人
        User user = userDao.selectById(activity.getPartyId());
        if(user!=null){
            uname = user.getName();
            uIdCard = user.getCertificateNumber();
        }


        Company company = companyDao.selectById(activity.getPartyId());
        if(company!=null){
            uname = company.getName();
            uIdCard = company.getLicense();

        }


        //委托人姓名
        convertAssetDto(dtoArray, new Object[]{uname}, new String[]{}, new Integer[]{}, "委托人名称", "委托方信息", "USERNAME", "26", "euserName000");
        //委托人证件号码
        convertAssetDto(dtoArray, new Object[]{uIdCard}, new String[]{}, new Integer[]{}, "委托人证件号码", "委托方信息", "USERPHONE", "26", "euserPhone000");


        //项目联系人
        convertAssetDto(dtoArray, new Object[]{activity.getContactName()}, new String[]{}, new Integer[]{}, "项目联系人", "委托方信息", "TEXT", "26", "econtactName000");

        //联系电话
        convertAssetDto(dtoArray, new Object[]{activity.getContactPhone()}, new String[]{}, new Integer[]{}, "联系人联系方式", "委托方信息", "TEXT", "26", "econtactPhone000");


        return dtoArray;
	}



    private void convertAssetDto(List<AssetRes> dtoArray,
                                 Object[] values,
                                 Object[] numArray,
                                 Object[] idArray,
                                 String name,
                                 String titleName,
                                 String type,
                                 String title,
                                 String key) {
        AssetRes assetDto = new AssetRes();
        List<Object> val = assetDto.getVal();
        List<Object> valId = assetDto.getValId();
        List<Object> valNum = assetDto.getValNum();
        val.addAll(Arrays.asList(values));
        if (numArray != null) {
            valNum.addAll(Arrays.asList(numArray));
        }
        if (idArray != null) {
            valId.addAll(Arrays.asList(idArray));
        }
        assetDto.setVal(val);
        assetDto.setValId(valId);
        assetDto.setValNum(valNum);
        assetDto.setTitleName(titleName);
        assetDto.setGrading("0");
        assetDto.setName(name);
        assetDto.setGradingSecond("0");
        if ("zwr000".equals(key) || "qymc000".equals(key) || "yyzz000".equals(key)) {
            assetDto.setTitleSubset("2");
        } else {
            assetDto.setTitleSubset("0");
        }
        assetDto.setType(type);
        assetDto.setTitle(title);
        assetDto.setKey(key);
        dtoArray.add(assetDto);
    }


    @Override
    public void oldDataAddBusTypeName() {
        //获取所有的data 数据
        List<EnrollingActivityData>  allList   = enrollingActivityDataDao.selectAll();

        //
        for(EnrollingActivityData data :allList){
            JSONObject jsonObject = data.getContent();

            List<String> busTypeNameList = new ArrayList<>();
            JSONArray templateDate = jsonObject.getJSONArray("templateDate");
            //遍历获取模板的所有字段
            for (int i = 0; i < templateDate.size(); i++) {
                AssetDto json = templateDate.getJSONObject(i).toJavaObject(AssetDto.class);


                if("ebdwlx".equals(formatKey(json.getKey()))){

                    busTypeNameList.add(json.getVal().get(0));

                }

            }

            //更新属性


            EnrollingActivity activity = new EnrollingActivity();
            activity.setId(data.getActivityId());
            activity.setBusTypeName(getBusTypeNameInfo(busTypeNameList));
            enrollingActivityDao.updateById(activity);



        }



    }


    public String formatKey(String key) {
        Pattern pattern = Pattern.compile("[\\d]");
        Matcher matcher = pattern.matcher(key);
        return (matcher.replaceAll("").trim());
    }


    private String getBusTypeNameInfo(List<String> busTypeNameList) {

        String busTypeName="";

        if(busTypeNameList.size()<1){
            return "";
        }

        for(String name :busTypeNameList){

            busTypeName =busTypeName + name+",";
        }

        return busTypeName;
    }
}