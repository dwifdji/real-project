package com._360pai.crawler.crawlers.jdPm;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import com._360pai.arch.core.sysconfig.properties.SystemProperties;
import com._360pai.crawler.common.dto.AddressDetail;
import com._360pai.crawler.common.util.*;
import com._360pai.crawler.config.AsyncConfig;
import com._360pai.crawler.dao.auction.TMapAuctionDao;
import com._360pai.crawler.dao.jdPm.JdSearchUrlDao;
import com._360pai.crawler.model.auction.TMapAuction;
import com._360pai.crawler.model.jdPm.JDSearchUrl;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
@Crawler(name = "jdTransactionDataSiFa", useUnrepeated = false)
public class JDTransactionDataCrawlerSiFa extends BaseSeimiCrawler {

    private static final String DETAILURL = "https://paimai.jd.com/";


    @Autowired
    private AsyncConfig asyncConfig;

    @Autowired
    private TMapAuctionDao tMapAuctionDao;
    @Autowired
    private JdSearchUrlDao jdSearchUrlDao;


    @Autowired
    private SystemProperties systemProperties;

    private static final String  PAGEURL = "https://auction.jd.com/getJudicatureList.html?limit=40&paimaiStatus=2&childrenCateId=";


    private static final String  NUM_URL = "https://auction.jd.com/getJudicatureList.html?limit=1&paimaiStatus=2&childrenCateId=";



    @Override
    public String[] startUrls() {
        return new String[0];
    }

    @Override
    public void start(Response response) {

        //更新爬取url信息
        updateJdUrlInfo(response);

        //获取将要爬取的url
        List<JDSearchUrl> all = jdSearchUrlDao.getTodoUrl();

        try {
            for(int j = 0, len = all.size(); j < len; j++) {
                JDSearchUrl jdSearchUrl = all.get(j);
                Map<String, Object> params = new HashMap<>();
                params.put("categoryName", jdSearchUrl.getCategoryName());
                params.put("jdSearchUrl", jdSearchUrl);
                push(Request.build(jdSearchUrl.getSearchUrl(),
                        JDTransactionDataCrawlerSiFa::getCategoryData).setMeta(params));

                //爬取过的url 更新其状态
                updateJdUrlStatus(jdSearchUrl);

            }
        } catch (Exception e) {

            logger.error("处理组合类别数据异常{}", e);
        }

    }

    private void updateJdUrlStatus(JDSearchUrl jdSearchUrl) {


        jdSearchUrl.setStatus("done");
        jdSearchUrl.setUpdateTime(new Date());

        jdSearchUrlDao.saveAndFlush(jdSearchUrl);
    }


    private void updateJdUrlInfo(Response response) {
        JXDocument doc = response.document();
        saveAllSearchUrl(doc);

    }


