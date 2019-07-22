package com._360pai.core.provider.applet;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.arch.common.utils.RandomNumberGenerator;
import com._360pai.arch.core.redis.RedisCachemanager;
import com._360pai.arch.core.sysconfig.properties.GatewayProperties;
import com._360pai.arch.core.sysconfig.properties.SystemProperties;
import com._360pai.core.aspact.ExceptionEmailService;
import com._360pai.core.common.constants.AppletEnum;
import com._360pai.core.common.constants.ExceptionEmailEnum;
import com._360pai.core.common.constants.ServiceConfigEnum;
import com._360pai.core.condition.applet.TAppletOpenShopOrderCondition;
import com._360pai.core.facade.activity.req.ActivityReq;
import com._360pai.core.facade.applet.AppletFacade;
import com._360pai.core.facade.applet.req.AppletReq;
import com._360pai.core.facade.applet.req.AssistantReq;
import com._360pai.core.facade.applet.vo.InviteRecord;
import com._360pai.core.facade.shop.ShopFacade;
import com._360pai.core.facade.shop.req.ShopReq;
import com._360pai.core.model.applet.TAppletOpenShopOrder;
import com._360pai.core.model.applet.TAppletShop;
import com._360pai.core.model.assistant.DetailViewRecode;
import com._360pai.core.model.assistant.TServiceConfig;
import com._360pai.core.service.activity.AuctionActivityShareStatsService;
import com._360pai.core.service.applet.AppletService;
import com._360pai.core.service.applet.AppletShopService;
import com._360pai.core.service.applet.TAppletOpenShopOrderService;
import com._360pai.core.service.assistant.DetailViewRecodeService;
import com._360pai.core.service.assistant.TServiceConfigService;
import com._360pai.core.vo.applet.OpenShopPayVo;
import com._360pai.gateway.common.dfftpay.PayEnums;
import com._360pai.gateway.common.dfftpay.PayResultEnums;
import com._360pai.gateway.controller.req.dfftpay.UnifiedPayReq;
import com._360pai.gateway.controller.req.dfftpay.UnifiedPayResp;
import com._360pai.gateway.controller.req.dfftpay.UnifiedQueryReq;
import com._360pai.gateway.controller.req.dfftpay.WxScanPayReq;
import com._360pai.gateway.facade.PayFacade;
import com._360pai.gateway.facade.WxFacade;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xdrodger
 * @Title: AppletProvider
 * @ProjectName zeus
 * @Description:
 * @date 2018/11/22 15:39
 */
@Component
@Service(version = "1.0.0")
@Slf4j
public class AppletProvider implements AppletFacade {

    @Autowired
    private AppletService appletService;

    @Resource
    private RedisCachemanager redisCachemanager;

    @Reference(version = "1.0.0")
    private WxFacade wxFacade;

    @Autowired
    private SystemProperties systemProperties;

    @Autowired
    private GatewayProperties gatewayProperties;

    @Autowired
    private ExceptionEmailService exceptionEmailService;


    @Autowired
    private TAppletOpenShopOrderService tAppletOpenShopOrderService;

    @Reference(version = "1.0.0")
    private PayFacade payFacade;


    @Reference(version = "1.0.0")
    private ShopFacade shopFacade;

    @Autowired
    private TServiceConfigService tServiceConfigService;

    @Autowired
    private AuctionActivityShareStatsService auctionActivityShareStatsService;


    @Autowired
    private  AppletShopService appletShopService;

    @Autowired
    private DetailViewRecodeService detailViewRecodeService;

    @Override
    public PageInfoResp<InviteRecord> getInviteRecordList(AppletReq.InviteRecordReq req) {
        return appletService.getInviteRecordList(req);
    }

    @Override
    public ResponseModel getConfigInfo() {
        Map<String, Object> data = new HashMap<>();
        data.put("servicePhone", systemProperties.getAppletServicePhone());
        return ResponseModel.succ(data);
    }


