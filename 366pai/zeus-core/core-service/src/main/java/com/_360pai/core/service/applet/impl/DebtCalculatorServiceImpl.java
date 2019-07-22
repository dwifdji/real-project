package com._360pai.core.service.applet.impl;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.applet.req.CalculatorReq;
import com._360pai.core.facade.applet.vo.DebtCalculator;
import com._360pai.core.facade.applet.vo.DebtCalculatorBroadcast;
import com._360pai.core.model.account.TAccountExtBind;
import com._360pai.core.model.numberJump.TDebtCalculator;
import com._360pai.core.model.numberJump.TDebtCalculatorBroadcast;
import com._360pai.core.service.applet.DebtCalculatorService;
import com._360pai.core.utils.ReqConvertUtil;
import com._360pai.core.utils.RespConvertUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static com._360pai.core.common.CoreConstants.BIG_DECIMAL_SCALE;
import static com._360pai.core.common.CoreConstants.SHOW_BIG_DECIMAL_SCALE;

/**
 * @author xdrodger
 * @Title: DebtCalculatorServiceImpl
 * @ProjectName dev2-zeus
 * @Description:
 * @date 2019-04-30 10:51
 */
@Slf4j
@Service("debtCalculatorService")
public class DebtCalculatorServiceImpl extends CalculatorServiceImpl implements DebtCalculatorService {


    @Override
    public ResponseModel debtCalculator(CalculatorReq.DebtCalculatorReq req) {
        if (req.getLiquidationAmount() == null || req.getTransferAmount() == null) {
            throw new BusinessException(ApiCallResult.EMPTY);
        }
        if (req.getWithFundingPeriod() == null) {
            throw new BusinessException(ApiCallResult.EMPTY);
        }
        if (req.getWithFundingAnnualizedRate() == null || req.getWithFundingAnnualizedRate().compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException(ApiCallResult.EMPTY);
        }
        if (req.getDisposalAnnualizedRate() == null || req.getDisposalAnnualizedRate().compareTo(BigDecimal.ZERO) < 0
                || req.getDisposalPeriod() == null) {
            throw new BusinessException(ApiCallResult.EMPTY);
        }
        BigDecimal      gpAmount          = req.getGpAmount() == null || req.getGpAmount().intValue() == 0 ? BigDecimal.ONE : req.getGpAmount();
        BigDecimal      lpAmount          = req.getLpAmount() == null ? BigDecimal.ZERO : req.getLpAmount();
        BigDecimal      liquidationAmount = req.getLiquidationAmount();
        TDebtCalculator calculator        = ReqConvertUtil.convertToTDebtCalculator(req);
        // 资金成本=LP配资金额*配资成本费率（年）/12*配资周期（月）
        BigDecimal withFundingAmount = lpAmount
                .multiply(req.getWithFundingAnnualizedRate())
                .divide(new BigDecimal(100), BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_DOWN)
                .divide(new BigDecimal(12), BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_DOWN)
                .multiply(req.getWithFundingPeriod());
        // 处置成本=转让价*处置成本费率（年）/12*处置周期（月）
        BigDecimal disposalAmount = req.getTransferAmount()
                .multiply(req.getDisposalAnnualizedRate())
                .divide(new BigDecimal(100), BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_DOWN)
                .divide(new BigDecimal(12), BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_DOWN)
                .multiply(req.getDisposalPeriod());
        // 尽调成本
        BigDecimal dueDiligenceAmount = req.getDueDiligenceAmount() == null ? BigDecimal.ZERO : req.getDueDiligenceAmount();
        // 杂费
        BigDecimal miscAmount = req.getMiscAmount() == null ? BigDecimal.ZERO : req.getMiscAmount();

        // 实际收益金额=清收目标-资金成本-处置成本-尽调成本-杂费-转让价
        BigDecimal estimatedIncome = liquidationAmount
                .subtract(withFundingAmount)
                .subtract(disposalAmount)
                .subtract(dueDiligenceAmount)
                .subtract(miscAmount)
                .subtract(req.getTransferAmount());
        // 投资（年）收益率=实际收益金额/GP自有金额/处置周期（月）*12
        BigDecimal annualizedReturn = estimatedIncome
                .divide(gpAmount.equals(BigDecimal.ZERO) ? BigDecimal.ONE : gpAmount, BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_DOWN)
                .divide(req.getDisposalPeriod(), BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_DOWN)
                .multiply(new BigDecimal(12));

        // 投入成本总计
        BigDecimal totalCost = req.getTransferAmount()
                .add(withFundingAmount)
                .add(disposalAmount)
                .add(dueDiligenceAmount)
                .add(miscAmount);

        calculator.setWithFundingAmount(withFundingAmount);
        calculator.setDisposalAmount(disposalAmount);
        calculator.setAnnualizedReturn(annualizedReturn.multiply(new BigDecimal(100)));
        calculator.setEstimatedIncome(estimatedIncome);
        calculator.setTotalCost(totalCost);
        calculator.setDueDiligenceAmount(dueDiligenceAmount);
        calculator.setMiscAmount(miscAmount);
        int result = debtCalculatorDao.insert(calculator);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        Map<String, Object> data = new HashMap<>(1);
        data.put("id", calculator.getId());
        return ResponseModel.succ(data);
    }

