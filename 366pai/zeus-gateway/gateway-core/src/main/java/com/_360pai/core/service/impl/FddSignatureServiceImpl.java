package com._360pai.core.service.impl;

import com._360pai.arch.common.HttpUtils;
import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.arch.common.utils.NumberFormatUtils;
import com._360pai.arch.common.utils.RandomNumberGenerator;
import com._360pai.arch.core.sysconfig.properties.GatewayProperties;
import com._360pai.core.aspact.GatewayExceptionEmailService;
import com._360pai.core.common.constants.ExceptionEmailEnum;
import com._360pai.core.condition.fdd.*;
import com._360pai.core.dao.fdd.*;
import com._360pai.core.model.FddSignatuer.*;
import com._360pai.core.model.fdd.*;
import com._360pai.core.service.FddSignatureService;
import com._360pai.gateway.common.fddSignature.FddEnums;
import com._360pai.gateway.common.fddSignature.FddSignatureUtils;
import com._360pai.gateway.controller.req.fdd.*;
import com._360pai.gateway.exception.BusinessException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 描述：法大大服务接口实现类
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/13 13:28
 */
@Service
public class FddSignatureServiceImpl extends  GatewayExceptionEmailService implements FddSignatureService {

    private final Logger logger = LoggerFactory.getLogger(FddSignatureServiceImpl.class);

    //异步签章线程池
    ExecutorService autoSignContractThreadPool = Executors.newFixedThreadPool(3);


    @Autowired
    private GatewayFddMemberDao gatewayFddMemberDao;

    @Autowired
    private GatewayFddGenerateContractDao gatewayFddGenerateContractDao;

    @Autowired
    private GatewayFddRecordDao gatewayFddRecordDao;

    @Autowired
    private GatewayFddSignAutoDao gatewayFddSignAutoDao;

    @Autowired
    private GatewayProperties gatewayProperties;


    @Autowired
    private GatewayFddSignContractDao gatewayFddSignContractDao;


    @Autowired
    private GatewayFddTemplateDao gatewayFddTemplateDao;


    @Autowired
    private GatewayFddSignKeyworkDao gatewayFddSignKeyworkDao;


    @Autowired
    private GatewayFddCallBackRecordDao gatewayFddCallBackRecordDao;



    private static final String  SELLER ="seller";

    private static final String  BUYER ="buyer";

    private static final String  AUTO ="auto";


    //法大大开户接口
    @Override
    public OpenMemberResp openMember(OpenMemberReq req) {

        OpenMemberResp resp = new OpenMemberResp();

        try{
            //请求法大大接口
            String respStr;
            //个人类型
            if("1".equals(req.getCustomer_type())){
                 respStr = FddSignatureUtils.invokeSyncPersonAuto(gatewayProperties,req.getCustomer_name(),req.getEmail(),req.getId_card(),req.getIdent_type(),req.getMobile());

            }else {
                //校验邮箱 若为空随机选取邮箱
                if(StringUtils.isEmpty(req.getEmail())){
                    req.setEmail(RandomNumberGenerator.wordGenerator(10)+"@360pai.com");
                }
                respStr = FddSignatureUtils.invokeSyncCompanyAuto(gatewayProperties,req.getCustomer_name(),req.getEmail(),req.getId_card(),req.getIdent_type(),req.getMobile());
            }

            resp = getResp(respStr,resp);

        }catch (Exception e){

            //发送异常邮件
            sendExceptionEmail(ExceptionEmailEnum.IMPORTANT_LEVEL.SERIOUS,ExceptionEmailEnum.MODULE_TYPE.SIGNATURE,"法大大开户请求",JSON.toJSONString(req),e);

            logger.error("请求法大大开户服务异常，异常信息为",e);
            resp.setCode(ApiCallResult.EXCEPTION.getCode());
            resp.setDesc(ApiCallResult.EXCEPTION.getDesc());
        }
        //记录请求记录
        insertMemberRecord(req,resp);

        //发送提示邮件
        sendFddRemindEmail(req,resp,resp.getCode());
        return resp;
    }




    private OpenMemberResp getResp(String respStr,OpenMemberResp resp) {

        JSONObject obj=JSON.parseObject(respStr);

        String code = obj.getString("code");

        resp.setCode(code);
        resp.setDesc(obj.getString("msg"));

        if("1000".equals(code)){
            resp.setCode(ApiCallResult.SUCCESS.getCode());
            resp.setCustomer_id(obj.getString("customer_id"));
        }
        return resp;
    }


    @Override
    public ResponseModel uploadTemplate(UploadTemplateReq req) {

        ResponseModel resp = new  ResponseModel();

        try{
            FddSignatureUtils utils = new FddSignatureUtils();

            String responseStr = utils.invokeUploadTemplate(gatewayProperties,req.getTemplate_id(),req.getFile(),req.getDoc_url());
            String code = JSON.parseObject(responseStr).getString("code");
            resp.setCode("0000".equals(code)?ApiCallResult.SUCCESS.getCode():code);
            resp.setDesc(JSON.parseObject(responseStr).getString("msg"));

        }catch (Exception e){
            logger.error("上传法大大模板异常，异常信息为{}",e);
            resp.setCode(ApiCallResult.EXCEPTION.getCode());
            resp.setDesc(ApiCallResult.EXCEPTION.getDesc());
        }

        //请求记录
        insertCommRecord(req.getTemplate_id(),JSON.toJSONString(req),JSON.toJSONString(req),resp.getCode(),"uploadTemplate");

        return resp;
    }


    @Override
    public ExtSignContractResp extSignContract(ExtSignContractReq req) {

        ExtSignContractResp resp = new ExtSignContractResp();

        try{
            //当为服务类协议时
            if(FddEnums.SING_TYPE.JINDIAO_DELEGATION.getType().equals(req.getType())||FddEnums.SING_TYPE.JINZI_DELEGATION.getType().equals(req.getType())||FddEnums.SING_TYPE.SANFANG_DELEGATION.getType().equals(req.getType())){

                return serviceAutoSignContract(req);
            }
            //获取手动签章参数
            FddSignInfo extInfo = getExtInfo(req.getSign_list());

            //未获取手动签章参数 直接返回
            if(extInfo==null){
                resp.setCode(ApiCallResult.EMPTY.getCode());
                resp.setDesc(ApiCallResult.EMPTY.getDesc());
            }else{
                //获取手动签章参数
                resp = getExtSignContractResp(extInfo,req);
            }

            //将请求参数保存
            saveExtSignContract(req,resp);
        }catch (Exception e){
            //发送异常邮件
            sendExceptionEmail(ExceptionEmailEnum.IMPORTANT_LEVEL.SERIOUS,ExceptionEmailEnum.MODULE_TYPE.SIGNATURE,"法大大签章接口",JSON.toJSONString(req),e);
            logger.error("调用自动签章接口异常，异常信息为：{}",e);
            resp.setCode(ApiCallResult.EXCEPTION.getCode());
            resp.setDesc(ApiCallResult.EXCEPTION.getDesc());
        }
        //发送提示邮件
        sendFddRemindEmail(req,resp,resp.getCode());

        return resp;
    }

    /**
     *
     *服务类自动签章
     */
    private ExtSignContractResp serviceAutoSignContract(ExtSignContractReq req) {

        ExtSignContractResp resp = new ExtSignContractResp();

        GatewayFddTemplate template = getTemplateInfo(req.getType());


        List<FddSignInfo>  sign_list = req.getSign_list();

        //添加平台的签章
        if(FddEnums.SING_TYPE.JINDIAO_DELEGATION.getType().equals(req.getType())||FddEnums.SING_TYPE.SANFANG_DELEGATION.getType().equals(req.getType())){

            FddSignInfo webInfo = new FddSignInfo();
            webInfo.setSign_role(FddEnums.SING_ROLE_TYPE.WEB.getType());
            webInfo.setMem_role("2");
            webInfo.setIs_auto(FddEnums.SING_AUTO.AUTO.getType());
            webInfo.setFdd_id(gatewayProperties.getFddWebFddId());

            sign_list.add(webInfo);

        }

        req.setSign_list(sign_list);

        String downLoadUrl = null;
        String viewPdfUrl = null;

        for(FddSignInfo info :sign_list ){

            String transaction_id = getTransactionNum(FddEnums.TRANSACTION_TYPE.WEB.getType());

            String responseStr = FddSignatureUtils.invokeExtSignAuto(gatewayProperties,transaction_id,info.getFdd_id(),"1",req.getContract_id(),template.getDocTitle(),getSignKeyWord(req.getType(),info.getSign_role()),"2");

            JSONObject obj=JSON.parseObject(responseStr);

            if("1000".equals(obj.getString("code"))){
                downLoadUrl = obj.getString("download_url");
                viewPdfUrl = obj.getString("viewpdf_url");
            }
            //保存自动签章记录
            saveAutoRecord(info,req,responseStr);
        }

        //保存服务类签章记录
        saveServiceAutoSign(req,resp,downLoadUrl,viewPdfUrl);

        resp.setCode(ApiCallResult.SUCCESS.getCode());
        resp.setDesc(ApiCallResult.SUCCESS.getDesc());

        return resp;
    }

