package com.winback.gateway.service.check.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.winback.arch.common.enums.ApiCallResult;
import com.winback.arch.common.enums.EmailEnum;
import com.winback.arch.common.utils.DateUtil;
import com.winback.arch.common.utils.ExceptionEmailUtil;
import com.winback.arch.core.sysconfig.properties.GatewayProperties;
import com.winback.gateway.condition.check.TGatewayCheckRecordCondition;
import com.winback.gateway.dao.check.TGatewayCheckRecordDao;
import com.winback.gateway.model.check.TGatewayCheckRecord;
import com.winback.gateway.service.check.QiChaChaService;
import com.winback.gateway.common.check.QiChaChaUtils;
import com.winback.gateway.common.constants.CheckEnum;
import com.winback.gateway.condition.check.TGatewayCheckRecordCondition;
import com.winback.gateway.dao.check.TGatewayCheckRecordDao;
import com.winback.gateway.model.check.TGatewayCheckRecord;
import com.winback.gateway.resp.risk.RiskComInfoResp;
import com.winback.gateway.resp.risk.RiskInvestmentResp;
import com.winback.gateway.service.check.QiChaChaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述：企查查接口实现
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2019/1/16 10:00
 */
@Slf4j
@Service
public class QiChaChaServiceImpl implements QiChaChaService {

    @Autowired
    private GatewayProperties gatewayProperties;

    @Autowired
    private TGatewayCheckRecordDao gatewayCheckRecordDao;


    @Override
    public RiskComInfoResp getRiskComInfo(String keyWord) {

        RiskComInfoResp resp = new RiskComInfoResp();

        //获取公司基本信息
        resp = getComDetailInfo(resp,keyWord);

        //当基本信息都没有时 直接返回
       if(ApiCallResult.DATA_IS_EMPTY.getCode().equals(resp.getCode())||ApiCallResult.EXCEPTION.getCode().equals(resp.getCode())){

            return resp;
        }

        //异常信息
        resp = getComExceptionInfo(resp,keyWord);

        //执行信息
        resp = getComZhiXingInfo(resp,keyWord);

        //失信信息
        resp = getComshiXinInfo(resp,keyWord);

        //裁判文书信息
        resp = getComJudgmentInfo(resp,keyWord);

        return resp;
    }

    private RiskComInfoResp getComJudgmentInfo(RiskComInfoResp resp, String keyWord) {

        String judgmentInfo =null;

        Boolean notRecord = false;

        int accuserNum = 0;

        int defendantNum = 0;

        String code = ApiCallResult.DATA_IS_EMPTY.getCode();
        try {

            //查询搜索记录
            TGatewayCheckRecord record =  getCheckRecord(CheckEnum.API_TYPE.COM_JUDGMENT.getType(),keyWord);

            notRecord =record==null;

            //裁判文书信息
            judgmentInfo = notRecord?QiChaChaUtils.searchJudgmentDoc(gatewayProperties,keyWord,"1","50"):record.getRespParam();

            //解析返回参数
            JSONObject object = JSON.parseObject(judgmentInfo);

            //查询成功解析参数
            if(querySuccess(object)){

                code = ApiCallResult.SUCCESS.getCode();

                String resultStr = object.getString("Result");


                JSONObject result =  JSON.parseObject(resultStr);

                if(result!=null){
                    JSONArray judgementList = result.parseArray(result.getString("JudgementList"));

                    for(int i=0;i<judgementList.size();i++){
                        JSONObject judgement = judgementList.getJSONObject(i);
                        //原告
                        if("0".equals(judgement.getString("CaseRole"))){

                            accuserNum =accuserNum+judgement.getInteger("Count");
                        }

                        if("1".equals(judgement.getString("CaseRole"))){
                            defendantNum =defendantNum+judgement.getInteger("Count");

                        }
                    }
                }


            }

        }catch (Exception e){
            ExceptionEmailUtil.appExceptionEmail(EmailEnum.IMPORTANT_LEVEL.SERIOUS,EmailEnum.MODULE_TYPE.CHECK, JSON.toJSONString(keyWord),"企查查获取裁判文书信息异常", null,e);

            log.error("企查查获取裁判文书信息异常，异常信息为：{}",e);
         }
        resp.setAccuserNum(String.valueOf(accuserNum));//原告笔数
        resp.setDefendantNum(String.valueOf(defendantNum));//被告笔数
        //保存记录
        if(notRecord){
            saveCheckRecord(CheckEnum.API_TYPE.COM_JUDGMENT.getType(),keyWord,judgmentInfo,code);

        }

        return resp;
    }

