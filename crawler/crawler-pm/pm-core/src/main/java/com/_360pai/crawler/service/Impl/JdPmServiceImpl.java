package com._360pai.crawler.service.Impl;

import cn.wanghaomiao.seimi.struct.Response;
import com._360pai.crawler.dao.jdPm.JdPmDao;
import com._360pai.crawler.model.jdPm.JdPm;
import com._360pai.crawler.service.JdPmService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author zxiao
 * @Title: JdPmServiceImpl
 * @ProjectName 345V1
 * @Description:
 * @date 2018/11/9 15:12
 */
@Service
public class JdPmServiceImpl implements JdPmService {

    private static final Logger logger = LoggerFactory.getLogger(JdPmService.class);

    @Resource
    private JdPmDao jdPmDao;

    @Override
    public JdPm saveItem(JdPm content) {
        return jdPmDao.save(content);
    }

    @Override
    public JdPm findByCode(String code) {
        return jdPmDao.findByCode(code);
    }

    @Override
    public JdPm updateContent(Response response) {
        Map<String, Object> meta = response.getMeta();
        String code = (String) meta.get("code");
        JdPm jdPm = jdPmDao.findByCode(code);
        if (jdPm == null) {
            return null;
        }
        String content = response.getContent();
        JSONObject jsonObject = JSONObject.parseObject(content);
        logger.info("当前查询的商品code为：{}，基础信息为：{}", code, jsonObject);
        String consultTel = jsonObject.getString("consultTel");
        JSONObject productAddress = jsonObject.getJSONObject("productAddress");
        String province = productAddress.getString("province");
        String city = productAddress.getString("city");
        String county = productAddress.getString("county");
        String address = productAddress.getString("address");
        String addr = province + " " + city + " " + county + " " + address;

        jdPm.setPersonPhone(consultTel);
        jdPm.setAddress(addr);

        jdPm.setProductBasicInfo(jsonObject.toJSONString());
        return jdPmDao.save(jdPm);
    }

    @Override
    public void updateNum(Response response) {
        Map<String, Object> meta = response.getMeta();
        String code = (String) meta.get("code");
        JdPm jdPm = jdPmDao.findByCode(code);
        if (jdPm == null) {
            return;
        }
        String content = response.getContent();
        JSONObject jsonObject = JSONObject.parseObject(content);
        logger.info("当前查询的商品code为：{}，报名和浏览数据为：{}", code, jsonObject);
        Integer accessEnsureNum = jsonObject.getInteger("accessEnsureNum");
        Integer accessNum = jsonObject.getInteger("accessNum");

        jdPm.setAccessEnsureNum(accessEnsureNum.toString());
        jdPm.setAccessNum(accessNum.toString());
        jdPm.setQueryData(jsonObject.toJSONString());
        jdPmDao.save(jdPm);
    }

    @Override
    public void updateRemind(Response response) {
        Map<String, Object> meta = response.getMeta();
        String code = (String) meta.get("code");
        JdPm jdPm = jdPmDao.findByCode(code);
        if (jdPm == null) {
            return;
        }
        String content = response.getContent();
        JSONObject jsonObject = JSONObject.parseObject(content);
        logger.info("当前查询的商品code为：{}，报名和浏览数据为：{}", code, jsonObject);
        Integer remindCount = jsonObject.getInteger("remindCount");

        jdPm.setRemindCount(remindCount.toString());
        jdPmDao.save(jdPm);
    }
}