    /**
     *
     *自动签章信息记录
     */
    private void saveAutoRecord(FddSignInfo info, ExtSignContractReq req, String responseStr) {

        JSONObject obj=JSON.parseObject(responseStr);

        String code = obj.getString("code");

        //保存请求记录
        insertCommRecord(req.getContract_id(),JSON.toJSONString(info),responseStr,code,"autoSign");
        //当自动签章不成功时发送提醒邮件
        if(!"1000".equals(code)){
            sendFddRemindEmail(JSON.toJSONString(req)+JSON.toJSONString(info),responseStr,code);
        }



    }

    /**
     *
     *服务类签章记录
     */
    private void saveServiceAutoSign(ExtSignContractReq req,ExtSignContractResp resp,String downLoadUrl,String viewPdfUrl) {

        //保存请求记录
        insertCommRecord(req.getContract_id(),JSON.toJSONString(req),JSON.toJSONString(resp),resp.getCode(),"serviceAutoSign");
        try {
            GatewayFddSignContract signParam = new GatewayFddSignContract();
            signParam.setActivityId(req.getActivity_id());
            signParam.setContractId(req.getContract_id());
            signParam.setAutoSignParam(JSON.toJSONString(req.getSign_list()));
            signParam.setSellerSigned(true);
            signParam.setAllSigned(true);
            signParam.setCreateTime(new Date());
            signParam.setSignType(req.getType());
            signParam.setDownloadUrl(downLoadUrl);
            signParam.setViewpdfUrl(viewPdfUrl);
            gatewayFddSignContractDao.insert(signParam);
        }catch (Exception e){
            logger.error("服务类保存记录异常，异常信息为:{}",e);
        }




    }


    /**
     *
     *获取手动签章返回参数
     */
    private ExtSignContractResp getExtSignContractResp(FddSignInfo extInfo,ExtSignContractReq req) {
        ExtSignContractResp resp = new ExtSignContractResp();

        //获取模板信息
        GatewayFddTemplate template = getTemplateInfo(req.getType());
        if(template==null){
            resp.setCode("100");
            resp.setDesc("未找到模板信息");
            return  resp;
        }
        //交易号
        String transaction_id = getTransactionNum(extInfo.getSign_role().equals(FddEnums.SING_ROLE_TYPE.BUYER.getType())?FddEnums.TRANSACTION_TYPE.BUYER.getType():FddEnums.TRANSACTION_TYPE.SELLER.getType());

        String keyWord = getSignKeyWord(req.getType(),extInfo.getSign_role());

        String param = FddSignatureUtils.extSign(gatewayProperties,transaction_id,extInfo.getFdd_id(),extInfo.getMem_role(),req.getContract_id(),template.getDocTitle(),keyWord,"2",gatewayProperties.getFddNotifyUrl());

        resp.setCode(ApiCallResult.SUCCESS.getCode());
        resp.setDesc(ApiCallResult.SUCCESS.getDesc());
        resp.setParam(param);
        resp.setUrl(gatewayProperties.getFddUrl()+"extsign.api");

        return resp;

    }

    /**
     *
     * 获取签章的关键字
     */
    private String getSignKeyWord(String type, String sign_role) {
        GatewayFddSignKeyworkCondition condition = new GatewayFddSignKeyworkCondition();
        condition.setType(type);
        condition.setRoleType(sign_role);
        GatewayFddSignKeywork keyWork = gatewayFddSignKeyworkDao.selectFirst(condition);
        if(keyWork==null){
            logger.error("查询签章的关键字为空,查询信息为：类型{},角色编号{}",type,sign_role);

            return null;
        }

        return keyWork.getKeyName();
     }

    /**
     *
     * 获取手动签章参数
     */
    private FddSignInfo getExtInfo(List<FddSignInfo> sign_list) {

        for(FddSignInfo info :sign_list){
            //获取手动签章参数
            if(FddEnums.SING_AUTO.NOT_AUTO.getType().equals(info.getIs_auto())){
                return info;
             }

        }
        return null;
     }

    private void saveExtSignContract(ExtSignContractReq req,ExtSignContractResp resp) {
        //保存请求记录
        insertCommRecord(req.getContract_id(),JSON.toJSONString(req),JSON.toJSONString(resp),resp.getCode(),"extSignContract");

        //根据合同查询是否已经有请求的数据了
        GatewayFddSignContractCondition condition = new GatewayFddSignContractCondition();

        condition.setContractId(req.getContract_id());
        condition.setActivityId(req.getActivity_id());
        try{
            GatewayFddSignContract contract = gatewayFddSignContractDao.selectFirst(condition);
            Map<String,String> map = getAssortInfo(req.getSign_list());

            if(contract==null){

                GatewayFddSignContract signParam = new GatewayFddSignContract();

                signParam.setActivityId(req.getActivity_id());
                signParam.setContractId(req.getContract_id());
                signParam.setAutoSignParam(map.get(AUTO));
                signParam.setBuyerSignParam(map.get(BUYER));
                signParam.setSellerSignParam(map.get(SELLER));
                signParam.setCreateTime(new Date());
                signParam.setSignType(req.getType());
                gatewayFddSignContractDao.insert(signParam);
        }else{
                if(StringUtils.isEmpty(contract.getAutoSignParam())||contract.getAutoSignParam().length()<map.get(AUTO).length()){
                    contract.setAutoSignParam(map.get(AUTO));
                }
                if(StringUtils.isEmpty(contract.getBuyerSignParam())){
                    contract.setBuyerSignParam(map.get(BUYER));
                }
                if(StringUtils.isEmpty(contract.getSellerSignParam())){
                    contract.setSellerSignParam(map.get(SELLER));
                }
                contract.setUpdateTime(new Date());
                gatewayFddSignContractDao.updateById(contract);

            }

        }catch (Exception e){

            //发送异常邮件
            sendExceptionEmail(ExceptionEmailEnum.IMPORTANT_LEVEL.SERIOUS,ExceptionEmailEnum.MODULE_TYPE.SIGNATURE,"法大大保存签章参数",JSON.toJSONString(req),e);

            logger.error("保存签章参数异常，异常信息为",e);

        }

    }

    /**
     *
     * 将签章参数分类
     */
    private Map<String,String> getAssortInfo(List<FddSignInfo> sign_list) {

        Map<String,String> map = new HashMap<>();
        //遍历签章参数
        Iterator<FddSignInfo> it = sign_list.iterator();
        while(it.hasNext()){
            FddSignInfo info = it.next();
            if(FddEnums.SING_ROLE_TYPE.SELLER.getType().equals(info.getSign_role())){
                map.put(SELLER,JSON.toJSONString(info));
                it.remove();
                //竞买人
            }else if(FddEnums.SING_ROLE_TYPE.BUYER.getType().equals(info.getSign_role())){
                map.put(BUYER,JSON.toJSONString(info));
                it.remove();
            }

        }

        //将平台的自动签章参数添加进去
        FddSignInfo webInfo = new FddSignInfo();
        webInfo.setSign_role(FddEnums.SING_ROLE_TYPE.WEB.getType());
        webInfo.setMem_role("2");
        webInfo.setIs_auto(FddEnums.SING_AUTO.AUTO.getType());
        webInfo.setFdd_id(gatewayProperties.getFddWebFddId());
        sign_list.add(webInfo);
        map.put(AUTO,JSON.toJSONString(sign_list));

        return  map;

    }


    /**
     *
     * 生成合同接口
     * @param req 公共请求参数
     * @param param 根据各个协议确定
     */
    @Override
    public GenerateContractResp generateContract(GenerateContractComReq req, Object param) {

        GenerateContractResp resp = new GenerateContractResp();

        //检查是否已经生成了合同
        /*if(!checkExist(req)){
            return GenerateContractResp.fail("合同已生成，请勿重复生成！");
        }*/

        //获取模板信息
        GatewayFddTemplate template = getTemplateInfo(req.getType());
        if(template==null){
            return GenerateContractResp.fail("未找到模板信息！");
        }

        String contractId = getTransactionNum(FddEnums.TRANSACTION_TYPE.CONTRACT.getType());

        try{
            //调用法大大
            String responseStr = FddSignatureUtils.invokeGenerateContract(gatewayProperties,template.getTemplateId(),contractId,template.getDocTitle(),getParameterMap(req.getType() ,param),null);
            //返回结果封装
            resp = getGenerateContractResp(responseStr,contractId);

        }catch (Exception e){
            //发送异常邮件
            sendExceptionEmail(ExceptionEmailEnum.IMPORTANT_LEVEL.SERIOUS,ExceptionEmailEnum.MODULE_TYPE.SIGNATURE,"法大大生成合同接口",JSON.toJSONString(req),e);

            logger.error("调用自动签章接口异常，异常信息为：",e);

            resp.setCode(ApiCallResult.EXCEPTION.getCode());
            resp.setDesc(ApiCallResult.EXCEPTION.getDesc());
        }
        req.setParam(param);
        //保存请求合同记录
        saveGenerateContract(req,resp,template,contractId);

        //发送提示邮件
        sendFddRemindEmail(req,resp,resp.getCode());

        return resp;
    }

