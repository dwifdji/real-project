package com._360pai.seimi.crawlers.aliPm;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import com._360pai.seimi.commons.AliPmEnum;
import com._360pai.seimi.commons.util.ComUtils;
import com._360pai.seimi.commons.util.CrawlerUtils;
import com._360pai.seimi.config.AsyncConfig;
import com._360pai.seimi.model.alipm.TAliPmSf;
import com._360pai.seimi.model.alipm.TAliPmSfUrl;
import com._360pai.seimi.service.alipm.AliPmSfService;
import com._360pai.seimi.service.alipm.AliPmUrlService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 根据url 获取阿里司法拍卖数据
 */
@Crawler(name = "getAliPmSfByUrlCrawler")
@Component
public class GetAliPmSfByUrlCrawler extends BaseSeimiCrawler {

    private static final Pattern COMPILE = Pattern.compile("\\{\"data\":\\[(.*?)]}");


    @Autowired
    private AsyncConfig asyncConfig;


    @Resource
    private AliPmUrlService aliPmUrlService;


    @Resource
    private AliPmSfService aliPmSfService;




    @Override
    public String[] startUrls() {

        return new String[]{"https://zc-paimai.taobao.com/zc_item_list.htm"};
    }

    @Override
    public void start(Response response) {

        try {

            List<TAliPmSfUrl>   urlList = aliPmUrlService.findAliPmSfTodoUrlList();
            for(TAliPmSfUrl  cityUrl :urlList){
                Map<String ,Object> params = getMap(cityUrl);
                push(Request.build( cityUrl.getReqUrl(), GetAliPmSfByUrlCrawler::getAliZcList).setMeta(params));
                Thread.sleep(200);

                //更新url状态
                cityUrl.setStatus(AliPmEnum.URL_STATUS.DONE.getKey());
                cityUrl.setUpdateTime(new Date());
                aliPmUrlService.updateAliPmSfUrl(cityUrl);
            }


        } catch (Exception e) {

        }

    }

    private Map<String,Object> getMap(TAliPmSfUrl cityUrl) {
        Map<String,Object> params = new HashMap<>();

        params.put("proName",cityUrl.getProName());
        params.put("cityName",cityUrl.getCityName());
        params.put("areaName",cityUrl.getAreaName());
        params.put("typeName",cityUrl.getTypeName());
        params.put("typeCode",cityUrl.getCategoryCode());

        return params;
    }


    /**
     *
     *获取阿里区域资产信息
     */
    public void getAliZcList(Response response) {


        try {
            Map<String,Object> map = response.getMeta();

            String pageStr = CrawlerUtils.getDocumentStr(response,"body > div.sf-wrap > div.pagination.J_Pagination > span.page-skip > em");

            Integer page = Integer.valueOf(pageStr);
            String url = response.getRequest().getUrl();
            for (int i = 1; i <= page; i++) {
                String pageUrl = url + "&page=" + i;
                Thread.sleep(500);
                push(Request.build(pageUrl, GetAliPmSfByUrlCrawler::getList).setMeta(map));
            }
        }catch (Exception e){

        }

    }






    /**
     *
     *获取资产信息
     */
    public void getList(Response response) {

        //解析每一页的数据
        Map<String,Object> reqMap = response.getMeta();
        String content = response.getContent();

        String doc = content.replace("\n", "");
        String text = null;
        Matcher matcher = COMPILE.matcher(doc);
        if (matcher.find()) {
            text = matcher.group(0);
        }
        if (StringUtils.isNoneBlank(text)) {
            JSONObject jsonObject = JSONObject.parseObject(text);
            JSONArray data = jsonObject.getJSONArray("data");
            for (int i = 0; i < data.size(); i++) {

                JSONObject json = data.getJSONObject(i);
                String itemUrl = json.getString("itemUrl");
                itemUrl = "https:" + itemUrl;
                Map<String,Object> map = new HashMap<>();
                map.putAll(reqMap);
                map.put("data",json.toJSONString());
                map.put("itemUrl",itemUrl);

                push(Request.build(itemUrl, GetAliPmSfByUrlCrawler::getDetail).setMeta(map));

            }
        }


  }


    /**
     *
     *获取详情信息
     */
     public void getDetail(Response response)   {

         asyncConfig.aliZcListExecutor().execute(new Runnable() {
             @Override
             public void run() {
                 getExecuteDetail(response);

             }
         });


    }

