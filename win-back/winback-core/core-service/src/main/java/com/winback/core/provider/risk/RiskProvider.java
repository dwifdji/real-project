package com.winback.core.provider.risk;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.winback.arch.common.ResponseModel;
import com.winback.arch.common.enums.ApiCallResult;
import com.winback.arch.common.utils.DateUtil;
import com.winback.core.commons.constants.RiskEnum;
import com.winback.core.condition.risk.TRiskComInfoCondition;
import com.winback.core.condition.risk.TRiskSearchRecordCondition;
import com.winback.core.condition.risk.TRiskStockInfoCondition;
import com.winback.core.facade.risk.RiskFacade;
import com.winback.core.facade.risk.req.RiskReq;
import com.winback.core.model.risk.TRiskComInfo;
import com.winback.core.model.risk.TRiskInvestment;
import com.winback.core.model.risk.TRiskSearchRecord;
import com.winback.core.model.risk.TRiskStockInfo;
import com.winback.core.service.risk.RiskService;
import com.winback.core.vo.risk.RiskComInfoVo;
import com.winback.gateway.facade.QiChaChaFacade;
import com.winback.gateway.resp.risk.RiskComInfoResp;
import com.winback.gateway.resp.risk.RiskInvestmentResp;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2019/1/22 13:50
 */
@Component
@Service(version = "1.0.0")
public class RiskProvider implements RiskFacade {

    @Autowired
    private RiskService riskService;


    @Reference(version = "1.0.0")
    private QiChaChaFacade qiChaChaFacade;


    @Override
    @Transactional
    public ResponseModel getComInfo(RiskReq.keyWordReq req) {

        req.setType(1);

        //保存搜索记录
        Integer id = saveSearchRecord(req);

        //查询是否有查询成功的记录
        if(hasRecordInfo(req)){

            return getLocalComInfo(req);
        }

        //没有记录直接查gateway数据
        RiskComInfoResp resp = qiChaChaFacade.getRiskComInfo(req.getKeyWord());
        //不为成功直接返回数据为空
        if(ApiCallResult.EXCEPTION.getCode().equals(resp.getCode())){
            return ResponseModel.fail(resp.getDesc());
        }

        if(ApiCallResult.DATA_IS_EMPTY.getCode().equals(resp.getCode())){

            return ResponseModel.fail(ApiCallResult.DATA_IS_EMPTY);
        }

        RiskComInfoVo respVo = getRespVo(resp);

        //查询成功更新搜索记录表
        updateRecord(id);

        //保存公司基本信息
        saveComInfo(req,resp);

        return ResponseModel.succ(respVo);
    }

    private void updateRecord(Integer id) {
        TRiskSearchRecord record = new TRiskSearchRecord();
        record.setUpdateTime(DateUtil.getSysTime());
        record.setId(id);
        record.setStatus(1);
        riskService.updateSearchRecord(record);
    }


    private RiskComInfoVo getRespVo(RiskComInfoResp resp) {

        RiskComInfoVo respVo = new RiskComInfoVo();

        respVo.setComName(resp.getComName());
        respVo.setAccuserNum(resp.getAccuserNum());
        respVo.setComStatus(resp.getComStatus());
        respVo.setComType(resp.getComType());
        respVo.setLoseCreditNum(resp.getLoseCreditNum());
        respVo.setDefendantNum(resp.getDefendantNum());
        respVo.setRegistArea(resp.getRegisterArea());
        respVo.setHasException(resp.getExceptionFlag()==1?"正常":"异常");
        respVo.setExecuteNum(resp.getExecuteNum());
        respVo.setRegistCapital(resp.getRegisterCapital());
        respVo.setType("公司");
        respVo.setShareholdersInfo(resp.getShareholdersInfo());

        return respVo;
    }

    /**
     *
     *保存搜索记录
     */
    private Integer saveSearchRecord(RiskReq.keyWordReq req) {

        TRiskSearchRecord record = new TRiskSearchRecord();
        record.setCreateTime(DateUtil.getSysTime());
        record.setDeleteFlag(false);
        record.setKeyWord(req.getKeyWord());
        record.setType(req.getType());
        record.setStatus(0);
        record.setOperatorName(req.getOperatorName());
        return riskService.saveSearchRecord(record);
    }

    /**
     *
     *保存搜索的公司信息
     */
    private void saveComInfo(RiskReq.keyWordReq req, RiskComInfoResp resp) {
        TRiskComInfo info = new TRiskComInfo();
        BeanUtils.copyProperties(resp,info);

        info.setCreateTime(DateUtil.getSysTime());
        info.setName(req.getKeyWord());
        info.setType(RiskEnum.RECORD_TYPE.COM_INFO.getType());
        info.setStatus(resp.getComStatus());
        riskService.saveRiskComInfo(info);

    }

