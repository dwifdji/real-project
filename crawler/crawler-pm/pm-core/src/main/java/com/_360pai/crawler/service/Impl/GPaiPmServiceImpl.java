package com._360pai.crawler.service.Impl;

import cn.wanghaomiao.seimi.struct.Response;
import com._360pai.crawler.dao.com.MapAuctionDao;
import com._360pai.crawler.dao.gpai.GPaiPmAreaDao;
import com._360pai.crawler.dao.gpai.GPaiPmDao;
import com._360pai.crawler.model.com.TMapAuction;
import com._360pai.crawler.model.gpai.GPaiAreaModel;
import com._360pai.crawler.model.gpai.GPaiPm;
import com._360pai.crawler.service.GPaiPmService;
import com._360pai.crawler.common.util.HttpUtilNew;
import com._360pai.crawler.common.util.HttpUtilNewModel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xdrodger
 * @Title: GPaiPmServiceImpl
 * @ProjectName zeus
 * @Description:
 * @date 2018/11/14 14:10
 */
@Service
@Slf4j
public class GPaiPmServiceImpl implements GPaiPmService {

    private static final String GPAI_SOURCE = "gpai";


    private static final String SEARCH_URL = "http://m.gpai.net/api/search";

    @Autowired
    private GPaiPmDao gPaiPmDao;
    @Autowired
    private GPaiPmAreaDao gPaiPmAreaDao;

    @Autowired
    private MapAuctionDao mapAuctionDao;


    @Override
    public List<GPaiAreaModel> selectAllAreas() {
        return gPaiPmAreaDao.findAll();
    }

    @Override
    @Transactional
    public GPaiPm saveItem(GPaiPm gPaiPm) {

        //获取字段
        GPaiPm paiPm = gPaiPmDao.findByItemId(gPaiPm.getItemId());
        if(paiPm!=null){
            gPaiPm.setId(paiPm.getId());
            gPaiPm.setUpdateTime(paiPm.getCreateTime());
            gPaiPm.setUpdateTime(new Date());
        }

        //添加到map_auction 表
        gPaiPmDao.saveAndFlush(gPaiPm);

        saveOrUpdateData(gPaiPm);


        return gPaiPm;
    }

    private void saveOrUpdateData(GPaiPm gPaiPm) {




        TMapAuction auction = new TMapAuction();

        auction.setRelevanceId(String.valueOf(gPaiPm.getId()));
        auction.setSource(GPAI_SOURCE);
        auction.setLng(gPaiPm.getLng());
        auction.setLat(gPaiPm.getLat());
        auction.setStartingPrice(bigDecimalToStr(gPaiPm.getStartPrice()));
        auction.setProName(gPaiPm.getProName());
        auction.setCityName(gPaiPm.getCityName());
        auction.setAreaName(gPaiPm.getAreaName());
        auction.setLooker(String.valueOf(gPaiPm.getViewCount()));
        auction.setItemUrl(gPaiPm.getItemUrl());
        auction.setTitle(gPaiPm.getTitle());
        auction.setAddressDetail(gPaiPm.getAddressDetail());
        auction.setTypeName(getTypeNameInfo(gPaiPm));
        auction.setStartDate(gPaiPm.getBeginAt());
        auction.setEndDate(gPaiPm.getEndAt());
        auction.setStage(gPaiPm.getStage());
        auction.setSellType(gPaiPm.getSellType());
        auction.setArea(gPaiPm.getArea());
        auction.setRaisePrice(bigDecimalToStr(gPaiPm.getIncrement()));
        auction.setAmount(bigDecimalToStr(gPaiPm.getAmount()));
        auction.setBidNum(gPaiPm.getBidNum());///
        auction.setAppler(String.valueOf(gPaiPm.getParticipantNumber()));
        auction.setDeposit(bigDecimalToStr(gPaiPm.getDeposit()));
        auction.setReminder(String.valueOf(gPaiPm.getRemindCount()));
        auction.setConsultPrice(bigDecimalToStr(gPaiPm.getRefPrice()));
        auction.setCurrentPrice(bigDecimalToStr(gPaiPm.getCurrentPrice()));
        auction.setMarketPrice(bigDecimalToStr(gPaiPm.getRefPrice()));
        auction.setDeleteFlag(0);
        auction.setStatus("done");
        auction.setCreateTime(new Date());


        //获取公拍网相关的数据
        List<TMapAuction> mapAuctionList = mapAuctionDao.getByrelevanceId(String.valueOf(gPaiPm.getId()),GPAI_SOURCE);
        if(mapAuctionList!=null&&mapAuctionList.size()>0){
            auction.setId(mapAuctionList.get(0).getId());
            auction.setUpdateTime(new Date());
            auction.setCreateTime(mapAuctionList.get(0).getCreateTime());
        }


        mapAuctionDao.saveAndFlush(auction);


    }

