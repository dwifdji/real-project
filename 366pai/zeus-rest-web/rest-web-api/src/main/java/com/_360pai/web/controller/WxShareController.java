package com._360pai.web.controller;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.utils.WxCommonUtil;
import com._360pai.arch.common.utils.WxSign;
import com._360pai.arch.core.redis.Cachemanager;
import com._360pai.core.model.assistant.AccessToken;
import com._360pai.core.model.assistant.JsApiTicket;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author zxiao
 * @Title: WxShareController
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/11/1 15:13
 */
@Slf4j
@RestController
public class WxShareController {

    @Value("${appid}")
    private String appid;
    @Value("${appsecret}")
    private String appsecret;
    @Resource
    private Cachemanager cachemanager;

    @RequestMapping("/open/share")
    public ResponseModel wxShare(@Param("url") String url) {
        String accessToken;
        //获取accessToken
        accessToken = (String) cachemanager.get(SystemConstant.WX_ACCESS_TOKEN + appid);
        log.info("redis取出的accessToken值为：{}", JSON.toJSONString(accessToken));
        if (null == accessToken) {
            AccessToken token = WxCommonUtil.getAccessToken(appid, appsecret);
            log.info("通过微信取出的accessToken值为：{}", JSON.toJSONString(accessToken));
            cachemanager.set(SystemConstant.WX_ACCESS_TOKEN + appid, token.getAccessToken(), SystemConstant.WX_TIME_OUT);
            accessToken = token.getAccessToken();
        }

        String jsApiTicket = null;
        if (StringUtils.isNotEmpty(accessToken)) {
            //获取jsapi_ticket
            jsApiTicket = (String) cachemanager.get(SystemConstant.WX_JSAPI_TICKET + appid);
            log.info("redis取出的jsapi_ticket值为：{}", JSON.toJSONString(jsApiTicket));
            if (null == jsApiTicket) {
                JsApiTicket ticket = WxCommonUtil.getJsApiTicket(accessToken);
                log.info("通过微信取出的accessToken值为：{}，JS_TICKET值为：{}", accessToken, JSON.toJSONString(jsApiTicket));
                cachemanager.set(SystemConstant.WX_JSAPI_TICKET + appid, ticket.getTicket(), SystemConstant.WX_TIME_OUT);
                jsApiTicket = ticket.getTicket();
            }
        }

        if (null != jsApiTicket) {
            log.info("取出的jsapi_ticket值为：{}", JSON.toJSONString(jsApiTicket));
            Map<String, String> sign = WxSign.sign(jsApiTicket, appid, url);
            return ResponseModel.succ(sign);
        } else {
            return ResponseModel.fail();
        }
    }

}