    private ResponseModel getLocalComInfo(RiskReq.keyWordReq req) {

        TRiskComInfoCondition condition = new TRiskComInfoCondition();
        condition.setName(req.getKeyWord());
        condition.setDeleteFlag(false);

        TRiskComInfo resp = riskService.getRiskComInfo(condition);

        if(resp==null){
            return ResponseModel.fail(ApiCallResult.DATA_IS_EMPTY);
        }

        RiskComInfoVo respVo = new RiskComInfoVo();

        respVo.setComName(resp.getName());
        respVo.setAccuserNum(resp.getAccuserNum());
        respVo.setComStatus(resp.getStatus());
        respVo.setComType(resp.getComType());
        respVo.setLoseCreditNum(resp.getLoseCreditNum());
        respVo.setDefendantNum(resp.getDefendantNum());
        respVo.setRegistArea(resp.getRegisterArea());
        respVo.setHasException(resp.getExceptionFlag()==1?"正常":"异常");
        respVo.setExecuteNum(resp.getExecuteNum());
        respVo.setRegistCapital(resp.getRegisterCapital());
        respVo.setType("公司");

        respVo.setShareholdersInfo(resp.getShareholdersInfo());


        return ResponseModel.succ(respVo);
    }

    private Boolean hasRecordInfo(RiskReq.keyWordReq req) {

        TRiskSearchRecordCondition condition = new TRiskSearchRecordCondition();
        condition.setKeyWord(req.getKeyWord());
        condition.setType(req.getType());
        condition.setDeleteFlag(false);
        condition.setStatus(1);

        return riskService.getSearchRecord(condition).size()>0;
    }

    @Override
    public ResponseModel getInvestList(RiskReq.keyWordReq req) {

        //获取投资记录信息
        req.setType(2);

        //保存搜索记录
        Integer id = saveSearchRecord(req);

        //如果有记录直接查询表
        if(hasRecordInfo(req)){

            return getLocalInvestInfo(req);
        }

        //保存对外投资信息
        saveInvest(req,id);

         //获取信息
        return getLocalInvestInfo(req);

    }

    private void saveInvest(RiskReq.keyWordReq req,Integer id) {

        //geteway获取数据
        RiskInvestmentResp resp = qiChaChaFacade.searchInvestment(req.getKeyWord(),"1","50");

        //成功解析参数
        if(ApiCallResult.SUCCESS.getCode().equals(resp.getCode())){

            String str = resp.getJsonIno();

            //解析返回参数
            JSONObject object = JSON.parseObject(str);

            //查询成功解析参数
            if("200".equals(object.getString("Status"))) {
                //查询成功更新搜索记录表
                updateRecord(id);

                JSONArray list = object.parseArray(object.getString("Result"));
                //批量保存信息

                List<TRiskInvestment> saveList = new ArrayList<>();

                for(int i=0;i<list.size();i++) {

                    TRiskInvestment investment = new TRiskInvestment();
                    JSONObject jsonObject = list.getJSONObject(i);

                    investment.setKeyWord(req.getKeyWord());
                    investment.setComName(jsonObject.getString("Name"));
                    investment.setStatus(jsonObject.getString("Status"));
                    investment.setLegalPerson(jsonObject.getString("OperName"));
                    investment.setInvestScale(jsonObject.getString("FundedRatio"));
                    investment.setRegistCapital(jsonObject.getString("RegistCapi"));
                    investment.setSetupDate(jsonObject.getString("StartDate"));
                    investment.setDeleteFlag(false);
                    investment.setCreateTime(DateUtil.getSysTime());
                    saveList.add(investment);
                }

                if(saveList.size()>0){
                    riskService.batchRiskInvestment(saveList);

                }


            }

        }

    }

    private ResponseModel getLocalInvestInfo(RiskReq.keyWordReq req) {


        return ResponseModel.succ(riskService.getRiskInvestmentList(req));
    }

    @Override
    public ResponseModel getEquityInfo(RiskReq.keyWordReq req) {

        req.setType(3);

        //保存搜索记录
        Integer id = saveSearchRecord(req);
        //如果有记录直接查询表
        if(hasRecordInfo(req)){

            return getLocalEquityInfo(req);
        }

        JSONObject json = new JSONObject();

        json.put("info",qiChaChaFacade.getStockAnalysisData(req.getKeyWord()));

        //查询成功更新搜索记录表
        updateRecord(id);


        return ResponseModel.succ(json);
    }


    /**
     *
     *获取本地的股权穿透信息
     */
    private ResponseModel getLocalEquityInfo(RiskReq.keyWordReq req) {

        TRiskStockInfoCondition condition = new TRiskStockInfoCondition();

        condition.setKeyWord(req.getKeyWord());
        condition.setDeleteFlag(false);


        JSONObject json = new JSONObject();


        TRiskStockInfo stockInfo = riskService.getRiskStockInf(condition);

        if(stockInfo!=null){
            json.put("info",stockInfo.getStockInfo());
        }

        return ResponseModel.succ(json);

    }
}
