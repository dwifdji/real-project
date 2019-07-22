package com._360pai.core.service.applet.impl;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.core.common.constants.CalculatorEnum;
import com._360pai.core.common.constants.ServiceConfigEnum;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.applet.req.CalculatorReq;
import com._360pai.core.facade.applet.vo.PrincipalInterestCalculator;
import com._360pai.core.facade.applet.vo.PrincipalInterestCalculatorBroadcast;
import com._360pai.core.facade.applet.vo.PrincipalInterestCalculatorDetail;
import com._360pai.core.model.account.TAccountExtBind;
import com._360pai.core.model.numberJump.*;
import com._360pai.core.service.applet.PrincipalInterestCalculatorService;
import com._360pai.core.utils.ReqConvertUtil;
import com._360pai.core.utils.RespConvertUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static com._360pai.core.common.CoreConstants.BIG_DECIMAL_SCALE;
import static com._360pai.core.common.CoreConstants.SHOW_BIG_DECIMAL_SCALE;

/**
 * @author xdrodger
 * @Title: PrincipalInterestCalculatorServiceImpl
 * @ProjectName dev2-zeus
 * @Description:
 * @date 2019-04-30 10:56
 */
@Slf4j
@Service("principalInterestCalculatorService")
public class PrincipalInterestCalculatorServiceImpl extends CalculatorServiceImpl implements PrincipalInterestCalculatorService {

