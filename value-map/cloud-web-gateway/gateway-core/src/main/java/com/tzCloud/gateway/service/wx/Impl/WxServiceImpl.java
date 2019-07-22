package com.tzCloud.gateway.service.wx.Impl;



import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.tzCloud.arch.common.HttpUtils;
import com.tzCloud.arch.common.enums.ApiCallResult;
import com.tzCloud.arch.core.redis.RedisCachemanager;
import com.tzCloud.arch.core.sysconfig.properties.GatewayProperties;
import com.tzCloud.arch.common.constant.SystemConstant;
import com.tzCloud.gateway.service.wx.WxService;
import com.tzCloud.gateway.controller.req.wx.WXACodeUnLimitReq;
import com.tzCloud.gateway.resp.wx.OpenIdResp;
import com.tzCloud.gateway.resp.wxpay.WXACodeUnLimitResp;
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

/**
 * 描述：微信服务实现类
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/11/23 11:03
 */
@Service
public class WxServiceImpl implements WxService {

    private  final Logger logger = LoggerFactory.getLogger(WxServiceImpl.class);

    @Autowired
    private GatewayProperties gatewayProperties;


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
            String token  = getAccessToken();

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
    private String getAccessToken() {
        //获取缓存的token值
        String redisToken = (String) redisCachemanager.get(SystemConstant.WX_PAY_TOKEN_KEY);
        if(StringUtils.isNotEmpty(redisToken)){
            return redisToken;
        }

        StringBuffer param = new StringBuffer();
        param.append("grant_type=");
        param.append("client_credential");
        param.append("&appid=");
        param.append(gatewayProperties.getAppletAppId());
        param.append("&secret=");
        param.append(gatewayProperties.getAppletAppSecret());

        String wxResp = HttpUtils.sendGet(gatewayProperties.getAppletTokenUrl(),param.toString());

        JSONObject jsonObject = JSONObject.parseObject(wxResp);

        String token = jsonObject.getString("access_token");

        //缓存token
        redisCachemanager.set(SystemConstant.WX_PAY_TOKEN_KEY,token,SystemConstant.WX_PAY_TOKEN_TIME_OUT);

        return token;
    }



    /**
     *获取微信openid
     */
    @Override
    public OpenIdResp getWxOpenId(String jsCode) {

        OpenIdResp resp = new OpenIdResp();
        StringBuffer param = new StringBuffer();

        try {
            param.append("appid=");
            param.append(gatewayProperties.getAppletAppId());
            param.append("&secret=");
            param.append(gatewayProperties.getAppletAppSecret());
            param.append("&js_code=");
            param.append(jsCode);
            param.append("&grant_type=");
            param.append("authorization_code");
            String wxResp = HttpUtils.sendGet(gatewayProperties.getAppletOpenIdUrl(),param.toString());
            logger.info("get openid={}", wxResp);
            resp = getResp(wxResp);

        }catch (Exception e){
            resp.setCode(ApiCallResult.EXCEPTION.getCode());
            resp.setCode(ApiCallResult.EXCEPTION.getDesc());


            logger.error("查询微信openid异常，异常信息为：{}",e);
        }
        return resp;
    }


    private OpenIdResp getResp(String wxResp) {

        OpenIdResp resp = new OpenIdResp();

        JSONObject jsonObject = JSONObject.parseObject(wxResp);

        String openid = jsonObject.getString("openid");

        if(StringUtils.isNotEmpty(openid)){
            resp.setCode(ApiCallResult.SUCCESS.getCode());
            resp.setDesc(ApiCallResult.SUCCESS.getDesc());
            resp.setOpenId(jsonObject.getString("openid"));
            resp.setSessionKey(jsonObject.getString("session_key"));
            resp.setUnionId(jsonObject.getString("unionid"));
        }else{
            resp.setCode(jsonObject.getString("errcode"));
            resp.setDesc(jsonObject.getString("errmsg"));
        }
        return resp;
    }


}
