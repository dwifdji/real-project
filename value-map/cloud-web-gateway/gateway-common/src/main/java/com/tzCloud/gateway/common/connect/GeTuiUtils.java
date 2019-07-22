package com.tzCloud.gateway.common.connect;



import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.tzCloud.arch.core.sysconfig.properties.GatewayProperties;


/**
 * 描述：个推工具
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2019/1/17 15:04
 */
public class GeTuiUtils {


    /**
     *
     * 单体推送信息
     */
    public static IPushResult pushMessageToSingle(GatewayProperties gatewayProperties, String clientId,String title,String text,String logo,String logoUrl,String url)  {

        // https连接
        IGtPush push = new IGtPush(gatewayProperties.getPushAppKey(), gatewayProperties.getPushMasterSecret(), true);
        // host为域名，根据域名区分是http协议/https协议
        LinkTemplate template = linkTemplateInfo(gatewayProperties,title,text,logo,logoUrl,url);
        SingleMessage message = new SingleMessage();
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 3600 * 1000);
        message.setData(template);
        message.setPushNetWorkType(0); // 可选，判断是否客户端是否wifi环境下推送，1为在WI
        Target target = new Target();
        target.setAppId(gatewayProperties.getPushAppId());
        target.setClientId(clientId);

        IPushResult ret;
        try {
            ret = push.pushMessageToSingle(message, target);
        } catch (RequestException e) {
             ret = push.pushMessageToSingle(message, target, e.getRequestId());

            throw new RuntimeException(e);
        }

        return ret;
    }



    public static LinkTemplate linkTemplateInfo(GatewayProperties gatewayProperties,String title,String text,String logo,String logoUrl,String url) {
        LinkTemplate template = new LinkTemplate();
        // 设置APPID与APPKEY
        template.setAppId(gatewayProperties.getPushAppId());
        template.setAppkey(gatewayProperties.getPushAppKey());
        // 设置通知栏标题与内容
        template.setTitle(title);
        template.setText(text);
        // 配置通知栏图标
        template.setLogo(logo);
        // 配置通知栏⽹络图标，填写图标URL地址
        template.setLogoUrl(logoUrl);
        // 设置通知是否响铃，震动，或者可清除
        template.setIsRing(true);
        template.setIsVibrate(true);
        template.setIsClearable(true);
        // 设置打开的⽹址地址
        template.setUrl(url);
        return template;
    }





}
