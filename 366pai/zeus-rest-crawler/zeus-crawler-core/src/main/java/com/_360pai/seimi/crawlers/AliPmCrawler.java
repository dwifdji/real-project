package com._360pai.seimi.crawlers;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import com._360pai.seimi.model.AliPm;
import com._360pai.seimi.service.AliPmService;
import com._360pai.seimi.util.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zxiao
 * @Title: AaliPM
 * @ProjectName zeus_spider
 * @Description:
 * @date 2018/11/5 10:01
 */
@Crawler(name = "aliPm", delay = 5)
@Component
public class AliPmCrawler extends BaseSeimiCrawler {

    @Resource
    private AliPmService aliPmService;

    @Override
    public String[] startUrls() {

        String endDay = DateUtil.today();
        Date date = DateUtil.lastMouth(3);
        String startDay = DateUtil.format(date, DateUtil.NORM_DATE_PATTERN);

        return new String[]{"https://zc-paimai.taobao.com/zc_item_list.htm?spm=a213w.7398504.filter.46.32af4afcdbcGBp" +
                "&front_category=56956002" +
                "&sorder=-1" +
                "&auction_start_seg=0" +
                "&auction_start_from=" + startDay + "" +
                "&auction_start_to=" + endDay};
    }

    @Override
    public List<Request> startRequests() {
        return super.startRequests();
    }

    private static final Pattern COMPILE = Pattern.compile("\\{\"data\":\\[(.*?)]}");

    @Override
    public void start(Response response) {
        String content = response.getContent();
        Document parse = Jsoup.parse(content);
        String pageStr = parse.select("body > div.sf-wrap > div.pagination.J_Pagination > span.page-skip > em").text();
        Integer page = Integer.valueOf(pageStr);
        String url = response.getRequest().getUrl();
        for (int i = 2; i <= page; i++) {
            String pageUrl = url + "&page=" + i;
            logger.info("当前列表页url为：{}", pageUrl);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            push(Request.build(pageUrl, AliPmCrawler::getList));
        }
    }

    public void getList(Response response) {
        String content = response.getContent();
        String doc = content.replace("\n", "");
        Matcher matcher = COMPILE.matcher(doc);
        String text = null;
        if (matcher.find()) {
            System.out.println("matcher = " + matcher.group(0));
            text = matcher.group(0);
        }
        if (StringUtils.isNoneBlank(text)) {
            JSONObject jsonObject = JSONObject.parseObject(text);
            JSONArray data = jsonObject.getJSONArray("data");
            for (int i = 0; i < data.size(); i++) {
                JSONObject json = data.getJSONObject(i);
                String itemUrl = json.getString("itemUrl");
                String title = json.getString("title");
                String id = json.getString("id");
                String startDate = json.getString("start");
                String endDate = json.getString("end");
                String status = json.getString("status");
                itemUrl = "https:" + itemUrl;
                logger.info("商品url为：{}", itemUrl);
                AliPm aliPm = aliPmService.findByCode(id);
                if (aliPm == null) {
                    aliPm = new AliPm();
                }
                aliPm.setCode(id);
                aliPm.setTitle(title);
                aliPm.setItemUrl(itemUrl);
                aliPm.setStartDate(new Date(Long.valueOf(startDate)));
                aliPm.setEndDate(new Date(Long.valueOf(endDate)));
                aliPm.setStatus(status);
                aliPm.setRespDate(json.toJSONString());
                aliPmService.saveItem(aliPm);
                Map<String, Object> map = new HashMap<>(16);
                map.put("code", id);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                push(Request.build(itemUrl, AliPmCrawler::getJSON).setMeta(map));
            }
            logger.info("商品列表抓取结束数量为：{}", data.size());
        }
    }

    public void getJSON(Response response) {
        aliPmService.updateContent(response);
    }
}