    private String  getTypeNameInfo(GPaiPm gPaiPm) {

        if(gPaiPm.getTitle().contains("厂房")||gPaiPm.getTitle().contains("工业")){

            return "工业用房";
        }

        if(gPaiPm.getTitle().contains("商铺")){

            return "商业用房";
        }

        return "房产".equals(gPaiPm.getTypeName())?"住宅用房":gPaiPm.getTypeName();
    }


    private  String bigDecimalToStr(BigDecimal decimal){

        if(decimal==null){
            return null;
        }

        return String.valueOf(decimal);
    }






    @Override
    public GPaiPm findByItemId(Integer itemId) {
        return gPaiPmDao.findByItemId(itemId);
    }

    @Override
    public boolean updateContent(Response response) {
        String content = response.getContent();
       /* JSONObject result;
        try {
            result = JSONObject.parseObject(content);
        } catch (Exception e) {
            e.printStackTrace();
            content = content.replace("\"itemType\":", "\"itemType\":\"\"");
            System.out.println(content);
            result = JSONObject.parseObject(content);
        }
        log.info(result.toJSONString());
        if (!result.containsKey("code") || !result.getInteger("code").equals(0)) {
            return false;
        }
        JSONObject data = result.getJSONObject("data");
        Map<String, Object> map = response.getMeta();
        String itemId = (String) response.getMeta().get("itemId");

        GPaiPm gPaiPm = gPaiPmDao.findByItemId(Integer.parseInt(itemId));
        if (gPaiPm == null) {
            return false;
        }
        gPaiPm.setTitle(data.getString("itemName"));
        if (data.containsKey("priceStart") && StringUtils.isNotEmpty(data.getString("priceStart"))) {
            gPaiPm.setStartPrice(new BigDecimal(data.getString("priceStart")));
        }
        if (data.containsKey("priceAppraise") && StringUtils.isNotEmpty(data.getString("priceAppraise"))) {
            gPaiPm.setRefPrice(new BigDecimal(data.getString("priceAppraise")));
        }
        if ("最新价".equals(data.getString("priceDesc"))) {
            gPaiPm.setCurrentPrice(new BigDecimal(data.getString("priceShow")));
        } else if ("成交价".equals(data.getString("priceDesc"))) {
            gPaiPm.setAmount(new BigDecimal(data.getString("priceShow")));
        }
        if (data.containsKey("priceStep") && StringUtils.isNotEmpty(data.getString("priceStep"))) {
            gPaiPm.setIncrement(new BigDecimal(data.getString("priceStep")));
        }
        if (data.containsKey("depositShu") && StringUtils.isNotEmpty(data.getString("depositShu"))) {
            gPaiPm.setDeposit(new BigDecimal(data.getString("depositShu")));
        }
        if (data.containsKey("catelogTimeStart") && StringUtils.isNotEmpty(data.getString("catelogTimeStart"))) {
            gPaiPm.setBeginAt(data.getString("catelogTimeStart"));
        }
        if (data.containsKey("catelogTimeEnd") && StringUtils.isNotEmpty(data.getString("catelogTimeEnd"))) {
            gPaiPm.setEndAt(data.getString("catelogTimeEnd"));
        } else if (data.containsKey("webAuctionTime") && StringUtils.isNotEmpty(data.getString("webAuctionTime"))) {
            gPaiPm.setEndAt(data.getString("webAuctionTime"));
        } else if (data.containsKey("auctionJoinEnd") && StringUtils.isNotEmpty(data.getString("auctionJoinEnd"))) {
            gPaiPm.setEndAt(data.getString("auctionJoinEnd"));
        }
        gPaiPm.setContactName(data.getString("contactName"));
        gPaiPm.setContactPhone(data.getString("contactTel"));
        gPaiPm.setParticipantNumber(data.getInteger("joinCount"));
        gPaiPm.setRemindCount(data.getInteger("favCount"));
        gPaiPm.setViewCount(data.getInteger("viewCount"));
        JSONObject extra = new JSONObject();
        extra.put("imageUrls", data.getJSONArray("imgs"));
        extra.put("bidDetails", data.getJSONArray("bidDetail"));
        gPaiPm.setExtra(extra.toJSONString());
        gPaiPm.setCourtName(data.getString("organName"));
        gPaiPm.setItemMode(data.getString("itemMode"));
        gPaiPm.setState(data.getString("catelogState"));
        gPaiPm.setStateDesc(data.getString("stateDesc"));
        gPaiPm.setBidMode(data.getString("bidMode"));
        if (data.containsKey("companyName") && StringUtils.isNotEmpty(data.getString("companyName"))) {
            gPaiPm.setAgency(data.getString("companyName"));
        } else if (data.containsKey("") && StringUtils.isNotEmpty(data.getString(""))) {
            gPaiPm.setAgency(data.getString("Company_Name"));
        }
        gPaiPm.setUpdateTime(new Date());*/
//        gPaiPmDao.save(gPaiPm);
        return true;
    }

