package com._360pai;

import com._360pai.seimi.util.DateUtil;
import com._360pai.seimi.util.HtmlRegexUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import jodd.io.FileUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.util.*;

/**
 * @author xdrodger
 * @Title: SimpleTest
 * @ProjectName zeus
 * @Description:
 * @date 15/09/2018 11:55
 */
public class SimpleTest {

    private String sepa = java.io.File.separator;

    @Test
    public void testParseGPaiList() {
        try {
            String content = FileUtil.readString(System.getProperty("user.dir") + sepa + "src" + sepa + "test" + sepa + "lib" + sepa + "gpai-list.html");
            Document parse = Jsoup.parse(content);

            String pageStr = parse.select("body > div.container > div.block-wrap.notice-wrap > div.auto > div.page-bar > div.page-nav > span.page-infos > label").text();
            Integer page = Integer.valueOf(HtmlRegexUtils.extractNum(pageStr));
            Elements lis = parse.select("body > div.container > div.block-wrap.notice-wrap > div.auto > div.filt-result-list ul.main-col-list.clearfix > li");
            for (Element li : lis) {
                String detailUrl = li.select("div.list-item > a").first().attr("href");
                System.out.println(detailUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testParseGPaiDetail() {
        try {
            String content = FileUtil.readString(System.getProperty("user.dir") + sepa + "src" + sepa + "test" + sepa + "lib" + sepa + "gpai-detail.html");
            Document parse = Jsoup.parse(content);

            JSONObject data = new JSONObject();
            data.put("itemUrl", "");
            data.put("itemId", "");
            // 成交价
            data.put("amount", "");

            //
            data.put("bidDetails", "");


            Element title = parse.select("body > div > div.block-wrap.details-wrap > div.auto > div.details-top.clearfix > div.details-main > div.d-m-infos > div:nth-child(1) > div > b").first();
            if (title != null) {
                data.put("title", title.text());
            }
            List<String> imgUrls = new ArrayList<>();
            Elements list = parse.select("body > div > div.block-wrap.details-wrap > div.auto > div.details-top.clearfix > div.details-main > div.d-m-pics > ul >li > a");
            for (Element item : list) {
                imgUrls.add(item.attr("rev"));
            }
            data.put("imgUrls", imgUrls);

            Element priceStep = parse.getElementById("Price_Step");
            if (priceStep != null) {
                data.put("priceStep", HtmlRegexUtils.extractNum((priceStep.text())));
            }
            Element priceStart = parse.getElementById("Price_Start");
            if (priceStep != null) {
                data.put("priceStart", HtmlRegexUtils.extractNum((priceStart.text())));
            }

            Element analysisData = parse.select("body > div.container > div.block-wrap.details-wrap > div > div.details-top.clearfix > div.details-main > div.d-m-pics > div.peoples-infos").first();
            if (analysisData != null) {
                Element participantNumber = analysisData.getElementsContainingOwnText("人报名").first();
                if (participantNumber != null) {
                    data.put("participantNumber", HtmlRegexUtils.extractNum(participantNumber.text()));
                }
                Element remindCount = analysisData.getElementsContainingOwnText("人设置提醒").first();
                if (remindCount != null) {
                    data.put("remindCount", HtmlRegexUtils.extractNum(remindCount.text()));
                }
                Element viewCount = analysisData.getElementsContainingOwnText("人围观").first();
                if (viewCount != null) {
                    System.out.println(viewCount.text());
                    data.put("viewCount", HtmlRegexUtils.extractNum(viewCount.text()));
                }
            }

            //Elements tds = parse.select("body > div > div.block-wrap.details-wrap > div.auto > div.details-top.clearfix > div.details-main > div.d-m-infos > div:nth-child(3) > div > table:nth-child(1) > tbody > tr > td");
            //for (Element td : tds) {
            //     td.getElementsContainingOwnText("保证金");
            //}
            Element firstTBody = parse.select("body > div > div.block-wrap.details-wrap > div.auto > div.details-top.clearfix > div.details-main > div.d-m-infos > div:nth-child(3) > div > table:nth-child(1) > tbody").first();
            if (firstTBody != null) {
                Element deposit = firstTBody.getElementsContainingOwnText("保证金").first();
                if (deposit != null) {
                    data.put("deposit", HtmlRegexUtils.extractNum3(deposit.text()));
                }
                Element biddingExtension = firstTBody.getElementsContainingOwnText("延时周期").first();
                if (biddingExtension != null) {
                    data.put("biddingExtension", HtmlRegexUtils.extractNum(biddingExtension.text()));
                }
                Element refPrice = firstTBody.getElementsContainingOwnText("评估价").first();
                if (refPrice != null) {
                    data.put("refPrice", HtmlRegexUtils.extractNum(refPrice.text()));
                }
            }
            //for (Element td : tds) {
            //    td.getElementsContainingOwnText("保证金");
            //}

            Element contactTBody = parse.select("body > div.container > div.block-wrap.details-wrap > div > div.details-top.clearfix > div.details-main > div.d-m-infos > div:nth-child(3) > div > table:nth-child(5) > tbody").first();
            if (contactTBody != null) {
                Element contactName = contactTBody.getElementsContainingOwnText("联系人").first();
                if (contactName != null) {
                    data.put("contactName", HtmlRegexUtils.extractStringByColon(contactName.text()));
                }
                Element contactPhone = contactTBody.getElementsContainingOwnText("联系电话").first();
                if (contactPhone != null) {
                    data.put("contactPhone", HtmlRegexUtils.extractStringByColon(contactPhone.text()));
                }
            }
            System.out.println("--");
            System.out.println("--");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Test
    public void testTime() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MILLISECOND, 100864800);
        System.out.println(DateUtil.formatDate(cal.getTime(), DateUtil.STYLE_1));
        System.out.println("--");

        System.out.println(new Date().getTime() / 1000);
        System.out.println("--");

        Date date = DateUtil.parseDateTime("2018-11-14 10:00:00");
        System.out.println(DateUtil.getMargin(date, new Date()));
        Date now = new Date();
        System.out.println(date.getTime() - now.getTime());

        System.out.println("--");

    }

    @Test
    public void testString() {
        try {
            String content = FileUtil.readString(System.getProperty("user.dir") + sepa + "src" + sepa + "test" + sepa + "lib" + sepa + "demo.txt");
            JSONObject json = JSON.parseObject(content);
            System.out.println(json.toJSONString());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testJSON() {
        String jsonStr = "{\"code\":0,\"msg\":\"\",\"data\":{\"itemId\":12772,\"auctionId\":0,\"eChannel\":\"3\",\"refreshTime\":0,\"webAuctionName\":\"\",\"itemName\":\"\",\"stateDesc1\":\"已经结束\",\"stateDesc2\":\"流标\",\"timeDesc\":\"\",\"stateDesc\":\"流标\",\"stateTime\":\"流标\",\"secondNow\":\"0\",\"AuctionBidType\":\"在线拍\",\"bidState\":\"4\",\"bidStateDesc\":\"已结束\",\"bidStateDesc2\":\"\",\"priceDesc\":\"起拍价\",\"priceShow\":\"-\",\"itemBuyerDesc\":\"\",\"joinCount\":\"0\",\"favCount\":\"0\",\"viewCount\":\"110\",\"bidCount\":\"0\",\"isLimited\":\"\",\"isLoan\":\"\",\"myPriority\":\"0\",\"myJoinState\":\"无\",\"myBuyerCode\":\"-\",\"priceStart\":\"0\",\"priceAppraise\":\"\",\"priceStep\":\"\",\"bidCountDesc\":\"--\",\"bidRunHour\":\"小时\",\"bidMode\":\"法院自主拍卖\",\"depositShu\":\"0\",\"priceOther\":\"\",\"itemNumber\":\"\",\"itemUnit\":\"\",\"priorityDesc\":\"-\",\"priceType\":\"0\",\"priceTypeDesc\":\"按总价拍卖\",\"organName\":\"最高人民法院\",\"courtCode\":\"0\",\"contactName\":\"&nbsp;\",\"contactTel\":\"\",\"commisionDesc\":\"-\",\"videoUrl\":\"\",\"Debug\":\"_myBidState_0_Web_Deposit_Type_ITEM\",\"webAuctionTime\":\"\",\"linkTel\":\"\",\"imgUrl\":\"images/notice-picnull.jpg\",\"stateDesc1____\":\"已经结束\",\"secondCount\":\"-88888\",\"itemType\":,\"catelogCode\":\"\",\"catelogState\":113,\"itemImgMore\":null,\"imgs\":[],\"itemMode\":\"在线拍\",\"favShu\":0,\"isFav\":\"False\",\"catelogTimeEnd\":\"\",\"itemReference\":0,\"myPrice\":0,\"myDepositShu\":\"0\",\"myBidStateDesc\":\"无\",\"catelogTimeStart\":\"\",\"auctionAdd\":\"\",\"auctionJoinEnd\":\"2050-1-1\",\"auctionJoinAdd\":\"\",\"itemPoint\":\"\",\"companyId\":0,\"Company_Name\":\"\",\"companyAddress\":\"\",\"companyTel\":\"\",\"itemRemos\":[],\"itemYou\":\"\",\"companyName\":\"0\",\"tags\":\"\",\"bidDetail\":[]}}";
        try {
            JSONObject json = JSON.parseObject(jsonStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--");
        try {
            jsonStr = jsonStr.replace("\"itemType\":", "\"itemType\":\"\"");
            System.out.println(jsonStr);
            JSONObject json = JSON.parseObject(jsonStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--");
    }

    @Test
    public void testTmp() {
        try {
            String url  = "http://www.rmfysszc.gov.cn/statichtml/rm_obj/96595.shtml";
            System.out.println(url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf(".")));
            System.out.println("--");

            System.out.println(60 * 24 * 3600 * 1000);
            System.out.println();



            for (int i = 0; i < 3; i ++) {
                System.out.println("00" + (i + 1));
            }

            System.out.println("--");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFilterString() {
        String detail = "<p><span style=\"font-size: 24px;\">一、债务人基本情况</span></p><p><span style=\"font-size: 24px;\">\t  安徽省鑫港再生资源有限公司（以下简称“鑫港再生”)是为建设马鞍山再生资源集散市场而成立的项目法人，2008年4月在马鞍山市工商行政管理局依法登记在册，注册资本为4700万元，由安徽鑫港炉料股份有限公司（以下简称“鑫港炉料”）100%出资。主要经营范围：生产性废旧金属、废五金家电、废电子产品、报废机械设备、非生产性废旧资源的收购、销售等，所属行业为：批发和零售业，目前该公司及其母公司鑫港炉料生产经营均处于停产状态。</span></p><p><span style=\"font-size: 24px;\">二、抵押物基本情况</span></p><p><span style=\"font-size: 24px;\">该笔债权抵押物充足，包括厂房和办公房地产。抵押物1为雨山区湖南路39号5层的办公地产，面积为664.02平方米。该办公地产位于红旗中路、湖南西路等城市主次干道，道路交通状况较好。抵押物2为 雨山区天门大道南段1188号1-5栋的厂房，面积共计41107平方米</span></p><p><span style=\"font-size: 24px;\">三、诉讼情况</span></p><p><span style=\"font-size: 24px;\">\t该案件已经进入执行程序，但尚未申请评估拍卖。</span></p><p><img src=\"https://cdn-images.360pai.com/FnnA6C5ZtDTvrqipn-1Dz-D5M3Xf\"></p>";

        detail = detail.replaceAll("font-size: \\d*px;", "");

        System.out.println(detail);

        String url = "http://www.360pai.com/rest-web/open/bid_order";
        System.out.println(url.indexOf("/rest-web/open/bid_order"));
        System.out.println(url.substring(0, url.indexOf("/rest-web/open/bid_order")));

        Set<String> set = new HashSet<>();
        set.add("a");
        set.add("b");
        set.add("c");
        System.out.println(set.toString());
        String str = String.join(",", set);
        System.out.println(str);
        System.out.println(String.join(",", set));
    }



    @Test
    public void testJson() {
        try {
            JSONObject json = new JSONObject();
            System.out.println(json.getString("name"));
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testList() {
        List<String> list1 = new ArrayList<>();
        list1.add("a");
        list1.add("b");

        List<String> list2 = new ArrayList<>();
        list2.add("d");
        list2.add("c");
        list1.retainAll(list2);
        System.out.println(list1);
    }

}
