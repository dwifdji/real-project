package com.tzCloud.core.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.hankcs.hanlp.model.perceptron.PerceptronNERecognizer;
import com.hankcs.hanlp.seg.Segment;
import com.tzCloud.arch.common.PageInfoResp;
import com.tzCloud.arch.common.utils.DateUtil;
import com.tzCloud.arch.core.redis.RedisCachemanager;
import com.tzCloud.core.condition.caseMatching.CaseHtmlAnalysisCondition;
import com.tzCloud.core.condition.caseMatching.CaseHtmlDataCondition;
import com.tzCloud.core.condition.caseMatching.CpwswItemCondition;
import com.tzCloud.core.condition.caseMatching.TLawyerInfoCondition;
import com.tzCloud.core.condition.view.ViewBriefCourtCondition;
import com.tzCloud.core.condition.view.ViewBriefCourtLawfirmCondition;
import com.tzCloud.core.condition.view.ViewBriefLawfirmLawyerCondition;
import com.tzCloud.core.condition.view.ViewBriefLawyerRankingCondition;
import com.tzCloud.core.constant.CaseEnum;
import com.tzCloud.core.dao.caseMatching.CaseHtmlAnalysisDao;
import com.tzCloud.core.dao.caseMatching.CaseHtmlDataDao;
import com.tzCloud.core.dao.caseMatching.CpwswItemDao;
import com.tzCloud.core.dao.caseMatching.TLawyerInfoDao;
import com.tzCloud.core.dao.view.ViewTableDao;
import com.tzCloud.core.facade.caseMatching.req.CaseMatchingReq;
import com.tzCloud.core.facade.caseMatching.resp.*;
import com.tzCloud.core.hanLP.Config;
import com.tzCloud.core.hanLP.HanLPFactory;
import com.tzCloud.core.model.caseMatching.CaseHtmlAnalysis;
import com.tzCloud.core.model.caseMatching.CaseHtmlData;
import com.tzCloud.core.model.caseMatching.CpwswItem;
import com.tzCloud.core.model.caseMatching.TLawyerInfo;
import com.tzCloud.core.service.CaseHtmlAnalysisService;
import com.tzCloud.core.service.CollectService;
import com.tzCloud.core.service.ViewTableService;
import com.tzCloud.core.utils.WenshuHtmlSaveUntil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.Future;

import static com.tzCloud.core.common.SystemContent.LAWYER_IMAG_URL;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2019/3/26 14:25
 */
@Service
@Slf4j
public class ViewTableServiceImpl implements ViewTableService {
    private static String                  ALL_STR             = "全部";
    @Resource
    private        ViewTableDao            viewTableDao;
    @Resource
    private        CpwswItemDao            cpwswItemDao;
    @Resource
    private        TLawyerInfoDao          tLawyerInfoDao;
    @Resource
    private        CaseHtmlAnalysisDao     caseHtmlAnalysisDao;
    @Resource
    private        CaseHtmlDataDao         caseHtmlDataDao;
    @Resource
    private        CaseHtmlAnalysisService caseHtmlAnalysisService;
    @Resource
    private        CollectService          collectService;
    private        WenshuHtmlSaveUntil     wenshuHtmlSaveUntil = new WenshuHtmlSaveUntil();