    @Override
    public Page<GPaiPm> findNeedToUpdate(int page, int size) {
        //return gPaiPmDao.findByTitleIsNull(new PageRequest(page, size));
        return gPaiPmDao.findByEndAtIsNull(new PageRequest(page, size));
    }

    @Override
    public List<GPaiAreaModel> getAllCities() {
        List<JSONObject> provinces = getAllProvinces();
        List<GPaiAreaModel> dataList = new ArrayList<>();

        for (JSONObject province : provinces) {
            List<GPaiAreaModel> newCityList = new ArrayList<>();
            newCityList = getCities(province.getInteger("id"), province.getString("name"), newCityList);
            dataList.addAll(newCityList);
        }

        gPaiPmAreaDao.save(dataList);
        return dataList;
    }

    @Override
    public void setContent(String newUrl, GPaiPm gPaiPm, CloseableHttpClient httpClient) {
        String content = "";
        try{
            HttpGet httpGet = new HttpGet(newUrl);
            httpClient.execute(httpGet);
            CloseableHttpResponse execute = httpClient.execute(httpGet);
            content= EntityUtils.toString(execute.getEntity());

        }catch (Exception e){
            e.printStackTrace();
        }

        JSONObject result;
        try {
            result = JSONObject.parseObject(content);
        } catch (Exception e) {
            e.printStackTrace();
            content = content.replace("\"itemType\":", "\"itemType\":\"\"");
            System.out.println(content);
            result = JSONObject.parseObject(content);
        }
        log.info(result.toJSONString());

        JSONObject data = result.getJSONObject("data");

        gPaiPm.setTitle(data.getString("itemName"));
        if (data.containsKey("priceStart") && StringUtils.isNotEmpty(data.getString("priceStart"))) {
            gPaiPm.setStartPrice(new BigDecimal(data.getString("priceStart")));
        }
        if (data.containsKey("priceAppraise") && StringUtils.isNotEmpty(data.getString("priceAppraise"))) {
            gPaiPm.setRefPrice(new BigDecimal(data.getString("priceAppraise")));
        }
        if ("最新价".equals(data.getString("priceDesc"))) {
            gPaiPm.setCurrentPrice(new BigDecimal(data.getString("priceShow")));
        } else if ("成交价".equals(data.getString("priceDesc"))) {
            gPaiPm.setAmount(new BigDecimal(data.getString("priceShow")));
        }
        if (data.containsKey("priceStep") && StringUtils.isNotEmpty(data.getString("priceStep"))) {
            gPaiPm.setIncrement(new BigDecimal(data.getString("priceStep")));
        }
        if (data.containsKey("depositShu") && StringUtils.isNotEmpty(data.getString("depositShu"))) {
            gPaiPm.setDeposit(new BigDecimal(data.getString("depositShu")));
        }


        gPaiPm.setBeginAt(getBeginAtInfo(data));
        gPaiPm.setEndAt(getEndAtInfo(data));
        gPaiPm.setContactName(data.getString("contactName"));
        gPaiPm.setItemType(data.getString("itemType"));
        gPaiPm.setContactPhone(data.getString("contactTel"));
        gPaiPm.setParticipantNumber(data.getInteger("joinCount"));
        gPaiPm.setRemindCount(data.getInteger("favCount"));
        gPaiPm.setViewCount(data.getInteger("viewCount"));

        JSONObject extra = new JSONObject();
        extra.put("imageUrls", data.getJSONArray("imgs"));
        extra.put("bidDetails", data.getJSONArray("bidDetail"));
        gPaiPm.setExtra(extra.toJSONString());
        gPaiPm.setCourtName(data.getString("organName"));
        gPaiPm.setItemMode(data.getString("itemMode"));
        gPaiPm.setState(data.getString("catelogState"));
        gPaiPm.setStateDesc(data.getString("stateDesc"));
        gPaiPm.setBidMode(data.getString("bidMode"));
        gPaiPm.setStage(data.getString("bidCountDesc"));
        gPaiPm.setSellType(data.getString("AuctionBidType"));
        gPaiPm.setBidNum(data.getString("bidCount"));
        gPaiPm.setArea(data.getString("itemNumber"));




        if (data.containsKey("companyName") && StringUtils.isNotEmpty(data.getString("companyName"))) {
            gPaiPm.setAgency(data.getString("companyName"));
        } else if (data.containsKey("") && StringUtils.isNotEmpty(data.getString(""))) {
            gPaiPm.setAgency(data.getString("Company_Name"));
        }
        gPaiPm.setUpdateTime(new Date());
    }

