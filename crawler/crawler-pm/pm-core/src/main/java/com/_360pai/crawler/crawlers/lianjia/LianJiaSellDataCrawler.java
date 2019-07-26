package com._360pai.crawler.crawlers.lianjia;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import com._360pai.crawler.common.util.StringFormatUtil;
import com._360pai.crawler.dao.lianjia.LianJiaAreaModelDao;
import com._360pai.crawler.dao.lianjia.LianJiaSellDataDao;
import com._360pai.crawler.dao.lianjia.LianJiaTransactionDataDao;
import com._360pai.crawler.model.lianjia.LianJiaAreaModel;
import com._360pai.crawler.model.lianjia.LianJiaSellData;
import com._360pai.crawler.model.lianjia.LianJiaTranscationData;
import org.apache.commons.lang.StringUtils;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Crawler(name = "lianjiaSellData", useUnrepeated = false)
@Component
public class LianJiaSellDataCrawler extends BaseSeimiCrawler {

    @Autowired
    private LianJiaAreaModelDao lianJiaAreaModelDao;
    @Autowired
    private LianJiaSellDataDao lianJiaSellDataDao;

    @Override
    public String[] startUrls() {
        return new String[0];
    }

    @Override
    public void start(Response response) {
        List<LianJiaAreaModel> all = lianJiaAreaModelDao.findAll();

        try {
            for(int i = 0, len = all.size(); i < len; i++) {
                LianJiaAreaModel lianJiaAreaModel = all.get(i);
                Map<String, Object> params = new HashMap<>();
                params.put("areaId", lianJiaAreaModel.getId());
                String newUrl = lianJiaAreaModel.getAreaUrl().replaceAll("chengjiao", "ershoufang");
                push(Request.build(newUrl,
                        LianJiaSellDataCrawler::getAreaList).setMeta(params));

                Thread.sleep(500);
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
                        LianJiaSellDataCrawler::getPageList).setMeta(response.getMeta()));

                Thread.sleep(500);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info("根据地区爬取异常数据异常{}" , e.getMessage());
        }
    }


    public void getPageList(Response response) {
        JXDocument doc = response.document();
        List<JXNode> jxNodes = null;
        jxNodes = doc.selN("//ul[@class='sellListContent']/li/a/@href");
        if(jxNodes == null || jxNodes.size() <= 0){
            jxNodes = doc.selN("//ul[@class='sellListContent LOGCLICKDATA LOGVIEWDATA']/li/a/@href");
        }
        try {
            if(jxNodes != null && jxNodes.size() > 0) {
                for(int i = 0, len = jxNodes.size(); i < len; i++ ) {
                    String detailUrl = jxNodes.get(i).toString();
                    String code = detailUrl.substring(detailUrl.indexOf("ershoufang/") + 11, detailUrl.indexOf(".html"));
                    LianJiaSellData lianJiaSellData = lianJiaSellDataDao.selectOneByCode(code);
                    if(lianJiaSellData == null){
                        Map<String, Object> meta = response.getMeta();

                        push(Request.build(jxNodes.get(i).toString(),
                                LianJiaSellDataCrawler::getDetailData).setMeta(meta));
                    }
                }
            }
        }catch (Exception e){
            logger.info("根据分页异常数据异常{}" , e.getMessage());
        }
    }


    public void getDetailData(Response response) {
        JXDocument doc = response.document();

        String title =  StringFormatUtil.changeObjectToStr(
                doc.selNOne("//div[@class='sellDetailHeader']/div/div/div/h1/text()"));
        String currentPrice = StringFormatUtil.changeObjectToStr(
                doc.selNOne("//div[@class='price ']/span/allText()"));
         //单位价格
        String unitPrice = StringFormatUtil.changeObjectToStr(
                doc.selNOne("//div[@class='price ']/div/div/span/text()"));

        // 基本信息
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
        String buildingStructure = StringFormatUtil.changeObjectToStr(
                doc.selNOne("//div[@class='introContent']/div[1]/div[2]/ul/li[8]/text()"));
        String renovationCondition = StringFormatUtil.changeObjectToStr(
                doc.selNOne("//div[@class='introContent']/div[1]/div[2]/ul/li[9]/text()"));
        String ladderRatio = StringFormatUtil.changeObjectToStr(
                doc.selNOne("//div[@class='introContent']/div[1]/div[2]/ul/li[10]/text()"));
        String elevatorFlag = StringFormatUtil.changeObjectToStr(
                doc.selNOne("//div[@class='introContent']/div[1]/div[2]/ul/li[11]/text()"));
        String yearLimit = StringFormatUtil.changeObjectToStr(
                doc.selNOne("//div[@class='introContent']/div[1]/div[2]/ul/li[12]/text()"));


        String listingTime = StringFormatUtil.changeObjectToStr(
                doc.selNOne("//div[@class='introContent']/div[2]/div[2]/ul/li[1]/span[2]/text()"));
        String tradingAuthority = StringFormatUtil.changeObjectToStr(
                doc.selNOne("//div[@class='introContent']/div[2]/div[2]/ul/li[2]/span[2]/text()"));
        String lastTime = StringFormatUtil.changeObjectToStr(
                doc.selNOne("//div[@class='introContent']/div[2]/div[2]/ul/li[3]/span[2]/text()"));
        String useType = StringFormatUtil.changeObjectToStr(
                doc.selNOne("//div[@class='introContent']/div[2]/div[2]/ul/li[4]/span[2]/text()"));
        String houseLimit = StringFormatUtil.changeObjectToStr(
                doc.selNOne("//div[@class='introContent']/div[2]/div[2]/ul/li[5]/span[2]/text()"));
        String houseBelong = StringFormatUtil.changeObjectToStr(
                doc.selNOne("//div[@class='introContent']/div[2]/div[2]/ul/li[6]/span[2]/text()"));
        String mortgageInformation = StringFormatUtil.changeObjectToStr(
                doc.selNOne("//div[@class='introContent']/div[2]/div[2]/ul/li[7]/span[2]/text()"));
        String roomPreparation = StringFormatUtil.changeObjectToStr(
                doc.selNOne("//div[@class='introContent']/div[2]/div[2]/ul/li[8]/span[2]/text()"));
        String attentionNum = StringFormatUtil.changeObjectToStr(
                doc.selNOne("//span[@id='favCount']/text()"));
        String lookTimes = StringFormatUtil.changeObjectToStr(
                doc.selNOne("//span[@id='cartCount']/text()"));
        String code = StringFormatUtil.changeObjectToStr(
                doc.selNOne("//div[@class='houseRecord']/span[2]/text()"));

        LianJiaSellData lianJiaSellData = new LianJiaSellData();
        lianJiaSellData.setAreaId((Integer) response.getMeta().get("areaId"));
        lianJiaSellData.setCode(code);
        lianJiaSellData.setBuildingStructure(buildingStructure);
        lianJiaSellData.setCurrentPrice(currentPrice + "万");
        lianJiaSellData.setHouseFloor(houseFloor);
        lianJiaSellData.setHouseLimit(houseLimit);
        lianJiaSellData.setCreateTime(new Date());
        lianJiaSellData.setConstructionArea(constructionArea);
        lianJiaSellData.setHouseType(houseType);
        lianJiaSellData.setElevatorFlag(elevatorFlag);
        lianJiaSellData.setHouseOrientation(houseOrientation);
        lianJiaSellData.setHouseBelong(houseBelong);
        lianJiaSellData.setTitle(title);
        lianJiaSellData.setUseType(useType);

        Integer attentionNumInt = "暂无数据".equals(attentionNum) || StringUtils.isBlank(attentionNum) ?
                0 : Integer.parseInt(attentionNum);
        lianJiaSellData.setAttentionNum(attentionNumInt);
        lianJiaSellData.setInnerGrea(innerArea);
        lianJiaSellData.setLadderRatio(ladderRatio);
        lianJiaSellData.setListingTime(listingTime);
        lianJiaSellData.setTradingAuthority(tradingAuthority);
        lianJiaSellData.setUnitPrice(unitPrice);
        lianJiaSellData.setYearLimit(yearLimit);
        lianJiaSellData.setBuildingType(buildingType);
        lianJiaSellData.setRenovationCondition(renovationCondition);
        Integer lookTimesInt = "暂无数据".equals(lookTimes) || StringUtils.isBlank(lookTimes) ?
                0 : Integer.parseInt(lookTimes);
        lianJiaSellData.setLookTimes(lookTimesInt);
        lianJiaSellData.setHouseStructure(houseStructure);
        lianJiaSellData.setMortgageInformation(mortgageInformation);
        lianJiaSellData.setLastTime(lastTime);
        lianJiaSellData.setRoomPreparation(roomPreparation);

        try {
            lianJiaSellDataDao.save(lianJiaSellData);
        }catch (Exception e){
            logger.error("并发异常插入了重复数据" + e.getMessage());
        }
    }
}
