package com.tzCloud.gateway.common.alisms;


import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.tzCloud.arch.common.ResponseModel;
import com.tzCloud.arch.common.enums.ApiCallResult;
import com.tzCloud.arch.core.sysconfig.properties.GatewayProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 描述：阿里短信发送工具类
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/7 15:04
 */

public class AliSmsUtils {

    private  static final Logger logger = LoggerFactory.getLogger(AliSmsUtils.class);


    //产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain  = "dysmsapi.aliyuncs.com";


    static final String SUCCESS_MSG = "短信发送成功";

    static final String FAILURE_CODE = "111";

    static final String SMS_SUCCESS_CODE = "OK";



    public static ResponseModel sendSms(GatewayProperties gatewayProperties, String phoneNumbers, String templateCode, String templateParam)  {

        ResponseModel resp = new ResponseModel();
         //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", gatewayProperties.getAliDefaultConnectTimeout());
        System.setProperty("sun.net.client.defaultReadTimeout", gatewayProperties.getAliDefaultReadTimeout());

        try{
            //初始化acsClient,暂不支持region化
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", gatewayProperties.getAliAccessKeyId(), gatewayProperties.getAliAccessKeySecret());
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);

            //组装请求对象-具体描述见控制台-文档部分内容
            SendSmsRequest request = new SendSmsRequest();
            //必填:待发送手机号
            request.setPhoneNumbers(phoneNumbers);
            //必填:短信签名-可在短信控制台中找到
            request.setSignName(gatewayProperties.getAliSignName());
            //必填:短信模板-可在短信控制台中找到
            request.setTemplateCode(templateCode);
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            request.setTemplateParam(templateParam);

            //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
            //request.setSmsUpExtendCode("90997");

            //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
            request.setOutId("cn-hangzhou");

            //hint 此处可能会抛出异常，注意catch
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

            if(SMS_SUCCESS_CODE.equals(sendSmsResponse.getCode())){
                resp.setCode(ApiCallResult.SUCCESS.getCode());
                resp.setDesc(SUCCESS_MSG);
            }else{
                resp.setCode(FAILURE_CODE);
                resp.setDesc(sendSmsResponse.getMessage());
            }

        }catch (Exception e){

            logger.error("发送短信异常，异常信息为:{}",e);
            resp.setCode(ApiCallResult.EXCEPTION.getCode());
            resp.setDesc(ApiCallResult.EXCEPTION.getDesc());
            throw new RuntimeException(e);

        }

        return resp;
    }


}