    private void saveCheckRecord(String type, String keyWord, String respInfo,String respCode) {

        try {
            TGatewayCheckRecord record = new TGatewayCheckRecord();
            record.setDeleteFlag(false);
            record.setType(type);
            record.setReqParam(keyWord);
            record.setRespParam(respInfo);
            record.setStatus(ApiCallResult.SUCCESS.getCode().equals(respCode)?1:0);
            record.setCreateTime(DateUtil.getSysTime());
            gatewayCheckRecordDao.insert(record);
        }catch (Exception e){

            ExceptionEmailUtil.appExceptionEmail(EmailEnum.IMPORTANT_LEVEL.SERIOUS,EmailEnum.MODULE_TYPE.CHECK, JSON.toJSONString(keyWord),"保存企查查记录异常", null,e);

            log.error("保存企查查记录异常，异常信息为：{}",e);
        }



    }

    private RiskComInfoResp getComshiXinInfo(RiskComInfoResp resp, String keyWord) {

        String shiXinInfo = null;

        resp.setLoseCreditNum("0");
        Boolean notRecord = false;

        String code = ApiCallResult.DATA_IS_EMPTY.getCode();

        try {

            //查询搜索记录
            TGatewayCheckRecord record =  getCheckRecord(CheckEnum.API_TYPE.COM_SHI_XIN.getType(),keyWord);
            notRecord = record==null;
            //失信记录
            shiXinInfo =notRecord? QiChaChaUtils.searchShiXin(gatewayProperties,keyWord,"1","10"):record.getRespParam();
            //解析返回参数
            JSONObject object = JSON.parseObject(shiXinInfo);

            //查询成功解析参数
            if(querySuccess(object)){
                code = ApiCallResult.SUCCESS.getCode();

                JSONObject pageInfo =  object.getJSONObject(object.getString("Paging"));

                if(pageInfo!=null){
                    //设置总笔数
                    resp.setLoseCreditNum(pageInfo.getString("TotalRecords"));
                }


            }

        }catch (Exception e){
            ExceptionEmailUtil.appExceptionEmail(EmailEnum.IMPORTANT_LEVEL.SERIOUS,EmailEnum.MODULE_TYPE.CHECK, JSON.toJSONString(keyWord),"企查查查询失信信息异常", null,e);

            log.error("企查查查询失信信息异常，异常信息为：{}",e);

        }


        if(notRecord){
            //保存记录
            saveCheckRecord(CheckEnum.API_TYPE.COM_SHI_XIN.getType(),keyWord,shiXinInfo,code);
        }


        return resp;
    }