    @Override
    public ResponseModel saveVisitRecord(AssistantReq.comReq req) {

        //商品详情的访客 添加标的浏览量
        if("2".equals(req.getType())){
            if (StringUtils.isEmpty(req.getAuctionId())) {
                return ResponseModel.succ();
            }
            ActivityReq.ActivityId viewReq = new ActivityReq.ActivityId();
            viewReq.setActivityId(Integer.valueOf(req.getAuctionId()));
            auctionActivityShareStatsService.activityView(Integer.valueOf(req.getAuctionId()),null,1);
            //插入浏览记录
            detailViewRecodeService.insertActivityAppletRecode(Integer.valueOf(req.getAuctionId()),req.getAccountId(),req.getPartyId());
            //访问店铺添首页添加店铺的访问
        }else if("1".equals(req.getType())&&req.getReqShopId()!=null&&!String.valueOf(req.getReqShopId()).equals(req.getShopId())){
            TAppletShop shop =  appletShopService.getAppletShopById(req.getReqShopId());
            shop.setViewCount(shop.getViewCount()+1);
            shop.setUpdateTime(DateUtil.getSysTime());
            appletShopService.updateShopById(shop);
        }
        //不传店铺id 直接返回成功 不处理
        if (req.getReqShopId()==null) {
            return ResponseModel.succ();
        }
        //自己进自己的店 不算访客
        if(req.getReqShopId()!=null&&String.valueOf(req.getReqShopId()).equals(req.getShopId())){
            return ResponseModel.succ();
        }

        appletService.saveOrUpdateViewShop(req);
        return ResponseModel.succ();
    }

    @Override
    public ResponseModel getVisitList(AssistantReq.comReq req) {


        return ResponseModel.succ(appletService.getVisitList(req));
    }

    @Override
    @Transactional
    public ResponseModel openShopPay(AssistantReq.comReq req) {
        log.info("开始请求店铺支付接口，参数为：{}",JSON.toJSONString(req));
        //校验是否已经支付过了
        if(isRepeatPay(req)){
            return ResponseModel.fail(ApiCallResult.PAY_REPEAT);
        }

        UnifiedPayResp payResp = new UnifiedPayResp();
        //获取开店所需金额
        String amount = getOpenAmount(req);
        log.info("获取开店费用，费用为：{}",amount);

        OpenShopPayVo vo = new  OpenShopPayVo();

        //开店不需要支付的情况
        if(StringUtils.isEmpty(amount)){
            vo.setAmount("0");
            payResp.setPayOrder(RandomNumberGenerator.generatorMarkOrderNumber(PayEnums.PAY_ORDER_MARK.WX_ORDER.getType(),4));
        }else{
            //调用统一支付接口 获取参数
            payResp = getAppletPayResp(req,amount);
            //支付请求不成功 直接返回
            if(!PayResultEnums.PAY_NOTICE.getCode().equals(payResp.getCode())){
                return ResponseModel.fail(payResp.getDesc());
            }
            vo.setAmount(amount);
            vo.setNonceStr(RandomNumberGenerator.getUUID());
            vo.setTimeStamp(DateUtil.format(new Date(),DateUtil.FORMAT_LONG_NO_SPLIT));
            vo.setSignType("MD5");
            vo.setPayPackage("prepay_id="+payResp.getParam());
            vo.setPaySign(getPaySign(vo));
        }
        vo.setOrderId(payResp.getPayOrder());

        //插入开店订单表
        saveAppletOpenShopOrder(req,new BigDecimal(vo.getAmount()),payResp);

        log.info("开店接口参数返回,{}",JSON.toJSONString(vo));
        return ResponseModel.succ(vo);
    }

    private boolean isRepeatPay(AssistantReq.comReq req) {

        TAppletOpenShopOrderCondition condition = new TAppletOpenShopOrderCondition();
        condition.setPartyId(req.getPartyId());
        condition.setOpenId(req.getOpenId());
        condition.setPayStatus(Integer.valueOf(AppletEnum.PAY_STATUS.SUCCESS.getKey()));
        condition.setIsDelete(false);

        return tAppletOpenShopOrderService.getFirstAppletOpenShopOrder(condition)!=null;
    }

