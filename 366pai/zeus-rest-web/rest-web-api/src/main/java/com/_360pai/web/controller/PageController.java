package com._360pai.web.controller;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.utils.JsonUtil;
import com._360pai.arch.core.filter.PropertyFilterFactory;
import com._360pai.core.common.SystemDict;
import com._360pai.core.common.constants.ActivityEnum;
import com._360pai.core.common.constants.AssetEnum;
import com._360pai.core.common.constants.PartyEnum;
import com._360pai.core.facade.THallActivityFacade;
import com._360pai.core.facade.agency.AgencyFacade;
import com._360pai.core.facade.asset.AssetPropertiesFacade;
import com._360pai.core.facade.asset.TAssetCateGoryFacade;
import com._360pai.core.facade.asset.req.TAssetCategoryReq;
import com._360pai.core.facade.assistant.CityFacade;
import com._360pai.core.facade.assistant.req.CityReq;
import com._360pai.web.vo.TAssetCategoryVo;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zxiao
 * @Title: PageController
 * @ProjectName zeus-rest-web
 * @Description:
 * @date 2018/8/29 16:19
 */
@RestController
public class PageController {
    @Reference(version = "1.0.0")
    protected CityFacade cityFacade;
    @Reference(version = "1.0.0")
    protected TAssetCateGoryFacade assetCategoryGroupFacade;
    @Reference(version = "1.0.0")
    protected AssetPropertiesFacade assetPropertiesFacade;
    @Reference(version = "1.0.0")
    protected THallActivityFacade tHallActivityFacade;
    @Reference(version = "1.0.0")
    protected AgencyFacade agencyFacade;


    private static final JSONArray JSON_ARRAY;
    private static final JSONArray MODE_ARRAY;

    private static  String searchProvinces = "[{\"name\":\"浙江\",\"id\":\"11\"},{\"name\":\"江苏\",\"id\":\"10\"},{\"name\":\"河南\",\"id\":\"16\"},{\"name\":\"福建\",\"id\":\"13\"},{\"name\":\"上海\",\"id\":\"9\"},{\"name\":\"广东\",\"id\":\"19\"},{\"name\":\"安徽\",\"id\":\"12\"},{\"name\":\"内蒙古\",\"id\":\"5\"},{\"name\":\"北京\",\"id\":\"1\"},{\"name\":\"湖北\",\"id\":\"17\"},{\"name\":\"云南\",\"id\":\"25\"},{\"name\":\"山东\",\"id\":\"15\"},{\"name\":\"海南\",\"id\":\"21\"},{\"name\":\"江西\",\"id\":\"14\"},{\"name\":\"广西\",\"id\":\"20\"},{\"name\":\"天津\",\"id\":\"2\"},{\"name\":\"重庆\",\"id\":\"22\"},{\"name\":\"湖南\",\"id\":\"18\"},{\"name\":\"河北\",\"id\":\"3\"},{\"name\":\"四川\",\"id\":\"23\"},{\"name\":\"山西\",\"id\":\"4\"},{\"name\":\"贵州\",\"id\":\"24\"},{\"name\":\"宁夏\",\"id\":\"30\"},{\"name\":\"青海\",\"id\":\"29\"},{\"name\":\"辽宁\",\"id\":\"6\"},{\"name\":\"吉林\",\"id\":\"7\"},{\"name\":\"黑龙江\",\"id\":\"8\"},{\"name\":\"西藏\",\"id\":\"26\"},{\"name\":\"陕西\",\"id\":\"27\"},{\"name\":\"甘肃\",\"id\":\"28\"},{\"name\":\"新疆\",\"id\":\"31\"},{\"name\":\"香港\",\"id\":\"32\"},{\"name\":\"澳门\",\"id\":\"33\"},{\"name\":\"台湾\",\"id\":\"34\"}]";

    private static String activityProvinces = "[{\"name\":\"浙江\",\"id\":\"11\"},{\"name\":\"江苏\",\"id\":\"10\"},{\"name\":\"河南\",\"id\":\"16\"},{\"name\":\"福建\",\"id\":\"13\"},{\"name\":\"上海\",\"id\":\"9\"},{\"name\":\"广东\",\"id\":\"19\"},{\"name\":\"安徽\",\"id\":\"12\"},{\"name\":\"北京\",\"id\":\"1\"},{\"name\":\"湖北\",\"id\":\"17\"},{\"name\":\"云南\",\"id\":\"25\"},{\"name\":\"山东\",\"id\":\"15\"},{\"name\":\"海南\",\"id\":\"21\"},{\"name\":\"广西\",\"id\":\"20\"},{\"name\":\"天津\",\"id\":\"2\"},{\"name\":\"重庆\",\"id\":\"22\"},{\"name\":\"湖南\",\"id\":\"18\"},{\"name\":\"河北\",\"id\":\"3\"},{\"name\":\"四川\",\"id\":\"23\"},{\"name\":\"辽宁\",\"id\":\"6\"},{\"name\":\"吉林\",\"id\":\"7\"},{\"name\":\"陕西\",\"id\":\"27\"}]";