    private boolean checkExist(GenerateContractComReq req) {
        GatewayFddGenerateContractCondition condition = new GatewayFddGenerateContractCondition();
        condition.setPartyId(Integer.valueOf(req.getPartyId()));
        condition.setActivityId(req.getActivityId());
        condition.setStatus("000");

        return gatewayFddGenerateContractDao.selectList(condition).size()==0;

     }

    @Override
    public GenerateContractResp extSignNotify(FddCallBackReq req) {

        GenerateContractResp resp = new GenerateContractResp();

        try {
            //获取签章的参数
            GatewayFddSignContractCondition condition = new GatewayFddSignContractCondition();
            condition.setContractId(req.getContract_id());

            GatewayFddSignContract signContract =gatewayFddSignContractDao.selectFirst(condition);

            if(signContract==null){
                logger.error("回调参数为空，回调参数为：",JSON.toJSONString(req));
                resp.setCode(ApiCallResult.DATA_EMPTY.getCode());
                resp.setDesc(ApiCallResult.DATA_EMPTY.getDesc());
                return resp;
            }
            resp.setActivityId(signContract.getActivityId());
            resp.setContractId(signContract.getContractId());
            resp.setSignType(signContract.getSignType());
            if(signContract.getAllSigned()){
                logger.error("合同已经全部签章过了,返回签章成功信息：{}",JSON.toJSONString(req));
                resp.setCode(ApiCallResult.SUCCESS.getCode());
                resp.setDesc(ApiCallResult.SUCCESS.getDesc());
                return resp;
            }

            //竞买人回调
            if(req.getTransaction_id().contains(FddEnums.TRANSACTION_TYPE.BUYER.getType())){

                FddSignInfo signInfo =  JSON.parseObject(signContract.getBuyerSignParam(),FddSignInfo.class);
                resp.setPartyId(signInfo.getParty_id());
                resp.setSignRole(FddEnums.SING_ROLE_TYPE.BUYER.getType());
            }else{
                FddSignInfo signInfo =  JSON.parseObject(signContract.getSellerSignParam(),FddSignInfo.class);
                resp.setPartyId(signInfo.getParty_id());
                resp.setSignRole(FddEnums.SING_ROLE_TYPE.SELLER.getType());

            }


            resp.setCode(ApiCallResult.SIGN_FAILURE.getCode());
            resp.setDesc(ApiCallResult.SIGN_FAILURE.getDesc());
            //签章成功处理
            if(FddEnums.SING_RESULT.SUCCESS.getType().equals(req.getResult_code())){
                resp.setCode(ApiCallResult.SUCCESS.getCode());
                resp.setDesc(ApiCallResult.SUCCESS.getDesc());



                //当为成交确认协议是  要区分是委托人还是 竞买人
                if(FddEnums.SING_TYPE.DEAL.getType().equals(signContract.getSignType())||FddEnums.SING_TYPE.LEASE_DEAL.getType().equals(signContract.getSignType())||FddEnums.SING_TYPE.LEASE_HAS_LICENSE.getType().equals(signContract.getSignType())||FddEnums.SING_TYPE.LEASE_WITHOUT_LICENSE.getType().equals(signContract.getSignType())){
                    //竞买人回调
                    if(req.getTransaction_id().contains(FddEnums.TRANSACTION_TYPE.BUYER.getType())){
                        signContract.setBuyerSigned(true);

                    }else{
                        signContract.setSellerSigned(true);
                    }
                }else{
                    signContract.setSellerSigned(true);
                }

                signContract.setId(signContract.getId());
                signContract.setDownloadUrl(req.getDownload_url());
                signContract.setViewpdfUrl(req.getViewpdf_url());
                signContract.setUpdateTime(new Date());
                gatewayFddSignContractDao.updateById(signContract);

                //当时成交确认协议时 需要 买卖双方都签章是才能自动签章
                if(FddEnums.SING_TYPE.DEAL.getType().equals(signContract.getSignType())||FddEnums.SING_TYPE.LEASE_DEAL.getType().equals(signContract.getSignType())||FddEnums.SING_TYPE.LEASE_HAS_LICENSE.getType().equals(signContract.getSignType())||FddEnums.SING_TYPE.LEASE_WITHOUT_LICENSE.getType().equals(signContract.getSignType())){

                    if(signContract.getBuyerSigned()&&signContract.getSellerSigned()){
                        autoSignContract(signContract);
                    }
                }else{
                    autoSignContract(signContract);
                }
            }
        }catch (Exception e){

            //发送异常邮件
            sendExceptionEmail(ExceptionEmailEnum.IMPORTANT_LEVEL.SERIOUS,ExceptionEmailEnum.MODULE_TYPE.SIGNATURE,"法大大回调处理",JSON.toJSONString(req),e);

            resp.setCode(ApiCallResult.EXCEPTION.getCode());
            resp.setDesc(ApiCallResult.EXCEPTION.getDesc());
            logger.error("签章回调处理异常，异常信息为",e);
        }
        //发送提示邮件
        sendFddRemindEmail(req,resp,resp.getCode());

        return resp;

    }



    @Override
    public ResponseModel saveCallBackInfo(GatewayFddCallBackRecord req) {
        //查询是否已经有记录了 有了记录直接返回
        GatewayFddCallBackRecordCondition condition = new GatewayFddCallBackRecordCondition();
        condition.setContractId(req.getContractId());
        condition.setTransactionId(req.getTransactionId());
        condition.setCoreStatus("000");
        condition.setGatewayStatus("000");

        GatewayFddCallBackRecord record = gatewayFddCallBackRecordDao.selectFirst(condition);

        //记录不为null 说明已经处理过了
        if(record!=null){
            return ResponseModel.fail();

        }

        gatewayFddCallBackRecordDao.insert(req);

        return  ResponseModel.succ();
    }

    @Override
    public void updateCallBackInfo(GatewayFddCallBackRecord req) {

        try {
            GatewayFddCallBackRecordCondition record = new GatewayFddCallBackRecordCondition();
            record.setContractId(req.getContractId());
            record.setTransactionId(req.getTransactionId());

            GatewayFddCallBackRecord callBackRecord = gatewayFddCallBackRecordDao.selectFirst(record);
            if(callBackRecord==null){
                return;
            }

            callBackRecord.setCoreStatus(req.getCoreStatus());
            callBackRecord.setGatewayStatus(req.getGatewayStatus());
            callBackRecord.setUpdateAt(new Date());

            gatewayFddCallBackRecordDao.updateById(callBackRecord);
        }catch (Exception e){

            logger.error("更新法大大返回信息异常,异常信息为：{}",e);

        }




    }

    @Override
    public void querySignQuartz() {
        logger.info("job主动查询签章状态启动");

        //获取物签章回调的记录
        List<GatewayFddSignContract> contractsList = gatewayFddSignContractDao.queryNotSignConTract();
        logger.info("查询到未签章的数据为：{}",JSON.toJSONString(contractsList));

        for(GatewayFddSignContract contract: contractsList){

            //委托人未签署时
            if(!contract.getSellerSigned()){


                logger.info("未签署回调信息为：{}",JSON.toJSONString(contract));
                if(!contract.getSellerSigned()&&StringUtils.isNotEmpty(contract.getSellerSignParam())){
                    FddSignInfo signInfo =  JSON.parseObject(contract.getSellerSignParam(),FddSignInfo.class);

                    //主动查询签章的状态
                    String resp = FddSignatureUtils.invokeQuerySignStatus(gatewayProperties,contract.getContractId(),signInfo.getFdd_id());
                    logger.info("查询返回的签章状态：{}",resp);

                    //调用接口
                    reqCallBack(resp,contract.getContractId());
                }
            }

            //成交协议竞买人 未签章时 回调
            if(!contract.getBuyerSigned()&&(FddEnums.SING_TYPE.DEAL.getType().equals(contract.getSignType())||FddEnums.SING_TYPE.LEASE_DEAL.getType().equals(contract.getSignType())||FddEnums.SING_TYPE.LEASE_WITHOUT_LICENSE.getType().equals(contract.getSignType())||FddEnums.SING_TYPE.LEASE_HAS_LICENSE.getType().equals(contract.getSignType()))){

                logger.info("竞买人签章未回调的信息为：{}",JSON.toJSONString(contract));

                if(StringUtils.isNotEmpty(contract.getBuyerSignParam())){
                    FddSignInfo signInfo =  JSON.parseObject(contract.getBuyerSignParam(),FddSignInfo.class);

                    //主动查询签章的状态
                    String resp = FddSignatureUtils.invokeQuerySignStatus(gatewayProperties,contract.getContractId(),signInfo.getFdd_id());

                    logger.info("竞买人查询签章返回结果为：{}",resp);


                    //调用接口
                    reqCallBack(resp,contract.getContractId());
                }



            }



        }

    }



