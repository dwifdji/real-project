package com._360pai.seimi.crawlers;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import com._360pai.seimi.dao.LianJiaAreaModelDao;
import com._360pai.seimi.dao.LianJiaTransactionDataDao;
import com._360pai.seimi.model.LianJiaAreaModel;
import com._360pai.seimi.model.LianJiaTranscationData;
import com._360pai.seimi.util.StringFormatUtil;
import org.apache.commons.lang.StringUtils;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Crawler(name = "lianjiaTransactionData", useUnrepeated = false)
@Component
public class LianJiaTransactionDataCrawler extends BaseSeimiCrawler {

    @Autowired
    private LianJiaAreaModelDao lianJiaAreaModelDao;
    @Autowired
    private LianJiaTransactionDataDao lianJiaTransactionDataDao;

    @Override
    public String[] startUrls() {
        return new String[0];
    }

    @Override
    public void start(Response response) {
        List<LianJiaAreaModel> all = lianJiaAreaModelDao.findAll();

        try {
            for(int i = 1, len = all.size(); i < len; i++) {
                LianJiaAreaModel lianJiaAreaModel = all.get(i);
                Map<String, Object> params = new HashMap<>();
                params.put("areaId", lianJiaAreaModel.getId());

                push(Request.build(lianJiaAreaModel.getAreaUrl(),
                        LianJiaTransactionDataCrawler::getAreaList).setMeta(params));

                Thread.sleep(1000);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void getAreaList(Response response) {
        logger.info("根据地区爬取的路径是" + response.getUrl());
        JXDocument doc = response.document();

        JXNode jxNode = doc.selNOne("//div[@class='page-box house-lst-page-box']/@page-data");
        if(jxNode == null) {
            return;
        }
        String totalPageSt = jxNode.toString();
        String totalPageSub = totalPageSt.substring(totalPageSt.indexOf(":") + 1, totalPageSt.indexOf(","));

        Integer totalPage = Integer.parseInt(totalPageSub);

        try {
            for(int i = 1; i < totalPage + 1; i++) {
                push(Request.build(response.getUrl() + "pg" + i,
                        LianJiaTransactionDataCrawler::getPageList).setMeta(response.getMeta()));

                Thread.sleep(1000);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void getPageList(Response response) {
        JXDocument doc = response.document();

        List<JXNode> jxNodes = doc.selN("//ul[@class='listContent']/li/a/@href");
        try {
            if(jxNodes != null && jxNodes.size() > 0) {
                for(int i = 0, len = jxNodes.size(); i < len; i++ ) {
                    String detailUrl = jxNodes.get(i).toString();
                    String code = detailUrl.substring(detailUrl.indexOf("chengjiao/") + 10, detailUrl.indexOf(".html"));

                    LianJiaTranscationData lianJiaTranscationData = lianJiaTransactionDataDao.selectOneByCode(code);
                    if(lianJiaTranscationData == null){
                        push(Request.build(jxNodes.get(i).toString(),
                                LianJiaTransactionDataCrawler::getDetailData).setMeta(response.getMeta()));
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void getDetailData(Response response) {
        JXDocument doc = response.document();
        String title =  StringFormatUtil.changeObjectToStr(
                doc.selNOne("//div[@class='house-title LOGVIEWDATA LOGVIEW']/div/text()"));

        String endTime = StringFormatUtil.changeObjectToStr(
                doc.selNOne("//div[@class='house-title LOGVIEWDATA LOGVIEW']/div/span/text()"));
        String endTimeSt = endTime.substring(0, endTime.indexOf("成交")).replaceAll(" ", "");
        String currentPrice = StringFormatUtil.changeObjectToStr(
                doc.selNOne("//div[@class='info fr']/div[1]/span/i/text()"));
        //单位价格
        String unitPrice = StringFormatUtil.changeObjectToStr(
                doc.selNOne("//div[@class='info fr']/div[1]/b/text()"));


        String listingPrice = StringFormatUtil.changeObjectToStr(
                doc.selNOne("//div[@class='info fr']/div[3]/span[1]/label/text()"));

        String transactionCycle = StringFormatUtil.changeObjectToStr(
                doc.selNOne("//div[@class='info fr']/div[3]/span[2]/label/text()"));

        String priceAdjustment = StringFormatUtil.changeObjectToStr(
                doc.selNOne("//div[@class='info fr']/div[3]/span[3]/label/text()"));

        String lookTimes = StringFormatUtil.changeObjectToStr(
                doc.selNOne("//div[@class='info fr']/div[3]/span[4]/label/text()"));

        String attentionNum = StringFormatUtil.changeObjectToStr(
                doc.selNOne("//div[@class='info fr']/div[3]/span[5]/label/text()"));

        String viewNum = StringFormatUtil.changeObjectToStr(
                doc.selNOne("//div[@class='info fr']/div[3]/span[6]/label/text()"));
        String houseType = StringFormatUtil.changeObjectToStr(
                doc.selNOne("//div[@class='introContent']/div[1]/div[2]/ul/li[1]/text()"));
        String houseFloor = StringFormatUtil.changeObjectToStr(
                doc.selNOne("//div[@class='introContent']/div[1]/div[2]/ul/li[2]/text()"));
        String constructionArea = StringFormatUtil.changeObjectToStr(
                doc.selNOne("//div[@class='introContent']/div[1]/div[2]/ul/li[3]/text()"));
        String houseStructure = StringFormatUtil.changeObjectToStr(
                doc.selNOne("//div[@class='introContent']/div[1]/div[2]/ul/li[4]/text()"));
        String innerArea = StringFormatUtil.changeObjectToStr(
                doc.selNOne("//div[@class='introContent']/div[1]/div[2]/ul/li[5]/text()"));
        String buildingType = StringFormatUtil.changeObjectToStr(
                doc.selNOne("//div[@class='introContent']/div[1]/div[2]/ul/li[6]/text()"));
        String houseOrientation = StringFormatUtil.changeObjectToStr(
                doc.selNOne("//div[@class='introContent']/div[1]/div[2]/ul/li[7]/text()"));
        String builtEra = StringFormatUtil.changeObjectToStr(
                doc.selNOne("//div[@class='introContent']/div[1]/div[2]/ul/li[8]/text()"));
        String renovationCondition = StringFormatUtil.changeObjectToStr(
                doc.selNOne("//div[@class='introContent']/div[1]/div[2]/ul/li[9]/text()"));
        String buildingStructure = StringFormatUtil.changeObjectToStr(
                doc.selNOne("//div[@class='introContent']/div[1]/div[2]/ul/li[10]/text()"));
        String heatingMethod = StringFormatUtil.changeObjectToStr(
                doc.selNOne("//div[@class='introContent']/div[1]/div[2]/ul/li[11]/text()"));
        String ladderRatio = StringFormatUtil.changeObjectToStr(
                doc.selNOne("//div[@class='introContent']/div[1]/div[2]/ul/li[12]/text()"));
        String yearLimit = StringFormatUtil.changeObjectToStr(
                doc.selNOne("//div[@class='introContent']/div[1]/div[2]/ul/li[13]/text()"));
        String elevatorFlag = StringFormatUtil.changeObjectToStr(
                doc.selNOne("//div[@class='introContent']/div[1]/div[2]/ul/li[14]/text()"));


        String code = StringFormatUtil.changeObjectToStr(
                doc.selNOne("//div[@class='introContent']/div[2]/div[2]/ul/li[1]/text()"));
        String tradingAuthority = StringFormatUtil.changeObjectToStr(
                doc.selNOne("//div[@class='introContent']/div[2]/div[2]/ul/li[2]/text()"));
        String listingTime = StringFormatUtil.changeObjectToStr(
                doc.selNOne("//div[@class='introContent']/div[2]/div[2]/ul/li[3]/text()"));
        String useType = StringFormatUtil.changeObjectToStr(
                doc.selNOne("//div[@class='introContent']/div[2]/div[2]/ul/li[4]/text()"));
        String houseLimit = StringFormatUtil.changeObjectToStr(
                doc.selNOne("//div[@class='introContent']/div[2]/div[2]/ul/li[5]/text()"));
        String houseBelong = StringFormatUtil.changeObjectToStr(
                doc.selNOne("//div[@class='introContent']/div[2]/div[2]/ul/li[6]/text()"));

        LianJiaTranscationData lianJiaTranscationData = new LianJiaTranscationData();
        lianJiaTranscationData.setAreaId((Integer) response.getMeta().get("areaId"));
        lianJiaTranscationData.setCode(code);
        lianJiaTranscationData.setBuiltEra(builtEra);
        lianJiaTranscationData.setBuildingStructure(buildingStructure);
        lianJiaTranscationData.setCurrentPrice(currentPrice);
        lianJiaTranscationData.setHouseFloor(houseFloor);
        lianJiaTranscationData.setHouseLimit(houseLimit);
        lianJiaTranscationData.setCreateTime(new Date());
        lianJiaTranscationData.setConstructionArea(constructionArea);
        lianJiaTranscationData.setHouseType(houseType);
        lianJiaTranscationData.setElevatorFlag(elevatorFlag);
        lianJiaTranscationData.setHouseOrientation(houseOrientation);
        lianJiaTranscationData.setHouseBelong(houseBelong);
        lianJiaTranscationData.setTitle(title);
        lianJiaTranscationData.setUseType(useType);
        Integer attentionNumInt = "暂无数据".equals(attentionNum) || StringUtils.isBlank(attentionNum) ?
                0 : Integer.parseInt(attentionNum);
        lianJiaTranscationData.setAttentionNum(attentionNumInt);
        lianJiaTranscationData.setInnerGrea(innerArea);
        lianJiaTranscationData.setHeatingMethod(heatingMethod);
        lianJiaTranscationData.setLadderRatio(ladderRatio);
        lianJiaTranscationData.setListingPrice(listingPrice);
        lianJiaTranscationData.setListingTime(listingTime);
        lianJiaTranscationData.setTradingAuthority(tradingAuthority);
        lianJiaTranscationData.setUnitPrice(unitPrice);
        lianJiaTranscationData.setViewNum(viewNum);
        lianJiaTranscationData.setYearLimit(yearLimit);
        lianJiaTranscationData.setBuildingType(buildingType);
        lianJiaTranscationData.setRenovationCondition(renovationCondition);
        Integer transactionInt = "暂无数据".equals(transactionCycle) || StringUtils.isBlank(transactionCycle) ?
                0 : Integer.parseInt(transactionCycle);
        lianJiaTranscationData.setTransactionCycle(transactionInt);
        Integer lookTimesInt = "暂无数据".equals(lookTimes) || StringUtils.isBlank(lookTimes) ?
                0 : Integer.parseInt(lookTimes);
        lianJiaTranscationData.setLookTimes(lookTimesInt);
        lianJiaTranscationData.setHouseStructure(houseStructure);
        Integer priceAdjustmentInt = "暂无数据".equals(priceAdjustment) || StringUtils.isBlank(priceAdjustment) ?
                0 : Integer.parseInt(priceAdjustment);
        lianJiaTranscationData.setPriceAdjustment(priceAdjustmentInt);
        lianJiaTranscationData.setEndTime(endTimeSt);
        try {
            lianJiaTransactionDataDao.save(lianJiaTranscationData);
        }catch (Exception e){
            logger.error("并发异常插入了重复数据" + e.getMessage());
        }

    }

}
