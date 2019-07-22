package com._360pai.core.service.impl;


import com._360pai.arch.common.HttpUtils;
import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.arch.core.redis.RedisCachemanager;
import com._360pai.arch.core.sysconfig.properties.GatewayProperties;
import com._360pai.core.aspact.GatewayExceptionEmailService;
import com._360pai.core.common.constants.ExceptionEmailEnum;
import com._360pai.core.service.WxService;
import com._360pai.gateway.controller.req.wx.WXACodeUnLimitReq;
import com._360pai.gateway.resp.wx.MpUserInfoResp;
import com._360pai.gateway.resp.wx.MpUserListResp;
import com._360pai.gateway.resp.wx.TemplateResp;
import com._360pai.gateway.resp.wxpay.WXACodeUnLimitResp;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 描述：微信服务实现类
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/11/23 11:03
 */
@Service
public class WxServiceImpl extends GatewayExceptionEmailService implements WxService {

    private  final Logger logger = LoggerFactory.getLogger(WxServiceImpl.class);

    @Autowired
    private  GatewayProperties gatewayProperties;


    @Resource
    private RedisCachemanager redisCachemanager;



    @Override
    public WXACodeUnLimitResp getWXACodeUnLimit(WXACodeUnLimitReq req) {
        WXACodeUnLimitResp resp = new WXACodeUnLimitResp();
        InputStream in=null;

        InputStream stream1=null;

        InputStream stream2=null;

        ByteArrayOutputStream baos=null;

        try {
            //获取微信token
            String token  = getAccessToken(req);

            if(StringUtils.isBlank(token)){
                return WXACodeUnLimitResp.failure("101","获取token失败");
            }
            JSONObject params = new JSONObject();
            params.put("scene", req.getScene());  //参数
            params.put("page", req.getPage()); //位置
            //获取二维码流
            in = HttpUtils.sendPostReader(gatewayProperties.getAppletAcodeUnLimit()+"?access_token="+token, params.toJSONString());

            baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) > -1 ) {
                baos.write(buffer, 0, len);
            }
            baos.flush();

            stream1 = new ByteArrayInputStream(baos.toByteArray());

            String str = IOUtils.toString(stream1, "utf-8");

            //校验微信是不是返回错误了
            if(str.contains("errcode")){
                JSONObject errInfo = JSONObject.parseObject(str);
                String errCode = errInfo.getString("errcode");
                resp.setCode(errCode);
                resp.setDesc(errInfo.getString("errmsg"));
                if("41030".equals(errCode)){
                    resp.setDesc("所传page页面不存在，或者小程序没有发布");
                }
                return resp;
            }

            stream2 = new ByteArrayInputStream(baos.toByteArray());

            //将图片上传七牛
            String imgUrl = uploadQiNiu(stream2,req.getScene());