    /**
     * 需求金额=转让价-自有金额
     * <p>
     * 实际收益金额=清收目标-资金成本-处置成本-尽调成本-杂费-转让价
     * <p>
     * 资金成本=需求金额*配资成本费率（年）/12*配资周期（月）
     * <p>
     * 处置成本=清收目标*处置成本费率
     * <p>
     * 尽调成本=用户自主填写
     * <p>
     * 杂费=用户自主填写
     * <p>
     * 成本小计=资金成本+处置成本+尽调成本+杂费
     * <p>
     * 配资金额年收益率=配资成本费率（年）
     * <p>
     * 劣后资金年收益率=实际收益金额/自有资金/配资周期（月）*12
     *
     * @param req
     * @return
     */
    @Override
    public ResponseModel debtCalculatorV2(CalculatorReq.DebtCalculatorReq req) {
        log.info("债权计算器req: {}", req);

        //债权本金
        BigDecimal debtPrincipal = req.getDebtPrincipal();
        // 债权利息
        BigDecimal debtInterest = req.getDebtInterest();
        //债权最高额
        BigDecimal maximumDebt = req.getMaximumDebt();
        //清收目标金额
        BigDecimal liquidationAmount = req.getLiquidationAmount();
        //转让价格
        BigDecimal transferAmount = req.getTransferAmount();
        //自有资金GP
        BigDecimal gpAmount = req.getGpAmount();
        //需要配资LP金额
        BigDecimal lpAmount = req.getLpAmount();
        // 尽调成本
        BigDecimal dueDiligenceAmount = req.getDueDiligenceAmount();
        // 杂费
        BigDecimal miscAmount = req.getMiscAmount();


        TDebtCalculator calculator = ReqConvertUtil.convertToTDebtCalculator(req);

        //需求金额=转让价-自有金额
        //实际收益金额=清收目标-资金成本-处置成本-尽调成本-杂费-转让价
        //资金成本=需求金额*配资成本费率（年）/12*配资周期（月）

        //  资金成本=(转让价-自有金额)*配资成本费率（年）/12*配资周期（月）

        BigDecimal withFundingAmount = transferAmount.subtract(gpAmount)
                .multiply(req.getWithFundingAnnualizedRate())
                .divide(new BigDecimal(100), BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_DOWN)
                .divide(new BigDecimal(12), BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_DOWN)
                .multiply(req.getWithFundingPeriod());

        //处置成本=清收目标*处置成本费率
        BigDecimal disposalAmount = liquidationAmount
                .multiply(req.getDisposalAnnualizedRate())
                .divide(new BigDecimal(100), BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_DOWN);


        // 实际收益金额=清收目标-资金成本-处置成本-尽调成本-杂费-转让价
        BigDecimal estimatedIncome = liquidationAmount
                .subtract(withFundingAmount)
                .subtract(disposalAmount)
                .subtract(dueDiligenceAmount)
                .subtract(miscAmount)
                .subtract(transferAmount);

        //劣后资金年收益率=实际收益金额/自有资金/处置周期（月）*12
        BigDecimal annualizedReturn = estimatedIncome
                .divide(gpAmount.equals(BigDecimal.ZERO) ? BigDecimal.ONE : gpAmount, BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_DOWN)
                .divide(req.getDisposalPeriod().equals(BigDecimal.ZERO) ? BigDecimal.ONE : req.getDisposalPeriod(), BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_DOWN)
                .multiply(new BigDecimal("12"));

        // 成本小计=资金成本+处置成本+尽调成本+杂费
        BigDecimal totalCost = withFundingAmount
                .add(disposalAmount)
                .add(dueDiligenceAmount)
                .add(miscAmount);

        calculator.setWithFundingAmount(withFundingAmount);
        calculator.setDisposalAmount(disposalAmount);
        calculator.setAnnualizedReturn(annualizedReturn.multiply(new BigDecimal(100)));
        calculator.setEstimatedIncome(estimatedIncome);
        calculator.setTotalCost(totalCost);
        calculator.setDueDiligenceAmount(dueDiligenceAmount);
        calculator.setMiscAmount(miscAmount);
        int result = debtCalculatorDao.insert(calculator);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        Map<String, Object> data = new HashMap<>(1);
        data.put("id", calculator.getId());
        return ResponseModel.succ(data);
    }