    private void getExecuteDetail(Response response) {
         //解析每一页的数据
        Map<String,Object> reqMap = response.getMeta();

        List<JXNode> picList =CrawlerUtils.getJXNodeList(response,"//ul[@id='J_UlThumb']/li");

        JXDocument document = response.document();

        //获取详情的图片
        JSONArray picArray = new JSONArray();

        for(JXNode node :picList){

            String pic = "https:"+node.sel("/div/a/img/@src").get(0).toString();
            picArray.add(pic);
        }
        String appler = (String) document.selOne("//*[@id=\"page\"]/div[4]/div/div/div[2]/div[3]/span[1]/em/text()");
        //提醒人数
        String reminder = (String) document.selOne("//*[@id=\"page\"]/div[4]/div/div/div[2]/div[3]/span[3]/em/text()");
        //浏览人数
        String looker = (String) document.selOne("//*[@id=\"J_Looker\"]/text()");

        //处置单位
        String disposition = (String) document.selOne("//*[@id=\"page\"]/div[4]/div/div/div[3]/div[2]/p/span[2]/a/text()");
        //联系人
        String person = (String) document.selOne("//*[@id=\"page\"]/div[4]/div/div/div[3]/div[2]/div/div/em/text()");
        //联系电话
        String personPhone = (String) document.selOne("//*[@id=\"page\"]/div[4]/div/div/div[3]/div[2]/div/div/div/allText()");


        List<JXNode> alist = document.selN("//div[@class=\"crumbs\"]/a");

        if(alist !=null &&alist.size()>1){
            disposition =alist.get(1).sel("/text()").get(0).toString();
        }


        //成交价
        String amount = (String) document.selOne("//*[@id=\"sf-price\"]/div/p[1]/span/em/text()");

        //起始价
        String beginPirce = null;

        //加价幅度
        String raisePrice = null;

        //运费
        String freight = null;
        //竞价周期
        String cycle = null;

        //优先购买人
        String preemptor = null;

        //保证金
        String deposit = null;
        //延迟周期
        String delayPeriod = null;

        //保留价
        String reservePrice = null;


        //获取表格的数据信息
        List<JXNode> tableList = document.selN("//*[@id=\"J_HoverShow\"]/tr");


        for(JXNode tr:tableList){

            List<JXNode> tdList  = tr.sel("/td");


            for(JXNode td: tdList){

                List<JXNode>  tds = td.sel("/span/text()");

                if(tds==null||tds.size()<1){
                    continue;
                }
                String tdKey = tds.get(0).toString();

                tdKey =tdKey.replaceAll("\r|\n|\\s", "");

                if("起始价".equals(tdKey)){
                    beginPirce = td.sel("/span/span/text()").get(0).toString();
                }


                if("加价幅度".equals(tdKey)){
                    raisePrice = td.sel("/span/span/text()").get(0).toString();
                }


                if("运费".equals(tdKey)){
                    freight = td.sel("/span/text()").get(1).toString().replaceAll(":","");
                    if(StringUtils.isEmpty(freight)){
                        freight = td.sel("/span/span/text()").get(0).toString();
                    }
                }

                if("竞价周期".equals(tdKey)){
                    cycle = td.sel("/span/text()").get(1).toString().replaceAll(":","");
                }


                if("优先购买权人".equals(tdKey)){
                    preemptor = td.sel("/span/text()").get(1).toString().replaceAll(":","");
                }


                if("保证金".equals(tdKey)){
                    deposit = td.sel("/span/span/text()").get(0).toString();
                }



                if("延时周期".equals(tdKey)){
                    delayPeriod = td.sel("/span/text()").get(1).toString().replaceAll(":","");
                }

                if("保留价".equals(tdKey)){
                    reservePrice = td.sel("/span/text()").get(1).toString().replaceAll(":","");
                }


            }

        }


        JSONObject dataJson = JSONObject.parseObject(reqMap.get("data").toString());


        TAliPmSf model = new TAliPmSf();

        model.setAmount(amount);
        model.setAppler(appler);
        model.setProName(getValueByKey(reqMap,"proName"));
        model.setAreaName(getValueByKey(reqMap,"areaName"));
        model.setCityName(getValueByKey(reqMap,"cityName"));
        model.setCode(dataJson.getString("id"));
        model.setCreateTime(new Date());
        model.setCycle(cycle);
        model.setDeposit(deposit);
        model.setPerson(person);
        model.setDelayPeriod(delayPeriod);
        model.setReminder(reminder);
        model.setTitle(dataJson.getString("title"));
        model.setStatus(dataJson.getString("status"));
        model.setItemUrl(getValueByKey(reqMap,"itemUrl"));
        model.setReservePrice(reservePrice);
        model.setRaisePrice(raisePrice);
        model.setFreight(freight);
        model.setPreemptor(preemptor);
        model.setRaisePrice(raisePrice);
        model.setPersonPhone(personPhone);
        model.setDisposition(disposition);
        model.setPicUrl(picArray.toJSONString());
        model.setLooker(looker);
        model.setStaringPrice(beginPirce);
        model.setStartDate(ComUtils.timeStamp2Date(dataJson.getString("start")));
        model.setEndDate(ComUtils.timeStamp2Date(dataJson.getString("end")));
        model.setStartStamp(dataJson.getString("start"));
        model.setEndStamp(dataJson.getString("end"));

        model.setDataDetail(getValueByKey(reqMap,"data"));
        model.setCurrentPrice(dataJson.getString("currentPrice"));//当前价
        model.setBidNum(dataJson.getString("bidCount"));//竞价总数
        model.setConsultPrice(dataJson.getString("consultPrice"));//评估价
        model.setTypeName(getValueByKey(reqMap,"typeName"));
        model.setTypeCode(getValueByKey(reqMap,"typeCode"));

        model.setDeleteFlag(0);
        model.setMarketPrice(dataJson.getString("marketPrice"));//市场价

        aliPmSfService.saveAliPmSf(model);


    }









    private String getValueByKey(Map<String,Object> reqMap, String proName) {
        Object value = reqMap.get(proName);

        if(value==null){
            return null;
        }

        return value.toString();
    }


}