    @Transactional
    @Override
    public ResponseModel principalInterestCalculator(CalculatorReq.PrincipalInterestCalculatorReq req) {
        // 参数验证
        TPrincipalInterestCalculator calculator = ReqConvertUtil.convertToTPrincipalInterestCalculator(req);

        BigDecimal loanPeriod = req.getLoanPeriod() == null ? new BigDecimal(36) : req.getLoanPeriod();
        // 罚息比率默认为50%
        BigDecimal                       penaltyRate = req.getPenaltyRate() == null ? new BigDecimal(serviceConfigService.findByConfigType(ServiceConfigEnum.CALCULATOR_PENALTY_RATE)) : req.getPenaltyRate();
        List<TBankBenchmarkInterestRate> list        = benchmarkInterestRateDao.getByDateDuration(req.getOverdueStart(), req.getOverdueEnd());

        if (CollectionUtils.isNotEmpty(list)) {
            list.get(0).setStartDate(req.getOverdueStart());
            list.get(list.size() - 1).setEndDate(req.getOverdueEnd());
        }
        List<TPrincipalInterestCalculatorDetail> overdueDetails  = new ArrayList<>();
        BigDecimal                               overdueInterest = BigDecimal.ZERO;
        for (TBankBenchmarkInterestRate item : list) {
            TPrincipalInterestCalculatorDetail detail = new TPrincipalInterestCalculatorDetail();
            detail.setStartDate(item.getStartDate());
            detail.setEndDate(item.getEndDate());
            BigDecimal benchmarkInterestRate = item.getRate(loanPeriod);
            detail.setType("0");
            // 逾息利息=剩余本金*基准利率年利率*（1+上浮/下降比率）*（1+加收的罚息比率）/360*逾期时间天数（终止日期-起始日期）
            int overdueDays = DateUtil.getMargin(detail.getEndDate(), detail.getStartDate());
            // 基准利率年利率*（1+上浮/下降比率）*（1+加收的罚息比率）
            BigDecimal actualInterestRate = benchmarkInterestRate.divide(new BigDecimal(100), BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_DOWN).
                    multiply(new BigDecimal(1)
                            .add(req.getFluctuationRate() == null ? BigDecimal.ZERO : req.getFluctuationRate().divide(new BigDecimal(100), BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_DOWN)))
                    .multiply(new BigDecimal(1)
                            .add(penaltyRate.divide(new BigDecimal(100), BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_DOWN)));

            BigDecimal interest = req.getRemainPrincipal()
                    .multiply(actualInterestRate)
                    .divide(new BigDecimal(360), BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_DOWN)
                    .multiply(new BigDecimal(overdueDays));
            detail.setActualInterestRate(actualInterestRate);
            detail.setInterest(interest);
            detail.setBenchmarkInterestRate(benchmarkInterestRate);
            overdueDetails.add(detail);

            overdueInterest = overdueInterest.add(interest);
        }
        BigDecimal                               delayPerformanceAmount = BigDecimal.ZERO;
        List<TPrincipalInterestCalculatorDetail> delayDetails           = new ArrayList<>();
        if (CalculatorEnum.BenchmarkInterestRateSource.Bank.getKey().equals(req.getBenchmarkInterestRateSource())) {
            List<TBankBenchmarkInterestRate> delayHits = benchmarkInterestRateDao.getByDateDuration(req.getDelayPerformanceStart(), req.getDelayPerformanceEnd());
            if (CollectionUtils.isNotEmpty(delayHits)) {
                delayHits.get(0).setStartDate(req.getDelayPerformanceStart());
                delayHits.get(delayHits.size() - 1).setEndDate(req.getDelayPerformanceEnd());
            }
            for (TBankBenchmarkInterestRate item : delayHits) {
                TPrincipalInterestCalculatorDetail detail = new TPrincipalInterestCalculatorDetail();
                detail.setStartDate(item.getStartDate());
                detail.setEndDate(item.getEndDate());
                BigDecimal benchmarkInterestRate = item.getRate(loanPeriod);
                detail.setType("1");

                int delayDays = DateUtil.getMargin(detail.getEndDate(), detail.getStartDate());
                // 国家利率按2倍基准利率计算，迟延履行金=剩余本金*基准利率年利率x2/360*迟延履行期天数（终止日期-起始日期）
                BigDecimal actualInterestRate = benchmarkInterestRate
                        .divide(new BigDecimal(100), BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_DOWN)
                        .multiply(new BigDecimal(2));

                BigDecimal interest = req.getRemainPrincipal()
                        .multiply(actualInterestRate).divide(new BigDecimal(360), BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_DOWN)
                        .multiply(new BigDecimal(delayDays));

                detail.setActualInterestRate(actualInterestRate);
                detail.setInterest(interest);
                detail.setBenchmarkInterestRate(benchmarkInterestRate);

                delayDetails.add(detail);
                delayPerformanceAmount = delayPerformanceAmount.add(interest);
            }
        } else {
            // 法院利率按0.175‰利率计算，迟延履行金=剩余本金*1.75/10000*迟延履行期天数（终止日期-起始日期）
            int delayPerformanceDays = DateUtil.getMargin(req.getDelayPerformanceEnd(), req.getDelayPerformanceStart());
            delayPerformanceAmount = req.getRemainPrincipal().multiply(new BigDecimal(1.75)).divide(new BigDecimal(10000)).multiply(new BigDecimal(delayPerformanceDays));
        }
        //总计=欠息+逾期利息+迟延履行金
        BigDecimal totalCost = req.getDebtInterest().add(overdueInterest).add(delayPerformanceAmount);
        calculator.setDelayPerformanceAmount(delayPerformanceAmount);
        calculator.setTotalCost(totalCost);
        calculator.setOverdueInterest(overdueInterest);
        calculator.setPenaltyRate(penaltyRate);
        calculator.setBenchmarkInterestRateSource(req.getBenchmarkInterestRateSource());
        int result = principalInterestCalculatorDao.insert(calculator);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }

        overdueDetails.addAll(delayDetails);
        overdueDetails.forEach(item -> {
            item.setCalculatorId(calculator.getId());
            item.setActualInterestRate(item.getActualInterestRate().multiply(new BigDecimal(100)));
            int insertResult = principalInterestCalculatorDetailDao.insert(item);
            if (insertResult == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        });


        Map<String, Object> data = new HashMap<>(1);
        data.put("id", calculator.getId());
        return ResponseModel.succ(data);
    }
//
//    private void validateParam(CalculatorReq.PrincipalInterestCalculatorReq req) {
//        if (req.getRemainPrincipal() == null) {
//            throw new BusinessException(ApiCallResult.EMPTY);
//        }
//        if (req.getDebtInterest() == null) {
//            throw new BusinessException(ApiCallResult.EMPTY);
//        }
//        if (req.getOverdueStart() == null || req.getOverdueEnd() == null || req.getOverdueStart().compareTo(req.getOverdueEnd()) > 0) {
//            throw new BusinessException(ApiCallResult.EMPTY);
//        }
//        if (req.getDelayPerformanceStart() == null || req.getDelayPerformanceEnd() == null || req.getDelayPerformanceStart().compareTo(req.getDelayPerformanceEnd()) > 0) {
//            throw new BusinessException(ApiCallResult.EMPTY);
//        }
//    }
//
    /**
     * 本息总计=剩余本金+利息总计
     *
     * 利息总计=欠息+逾期利息+迟延履行金
     *
     * 欠息
     *
     * 欠息金额=用户自己输入的金额
     *
     *
     *
     * 逾期利息
     *
     * 逾息利息=剩余本金*基准利率年利率*（1+上浮/-下降比率）*（1+加收的罚息比率）/360*逾期时间天数（终止日期-起始日期）
     *
     * 罚息比率不填写默认为0
     *
     *
     *
     * 迟延履行金（用户迟延履行期不填就认为没有迟延履行金）延迟履约金为0
     *
     * 1）法院利率按0.175‰利率计算，迟延履行金=剩余本金*1.75/10000*迟延履行期天数（终止日期-起始日期）
     *
     * 2）国家利率按2倍基准利率计算，迟延履行金=剩余本金*基准利率年利率x2/360*迟延履行期天数（终止日期-起始日期）
     *
     * @param req
     * @return
     */
    @Transactional
    @Override
    public ResponseModel principalInterestCalculatorV2(CalculatorReq.PrincipalInterestCalculatorReq req) {
        log.info("本息计算器，参数 principalInterestCalculatorV2 req {}", req);

        TPrincipalInterestCalculator calculator = ReqConvertUtil.convertToTPrincipalInterestCalculator(req);

        //贷款期限
        BigDecimal                       loanPeriod      = req.getLoanPeriod();
        //罚息比例
        BigDecimal                       penaltyRate     = req.getPenaltyRate();
        //浮动利率
        BigDecimal                       fluctuationRate = req.getFluctuationRate();
        List<TBankBenchmarkInterestRate> list            = benchmarkInterestRateDao.getByDateDuration(req.getOverdueStart(), req.getOverdueEnd());

        if (CollectionUtils.isNotEmpty(list)) {
            list.get(0).setStartDate(req.getOverdueStart());
            list.get(list.size() - 1).setEndDate(req.getOverdueEnd());
        }
        List<TPrincipalInterestCalculatorDetail> overdueDetails  = new ArrayList<>();
        BigDecimal                               overdueInterest = BigDecimal.ZERO;
        for (TBankBenchmarkInterestRate item : list) {
            TPrincipalInterestCalculatorDetail detail = new TPrincipalInterestCalculatorDetail();
            detail.setStartDate(item.getStartDate());
            detail.setEndDate(item.getEndDate());
            BigDecimal benchmarkInterestRate = item.getRate(loanPeriod);
            detail.setType("0");
            // 逾息利息=剩余本金*基准利率年利率*（1+上浮/下降比率）*（1+加收的罚息比率）/360*逾期时间天数（终止日期-起始日期）
            int overdueDays = DateUtil.getMargin(detail.getEndDate(), detail.getStartDate());
            // 基准利率年利率*（1+上浮/下降比率）*（1+加收的罚息比率）
            BigDecimal actualInterestRate = benchmarkInterestRate.divide(new BigDecimal(100), BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_DOWN).
                    multiply(new BigDecimal(1)
                            .add(fluctuationRate.divide(new BigDecimal(100), BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_DOWN)))
                    .multiply(new BigDecimal(1)
                            .add(penaltyRate.divide(new BigDecimal(100), BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_DOWN)));

            BigDecimal interest = req.getRemainPrincipal()
                    .multiply(actualInterestRate)
                    .divide(new BigDecimal(360), BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_DOWN)
                    .multiply(new BigDecimal(overdueDays));
            detail.setActualInterestRate(actualInterestRate);
            detail.setInterest(interest);
            detail.setBenchmarkInterestRate(benchmarkInterestRate);
            overdueDetails.add(detail);

            overdueInterest = overdueInterest.add(interest);
        }

        List<TPrincipalInterestCalculatorDetail> delayDetails = new ArrayList<>();

        //延迟履行金
        BigDecimal delayPerformanceAmount = BigDecimal.ZERO;
        // 如果延迟履行期没有填写，则没有延迟履行金
        if (req.getDelayPerformanceStart() != null && req.getDelayPerformanceEnd() != null && req.getDelayPerformanceStart().compareTo(req.getDelayPerformanceEnd()) <= 0) {

            if (CalculatorEnum.BenchmarkInterestRateSource.Bank.getKey().equals(req.getBenchmarkInterestRateSource())) {
                List<TBankBenchmarkInterestRate> delayHits = benchmarkInterestRateDao.getByDateDuration(req.getDelayPerformanceStart(), req.getDelayPerformanceEnd());
                if (CollectionUtils.isNotEmpty(delayHits)) {
                    delayHits.get(0).setStartDate(req.getDelayPerformanceStart());
                    delayHits.get(delayHits.size() - 1).setEndDate(req.getDelayPerformanceEnd());
                }
                for (TBankBenchmarkInterestRate item : delayHits) {
                    TPrincipalInterestCalculatorDetail detail = new TPrincipalInterestCalculatorDetail();
                    detail.setStartDate(item.getStartDate());
                    detail.setEndDate(item.getEndDate());
                    BigDecimal benchmarkInterestRate = item.getRate(loanPeriod);
                    detail.setType("1");

                    int delayDays = DateUtil.getMargin(detail.getEndDate(), detail.getStartDate());
                    // 国家利率按2倍基准利率计算，迟延履行金=剩余本金*基准利率年利率x2/360*迟延履行期天数（终止日期-起始日期）
                    BigDecimal actualInterestRate = benchmarkInterestRate
                            .divide(new BigDecimal(100), BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_DOWN)
                            .multiply(new BigDecimal(2));

                    BigDecimal interest = req.getRemainPrincipal()
                            .multiply(actualInterestRate).divide(new BigDecimal("360"), BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_DOWN)
                            .multiply(new BigDecimal(delayDays));

                    detail.setActualInterestRate(actualInterestRate);
                    detail.setInterest(interest);
                    detail.setBenchmarkInterestRate(benchmarkInterestRate);

                    delayDetails.add(detail);
                    delayPerformanceAmount = delayPerformanceAmount.add(interest);
                }
            } else {
                // 法院利率按0.175‰利率计算，迟延履行金=剩余本金*1.75/10000*迟延履行期天数（终止日期-起始日期）
                int delayPerformanceDays = DateUtil.getMargin(req.getDelayPerformanceEnd(), req.getDelayPerformanceStart());
                delayPerformanceAmount = req.getRemainPrincipal().multiply(new BigDecimal("1.75")).divide(new BigDecimal("10000")).multiply(new BigDecimal(delayPerformanceDays));
            }
        }
        //总计=欠息+逾期利息+迟延履行金
        //利息总计=欠息+逾期利息+迟延履行金
        BigDecimal totalCost = req.getDebtInterest().add(overdueInterest).add(delayPerformanceAmount);
        calculator.setDelayPerformanceAmount(delayPerformanceAmount);
        calculator.setTotalCost(totalCost);
        calculator.setOverdueInterest(overdueInterest);
        calculator.setPenaltyRate(penaltyRate);
        calculator.setBenchmarkInterestRateSource(req.getBenchmarkInterestRateSource());
        int result = principalInterestCalculatorDao.insert(calculator);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }

        overdueDetails.addAll(delayDetails);
        overdueDetails.forEach(item -> {
            item.setCalculatorId(calculator.getId());
            item.setActualInterestRate(item.getActualInterestRate().multiply(new BigDecimal(100)));
            int insertResult = principalInterestCalculatorDetailDao.insert(item);
            if (insertResult == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        });


        Map<String, Object> data = new HashMap<>(2);
        data.put("id", calculator.getId());
        return ResponseModel.succ(data);
    }


    @Override
    public ResponseModel getPrincipalInterestCalculatorBroadcastV2(CalculatorReq.QueryReq req) {
        TPrincipalInterestCalculator calculator = principalInterestCalculatorDao.selectById(req.getId());
        if (calculator == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        PageInfo<TPrincipalInterestCalculatorBroadcast> pageInfo   = principalInterestCalculatorBroadcastDao.findByCalculatorId(req.getPage(), req.getPerPage(), req.getId());
        List<PrincipalInterestCalculatorBroadcast>      resultList = new ArrayList<>();
        for (TPrincipalInterestCalculatorBroadcast item : pageInfo.getList()) {
            if (!item.getReadFlag()) {
                item.setReadFlag(true);
                principalInterestCalculatorBroadcastDao.updateById(item);
            }
            PrincipalInterestCalculatorBroadcast vo = new PrincipalInterestCalculatorBroadcast();
            BeanUtils.copyProperties(item, vo);
            vo.setProjectName(calculator.getProjectName());
            resultList.add(vo);
        }

        TPrincipalInterestCalculatorBroadcast tPrincipalInterestCalculatorBroadcast = principalInterestCalculatorBroadcastDao.findLatestByCalculatorId(req.getId());

//        totalIncome	int	累计收益
//        todayIncome	int	今日利息收益
//        days	int	计息天数

        Map<String, Object> result = Maps.newHashMap();
        result.put("projectName", StringUtils.isBlank(calculator.getProjectName()) ? "暂无" : calculator.getProjectName());
        result.put("projectManager", StringUtils.isBlank(calculator.getProjectManager()) ? "暂无" : calculator.getProjectManager());
        result.put("closedFlag",  calculator.getClosedFlag());
        result.put("list", resultList);
        result.put("total", pageInfo.getTotal());
        result.put("hasNextPage", pageInfo.isHasNextPage());
        result.put("days", tPrincipalInterestCalculatorBroadcast == null ? 0 : tPrincipalInterestCalculatorBroadcast.getDays());
        result.put("totalIncome", tPrincipalInterestCalculatorBroadcast == null ? 0 : tPrincipalInterestCalculatorBroadcast.getIncome().divide(new BigDecimal("10000"),BIG_DECIMAL_SCALE,BigDecimal.ROUND_HALF_UP));
        result.put("todayIncome", tPrincipalInterestCalculatorBroadcast == null ? 0 : tPrincipalInterestCalculatorBroadcast.getTodayInterest());

        return ResponseModel.succ(result);
    }

    @Override
    public ResponseModel getPrincipalInterestCalculatorDetail(CalculatorReq.QueryReq req) {
        TPrincipalInterestCalculator calculator = principalInterestCalculatorDao.selectById(req.getId());
        if (calculator == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        PrincipalInterestCalculator vo = RespConvertUtil.convertToPrincipalInterestCalculator(calculator);
        vo.setList(getOverdueDetailList(calculator.getId()));
        return ResponseModel.succ(vo);
    }

    @Override
    public ResponseModel getPrincipalInterestCalculatorBroadcast(CalculatorReq.QueryReq req) {
        TPrincipalInterestCalculator                       calculator = principalInterestCalculatorDao.selectById(req.getId());
        if (calculator == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        PageInfo<TPrincipalInterestCalculatorBroadcast> pageInfo   = principalInterestCalculatorBroadcastDao.findByCalculatorId(req.getPage(), req.getPerPage(), req.getId());
        List<PrincipalInterestCalculatorBroadcast>      resultList = new ArrayList<>();
        for (TPrincipalInterestCalculatorBroadcast item : pageInfo.getList()) {
            if (!item.getReadFlag()) {
                item.setReadFlag(true);
                principalInterestCalculatorBroadcastDao.updateById(item);
            }
            PrincipalInterestCalculatorBroadcast vo = new PrincipalInterestCalculatorBroadcast();
            BeanUtils.copyProperties(item, vo);
            vo.setProjectName(calculator.getProjectName());
            resultList.add(vo);
        }

        TPrincipalInterestCalculatorBroadcast tPrincipalInterestCalculatorBroadcast   = principalInterestCalculatorBroadcastDao.findLatestByCalculatorId(req.getId());

//        totalIncome	int	累计收益
//        todayIncome	int	今日利息收益
//        days	int	计息天数

        Map<String,Object> result = Maps.newHashMap();
        result.put("projectName", StringUtils.isBlank(calculator.getProjectName()) ? "暂无" : calculator.getProjectName());
        result.put("list",resultList);
        result.put("total",pageInfo.getTotal());
        result.put("hasNextPage",pageInfo.isHasNextPage());
        result.put("days",tPrincipalInterestCalculatorBroadcast==null?0:tPrincipalInterestCalculatorBroadcast.getDays());
        result.put("totalIncome",tPrincipalInterestCalculatorBroadcast==null?0:tPrincipalInterestCalculatorBroadcast.getIncome());
        result.put("todayIncome",tPrincipalInterestCalculatorBroadcast==null?0:tPrincipalInterestCalculatorBroadcast.getTodayInterest());

        return ResponseModel.succ(result);
    }

    @Override
    public ResponseModel getPrincipalInterestCalculatorDetailV2(CalculatorReq.QueryReq req) {
        TPrincipalInterestCalculator calculator = principalInterestCalculatorDao.selectById(req.getId());
        if (calculator == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        PrincipalInterestCalculator vo = RespConvertUtil.convertToPrincipalInterestCalculator(calculator);
        unitTransfer(vo);
        vo.setList(getOverdueDetailListV2(calculator.getId()));
        return ResponseModel.succ(vo);
    }

    private void unitTransfer(PrincipalInterestCalculator vo) {
        BigDecimal wan = new BigDecimal("10000");
        vo.setPrincipalAndInterestTotal(vo.getPrincipalAndInterestTotal().divide(wan,SHOW_BIG_DECIMAL_SCALE,RoundingMode.HALF_DOWN));
        vo.setDebtInterest(vo.getDebtInterest().divide(wan,SHOW_BIG_DECIMAL_SCALE,RoundingMode.HALF_DOWN));
        vo.setOverdueInterest(vo.getOverdueInterest().divide(wan,SHOW_BIG_DECIMAL_SCALE,RoundingMode.HALF_DOWN));
        vo.setDelayPerformanceAmount(vo.getDelayPerformanceAmount().divide(wan,SHOW_BIG_DECIMAL_SCALE,RoundingMode.HALF_DOWN));
        vo.setTotalCost(vo.getTotalCost().divide(wan,SHOW_BIG_DECIMAL_SCALE,RoundingMode.HALF_DOWN));
        vo.setRemainPrincipal(vo.getRemainPrincipal().divide(wan,SHOW_BIG_DECIMAL_SCALE,RoundingMode.HALF_DOWN));
    }

    private void unitTransfer(TPrincipalInterestCalculator vo) {
        BigDecimal wan = new BigDecimal("10000");
        vo.setDebtInterest(vo.getDebtInterest().multiply(wan));
        vo.setOverdueInterest(vo.getOverdueInterest().multiply(wan));
        vo.setDelayPerformanceAmount(vo.getDelayPerformanceAmount().multiply(wan));
        vo.setTotalCost(vo.getTotalCost().multiply(wan));
    }

    @Override
    public ResponseModel principalInterestCalculatorDel(CalculatorReq.QueryReq req) {
        TPrincipalInterestCalculator calculator = principalInterestCalculatorDao.selectById(req.getId());
        if (calculator == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        calculator.setIsDelete(true);
        principalInterestCalculatorDao.updateById(calculator);
        return ResponseModel.succ();
    }

    @Override
    public ResponseModel principalInterestCalculatorClose(CalculatorReq.QueryReq req) {
        TPrincipalInterestCalculator calculator = principalInterestCalculatorDao.selectById(req.getId());
        if (calculator == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        calculator.setClosedFlag(true);
        principalInterestCalculatorDao.updateById(calculator);
        return ResponseModel.succ();
    }

    private List<PrincipalInterestCalculatorDetail> getOverdueDetailList(Integer calculatorId) {
        List<TPrincipalInterestCalculatorDetail> list       = principalInterestCalculatorDetailDao.findOverdueListByCalculatorId(calculatorId);
        List<PrincipalInterestCalculatorDetail>  resultList = new ArrayList<>();
        for (TPrincipalInterestCalculatorDetail item : list) {
            PrincipalInterestCalculatorDetail vo = RespConvertUtil.convertToPrincipalInterestCalculatorDetail(item);
            resultList.add(vo);
        }
        return resultList;
    }

    private List<PrincipalInterestCalculatorDetail> getOverdueDetailListV2(Integer calculatorId) {
        List<TPrincipalInterestCalculatorDetail> list       = principalInterestCalculatorDetailDao.findOverdueListByCalculatorId(calculatorId);
        List<PrincipalInterestCalculatorDetail>  resultList = new ArrayList<>();
        BigDecimal wan = new BigDecimal("10000");
        for (TPrincipalInterestCalculatorDetail item : list) {
            PrincipalInterestCalculatorDetail vo = RespConvertUtil.convertToPrincipalInterestCalculatorDetail(item);
            vo.setInterest(vo.getInterest().divide(wan,SHOW_BIG_DECIMAL_SCALE,RoundingMode.HALF_DOWN));
            resultList.add(vo);
        }
        return resultList;
    }

    @Override
    public void doBroadcastJob() {
        int                 page       = 1;
        int                 perPage    = 20;
        Map<String, Object> params     = new HashMap<>(2);
        String              recordDate = DateUtil.formatNormDate(new Date());
        log.info("本息计算器播报定时任务开始，recordDate={}", recordDate);
        String today = DateUtil.formatNormDate(new Date());
        params.put("startTime", today + " 00:00:00");
        params.put("endTime", today + " 23:59:59");
        int rows   = 0;
        int errors = 0;
        while (true) {
            log.info("本息计算器播报定时任务，recordDate={}，page={}", recordDate, page);
            PageInfo<TPrincipalInterestCalculator> pageInfo = principalInterestCalculatorDao.getNeedToBroadcastList(page, perPage, params);
            for (TPrincipalInterestCalculator item : pageInfo.getList()) {
                log.info("本息计算器播报定时任务开始，recordDate={}，page={}, calculatorId={}", recordDate, page, item.getId());
                try {
                    processPrincipalInterestCalculatorBroadcast(item);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.info("本息计算器播报异常，recordDate={}，page={}, calculatorId={}", recordDate, page, item.getId());
                    errors++;
                }
                rows++;
            }
            if (!pageInfo.isHasNextPage()) {
                break;
            }
            page++;
        }
        log.info("本息计算器播报定时任务结束，recordDate={}，rows={}，errors={}", recordDate, rows, errors);
    }

    @Override
    public void processPrincipalInterestCalculatorBroadcast(TPrincipalInterestCalculator item) {
//        unitTransfer(item);
        int                                   days            = 1;
        Date                                  recordDate      = DateUtil.addDay(item.getOverdueEnd(), 1);
        TPrincipalInterestCalculatorBroadcast latestBroadcast = principalInterestCalculatorBroadcastDao.findLatestByCalculatorId(item.getId());
        if (latestBroadcast != null) {
            days = latestBroadcast.getDays() + 1;
            recordDate = DateUtil.addDay(latestBroadcast.getRecordDate(), 1);
        }
        TBankBenchmarkInterestRate tBankBenchmarkInterestRate = benchmarkInterestRateDao.getByDate(recordDate);

        BigDecimal totalCost       = item.getTotalCost();
        BigDecimal remainPrincipal = item.getRemainPrincipal();

        BigDecimal loanPeriod            = item.getLoanPeriod() == null ? new BigDecimal(36) : item.getLoanPeriod();
        BigDecimal benchmarkInterestRate = tBankBenchmarkInterestRate.getRate(loanPeriod);
        BigDecimal fluctuationRate       = item.getFluctuationRate() == null ? BigDecimal.ZERO : item.getFluctuationRate();
        //基准利率年利率（根据当天的日期测算）*（1+上浮/下降比率）*（1+加收的罚息比率）
        BigDecimal actualInterestRate = benchmarkInterestRate
                .divide(new BigDecimal(100), BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_DOWN)
                .multiply(new BigDecimal(1).add(fluctuationRate.divide(new BigDecimal(100), BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_DOWN)))
                .multiply(new BigDecimal(1).add(item.getPenaltyRate().divide(new BigDecimal(100), BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_DOWN)));

        // 剩余本金*基准利率年利率（根据当天的日期测算）*（1+上浮/下降比率）*（1+加收的罚息比率）/360*1
        BigDecimal overdueInterest = remainPrincipal
                .multiply(actualInterestRate)
                .divide(new BigDecimal(360), BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_DOWN)
                .multiply(BigDecimal.ONE);


        BigDecimal todayInterest;

//        总计=之前的总费用合计+剩余本金*1.75/10000*1+剩余本金*基准利率年利率（根据当天的日期测算）*（1+上浮/下降比率）*（1+加收的罚息比率）/360*1
        if (CalculatorEnum.BenchmarkInterestRateSource.Court.getKey().equals(item.getBenchmarkInterestRateSource())) {

            // 总计=
            // 之前的总费用合计
            // +剩余本金*1.75/10000*1
            // +剩余本金*基准利率年利率（根据当天的日期测算）*（1+上浮/下降比率）*（1+加收的罚息比率）/360*1

            BigDecimal delayPerformanceAmount = remainPrincipal
                    .multiply(new BigDecimal(1.75))
                    .divide(new BigDecimal(10000), BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_DOWN)
                    .multiply(BigDecimal.ONE);

            todayInterest = delayPerformanceAmount.add(overdueInterest);

        } else {
            //            总计=
            // 之前的总费用合计
            // +剩余本金*基准利率年利率x2/360*1
            // +剩余本金*基准利率年利率（根据当天的日期测算）*（1+上浮/下降比率）*（1+加收的罚息比率）/360*1

            BigDecimal delayPerformanceAmount = remainPrincipal
                    .multiply(benchmarkInterestRate)
                    .multiply(new BigDecimal(2))
                    .divide(new BigDecimal(100), BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_DOWN)
                    .divide(new BigDecimal(360), BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_DOWN)
                    .multiply(BigDecimal.ONE);
            todayInterest = delayPerformanceAmount.add(overdueInterest);
        }
        BigDecimal income;
        if (latestBroadcast != null) {
            income = latestBroadcast.getIncome().add(todayInterest);
        } else {
            income = totalCost.add(todayInterest);
        }

        TPrincipalInterestCalculatorBroadcast broadcast = new TPrincipalInterestCalculatorBroadcast();
        broadcast.setCalculatorId(item.getId());
        broadcast.setRecordDate(recordDate);
        broadcast.setDays(days);
        broadcast.setTodayInterest(todayInterest);
        broadcast.setIncome(income);

        int result = principalInterestCalculatorBroadcastDao.insert(broadcast);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }

        // 发送模版消息
        sendMpTemplateMsgIfNeed(item, broadcast);
    }

    private void sendMpTemplateMsgIfNeed(TPrincipalInterestCalculator calculator, TPrincipalInterestCalculatorBroadcast broadcast) {
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
            params.put("todayInterest", broadcast.getTodayInterest().setScale(2, BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString());
            params.put("income", broadcast.getIncome().setScale(2, BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString());
            params.put("updateTime", DateUtil.formatNormDate(new Date()));
            wxFacade.sendPrincipalInterestCalculatorBroadcastTemplateMsg(mpExtBind.getExtUserId(), params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