    @Override
    public ResponseModel getDeptCalculatorBroadcastV2(CalculatorReq.QueryReq req) {
        TDebtCalculator calculator = debtCalculatorDao.selectById(req.getId());
        if (calculator == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        PageInfo<TDebtCalculatorBroadcast> pageInfo   = debtCalculatorBroadcastDao.findByCalculatorId(req.getPage(), req.getPerPage(), req.getId());
        List<DebtCalculatorBroadcast>      resultList = new ArrayList<>();
        for (TDebtCalculatorBroadcast item : pageInfo.getList()) {
            if (!item.getReadFlag()) {
                item.setReadFlag(true);
                debtCalculatorBroadcastDao.updateById(item);
            }
            DebtCalculatorBroadcast vo = new DebtCalculatorBroadcast();
            BeanUtils.copyProperties(item, vo);
            vo.setProjectName(calculator.getProjectName());
            vo.setDays(calculator.getDisposalPeriod().multiply(new BigDecimal(30)).subtract(new BigDecimal(vo.getDays())).intValue());
            resultList.add(vo);
        }

        TDebtCalculatorBroadcast tDebtCalculatorBroadcast = debtCalculatorBroadcastDao.findLatestByCalculatorId(req.getId());

        Map<String, Object> result = Maps.newHashMap();
        result.put("projectName", StringUtils.isBlank(calculator.getProjectName()) ? "暂无" : calculator.getProjectName());
        result.put("projectManager", StringUtils.isBlank(calculator.getProjectManager()) ? "暂无" : calculator.getProjectManager());
        result.put("closedFlag", calculator.getClosedFlag());
        result.put("list", resultList);
        result.put("total", pageInfo.getTotal());
        result.put("hasNextPage", pageInfo.isHasNextPage());
//        result.put("days", tDebtCalculatorBroadcast == null ? 0 : tDebtCalculatorBroadcast.getDays());
        result.put("days", calculator.getDisposalPeriod().multiply(new BigDecimal(30)).subtract(tDebtCalculatorBroadcast == null ? BigDecimal.ZERO : new BigDecimal(tDebtCalculatorBroadcast.getDays())));
        result.put("remainAnnualizedReturn", tDebtCalculatorBroadcast == null ? 0 : tDebtCalculatorBroadcast.getAnnualizedReturn());
        result.put("remainIncome", tDebtCalculatorBroadcast == null ? 0 : tDebtCalculatorBroadcast.getIncome());
        return ResponseModel.succ(result);
    }

    @Override
    public ResponseModel getDebtCalculatorDetail(CalculatorReq.QueryReq req) {
        TDebtCalculator calculator = debtCalculatorDao.selectById(req.getId());
        if (calculator == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        DebtCalculator vo = RespConvertUtil.convertToDebtCalculator(calculator);
        return ResponseModel.succ(vo);
    }

    @Override
    public ResponseModel getDeptCalculatorBroadcast(CalculatorReq.QueryReq req) {
        TDebtCalculator calculator = debtCalculatorDao.selectById(req.getId());
        if (calculator == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        PageInfo<TDebtCalculatorBroadcast> pageInfo   = debtCalculatorBroadcastDao.findByCalculatorId(req.getPage(), req.getPerPage(), req.getId());
        List<DebtCalculatorBroadcast>      resultList = new ArrayList<>();
        for (TDebtCalculatorBroadcast item : pageInfo.getList()) {
            if (!item.getReadFlag()) {
                item.setReadFlag(true);
                debtCalculatorBroadcastDao.updateById(item);
            }
            DebtCalculatorBroadcast vo = new DebtCalculatorBroadcast();
            BeanUtils.copyProperties(item, vo);
            vo.setProjectName(calculator.getProjectName());
            vo.setDays(calculator.getDisposalPeriod().multiply(new BigDecimal(30)).subtract(new BigDecimal(vo.getDays())).intValue());
            resultList.add(vo);
        }

        TDebtCalculatorBroadcast tDebtCalculatorBroadcast = debtCalculatorBroadcastDao.findLatestByCalculatorId(req.getId());

        Map<String, Object> result = Maps.newHashMap();
        result.put("projectName", StringUtils.isBlank(calculator.getProjectName()) ? "暂无" : calculator.getProjectName());
        result.put("list", resultList);
        result.put("total", pageInfo.getTotal());
        result.put("hasNextPage", pageInfo.isHasNextPage());
//        result.put("days", tDebtCalculatorBroadcast == null ? 0 : tDebtCalculatorBroadcast.getDays());
        result.put("days", calculator.getDisposalPeriod().multiply(new BigDecimal(30)).subtract(tDebtCalculatorBroadcast == null ? BigDecimal.ZERO : new BigDecimal(tDebtCalculatorBroadcast.getDays())));
        result.put("remainAnnualizedReturn", tDebtCalculatorBroadcast == null ? 0 : tDebtCalculatorBroadcast.getAnnualizedReturn());
        result.put("remainIncome", tDebtCalculatorBroadcast == null ? 0 : tDebtCalculatorBroadcast.getIncome());
        return ResponseModel.succ(result);
    }

    @Override
    public ResponseModel getDebtCalculatorDetailV2(CalculatorReq.QueryReq req) {
        TDebtCalculator calculator = debtCalculatorDao.selectById(req.getId());
        if (calculator == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        DebtCalculator vo = RespConvertUtil.convertToDebtCalculator(calculator);
        unitTransferToWan(vo);
        return ResponseModel.succ(vo);
    }

    private void unitTransferToWan(DebtCalculator vo) {
        BigDecimal wan = new BigDecimal("10000");
        if (vo.getDebtPrincipal() != null) {
            vo.setDebtPrincipal(vo.getDebtPrincipal().divide(wan, SHOW_BIG_DECIMAL_SCALE, RoundingMode.HALF_DOWN));
        }
        if (vo.getDebtInterest() != null) {
            vo.setDebtInterest(vo.getDebtInterest().divide(wan, SHOW_BIG_DECIMAL_SCALE, RoundingMode.HALF_DOWN));
        }
        if (vo.getMaximumDebt() != null) {
            vo.setMaximumDebt(vo.getMaximumDebt().divide(wan, SHOW_BIG_DECIMAL_SCALE, RoundingMode.HALF_DOWN));
        }
        if (vo.getLiquidationAmount() != null) {
            vo.setLiquidationAmount(vo.getLiquidationAmount().divide(wan, SHOW_BIG_DECIMAL_SCALE, RoundingMode.HALF_DOWN));
        }
        if (vo.getTransferAmount() != null) {
            vo.setTransferAmount(vo.getTransferAmount().divide(wan, SHOW_BIG_DECIMAL_SCALE, RoundingMode.HALF_DOWN));
        }
        if (vo.getGpAmount() != null) {
            vo.setGpAmount(vo.getGpAmount().divide(wan, SHOW_BIG_DECIMAL_SCALE, RoundingMode.HALF_DOWN));
        }
        if (vo.getLpAmount() != null) {
            vo.setLpAmount(vo.getLpAmount().divide(wan, SHOW_BIG_DECIMAL_SCALE, RoundingMode.HALF_DOWN));
        }
        if (vo.getDueDiligenceAmount() != null) {
            vo.setDueDiligenceAmount(vo.getDueDiligenceAmount().divide(wan, SHOW_BIG_DECIMAL_SCALE, RoundingMode.HALF_DOWN));
        }
        if (vo.getMiscAmount() != null) {
            vo.setMiscAmount(vo.getMiscAmount().divide(wan, SHOW_BIG_DECIMAL_SCALE, RoundingMode.HALF_DOWN));
        }
        if (vo.getTotalCost() != null) {
            vo.setTotalCost(vo.getTotalCost().divide(wan, SHOW_BIG_DECIMAL_SCALE, RoundingMode.HALF_DOWN));
        }

        if (vo.getEstimatedIncome() != null) {
            vo.setEstimatedIncome(vo.getEstimatedIncome().divide(wan, SHOW_BIG_DECIMAL_SCALE, RoundingMode.HALF_DOWN));
        }
        if (vo.getWithFundingAmount() != null) {
            vo.setWithFundingAmount(vo.getWithFundingAmount().divide(wan, SHOW_BIG_DECIMAL_SCALE, RoundingMode.HALF_DOWN));
        }
        if (vo.getDisposalAmount() != null) {
            vo.setDisposalAmount(vo.getDisposalAmount().divide(wan, SHOW_BIG_DECIMAL_SCALE, RoundingMode.HALF_DOWN));
        }
    }


    @Override
    public ResponseModel debtCalculatorDel(CalculatorReq.QueryReq req) {
        TDebtCalculator calculator = debtCalculatorDao.selectById(req.getId());
        if (calculator == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        calculator.setIsDelete(true);
        debtCalculatorDao.updateById(calculator);
        return ResponseModel.succ();
    }

    @Override
    public ResponseModel debtCalculatorClose(CalculatorReq.QueryReq req) {
        TDebtCalculator calculator = debtCalculatorDao.selectById(req.getId());
        if (calculator == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        calculator.setClosedFlag(true);
        debtCalculatorDao.updateById(calculator);
        return ResponseModel.succ();
    }

    @Override
    public void doBroadcastJob() {
        int                 page       = 1;
        int                 perPage    = 20;
        Map<String, Object> params     = new HashMap<>();
        String              recordDate = DateUtil.formatNormDate(new Date());
        log.info("债权计算器播报定时任务开始，recordDate={}", recordDate);
        params.put("recordDate", recordDate);
        int rows   = 0;
        int errors = 0;
        while (true) {
            log.info("债权计算器播报定时任务，recordDate={}，page={}", recordDate, page);
            PageInfo<TDebtCalculator> pageInfo = debtCalculatorDao.getNeedToBroadcastList(page, perPage, params);
            for (TDebtCalculator item : pageInfo.getList()) {
                log.info("债权计算器播报定时任务开始，recordDate={}，page={}, calculatorId={}", recordDate, page, item.getId());
                try {
                    processDebtCalculatorBroadcast(item);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.info("债权计算器播报异常，recordDate={}，page={}, calculatorId={}", recordDate, page, item.getId());
                    errors++;
                }
                rows++;
            }
            if (!pageInfo.isHasNextPage()) {
                break;
            }
            page++;
        }
        log.info("债权计算器播报定时任务结束，recordDate={}，rows={}，errors={}", recordDate, rows, errors);
    }

    @Override
    public void processDebtCalculatorBroadcast(TDebtCalculator item) {
        TDebtCalculatorBroadcast tDebtCalculatorBroadcast = debtCalculatorBroadcastDao.findLatestByCalculatorIdAndDate(item.getId(), new Date());
        if (tDebtCalculatorBroadcast != null) {
            return;
        }
        int                      days            = 1;
        TDebtCalculatorBroadcast latestBroadcast = debtCalculatorBroadcastDao.findLatestByCalculatorId(item.getId());
        if (latestBroadcast != null) {
            days = latestBroadcast.getDays() + 1;
        }
//        // 处置成本=转让价*处置成本费率（年）/360*1天
//
//        BigDecimal disposalAmount = item.getTransferAmount()
//                .multiply(item.getDisposalAnnualizedRate())
//                .divide(new BigDecimal(100), BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_DOWN)
//                .divide(new BigDecimal(360), BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_DOWN)
//                .multiply(new BigDecimal(days));
//        // 实际收益金额=清收目标-资金成本-处置成本-尽调成本-杂费-转让价
//        BigDecimal income = item.getLiquidationAmount()
//                .subtract(item.getWithFundingAmount())
//                .subtract(disposalAmount)
//                .subtract(item.getDueDiligenceAmount())
//                .subtract(item.getMiscAmount())
//                .subtract(item.getTransferAmount());
//        // 投资（年）收益率=实际收益金额/GP自有金额/处置周期（月）/12
//        BigDecimal gpAmount = item.getGpAmount() == null ? BigDecimal.ONE : item.getGpAmount();
//
//        BigDecimal annualizedReturn = income
//                .divide(gpAmount, BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_DOWN)
//                .divide(item.getDisposalPeriod(), BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_DOWN)
//                .multiply(new BigDecimal(12));
//
//
//
//
//        TDebtCalculatorBroadcast broadcast = new TDebtCalculatorBroadcast();
//        broadcast.setCalculatorId(item.getId());
//        broadcast.setDays(days);
//        broadcast.setIncome(income);
//        broadcast.setRecordDate(new Date());
//        broadcast.setAnnualizedReturn(annualizedReturn.multiply(new BigDecimal(100)));
//        debtCalculatorBroadcastDao.insert(broadcast);
        /*
        新公式
        每日播报公式
        处置第1天  当前收益率=劣后资金年收益率*360/1
        处置第2天 当前收益率=劣后资金年收益率*360/2
         */
        TDebtCalculatorBroadcast broadcast = new TDebtCalculatorBroadcast();
        broadcast.setCalculatorId(item.getId());
        broadcast.setDays(days);
        broadcast.setIncome(item.getEstimatedIncome());
        broadcast.setRecordDate(new Date());
        broadcast.setAnnualizedReturn(item.getAnnualizedReturn().multiply(new BigDecimal("360").divide(new BigDecimal(days), BIG_DECIMAL_SCALE, RoundingMode.HALF_DOWN)));
        debtCalculatorBroadcastDao.insert(broadcast);


        sendMpTemplateMsgIfNeed(item, broadcast);
    }


    private void sendMpTemplateMsgIfNeed(TDebtCalculator calculator, TDebtCalculatorBroadcast broadcast) {
        try {
            TAccountExtBind extBind = accountExtBindDao.selectById(calculator.getExtBindId());
            String          unionId = extBind.getUnionId();
            if (StringUtils.isBlank(unionId)) {
                return;
            }
            TAccountExtBind mpExtBind = accountExtBindDao.findMp360PaiBy(unionId);
            if (mpExtBind == null) {
                return;
            }
            JSONObject params = new JSONObject();
            params.put("projectName", StringUtils.isBlank(calculator.getProjectName()) ? "暂无" : calculator.getProjectName());
            params.put("annualizedReturn", broadcast.getAnnualizedReturn().setScale(2, BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString() + "%");
            params.put("income", broadcast.getIncome().setScale(2, BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString());
            params.put("updateTime", DateUtil.formatNormDate(new Date()));
            wxFacade.sendDebtCalculatorBroadcastTemplateMsg(mpExtBind.getExtUserId(), params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