    /**
     *
     *主动调用支付回调
     */
    private void reqCallBack(String resp,String contractId) {

        try{

            JSONObject respJson = JSON.parseObject(resp);

            String code = respJson.getString("code");
            //操作成功
            if("1000".equals(code)){
                //签署成功
                if("1".equals(respJson.getString("sign_status"))){


                    //调用回调接口
                    String download_url = respJson.getString("download_url");
                    String viewpdf_url = respJson.getString("viewpdf_url");
                    String transaction_id = respJson.getString("transaction_id");

                    Map<String, String> parameters = new HashMap<>();

                    parameters.put("transaction_id",transaction_id);
                    parameters.put("contract_id",contractId);
                    parameters.put("result_code","3000");
                    parameters.put("result_desc","job成功回调");
                    parameters.put("download_url",download_url);
                    parameters.put("viewpdf_url",viewpdf_url);
                    parameters.put("type","2");

                    HttpUtils.sendPost(gatewayProperties.getFddNotifyUrl(),parameters);


                }


            }

        }catch (Exception e){

            logger.error("job主动回调异常，异常信息为：{}",e);
        }







    }


    /**
     *
     *异步自动签章
     */
    private void autoSignContract(GatewayFddSignContract signContract) {

        logger.info("开始处理自动签章，签章参数为：{}",JSON.toJSONString(signContract));

        autoSignContractThreadPool.execute((()->runnableAutoSignContract(signContract)));

    }


    /**
     *
     *异步自动签章接口
     */
    void runnableAutoSignContract(GatewayFddSignContract signContract) {

        //当全部已经签署的时候直接返回
        if(signContract.getAllSigned()){

            return;
        }
        List<FddSignInfo> list =JSON.parseArray(signContract.getAutoSignParam(),FddSignInfo.class);

        GatewayFddTemplate template = getTemplateInfo(signContract.getSignType());

        for(FddSignInfo info : list){

            String transaction_id = getTransactionNum(FddEnums.TRANSACTION_TYPE.WEB.getType());
            logger.info("开始自动签章，签章参数为：{}",JSON.toJSONString(info));

            String responseStr = FddSignatureUtils.invokeExtSignAuto(gatewayProperties,transaction_id,info.getFdd_id(),"1",signContract.getContractId(),template.getDocTitle(),getSignKeyWord(signContract.getSignType(),info.getSign_role()),"2");
            ExtSignContractReq req = new ExtSignContractReq();
            req.setContract_id(signContract.getContractId());
            //保存自动签章记录
            saveAutoRecord(info,req,responseStr);
            logger.info("结束自动签章，返回参数为：{}",responseStr);

        }

        //根据签章结果更新签章表

        signContract.setAllSigned(true);
        signContract.setUpdateTime(new Date());
        logger.info("更新的参数为：{}",JSON.toJSONString(signContract));


        gatewayFddSignContractDao.updateById(signContract);

    }

    /**
     *
     * 保存生成合同记录
     */
    private void saveGenerateContract(GenerateContractComReq req, GenerateContractResp resp,GatewayFddTemplate template,String contractId) {

        //保存请求记录
        insertCommRecord(req.getActivityId(),JSON.toJSONString(req),JSON.toJSONString(resp),resp.getCode(),"generateContract");

        try{
            GatewayFddGenerateContract  contract = new GatewayFddGenerateContract();

            contract.setContractId(contractId);
            contract.setDocTitle(template.getTemplateId());
            contract.setTemplateId(template.getTemplateId());
            contract.setDownloadUrl(resp.getDownloadUrl());
            contract.setViewpdfUrl(resp.getViewPdfUrl());
            contract.setFddId(req.getFddId());
            contract.setStatus(resp.getCode());
            contract.setMsg(resp.getDesc());
            contract.setCreateTime(new Date());
            contract.setType(req.getType());
            contract.setActivityId(req.getActivityId());
            contract.setPartyId(Integer.valueOf(req.getPartyId()==null?"0":req.getPartyId()));
            gatewayFddGenerateContractDao.insert(contract);

        }catch (Exception e){

            //发送异常邮件
            sendExceptionEmail(ExceptionEmailEnum.IMPORTANT_LEVEL.SERIOUS,ExceptionEmailEnum.MODULE_TYPE.SIGNATURE,"法大大保存合同信息",JSON.toJSONString(req),e);
            logger.error("生成合同保存记录异常，异常信息为",e);
        }


    }

    /**
     * 合同生成参数返回封装
     *
     */
    private GenerateContractResp getGenerateContractResp(String responseStr,String contractId) {

        GenerateContractResp resp = new GenerateContractResp();

        JSONObject obj=JSON.parseObject(responseStr);

        String code = obj.getString("code");

        resp.setCode(code);
        resp.setDesc(obj.getString("msg"));

        if("1000".equals(code)){
            resp.setCode(ApiCallResult.SUCCESS.getCode());
            resp.setDownloadUrl(obj.getString("download_url"));
            resp.setViewPdfUrl(obj.getString("viewpdf_url"));
            resp.setContractId(contractId);
        }

        return resp;



    }

    /**
     *
     *获取模板信息
     *
     */
    private GatewayFddTemplate getTemplateInfo(String type) {

        GatewayFddTemplateCondition condition= new GatewayFddTemplateCondition();

        condition.setType(FddEnums.SING_TYPE_DB.getDesc(type));
        condition.setIsValid("1");//是否使用标志

        return gatewayFddTemplateDao.selectFirst(condition);
    }

    /**
     *
     * 根据类型组装模板参数
     */
    private String getParameterMap(String type, Object param) {

        String parameter;


        if(FddEnums.SING_TYPE.DELEGATION.getType().equals(type)){

            parameter = getDelegationParam(param);

        }else if(FddEnums.SING_TYPE.ADDITIONAL.getType().equals(type)){

            parameter = getAdditionalParam(param);

        }else if(FddEnums.SING_TYPE.DEAL.getType().equals(type)){

            parameter = getDealParam(param);

        }else if (FddEnums.SING_TYPE.ENROLLING_DELEGATION.getType().equals(type)){

            parameter = getEnrollingDelegationParam(param);

        }else if (FddEnums.SING_TYPE.BANK_DELEGATION.getType().equals(type)){

            parameter = getBankDelegationParam(param);

            //线下委托模板
        }else if (FddEnums.SING_TYPE.OFFLINE_DELEGATION.getType().equals(type)){

            parameter = getOfflineDelegationParam(param,FddEnums.SING_TYPE.OFFLINE_DELEGATION.getType());

            //债权模板
        }else if (FddEnums.SING_TYPE.ZHAIQUAN_DELEGATION.getType().equals(type)){

            parameter = getCreditDelegationParam(param);

            //物权模板
        }else if (FddEnums.SING_TYPE.WUQUAN_DELEGATION.getType().equals(type)){

            parameter = getRealDelegationParam(param);
        //尽调报告销售授权委托书
        }else if (FddEnums.SING_TYPE.JINDIAO_DELEGATION.getType().equals(type)){

            parameter = getDueReportParam(param);
        //尽职调查授权委托书
        }else if (FddEnums.SING_TYPE.JINZI_DELEGATION.getType().equals(type)){

            parameter = getDueDiligenceParam(param);
        //三方协议
        }else if (FddEnums.SING_TYPE.SANFANG_DELEGATION.getType().equals(type)){

            parameter = getTripleAgreementParam(param);

        }else if (FddEnums.SING_TYPE.SERVICE_ADVISORY.getType().equals(type)){

            parameter = getServiceAdvisoryParam(param);

        }else if (FddEnums.SING_TYPE.BANK_OFFLINE_DELEGATION.getType().equals(type)){

            parameter = getOfflineDelegationParam(param,FddEnums.SING_TYPE.BANK_OFFLINE_DELEGATION.getType());

        }else if (FddEnums.SING_TYPE.LEASE_DELEGATION.getType().equals(type)){

            parameter = getLeaseDelegationParam(param);

            //租赁权成交确认书
        }else if (FddEnums.SING_TYPE.LEASE_DEAL.getType().equals(type)){

            parameter = getLeaseDealParam(param);

            //租赁协议无证版
        }else if (FddEnums.SING_TYPE.LEASE_WITHOUT_LICENSE.getType().equals(type)){

            parameter = getLeaseWithoutLicenseParam(param);

            //租赁协议有证版
        }else if (FddEnums.SING_TYPE.LEASE_HAS_LICENSE.getType().equals(type)){

            parameter = getLeaseHasLicenseParam(param);

        }else{
            throw new BusinessException("未定义合同类型！");
        }

        return  parameter;


    }



