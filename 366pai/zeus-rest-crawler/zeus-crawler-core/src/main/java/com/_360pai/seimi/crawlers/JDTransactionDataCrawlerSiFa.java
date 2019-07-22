package com._360pai.seimi.crawlers;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import com._360pai.seimi.dao.JdPmTransactionDataBankLegalDao;
import com._360pai.seimi.dao.JdPmTransactionDataBidRecordLegalDao;
import com._360pai.seimi.dao.JdPmTransactionDataLegalDao;
import com._360pai.seimi.model.JdPmTransactionDataLegal;
import com._360pai.seimi.model.JdPmTransactionDataBankLegal;
import com._360pai.seimi.model.JdPmTransactionDataBidRecordLegal;
import com._360pai.seimi.util.StringFormatUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.*;

@Component
@Crawler(name = "jdTransactionDataSiFa", useUnrepeated = false)
public class JDTransactionDataCrawlerSiFa extends BaseSeimiCrawler {

    private static final String DETAILURL = "https://paimai.jd.com/";

    @Autowired
    private JdPmTransactionDataLegalDao jdPmTransactionDataDao;
    @Autowired
    private JdPmTransactionDataBidRecordLegalDao jdPmTransactionDataBidRecordDao;
    @Autowired
    private JdPmTransactionDataBankLegalDao jdPmTransactionDataBankDao;
    @Override
    public String[] startUrls() {
        return new String[0];
    }

    @Override
    public void start(Response response) {

        System.out.println(response.getContent());
        JXDocument doc = response.document();
        List<JXNode> jxNodes = doc.selN("//div[@class='s-line s-location']/div/div[2]/div/dl/dd/a/@data-id");
        List<JXNode> newJxNodes = doc.selN("//div[@class='s-line s-category']/div[1]/div[2]/div/ul/li");

        for(int j = 1, len = jxNodes.size(); j < len; j++) {
            for (int i = 1; i < newJxNodes.size(); i++) {

                String node = newJxNodes.get(i).sel("/@data-childrencateid").get(0).toString();
                String categoryName = newJxNodes.get(i).sel("/a/text()").get(0).toString();

                HashMap<String, Object> params = new HashMap<>();
                params.put("categoryName", categoryName);
                String  PAGEURL = "https://auction.jd.com/getJudicatureList.html?limit=40&paimaiStatus=2&childrenCateId=";
                push(Request.build(PAGEURL + node + "&provinceId=" + jxNodes.get(j),JDTransactionDataCrawlerSiFa::getCategoryData).setMeta(params));
            }
        }
    }


