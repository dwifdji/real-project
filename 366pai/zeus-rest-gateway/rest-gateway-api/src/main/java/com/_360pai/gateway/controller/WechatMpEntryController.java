package com._360pai.gateway.controller;

import com._360pai.gateway.service.WechatService;
import com._360pai.gateway.util.SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author xdrodger
 * @Title: WechatMpEntryController
 * @ProjectName dev2-zeus
 * @Description:
 * @date 2019-05-05 09:48
 */
@Slf4j
@Controller
@RequestMapping("/wechat")
public class WechatMpEntryController {

    @Autowired
    private WechatService wechatService;

    private static String token = "360pai";

    /**
     * 微信接入
     */
    @RequestMapping(value = "/connect", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public void connectWeixin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        request.setCharacterEncoding("UTF-8");  //微信服务器POST消息时用的是UTF-8编码，在接收时也要用同样的编码，否则中文会乱码；
        response.setCharacterEncoding("UTF-8"); //在响应消息（回复消息给用户）时，也将编码方式设置为UTF-8，原理同上；
        boolean isGet = request.getMethod().toLowerCase().equals("get");
        PrintWriter out = response.getWriter();
        try {
            if (isGet) {
                String signature = request.getParameter("signature");// 微信加密签名
                String timestamp = request.getParameter("timestamp");// 时间戳
                String nonce = request.getParameter("nonce");// 随机数
                String echostr = request.getParameter("echostr");//随机字符串
                // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
                if (SignUtil.checkSignature(token, signature, timestamp, nonce)) {
                    log.info("Connect the weixin server is successful.");
                    response.getWriter().write(echostr);
                } else {
                    log.error("Failed to verify the signature!");
                }
            } else {
                String respMessage = "";
                out.write("");
                try {
                    respMessage = wechatService.weixinPost(request);
                    out.write(respMessage);
                    log.info("The request completed successfully");
                    log.info("to weixin server " + respMessage);
                } catch (Exception e) {
                    log.error("Failed to convert the message from weixin!");
                }
            }
        } catch (Exception e) {
            log.error("Connect the weixin server is error.");
        } finally {
            out.close();
        }
    }
}
