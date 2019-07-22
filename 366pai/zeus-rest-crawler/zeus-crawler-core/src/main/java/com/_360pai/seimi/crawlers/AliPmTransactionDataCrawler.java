package com._360pai.seimi.crawlers;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import com._360pai.seimi.dao.AliPmTransactionDataBidRecordDao;
import com._360pai.seimi.dao.AliPmTransactionDataDao;
import com._360pai.seimi.model.AliPmTransactionData;
import com._360pai.seimi.model.AliPmTransactionDataBidRecord;
import com._360pai.seimi.util.StringFormatUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * create by liuhaolei on 2018-03-18
 */
@Component
@Crawler(name = "aliTransactionData", useUnrepeated = false)
public class AliPmTransactionDataCrawler extends BaseSeimiCrawler {

    private static final Pattern COMPILE = Pattern.compile("\\{\"data\":\\[(.*?)]}");
    @Autowired
    private AliPmTransactionDataDao aliPmTransactionDataDao;
    @Autowired
    private AliPmTransactionDataBidRecordDao aliPmTransactionDataBidRecordDao;

    @Override
    public String[] startUrls() {
        return new String[0];
    }

    @Override
    public void start(Response response) {
        JXDocument document = response.document();
        List<JXNode> jxNodes = document.selN("//div[@class='sf-filter J_Filter']/ul[1]/li/div[2]/ul/li");

        for(int i = 0; i < jxNodes.size(); i++) {
            String categoryUrl = jxNodes.get(i).sel("/em/a/@href").get(0).toString();
            String categoryName = jxNodes.get(i).sel("/em/a/text()").get(0).toString();

            Map<String, Object> params = new HashMap<>();
            params.put("categoryName", categoryName);

            push(Request.build("http:" + categoryUrl,AliPmTransactionDataCrawler::getCategoryData).setMeta(params));
        }
    }

    // 分页数据查询
    public void getCategoryData(Response response){
        JXDocument document = response.document();
        try {
            JXNode jxNode = document.selNOne("//span[@class='page-skip']/em/text()");
            Integer totalPage = jxNode == null ? 0 : Integer.parseInt(jxNode.toString());
            for(int i = 1; i < totalPage + 1; i++) {
                push(Request.build(response.getUrl() + "&page=" + i, AliPmTransactionDataCrawler::getPageData).
                        setMeta(response.getMeta()));
            }
         } catch (Exception e) {
            e.printStackTrace();
            logger.error("处理阿里拍卖数据异常{}", e.getMessage());
        }
    }