    private void saveAppletOpenShopOrder(AssistantReq.comReq req, BigDecimal amount, UnifiedPayResp payResp) {

        TAppletOpenShopOrder shopOrder = new TAppletOpenShopOrder();
        shopOrder.setPayStatus(Integer.valueOf(AppletEnum.PAY_STATUS.INIT.getKey()));
        shopOrder.setAmount(amount);
        shopOrder.setCreateTime(DateUtil.getSysTime());
        shopOrder.setIsDelete(false);
        shopOrder.setPrepayId(payResp.getParam());
        shopOrder.setOpenId(req.getOpenId());
        shopOrder.setOrderId(payResp.getPayOrder());
        shopOrder.setPartyId(req.getPartyId());
        shopOrder.setType(1);

        tAppletOpenShopOrderService.saveOrder(shopOrder);
    }

    /**
     *
     *微信加密
     */
    private String getPaySign(OpenShopPayVo vo) {
        Map<String, String> data = new HashMap<>();

        data.put("appId",gatewayProperties.getAppletAppId());
        data.put("nonceStr",vo.getNonceStr());
        data.put("package",vo.getPayPackage());
        data.put("signType",vo.getSignType());
        data.put("timeStamp",vo.getTimeStamp());
        try {

            return WXPayUtil.generateSignature(data, gatewayProperties.getWxPayKey(), WXPayConstants.SignType.MD5);

        }catch (Exception e){
            log.error("小程序支付二次签名异常，异常信息为：{}",e);
        }

        return null;

     }

    private UnifiedPayResp getAppletPayResp(AssistantReq.comReq req ,String amount) {

        UnifiedPayResp payResp = new UnifiedPayResp();

        UnifiedPayReq payReq = new UnifiedPayReq();
        payReq.setPayType(PayEnums.PAY_TYPE.APPLET_PAY.getType());
        payReq.setPartyId(req.getPartyId());
        payReq.setPayBusCode(PayEnums.PAY_BUS_CODE.APPLET_OPEN_PAY.getType());
        payReq.setAmount(new BigDecimal(amount));
        payReq.setBusId(req.getOpenId());

        WxScanPayReq wxScanPayReq = new WxScanPayReq();
        wxScanPayReq.setBody("开店基金");
        wxScanPayReq.setOpenId(req.getOpenId());

        payReq.setPayParam(wxScanPayReq);
        log.info("开店请求微信支付接口，参数为：{}",JSON.toJSONString(payReq));

        try {

            payResp = payFacade.unifiedPay(payReq);

        }catch (Exception e){

            exceptionEmailService.sendExceptionEmail(ExceptionEmailEnum.IMPORTANT_LEVEL.SERIOUS,ExceptionEmailEnum.MODULE_TYPE.APPLET,"开店支付异常",JSON.toJSONString(payReq),exceptionEmailService.exceptionToStr(e));
            log.error("开店调用小程序异常，异常信息为：{}",e);
            payResp.setCode(PayResultEnums.SYS_EXCEPTION.getCode());
            payResp.setDesc(PayResultEnums.SYS_EXCEPTION.getDesc());
        }


        return payResp;
    }

    private String getOpenAmount(AssistantReq.comReq req) {

        //看看是不是不用支付的用户
        if(checkNotPay(req.getPartyId())){
            return null;
        }

        //查询配置的信息
        TServiceConfig tServiceConfig = tServiceConfigService.selectByConfigType(ServiceConfigEnum.OPEN_SHOP_PRICE);

        if(!("0.00").equals(tServiceConfig.getConfigValue())&&!"0".equals(tServiceConfig.getConfigValue())){

            return  tServiceConfig.getConfigValue();

        }

        return null;
    }

