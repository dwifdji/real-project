package com._360pai.seimi.crawlers;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.http.HttpMethod;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import com._360pai.seimi.dao.RMFYSSTransactionDataBidRecordDao;
import com._360pai.seimi.dao.RMFYSSTransactionDataDao;
import com._360pai.seimi.model.AreaModel;
import com._360pai.seimi.model.JdPmTransactionDataLegal;
import com._360pai.seimi.model.RMFYSSTransactionData;
import com._360pai.seimi.model.RMFYSSTransactionDataBidRecord;
import com._360pai.seimi.util.DateUtil;
import com._360pai.seimi.util.StringFormatUtil;
import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * create by liuhaolei on 2018-03-21
 */
@Component
@Crawler(name = "rmfysswTransactionData", useUnrepeated = false)
public class RMFYSSWTransactionDataCrawler extends BaseSeimiCrawler {

    @Autowired
    private RMFYSSTransactionDataDao rmfyssTransactionDataDao;
    @Autowired
    private RMFYSSTransactionDataBidRecordDao rmfyssTransactionDataBidRecordDao;
    private static List<AreaModel> areaModels = new ArrayList<>();

    static {
        try {
            CookieStore cookieStore = new BasicCookieStore();
            CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
            String getUrl = "https://www1.rmfysszc.gov.cn/projects.shtml?dh=3&gpstate=1&wsbm_slt=1";
            HttpPost cityPost = new HttpPost("https://www1.rmfysszc.gov.cn/GetCity.shtml");
            HttpGet get = new HttpGet(getUrl);
            setHeadeParams(get);

            CloseableHttpResponse execute = httpClient.execute(get);
            String html = EntityUtils.toString(execute.getEntity());

            JXDocument doc = JXDocument.create(html);
            List<JXNode> cityNodes = doc.selN("//select[@id='city']/option");

            for(int j = 23; j < 24; j++) {
                String id = cityNodes.get(j).sel("/@value").get(0).toString();
                String province = cityNodes.get(j).sel("/text()").get(0).toString();
                getCityList(httpClient, cityPost, id, province, areaModels);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String[] startUrls() {
        return new String[0];
    }

    @Override
    public void start(Response response) {

        JXDocument doc = response.document();
        try {
            List<JXNode> jxNodes = doc.selN("//div[@id='Search_right']/div[1]/div/a");

            for(int i = 1; i < jxNodes.size(); i++) {
                String categoryId = jxNodes.get(i).sel("/@id").get(0).toString();
                String categoryName = jxNodes.get(i).sel("/text()").get(0).toString();

                for(int j = 0; j < areaModels.size(); j++) {
                    AreaModel areaModel = areaModels.get(j);
                    Map<String, Object> params = new HashMap<>();
                    params.put("categoryId", categoryId);
                    params.put("categoryName", categoryName);
                    params.put("areaModel", areaModel);

                    Map<String, String> paramsMap = getParams(areaModel);
                    paramsMap.put("type", (String) params.get("categoryId"));
                    paramsMap.put("state", "4");
                    paramsMap.put("page", "1");

                    Map<String, String> heards = getHeader();
                    push(Request.build("https://www1.rmfysszc.gov.cn/ProjectHandle.shtml", RMFYSSWTransactionDataCrawler::getCategoryList).
                        setHttpMethod(HttpMethod.POST).setParams(paramsMap).setHeader(heards).setMeta(params));

                        Thread.sleep(600);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private Map<String, String> getHeader() {
        Map<String, String> header = new HashMap<>();

        header.put("Accept", "application/json, text/javascript, */*; q=0.01");
        header.put("Accept-Encoding", "gzip, deflate, br");
        header.put("Cache-Control", "no-cache");
        header.put("Connection", "keep-alive");
        header.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        header.put("Cookie",  "UM_distinctid=1699ddd876123a-036519e40ce505-37664109-e1000-1699ddd87625fe; __jsluid=656617dd9eb33501a5103544a1fbfbe3; CNZZDATA3765988=cnzz_eid%3D1003298408-1553132628-https%253A%252F%252Fwww1.rmfysszc.gov.cn%252F%26ntime%3D1553132628; ASP.NET_SessionId=uzsuunejsow2bvzwuxcqfmcd; SF_cookie_9=76891161");
        header.put("Host", "www1.rmfysszc.gov.cn");
        header.put("Origin", "https://www1.rmfysszc.gov.cn");
        header.put("Pragma", "no-cache");
        header.put("Referer", "https://www1.rmfysszc.gov.cn/projects.shtml?dh=3&gpstate=1&wsbm_slt=1");
        header.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
        header.put("X-Requested-With", "XMLHttpRequest");

        return header;
    }

    private Map<String, String> getParams(AreaModel areaModel) {
        Map<String, String> params = new HashMap<>();

        params.put("name", "");
        params.put("area", areaModel.getArea());
        params.put("city", areaModel.getCity());   //类型
        params.put("city1", areaModel.getCity1());
        params.put("city2", areaModel.getCity2());
        params.put("time", "0");
        params.put("time1", "");
        params.put("time2", "");
        params.put("money", "");
        params.put("money1", "");
        params.put("number", "0");
        params.put("fid1", "");
        params.put("fid2", "");
        params.put("fid3", "");
        params.put("order", "0");
        params.put("include", "0");

        return params;
    }


    public void getCategoryList(Response response) {
         try {
            String html = response.getContent();

            JSONObject json = JSONObject.parseObject(html);

            JXDocument htmlDoc = JXDocument.create(json.getString("html"));

            List<JXNode> jxNodes = htmlDoc.selN("//div[@class='tip']");

            if(jxNodes.size() >= 1 && StringUtils.isBlank(json.getString("page"))) {
                return;
            }

            Integer totalPage = 0;
            if(StringUtils.isBlank(json.getString("page"))) {
                totalPage = 1;
            }else{
                JXDocument page = JXDocument.create(json.getString("page"));
                String totalPageSt = page.selN("//a[last()]/@onclick").get(0).toString();
                String subPage = totalPageSt.substring(totalPageSt.indexOf("(") + 1, totalPageSt.indexOf(")"));
                totalPage = StringUtils.isBlank(totalPageSt) ? 1 : Integer.parseInt(subPage);
            }

            totalPage = totalPage > 100 ? 100 : totalPage;
            AreaModel areaModel = (AreaModel) response.getMeta().get("areaModel");
                logger.info("该"+ areaModel.getCity() + areaModel.getCity1() + areaModel.getCity2() +"的分页总数是" +  totalPage);
                for(int i = 1; i < totalPage + 1; i++) {
                    Map<String, String> paramsMap = getParams(areaModel);
                    paramsMap.put("type", (String) response.getMeta().get("categoryId"));
                    paramsMap.put("state", "4");
                    paramsMap.put("page", String.valueOf(i));

                    logger.info("开始爬取" + response.getMeta().get("categoryName")+ "类别的第" + i + "页数据");
                    push(Request.build("https://www1.rmfysszc.gov.cn/ProjectHandle.shtml", RMFYSSWTransactionDataCrawler::getPageList).
                    setHttpMethod(HttpMethod.POST).setParams(paramsMap).
                    setHeader(response.getRequest().getHeader()).setMeta(response.getMeta()));

                    Thread.sleep(600);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取分页数据异常{}", e.getMessage());
        }
    }

    public static void getCityList(CloseableHttpClient httpClient, HttpPost cityPost, String id, String province, List<AreaModel> areaModels) {
        setHeadeParams(cityPost);

        try{
            List<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("id", id));   //类型

            cityPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            CloseableHttpResponse httpResponse = httpClient.execute(cityPost);
            String html = EntityUtils.toString(httpResponse.getEntity());

            JXDocument doc = JXDocument.create(html);
            List<JXNode> jxNodes = doc.selN("//option");

            if(jxNodes != null && jxNodes.size() > 1) {
                for(int i = 1, len = jxNodes.size(); i < len; i++) {
                    String cityId = jxNodes.get(i).sel("/@value").get(0).toString();
                    String city = jxNodes.get(i).sel("/text()").get(0).toString();
                    getCityList2(httpClient, cityPost, cityId, province, city, areaModels);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void getCityList2(CloseableHttpClient httpClient, HttpPost cityPost,
                              String id, String province, String city, List<AreaModel> areaModels) {
        try{
            List<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("id", id));   //类型

            cityPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            CloseableHttpResponse httpResponse = httpClient.execute(cityPost);
            String html = EntityUtils.toString(httpResponse.getEntity());


            JXDocument doc = JXDocument.create(html);
            List<JXNode> jxNodes = doc.selN("//option");
            if(jxNodes != null && jxNodes.size() > 0) {
                for(int i = 1, len = jxNodes.size(); i < len; i++) {
                    String region = jxNodes.get(i).sel("/text()").get(0).toString();
                    if(!"市辖区".equals(region)) {
                        AreaModel areaModel = new AreaModel();
                        areaModel.setArea(region);
                        areaModel.setCity(province);
                        areaModel.setCity1(city);
                        areaModel.setCity2(region);

                        areaModels.add(areaModel);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void getPageList(Response response) {
        try {
            logger.info("爬取的最新的分页路径是第" + response.getRequest().getParams().get("page") + "页");
            JSONObject json = JSONObject.parseObject(response.getContent());
            JXDocument page = JXDocument.create(json.getString("html"));

            List<JXNode> jxNodes = page.selN("//div[@class='tip']");
            if(jxNodes.size() >= 1 && StringUtils.isBlank(json.getString("page"))) {
                return;
            }

            List<JXNode> newJxNodes = page.selN("//div[@class='product']");

            Map<String, Object> params = response.getMeta();
            List<RMFYSSTransactionData> rmfyssTransactionDataList = new ArrayList<>();
            for(int j = 0; j < newJxNodes.size(); j++) {
                String id = newJxNodes.get(j).sel("/@id").get(0).toString();
                String imgUrl = newJxNodes.get(j).sel("/div[1]/a/img/@src").get(0).toString();

                RMFYSSTransactionData oneByCode = rmfyssTransactionDataDao.getOneByCode(id);
                if(oneByCode == null) {
                    Map<String, Object> newParams = new HashMap<>();
                    newParams.put("categoryId", params.get("categoryId"));
                    newParams.put("categoryName", params.get("categoryName"));
                    newParams.put("areaModel", params.get("areaModel"));
                    newParams.put("id", id);
                    newParams.put("imgUrl", imgUrl);

                    String detailUrl = "https://www.rmfysszc.gov.cn/statichtml/rm_obj/";

                    RMFYSSTransactionData rmfyssTransactionData = getNewDetail(detailUrl + id + ".shtml", newParams);

                    if(rmfyssTransactionData != null) {
                            rmfyssTransactionDataList.add(rmfyssTransactionData);
                    }
                 }
            }
                rmfyssTransactionDataDao.save(rmfyssTransactionDataList);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取分页数据异常{}", e.getMessage());
        }
    }

    private RMFYSSTransactionData getNewDetail(String getUrl, Map<String, Object> params) {

        try{
            CloseableHttpClient httpClient = HttpClients.custom().build();

            HttpGet get = new HttpGet(getUrl);
            setNewGetHeader(get);

            CloseableHttpResponse execute = httpClient.execute(get);
            String result = EntityUtils.toString(execute.getEntity(),"UTF-8");

            JXDocument doc = JXDocument.create(result);

            String startPrice = StringFormatUtil.
                    changeObjectToStr(doc.selNOne("//div[@id='MainRight']/div[1]/div/span/text()"));
            String assessmentPrice = StringFormatUtil.
                    changeObjectToStr(doc.selNOne("//div[@id='bg1']/div[1]/table/tbody/tr[1]/td/span[2]/text()"));
            String ensurePrice = StringFormatUtil.
                    changeObjectToStr(doc.selNOne("//div[@id='bg1']/div[1]/table/tbody/tr[2]/td/span[2]/text()"));
            String messageDate = StringFormatUtil.
                    changeObjectToStr(doc.selNOne("//div[@id='bg1']/div[1]/table/tbody/tr[3]/td/span/text()"));
            String aucitonTimes = StringFormatUtil.
                    changeObjectToStr(doc.selNOne("//div[@id='bg1']/div[1]/table/tbody/tr[4]/td/span/text()"));
            String shopName = StringFormatUtil.
                    changeObjectToStr(doc.selNOne("//div[@id='bg1']/div[2]/table/tbody/tr[1]/td/span/text()"));
            String shopPeople = StringFormatUtil.
                    changeObjectToStr(doc.selNOne("//div[@id='bg1']/div[2]/table/tbody/tr[2]/td/span/text()"));
            String shopPhone = StringFormatUtil.
                    changeObjectToStr(doc.selNOne("//div[@id='bg1']/div[2]/table/tbody/tr[3]/td/span/text()"));
            String title = StringFormatUtil.
                    changeObjectToStr(doc.selNOne("//title/text()"));
//            String timeSt = StringFormatUtil.
//                    changeObjectToStr(doc.selNOne("//div[@id='pmgg']/div/p[3]/span[position() < 18]/allText()"));

//            String beginTime = timeSt.substring(timeSt.indexOf("将于") + 2, timeSt.indexOf("至"));
//
//            String endTime = timeSt.substring(timeSt.indexOf("至") + 1, timeSt.indexOf("止"));
//            rmfyssTransactionData.setBeginTime(beginTime);
//            rmfyssTransactionData.setEndTime(endTime);

            RMFYSSTransactionData rmfyssTransactionData = new RMFYSSTransactionData();
            rmfyssTransactionData.setShopName(shopName.substring(shopName.indexOf(": ") + 2));
            rmfyssTransactionData.setStartPrice(startPrice.substring(startPrice.indexOf("￥") + 1));

            rmfyssTransactionData.setImgUrl((String) params.get("imgUrl"));
            rmfyssTransactionData.setCategoryName((String) params.get("categoryName"));
            AreaModel areaModel = (AreaModel) params.get("areaModel");
            rmfyssTransactionData.setProvince(areaModel.getCity());
            rmfyssTransactionData.setCity(areaModel.getCity1());
            rmfyssTransactionData.setCounty(areaModel.getCity2());
            rmfyssTransactionData.setAssessmentPrice(assessmentPrice);
            rmfyssTransactionData.setEnsurePrice(ensurePrice);
            rmfyssTransactionData.setAuctionTimes(aucitonTimes.substring(aucitonTimes.indexOf(": ") + 2));
            rmfyssTransactionData.setCode((String) params.get("id"));
            rmfyssTransactionData.setTitle(title);
            rmfyssTransactionData.setMessageTime(messageDate.substring(messageDate.indexOf(": ") + 2));
            rmfyssTransactionData.setShopPeople(shopPeople);
            rmfyssTransactionData.setCreateTime(new Date());
            rmfyssTransactionData.setShopPhone(shopPhone.substring(shopPhone.indexOf(": ") + 2));

            //
            String newHtml = result.replaceAll(" ", "");
            int index = newHtml.indexOf("GetbmNumber.shtml");
            String substring = "";
            if(index > 0) {
                substring  = newHtml.substring(index, newHtml.length());
            }

            int beginIndex = substring.indexOf(";") + 1;
            int endIndex = substring.indexOf("$");

            String newSubSt = substring.substring(beginIndex, endIndex);

            String pid = newSubSt.substring(newSubSt.indexOf("varpid='") + 8, newSubSt.indexOf("';"));
            String oid = newSubSt.substring(newSubSt.indexOf("varoid='") + 8, newSubSt.length() - 4);

            setNoticeNumber("https://www1.rmfysszc.gov.cn/Object/GetCollection.shtml?id=" + params.get("id"), httpClient, rmfyssTransactionData);

            setBMNumber("https://www1.rmfysszc.gov.cn/Object/GetbmNumber.shtml?pid=" + pid + "&oid=" + oid, httpClient, rmfyssTransactionData);

            setCurrentPrice("https://www1.rmfysszc.gov.cn/Object/Finish.shtml?oid=" + params.get("id") + "&pid=" + pid, httpClient, rmfyssTransactionData);

            setPriorityFlag("https://www1.rmfysszc.gov.cn/Object/yxq.shtml?pid=" + pid + "&oid=" + oid, httpClient, rmfyssTransactionData);
            String bidListUrl = "https://www1.rmfysszc.gov.cn/object/Record/";

            Map<String, Object> newParams = new HashMap<>();
            newParams.put("id", params.get("id"));

            push(Request.build(bidListUrl + params.get("id") + ".shtml", RMFYSSWTransactionDataCrawler::getBidList).setMeta(newParams));

            return rmfyssTransactionData;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("获取详情数据异常");
        }

        return null;
    }

    private void setNewGetHeader(HttpGet httpGet) {
        //        httpPost.addHeader("AD_HTTP_CACHE_MARK", "Not GET request");
        httpGet.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        httpGet.addHeader("Accept-Encoding", "gzip, deflate, br");
        httpGet.addHeader("Accept-Language", "zh-CN,zh;q=0.9");
        httpGet.addHeader("Cache-Control", "no-cache");
        httpGet.addHeader("Connection", "keep-alive");
//        httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        httpGet.addHeader("Cookie",  "__jsluid=30e09fbe2aa789cb6014c0332655757f; UM_distinctid=1699ddd876123a-036519e40ce505-37664109-e1000-1699ddd87625fe; SF_cookie_8=68437105; CNZZDATA3765988=cnzz_eid%3D68800744-1553127225-%26ntime%3D1553563682");
        httpGet.addHeader("Host", "www.rmfysszc.gov.cn");
//        httpGet.addHeader("Origin", "https://www1.rmfysszc.gov.cn");
        httpGet.addHeader("Pragma", "no-cache");
//        httpGet.addHeader("Referer", "https://www1.rmfysszc.gov.cn/projects.shtml?dh=3&gpstate=1&wsbm_slt=1");
        httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
//        httpGet.addHeader("X-Requested-With", "XMLHttpRequest");

    }

    // 保存详情数据
    public void getDetail(Response response) {
        Map<String, Object> params = response.getMeta();
        JXDocument doc = response.document();
        try{

         }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void setPriorityFlag(String getUrl, CloseableHttpClient httpClient, RMFYSSTransactionData rmfyssTransactionData) {
        HttpGet get = new HttpGet(getUrl);
        setHeadeParams(get);
        try{
            CloseableHttpResponse execute = httpClient.execute(get);
            String result = EntityUtils.toString(execute.getEntity());
            logger.info("获取的是否有优先购买权的结果为" + result);
            if(StringUtils.isNotBlank(result)) {
                String resultSt = result.substring(result.indexOf("(") + 1, result.indexOf(")"));
                JSONObject json = JSONObject.parseObject(resultSt);
                JXDocument doc = JXDocument.create(json.getString("Count"));
                JXNode jxNode = doc.selNOne("//center");
                rmfyssTransactionData.setPriorityFlag(jxNode == null? "有" : "无");
            }
        }catch (Exception e) {
            rmfyssTransactionData.setPriorityFlag("有");
            logger.error("设置是否有优先购买权的时候报错" + e.getMessage());
        }
    }

    private void setCurrentPrice(String getUrl, CloseableHttpClient httpClient, RMFYSSTransactionData rmfyssTransactionData) {
        try{
            HttpGet get = new HttpGet(getUrl);
            setHeadeParams(get);
            CloseableHttpResponse execute = httpClient.execute(get);
            String result = EntityUtils.toString(execute.getEntity());

            if(StringUtils.isNotBlank(result)) {
                String resultSt = result.substring(result.indexOf("(") + 1, result.indexOf(")"));
                JSONObject json = JSONObject.parseObject(resultSt);

                rmfyssTransactionData.setCurrentPrice(json.getString("price") + "万元");
                rmfyssTransactionData.setTransactionTime(json.getString("time"));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void getBidList(Response response) {
        try {
            JXDocument doc =  response.document();
            List<JXNode> jxNodes = doc.selN("//tr[@class='Record']");

            Map<String, Object> params = response.getMeta();
            if(jxNodes != null && jxNodes.size() > 0) {
                List<RMFYSSTransactionDataBidRecord> rmfyssTransactionDataBidRecords = new ArrayList<>();
                for(int i = 0, len = jxNodes.size(); i < len; i++) {
                    String orderNum = StringFormatUtil.changeObjectToStr(jxNodes.get(i).sel("/td[1]/text()").get(0));
                    String state = StringFormatUtil.changeObjectToStr(jxNodes.get(i).sel("/td[2]/b/text()").get(0));
                    String code = StringFormatUtil.changeObjectToStr(jxNodes.get(i).sel("/td[3]/text()").get(0));
                    String money = StringFormatUtil.changeObjectToStr(jxNodes.get(i).sel("/td[4]/text()").get(0));
                    String time = StringFormatUtil.changeObjectToStr(jxNodes.get(i).sel("/td[5]/text()").get(0));

                    RMFYSSTransactionDataBidRecord rmfyssTransactionDataBidRecord = new RMFYSSTransactionDataBidRecord();
                    rmfyssTransactionDataBidRecord.setAuctionCode((String) params.get("id"));
                    rmfyssTransactionDataBidRecord.setOrderNum(Integer.parseInt(orderNum));
                    rmfyssTransactionDataBidRecord.setCreateTime(new Date());
                    rmfyssTransactionDataBidRecord.setBidTime(DateUtil.parseSpecialDateTime(time));
                    rmfyssTransactionDataBidRecord.setPrice(money);
                    rmfyssTransactionDataBidRecord.setUserName(code);
                    rmfyssTransactionDataBidRecord.setState(state);

                    rmfyssTransactionDataBidRecords.add(rmfyssTransactionDataBidRecord);
                 }

                rmfyssTransactionDataBidRecordDao.save(rmfyssTransactionDataBidRecords);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setNoticeNumber(String getUrl, CloseableHttpClient httpClient, RMFYSSTransactionData rmfyssTransactionData) {
        try{
            HttpGet get = new HttpGet(getUrl);
            setHeadeParams(get);
            CloseableHttpResponse execute = httpClient.execute(get);
            String result = EntityUtils.toString(execute.getEntity());


            if(StringUtils.isNotBlank(result)) {
                String resultSt = result.substring(result.indexOf("(") + 1, result.indexOf(")"));
                JSONObject json = JSONObject.parseObject(resultSt);

                rmfyssTransactionData.setAccessNum(json.getString("Hits"));
                rmfyssTransactionData.setCollectionNum(
                        json.getString("Count"));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setBMNumber(String getUrl, CloseableHttpClient httpClient, RMFYSSTransactionData rmfyssTransactionData) {
        try{
            HttpGet get = new HttpGet(getUrl);
            setHeadeParams(get);
            CloseableHttpResponse execute = httpClient.execute(get);
            String result = EntityUtils.toString(execute.getEntity());

            if(StringUtils.isNotBlank(result)) {
                String resultSt = result.substring(result.indexOf("(") + 1, result.indexOf(")"));
                JSONObject json = JSONObject.parseObject(resultSt);
                rmfyssTransactionData.setAccessEnsureNum(json.getString("Count"));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setNewParams(List<NameValuePair> nameValuePairs) {
        nameValuePairs.add(new BasicNameValuePair("type", "0"));
        nameValuePairs.add(new BasicNameValuePair("name", ""));
        nameValuePairs.add(new BasicNameValuePair("time", "0"));
        nameValuePairs.add(new BasicNameValuePair("time1", ""));
        nameValuePairs.add(new BasicNameValuePair("time2", ""));
        nameValuePairs.add(new BasicNameValuePair("money", ""));
        nameValuePairs.add(new BasicNameValuePair("number", "0"));
        nameValuePairs.add(new BasicNameValuePair("fid1", ""));
        nameValuePairs.add(new BasicNameValuePair("fid2", ""));
        nameValuePairs.add(new BasicNameValuePair("fid3", ""));
        nameValuePairs.add(new BasicNameValuePair("order", "0"));
        nameValuePairs.add(new BasicNameValuePair("include", "0"));

    }


    private void setOtherParams(List<NameValuePair> nameValuePairs, AreaModel areaModel) {


        nameValuePairs.add(new BasicNameValuePair("name", ""));
        nameValuePairs.add(new BasicNameValuePair("area", areaModel.getArea()));
        nameValuePairs.add(new BasicNameValuePair("city", areaModel.getCity()));   //类型
        nameValuePairs.add(new BasicNameValuePair("city1", areaModel.getCity1()));
        nameValuePairs.add(new BasicNameValuePair("city2", areaModel.getCity2()));
        nameValuePairs.add(new BasicNameValuePair("time", "0"));
        nameValuePairs.add(new BasicNameValuePair("time1", ""));
        nameValuePairs.add(new BasicNameValuePair("time2", ""));
        nameValuePairs.add(new BasicNameValuePair("money", ""));
        nameValuePairs.add(new BasicNameValuePair("money1", ""));
        nameValuePairs.add(new BasicNameValuePair("number", "0"));
        nameValuePairs.add(new BasicNameValuePair("fid1", ""));
        nameValuePairs.add(new BasicNameValuePair("fid2", ""));
        nameValuePairs.add(new BasicNameValuePair("fid3", ""));
        nameValuePairs.add(new BasicNameValuePair("order", "0"));
        nameValuePairs.add(new BasicNameValuePair("include", "0"));

    }

    public static HttpPost setHeadeParams(HttpPost httpPost) {
//        httpPost.addHeader("AD_HTTP_CACHE_MARK", "Not GET request");
        httpPost.addHeader("Accept", "application/json, text/javascript, */*; q=0.01");
        httpPost.addHeader("Accept-Encoding", "gzip, deflate, br");
        httpPost.addHeader("Accept-Language", "zh-CN,zh;q=0.9");
        httpPost.addHeader("Cache-Control", "no-cache");
        httpPost.addHeader("Connection", "keep-alive");
//        httpPost.addHeader("Content-Length", "160");
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        httpPost.addHeader("Cookie",  "UM_distinctid=1699ddd876123a-036519e40ce505-37664109-e1000-1699ddd87625fe; __jsluid=656617dd9eb33501a5103544a1fbfbe3; CNZZDATA3765988=cnzz_eid%3D1003298408-1553132628-https%253A%252F%252Fwww1.rmfysszc.gov.cn%252F%26ntime%3D1553132628; ASP.NET_SessionId=uzsuunejsow2bvzwuxcqfmcd; SF_cookie_9=76891161");
        httpPost.addHeader("Host", "www1.rmfysszc.gov.cn");
        httpPost.addHeader("Origin", "https://www1.rmfysszc.gov.cn");
        httpPost.addHeader("Pragma", "no-cache");
        httpPost.addHeader("Referer", "https://www1.rmfysszc.gov.cn/projects.shtml?dh=3&gpstate=1&wsbm_slt=1");
        httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
        httpPost.addHeader("X-Requested-With", "XMLHttpRequest");

        return httpPost;
    }


    public static void setHeadeParams(HttpGet httpGet) {
//        httpPost.addHeader("AD_HTTP_CACHE_MARK", "Not GET request");
        httpGet.addHeader("Accept", "application/json, text/javascript, */*; q=0.01");
        httpGet.addHeader("Accept-Encoding", "gzip, deflate, br");
        httpGet.addHeader("Accept-Language", "zh-CN,zh;q=0.9");
        httpGet.addHeader("Cache-Control", "no-cache");
        httpGet.addHeader("Connection", "keep-alive");
//        httpPost.addHeader("Content-Length", "160");
        httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        httpGet.addHeader("Cookie",  "UM_distinctid=1699ddd876123a-036519e40ce505-37664109-e1000-1699ddd87625fe; __jsluid=656617dd9eb33501a5103544a1fbfbe3; CNZZDATA3765988=cnzz_eid%3D1003298408-1553132628-https%253A%252F%252Fwww1.rmfysszc.gov.cn%252F%26ntime%3D1553132628; ASP.NET_SessionId=uzsuunejsow2bvzwuxcqfmcd; SF_cookie_9=76891161");
        httpGet.addHeader("Host", "www1.rmfysszc.gov.cn");
        httpGet.addHeader("Origin", "https://www1.rmfysszc.gov.cn");
        httpGet.addHeader("Pragma", "no-cache");
        httpGet.addHeader("Referer", "https://www1.rmfysszc.gov.cn/projects.shtml?dh=3&gpstate=1&wsbm_slt=1");
        httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
        httpGet.addHeader("X-Requested-With", "XMLHttpRequest");
    }


}
