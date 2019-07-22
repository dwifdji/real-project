package com._360pai.gateway.service;

import com._360pai.core.facade.account.AccountFacade;
import com._360pai.gateway.facade.PayFacade;
import com._360pai.gateway.util.MessageUtil;
import com._360pai.gateway.util.TextMessage;
import com.alibaba.dubbo.config.annotation.Reference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * @author xdrodger
 * @Title: WechatService
 * @ProjectName dev2-zeus
 * @Description:
 * @date 2019-05-05 10:00
 */
@Slf4j
@Service
public class WechatService {

    @Reference(version = "1.0.0")
    private AccountFacade accountFacade;

    /**
     * 处理微信发来的请求
     *
     * @param request
     * @return
     */
    public String weixinPost(HttpServletRequest request) {
        String respMessage = "";
        try {

            // xml请求解析
            Map<String, String> requestMap = MessageUtil.xmlToMap(request);

            // 发送方帐号（open_id）
            String fromUserName = requestMap.get("FromUserName");
            // 公众帐号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");
            // 消息内容
            String content = requestMap.get("Content");

            log.info("FromUserName is:" + fromUserName + ", ToUserName is:" + toUserName + ", MsgType is:" + msgType);

            // 文本消息
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                //这里根据关键字执行相应的逻辑，只有你想不到的，没有做不到的
                if (content.equals("xxx")) {

                }

                //自动回复
                TextMessage text = new TextMessage();
                text.setContent("360PAI.COM是中国专业的债权拍卖处置交易服务平台。首创特殊资产债权行业处置及服务标准，根据债权处置过程中的需求自动生成12款标准化债权拍品，联合全国6000家拍卖行构建全网联拍体系，实现360°全网同步竞价拍卖，客服热线：400-015-0005。");
                text.setToUserName(fromUserName);
                text.setFromUserName(toUserName);
                text.setCreateTime(System.currentTimeMillis()+"");
                text.setMsgType(msgType);

                respMessage = MessageUtil.textMessageToXml(text);

            }
            // 事件推送
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                // 事件类型
                String eventType = requestMap.get("Event");
                // 订阅
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {

                    //TextMessage text = new TextMessage();
                    //text.setContent("欢迎关注，xxx");
                    //text.setToUserName(fromUserName);
                    //text.setFromUserName(toUserName);
                    //text.setCreateTime(new Date().getTime() + "");
                    //text.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
                    //
                    //respMessage = MessageUtil.textMessageToXml(text);
                    accountFacade.subscribeMp(fromUserName);
                }
                // 取消订阅
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {

                }
                //// 自定义菜单点击事件
                //else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                //    String eventKey = requestMap.get("EventKey");// 事件KEY值，与创建自定义菜单时指定的KEY值对应
                //    if (eventKey.equals("customer_telephone")) {
                //        TextMessage text = new TextMessage();
                //        text.setContent("0755-86671980");
                //        text.setToUserName(fromUserName);
                //        text.setFromUserName(toUserName);
                //        text.setCreateTime(new Date().getTime() + "");
                //        text.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
                //
                //        respMessage = MessageUtil.textMessageToXml(text);
                //    }
                //}
            }
        } catch (Exception e) {
            log.error("error......",e);
        }
        return respMessage;
    }


}
