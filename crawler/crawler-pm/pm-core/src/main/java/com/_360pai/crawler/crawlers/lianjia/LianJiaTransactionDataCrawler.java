package com._360pai.crawler.crawlers.lianjia;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;

import com._360pai.crawler.common.dto.AddressDetail;
import com._360pai.crawler.common.util.BaiDuMapUtil;
import com._360pai.crawler.common.util.StringFormatUtil;
import com._360pai.crawler.dao.lianjia.LianJiaAreaModelDao;
import com._360pai.crawler.dao.lianjia.LianJiaTransactionDataDao;
import com._360pai.crawler.model.lianjia.LianJiaAreaModel;
import com._360pai.crawler.model.lianjia.LianJiaTranscationData;
import org.apache.commons.lang.StringUtils;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            for(int i = 1, len = all.size() + 1; i < len; i++) {
                LianJiaAreaModel lianJiaAreaModel = all.get(i);
                Map<String, Object> params = new HashMap<>();
                params.put("areaId", lianJiaAreaModel.getId());
                params.put("lianJiaAreaModel", lianJiaAreaModel);

                push(Request.build(lianJiaAreaModel.getAreaUrl(),
                        LianJiaTransactionDataCrawler::getAreaList).setMeta(params));

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
        JXNode total = doc.selNOne("//div[@class='total fl']/span/text()");

        String totalSt = total.toString().replace(" ", "");
        Integer totalNum = Integer.parseInt(totalSt);
        LianJiaAreaModel lianJiaAreaModel = (LianJiaAreaModel) response.getMeta().get("lianJiaAreaModel");
        Integer transactionNum = lianJiaAreaModel.getTransactionNum();

        if(totalNum != 0 && jxNode != null && transactionNum != null && totalNum > transactionNum) {
            String totalPageSt = jxNode.toString();
            String totalPageSub = totalPageSt.substring(totalPageSt.indexOf(":") + 1, totalPageSt.indexOf(","));

            Integer totalPage = Integer.parseInt(totalPageSub);

            try {
                for(int i = 1; i < totalPage + 1; i++) {
                    push(Request.build(response.getUrl() + "pg" + i,
                            LianJiaTransactionDataCrawler::getPageList).setMeta(response.getMeta()));

                    logger.info("开始爬取第" + i + "页数据");
                    Thread.sleep(1000);
                }
            }catch (Exception e){
                e.printStackTrace();
                logger.info("根据地区爬取异常数据异常{}" , e.getMessage());
            }

            lianJiaAreaModel.setTransactionNum(totalNum);

            lianJiaAreaModelDao.saveAndFlush(lianJiaAreaModel);
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
                        push(Request.build(detailUrl,
                                LianJiaTransactionDataCrawler::getDetailData).setMeta(response.getMeta()));
                    }else{
                        logger.info("已经存在数据" + lianJiaTranscationData.getTitle());
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

        Integer areaId = (Integer) response.getMeta().get("areaId");
        LianJiaTranscationData lianJiaTranscationData = new LianJiaTranscationData();
        lianJiaTranscationData.setItemUrl(response.getUrl());
        lianJiaTranscationData.setAreaId(areaId);
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
        lianJiaTranscationData.setDeleteFlag(0);
        lianJiaTranscationData.setEndMonth(endTime.substring(0, 7));

        // 特殊处理无成交价数据和成交价为区间数据
        if(StringUtils.isBlank(currentPrice) || currentPrice.contains("-")) {
            setLianJiaCoordinate(lianJiaTranscationData, doc);
        }
        // 特殊处理经纬度
        LianJiaAreaModel lianJiaAreaModel = lianJiaAreaModelDao.findOne(areaId);
        String[] titleArray = lianJiaTranscationData.getTitle().split(" ");
        AddressDetail addressDetail = setCoordinate(titleArray[0], lianJiaAreaModel);
        if(addressDetail != null) {
            lianJiaTranscationData.setLat(addressDetail.getLat());
            lianJiaTranscationData.setLng(addressDetail.getLng());
            addressDetail = setAddressDetail(addressDetail);
            lianJiaTranscationData.setAddress(addressDetail.getAdress());
        }

        try {
            lianJiaTransactionDataDao.save(lianJiaTranscationData);
        }catch (Exception e){
            logger.error("并发异常插入了重复数据" + e.getMessage());
        }

    }

    private AddressDetail setAddressDetail(AddressDetail addressDetail) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("location", addressDetail.getLat() + "," + addressDetail.getLng());

        addressDetail = BaiDuMapUtil.getAdressDetailFormat(params, addressDetail);

        return addressDetail;
    }

    private AddressDetail setCoordinate(String name, LianJiaAreaModel lianJiaAreaModel) {
        Map<String, String> params = new HashMap<>();
        params.put("address", lianJiaAreaModel.getProvince() +
                lianJiaAreaModel.getCity() + lianJiaAreaModel.getArea() + name);

        AddressDetail adressDetail = BaiDuMapUtil.getAdressDetail(params);

        return adressDetail;
    }

    private void setLianJiaCoordinate(LianJiaTranscationData lianJiaTranscationData, JXDocument doc) {


        JXNode selNOne = doc.selNOne("//div[@class='price']/text()");

        String currentPriceSt = selNOne.toString();
        String priceSt = "";
        if(StringUtils.isNotBlank(currentPriceSt) &&  !"暂无价格".equals(currentPriceSt) && !"暂无数据".equals(lianJiaTranscationData.getConstructionArea())) {
            priceSt = doc.selOne("//div[@class='price']/span/i/text()").toString();
        }else if(lianJiaTranscationData.getCurrentPrice().contains("-") && !"暂无数据".equals(lianJiaTranscationData.getConstructionArea())){
            priceSt = lianJiaTranscationData.getCurrentPrice();
        }

        if(StringUtils.isNotBlank(priceSt)) {
            String[] split = priceSt.split("-");

            BigDecimal bigDecimal1 = new BigDecimal(split[0]);
            BigDecimal bigDecimal2 = new BigDecimal(split[1]);
            BigDecimal bigDecimal3 = new BigDecimal(2);
            BigDecimal bigDecimal4 = new BigDecimal(lianJiaTranscationData.getConstructionArea().replace("㎡", ""));
            BigDecimal bigDecimal5 = new BigDecimal(10000);

            BigDecimal currentPrice = bigDecimal1.add(bigDecimal2).divide(bigDecimal3, 0, BigDecimal.ROUND_HALF_UP);

            BigDecimal unitPrice = currentPrice.multiply(bigDecimal5).divide(bigDecimal4, 0, BigDecimal.ROUND_HALF_UP);

            String listPrice = doc.selOne("//div[@class='msg']/span[1]/label/text()").toString();


            lianJiaTranscationData.setListingPrice(listPrice);
            lianJiaTranscationData.setUnitPrice(unitPrice.toString());
            lianJiaTranscationData.setCurrentPrice(currentPrice.toString());
        }

    }

}