    /**
     *
     * 租赁权协议 无证版
     */
    private String getLeaseHasLicenseParam(Object param) {


        LeaseHasLicenseReq req = (LeaseHasLicenseReq)param;

        JSONObject map = new JSONObject();
        map.put("asset_code",req.getAssetCode());
        map.put("activity_code",req.getActivityCode());
        map.put("seller",req.getSeller());
        map.put("buyer",req.getBuyer());
        map.put("address",req.getAddress());
        map.put("structure",req.getStructure());
        map.put("area",req.getArea());
        map.put("use",req.getUse());
        map.put("business_project",req.getBusinessProject());
        map.put("lease_years",req.getLeaseYears());
        map.put("start_years",DateUtil.formatStrDate(req.getLeaseStartTime(),Calendar.YEAR));
        map.put("start_mon",DateUtil.formatStrDate(req.getLeaseStartTime(),Calendar.MONTH));
        map.put("start_day",DateUtil.formatStrDate(req.getLeaseStartTime(),Calendar.DATE));
        map.put("end_years",DateUtil.formatStrDate(req.getLeaseEndTime(),Calendar.YEAR));
        map.put("end_mon",DateUtil.formatStrDate(req.getLeaseEndTime(),Calendar.MONTH));
        map.put("end_day",DateUtil.formatStrDate(req.getLeaseEndTime(),Calendar.DATE));
        map.put("deal_amount",req.getDeal_amount());
        map.put("increase",req.getIncrease());
        map.put("pay_way",req.getPayWay());
        map.put("rent",req.getRent());
        map.put("rent_chn",req.getRentChn());
        map.put("deposit",req.getDeposit());
        map.put("deposit_chn",req.getDepositChn());
        map.put("total_amount",req.getTotalAmount());
        map.put("total_amount_chn",req.getTotalAmountChn());
        map.put("side",req.getSide());
        map.put("supplement",req.getSupplement());
        map.put("seller_address",req.getSellerAddress());
        map.put("buyer_address",req.getBuyerAddress());
        map.put("user",req.getUser());
        map.put("buyer_num",req.getBuyerNum());
        map.put("seller_phone",req.getSellerPhone());
        map.put("buyer_phone",req.getBuyerPhone());
        map.put("sign_years",DateUtil.formatStrDate(DateUtil.getTodayString(),Calendar.YEAR));
        map.put("sign_mon",DateUtil.formatStrDate(DateUtil.getTodayString(),Calendar.MONTH));
        map.put("sign_day",DateUtil.formatStrDate(DateUtil.getTodayString(),Calendar.DATE));


        return map.toString();
    }




    /**
     *
     * 租赁权协议 无证版
     */
    private String getLeaseWithoutLicenseParam(Object param) {


        LeaseWithoutLicenseReq req = (LeaseWithoutLicenseReq)param;

        JSONObject map = new JSONObject();
        map.put("asset_code",req.getAssetCode());
        map.put("activity_code",req.getActivityCode());
        map.put("seller",req.getSeller());
        map.put("buyer",req.getBuyer());
        map.put("address",req.getAddress());
        map.put("structure",req.getStructure());
        map.put("area",req.getArea());
        map.put("use",req.getUse());
        map.put("business_project",req.getBusinessProject());
        map.put("lease_years",req.getLeaseYears());
        map.put("start_years",DateUtil.formatStrDate(req.getLeaseStartTime(),Calendar.YEAR));
        map.put("start_mon",DateUtil.formatStrDate(req.getLeaseStartTime(),Calendar.MONTH));
        map.put("start_day",DateUtil.formatStrDate(req.getLeaseStartTime(),Calendar.DATE));
        map.put("end_years",DateUtil.formatStrDate(req.getLeaseEndTime(),Calendar.YEAR));
        map.put("end_mon",DateUtil.formatStrDate(req.getLeaseEndTime(),Calendar.MONTH));
        map.put("end_day",DateUtil.formatStrDate(req.getLeaseEndTime(),Calendar.DATE));
        map.put("deal_amount",req.getDeal_amount());
        map.put("increase",req.getIncrease());
        map.put("pay_way",req.getPayWay());
        map.put("rent",req.getRent());
        map.put("rent_chn",req.getRentChn());
        map.put("deposit",req.getDeposit());
        map.put("deposit_chn",req.getDepositChn());
        map.put("total_amount",req.getTotalAmount());
        map.put("total_amount_chn",req.getTotalAmountChn());
        map.put("side",req.getSide());
        map.put("supplement",req.getSupplement());
        map.put("seller_address",req.getSellerAddress());
        map.put("buyer_address",req.getBuyerAddress());
        map.put("user",req.getUser());
        map.put("buyer_num",req.getBuyerNum());
        map.put("seller_phone",req.getSellerPhone());
        map.put("buyer_phone",req.getBuyerPhone());
        map.put("sign_years",DateUtil.formatStrDate(DateUtil.getTodayString(),Calendar.YEAR));
        map.put("sign_mon",DateUtil.formatStrDate(DateUtil.getTodayString(),Calendar.MONTH));
        map.put("sign_day",DateUtil.formatStrDate(DateUtil.getTodayString(),Calendar.DATE));

        return map.toString();
    }








    /**
     *
     * 租赁权成交确认书
     */
    private String getLeaseDealParam(Object param) {


        LeaseGenerateDealReq req = (LeaseGenerateDealReq)param;

        JSONObject map = new JSONObject();

        map.put("seller",req.getSeller());
        map.put("activity_code",req.getActivityCode());
        map.put("auction_firm",req.getAuctionFirm());
        map.put("buyer",req.getBuyer());
        map.put("union_auction_firm",req.getUnionAuctionFirm());
        map.put("auction_period",req.getAuctionPeriod());
        map.put("order_time",req.getOrderTime());
        map.put("lot_code",req.getLotCode());
        map.put("hammer_price",req.getHammerPrice());
        map.put("lot_name",req.getLotName());
        //map.put("total_amount_chn",req.getTotalAmountChn());
        map.put("total_amount",req.getTotalAmount());
        map.put("payment_period",req.getPaymentPeriod());
        map.put("deal_amount",req.getDealAmount());
        map.put("seller_percent",req.getSellerPercent());
        map.put("buyer_percent",req.getBuyerPercent());
        map.put("seller_commission",req.getSellerCommission());
        map.put("buyer_commission",req.getBuyerCommission());
        map.put("deposit",req.getDeposit());
        map.put("deposit_code",req.getDepositCode());
        map.put("discount",req.getDiscount()+"%");


        return map.toString();
    }










    /**
     *
     * 租赁权委托合同
     */
    private String getLeaseDelegationParam(Object param) {

        GenerateLeaseDelegationReq req = (GenerateLeaseDelegationReq)param;

        JSONObject map = new JSONObject();

        map.put("activity_code",req.getActivityCode());
        map.put("seller",req.getSeller());
        map.put("web_name",gatewayProperties.getWebName());
        //map.put("activity_name",req.getLotName());
        map.put("activity_name",getLeaseYears(req));
        map.put("seller_type",req.getAuctionMethod());
        map.put("keep_price",NumberFormatUtils.getTenThousandOfANumber(Double.valueOf(req.getReservePrice())));
        map.put("start_years",DateUtil.formatStrDate(req.getBeginTime(),Calendar.YEAR));
        map.put("start_mon",DateUtil.formatStrDate(req.getBeginTime(),Calendar.MONTH));
        map.put("start_date",DateUtil.formatStrDate(req.getBeginTime(),Calendar.DATE));
        map.put("start_hour",DateUtil.formatStrDate(req.getBeginTime(),Calendar.HOUR_OF_DAY));
        map.put("end_year",DateUtil.formatStrDate(req.getEndTime(),Calendar.YEAR));
        map.put("end_month",DateUtil.formatStrDate(req.getEndTime(),Calendar.MONTH));
        map.put("end_date",DateUtil.formatStrDate(req.getEndTime(),Calendar.DATE));
        map.put("end_hour",DateUtil.formatStrDate(req.getEndTime(),Calendar.HOUR_OF_DAY));
        map.put("seller_id",req.getSellerIdNumber());
        map.put("seller_address",req.getSellerAddress());
        map.put("seller_phone",req.getSellerPhone());
        map.put("seller_legal_rep",req.getSellerLegalRep());
        map.put("bank_name",req.getBankName());
        map.put("bank_acc_name",req.getBankAccountName());
        map.put("bank_acc_no",req.getBankAccount());
        map.put("company_address",req.getBranchBankName());

        map.put("entrust_name",req.getEntrustName());
        map.put("entrust_phone",req.getEntrustPhone());
        map.put("web",gatewayProperties.getWebName());
        map.put("web_address",gatewayProperties.getWebAddress());
        map.put("web_legal_name",gatewayProperties.getWebAgency());
        map.put("web_phone",gatewayProperties.getWebPhone());

        return  map.toString();
    }

    private String getLeaseYears(GenerateLeaseDelegationReq req) {

        int leaseDay = DateUtil.getMargin(req.getLeaseEndTime(),req.getLeaseStartTime());

        return String.valueOf(Math.round((float)leaseDay/365));

     }