    private String getEndAtInfo(JSONObject data) {


        if (data.containsKey("catelogTimeEnd") && StringUtils.isNotEmpty(data.getString("catelogTimeEnd"))&&isValid(data.getString("catelogTimeEnd"))) {
            return data.getString("catelogTimeEnd");
        }
        if (data.containsKey("webAuctionTime") && StringUtils.isNotEmpty(data.getString("webAuctionTime"))) {
            return data.getString("webAuctionTime");
        }

        return null;
    }

    private String getBeginAtInfo(JSONObject data) {

        if (data.containsKey("catelogTimeStart") && StringUtils.isNotEmpty(data.getString("catelogTimeStart"))&&isValid(data.getString("catelogTimeStart"))) {
            return data.getString("catelogTimeStart");
        }

        if (data.containsKey("auctionJoinEnd") && StringUtils.isNotEmpty(data.getString("auctionJoinEnd"))) {
            return data.getString("auctionJoinEnd");
        }

        return null;
    }

    private boolean isValid(String data) {


        return !"1900-1-1".equals(data);
    }

    private List<JSONObject> getAllProvinces() {
        List<JSONObject> dataList = new ArrayList<>();
        HttpUtilNewModel res = HttpUtilNew.get(SEARCH_URL);
        if (res == null || 200 != res.getStatusCode()) {
            return dataList;
        }
        if (StringUtils.isEmpty(res.getHtml())) {
            return dataList;
        }
        JSONObject result = JSON.parseObject(res.getHtml());
        if (!result.containsKey("code") || !result.getInteger("code").equals(0)) {
            return dataList;
        }
        JSONArray cityList = result.getJSONObject("data").getJSONArray("cityList");
        for (Object o : cityList) {
            JSONObject city = (JSONObject) o;
            JSONObject province = new JSONObject();
            province.put("id", city.getString("id"));
            province.put("name", city.getString("cName"));
            dataList.add(province);
        }
        return dataList;
    }

    private List<GPaiAreaModel> getCities(Integer provinceId, String provinceName, List<GPaiAreaModel> cityModels) {
        HttpUtilNewModel res = HttpUtilNew.get(SEARCH_URL + "?cityNum=" + provinceId);
        if (res == null || 200 != res.getStatusCode()) {
            return cityModels;
        }
        if (StringUtils.isEmpty(res.getHtml())) {
            return cityModels;
        }
        JSONObject result = JSON.parseObject(res.getHtml());
        if (!result.containsKey("code") || !result.getInteger("code").equals(0)) {
            return cityModels;
        }
        JSONArray cityList = result.getJSONObject("data").getJSONArray("city02");
        for (Object o : cityList) {
            JSONObject city = (JSONObject) o;

            List<GPaiAreaModel> gPaiAreaModels = getAreas(city.getInteger("id"), city.getString("cName"), provinceId, provinceName);
            cityModels.addAll(gPaiAreaModels);
        }
        return cityModels;
    }

    private List<GPaiAreaModel> getAreas(Integer id, String cName, Integer provinceId, String provinceName) {
        List<GPaiAreaModel> dataList = new ArrayList<>();
        HttpUtilNewModel res = HttpUtilNew.get(SEARCH_URL + "?cityNum=" + id);
        if (res == null || 200 != res.getStatusCode()) {
            return dataList;
        }
        if (StringUtils.isEmpty(res.getHtml())) {
            return dataList;
        }
        JSONObject result = JSON.parseObject(res.getHtml());
        if (!result.containsKey("code") || !result.getInteger("code").equals(0)) {
            return dataList;
        }
        JSONArray cityList = result.getJSONObject("data").getJSONArray("city03");
        if(cityList != null && cityList.size() > 0) {
            for (Object o : cityList) {
                GPaiAreaModel gPaiAreaModel = new GPaiAreaModel();
                JSONObject city = (JSONObject) o;
                gPaiAreaModel.setAreaId(city.getInteger("id"));
                gPaiAreaModel.setArea(city.getString("cName"));
                gPaiAreaModel.setProvince(provinceName);
                gPaiAreaModel.setProvinceId(provinceId);
                gPaiAreaModel.setCity(cName);
                gPaiAreaModel.setCityId(id);
                gPaiAreaModel.setCreateTime(new Date());

                dataList.add(gPaiAreaModel);
            }
        }

        return  dataList;
    }

}