    public void getCategoryData(Response response){

        JSONObject json = JSONObject.parseObject(response.getContent());
        Integer total = Integer.parseInt(json.get("total").toString()) ;
        Map<String, Object> meta = response.getMeta();

        System.out.println("该类别所有数据是" + total + "请求路径是" + response.getUrl());
        try {
            for(int i = 1; i < total/40 + 2 ; i++) {
                getPageData(response.getUrl() + "&page=" + i, (String)meta.get("categoryName"));
                logger.info("开始爬取第" + i + "页的数据");
//                push(Request.build(response.getUrl() + "&page=" + i,JDTransactionDataCrawler::getPageData).setMeta(meta));

            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("处理详情页数据异常{}", e.getMessage());
        }
    }
    public void getPageData(String newUrl, String categoryName){

//    public void getPageData(Response response){
        try {
            CookieStore cookieStore = new BasicCookieStore();
            CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
            System.out.println("调用的分页路径是" + newUrl);

            HttpGet get = new HttpGet(newUrl);
            CloseableHttpResponse execute = httpClient.execute(get);
            String html = EntityUtils.toString(execute.getEntity());

            List<JdPmTransactionDataLegal> jdPmTransactionDataList = new ArrayList<>();
            JSONObject json = JSONObject.parseObject(html);

//            JSONObject json = JSONObject.parseObject(response.getContent());

//            System.out.println("当前页的连接为" + newUrl);
            JSONArray jsonArray = (JSONArray) JSONArray.parse(json.get("ls").toString());
            for(int i = 0; i < jsonArray.size(); i++){
                JSONObject job = jsonArray.getJSONObject(i);

                String id = StringFormatUtil.changeObjectToStr(job.get("id"));

                Map<String, Object> params =  new HashMap<>();

                params.put("id", id);
                //判断是否已经存在对象
                JdPmTransactionDataLegal oneByCode = jdPmTransactionDataDao.getOneByCode(id);

                if(oneByCode == null) {
                    String AUCTIONDETAIL = "https://api.m.jd.com/api?appid=paimai&functionId=getProductBasicInfo&body=%7b%22paimaiId%22%3a" + "INFOID" +"%7d&loginType=3";
                    oneByCode = getDetailData(AUCTIONDETAIL.replace("INFOID", id), id, categoryName);
                    //添加到list
                    jdPmTransactionDataList.add(oneByCode);
                }else {
                    System.out.println("已经存在数据是" + id);
                }
            }
            jdPmTransactionDataDao.save(jdPmTransactionDataList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("处理详情页数据异常{}", e.getMessage());
        }
    }


    public JdPmTransactionDataLegal getDetailData(String newUrl, String id, String categoryName){
        try {

            CookieStore cookieStore = new BasicCookieStore();
            CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();

            HttpGet get = new HttpGet(newUrl);
            CloseableHttpResponse execute = httpClient.execute(get);
            String html = EntityUtils.toString(execute.getEntity());

            JSONObject job = JSONObject.parseObject(html);
            JSONObject json = JSONObject.parseObject(job.getString("data"));
            JSONObject judicatureJson = JSONObject.parseObject(json.getString("judicatureBasicInfoResult"));
            JSONObject productAddressJson = JSONObject.parseObject(json.getString("productAddressResult"));

            //保存详情数据
            JdPmTransactionDataLegal jdPmTransactionData = new JdPmTransactionDataLegal();
            jdPmTransactionData.setCode(id);
            jdPmTransactionData.setCategoryName(categoryName);
            jdPmTransactionData.setStartPrice(StringFormatUtil.changeObjectToStr(json.get("startPrice")));
            jdPmTransactionData.setTitle(StringFormatUtil.changeObjectToStr(json.get("title")));
            jdPmTransactionData.setAssessmentPrice(StringFormatUtil.changeObjectToStr(json.get("assessmentPrice")));
            jdPmTransactionData.setPriceLowerOffset(StringFormatUtil.changeObjectToStr(json.get("priceLowerOffset")));
            jdPmTransactionData.setEnsurePrice(StringFormatUtil.changeObjectToStr(json.get("ensurePrice")));
            jdPmTransactionData.setConsultName(StringFormatUtil.changeObjectToStr(judicatureJson.get("consultName")));
            jdPmTransactionData.setConsulTel(StringFormatUtil.changeObjectToStr(judicatureJson.get("consultTel")));
            jdPmTransactionData.setProvince(StringFormatUtil.changeObjectToStr(productAddressJson.get("province")));
            jdPmTransactionData.setCity(StringFormatUtil.changeObjectToStr(productAddressJson.get("city")));
            jdPmTransactionData.setCounty(StringFormatUtil.changeObjectToStr(productAddressJson.get("county")));
            jdPmTransactionData.setAddress(StringFormatUtil.changeObjectToStr(productAddressJson.get("address")));

            Date beginTime = changeTimeToDate(StringFormatUtil.changeObjectToStr(json.get("startTime")));
            jdPmTransactionData.setBeginTime(beginTime);

            jdPmTransactionData.setCreateTime(new Date());
            jdPmTransactionData.setAuctionTimes(StringFormatUtil.changeObjectToStr(json.get("paimaiTimes")));

            String courtVendorId = StringFormatUtil.changeObjectToStr(json.get("courtVendorId"));

            JdPmTransactionDataLegal oneByCode = jdPmTransactionDataDao.getOneByCode(id);
            if(oneByCode != null) {
                logger.error("出现了重复的数据" + id + StringFormatUtil.changeObjectToStr(json.get("title")));
                throw new NullPointerException("出现了重复数据");
            }

            //设置另外一个详情
            String LAWYERAUCTIONDETAIL = "https://api.m.jd.com/api?appid=paimai&functionId=getPaimaiRealTimeData&body=%7b%22end%22:9,%22paimaiId%22:"+"INFOID" + ",%22source%22:0,%22start%22:0%7d&loginType=3";
            getAnotherDetailData(LAWYERAUCTIONDETAIL.replace("INFOID", id), httpClient, jdPmTransactionData);

            //设置其他详情
            String LAWYERFORM = "https://api.m.jd.com/api?appid=paimai&functionId=queryVendorInfo&body=%7b%22publishSource%22:7,%22vendorId%22:"+"INFOID"+"%7d&loginType=3";
            setOntherInfo(httpClient, LAWYERFORM.replace("INFOID", courtVendorId), jdPmTransactionData);

            //获取是否有优先竞买人
            String PREFERREDBIDDER = "https://api.m.jd.com/api?appid=paimai&functionId=queryAllPriorPurchaserLevel&body=%7b%22paimaiId%22%3a" + "INFOID" + "%7d&loginType=3";
            setPreferredBidder(httpClient, PREFERREDBIDDER.replace("INFOID", id), jdPmTransactionData);

            //保存真正的详情数据
            return jdPmTransactionData;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("处理详情页数据异常{}", e.getMessage());
        }
        return null;
    }

    //设置是否有优先竞买人
    private void setPreferredBidder(CloseableHttpClient httpClient, String newUrl, JdPmTransactionDataLegal jdPmTransactionData) {

        try{
            HttpGet get = new HttpGet(newUrl);
            CloseableHttpResponse execute = httpClient.execute(get);
            String html = EntityUtils.toString(execute.getEntity());
            JSONObject json = JSONObject.parseObject(html);

            JSONArray jsonArray = (JSONArray) JSONArray.parse(json.get("data").toString());
            String priorityFlag =  jsonArray != null && jsonArray.size() > 0 ? "有": "无" ;
            jdPmTransactionData.setPriorityFlag(priorityFlag);
        }catch (Exception e){
            logger.error("设置是否有优先竞买人异常", e.getMessage());
        }
    }


    private void setOntherInfo(CloseableHttpClient httpClient, String newUrl, JdPmTransactionDataLegal jdPmTransactionData) {

        try{
            HttpGet get = new HttpGet(newUrl);
            CloseableHttpResponse execute = httpClient.execute(get);
            String html = EntityUtils.toString(execute.getEntity());

            JSONObject json = JSONObject.parseObject(html);
            JSONObject newJson = JSONObject.parseObject(json.getString("data"));
            String shopName = newJson.getString("shopName");
            jdPmTransactionData.setShopName(shopName);
        }catch (Exception e){
            logger.error("详情数据3异常", e.getMessage());
        }
    }


    private void getAnotherDetailData(String detailUrl, CloseableHttpClient httpClient, JdPmTransactionDataLegal jdPmTransactionData) {
        try {
            HttpGet get = new HttpGet(detailUrl);

            CloseableHttpResponse execute = httpClient.execute(get);
            String html = EntityUtils.toString(execute.getEntity());

            JSONObject json = JSONObject.parseObject(html);
            JSONObject newJson = JSONObject.parseObject(json.getString("data"));

            jdPmTransactionData.setCurrentPrice(StringFormatUtil.changeObjectToStr(newJson.get("currentPrice")));
            jdPmTransactionData.setDelayedCount( StringFormatUtil.changeObjectToStr(newJson.get("delayedCount")));
            jdPmTransactionData.setAccessEnsureNum(StringFormatUtil.changeObjectToStr(newJson.get("accessEnsureNum")));
            jdPmTransactionData.setAccessNum(StringFormatUtil.changeObjectToStr(newJson.get("accessNum")));
            Date endTime = changeTimeToDate(StringFormatUtil.changeObjectToStr(newJson.get("endTime")));
            jdPmTransactionData.setEndTime(endTime);
            //成交书路径
            String confirmationUrl = StringFormatUtil.changeObjectToStr(newJson.get("confirmationUrl"));
            jdPmTransactionData.setConfirmationUrl(confirmationUrl);

            //获取
            String bidCount = StringFormatUtil.changeObjectToStr(newJson.get("bidCount"));
            int intCount = bidCount == null ? 0 : Integer.parseInt(bidCount);

            getBidList(httpClient, jdPmTransactionData.getCode(), intCount);

            //保存可以贷款银行列表
            String BANKRUL = "https://api.m.jd.com/api?appid=paimai&functionId=wareLoanBanks&body=%7b%22paimaiId%22:"+ "INFOID" +"%7d";
            getBankList(httpClient, BANKRUL.replace("INFOID", jdPmTransactionData.getCode()), jdPmTransactionData.getCode());

            //下载成交记录
            try{
                if(StringUtils.isNotBlank(confirmationUrl)) {
                    downloadContract(confirmationUrl, jdPmTransactionData.getTitle(), jdPmTransactionData.getCategoryName(), jdPmTransactionData.getCode());
                }
            }catch (Exception e){
                logger.error("下载成交详情数据异常", e.getMessage());
            }
        }catch (Exception e){
            logger.error("获取详情2异常", e.getMessage());
        }
    }


    private void getBankList(CloseableHttpClient httpClient, String pageUrl, String code) {
        try{
            HttpGet get = new HttpGet(pageUrl);
            CloseableHttpResponse execute = httpClient.execute(get);
            String html = EntityUtils.toString(execute.getEntity());
            JSONObject json = JSONObject.parseObject(html);
            JSONArray jsonArray = JSONObject.parseArray(json.getString("data"));

            List<JdPmTransactionDataBankLegal> jdPmTransactionDataBanks = new ArrayList<>();
            if(jsonArray != null && jsonArray.size() > 0) {
                for(int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                    JdPmTransactionDataBankLegal jdPmTransactionDataBank = new JdPmTransactionDataBankLegal();

                    jdPmTransactionDataBank.setApplyCount(StringFormatUtil.changeObjectToStr(jsonObject.get("applyCount")));
                    jdPmTransactionDataBank.setAuctionCode(code);
                    jdPmTransactionDataBank.setBankName(StringFormatUtil.changeObjectToStr(jsonObject.get("bankName")));
                    jdPmTransactionDataBank.setBankLogo(StringFormatUtil.changeObjectToStr(jsonObject.get("bankLogo")));
                    jdPmTransactionDataBank.setLoanRate(StringFormatUtil.changeObjectToStr(jsonObject.get("loanRate")));
                    jdPmTransactionDataBank.setMaxLoanRatio(StringFormatUtil.changeObjectToStr(jsonObject.get("maxLoanRatio")));
                    jdPmTransactionDataBank.setOtherExpenses(StringFormatUtil.changeObjectToStr(jsonObject.get("otherExpenses")));
                    jdPmTransactionDataBank.setCreateTime(new Date());

                    jdPmTransactionDataBanks.add(jdPmTransactionDataBank);
                }
            }
            jdPmTransactionDataBankDao.save(jdPmTransactionDataBanks);
        }catch (Exception e){
            logger.error("获取银行信息异常", e.getMessage());
        }
    }

    private void getBidList(CloseableHttpClient httpClient, String code, Integer intCount) {

        try{
            for (int i = 0; i < intCount / 10 + 1; i++){
                String LAWYERAUCTIONPAGEDETAIL = "https://api.m.jd.com/api?appid=paimai&functionId=getPaimaiRealTimeData&body=%7b%22end%22:"+"END"+",%22paimaiId%22:"+"INFOID" + ",%22source%22:0,%22start%22:"+"START"+"%7d&loginType=3";
                String newUrl = LAWYERAUCTIONPAGEDETAIL.replace("INFOID",
                        code).replace("END",  String.valueOf(i * 10 + 9)).replace("START", String.valueOf(i == 0 ? 0 : i * 10));
                HttpGet get = new HttpGet(newUrl);

                CloseableHttpResponse execute = httpClient.execute(get);
                String html = EntityUtils.toString(execute.getEntity());
                JSONObject json = JSONObject.parseObject(html);
                JSONObject newJson = JSONObject.parseObject(json.getString("data"));

                saveBidList(code, StringFormatUtil.changeObjectToStr(newJson.get("bidList")));
            }
        }catch (Exception e){
            logger.error("获取出价记录异常", e.getMessage());
        }

    }


    private void saveBidList(String code, String bidList) {
        JSONArray jsonArray = JSONArray.parseArray(bidList);

        List<JdPmTransactionDataBidRecordLegal> jdPmTransactionDataBidRecords = new ArrayList<>();
        if(jsonArray != null && jsonArray.size() > 0) {
            for(int i = 0; i < jsonArray.size(); i++) {
                JSONObject json = (JSONObject) jsonArray.get(i);

                JdPmTransactionDataBidRecordLegal jdPmTransactionDataBidRecord = new JdPmTransactionDataBidRecordLegal();
                jdPmTransactionDataBidRecord.setAuctionCode(code);

                Date bidTime = changeTimeToDate(StringFormatUtil.changeObjectToStr(json.get("bidTime")));
                jdPmTransactionDataBidRecord.setBidTime(bidTime);

                jdPmTransactionDataBidRecord.setPriorFlag(StringFormatUtil.changeObjectToStr(json.get("isPrior")));
                jdPmTransactionDataBidRecord.setPrice(StringFormatUtil.changeObjectToStr(json.get("price")));
                jdPmTransactionDataBidRecord.setUserName(StringFormatUtil.changeObjectToStr(json.get("username")));
                jdPmTransactionDataBidRecord.setCreateTime(new Date());

                jdPmTransactionDataBidRecords.add(jdPmTransactionDataBidRecord);
            }
        }

        jdPmTransactionDataBidRecordDao.save(jdPmTransactionDataBidRecords);
    }



    private Date changeTimeToDate(String beginTime) {
        long timeTimestamp = Long.parseLong(beginTime);
        return new Date(timeTimestamp);
    }


    /**
     * 下载合同
     */
    public void downloadContract(String fileUrl, String fileName, String bigCategory, String code) throws Exception{


        CookieStore cookieStore = new BasicCookieStore();
//        HttpHost proxy = new HttpHost("121.31.149.3", 8123);
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();

        InputStream in = null;
        FileOutputStream out = null;
        File file = null;
//        //特殊字符转换
//        URL url1 = new URL(fileUrl);
//        URI uri = new URI(url1.getProtocol(), url1.getHost(), url1.getPath(), url1.getQuery(), null);

        try {
            System.out.println("下载的路径是" + "https:" + fileUrl);
            HttpGet getCityPage = new HttpGet("https:" + fileUrl);
            CloseableHttpResponse fileExecute = httpClient.execute(getCityPage);

            in = fileExecute.getEntity().getContent();
            file = new File("D:/jd/司法拍卖/" + bigCategory + "/" );
            if (!file.exists()) {
                file.mkdirs();
            }

            boolean matches = fileName.matches("[^\\s\\\\/:\\*\\?\\\"<>\\|](\\x20|[^\\s\\\\/:\\*\\?\\\"<>\\|])*[^\\s\\\\/:\\*\\?\\\"<>\\|\\.]$");
            if(matches) {
                file  = new File("D:/jd/司法拍卖/"+ bigCategory + "/" + fileName + "_" + code + ".pdf");
            }else {
                file  = new File("D:/jd/司法拍卖/"+ bigCategory + "/" + code + ".pdf");
            }
            if(!file.exists()) {
                file.createNewFile();
                out = new FileOutputStream(file);
                byte[] b = new byte[1024];
                int len = 0;
                while ((len = in.read(b)) != -1) {
                    out.write(b, 0, len);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(in != null) {
                in.close();
            }
            if(out != null) {
                out.close();
            }
        }
    }



}