    private boolean checkNotPay(Integer partyId) {

        try{
            String partyIds = gatewayProperties.getAppletOpenShopNotPay();

            String[] ids = partyIds.split(",");

            for(int i=0;i<ids.length;i++){

                if(ids[i].equals(String.valueOf(partyId))){
                    return true;
                }
            }

        }catch (Exception e){
            log.error("获取开店不需要支付的partyId 异常，异常信息为{}",e);
        }
        return false;
    }


    /**
     *
     *开店回调支付处理
     */
    @Override
    @Transactional
    public ResponseModel openShopPayCallBack(AssistantReq.payCallBackReq req) {

        log.info("开店回调处理，参数为：{}",JSON.toJSONString(req));

        return openShop(req);
    }

    private ResponseModel openShop(AssistantReq.payCallBackReq req) {

        //获取支付的订单
        TAppletOpenShopOrderCondition condition = new TAppletOpenShopOrderCondition();
        condition.setIsDelete(false);
        condition.setOrderId(req.getOrderId());
        condition.setPartyId(req.getPartyId());
        TAppletOpenShopOrder order =tAppletOpenShopOrderService.getFirstAppletOpenShopOrder(condition);
        if(order==null){
            return ResponseModel.fail(ApiCallResult.DATA_IS_EMPTY);
        }
        //不为初始状态说明处理过了 直接返回
        if(!AppletEnum.PAY_STATUS.INIT.getKey().equals(String.valueOf(order.getPayStatus()))){
            return ResponseModel.succ();
        }
        UnifiedPayResp payResp = new UnifiedPayResp();
        payResp.setCode(PayResultEnums.PAY_SUCCESS.getCode());
        payResp.setDesc(PayResultEnums.PAY_SUCCESS.getDesc());
        //支付金额不为0 查询接口
        if(!order.getAmount().toString().equals("0.00")){
            UnifiedQueryReq queryReq  = new UnifiedQueryReq();
            queryReq.setPayType(PayEnums.PAY_TYPE.APPLET_PAY.getType());
            queryReq.setPayOrder(req.getOrderId());
            payResp = payFacade.unifiedPayQuery(queryReq);
        }
        if(PayResultEnums.PAY_SUCCESS.getCode().equals(payResp.getCode())){

            order.setPayStatus(Integer.valueOf(AppletEnum.PAY_STATUS.SUCCESS.getKey()));
            order.setUpdateTime(DateUtil.getSysTime());
            //更新支付状态
            tAppletOpenShopOrderService.updateById(order);
        }else{
            return ResponseModel.fail(ApiCallResult.NO_PAY);
        }

        //保存店铺信息
        saveShop(req,order);

        return ResponseModel.succ();
    }

    private void saveShop(AssistantReq.payCallBackReq req, TAppletOpenShopOrder order) {
        ShopReq.CreateReq createReq = new ShopReq.CreateReq();
        createReq.setPartyId(req.getPartyId());
        createReq.setOpenId(req.getOpenId());
        createReq.setAmount(order.getAmount());
        log.info("请求开店facade,请求参数为{}",JSON.toJSONString(createReq));

        shopFacade.createShop(createReq);

    }


    @Override
    public void queryAppletOrderQuartz() {

        //获取半小时内未成功的小程序订单
        List<TAppletOpenShopOrder> notPayList = tAppletOpenShopOrderService.getAppletNotPayOrderList();
        log.info("定时器查询小程序为支付订单，结果为：{}",JSON.toJSONString(notPayList));
        if(notPayList.size()<1){
            return;
        }

        for(TAppletOpenShopOrder order : notPayList){
            AssistantReq.payCallBackReq req = new AssistantReq.payCallBackReq();
            req.setOpenId(order.getOpenId());
            req.setPartyId(order.getPartyId());
            req.setOrderId(order.getOrderId());
            log.info("小程序支付订单定时任务，参数：{}",JSON.toJSONString(req));

            openShop(req);

        }

    }
}