    static {
        //资管类型
        JSON_ARRAY = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", "1");
        jsonObject.put("name", "银行");

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("id", "2");
        jsonObject1.put("name", "AMC");

        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("id", "3");
        jsonObject2.put("name", PartyEnum.Category.FOLK_ASSET_COMPANY.getValue());

        JSONObject jsonObject3 = new JSONObject();
        jsonObject3.put("id", "4");
        jsonObject3.put("name", "个人");

        JSONObject jsonObject4 = new JSONObject();
        jsonObject4.put("id", "5");
        jsonObject4.put("name", "拍卖公司");

        JSONObject jsonObject5 = new JSONObject();
        jsonObject5.put("id", "6");
        jsonObject5.put("name", "一般公司");

        JSON_ARRAY.add(jsonObject3);
        JSON_ARRAY.add(jsonObject);
        JSON_ARRAY.add(jsonObject1);
        JSON_ARRAY.add(jsonObject2);
        JSON_ARRAY.add(jsonObject4);
        JSON_ARRAY.add(jsonObject5);



        //拍卖方式
        MODE_ARRAY = new JSONArray();
        JSONObject modejsonObject1 = new JSONObject();
        modejsonObject1.put("id", ActivityEnum.ActivityMode.ENGLISH.getKey());
        modejsonObject1.put("name", ActivityEnum.ActivityMode.ENGLISH.getValue());

        JSONObject modejsonObject2 = new JSONObject();
        modejsonObject2.put("id", ActivityEnum.ActivityMode.DUTCH.getKey());
        modejsonObject2.put("name", ActivityEnum.ActivityMode.DUTCH.getValue());

        JSONObject modejsonObject3 = new JSONObject();
        modejsonObject3.put("id", ActivityEnum.ActivityMode.SEALED.getKey());
        modejsonObject3.put("name", ActivityEnum.ActivityMode.SEALED.getValue());

        JSONObject modejsonObject4 = new JSONObject();
        modejsonObject4.put("id", ActivityEnum.ActivityMode.PUBLIC.getKey());
        modejsonObject4.put("name", ActivityEnum.ActivityMode.PUBLIC.getValue());

        MODE_ARRAY.add(modejsonObject1);
        MODE_ARRAY.add(modejsonObject2);
        MODE_ARRAY.add(modejsonObject3);
        MODE_ARRAY.add(modejsonObject4);
    }

    /**
     * 功能描述: 首页属性加载
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/29 16:27
     */
    @GetMapping(value = "/open/nav")
    public ResponseModel nav() {
        Object cities = cityFacade.pageCities();
        TAssetCategoryReq req = new TAssetCategoryReq();
        req.setBusinessType(TAssetCategoryReq.AUCTION);
        req.setEnabled(true);
        Object cateGoryGroupList = assetCategoryGroupFacade.getAllCateGoryList(req);

        Object properties = assetPropertiesFacade.getProperties();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("asset_groups_names",getCateGoryGroupList(cateGoryGroupList));
        jsonObject.put("activity_cities", cities);
        jsonObject.put("activity_provinces", JSON.parseArray(activityProvinces));
        jsonObject.put("search_provinces", JSON.parseArray(searchProvinces));
        jsonObject.put("asset_property_names", properties);
        jsonObject.put("asset_hall_type", tHallActivityFacade.getAssetType());
        jsonObject.put("asset_hall_lease_type", tHallActivityFacade.getAssetLeaseType());
        jsonObject.put("enrolling_hall_type", tHallActivityFacade.getEnrollingType());
        jsonObject.put("asset_party_category", JSON_ARRAY);
        jsonObject.put("activity_status", JSONArray.parse(SystemDict.AUCTIONSTATUS));
        jsonObject.put("activity_mode", MODE_ARRAY);
        jsonObject.put("asset_lease_type", AssetEnum.LeaseAssetType.list);
        jsonObject.put("lease_area_type", AssetEnum.LeaseAreaType.list);
        jsonObject.put("serverAgency", agencyFacade.getDefaultAgency());
        //获取所有闵行区镇的数据
        CityReq cityReq = new CityReq();
        cityReq.setAreaId(792);
        jsonObject.put("lease_area_town", cityFacade.getTownsByAreaId(cityReq).getTowns());
        return ResponseModel.succ(jsonObject, PropertyFilterFactory.create(new String[]{"dealMode", "businessType", "enabled", "searchWeight"}));
    }


    /**
     *
     *添加租赁权拍卖
     */
    private Object getCateGoryGroupList(Object cateGoryGroupList) {

        List<TAssetCategoryVo> tAssetCategories = (List<TAssetCategoryVo>)cateGoryGroupList;

        TAssetCategoryVo vo = new TAssetCategoryVo();
        vo.setId(-1);
        vo.setName("租赁权拍卖");
        tAssetCategories.add(vo);

        return tAssetCategories;
    }
}
