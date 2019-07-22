package com.winback.gateway.common.connect;



import com.alibaba.fastjson.JSONObject;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.notify.Notify;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.dto.GtReq;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.gexin.rp.sdk.template.style.AbstractNotifyStyle;
import com.gexin.rp.sdk.template.style.Style0;
import com.winback.arch.common.enums.DeviceType;
import com.winback.arch.core.sysconfig.properties.GatewayProperties;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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
    public static IPushResult pushMessageToSingle(GatewayProperties gatewayProperties, String clientId,String title,String text,String channel,String logoUrl,String url,String getMajorKey,Integer msgType)  {

        // https连接
        IGtPush push = new IGtPush(gatewayProperties.getPushAppKey(), gatewayProperties.getPushMasterSecret(), true);

        //透传的消息模板
        TransmissionTemplate   transmissionTemplate;

        //安卓模板
        if(DeviceType.ANDROID.getKey().equals(channel)){
            //安卓模板信息
            transmissionTemplate = getAndroidTransmissionTemplate(gatewayProperties,title,text,getMajorKey,msgType);
        }else{
            transmissionTemplate = getTransmissionTemplate(gatewayProperties,title,text,getMajorKey,msgType);
        }

        SingleMessage message = new SingleMessage();
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 3600 * 1000);
        message.setData(transmissionTemplate);
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

    private static TransmissionTemplate getAndroidTransmissionTemplate(GatewayProperties gatewayProperties, String title, String text, String getMajorKey, Integer msgType) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",getMajorKey);
        jsonObject.put("msgType",msgType);
        jsonObject.put("title",StringUtils.isEmpty(title)?"系统消息":title);
        jsonObject.put("msgBody",text);

        String transmissionContent = jsonObject.toJSONString();

        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(gatewayProperties.getPushAppId());
        template.setAppkey(gatewayProperties.getPushAppKey());
        template.setTransmissionContent(transmissionContent);
        template.setTransmissionType(2);

        Notify notify = new Notify();
        notify.setTitle(StringUtils.isEmpty(title)?"系统消息":title);
        notify.setContent(text);

        notify.setIntent(getIntentInfo(getMajorKey,msgType));
        notify.setType(GtReq.NotifyInfo.Type._intent);
        template.set3rdNotifyInfo(notify);//设置第三方通知

        return template;
    }

    private static String getIntentInfo(String getMajorKey, Integer msgType) {

        String intent="";
        if(StringUtils.isEmpty(getMajorKey)){
            getMajorKey = "";
        }

        if(msgType==1){
            intent = "intent:#Intent;action=com.winyhl.winyinghuilai.action.CaseProgressDetailsActivity;component=com.winyhl.winyinghuilai/.ui.caseprogress.CaseProgressDetailsActivity;S.id="+getMajorKey+";S.msgType=msgType;end";

        }else if(msgType==4){
            if(StringUtils.isNotEmpty(getMajorKey)){
                intent ="intent:#Intent;action=com.winyhl.winyinghuilai.action.CaseDetailsActivity;component=com.winyhl.winyinghuilai/.CaseDetailsActivity;S.id="+getMajorKey+";S.msgType=msgType;end";
            }else {
                intent = "intent:#Intent;action=com.winyhl.winyinghuilai.action.MainActivity;component=com.winyhl.winyinghuilai/.MainActivity;S.id="+getMajorKey+";S.msgType=msgType;end";
            }
        }else if(msgType==2){

            if(StringUtils.isNotEmpty(getMajorKey)){
                intent ="intent:#Intent;action=com.winyhl.winyinghuilai.action.ActivityMessageDetails;component=com.winyhl.winyinghuilai/.ui.ActivityMessageDetails;S.id="+getMajorKey+";S.msgType=msgType;end";

            }else {
                intent ="intent:#Intent;action=com.winyhl.winyinghuilai.action.MessageContentListActivity;component=com.winyhl.winyinghuilai/.ui.my.MessageContentListActivity;S.id="+getMajorKey+";S.msgType=msgType;end";
            }

        } else if(msgType==3){
            intent = "intent:#Intent;action=com.winyhl.winyinghuilai.action.MainActivity;component=com.winyhl.winyinghuilai/.MainActivity;S.id="+getMajorKey+";S.msgType=msgType;end";
        }

        return intent;
    }

    private static TransmissionTemplate getTransmissionTemplate(GatewayProperties gatewayProperties, String title, String text,String getMajorKey,Integer msgType) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",getMajorKey);
        jsonObject.put("msgType",msgType);

        jsonObject.put("title",StringUtils.isEmpty(title)?"系统消息":title);
        jsonObject.put("msgBody",text);

        String transmissionContent = jsonObject.toJSONString();

        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(gatewayProperties.getPushAppId());
        template.setAppkey(gatewayProperties.getPushAppKey());
        template.setTransmissionContent(transmissionContent);
        template.setTransmissionType(2);
        APNPayload payload = new APNPayload();
        payload.setContentAvailable(1);
        payload.setSound("default");
        //字典模式使用下者
        payload.setAlertMsg(getDictionaryAlertMsg(title,text,transmissionContent));
        payload.setAutoBadge("+1");


        payload.addCustomMsg("msgType",msgType);
        payload.addCustomMsg("msgBody",transmissionContent);

        template.setAPNInfo(payload);


        return template;
    }


    private static APNPayload.DictionaryAlertMsg getDictionaryAlertMsg(String title, String text,String transmissionContent){
        APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
        alertMsg.setBody(text);
        alertMsg.setActionLocKey("ActionLockey");
        alertMsg.setLocKey(text);
        alertMsg.setLaunchImage("launch-image");
        // IOS8.2以上版本支持
        alertMsg.setTitle(title);
        alertMsg.setTitleLocKey(title);
        alertMsg.addTitleLocArg("TitleLocArg");

        return alertMsg;
    }





    public static LinkTemplate linkTemplateInfo(GatewayProperties gatewayProperties,String title,String text,String logo,String logoUrl,String url,String getMajorKey,Integer msgType) {
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

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",getMajorKey);
        jsonObject.put("msgType",msgType);

        String transmissionContent = jsonObject.toJSONString();
        APNPayload payload = new APNPayload();
        payload.setContentAvailable(1);
        payload.setSound("default");
        //字典模式使用下者
        payload.setAlertMsg(getDictionaryAlertMsg(title,text,null));

        payload.addCustomMsg("msgType",msgType);
        payload.addCustomMsg("msgBody",transmissionContent);

        template.setAPNInfo(payload);



        return template;
    }






    /**
     *
     * 全体普通推送
     */
    public static IPushResult pushMessageToAll(GatewayProperties gatewayProperties, String clientId,String title,String text,String getMajorKey,Integer msgType)  {

        // https连接
        IGtPush push = new IGtPush(gatewayProperties.getPushAppKey(), gatewayProperties.getPushMasterSecret(), true);

        //透传的消息模板
        TransmissionTemplate   transmissionTemplate = getTransmissionTemplate(gatewayProperties,title,text,getMajorKey,msgType);
        AppMessage message = new  AppMessage ();
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 3600 * 1000);
        message.setData(transmissionTemplate);
        message.setPushNetWorkType(0); // 可选，判断是否客户端是否wifi环境下推送，1为在WI
        List<String> appIdList = new ArrayList<>();
        appIdList.add(gatewayProperties.getPushAppId());
        message.setAppIdList(appIdList);
        IPushResult ret;
        try {
            ret = push.pushMessageToApp(message);
        } catch (RequestException e) {
            ret = push.pushMessageToApp(message);

            throw new RuntimeException(e);
        }

        return ret;
    }





    /**
     *
     * 全体推送
     */
    public static IPushResult pushMessageToAllLinkTemplate(GatewayProperties gatewayProperties, String clientId,String title,String text,String getMajorKey,Integer msgType)  {

        // https连接
        IGtPush push = new IGtPush(gatewayProperties.getPushAppKey(), gatewayProperties.getPushMasterSecret(), true);

        //消息提示模板
        NotificationTemplate template =getNotificationTemplate(gatewayProperties,title,text,null,null,null,getMajorKey,msgType);

        AppMessage message = new  AppMessage ();
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 3600 * 1000);
        message.setData(template);
        message.setPushNetWorkType(0); // 可选，判断是否客户端是否wifi环境下推送，1为在WI
        List<String> appIdList = new ArrayList<>();
        appIdList.add(gatewayProperties.getPushAppId());
        message.setAppIdList(appIdList);
        IPushResult ret;
        try {
            ret = push.pushMessageToApp(message);
        } catch (RequestException e) {
            ret = push.pushMessageToApp(message);

            throw new RuntimeException(e);
        }

        return ret;
    }


    /**
     *
     * 单体推送信息
     */
    public static IPushResult pushMessageToSingleLinkTemplate(GatewayProperties gatewayProperties, String clientId,String title,String text,String logo,String logoUrl,String url,String getMajorKey,Integer msgType)  {

        // https连接
        IGtPush push = new IGtPush(gatewayProperties.getPushAppKey(), gatewayProperties.getPushMasterSecret(), true);
        // host为域名，根据域名区分是http协议/https协议

        NotificationTemplate template =getNotificationTemplate(gatewayProperties,title,text,logo,logoUrl,url,getMajorKey,msgType);

        //LinkTemplate template = linkTemplateInfo(gatewayProperties,title,text,logo,logoUrl,url,getMajorKey,msgType);

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

    private static NotificationTemplate getNotificationTemplate(GatewayProperties gatewayProperties, String title, String text, String logo, String logoUrl, String url, String getMajorKey, Integer msgType) {

        NotificationTemplate template = new NotificationTemplate();

        // 设置APPID与APPKEY
        template.setAppId(gatewayProperties.getPushAppId());
        template.setAppkey(gatewayProperties.getPushAppKey());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",getMajorKey);
        jsonObject.put("msgType",msgType);
        jsonObject.put("notifyFlag","1");


        String transmissionContent = jsonObject.toJSONString();


        Style0 style = new Style0();

        // 透传消息设置，1为强制启动应⽤，客户端接收到消息后就会⽴即启动应⽤；2为等待应⽤启动
        template.setTransmissionType(2);
        template.setTransmissionContent(transmissionContent);
        // 设置通知栏标题与内容
        style.setTitle(StringUtils.isEmpty(title)?"消息提醒":title);
        style.setText(text);

        // 设置通知是否响铃，震动，或者可清除
        style.setRing(true);
        style.setVibrate(true);
        style.setClearable(true);
        template.setStyle(style);




        APNPayload payload = new APNPayload();
        payload.setContentAvailable(1);
        payload.setSound("default");
        //字典模式使用下者
        payload.setAlertMsg(getDictionaryAlertMsg(title,text,null));
        payload.setAutoBadge("+1");
        payload.addCustomMsg("msgType",msgType);
        payload.addCustomMsg("msgBody",transmissionContent);

        template.setAPNInfo(payload);



        return template;
    }

}