    /**
     *
     *咨询服务合同
     */
    private String getServiceAdvisoryParam(Object param) {

        GenerateServiceAdvisoryReq req = (GenerateServiceAdvisoryReq)param;
        JSONObject map = new JSONObject();
        map.put("user",req.getUser());
        map.put("user_address",req.getUserAddress());
        map.put("user_phone",req.getUserPhone());
        map.put("amount",req.getAmount());
        map.put("amount_upper",req.getAmountUpper());
        map.put("year",DateUtil.formatStrDate(DateUtil.getTodayString(),Calendar.YEAR));
        map.put("mon",DateUtil.formatStrDate(DateUtil.getTodayString(),Calendar.MONTH));
        map.put("date",DateUtil.formatStrDate(DateUtil.getTodayString(),Calendar.DATE));
        return map.toString();
    }

    private String getTripleAgreementParam(Object param) {

        GenerateServiceTripleAgreementReq req = (GenerateServiceTripleAgreementReq)param;

        JSONObject map = new JSONObject();

        map.put("seller",req.getSeller());

        map.put("agency",req.getLawFirm());
        map.put("entrust_sign_year",DateUtil.formatStrDate(req.getEntrustSignTime(),Calendar.YEAR));
        map.put("entrust_sign_mon",DateUtil.formatStrDate(req.getEntrustSignTime(),Calendar.MONTH));
        map.put("entrust_sign_date",DateUtil.formatStrDate(req.getEntrustSignTime(),Calendar.DATE));
        map.put("entrust_code",req.getEntrustCode());
        map.put("due_sign_year",DateUtil.formatStrDate(req.getDueSignTime(),Calendar.YEAR));
        map.put("due_sign_mon",DateUtil.formatStrDate(req.getDueSignTime(),Calendar.MONTH));
        map.put("due_sign_date",DateUtil.formatStrDate(req.getDueSignTime(),Calendar.DATE));
        map.put("due_code",req.getDueCode());
        map.put("activity_name",req.getActivityName());
        map.put("sign_year",DateUtil.formatStrDate(DateUtil.getTodayString(),Calendar.YEAR));
        map.put("sign_mon",DateUtil.formatStrDate(DateUtil.getTodayString(),Calendar.MONTH));
        map.put("sign_date",DateUtil.formatStrDate(DateUtil.getTodayString(),Calendar.DATE));
        return  map.toString();
    }

    private String getDueDiligenceParam(Object param) {


        GenerateServiceDueDiligenceReq req = (GenerateServiceDueDiligenceReq)param;


        JSONObject map = new JSONObject();

        map.put("seller",req.getSeller());
        map.put("agency",req.getLawFirm());
        map.put("begin_year",DateUtil.formatStrDate(req.getBeginTime(),Calendar.YEAR));
        map.put("begin_mon",DateUtil.formatStrDate(req.getBeginTime(),Calendar.MONTH));
        map.put("begin_date",DateUtil.formatStrDate(req.getBeginTime(),Calendar.DATE));

        map.put("end_year",DateUtil.formatStrDate(req.getEndTime(),Calendar.YEAR));
        map.put("end_mon",DateUtil.formatStrDate(req.getEndTime(),Calendar.MONTH));
        map.put("end_date",DateUtil.formatStrDate(req.getEndTime(),Calendar.DATE));

        map.put("activity_name",req.getActivityName());
        map.put("entrust_begin_year",DateUtil.formatStrDate(req.getEntrustBeginTime(),Calendar.YEAR));
        map.put("entrust_begin_mon",DateUtil.formatStrDate(req.getEntrustBeginTime(),Calendar.MONTH));
        map.put("entrust_begin_date",DateUtil.formatStrDate(req.getEntrustBeginTime(),Calendar.DATE));
        map.put("entrust_end_year",DateUtil.formatStrDate(req.getEntrustEndTime(),Calendar.YEAR));
        map.put("entrust_end_mon",DateUtil.formatStrDate(req.getEntrustEndTime(),Calendar.MONTH));
        map.put("entrust_end_date",DateUtil.formatStrDate(req.getEntrustEndTime(),Calendar.DATE));


        return  map.toString();
    }

    private String getDueReportParam(Object param) {

        GenerateServiceDueReportReq req = (GenerateServiceDueReportReq)param;


        JSONObject map = new JSONObject();

         map.put("seller",req.getSeller());

        map.put("activity_name",req.getActivityName());
         return  map.toString();


    }

    /**
     *
     *物权模板参数组装
     */
    private String getRealDelegationParam(Object param) {

        GenerateRealDelegationReq req = (GenerateRealDelegationReq)param;

        JSONObject map = new JSONObject();

        map.put("activity_code",req.getActivityCode());
        map.put("seller",req.getSeller());
        map.put("start_years",DateUtil.formatStrDate(req.getBeginTime(),Calendar.YEAR));
        map.put("start_mon",DateUtil.formatStrDate(req.getBeginTime(),Calendar.MONTH));
        map.put("start_date",DateUtil.formatStrDate(req.getBeginTime(),Calendar.DATE));
        map.put("start_hour",DateUtil.formatStrDate(req.getBeginTime(),Calendar.HOUR_OF_DAY));
        map.put("end_year",DateUtil.formatStrDate(req.getEndTime(),Calendar.YEAR));
        map.put("end_month",DateUtil.formatStrDate(req.getEndTime(),Calendar.MONTH));
        map.put("end_date",DateUtil.formatStrDate(req.getEndTime(),Calendar.DATE));
        map.put("end_hour",DateUtil.formatStrDate(req.getEndTime(),Calendar.HOUR_OF_DAY));
        map.put("seller_address",req.getSellerAddress());
        map.put("seller_phone",req.getSellerPhone());
        map.put("cost",req.getCost());
        map.put("activity_name",req.getActivityName());
        map.put("reference",req.getReference());
        map.put("web",gatewayProperties.getWebName());
        map.put("web_adress",gatewayProperties.getWebAddress());
        map.put("web_agency",gatewayProperties.getWebAgency());
        map.put("web_phone",gatewayProperties.getWebPhone());
        map.put("sign_year",DateUtil.formatStrDate(DateUtil.getTodayString(),Calendar.YEAR));
        map.put("sign_mon",DateUtil.formatStrDate(DateUtil.getTodayString(),Calendar.MONTH));
        map.put("sign_date",DateUtil.formatStrDate(DateUtil.getTodayString(),Calendar.DATE));
        return  map.toString();
    }

    /**
     *
     * 债权模板参数
     */
    private String getCreditDelegationParam(Object param) {
        GenerateCreditDelegationReq req = (GenerateCreditDelegationReq)param;

        JSONObject map = new JSONObject();
        map.put("activity_code",req.getActivityCode());
        map.put("seller",req.getSeller());
        map.put("activity_name",req.getActivityName());
        map.put("principal",req.getPrincipal());
        map.put("interest",req.getInterest());
        map.put("cost",req.getCost());
        map.put("begin_year",DateUtil.formatStrDate(req.getBeginTime(),Calendar.YEAR));
        map.put("begin_mon",DateUtil.formatStrDate(req.getBeginTime(),Calendar.MONTH));
        map.put("begin_date",DateUtil.formatStrDate(req.getBeginTime(),Calendar.DATE));
        map.put("begin_hour",DateUtil.formatStrDate(req.getBeginTime(),Calendar.HOUR_OF_DAY));
        map.put("end_year",DateUtil.formatStrDate(req.getEndTime(),Calendar.YEAR));
        map.put("end_mon",DateUtil.formatStrDate(req.getEndTime(),Calendar.MONTH));
        map.put("end_date",DateUtil.formatStrDate(req.getEndTime(),Calendar.DATE));
        map.put("end_hour",DateUtil.formatStrDate(req.getEndTime(),Calendar.HOUR_OF_DAY));
        map.put("seller_address",req.getSellerAddress());
        map.put("seller_phone",req.getSellerPhone());
        map.put("web",gatewayProperties.getWebName());
        map.put("web_agency",gatewayProperties.getWebAgency());
        map.put("web_adress",gatewayProperties.getWebAddress());
        map.put("web_phone",gatewayProperties.getWebPhone());
        map.put("sign_year",DateUtil.formatStrDate(DateUtil.getTodayString(),Calendar.YEAR));
        map.put("sign_mon",DateUtil.formatStrDate(DateUtil.getTodayString(),Calendar.MONTH));
        map.put("sign_date",DateUtil.formatStrDate(DateUtil.getTodayString(),Calendar.DATE));




        return  map.toString();

    }