    private RiskComInfoResp getComZhiXingInfo(RiskComInfoResp resp, String keyWord) {

        String zhiXingInfo =null;
        resp.setExecuteNum("0");

        Boolean notRecord = false;

        String code = ApiCallResult.DATA_IS_EMPTY.getCode();
         try {


             //查询搜索记录
             TGatewayCheckRecord record =  getCheckRecord(CheckEnum.API_TYPE.COM_ZHI_XING.getType(),keyWord);

             notRecord = record==null;
            //执行信息
            zhiXingInfo =notRecord ? QiChaChaUtils.searchZhiXing(gatewayProperties,keyWord,"1","50"):record.getRespParam();

             //解析返回参数
             JSONObject object = JSON.parseObject(zhiXingInfo);

             //查询成功解析参数
             if(querySuccess(object)){

                 code = ApiCallResult.SUCCESS.getCode();

                 JSONObject pageInfo =  object.getJSONObject(object.getString("Paging"));
                 if(pageInfo!=null){
                     //设置总笔数
                     resp.setExecuteNum(pageInfo.getString("TotalRecords"));
                 }



             }

        }catch (Exception e){

             ExceptionEmailUtil.appExceptionEmail(EmailEnum.IMPORTANT_LEVEL.SERIOUS,EmailEnum.MODULE_TYPE.CHECK, JSON.toJSONString(keyWord),"获取企查查执行信息异常", null,e);

             log.error("获取企查查执行信息异常，异常信息为：{}",e);

         }


         if(notRecord){
             //保存记录
             saveCheckRecord(CheckEnum.API_TYPE.COM_ZHI_XING.getType(),keyWord,zhiXingInfo,code);
         }


        return resp;
    }

    private RiskComInfoResp getComExceptionInfo(RiskComInfoResp resp, String keyWord) {
        String exceptionInfo = null;

        resp.setExceptionFlag(1);//异常信息

        Boolean notRecord = false;

        String code = ApiCallResult.DATA_IS_EMPTY.getCode();
        try {

            //查询搜索记录
            TGatewayCheckRecord record =  getCheckRecord(CheckEnum.API_TYPE.COM_EXCEPTION_INFO.getType(),keyWord);

            notRecord = record==null;

            //异常信息
            exceptionInfo =notRecord?QiChaChaUtils.getOpException(gatewayProperties,keyWord):record.getRespParam();

            //解析返回参数
            JSONObject object = JSON.parseObject(exceptionInfo);

            //查询成功解析参数
            if(querySuccess(object)){

                //查询成功
                code = ApiCallResult.SUCCESS.getCode();

                JSONArray  list= object.getJSONArray(object.getString("Result"));

                //异常结果大于0 返回异常信息
                if(list!=null&&list.size()>0){
                    resp.setExceptionFlag(2);
                }
            }

        }catch (Exception e){

            ExceptionEmailUtil.appExceptionEmail(EmailEnum.IMPORTANT_LEVEL.SERIOUS,EmailEnum.MODULE_TYPE.CHECK, JSON.toJSONString(keyWord),"企查查查询公司异常信息异常", null,e);


            log.error("企查查查询公司异常信息异常，异常信息为：{}",e);
         }

        //保存记录
        if(notRecord){
            saveCheckRecord(CheckEnum.API_TYPE.COM_EXCEPTION_INFO.getType(),keyWord,exceptionInfo,code);
        }

        return resp;
    }

    private RiskComInfoResp getComDetailInfo(RiskComInfoResp resp, String keyWord) {

        resp.setCode(ApiCallResult.DATA_IS_EMPTY.getCode());
        String detailInfo=null;
        Boolean notRecord = false;

        try {

            //查询搜索记录
            TGatewayCheckRecord record =  getCheckRecord(CheckEnum.API_TYPE.COM_DETAIL_INFO.getType(),keyWord);

            notRecord = record==null;

            detailInfo = notRecord?QiChaChaUtils.getDetailsByName(gatewayProperties,keyWord):record.getRespParam();

            //解析返回参数
            JSONObject json = JSON.parseObject(detailInfo);

            //当返回成功时 解析参数
            if(querySuccess(json)){
                resp.setCode(ApiCallResult.SUCCESS.getCode());

                JSONObject object = JSON.parseObject(json.getString("Result"));

                resp.setComName(object.getString("Name"));//公司名称
                resp.setComStatus(object.getString("Status"));//公司状态
                resp.setRegisterCapital(object.getString("RegistCapi"));//注册资本
                resp.setRegisterArea(object.getString("Address"));//注册地
                resp.setComType(object.getString("EconKind"));//公司类型
                resp.setShareholdersInfo(object.getString("Partners"));
            }

        }catch (Exception e){
            ExceptionEmailUtil.appExceptionEmail(EmailEnum.IMPORTANT_LEVEL.SERIOUS,EmailEnum.MODULE_TYPE.CHECK, JSON.toJSONString(keyWord),"获取公司基本信息异常", null,e);

            resp.setCode(ApiCallResult.EXCEPTION.getCode());
            resp.setDesc(ApiCallResult.EXCEPTION.getDesc());

            log.error("企查查查询公司基本信息异常，异常信息为：{}",e);
        }

        //保存记录
        if(notRecord){
            saveCheckRecord(CheckEnum.API_TYPE.COM_DETAIL_INFO.getType(),keyWord,detailInfo,resp.getCode());
        }

        return resp;
    }