    @Override
    public PageInfoResp<CaseListForLawyerResp> getCaseListByLawyer(CaseMatchingReq.CaseListByLawyer req) {
        CpwswItemCondition condition = new CpwswItemCondition();
        condition.setBrief(req.getBrief());
        condition.setLawyer(req.getLawyerName());
        PageHelper.startPage(req.getPage(), req.getPerPage());
        List<CaseListForLawyerResp> list = cpwswItemDao.getCaseListByLawyer2(condition);
        list.forEach(resp -> {
            try {
                String format = DateUtil.format(DateUtil.parse(resp.getCaseDate(), DateUtil.NORM_DATETIME_PATTERN), DateUtil.NORM_DATE_PATTERN);
                resp.setCaseProgram(CaseEnum.TrialRound.getValueByKey(resp.getCaseProgram()));
                resp.setCaseDate(format);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        PageInfo pageInfo = new PageInfo<>(list);
        return new PageInfoResp(pageInfo);
    }

    @Override
    public LawyerDetailResp getLawyerDetail(CaseMatchingReq.LawyerDetail req) throws Exception {
        CpwswItemCondition condition = new CpwswItemCondition();
        condition.setLawFirm(req.getLawFirmName());
        condition.setLawyer(req.getLawyerName());
        Future<List<LawyerDetailResp>> lists = cpwswItemDao.getLawyerDetail(condition);
        List<LawyerDetailResp>         list  = lists.get();
        if (CollectionUtils.isEmpty(list)) {
            CpwswItemCondition condition2 = new CpwswItemCondition();
            condition2.setLawyer(req.getLawyerName());
            lists = cpwswItemDao.getLawyerDetail(condition2);
            list = lists.get();
        }

        LawyerDetailResp resp = CollectionUtils.isEmpty(list) ? new LawyerDetailResp() : list.get(0);

        Future<List<Map>>                 courtLists            = cpwswItemDao.getCourtList(condition);
        Future<List<Map>>                 countGroupByBriefs    = cpwswItemDao.getCountGroupByBrief(condition);
        Future<List<Map<String, Object>>> caseCountByMonthLists = cpwswItemDao.getCaseCountByMonthList(condition);

        List<Map> courtList = courtLists.get();

        List<Map<String, Object>> courtStrList = new ArrayList<>();
        Map<String, Object>       alwaysCourt;
        for (Map map : courtList) {
            alwaysCourt = new HashMap<>(2);
            alwaysCourt.put("courtName", map.get("courtName"));
            alwaysCourt.put("caseNum", map.get("countNum"));
            courtStrList.add(alwaysCourt);
        }
        resp.setCourtList(courtStrList);


        List<Map> countGroupByBrief = countGroupByBriefs.get();
        Long      totalCaseNum      = 0L;
        for (Map map : countGroupByBrief) {
            totalCaseNum += (Long) map.get("caseNum");
        }
        resp.setTotalCaseNum(totalCaseNum.intValue());
        resp.setCaseTypeList(countGroupByBrief);

        // 按月group by 得到每个月的案件数 ，然后取最近12个月的数据

        List<Map<String, Object>> caseCountByMonthList = caseCountByMonthLists.get();

        String[]                  last12Months               = DateUtil.getLast12Months();
        List<Map<String, Object>> caseCountByMonthListResult = new ArrayList<>();
        Map<String, Object>       map;
        for (String month : last12Months) {
            map = new HashMap<>(2);
            Long caseCountNum = 0L;
            for (Map<String, Object> caseCount : caseCountByMonthList) {
                if (caseCount.get("months").equals(month)) {
                    caseCountNum = (Long) caseCount.get("caseNum");
                    break;
                }
            }
            map.put("caseNum", caseCountNum);
            map.put("month", month);
            caseCountByMonthListResult.add(map);
        }
        resp.setCaseCountByMonthList(caseCountByMonthListResult);

        if (StringUtils.isNotBlank(resp.getLawyerFirm())) {
            resp.setLawyerFirm(req.getLawFirmName());
        }
        if (StringUtils.isNotBlank(resp.getLawyerImgUrl())) {
            resp.setLawyerImgUrl(LAWYER_IMAG_URL);
        }
        if (resp.getLawyerId() == null) {
            resp.setAttentionFlag("0");
        } else {
            resp.setAttentionFlag(collectService.isCollect(req.getLoginId(), resp.getLawyerId().toString(), CaseEnum.CollectTypeEum.LAWYER.name())?"1":"0");
        }
        return resp;
    }

    @Override
    public CaseDetailResp getCaseDetail(CaseMatchingReq.CaseDetail req) {
        CpwswItemCondition condition = new CpwswItemCondition();
        condition.setDocId(req.getDocId());
        CpwswItem      cpwswItem = cpwswItemDao.selectFirst(condition);
        CaseDetailResp resp      = new CaseDetailResp();
        if (cpwswItem == null) {
            return resp;
        }
        resp.setDocId(req.getDocId());
        resp.setBrief(cpwswItem.getBrief());
        resp.setCaseNo(cpwswItem.getAh());
        resp.setCaseTitle(cpwswItem.getAjmc());
        resp.setCaseType(CaseType.convert(Integer.valueOf(StringUtils.isNotBlank(cpwswItem.getAjlx()) ? cpwswItem.getAjlx() : "0")));
        resp.setCourtName(cpwswItem.getFymc());
        resp.setTrialProcessType(cpwswItem.getSpcx());
        resp.setJudgementDate(DateUtil.formatNormDate(cpwswItem.getCprq()));

        resp.setCollegialPanel(viewTableDao.getCollegialPanel(req.getDocId()));

        CaseHtmlDataCondition caseHtmlDataCondition = new CaseHtmlDataCondition();
        caseHtmlDataCondition.setDocId(req.getDocId());
        CaseHtmlData caseHtmlData = caseHtmlDataDao.selectFirst(caseHtmlDataCondition);

        // 如果没有解析数据，则直接返回
        if (caseHtmlData == null) {
            return resp;
        }
        CaseHtmlAnalysis caseHtmlAnalysis = parse(caseHtmlData);
        resp.setDefendant(caseHtmlAnalysis.getDefendant());
        resp.setDefendantLawyer(caseHtmlAnalysis.getDefendantLawyer());
        resp.setProsecutor(caseHtmlAnalysis.getProsecutor());
        resp.setProsecutorLawyer(caseHtmlAnalysis.getProsecutorLawyer());
        resp.setCaseMainBody(caseHtmlAnalysis.getHtml());

        resp.setProsecutorLawyerList(getLawyerDetail(caseHtmlAnalysis.getProsecutorLawyer()));
        resp.setDefendantLawyerList(getLawyerDetail(caseHtmlAnalysis.getDefendantLawyer()));
        resp.setContainer(WenshuHtmlSaveUntil.segParse(caseHtmlAnalysis.getHtml()));
        return resp;
    }

    @Override
    public void getInitLawyerList(Integer initNum) throws Exception{
        List<Map<String,Object>> list     = viewTableDao.getInitBrief();
        for (Map<String,Object> map : list){
            PageHelper.startPage(1, initNum * getNumPolicy((Long) map.get("count")));
            List<Map<String, Object>> lawyerList   = viewTableDao.getInitLawyerList((String) map.get("brief"));
            PageInfo pageInfo = new PageInfo<>(lawyerList);
            process(pageInfo.getList());
        }
    }

    @Override
    public void initAllCaseDetail() {

    }

    @Override
    public CaseHtmlAnalysis getCaseHtmlAnalysis(String docId) {
        CaseHtmlData htmlData = caseHtmlDataDao.findBy(docId);
        if (htmlData != null) {
            return parse(htmlData);
        }
        return null;
    }

    private Integer getNumPolicy(Long totalNum ){
        if (totalNum>=1000){
            return 20;
        }else if (totalNum<1000&&totalNum>=500){
            return 15;
        }else if (totalNum<500 && totalNum>=100){
            return 10;
        }else {
            return 5;
        }
    }
    @Autowired
    private RedisCachemanager redisCachemanager;

    private void process(  List<Map<String, Object>> list) throws Exception{
        CaseMatchingReq.LawyerDetail req ;
        if (CollectionUtils.isEmpty(list)){
            return;
        }
        for (Map<String,Object> map: list){
            req = new CaseMatchingReq.LawyerDetail();
            req.setLawFirmName((String) map.get("lawyerFirm"));
            req.setLawyerName((String) map.get("lawyerName"));
            if(redisCachemanager.hGet("layerDetail",req.getLawyerName()+req.getLawFirmName())!=null){
                return;
            }
            LawyerDetailResp lawyerDetailResp = getLawyerDetail(req);
            redisCachemanager.hSet("layerDetail",req.getLawyerName()+req.getLawFirmName(),lawyerDetailResp);
        }
    }


    private CaseHtmlAnalysis parse(CaseHtmlData data) {
        CaseHtmlAnalysis analysis = new CaseHtmlAnalysis();
        try {
            PerceptronNERecognizer recognizer   = new PerceptronNERecognizer(Config.Model.NER);
            Segment                segment      = HanLPFactory.builder().segment(true);
            CaseHtmlData           caseHtmlData = new CaseHtmlData();
            caseHtmlData.setHtml(data.getHtml());
            caseHtmlData.setDocId(data.getDocId());
            analysis = caseHtmlAnalysisService.analysis(caseHtmlData, recognizer, segment);
            return analysis;
        } catch (Exception e) {
            e.printStackTrace();
            return analysis;
        }
    }

    private List<Map<String, Object>> getLawyerDetail(String lawyerNames) {
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object>       map;
        if (StringUtils.isNotBlank(lawyerNames)) {
            String[]             splitResult      = lawyerNames.split(";");
            TLawyerInfoCondition tLawyerCondition = new TLawyerInfoCondition();
            for (String lawyerName : splitResult) {
                map = Maps.newHashMap();
                map.put("lawyerName", lawyerName);
                tLawyerCondition.setXm(lawyerName);
                TLawyerInfo tLawyerInfo = tLawyerInfoDao.selectFirst(tLawyerCondition);
                if (tLawyerInfo != null) {
                    map.put("imgUrl", tLawyerInfo.getImgUrl());
                    map.put("lawFirm", tLawyerInfo.getLsswsmc());
                }
                result.add(map);
            }
        }
        return result;
    }

    static class CaseType {
        static String convert(int i) {
            if (i == 1) {
                return "刑事案件";
            } else if (i == 2) {
                return "民事案件";
            } else if (i == 3) {
                return "行政案件";
            } else if (i == 4) {
                return "赔偿案件";
            } else if (i == 5) {
                return "执行案件";
            } else {
                return "暂无";
            }
        }
    }

    @Override
    public PageInfoResp<CourtResp> getCourtList(CaseMatchingReq.CaseMatchingSearch req) {
        ViewBriefCourtCondition viewBriefCourtCondition = new ViewBriefCourtCondition();
        viewBriefCourtCondition.setBrief(req.getBriefStr());
        PageHelper.startPage(req.getPage(), req.getPerPage());
        PageHelper.orderBy("case_num desc");
        List<CourtResp>     viewBriefCourts = viewTableDao.getCourtList(viewBriefCourtCondition);
        PageInfo<CourtResp> pageInfo        = new PageInfo<>(viewBriefCourts);
        resetResult(pageInfo, req);
        return new PageInfoResp<>(pageInfo);
    }

    private void resetResult(PageInfo<CourtResp> pageInfo, CaseMatchingReq.CaseMatchingSearch req) {
        if (req.getPage() == 1) {
            Integer   caseCount = viewTableDao.getTotalCaseCount(req.getBriefStr());
            CourtResp all       = new CourtResp();
            all.setCaseNum(caseCount);
            all.setCourtName(ALL_STR);
            pageInfo.getList().add(0, all);
        }
    }

    @Override
    public PageInfoResp<LawyerFirmResp> getLawFirmList(CaseMatchingReq.LawFirmSearch req) {
        ViewBriefCourtLawfirmCondition condition = new ViewBriefCourtLawfirmCondition();
        condition.setBrief(req.getBriefStr());
        if (ALL_STR.equals(req.getCourtName())) {
            req.setCourtName(null);
        } else {
            condition.setCourtName(req.getCourtName());
        }
        PageHelper.startPage(req.getPage(), req.getPerPage());
        PageHelper.orderBy("case_num desc");
        List<LawyerFirmResp>     list     = viewTableDao.getLawFirmList(condition);
        PageInfo<LawyerFirmResp> pageInfo = new PageInfo<>(list);
        return new PageInfoResp<>(pageInfo);
    }

    @Override
    public PageInfoResp<LawyerForRankingListResp> getLawyerRankingList(CaseMatchingReq.CaseMatchingSearch req) {
        ViewBriefLawyerRankingCondition condition = new ViewBriefLawyerRankingCondition();
        condition.setBrief(req.getBriefStr());
        PageHelper.startPage(req.getPage(), req.getPerPage());
        PageHelper.orderBy("ranking_weight desc");
        List<LawyerForRankingListResp>     list     = viewTableDao.getLawyerRankingList(condition);
        PageInfo<LawyerForRankingListResp> pageInfo = new PageInfo<>(list);
        return new PageInfoResp<>(pageInfo);
    }

    @Override
    public PageInfoResp<LawyerForSearchingListResp> getLawyerList(CaseMatchingReq.LawyerSearch req) {
        ViewBriefLawfirmLawyerCondition condition = new ViewBriefLawfirmLawyerCondition();
        condition.setBrief(req.getBriefStr());
        condition.setLawyerFirm(req.getLawFirmName());
        PageHelper.startPage(req.getPage(), req.getPerPage());
        List<LawyerForSearchingListResp>     list     = viewTableDao.getLawyerList(condition);
        PageInfo<LawyerForSearchingListResp> pageInfo = new PageInfo<>(list);
        return new PageInfoResp<>(pageInfo);
    }
}