    /**
     *
     * 线下委托模板
     */
    private String getOfflineDelegationParam(Object param,String type) {


        GenerateOfflineDelegationReq req = (GenerateOfflineDelegationReq)param;

        JSONObject map = new JSONObject();

        map.put("activity_code",req.getActivityCode());
        map.put("seller",req.getSeller());
        map.put("agency",req.getAuctionFirm());
        map.put("web_name",gatewayProperties.getWebName());
        map.put("activity_name",req.getLotName());
        map.put("seller_type",req.getAuctionMethod());
        map.put("keep_price",req.getReservePrice()+"元");
        map.put("start_years",DateUtil.formatStrDate(req.getBeginTime(),Calendar.YEAR));
        map.put("start_mon",DateUtil.formatStrDate(req.getBeginTime(),Calendar.MONTH));
        map.put("start_date",DateUtil.formatStrDate(req.getBeginTime(),Calendar.DATE));
        map.put("start_hour",DateUtil.formatStrDate(req.getBeginTime(),Calendar.HOUR_OF_DAY));
        map.put("end_year",DateUtil.formatStrDate(req.getEndTime(),Calendar.YEAR));
        map.put("end_month",DateUtil.formatStrDate(req.getEndTime(),Calendar.MONTH));
        map.put("end_date",DateUtil.formatStrDate(req.getEndTime(),Calendar.DATE));
        map.put("end_hour",DateUtil.formatStrDate(req.getEndTime(),Calendar.HOUR_OF_DAY));
        map.put("seller_commission_rate",req.getSellerCommissionRate());
        map.put("seller_id",req.getSellerIdNumber());
        map.put("seller_address",req.getSellerAddress());
        map.put("seller_phone",req.getSellerPhone());
        map.put("seller_legal_rep",req.getSellerLegalRep());
        map.put("auction_firm_address",req.getAuctionFirmAddress());
        map.put("auction_firm_legal_rep",req.getAuctionFirmLegalRep());
        map.put("auction_firm_phone",req.getAuctionFirmPhone());
        map.put("bank_acc_name",req.getBankAccName());
        map.put("bank_acc_no",req.getBankAccNo());
        map.put("bank_name",req.getBankName());
        map.put("web",gatewayProperties.getWebName());
        map.put("web_address",gatewayProperties.getWebAddress());
        map.put("web_legal_name",gatewayProperties.getWebAgency());
        map.put("web_phone",gatewayProperties.getWebPhone());
        if(FddEnums.SING_TYPE.OFFLINE_DELEGATION.getType().equals(type)){
            map.put("company_address",gatewayProperties.getWebAddress());
            map.put("deal_date",req.getDealDate());
            map.put("confirm_date",req.getConfirmDate());
            map.put("sign_date",req.getSignDate());
        }else{
            map.put("sign_date",req.getSignDate());

        }


        return  map.toString();
    }


    /**
     *
     * 银行类委托模板
     */
    private String getBankDelegationParam(Object param) {


        GenerateBankDelegationReq req = (GenerateBankDelegationReq)param;

        JSONObject map = new JSONObject();

        map.put("activity_code",req.getActivityCode());
        map.put("seller",req.getSeller());
        map.put("auction_firm",req.getAuctionFirm());
        map.put("lot_name",req.getLotName());
        map.put("auction_method",req.getAuctionMethod());
        map.put("reserve_price",req.getReservePrice()+"元");
        map.put("begin_year",DateUtil.formatStrDate(req.getBeginTime(),Calendar.YEAR));
        map.put("begin_month",DateUtil.formatStrDate(req.getBeginTime(),Calendar.MONTH));
        map.put("begin_date",DateUtil.formatStrDate(req.getBeginTime(),Calendar.DATE));
        map.put("begin_hour",DateUtil.formatStrDate(req.getBeginTime(),Calendar.HOUR_OF_DAY));
        map.put("end_year",DateUtil.formatStrDate(req.getEndTime(),Calendar.YEAR));
        map.put("end_month",DateUtil.formatStrDate(req.getEndTime(),Calendar.MONTH));
        map.put("end_date",DateUtil.formatStrDate(req.getEndTime(),Calendar.DATE));
        map.put("end_hour",DateUtil.formatStrDate(req.getEndTime(),Calendar.HOUR_OF_DAY));
        map.put("seller_commission_rate",req.getSellerCommissionRate());
        map.put("bank_account",req.getBankAccount());
        map.put("bank_account_name",req.getBankAccountName());
        map.put("bank_name",req.getBankName());
        map.put("payment_duration",req.getPaymentDuration());
        map.put("delivery_duration",req.getDeliveryDuration());
        map.put("seller_id_number",req.getSellerIdNumber());
        map.put("seller_address",req.getSellerAddress());
        map.put("seller_phone",req.getSellerPhone());
        map.put("seller_legal_rep",req.getSellerLegalRep());
        map.put("auction_firm_address",req.getAuctionFirmAddress());
        map.put("auction_firm_legal_rep",req.getAuctionFirmLegalRep());
        map.put("auction_firm_phone",req.getAuctionFirmPhone());

        return  map.toString();
    }


    /**
     *
     * 预招商补充协议
     */
    private String getEnrollingDelegationParam(Object param) {

        GenerateEnrollingDelegationReq req = (GenerateEnrollingDelegationReq)param;

        JSONObject map = new JSONObject();

        map.put("merchant_activity_code",req.getMerchantActivityCode());
        map.put("seller",req.getSeller());
        map.put("seller_agency",req.getSellerAgency());
        map.put("merchant_object",req.getMerchantObject());
        map.put("reference_price",req.getReferencePrice());
        map.put("begin_year",DateUtil.formatStrDate(req.getBeginTime(),Calendar.YEAR));
        map.put("begin_month",DateUtil.formatStrDate(req.getBeginTime(),Calendar.MONTH));
        map.put("begin_date",DateUtil.formatStrDate(req.getBeginTime(),Calendar.DATE));
        map.put("begin_hour",DateUtil.formatStrDate(req.getBeginTime(),Calendar.HOUR_OF_DAY));
        map.put("end_year",DateUtil.formatStrDate(req.getEndTime(),Calendar.YEAR));
        map.put("end_month",DateUtil.formatStrDate(req.getEndTime(),Calendar.MONTH));
        map.put("end_date",DateUtil.formatStrDate(req.getEndTime(),Calendar.DATE));
        map.put("end_hour",DateUtil.formatStrDate(req.getEndTime(),Calendar.HOUR_OF_DAY));
        map.put("seller_address",req.getSellerAddress());
        map.put("seller_contact_name",req.getSellerContactName());
        map.put("seller_contact_phone",req.getSellerContactPhone());
        map.put("seller_agency_address",req.getSellerAgencyAddress());
        map.put("seller_agency_contact_name",req.getSellerAgencyContactName());
        map.put("seller_agency_contact_phone",req.getSellerAgencyContactPhone());
        map.put("seller_id_number",req.getSellerIdNumber());
        map.put("seller_legal_rep_name",req.getSellerLegalRepName());
        map.put("seller_legal_rep_phone",req.getSellerLegalRepPhone());
        map.put("seller_agency_legal_rep_name",req.getSellerAgencyLegalRepName());
        map.put("seller_agency_legal_rep_phone",req.getSellerAgencyLegalRepPhone());
        map.put("sign_year",DateUtil.formatStrDate(DateUtil.getTodayString(),Calendar.YEAR));
        map.put("sign_month",DateUtil.formatStrDate(DateUtil.getTodayString(),Calendar.MONTH));
        map.put("sign_date",DateUtil.formatStrDate(DateUtil.getTodayString(),Calendar.DATE));
        map.put("web",gatewayProperties.getWebName());
        map.put("web_adress",gatewayProperties.getWebAddress());
        map.put("web_agency",gatewayProperties.getWebAgency());
        map.put("web_phone",gatewayProperties.getWebPhone());
        map.put("cost",req.getCost());
        map.put("commission_percent",gatewayProperties.getCommissionPercent());



        return map.toString();
    }

    /**
     *
     * 成交确认协议
     */
    private String getDealParam(Object param) {


        GenerateDealReq req = (GenerateDealReq)param;

        JSONObject map = new JSONObject();

        map.put("seller",req.getSeller());
        map.put("activity_code",req.getActivityCode());
        map.put("auction_firm",req.getAuctionFirm());
        map.put("buyer",req.getBuyer());
        map.put("union_auction_firm",req.getUnionAuctionFirm());
        map.put("auction_period",req.getAuctionPeriod());
        map.put("order_time",req.getOrderTime());
        map.put("lot_code",req.getLotCode());
        map.put("hammer_price",req.getHammerPrice());
        map.put("lot_name",req.getLotName());
        map.put("seller_commission_rate",req.getSellerCommissionRate());
        map.put("seller_commission_fee",req.getSellerCommissionFee());
        map.put("seller_commission",req.getSellerCommission());
        map.put("buyer_commission_rate",req.getBuyerCommissionRate());
        map.put("buyer_commission_fee",req.getBuyerCommissionFee());
        map.put("buyer_commission",req.getBuyerCommission());
        map.put("total_amount_chn",req.getTotalAmountChn());
        map.put("total_amount",req.getTotalAmount());
        map.put("payment_period",req.getPaymentPeriod());


        return map.toString();
    }