    public void getCategoryData(Response response){

        JSONObject json = JSONObject.parseObject(response.getContent());
        Integer total = Integer.parseInt(json.get("total").toString()) ;
        Map<String, Object> meta = response.getMeta();

        JDSearchUrl model = (JDSearchUrl) meta.get("jdSearchUrl");
        if(total != 0) {
            model.setTotalNum(total);
            jdSearchUrlDao.saveAndFlush(model);
            System.out.println("该类别所有数据是" + total + "请求路径是" + response.getUrl());
            try {
                for(int i = 1; i < total / 40 + 2 ; i++) {
                    Map<String, Object> params = new HashMap<>();
                    params.put("categoryName", response.getMeta().get("categoryName"));
                    push(Request.build(response.getUrl() + "&page=" + i, JDTransactionDataCrawlerSiFa::getDetail).setMeta(params));
                    logger.info("开始爬取第" + i + "页的数据");

                    Thread.sleep(100);
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("处理分页数据异常{}", e.getMessage());
            }
        }
    }


    public void getDetail(Response response)   {


        asyncConfig.aliZcListExecutor().execute(new Runnable() {
            @Override
            public void run() {
                getPageData(response);

            }
        });


    }


    public void getPageData(Response response){


        try {
            JSONObject json = JSONObject.parseObject(response.getContent());

            JSONArray jsonArray = (JSONArray) JSONArray.parse(json.get("ls").toString());
            for(int i = 0; i < jsonArray.size(); i++){
                JSONObject job = jsonArray.getJSONObject(i);

                String id = StringFormatUtil.changeObjectToStr(job.get("id"));

                Map<String, Object> params =  new HashMap<>();

                params.put("id", id);
                //判断是否已经存在对象
                TMapAuction oneByCode = tMapAuctionDao.getOneByCode(id, "jd");
                if(oneByCode == null) {
                    String AUCTIONDETAIL = "https://api.m.jd.com/api?appid=paimai&functionId=getProductBasicInfo&body=%7b%22paimaiId%22%3a" + "INFOID" +"%7d&loginType=3";
                    oneByCode = getDetailData(AUCTIONDETAIL.replace("INFOID", id), id, (String) response.getMeta().get("categoryName"));
                    //添加到list
                    if(oneByCode != null) {
                        tMapAuctionDao.save(oneByCode);
                    }
                }else {
                    System.out.println("已经存在数据是" + id);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("处理详情页数据异常{}", e.getMessage());
        }
    }









    public TMapAuction getDetailData(String newUrl, String id, String categoryName){
        try {

            String html =  HttpsUtil.get(newUrl, null);

            JSONObject job = JSONObject.parseObject(html);
            JSONObject json = JSONObject.parseObject(job.getString("data"));
            JSONObject productAddressJson = JSONObject.parseObject(json.getString("productAddressResult"));
            TMapAuction tMapAuction = new TMapAuction();
            tMapAuction.setTypeName(categoryName);
            tMapAuction.setStartingPrice(new BigDecimal(json.getString("startPrice")).toPlainString());
            tMapAuction.setTitle(json.getString("title"));
            tMapAuction.setConsultPrice(new BigDecimal(json.getString("assessmentPrice")).toPlainString());
            tMapAuction.setRaisePrice(json.getString("priceLowerOffset"));
            tMapAuction.setDeposit(json.getString("ensurePrice"));
            tMapAuction.setProName(productAddressJson.getString("province"));
            tMapAuction.setCityName(productAddressJson.getString("city"));
            tMapAuction.setAreaName(productAddressJson.getString("county"));
            tMapAuction.setStartDate(changeTimeToDate(json.getString("startTime")));
            tMapAuction.setEndDate(changeTimeToDate(json.getString("endTime")));
            tMapAuction.setEndDate(changeTimeToDate(json.getString("endTime")));
            tMapAuction.setCreateTime(new Date());
            tMapAuction.setUpdateTime(new Date());
            tMapAuction.setDeleteFlag(0);
            tMapAuction.setTypeCode("1234");
            tMapAuction.setSellType("拍卖");
            tMapAuction.setSource("jd");
            tMapAuction.setItemUrl("https://paimai.jd.com/"+id);
            tMapAuction.setRelevanceId(id);
            tMapAuction.setStage("第" + json.getString("paimaiTimes") + "次拍卖");
            tMapAuction.setStatus("done");
            tMapAuction.setArea(getAreaInfo(id,json));

            //获取其他详情数据
            getAnotherDetailData(id,tMapAuction);
            String addressDetailSt = tMapAuction.getProName() + tMapAuction.getCityName() + tMapAuction.getAreaName() + productAddressJson.getString("address");
            tMapAuction.setAddressDetail(addressDetailSt);
            Map<String,String> latLngInfo = ComUtils.getLatLngInfo(addressDetailSt,tMapAuction.getCityName(),systemProperties);
            String lat = null;
            String lng = null;
            if(latLngInfo!=null){
                lat = latLngInfo.get("lat");
                lng = latLngInfo.get("lng");
            }
            tMapAuction.setLat(StringUtils.isEmpty(lat)?null:new BigDecimal(lat));
            tMapAuction.setLng(StringUtils.isEmpty(lng)?null:new BigDecimal(lng));

            return tMapAuction;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("报错id" + id);
            logger.error("处理详情页数据异常{}", e.getMessage());
        }
        return null;
    }


    /**
     *
     *获取面积信息
     *
     */
    private String getAreaInfo(String id,JSONObject json) {

        String albumId = StringFormatUtil.changeObjectToStr(json.get("albumId"));

        // 查找房屋面积
        List<String> areaStListUrl = new ArrayList<>();
        List<String> areaTableListUrl = new ArrayList<>();

        String announcementUrl = "https://api.m.jd.com/api?appid=paimai&functionId=queryAnnouncement&body=%7b%22albumId%22%3a" + "ALBUMID" + "%7d&loginType=3";
        announcementUrl = announcementUrl.replace("ALBUMID", albumId);
        String noticeUrl = "https://api.m.jd.com/api?appid=paimai&functionId=queryNotice&body=%7b%22paimaiId%22%3a" + "INFOID" + "%7d&loginType=3";
        noticeUrl = noticeUrl.replace("INFOID", id);
        areaStListUrl.add(announcementUrl);
        areaStListUrl.add(noticeUrl);
        String tableUrl = "https://api.m.jd.com/api?appid=paimai&functionId=queryProductDescription&body=%7b%22paimaiId%22:"+"INFOID" + ",%22source%22:0%7d&loginType=3";
        tableUrl = tableUrl.replace("INFOID", id);
        areaTableListUrl.add(tableUrl);

        return ComUtils.getAreaInfo(areaStListUrl, areaTableListUrl, ComUtils.URL_TYPE);

    }


    private void getAnotherDetailData(String id, TMapAuction mapAuction) {
        try {

            String LAWYERAUCTIONDETAIL = "https://api.m.jd.com/api?appid=paimai&functionId=getPaimaiRealTimeData&body=%7b%22end%22:9,%22paimaiId%22:"+"INFOID" + ",%22source%22:0,%22start%22:0%7d&loginType=3";


            String detailUrl =  LAWYERAUCTIONDETAIL.replace("INFOID", id);


            String html =  HttpsUtil.get(detailUrl, null);
            JSONObject json = JSON.parseObject(html);
            JSONObject newJson = JSON.parseObject(json.getString("data"));

            mapAuction.setAppler(newJson.getString("accessEnsureNum"));
            mapAuction.setLooker(newJson.getString("accessNum"));
            BigDecimal currentPrice =  new BigDecimal(newJson.getString("currentPrice")) ;
            mapAuction.setAmount(currentPrice.toPlainString());
            mapAuction.setCurrentPrice(currentPrice.toPlainString());
            mapAuction.setMarketPrice(currentPrice.toPlainString());

            String confirmationUrl = StringFormatUtil.changeObjectToStr(newJson.get("confirmationUrl"));
            mapAuction.setStatus(StringUtils.isNotBlank(confirmationUrl) ? "done" : "failure");

            //获取
            String bidCount = StringFormatUtil.changeObjectToStr(newJson.get("bidCount"));
            mapAuction.setBidNum(bidCount);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("获取详情异常", e.getMessage());
        }
    }


    private String changeTimeToDate(String beginTime) {
        long timeTimestamp = Long.parseLong(beginTime);
        return DateUtil.format(new Date(timeTimestamp),DateUtil.STYLE_1);
    }


    private void saveAllSearchUrl(JXDocument doc) {
        List<JXNode> jxNodes = doc.selN("//div[@class='s-line s-location']/div/div[2]/div/dl/dd/a/@data-id");
        List<JXNode> provinces = doc.selN("//div[@class='s-line s-location']/div/div[2]/div/dl/dd/a");
        List<JXNode> newJxNodes = doc.selN("//div[@class='s-line s-category']/div[1]/div[2]/div/ul/li");

        for(int j = 1, len = newJxNodes.size(); j < len; j++) {
            String node = newJxNodes.get(j).sel("/@data-childrencateid").get(0).toString();
            String categoryName = newJxNodes.get(j).sel("/a/text()").get(0).toString();

            if("住宅用房".equals(categoryName) || "商业用房".equals(categoryName)
                    || "工业用房".equals(categoryName) || "土地".equals(categoryName)) {
                for (int i = 1; i < jxNodes.size(); i++) {
                    String cityUrl = "https://d.jd.com/area/get?"+"&fid=" + jxNodes.get(i);
                    String province = provinces.get(i).sel("/text()").get(0).toString();

                    System.out.println(categoryName + province + cityUrl);
                    getAllCity(cityUrl, categoryName, node, province);
                }
            }
        }
    }


    public void getAllCity(String newUrl,String categoryName,String categoryId,String province) {
        String result = HttpsUtil.get(newUrl, null);

        try {
            JSONArray jsonArray = JSONArray.parseArray(result);
            for(int i = 0, len = jsonArray.size(); i < len; i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                String cityId = json.getString("id");
                String cityName = json.getString("name");

                JDSearchUrl byCode = jdSearchUrlDao.findByCode(cityId, categoryName);

                //拼接搜索的url
                String searchUrl = PAGEURL + categoryId + "&cityId=" + cityId;

                //获取该url 查询的总数
                String numResult = HttpsUtil.get(NUM_URL+ categoryId + "&cityId=" + cityId, null);

                JSONObject numJson = JSONObject.parseObject(numResult);

                String num = numJson.getString("total");

                //当已经有记录并且数量不相同时更新记录
                JDSearchUrl jdSearchUrl = new JDSearchUrl();

                //存在记录 并且总数相等直接返回
                if(byCode !=null && String.valueOf(byCode.getTotalNum()).equals(num)){
                    return;
                }

                //存在记录 并且总数不同更新记录
                if(byCode !=null && !String.valueOf(byCode.getTotalNum()).equals(num)){
                    jdSearchUrl.setId(byCode.getId());
                    jdSearchUrl.setStatus("todo");
                    jdSearchUrl.setUpdateTime(new Date());
                }



                jdSearchUrl.setCity(cityName);
                jdSearchUrl.setCityCode(cityId);
                jdSearchUrl.setCategoryName(categoryName);
                jdSearchUrl.setProvince(province);
                jdSearchUrl.setSearchUrl(searchUrl);
                jdSearchUrl.setCreateTime(new Date());
                jdSearchUrl.setTotalNum(Integer.valueOf(num));
                jdSearchUrl.setStatus("todo");

                jdSearchUrlDao.saveAndFlush(jdSearchUrl);


            }

        } catch (Exception e) {
            logger.error("处理分页数据异常{}", e);
        }

    }

}
