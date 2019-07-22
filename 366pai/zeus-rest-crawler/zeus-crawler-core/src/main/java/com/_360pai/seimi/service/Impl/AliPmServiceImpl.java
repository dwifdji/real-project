package com._360pai.seimi.service.Impl;

import cn.wanghaomiao.seimi.struct.Response;
import com._360pai.seimi.model.AliPm;
import com._360pai.seimi.dao.AliPmDao;
import com._360pai.seimi.service.AliPmService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.seimicrawler.xpath.JXDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author zxiao
 * @Title: AliPmServiceImpl
 * @ProjectName 345V1
 * @Description:
 * @date 2018/11/8 13:48
 */
@Service
public class AliPmServiceImpl implements AliPmService {

    Logger logger = LoggerFactory.getLogger(AliPmServiceImpl.class);
    //
    //{
    //    logger = LoggerFactory.getLogger(AliPmServiceImpl.class);
    //}

    @Resource
    private AliPmDao aliPmDao;

    @Override
    public AliPm saveItem(AliPm response) {
        return aliPmDao.save(response);
    }

    @Override
    public AliPm findByCode(String code) {
        return aliPmDao.findByCode(code);
    }

    @Override
    public AliPm updateContent(Response response) {
        String url = response.getRequest().getUrl();
        Map<String, Object> meta = response.getMeta();
        String code = (String) meta.get("code");
        AliPm render = aliPmDao.findByCode(code);
        try {
            JXDocument document = response.document();
            Document content = Jsoup.parse(response.getContent());
            //资产区域
            Elements address = content.getElementsByClass("item-address");
            if (address.isEmpty()) {
                Elements select = content.select("#J_DetailTabMain > div:nth-child(5) > div.detail-common-text.item-address");
                if (!select.isEmpty()) {
                    render.setArea(select.get(0).text());
                }
            } else {
                render.setArea(address.get(0).text());
            }
            //处置单位
            String disposition = (String) document.selOne("//*[@id=\"page\"]/div[4]/div/div/div[3]/div[2]/p/span[2]/a/text()");
            render.setDisposition(disposition);
            //联系人
            String person = (String) document.selOne("//*[@id=\"page\"]/div[4]/div/div/div[3]/div[2]/div/div/em/text()");
            render.setPerson(person);
            //联系电话
            String personPhone = (String) document.selOne("//*[@id=\"page\"]/div[4]/div/div/div[3]/div[2]/div/div/div/allText()");
            render.setPersonPhone(personPhone);
            //起拍价
            String staringPrice = (String) document.selOne("//*[@id=\"J_HoverShow\"]/tr[1]/td[1]/span[2]/allText()");
            render.setStaringPrice(staringPrice);
            //评估价
            String payPrice = null;
            String tr = (String) document.selOne("//*[@id=\"J_HoverShow\"]/tr[2]/td[1]/allText()");
            if (tr.contains("评 估 价 ")) {
                payPrice = (String) document.selOne("//*[@id=\"J_HoverShow\"]/tr[3]/td[1]/allText()");
            }
            if (StringUtils.isNotEmpty(payPrice)) {
                String[] split = payPrice.split(":");
                if (split.length > 1) {
                    render.setPayType(split[0]);
                    render.setPayPrice(split[1]);
                }
            }

            //成交价
            String amount = (String) document.selOne("//*[@id=\"sf-price\"]/div/p[1]/span/em/text()");
            render.setAmount(amount);
            //报名人数
            String appler = (String) document.selOne("//*[@id=\"page\"]/div[4]/div/div/div[2]/div[3]/span[1]/em/text()");
            render.setAppler(appler);
            //提醒人数
            String reminder = (String) document.selOne("//*[@id=\"page\"]/div[4]/div/div/div[2]/div[3]/span[3]/em/text()");
            render.setReminder(reminder);
            //浏览人数
            String looker = (String) document.selOne("//*[@id=\"J_Looker\"]/text()");
            render.setLooker(looker);

            if (Integer.valueOf(render.getAppler()) == 0) {
                render.setCurrentPrice(render.getAmount());
                render.setAmount(null);
            }
            aliPmDao.save(render);
            logger.info("当前url为：{}，当前数据为：{}", url, JSON.toJSONString(render));
            return aliPmDao.save(render);
        } catch (Exception e) {
            logger.info("出错url为：{}", url);
            e.printStackTrace();
        }
        return render;
    }
}