    /**
     *
     * 委托补充协议参数组装
     */
    private String getAdditionalParam(Object param) {

        GenerateAdditionalReq req = (GenerateAdditionalReq)param;

        JSONObject map = new JSONObject();
        map.put("seller",req.getSeller());
        map.put("auction_firm",req.getAuctionFirm());
        map.put("sign_year",DateUtil.formatStrDate(DateUtil.getTodayString(),Calendar.YEAR));
        map.put("sign_month",DateUtil.formatStrDate(DateUtil.getTodayString(),Calendar.MONTH));
        map.put("sign_date",DateUtil.formatStrDate(DateUtil.getTodayString(),Calendar.DATE));
        map.put("activity_code",req.getActivityCode());
        map.put("reserve_price",req.getReservePrice()+"元");
        map.put("new_reserve_price",req.getNewReservePrice()+"元");
        map.put("seller_id_number",req.getSellerIdNumber());
        map.put("seller_address",req.getSellerAddress());
        map.put("seller_legal_rep",req.getSellerLegalRep());
        map.put("seller_phone",req.getSellerPhone());
        map.put("auction_firm_address",req.getAuctionFirmAddress());
        map.put("auction_firm_legal_rep",req.getAuctionFirmLegalRep());
        map.put("auction_firm_phone",req.getAuctionFirmPhone());

        return map.toString();
    }

    /**
     *
     * 委托协议参数组装
     */
    private String getDelegationParam(Object param) {

        GenerateDelegationReq req = (GenerateDelegationReq)param;

        JSONObject map = new JSONObject();

        map.put("activity_code",req.getActivityCode());
        map.put("seller",req.getPartyName());
        map.put("auction_firm",req.getAgencyName());
        map.put("lot_name",req.getActivityName());
        map.put("auction_method",req.getAuctionMethod());
        map.put("reserve_price",req.getReservePrice()+"元");
        map.put("begin_year",DateUtil.formatStrDate(req.getBeginTime(),Calendar.YEAR));
        map.put("begin_month",DateUtil.formatStrDate(req.getBeginTime(),Calendar.MONTH));
        map.put("begin_date",DateUtil.formatStrDate(req.getBeginTime(),Calendar.DATE));
        map.put("begin_hour",DateUtil.formatStrDate(req.getBeginTime(),Calendar.HOUR_OF_DAY));
        map.put("end_year",DateUtil.formatStrDate(req.getEndTime(),Calendar.YEAR));
        map.put("end_month",DateUtil.formatStrDate(req.getEndTime(),Calendar.MONTH));
        map.put("end_date",DateUtil.formatStrDate(req.getEndTime(),Calendar.DATE));
        map.put("end_hour",DateUtil.formatStrDate(req.getEndTime(),Calendar.HOUR_OF_DAY));
        map.put("seller_commission_rate",req.getSellerCommissionRate());
        map.put("payment_duration",req.getPaymentDuration());
        map.put("delivery_duration",req.getDeliveryDuration());
        map.put("seller_id_number",req.getSellerIdNumber());
        map.put("seller_address",req.getSellerAddress());
        map.put("seller_phone",req.getSellerPhone());
        map.put("seller_legal_rep",req.getSellerLegalRep());
        map.put("auction_firm_address",req.getAuctionFirmAddress());
        map.put("auction_firm_legal_rep",req.getAuctionFirmLegalRep());
        map.put("auction_firm_phone",req.getAuctionFirmPhone());

        return  map.toString();
    }


    /**
     *
     *异步保存法大大请求记录数据
     * @param busId 业务关键字
     * @param requestParam 请求参数
     * @param responseParam 返回参数
     * @param responseCode 返回码
     */
    private void insertCommRecord(String busId,String requestParam,String responseParam,String responseCode,String type) {

        new Thread(()->threadInsert(busId, requestParam,responseParam,responseCode,type)).start();

    }

    /**
     *
     *异步插入记录
     */
    private void threadInsert(String busId, String requestParam, String responseParam, String responseCode, String type) {
        try{

            GatewayFddRecord record = new GatewayFddRecord();

            record.setBusId(busId);
            record.setRequestParam(requestParam);
            record.setResponseParam(responseParam);
            record.setResponseCode(responseCode);
            record.setReqType(type);
            record.setCreateTime(new Date());

            gatewayFddRecordDao.insert(record);

        }catch (Exception e){
            logger.error("保存法大大通用请求记录异常，异常信息{}",e);
        }
    }


    /**
     * 开户的请求记录
     *
     * @param req 请求参数
     * @param resp 返回参数
      */
    private void insertMemberRecord(OpenMemberReq req, OpenMemberResp resp) {

        insertCommRecord(req.getMobile(), JSON.toJSONString(req),JSON.toJSONString(resp),resp.getCode(),"openMen");

        try{

            GatewayFddMember member = new GatewayFddMember();
            member.setIdCard(req.getId_card());
            member.setMobile(req.getMobile());
            member.setEmail(req.getEmail());
            member.setIdentType(req.getIdent_type());
            member.setFddId(resp.getCustomer_id());
            member.setMsg(resp.getDesc());
            member.setStatus(resp.getCode());
            member.setPartyId(req.getParty_id()==null?null:Integer.valueOf(req.getParty_id()));
            member.setCreateTime(new Date());
            member.setCustomerName(req.getCustomer_name());
            gatewayFddMemberDao.insert(member);

        }catch (Exception e){
            //发送异常邮件
            sendExceptionEmail(ExceptionEmailEnum.IMPORTANT_LEVEL.SERIOUS,ExceptionEmailEnum.MODULE_TYPE.SIGNATURE,"保存法大大开户记录",JSON.toJSONString(req),e);

            logger.error("保存法大大开户记录异常，异常信息为：",e);
        }


    }



    /**
     * 插入自动签署合同记录
     *
     *
     * @param req 请求参数
     * @param resp 返回的参数
      */
    private void insertSignAuto(ExtSignAutoReq req, SignatuerResp resp) {

        insertCommRecord(req.getTransaction_id(),JSON.toJSONString(req),JSON.toJSONString(resp),resp.getCode(),"signAuto");

        try{

            GatewayFddSignAuto sign = new GatewayFddSignAuto();
            sign.setContractId(req.getContract_id());
            sign.setCustomerId(req.getCustomer_id());
            sign.setDocTitle(req.getDoc_title());
            sign.setDownloadUrl(resp.getDownload_url());
            sign.setViewpdfUrl(resp.getView_pdf_url());
            sign.setCustomerId(req.getCustomer_id());
            sign.setStatus(resp.getCode());
            sign.setMsg(resp.getDesc());
            sign.setCreateTime(new Date());
            gatewayFddSignAutoDao.insert(sign);
        }catch (Exception e){
            logger.error("插入自动签章记录异常，异常信息为",e);
        }


    }



    /**
     *
     * 获取签章订单号
     * @param mark 订单号前缀
     *
     */
    private String getTransactionNum(String mark) {

        return RandomNumberGenerator.generatorMarkOrderNumber(mark,4);
    }



    @Override
    public void notifyBusQuartz() {

        try {
            logger.info("job重新发送业务通知 启动");
            //查询gateway_fdd_call_back 表
            List<GatewayFddCallBackRecord> callBackRecordList = gatewayFddCallBackRecordDao.notifyFailureList();

            for(GatewayFddCallBackRecord backRecord:callBackRecordList){
                logger.info("法大大重新发送通知业务方，通知参数为：{}");







            }
        }catch (Exception e){

            logger.info("法大大job发送业务通知异常，异常信息为：{}",e);
        }








    }

    @Override
    public FCommResp updateMemInfo(UpdateMemInfoReq req) {

        FCommResp commResp = new FCommResp();

        try {
            String resp =  FddSignatureUtils.invokeInfoChange(gatewayProperties,req.getCustomer_id(),req.getMobile(),req.getEmail());

            JSONObject respJson = JSON.parseObject(resp);
            commResp.setCode(respJson.getString("code"));
            commResp.setDesc(respJson.getString("result"));

            if("1000".equals(respJson.getString("code"))){
                commResp.setCode(ApiCallResult.SUCCESS.getCode());
                commResp.setDesc(ApiCallResult.SUCCESS.getDesc());
            }

         }catch (Exception e){
            //发送异常邮件
            sendExceptionEmail(ExceptionEmailEnum.IMPORTANT_LEVEL.SERIOUS,ExceptionEmailEnum.MODULE_TYPE.SIGNATURE,"法大大开户信息修改",JSON.toJSONString(req),e);

            commResp.setCode(ApiCallResult.EXCEPTION.getCode());
            commResp.setDesc(ApiCallResult.EXCEPTION.getDesc());
            logger.error("修改法大大信息异常，异常信息为：{}",e);
        }


        insertCommRecord(req.getCustomer_id(),JSON.toJSONString(req),JSON.toJSONString(commResp),commResp.getCode(),"updateMemCode");
        //发送提示邮件
        sendFddRemindEmail(req,commResp,commResp.getCode());

        return commResp;
    }



}