            if(StringUtils.isBlank(imgUrl)){
                return WXACodeUnLimitResp.failure("102","上传七牛图片异常！");
            }
            //返回参数
            resp.setCode(ApiCallResult.SUCCESS.getCode());
            resp.setDesc(ApiCallResult.SUCCESS.getDesc());
            resp.setImgUrl(imgUrl);

        }catch (Exception e){
            sendExceptionEmail(ExceptionEmailEnum.IMPORTANT_LEVEL.SERIOUS,ExceptionEmailEnum.MODULE_TYPE.APPLET,"小程序获取二维码异常", JSON.toJSONString(req),e );

            resp.setCode(ApiCallResult.EXCEPTION.getCode());
            resp.setDesc(ApiCallResult.EXCEPTION.getDesc());
            logger.error("小程序获取二维码异常，异常信息为：{}",e);
        }finally {

            try {
                if (in != null) {
                    in.close();
                }
                if (stream1 != null) {
                    stream1.close();
                }
                if (stream2 != null) {
                    stream2.close();
                }
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }

        return resp;
    }

    @Override
    public TemplateResp sendDebtCalculatorBroadcastTemplateMsg(String openId, JSONObject params) {
        TemplateResp resp = new TemplateResp();
        String token = getMpAccessToken();
        JSONObject content = new JSONObject();
        content.put("touser", openId);
        content.put("template_id", SystemConstant.MP_360PAI_DEBT_TEMPLATE_ID);
        content.put("url", "");
        JSONObject miniprogram = new JSONObject();
        //miniprogram.put("appid", gatewayProperties.getAppletAppId());
        //miniprogram.put("pagepath", "pages/index/index");
        miniprogram.put("appid", gatewayProperties.getCalculatorAppId());
        miniprogram.put("pagepath", "pages/broadcast/broadcast");
        content.put("miniprogram", miniprogram);
        JSONObject data = new JSONObject();


        JSONObject first = new JSONObject();
        first.put("value", "收益播报提醒");
        first.put("color", "#173177");
        data.put("first", first);

        JSONObject keyword1 = new JSONObject();
        keyword1.put("value", params.getString("projectName"));
        keyword1.put("color", "#173177");
        data.put("keyword1", keyword1);

        JSONObject keyword2 = new JSONObject();
        keyword2.put("value", "播报中");
        keyword2.put("color", "#173177");
        data.put("keyword2", keyword2);

        JSONObject keyword3 = new JSONObject();

        keyword3.put("value", String.format("到今天为止剩余投资收益率：%s，剩余投资收益估算：%s元", params.getString("annualizedReturn"), params.getString("income")));
        keyword3.put("color", "#173177");
        data.put("keyword3", keyword3);

        JSONObject keyword4 = new JSONObject();
        keyword4.put("value", params.getString("updateTime"));
        keyword4.put("color", "#173177");
        data.put("keyword4", keyword4);

        JSONObject remark = new JSONObject();
        remark.put("value", "感谢你的使用。");
        remark.put("color", "#173177");
        data.put("remark", remark);

        content.put("data", data);
        String url = String.format(SystemConstant.SEND_MP_TEMPLATE_URL, token);
        String response = HttpUtils.sendPost(url, content.toJSONString());
        logger.info("发送实时播报模版消息，{}，入参：{}，出参：{}", url, content.toJSONString(), response);
        if (StringUtils.isBlank(response)) {
            resp.setCode(ApiCallResult.FAILURE.getCode());
            resp.setDesc(ApiCallResult.FAILURE.getDesc());
            return resp;
        }
        JSONObject responseJson = JSON.parseObject(response);
        if (!responseJson.containsKey("errcode") || responseJson.getIntValue("errcode") != 0) {
            resp.setCode(ApiCallResult.FAILURE.getCode());
            resp.setDesc(ApiCallResult.FAILURE.getDesc());
            return resp;
        }
        //返回参数
        resp.setCode(ApiCallResult.SUCCESS.getCode());
        resp.setDesc(ApiCallResult.SUCCESS.getDesc());
        resp.setMsgid(responseJson.getString("msgid"));
        return resp;
    }

    @Override
    public TemplateResp sendPrincipalInterestCalculatorBroadcastTemplateMsg(String openId, JSONObject params) {
        TemplateResp resp = new TemplateResp();
        String token = getMpAccessToken();
        JSONObject content = new JSONObject();
        content.put("touser", openId);
        content.put("template_id", SystemConstant.MP_360PAI_PRINCIPAL_INTEREST_TEMPLATE_ID);
        content.put("url", "");
        JSONObject miniprogram = new JSONObject();
        //miniprogram.put("appid", gatewayProperties.getAppletAppId());
        //miniprogram.put("pagepath", "pages/index/index");
        miniprogram.put("appid", gatewayProperties.getCalculatorAppId());
        miniprogram.put("pagepath", "pages/broadcast/broadcast");
        content.put("miniprogram", miniprogram);
        JSONObject data = new JSONObject();


        JSONObject first = new JSONObject();
        first.put("value", "收益播报提醒");
        first.put("color", "#173177");
        data.put("first", first);

        JSONObject keyword1 = new JSONObject();
        keyword1.put("value", params.getString("projectName"));
        keyword1.put("color", "#173177");
        data.put("keyword1", keyword1);

        JSONObject keyword2 = new JSONObject();
        keyword2.put("value", "播报中");
        keyword2.put("color", "#173177");
        data.put("keyword2", keyword2);

        JSONObject keyword3 = new JSONObject();

        keyword3.put("value", String.format("今日利息：%s元，截止昨日已累计收益：%s元", params.getString("todayInterest"), params.getString("income")));
        keyword3.put("color", "#173177");
        data.put("keyword3", keyword3);

        JSONObject keyword4 = new JSONObject();
        keyword4.put("value", params.getString("updateTime"));
        keyword4.put("color", "#173177");
        data.put("keyword4", keyword4);

        JSONObject remark = new JSONObject();
        remark.put("value", "感谢你的使用。");
        remark.put("color", "#173177");
        data.put("remark", remark);

        content.put("data", data);
        String url = String.format(SystemConstant.SEND_MP_TEMPLATE_URL, token);
        String response = HttpUtils.sendPost(url, content.toJSONString());
        logger.info("发送实时播报模版消息，{}，入参：{}，出参：{}", url, content.toJSONString(), response);
        if (StringUtils.isBlank(response)) {
            resp.setCode(ApiCallResult.FAILURE.getCode());
            resp.setDesc(ApiCallResult.FAILURE.getDesc());
            return resp;
        }
        JSONObject responseJson = JSON.parseObject(response);
        if (!responseJson.containsKey("errcode") || responseJson.getIntValue("errcode") != 0) {
            resp.setCode(ApiCallResult.FAILURE.getCode());
            resp.setDesc(ApiCallResult.FAILURE.getDesc());
            return resp;
        }
        //返回参数
        resp.setCode(ApiCallResult.SUCCESS.getCode());
        resp.setDesc(ApiCallResult.SUCCESS.getDesc());
        resp.setMsgid(responseJson.getString("msgid"));
        return resp;
    }

    @Override
    public MpUserInfoResp getMpUserInfo(String openId) {
        MpUserInfoResp resp = new MpUserInfoResp();
        String token = getMpAccessToken();
        String url = String.format(SystemConstant.GET_MP_USER_INFO_URL, token, openId);
        String response = HttpUtils.sendGet(url);
        logger.info("获取公众号用户信息接口，{}，出参：{}", url, response);
        if (StringUtils.isBlank(response)) {
            resp.setCode(ApiCallResult.FAILURE.getCode());
            resp.setDesc(ApiCallResult.FAILURE.getDesc());
            return resp;
        }
        JSONObject responseJson = JSON.parseObject(response);
        if (responseJson.containsKey("errcode")) {
            resp.setCode(ApiCallResult.FAILURE.getCode());
            resp.setDesc(ApiCallResult.FAILURE.getDesc());
            return resp;
        }
        resp.setCode(ApiCallResult.SUCCESS.getCode());
        resp.setDesc(ApiCallResult.SUCCESS.getDesc());
        resp.setOpenId(responseJson.getString("openid"));
        resp.setUnionId(responseJson.getString("unionid"));
        resp.setNickName(responseJson.getString("nickname"));
        resp.setHeadImgUrl(responseJson.getString("headimgurl"));
        resp.setSubscribe(responseJson.getString("subscribe"));
        return resp;
    }

    @Override
    public MpUserListResp getMpUserList(String nextOpenId) {
        MpUserListResp resp = new MpUserListResp();
        String token = getMpAccessToken();
        String url = String.format(SystemConstant.GET_MP_USER_LIST_URL, token, nextOpenId);
        String response = HttpUtils.sendGet(url);
        logger.info("获取公众号用户列表接口，{}，出参：{}", url, response);
        if (StringUtils.isBlank(response)) {
            resp.setCode(ApiCallResult.FAILURE.getCode());
            resp.setDesc(ApiCallResult.FAILURE.getDesc());
            return resp;
        }
        JSONObject responseJson = JSON.parseObject(response);
        if (responseJson.containsKey("errcode")) {
            resp.setCode(ApiCallResult.FAILURE.getCode());
            resp.setDesc(ApiCallResult.FAILURE.getDesc());
            return resp;
        }
        resp.setCode(ApiCallResult.SUCCESS.getCode());
        resp.setDesc(ApiCallResult.SUCCESS.getDesc());
        resp.setNextOpenId(responseJson.getString("next_openid"));
        if (responseJson.containsKey("data")) {
            List<String> list = JSON.parseArray(responseJson.getJSONObject("data").getJSONArray("openid").toJSONString(), String.class);
            resp.setList(list);
        } else {
            resp.setList(Collections.EMPTY_LIST);
        }
        return resp;
    }

    private String uploadQiNiu(InputStream in,String scene) {

        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);

        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        try {
            byte[] uploadBytes = toByteArray(in);
            ByteArrayInputStream byteInputStream=new ByteArrayInputStream(uploadBytes);
            Auth auth = Auth.create(gatewayProperties.getAccessKey(), gatewayProperties.getSecretKey());
            String upToken = auth.uploadToken(gatewayProperties.getBucket());

            Response response = uploadManager.put(byteInputStream,key,upToken,null, null);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);

            return  "https://"+gatewayProperties.getDomain()+"/"+putRet.key;


        } catch (Exception ex) {

            sendExceptionEmail(ExceptionEmailEnum.IMPORTANT_LEVEL.SERIOUS,ExceptionEmailEnum.MODULE_TYPE.APPLET,"小程序二维码上传七牛异常",null,ex );

            logger.error("上传七牛图片异常异常信息为：{}",ex);
        }

        return null;

    }


    private  byte[] toByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream out=new ByteArrayOutputStream();
        byte[] buffer=new byte[1024*4];
        int n=0;
        while ( (n=in.read(buffer)) !=-1) {
            out.write(buffer,0,n);
        }
        return out.toByteArray();
    }


    /**
     *
     *获取微信token
     */
    private String getAccessToken(WXACodeUnLimitReq req) {
        //获取缓存的token值
        String redisToken = (String) redisCachemanager.get(SystemConstant.WX_CHANNEL_KEY.equals(req.getChannel())?SystemConstant.WX_PAY_CALCULATOR_TOKEN_KEY:SystemConstant.WX_PAY_TOKEN_KEY);
        if(StringUtils.isNotEmpty(redisToken)){
            return redisToken;
        }

        String appId = SystemConstant.WX_CHANNEL_KEY.equals(req.getChannel())?gatewayProperties.getCalculatorAppId():gatewayProperties.getAppletAppId();

        String appSecret =SystemConstant.WX_CHANNEL_KEY.equals(req.getChannel())?gatewayProperties.getCalculatorAppSecret(): gatewayProperties.getAppletAppSecret();


        StringBuffer param = new StringBuffer();
        param.append("grant_type=");
        param.append("client_credential");
        param.append("&appid=");
        param.append(appId);
        param.append("&secret=");
        param.append(appSecret);

        String wxResp = HttpUtils.sendGet(gatewayProperties.getAppletTokenUrl(),param.toString());

        JSONObject jsonObject = JSONObject.parseObject(wxResp);

        String token = jsonObject.getString("access_token");

        //缓存token
        redisCachemanager.set(SystemConstant.WX_CHANNEL_KEY.equals(req.getChannel())?SystemConstant.WX_PAY_CALCULATOR_TOKEN_KEY:SystemConstant.WX_PAY_TOKEN_KEY,token,SystemConstant.WX_PAY_TOKEN_TIME_OUT);

        return token;
    }

    /**
     *
     *获取公众号微信token
     */
    private String getMpAccessToken() {
        //获取缓存的token值
        String redisToken = (String) redisCachemanager.get(SystemConstant.MP_360PAI_TOKEN_KEY);
        if(StringUtils.isNotEmpty(redisToken)){
            return redisToken;
        }

        String appId = gatewayProperties.getMp360PaiAppId();

        String appSecret = gatewayProperties.getMp360PaiAppSercret();


        StringBuffer param = new StringBuffer();
        param.append("grant_type=");
        param.append("client_credential");
        param.append("&appid=");
        param.append(appId);
        param.append("&secret=");
        param.append(appSecret);

        String wxResp = HttpUtils.sendGet(gatewayProperties.getAppletTokenUrl(),param.toString());

        JSONObject jsonObject = JSONObject.parseObject(wxResp);

        String token = jsonObject.getString("access_token");

        //缓存token
        redisCachemanager.set(SystemConstant.MP_360PAI_TOKEN_KEY, token, SystemConstant.WX_PAY_TOKEN_TIME_OUT);

        return token;
    }
}