    private TGatewayCheckRecord getCheckRecord(String type, String keyWord) {

        TGatewayCheckRecordCondition condition = new TGatewayCheckRecordCondition();

        condition.setReqParam(keyWord);
        condition.setDeleteFlag(false);
        condition.setStatus(1);
        condition.setType(type);
        return gatewayCheckRecordDao.selectFirst(condition);
    }

    private boolean querySuccess(JSONObject object) {

        return "200".equals(object.getString("Status"));
    }


    @Override
    public String getStockAnalysisData(String keyWord) {

        //查询搜索记录
        TGatewayCheckRecord record =  getCheckRecord(CheckEnum.API_TYPE.COM_EXCEPTION_INFO.getType(),keyWord);

        if(record!=null){
            return record.getReqParam();
        }


        String info =null;

        try {

            info = QiChaChaUtils.getStockAnalysisData(gatewayProperties,keyWord);

        }catch (Exception e){

            ExceptionEmailUtil.appExceptionEmail(EmailEnum.IMPORTANT_LEVEL.SERIOUS,EmailEnum.MODULE_TYPE.CHECK, JSON.toJSONString(keyWord),"企查查获取十层股权穿透图异常", null,e);

        }

        //保存记录
        saveCheckRecord(CheckEnum.API_TYPE.STOCK_ANALYSIS_DATA.getType(),keyWord,info,ApiCallResult.SUCCESS.getCode());

        return  info;
    }

    @Override
    public RiskInvestmentResp searchInvestment(String keyWord, String page , String pageSize) {

        RiskInvestmentResp resp = new RiskInvestmentResp();
        String investment = null;

        resp.setCode(ApiCallResult.DATA_IS_EMPTY.getCode());
        resp.setDesc(ApiCallResult.DATA_IS_EMPTY.getDesc());

        String searchRecord = keyWord+page+pageSize;

        Boolean notRecord = false;


        try {

            //查询搜索记录
            TGatewayCheckRecord record =  getCheckRecord(CheckEnum.API_TYPE.SEARCH_INVESTMENT.getType(),searchRecord);


            notRecord = record==null;

            //对外投资信息
            investment =notRecord? QiChaChaUtils.searchInvestment(gatewayProperties,keyWord,page,pageSize):record.getRespParam();

            //解析返回参数
            JSONObject object = JSON.parseObject(investment);

            //当返回成功时 解析参数
            if(querySuccess(object)){
                resp.setCode(ApiCallResult.SUCCESS.getCode());
                resp.setDesc(ApiCallResult.SUCCESS.getDesc());

                resp.setJsonIno(investment);

            }

        }catch (Exception e){
            ExceptionEmailUtil.appExceptionEmail(EmailEnum.IMPORTANT_LEVEL.SERIOUS,EmailEnum.MODULE_TYPE.CHECK, JSON.toJSONString(keyWord),"企查查获取对外投资异常", null,e);

            log.error("企查查获取对外投资异常，异常信息为：{}",e);
            resp.setCode(ApiCallResult.EXCEPTION.getCode());
            resp.setDesc(ApiCallResult.EXCEPTION.getDesc());
        }

        if(notRecord){
            //保存记录
            saveCheckRecord(CheckEnum.API_TYPE.SEARCH_INVESTMENT.getType(),searchRecord,investment,resp.getCode());
        }


        return resp;
    }
}
