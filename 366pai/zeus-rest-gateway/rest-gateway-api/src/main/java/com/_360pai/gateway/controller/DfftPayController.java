package com._360pai.gateway.controller;

import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.facade.account.AccountFacade;
import com._360pai.core.facade.account.resp.CompanyResp;
import com._360pai.core.facade.activity.AuctionFacade;
import com._360pai.core.facade.enrolling.EnrollingFacade;
import com._360pai.core.facade.enrolling.req.EnrollingReq;
import com._360pai.gateway.common.dfftpay.PayEnums;
import com._360pai.gateway.common.dfftpay.PayResultEnums;
import com._360pai.gateway.controller.req.dfftpay.DfftPayCallBackReq;
import com._360pai.gateway.controller.req.dfftpay.GatewayPayBackRecordReq;
import com._360pai.gateway.controller.req.dfftpay.UnifiedPayResp;
import com._360pai.gateway.facade.PayFacade;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class DfftPayController {

    private final Logger logger = LoggerFactory.getLogger(DfftPayController.class);


    @Reference(version = "1.0.0")
    private PayFacade payFacade;


    @Reference(version = "1.0.0")
    private AuctionFacade auctionFacade;


    @Reference(version = "1.0.0")
    private AccountFacade accountFacade;


    @Reference(version = "1.0.0")
    private EnrollingFacade enrollingFacade;
    @ResponseBody
    @RequestMapping("/dfftpay/callBack")
    public void scanResultNotice(HttpServletRequest request, HttpServletResponse response) {

        logger.info("接收到东方付通异步结果返回");

        try{
            // 读取请求内容
            String postdata = request.getParameter("postdata");
            if (postdata == null) {
                postdata = this.getInputStreamParam(request);
            }
            ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
            Map<String,String> param = objectMapper.readValue(postdata, Map.class);
            // 支付消息
            String message = param.get("payMessage");
            // 支付订单的支付号码
            String payID =  param.get("payID");
            // 支付状态
            String payStatus =  param.get("payStatus");
            // 签名
            String signature =  param.get("signature");

            DfftPayCallBackReq req = new DfftPayCallBackReq();
            req.setPayID(payID);
            req.setPayStatus(payStatus);
            req.setPayMessage(message);
            req.setSignature(signature);

            logger.info("接收到东方付通回调，回调参数为：{}",JSON.toJSONString(req));

            saveCallBackRecord(req);

            UnifiedPayResp resp = payFacade.ddftResultNotice(req);

            gatewayUpdateCallBack(req,resp);

            if(PayEnums.PAY_BUS_CODE.BALANCE_PAY.getType().equals(resp.getPayBusType())){
                logger.info("通知业务调用方{}",JSON.toJSONString(resp));

                auctionCallBack(resp);


            }else if (PayEnums.PAY_BUS_CODE.ENROLLING_COMMISSION_PAY.getType().equals(resp.getPayBusType())){

                logger.info("抵押物预招商支付结果回调业务方，回调参数为：{}",JSON.toJSONString(resp));

                enrollingCallBack(resp);


            }

            response.getWriter().write("{\"payStatus\":\"000000\"}");
            response.flushBuffer();
        }catch (Exception e){

            logger.error("东方付通回调异常，异常信息为：{}",e);
        }


     }



    @ResponseBody
    @RequestMapping("/dfft_company_info")
    public void getDfftCompanyInfo(HttpServletRequest request, HttpServletResponse response) {

        logger.info("东方付通调用查询机构信息");

        try{


            // 获取公司mem_code
            String mallUserId = request.getParameter("mallUserId");

            //获取请求类型
            String fileType = request.getParameter("fileType");

            if(StringUtils.isEmpty(mallUserId)){


                String reqStr = this.getInputStreamParam(request);

                JSONObject jsonObject = JSONObject.parseObject(reqStr);

                mallUserId = jsonObject.getString("mallUserId");
                fileType = jsonObject.getString("fileType");
            }

            if(StringUtils.isEmpty(mallUserId)){
                failureResponse(ApiCallResult.EMPTY.getDesc(),response);
                return;
            }

            CompanyResp companyResp = accountFacade.getCompanyByMemCode(mallUserId);
            if(companyResp==null){

                failureResponse(ApiCallResult.DATA_IS_EMPTY.getDesc(),response);

                return;

            }

            JSONArray jsonArray = new JSONArray();

            JSONObject licenseImg = new JSONObject();

            licenseImg.put("fileType","10");
            licenseImg.put("file",companyResp.getLicenseImg());


            JSONObject authorizationImg = new JSONObject();
            authorizationImg.put("fileType","11");
            authorizationImg.put("file",companyResp.getAuthorizationImg());

            if("10".equals(fileType)){
                jsonArray.add(licenseImg);
            }else if("11".equals(fileType)){
                jsonArray.add(authorizationImg);
            }else if("12".equals(fileType)){
                jsonArray.add(licenseImg);
                jsonArray.add(authorizationImg);
            }else {
                failureResponse(ApiCallResult.EMPTY.getDesc(),response);
                return;
             }

            successResp(jsonArray,response);


        }catch (Exception e){

            logger.error("东方付通调用查询机构信息，异常信息为：{}",e);
        }


    }

    private void successResp(JSONArray jsonArray, HttpServletResponse response) throws IOException{


        Map object = new HashMap();
        object.put("flag","success");
        object.put("message","成功");
        object.put("filegetType","1");
        object.put("fileList",jsonArray);

        response.getWriter().write(JSON.toJSONString(object));
        response.setCharacterEncoding("UTF-8");
        response.flushBuffer();

    }

    private void failureResponse(String desc,HttpServletResponse response) throws IOException{

        Map object = new HashMap();

        object.put("flag","failure");
        object.put("message",desc);

        response.getWriter().write(JSON.toJSONString(object));
        response.setCharacterEncoding("UTF-8");
        response.flushBuffer();
    }


    /**
      *
      *通知业务方
      */
    private void auctionCallBack(UnifiedPayResp resp) {

        try {
            auctionFacade.payCallBack(resp.getBusId(),resp.getPayOrder(),PayResultEnums.PAY_SUCCESS.getCode().equals(resp.getCode())?SystemConstant.PAY_ORDER_STATUS_PAID_SUCCESS:SystemConstant.PAY_ORDER_STATUS_PAID_FAIL);

            updateBusStatus(resp);
        }catch (Exception e){
            logger.error("回调异常，回调信息为,{}异常信息为{}",JSON.toJSONString(resp),e);

        }
    }

    /**
      *
      *gateway处理完成 更新信息
      */
    private void gatewayUpdateCallBack(DfftPayCallBackReq req, UnifiedPayResp resp) {

        try {
            GatewayPayBackRecordReq recordReq = new  GatewayPayBackRecordReq();
            recordReq.setOrderNum(req.getPayID());
            recordReq.setGatewayStatus(resp.getCode());

            payFacade.updateCallBackInfo(recordReq);
        }catch (Exception e){

            logger.error("更新支付回调表异常，异常信息为：{}",e);
        }



    }

    /**
      *
      *保存回调记录
      */
    private void saveCallBackRecord(DfftPayCallBackReq req) {

        try {
            GatewayPayBackRecordReq callBackInfo = new GatewayPayBackRecordReq();
            callBackInfo.setOrderNum(req.getPayID());
            callBackInfo.setReqParam(JSON.toJSONString(req));
            callBackInfo.setPayStatus(req.getPayStatus());
            callBackInfo.setCreateTime(new Date());
            payFacade.saveCallBackInfo(callBackInfo);

        }catch (Exception e){

            logger.error("保存东方付通回调记录异常，异常信息为：{}",e);
        }


    }

    /**
      *
      *佣金支付成功 回调业务方
      */
    private void enrollingCallBack(UnifiedPayResp resp) {

        try {

            EnrollingReq.payCommissionCallBack payCommissionCallBack = new EnrollingReq.payCommissionCallBack();

            payCommissionCallBack.setBusId(resp.getBusId());
            payCommissionCallBack.setOrderNum(payCommissionCallBack.getOrderNum());
            payCommissionCallBack.setPayStatus(resp.getCode());


            enrollingFacade.payCommissionCallBack(payCommissionCallBack);


            updateBusStatus(resp);


        }catch (Exception e){

            logger.error("预招商佣金回调业务方异常，异常信息为：{}",e);
        }
    }


    /**
     *
     *业务处理完成更新表
     */
    private void updateBusStatus(UnifiedPayResp resp) {

        try {
            GatewayPayBackRecordReq recordReq = new  GatewayPayBackRecordReq();
            recordReq.setOrderNum(resp.getPayOrder());
            recordReq.setCoreStatus(resp.getCode());

            payFacade.updateCallBackInfo(recordReq);
        }catch (Exception e){

            logger.error("更新回调表业务状态异常，异常信息为：{}",e);
        }
    }


    protected String getInputStreamParam(HttpServletRequest request) {
        final StringBuffer stringBuffer = new StringBuffer(255);
        try {
            final BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
            String line;
            synchronized (stringBuffer) {
                while ((line = in.readLine()) != null) {
                    stringBuffer.append(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }

}