    // 获取分页数据
    public void getPageData(Response response){
        try {
            String doc = response.getContent().replace("\n", "");
            Matcher regexData = COMPILE.matcher(doc);
            String dataString =  !regexData.find()? "": regexData.group(0);

            JSONObject json = JSON.parseObject(dataString);
            JSONArray jsonArrays = JSON.parseArray(json.getString("data"));

            
            for(int i = 0; i < jsonArrays.size(); i++ ) {
                JSONObject jsonObject = jsonArrays.getJSONObject(i);
                String id = jsonObject.getString("id");
                System.out.println(id);
                String bidCount = jsonObject.getString("bidCount");
                //每次都创建一个新的对象
                Map<String, Object> meta = new HashMap<>();
                meta = setMateParams(meta, jsonObject);
                meta.put("bidCount", bidCount);
                meta.put("id", id);
                meta.put("categoryName", response.getMeta().get("categoryName"));

                AliPmTransactionData aliPmTransactionData = aliPmTransactionDataDao.getOneByCode(id);
                if(aliPmTransactionData == null) {
                    push(Request.build("http://sf-item.taobao.com/sf_item/" + id + ".htm", AliPmTransactionDataCrawler::getDetailData).setMeta(meta));
                    setBidList(bidCount, id);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("处理阿里拍卖分页数据异常{}", e.getMessage());
        }
    }

    private Map<String, Object> setMateParams(Map<String, Object> meta, JSONObject jsonObject) {
        String currentPrice = jsonObject.getString("currentPrice");
        String viewerCount = jsonObject.getString("viewerCount");
        String applyCount = jsonObject.getString("applyCount");
        String start = jsonObject.getString("start");
        String end = jsonObject.getString("end");

        meta.put("currentPrice", currentPrice);
        meta.put("viewerCount", viewerCount);
        meta.put("applyCount", applyCount);
        meta.put("startTime", start);
        meta.put("endTime", end);

        return meta;
    }

    private void setBidList(String bidCount, String id) {
       Integer totalCount = bidCount == null ? 0 : Integer.parseInt(bidCount.toString());
       String bidListUrl = "http://sf-item.taobao.com/json/get_bid_records.htm?currentPage=PAGE&_ksTS=TIMESTEMP&id=INFOID&records_type=pageRecords";
        // 获取出价记录
       if(totalCount != 0) {
           Map<String, Object> params = new HashMap<>();
           params.put("id", id);
           for(int i = 1; i < totalCount / 20 + 2; i++) {
                String replace = bidListUrl.replace("TIMESTEMP", String.valueOf(System.currentTimeMillis())).
                        replace("INFOID", id).replace("PAGE", String.valueOf(i));

                push(Request.build(replace, AliPmTransactionDataCrawler::getPageBidList).setMeta(params));
            }
       }
    }


    // 分页数据查询
    public void getPageBidList(Response response){
        try {
            JSONObject json = JSONObject.parseObject(response.getContent());
            JSONArray jsonArray = json.getJSONArray("records");
            Map<String, Object> params = response.getMeta();

            List<AliPmTransactionDataBidRecord> result = new ArrayList<>();
            if(jsonArray != null && jsonArray.size() > 0) {
                for(int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    AliPmTransactionDataBidRecord model = new AliPmTransactionDataBidRecord();
                    model.setAuctionCode((String) params.get("id"));
                    model.setCreateTime(new Date());
                    model.setPrice(jsonObject.getString("price"));
                    model.setUserName( jsonObject.getString("alias"));
                    model.setSelfFlag(jsonObject.getString("isSelf"));
                    model.setPriorFlag(jsonObject.getString("priorityUser"));
                    String status = jsonObject.getString("status");
                    model.setStatus("99".equals(status)? "成交" : "出局");

                    Long bidTime = jsonObject.getString("bidTime") == null? null
                            : Long.parseLong(jsonObject.getString("bidTime"));
                    model.setBidTime(bidTime == null ? null : new Date(bidTime));

                    result.add(model);
                }
            }

            aliPmTransactionDataBidRecordDao.save(result);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("处理阿里拍卖出价记录异常{}", e.getMessage());
        }
    }

    // 获取详情数据
    public void getDetailData(Response response){
        try {
            Map<String, Object> meta = response.getMeta();
            JXDocument document = response.document();

            String title = StringFormatUtil.changeObjectToStr(document.selNOne("//div[@class='pm-main clearfix']/h1/text()"));
            String startPrice = StringFormatUtil.changeObjectToStr(document.selNOne("//tbody[@id='J_HoverShow']/tr[1]/td[1]/span/span/text()"));
            String increasePrice = StringFormatUtil.changeObjectToStr(document.selNOne("//tbody[@id='J_HoverShow']/tr[1]/td[2]/span/span/text()"));
//            String auctionType = StringFormatUtil.changeObjectToStr(document.selNOne("//tbody[@id='J_HoverShow']/tr[1]/td[3]/span/span/text()"));
            String securityDeposit = StringFormatUtil.changeObjectToStr(document.selNOne("//tbody[@id='J_HoverShow']/tr[2]/td[1]/span/span/text()"));
            String biddingCycle = StringFormatUtil.changeObjectToStr(document.selNOne("//tbody[@id='J_HoverShow']/tr[2]/td[2]/span[2]/text()"));
            String preferredPurchaser = StringFormatUtil.changeObjectToStr(document.selNOne("//tbody[@id='J_HoverShow']/tr[2]/td[3]/span[2]/text()"));
            String evaluationPrice = StringFormatUtil.changeObjectToStr(document.selNOne("//tbody[@id='J_HoverShow']/tr[3]/td[1]/span/span/text()"));
            String delayTime = StringFormatUtil.changeObjectToStr(document.selNOne("//tbody[@id='J_HoverShow']/tr[3]/td[2]/span[2]/text()"));
            String biddingRule = StringFormatUtil.changeObjectToStr(document.selNOne("//span[@class='pai-content']/text()"));
            String noticeAccount = StringFormatUtil.changeObjectToStr(document.selNOne("//em[@id='J_NotifyNum']/text()"));
            String shopName = StringFormatUtil.changeObjectToStr(document.selNOne("//p[@class='subscribe-unit']/span[2]/a/text()"));
            String shopPhone = StringFormatUtil.changeObjectToStr(document.selNOne("//div[@class='contact-card']/p[1]/span[2]/text()"));

            AliPmTransactionData aliPmTransactionData = new AliPmTransactionData();
            aliPmTransactionData.setTitle(title);
            aliPmTransactionData.setCode((String) meta.get("id"));
            aliPmTransactionData.setCurrentPrice((String) meta.get("currentPrice"));
            aliPmTransactionData.setStartPrice(startPrice);
            aliPmTransactionData.setEnsurePrice(securityDeposit);
            aliPmTransactionData.setAssessmentPrice(evaluationPrice);
            aliPmTransactionData.setDelayedCount(delayTime.replace(": ", ""));
            aliPmTransactionData.setCategoryName((String)meta.get("categoryName"));
            aliPmTransactionData.setPriceLowerOffset(increasePrice);
            aliPmTransactionData.setPriorityFlag(preferredPurchaser.replace(": ", ""));
            aliPmTransactionData.setAccessEnsureNum((String) meta.get("applyCount"));
            aliPmTransactionData.setAccessNum((String) meta.get("viewerCount"));
            String startTimeSt = (String) meta.get("startTime");
            Long startTime = startTimeSt == null? null : Long.parseLong(startTimeSt);
            aliPmTransactionData.setBeginTime( startTime == null ? null : new Date(startTime));

            String endTimeSt = (String) meta.get("endTime");
            Long endTime = startTimeSt == null? null : Long.parseLong(endTimeSt);
            aliPmTransactionData.setEndTime(endTime == null ? null : new Date(endTime));

            aliPmTransactionData.setNoticeNum(noticeAccount);
            aliPmTransactionData.setBiddingCycle(biddingCycle.replace(": ", ""));
            aliPmTransactionData.setBiddingRule(biddingRule);
            aliPmTransactionData.setCreateTime(new Date());
            aliPmTransactionData.setShopName(shopName);
            aliPmTransactionData.setShopPhone(shopPhone);

            String address = StringFormatUtil.changeObjectToStr(document.selNOne("//div[@id='itemAddress']/text()"));
            String[] addressArray = address.split(" ");
            if(addressArray != null && addressArray.length > 2) {
                aliPmTransactionData.setProvince(addressArray[0]);
                aliPmTransactionData.setCity(addressArray[1]);
                aliPmTransactionData.setCounty(addressArray[2] == null? "": addressArray[2]);
            }

            aliPmTransactionDataDao.save(aliPmTransactionData);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("处理阿里拍卖分页数据异常{}", e.getMessage());
        }
    }
}
