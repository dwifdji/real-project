package com.tzCloud.core.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.tzCloud.arch.common.PageInfoResp;
import com.tzCloud.arch.common.constant.ElasticSearchConstant;
import com.tzCloud.arch.common.utils.DateUtil;
import com.tzCloud.core.common.SystemContent;
import com.tzCloud.core.condition.caseMatching.CaseHtmlDsrxxCondition;
import com.tzCloud.core.constant.CaseEnum;
import com.tzCloud.core.constant.SysConstant;
import com.tzCloud.core.dao.caseMatching.CaseHtmlDsrxxDao;
import com.tzCloud.core.dao.caseMatching.TLawyerInfoDao;
import com.tzCloud.core.dao.lawyerSearch.TParseLawyerInfoDao;
import com.tzCloud.core.facade.caseMatching.resp.CaseListForLawyerResp;
import com.tzCloud.core.facade.legalEngine.req.LawyerSearchReq;
import com.tzCloud.core.facade.legalEngine.resp.*;
import com.tzCloud.core.facade.legalEngine.vo.Brief;
import com.tzCloud.core.facade.legalEngine.vo.Case;
import com.tzCloud.core.hanLP.Config;
import com.tzCloud.core.hanLP.HanLPFactory;
import com.tzCloud.core.model.caseMatching.CaseHtmlDsrxx;
import com.tzCloud.core.model.caseMatching.TLawyerInfo;
import com.tzCloud.core.model.caseMatching.TTreeContent;
import com.tzCloud.core.model.lawyerSearch.TParseLawyerInfo;
import com.tzCloud.core.model.legalEngine.FieldCount;
import com.tzCloud.core.model.legalEngine.LawFirmAnalysis;
import com.tzCloud.core.service.CollectService;
import com.tzCloud.core.service.TParseLawyerInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.percentiles.hdr.InternalHDRPercentiles;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.ScriptSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.annotation.Untainted;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author xiaolei
 * @create 2019-04-23 14:02
 */
@Slf4j
@Service
public class TParseLawyerInfoServiceImpl implements TParseLawyerInfoService {

    @Resource
    private TParseLawyerInfoDao tParseLawyerInfoDao;
    @Resource
    private CollectService collectService;
    @Resource
    private CaseHtmlDsrxxDao caseHtmlDsrxxDao;
    @Resource
    private RestHighLevelClient client;
    @Autowired
    private ObjectMapper objectMapper;
    @Resource
    private TLawyerInfoDao tLawyerInfoDao;

    AtomicInteger poolNumber = new AtomicInteger(1);
    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 600,
            TimeUnit.SECONDS, new ArrayBlockingQueue<>(25), r -> new Thread(r,"TParseLawyerInfoService_pool" + poolNumber.getAndIncrement()));


    @Override
    public LawyerSearchVO searchList(LawyerSearchReq.SearchList req)
    {
        conditionPreProcess(req);
        PageHelper.startPage(req.getPage(), req.getPerPage());
        List<TParseLawyerInfo> tParseLawyerInfos = tParseLawyerInfoDao.searchList(req.getConditions(), req.getOrderBy());
        PageInfo<TParseLawyerInfo> pageInfo = new PageInfo<>(tParseLawyerInfos);
        return convertToLawyerSearchVO(pageInfo, req.getAccountId());
    }

    @Override
    public LawFirmAnalysisVO lawFirmAnalysis(String lawFirm) throws Exception {
        Segment segment = HanLPFactory.builder().segment(true);
        Map<String, Integer> judgeResultCount = new HashMap<>();
        Map<String, Integer> courtLevel       = new HashMap<>();
        Map<String, Integer> courtStatistics  = new HashMap<>();
        Map<String, Integer> dsrxxStatistics  = new HashMap<>();
        Map<String, Integer> judgeStatistics ;

        if (tParseLawyerInfoDao.searchByLawFirm(lawFirm).isEmpty()) {
            return null;
        }

        // 案由分布
        Future<List<FieldCount>> briefDistributedFuture = tParseLawyerInfoDao.getBriefDistributed(lawFirm);
        List<FieldCount> fieldCounts = briefDistributedFuture.get();
        Map<String, Integer> briefDistributed = fieldCounts.stream().collect(Collectors.toMap(FieldCount::getField1, FieldCount::getCount1));
        Map<String, Integer> sortedBriefDistributed = new LinkedHashMap<>();
        briefDistributed.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue()
                        .reversed()).forEachOrdered(e -> sortedBriefDistributed.put(e.getKey(), e.getValue()));

        // 判决胜诉率
        Future<LawFirmAnalysis> judgeResultCountFuture = tParseLawyerInfoDao.getJudgeResultCount(lawFirm);
        LawFirmAnalysis lawFirmAnalysis = judgeResultCountFuture.get();
        judgeResultCount.put("胜诉", lawFirmAnalysis.getWinCount());
        judgeResultCount.put("败诉", lawFirmAnalysis.getLoseCount());
//        judgeResultCount.put("drawCount", lawFirmAnalysis.getDrawCount());
        Map<String, Integer> sortedJudgeResultCount = new LinkedHashMap<>();
        judgeResultCount.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue()
                        .reversed()).forEachOrdered(e -> sortedJudgeResultCount.put(e.getKey(), e.getValue()));

        // 法院层级
//        Future<List<FieldCount>> courtLevelFuture = tParseLawyerInfoDao.getCourtLevel(lawFirm);
//        List<FieldCount> fieldCounts1 = courtLevelFuture.get();
//        courtLevel.put("基层法院", ((int) (fieldCounts1.stream().filter((p) -> p.getField2() != null && p.getField2().equals("基层法院")).count())) );
//        courtLevel.put("中级法院", ((int) (fieldCounts1.stream().filter((p) -> p.getField2() != null && p.getField2().equals("中级法院")).count())) );
//        courtLevel.put("高级法院", ((int) (fieldCounts1.stream().filter((p) -> p.getField2() != null && p.getField2().equals("高级法院")).count())));
        Aggregations courtAggregations = getCourtLevelCollectByES(lawFirm);
        if (courtAggregations != null) {
            putTerm(courtAggregations.get("courtType"), courtLevel);
        }
        Map<String, Integer> sortedCourtLevel = new LinkedHashMap<>();
        courtLevel.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue()
                        .reversed()).forEachOrdered(e -> sortedCourtLevel.put(e.getKey(), e.getValue()));

        // 承办法院
        if (courtAggregations != null) {
            putTerm(courtAggregations.get("courtCount"), courtStatistics);
        }
//        Map<String, List<FieldCount>> collect = fieldCounts1.stream().collect(Collectors.groupingBy(FieldCount::getField1));
//        courtStatistics = collect.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().size()));
        Map<String, Integer> sortedCourtStatistics = new LinkedHashMap<>();
        courtStatistics.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue()
                        .reversed()).forEachOrdered(e -> sortedCourtStatistics.put(e.getKey(), e.getValue()));

        // 代理客户
        Future<List<FieldCount>> dsrxxStatisticsFuture = tParseLawyerInfoDao.getDsrxxStatistics(lawFirm);
        List<FieldCount> fieldCounts3 = dsrxxStatisticsFuture.get();
//        Future<Map<String, Integer>> callFuture = threadPoolExecutor.submit(new DsrxxStatistics(fieldCounts3, segment));
//        dsrxxStatistics = callFuture.get();
        putDsrxxStatistics(fieldCounts3, segment, dsrxxStatistics);
        Map<String, Integer> sortedDsrxxStatistics = new LinkedHashMap<>();
        dsrxxStatistics.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue()
                        .reversed()).forEachOrdered(e -> sortedDsrxxStatistics.put(e.getKey(), e.getValue()));

        // 承办法官
        Future<List<FieldCount>> judgeStatisticsFuture = tParseLawyerInfoDao.getJudgeStatistics(lawFirm);
        List<FieldCount> fieldCounts2 = judgeStatisticsFuture.get();
        List<String> temp = new ArrayList<>();
        fieldCounts2.forEach(field -> {
            String field1 = field.getField1();
            if (field1.contains("审判长")) field1 = field1.substring(field1.indexOf("审判长") + 3);
            if (field1.contains(";")) field1 = field1.substring(0, field1.indexOf(";"));
            if (field1.contains("；")) field1 = field1.substring(0, field1.indexOf("；"));
            temp.add(field1);
        });
        Map<String, List<String>> collect1 = temp.stream().collect(Collectors.groupingBy(e -> e));
        judgeStatistics = collect1.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().size()));
        Map<String, Integer> sortedJudgeStatistics = new LinkedHashMap<>();
        judgeStatistics.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue()
                        .reversed()).forEachOrdered(e -> sortedJudgeStatistics.put(e.getKey(), e.getValue()));


        LawFirmAnalysisVO lawFirmAnalysisVO = new LawFirmAnalysisVO();
        lawFirmAnalysisVO.setBriefDistributed(sortedBriefDistributed);
        lawFirmAnalysisVO.setCourtLevel(sortedCourtLevel);
        lawFirmAnalysisVO.setCourtStatistics(sortedCourtStatistics);
        lawFirmAnalysisVO.setJudgeResultCount(sortedJudgeResultCount);
        lawFirmAnalysisVO.setJudgeStatistics(sortedJudgeStatistics);
        lawFirmAnalysisVO.setDsrxxStatistics(sortedDsrxxStatistics);
        return lawFirmAnalysisVO;
    }

    private void putDsrxxStatistics(List<FieldCount> fieldCounts, Segment segment, Map<String, Integer> dsrxxStatistics) {
//        List<String> docIds = fieldCounts.stream().map(FieldCount::getField1).collect(Collectors.toList());
//        SearchResponse searchResponse = getCaseDsrxxByES(docIds);
        int personCount=0, companyCount=0;
        for (FieldCount field : fieldCounts) {
            List<CaseHtmlDsrxx> caseDsrxxByES = getCaseDsrxxByES(field.getField1());
            for (CaseHtmlDsrxx caseHtmlDsrxx : caseDsrxxByES) {
                String identity = caseHtmlDsrxx.getIdentity();
                String name = caseHtmlDsrxx.getName();
                List<Term> identitySeg = segment.seg(identity);
                List<Term> nameSeg = segment.seg(name);
                List<String> identityNatureList = identitySeg.stream().map(t -> t.nature.toString()).collect(Collectors.toList());
                List<String> nameNatureList = nameSeg.stream().map(t -> t.nature.toString()).collect(Collectors.toList());
                if (field.getField2().equals(Config.Constant.ACTIVE_LAWYER)
                        && identityNatureList.contains(Config.IDENTITY.identity_p.name())) {
                    if (nameNatureList.contains("companyKey") || nameNatureList.contains("ntc") || nameNatureList.contains("nto"))
                        companyCount++;
                    else personCount++;
                }
                if (field.getField2().equals(Config.Constant.PASSIVE_LAWYER)
                        && identityNatureList.contains(Config.IDENTITY.identity_d.name())) {
                    if (nameNatureList.contains("companyKey") || nameNatureList.contains("ntc") || nameNatureList.contains("nto"))
                        companyCount++;
                    else personCount++;
                }
            }
        }
        dsrxxStatistics.put("自然人", personCount );
        dsrxxStatistics.put("非法人组织", companyCount );
    }

    private Aggregations getCourtLevelCollectByES(String lawFirm) {
        List<String> docIdsByES = getDocIdsByES(lawFirm);
        if (docIdsByES != null && !docIdsByES.isEmpty()) {
            return getCaseByES(docIdsByES);
        }
        return null;
    }

    private List<CaseHtmlDsrxx> getCaseDsrxxByES(String docId) {
        List<CaseHtmlDsrxx> list = new ArrayList<>();
        // 根据律所名查询律所信息
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(ElasticSearchConstant.CASE_DSRXX);
        searchRequest.types(ElasticSearchConstant.DEFAULT_TYPE);
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.should(QueryBuilders.matchQuery("docId",docId));
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(boolQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHit[] hits = search.getHits().getHits();
            if (hits.length > 0) {
                for (SearchHit hit : hits) {
                    Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                    sourceAsMap.remove("createTime");
                    sourceAsMap.remove("updateTime");
                    CaseHtmlDsrxx caseHtmlDsrxx = objectMapper.convertValue(sourceAsMap, CaseHtmlDsrxx.class);
                    list.add(caseHtmlDsrxx);
                }
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<String> getDocIdsByES(String lawFirm) {
        // 根据律所名查询律所信息
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(ElasticSearchConstant.LAW_FIRM_INDEX);
        searchRequest.types(ElasticSearchConstant.DEFAULT_TYPE);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchPhraseQuery("lawFirm", lawFirm));
        searchSourceBuilder.aggregation(AggregationBuilders.terms("docIds").field("lawyerStatistics.docIds"));
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            Aggregations aggregations = searchResponse.getAggregations();
            Terms terms = aggregations.get("docIds");
            List<? extends Terms.Bucket> buckets = terms.getBuckets();
            List<String> collect = buckets.stream().map(b -> (b).getKeyAsString()).collect(Collectors.toList());
            return collect;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Aggregations getCaseByES(List<String> docIds) {
        // 根据律所名查询律所信息
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(ElasticSearchConstant.CASE_INDEX);
        searchRequest.types(ElasticSearchConstant.DEFAULT_TYPE);
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        for (String docId : docIds) {
            if (docId == null) continue;
            boolQueryBuilder.should(QueryBuilders.matchQuery("docId",docId));
        }
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.aggregation(AggregationBuilders.terms("courtType").field("court.type"));
        searchSourceBuilder.aggregation(AggregationBuilders.terms("courtCount").field("court.name"));
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            return searchResponse.getAggregations();
//            putTerm(aggregations.get("courtType"), courtLevel);
//            putTerm(aggregations.get("courtCount"), courtLevel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private void putTerm(Terms terms, Map<String, Integer> courtLevel) {
        if (terms != null) {
            List<? extends Terms.Bucket> buckets = terms.getBuckets();
            for (Terms.Bucket bucket : buckets) {
                String keyAsString = bucket.getKeyAsString();
                long docCount = bucket.getDocCount();
                courtLevel.put(keyAsString, ((int) (docCount)));
            }
        }
    }


    @Override
    public PageInfoResp<LawyerInfoVO> searchLawyerListByLawFirm(String lawFirm, Integer accountId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<LawyerInfoVO> lawyerListByLawFirm = tParseLawyerInfoDao.getLawyerListByLawFirm(lawFirm);
        PageInfo<LawyerInfoVO> pageInfo = new PageInfo<>(lawyerListByLawFirm);
        List<LawyerInfoVO> list = pageInfo.getList();
        for (LawyerInfoVO lawyerInfoVO : list)
        {
            lawyerInfoVO.setWinRate(String.format("%.0f", Double.parseDouble(lawyerInfoVO.getWinRate())) + "%");
            lawyerInfoVO.setAvatarImgUrl(SystemContent.LAWYER_IMAG_URL);
            if(accountId == null) lawyerInfoVO.setAttentionFlag("0");
            else lawyerInfoVO.setAttentionFlag(collectService.isCollect(accountId, lawyerInfoVO.getId().toString(), CaseEnum.CollectTypeEum.LAWYER.name())?"1":"0");
        }
        PageInfoResp<LawyerInfoVO> pageInfoResp = new PageInfoResp<>();
        pageInfoResp.setTotal(pageInfo.getTotal());
        pageInfoResp.setList(list);
        pageInfoResp.setHasNextPage(pageInfo.isHasNextPage());
        return pageInfoResp;
    }

    @Override
    public List<LawyerInfoVO> searchListResultLawyerInfoVO(LawyerSearchReq.SearchList req) {
        List<TParseLawyerInfo> tParseLawyerInfos = tParseLawyerInfoDao.searchList(req.getConditions(), req.getOrderBy());
        return convertToLawyerInfoVO(tParseLawyerInfos);
    }

    @Override
    public LawyerSearchVO searchES(LawyerSearchReq.SearchList req) {
        // 处理请求参数
        conditionPreProcess(req);
        // searchRequest  QueryBuilders SearchSourceBuilder client
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(ElasticSearchConstant.LAWYER_INDEX);
        searchRequest.types(ElasticSearchConstant.DEFAULT_TYPE);
        // 构造query
        QueryBuilder queryBuilder = queryProcess(req);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.aggregation(AggregationBuilders.terms("provinceAggregation").field("lawFirmProvince"));
        searchSourceBuilder.aggregation(AggregationBuilders.terms("briefAggregation").field("caseBrief.brief"));
        String temp = req.getOrderBy().split(" ")[0];
        if (!temp.equals("caseCount")) {
            searchSourceBuilder.sort(new FieldSortBuilder(temp).order(SortOrder.fromString(req.getOrderBy().split(" ")[1])));
        }
        searchSourceBuilder.size(req.getPerPage());
        searchSourceBuilder.from(req.getPage());
        searchRequest.source(searchSourceBuilder);
        try {
            log.info("index: {}, query: {}",ElasticSearchConstant.LAWYER_INDEX, searchRequest.source().toString());
            SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
            return setLawyerSearchVOBySearchResponse(search, req);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void searchLawFirmByCondition() {
        Segment segment = HanLPFactory.builder().segment(true);
        List<TParseLawyerInfo> byLawyerFirmISNUll = tParseLawyerInfoDao.getByLawyerFirmISNUll();
        for (TParseLawyerInfo tParseLawyerInfo : byLawyerFirmISNUll) {
            if (StringUtils.isBlank(tParseLawyerInfo.getLawyerName()) || StringUtils.isBlank(tParseLawyerInfo.getLawFirm())) {
                continue;
            }
            Map<String, String> stringMap = UnusualDataProcessServiceImpl.lawFirmNameProcess(tParseLawyerInfo.getLawFirm(), segment);
            List<TLawyerInfo> lawyerByName = tLawyerInfoDao.getLawyerByName(tParseLawyerInfo.getLawyerName(), stringMap.get("lawFirm"));
            if (!lawyerByName.isEmpty()) {
                System.out.println(tParseLawyerInfo.toString());
            }
        }
    }

    @Override
    public LawFirmSearchVO searchLawFirmES(LawyerSearchReq.SearchLawFirmList req) {
        // 处理请求参数
        conditionProcess(req);
        // 拼装query
        // searchRequest  QueryBuilders SearchSourceBuilder client
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(ElasticSearchConstant.LAW_FIRM_INDEX);
        searchRequest.types(ElasticSearchConstant.DEFAULT_TYPE);
        // 构造query
        QueryBuilder queryBuilder = queryProcess(req);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilder);
        String[] temp = req.getOrderBy().split(" ");
        // 综合排序 根据团队规模、总案例数排序
        if (temp[0].equals("id")) {
            searchSourceBuilder.sort(new ScriptSortBuilder(new Script("doc['lawyerStatistics.id'].values.size()"),
                    ScriptSortBuilder.ScriptSortType.NUMBER).order(SortOrder.fromString(temp[1])));
        } else if (temp[0].equals("caseCount")) {
            searchSourceBuilder.sort(new ScriptSortBuilder(new Script("doc['lawyerStatistics.docIds'].values.size()"),
                    ScriptSortBuilder.ScriptSortType.NUMBER).order(SortOrder.fromString(temp[1])));
        } else if (temp[0].equals("foundTime")) {
            searchSourceBuilder.sort(new FieldSortBuilder(temp[0]).order(SortOrder.fromString(temp[1])));
        }
        searchSourceBuilder.size(req.getPerPage());
        searchSourceBuilder.from(req.getPage());
        searchRequest.source(searchSourceBuilder);
        // 查询
        try {
            log.info("index: {}, query: {}",ElasticSearchConstant.LAW_FIRM_INDEX, searchRequest.source().toString());
            SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
            System.out.println(searchRequest.source().toString());
            return setLawFirmSearchResult(search);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public LawFirmInfoVO getLawFirmIntro(String lawFirm) {
        // 查询es
        // searchRequest  QueryBuilders SearchSourceBuilder client
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(ElasticSearchConstant.LAW_FIRM_INDEX);
        searchRequest.types(ElasticSearchConstant.DEFAULT_TYPE);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery(CaseEnum.lawFirmSearchField.lawFirm.name(),lawFirm));
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
            if (search.getHits().getTotalHits() == 0) return null;
            SearchHits hits = search.getHits();
            Map<String, Object> sourceAsMap = hits.getHits()[0].getSourceAsMap();
            List<HashMap>  lawyerStatisticsList = (List<HashMap>) sourceAsMap.get("lawyerStatistics");
            List<String> brief = new ArrayList<>();
            for (HashMap map : lawyerStatisticsList) {
                LawyerStatistics lawyerStatistics = objectMapper.convertValue(map, LawyerStatistics.class);
                List<String> docIds = lawyerStatistics.docIds;
                List<String> briefArray = getBriefArray(docIds);
                if (briefArray != null) brief.addAll(briefArray);
            }
            LawFirmInfoVO lawFirmInfoVO = objectMapper.convertValue(sourceAsMap, LawFirmInfoVO.class);
            lawFirmInfoVO.setBriefArray(sortBriefArray(brief));
            lawFirmInfoVO.setLawyerCount(lawyerStatisticsList.size());
            lawFirmInfoVO.setCaseCount(brief.size());
            return lawFirmInfoVO;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public LawyerInfoVO getLawyerIntro(String lawyerId) {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(ElasticSearchConstant.LAWYER_INDEX);
        searchRequest.types(ElasticSearchConstant.DEFAULT_TYPE);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("id",lawyerId));
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = search.getHits();
            if (hits.getTotalHits() > 0) {
                SearchHit hit = hits.getHits()[0];
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                LawyerInfoVO lawyerInfoVO = objectMapper.convertValue(sourceAsMap, LawyerInfoVO.class);
                List<Map>  list = (List<Map>) sourceAsMap.get("caseBrief");
                String[] briefs = list.stream().map(m -> m.get("brief")).distinct().toArray(String[]::new);
                lawyerInfoVO.setCaseCount(list.size());
                lawyerInfoVO.setBriefArray(briefs);
                String lawFirmCity = lawyerInfoVO.getLawFirmCity();
                if (StringUtils.isBlank(lawFirmCity) ) {
                    lawyerInfoVO.setLawFirmCity((String) sourceAsMap.get("lawFirmProvince"));
                }
                return lawyerInfoVO;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public LawyerCaseStatisticsVO getLawyerCaseStatistics(String lawyerId) {
        // 查出律师相关的docId
        List<String> dodIdsByLawyerId = getDodIdsByLawyerId(lawyerId);
        if (dodIdsByLawyerId != null) {
            // 根据律所名查询律所信息
            Aggregations aggregationLawFirm = getAggregationLawFirm(dodIdsByLawyerId);
            if (aggregationLawFirm != null) {
                // 根据docId聚合案例数据
                return lawyerCaseStatistics(aggregationLawFirm);
            }
        }
        return null;
    }

    @Override
    public PageInfoResp<CaseListForLawyerResp> getLawyerCase(LawyerSearchReq.SearchLawyerCaseList req) {
        // 根据lawyerId查出律师
        List<String> dodIdsByLawyerId = getDodIdsByLawyerId(req.getLawyerId());
        if (dodIdsByLawyerId != null) {
            SearchResponse searchResponse = searchCaseByDocIdAndBrief(dodIdsByLawyerId, req.getBrief(), (req.getPage()==0?req.getPage():req.getPage()-1) * req.getPerPage(), req.getPerPage());
            if (searchResponse != null) {
                return convertPageResp(searchResponse);
            }
        }
        return null;
    }


    private PageInfoResp<CaseListForLawyerResp> convertPageResp(SearchResponse searchResponse) {
        PageInfoResp<CaseListForLawyerResp> pageInfoResp = new PageInfoResp<>();
        SearchHits hits = searchResponse.getHits();
        List<CaseListForLawyerResp> list = new ArrayList<>(((int) (hits.getTotalHits())));
        if (hits.getTotalHits() > 0) {
            SearchHit[] hits1 = hits.getHits();
            CaseListForLawyerResp caseListForLawyerResp ;
            for (SearchHit hit : hits1) {
                caseListForLawyerResp = new CaseListForLawyerResp();
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                Case aCase = objectMapper.convertValue(sourceAsMap, Case.class);
                caseListForLawyerResp.setDocId(aCase.getDocId());
                caseListForLawyerResp.setCaseTitle(aCase.getTitle());
                caseListForLawyerResp.setCaseProgram(CaseEnum.TrialRound.getValueByKey(aCase.getTrialRound()) );
                caseListForLawyerResp.setCaseDate(DateUtil.formatDate2Str(aCase.getJudgementDate(), DateUtil.NORM_DATE_PATTERN));
                caseListForLawyerResp.setBrief(aCase.getBrief()==null?null: aCase.getBrief().getName());
                caseListForLawyerResp.setCourtName(aCase.getCourt()==null?null:aCase.getCourt().getName());
                caseListForLawyerResp.setCaseNo(aCase.getCaseNumber());
                caseListForLawyerResp.setType(CaseEnum.CaseType.getValueByKey(aCase.getType()));
                caseListForLawyerResp.setJudgementType(CaseEnum.JudgementType.getValueByKey(aCase.getJudgementType()));
                list.add(caseListForLawyerResp);
            }
        }
        pageInfoResp.setTotal(searchResponse.getHits().totalHits);
        pageInfoResp.setList(list);
        return pageInfoResp;
    }

    private SearchResponse searchCaseByDocIdAndBrief(List<String> docIds, String brief, int page, int perPage ) {
        // 根据律所名查询律所信息
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(ElasticSearchConstant.CASE_INDEX);
        searchRequest.types(ElasticSearchConstant.DEFAULT_TYPE);
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        docIds.removeIf(StringUtils::isEmpty);
        boolQueryBuilder.filter(QueryBuilders.termsQuery("docId", docIds));
        if (StringUtils.isNotBlank(brief)) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("brief.name",brief));
        }
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.from(page);
        searchSourceBuilder.size(perPage);
        searchSourceBuilder.query(boolQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        try {
            return client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private LawyerCaseStatisticsVO lawyerCaseStatistics(Aggregations aggregationLawFirm) {
        LawyerCaseStatisticsVO vo = new LawyerCaseStatisticsVO();
        List<Map<String, Object>> caseTypeList         = getCaseTypeList(aggregationLawFirm.get("caseTypeList"));
        List<Map<String, Object>> courtList            = getCourtList(aggregationLawFirm.get("courtList"));
        List<Map<String, Object>> locationList         = getLocationList(aggregationLawFirm.get("locationList"));
        List<Map<String, Object>> caseCountByMonthList = getCaseCountByMonthList(aggregationLawFirm.get("caseCountByMonthList"));
        vo.setCaseTypeList(caseTypeList);
        vo.setCourtList(courtList);
        vo.setLocationList(locationList);
        vo.setCaseCountByMonthList(caseCountByMonthList);
        return vo;
    }

    private Map<String,Long> formatDate(Map<String, Long> caseCountByMonthList) {
        Map<String,Long> map = new LinkedHashMap<>();
        SimpleDateFormat format =  new SimpleDateFormat( "yyyy-MM" );
        for (Iterator<Map.Entry<String, Long>> iterator = caseCountByMonthList.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry<String, Long> next = iterator.next();
            String nextKey = next.getKey();
            Long nextValue = next.getValue();
            String key = format.format(Long.valueOf(nextKey));
            map.put(key, nextValue);
        }
        return map;
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> getCaseCountByMonthList(Terms terms) {
        List<? extends Terms.Bucket> buckets = terms.getBuckets();
        buckets.sort(Comparator.comparing(Terms.Bucket::getDocCount).reversed());
        List <Map<String, Object>> list      = new ArrayList<>(buckets.size());
        SimpleDateFormat format              =  new SimpleDateFormat( "yyyy-MM" );
        Map map;
        for (Terms.Bucket bucket : buckets) {
            map = new HashMap(2);
            map.put("month"   , format.format(Long.valueOf(bucket.getKeyAsString())));
            map.put("caseNum" , bucket.getDocCount());
            list.add(map);
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> getLocationList(Terms terms) {
        List<? extends Terms.Bucket> buckets = terms.getBuckets();
        buckets.sort(Comparator.comparing(Terms.Bucket::getDocCount).reversed());
        List <Map<String, Object>> list      = new ArrayList<>(buckets.size());
        Map map;
        for (Terms.Bucket bucket : buckets) {
            map = new HashMap(2);
            map.put("city"   , bucket.getKeyAsString());
            map.put("caseNum" , bucket.getDocCount());
            list.add(map);
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> getCourtList(Terms terms) {
        List<? extends Terms.Bucket> buckets = terms.getBuckets();
        buckets.sort(Comparator.comparing(Terms.Bucket::getDocCount).reversed());
        List <Map<String, Object>> list      = new ArrayList<>(buckets.size());
        Map map;
        for (Terms.Bucket bucket : buckets) {
            map = new HashMap(2);
            map.put("courtName"   , bucket.getKeyAsString());
            map.put("caseNum" , bucket.getDocCount());
            list.add(map);
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> getCaseTypeList(Terms terms) {
        List<? extends Terms.Bucket> buckets = terms.getBuckets();
        buckets.sort(Comparator.comparing(Terms.Bucket::getDocCount).reversed());
        List <Map<String, Object>> list      = new ArrayList<>(buckets.size());
        Map map;
        for (Terms.Bucket bucket : buckets) {
            map = new HashMap(2);
            map.put("brief"   , bucket.getKeyAsString());
            map.put("caseNum" , bucket.getDocCount());
            list.add(map);
        }
        return list;
    }

    private Map<String, Long> getAggregationMap(Terms terms) {
        List<? extends Terms.Bucket> buckets = terms.getBuckets();
        Map<String, Long> collect = buckets.stream().collect(Collectors.toMap(Terms.Bucket::getKeyAsString, Terms.Bucket::getDocCount));
        Map<String, Long> sortedCollect = new LinkedHashMap<>();
        collect.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue()
                        .reversed()).forEachOrdered(e -> sortedCollect.put(e.getKey(), e.getValue()));
        return sortedCollect;
    }


    private Aggregations getAggregationLawFirm(List<String> docIds) {
        // 根据律所名查询律所信息
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(ElasticSearchConstant.CASE_INDEX);
        searchRequest.types(ElasticSearchConstant.DEFAULT_TYPE);
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        for (String docId : docIds) {
            if (docId == null) continue;
            boolQueryBuilder.should(QueryBuilders.matchQuery("docId",docId));
        }
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.aggregation(AggregationBuilders.terms("caseTypeList").field("brief.name").size(docIds.size()));
        searchSourceBuilder.aggregation(AggregationBuilders.terms("courtList").field("court.name").size(docIds.size()));
        searchSourceBuilder.aggregation(AggregationBuilders.terms("locationList").field("court.province").size(docIds.size()));
        searchSourceBuilder.aggregation(AggregationBuilders.terms("caseCountByMonthList").field("judgementDate").size(docIds.size()));
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            return searchResponse.getAggregations();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<String> getDodIdsByLawyerId(String lawyerId) {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(ElasticSearchConstant.LAWYER_INDEX);
        searchRequest.types(ElasticSearchConstant.DEFAULT_TYPE);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchPhraseQuery("id", lawyerId));
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = searchResponse.getHits();
            if (hits.getTotalHits() > 0) {
                SearchHit hit = hits.getHits()[0];
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                TParseLawyerInfo tParseLawyerInfo = objectMapper.convertValue(sourceAsMap, TParseLawyerInfo.class);
                List<DocVO> caseBrief = tParseLawyerInfo.getCaseBrief();
                return caseBrief.stream().map(DocVO::getDocId).collect(Collectors.toList());
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    private List<String> sortBriefArray(List<String> brief) {
        Map<String, Long> collect = brief.stream().collect(Collectors.groupingBy(String::toString, Collectors.counting()));
        List<Map.Entry<String,Long>> list = new ArrayList<>(collect.entrySet());
        list.sort(Comparator.comparing(Map.Entry::getValue));
        Collections.reverse(list);
        return list.stream().map(Map.Entry::getKey).collect(Collectors.toList());
    }

    private List<String> getBriefArray(List<String> docIds) {
        if (docIds == null || docIds.isEmpty()) return  null;
        List<String> list = new ArrayList<>(docIds.size());
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(ElasticSearchConstant.CASE_INDEX);
        searchRequest.types(ElasticSearchConstant.DEFAULT_TYPE);
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        for (String docId : docIds) {
            if (docId == null) continue;
            boolQueryBuilder.should(QueryBuilders.matchQuery("docId",docId));
        }
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(boolQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = search.getHits();
            SearchHit[] hits1 = hits.getHits();
            for (SearchHit searchHit : hits1) {
                Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
                sourceAsMap.remove("judgementDate");
                Case aCase = objectMapper.convertValue(sourceAsMap, Case.class);
                Brief brief = aCase.getBrief();
                TTreeContent o =(TTreeContent) SysConstant.briefTreeContents.get(brief.getFirstId());
                list.add(o.getKeyWord());
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private LawFirmSearchVO setLawFirmSearchResult(SearchResponse search) {
        SearchHits hits = search.getHits();
        SearchHit[] hits1 = hits.getHits();
        Set<String> provinceSet = Stream.of(hits1).map(t ->(String) t.getSourceAsMap().get("lawFirmProvince")).collect(Collectors.toSet());
        List<LawFirmVO> lawFirmVOList = new ArrayList<>(hits1.length);
        for (SearchHit searchHit : hits1) {
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
            List<HashMap>  lawyerStatisticsList = (List<HashMap>) sourceAsMap.get("lawyerStatistics");
            int caseCount = 0;
            for(HashMap map : lawyerStatisticsList) {
                List docIds =(List) map.get("docIds");
                if (docIds != null) {
                    caseCount += docIds.size();
                }
            }
//            List<Integer> caseCountList = lawyerStatisticsList.stream().map(hm ->(Integer) hm.get("caseCount")).collect(Collectors.toList());
//            int caseCount = caseCountList.stream().mapToInt(Integer::intValue).sum();
            LawFirmVO lawFirmVO = objectMapper.convertValue(sourceAsMap, LawFirmVO.class);
            lawFirmVO.setLawyerNum(lawyerStatisticsList.size());
            lawFirmVO.setCaseCount(caseCount);
            lawFirmVOList.add(lawFirmVO);
        }
        LawFirmSearchVO lawFirmSearchVO = new LawFirmSearchVO();
        lawFirmSearchVO.setTotal(hits.getTotalHits());
        lawFirmSearchVO.setProvinceSet(provinceSet);
        lawFirmSearchVO.setLawFirmVOList(lawFirmVOList);
        lawFirmSearchVO.setFoundTimeList(Arrays.asList("1","2","3"));
        lawFirmSearchVO.setLawyerNumList(Arrays.asList("1","2","3","4"));
        return lawFirmSearchVO;
    }

    private QueryBuilder queryProcess(LawyerSearchReq.SearchLawFirmList req) {
        // 查询条件
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (StringUtils.isNotBlank(req.getConditions().getFoundTime())) {
            RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(CaseEnum.lawFirmSearchField.foundTime.name());
            if (req.getConditions().getFoundTime_begin() != null) rangeQueryBuilder = rangeQueryBuilder.gte(req.getConditions().getFoundTime_begin());
            if (req.getConditions().getFoundTime_end()   != null) rangeQueryBuilder = rangeQueryBuilder.lte(req.getConditions().getFoundTime_end());
            boolQueryBuilder.must(rangeQueryBuilder);
        }
        if (StringUtils.isNotBlank(req.getConditions().getLawyerNum())) {
            String idOrCode = "";
            if (req.getConditions().getLawyerNum_begin() != null) idOrCode = "doc['lawyerStatistics.id'].values.size() > " + req.getConditions().getLawyerNum_begin();
            if (req.getConditions().getLawyerNum_end()   != null) idOrCode = StringUtils.isBlank(idOrCode)? "doc['lawyerStatistics.docIds'].values.size() < " + req.getConditions().getLawyerNum_end()
                    : idOrCode + "&&" + "doc['lawyerStatistics.docIds'].values.size() < " + req.getConditions().getLawyerNum_end();
            boolQueryBuilder.filter(QueryBuilders.scriptQuery(new Script(idOrCode)));
        }
        if (StringUtils.isNotBlank(req.getConditions().getSearchWord())) {
            boolQueryBuilder.must(QueryBuilders.wildcardQuery(CaseEnum.lawFirmSearchField.lawFirm.name(), "*"+req.getConditions().getSearchWord()+"*"));
        }
        if (StringUtils.isNotBlank(req.getConditions().getProvince())) {
            boolQueryBuilder.must(QueryBuilders.matchPhraseQuery(CaseEnum.lawFirmSearchField.lawFirmProvince.name(),req.getConditions().getProvince()));
        }
        return boolQueryBuilder;
    }

    private void conditionProcess(LawyerSearchReq.SearchLawFirmList req) {
        if (req.getConditions().getFoundTime() != null) {
            if ("1".equals(req.getConditions().getFoundTime())) {
                req.getConditions().setFoundTime_end("2000-01-01");
            } else if ("2".equals(req.getConditions().getFoundTime())) {
                req.getConditions().setFoundTime_begin("2000-01-01");
                req.getConditions().setFoundTime_end("2010-01-01");
            } else if ("3".equals(req.getConditions().getFoundTime())) {
                req.getConditions().setFoundTime_begin("2010-01-01");
            }
        }
        if (req.getConditions().getLawyerNum() != null) {
            if ("1".equals(req.getConditions().getLawyerNum())) {
                req.getConditions().setLawyerNum_end(20);
            } else if ("2".equals(req.getConditions().getLawyerNum())) {
                req.getConditions().setLawyerNum_begin(20);
                req.getConditions().setLawyerNum_end(50);
            } else if ("3".equals(req.getConditions().getLawyerNum())) {
                req.getConditions().setLawyerNum_begin(50);
                req.getConditions().setLawyerNum_end(150);
            } else if ("4".equals(req.getConditions().getLawyerNum())) {
                req.getConditions().setLawyerNum_begin(150);
            }
        }
        req.setOrderBy(CaseEnum.LawyFirmSearchOrderBy.convertKey(req.getOrderBy()));
        req.setPage(req.getPage()-1);
    }

    private LawyerSearchVO setLawyerSearchVOBySearchResponse(SearchResponse search, LawyerSearchReq.SearchList req) {
        // 获取聚合数据
        Aggregations aggregations = search.getAggregations();
        Terms provinceAggregation = aggregations.get("provinceAggregation");
        Terms briefAggregation    = aggregations.get("briefAggregation");
        List<? extends Terms.Bucket> briefBuckets = briefAggregation.getBuckets();
        Set<String> brief = briefBuckets.stream().map(e ->(String) (e).getKey()).collect(Collectors.toSet());
        List<? extends Terms.Bucket> provinceBuckets = provinceAggregation.getBuckets();
        Map<String, Long> province = provinceBuckets.stream().collect(Collectors.toMap(e ->(String) e.getKey(), Terms.Bucket::getDocCount));
        List<NameValueVO> provinceList = new LinkedList<>();
        // 转成name value
        province.entrySet().forEach(sle -> {
            NameValueVO nameValueVO = new NameValueVO();
            nameValueVO.setName(sle.getKey());
            nameValueVO.setValue(sle.getValue().toString());
            provinceList.add(nameValueVO);

        });
        // 获取查询到结果
        SearchHits hits = search.getHits();
        List<LawyerInfoVO> lawyerInfoVOLis = new ArrayList<>(hits.getHits().length);
        for (SearchHit hit : hits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            LawyerInfoVO lawyerInfoVO = objectMapper.convertValue(sourceAsMap, LawyerInfoVO.class);
            if (StringUtils.isBlank(lawyerInfoVO.getLawFirmCity()) && sourceAsMap.get("lawFirmProvince") != null) lawyerInfoVO.setLawFirmCity((String) sourceAsMap.get("lawFirmProvince"));
            // 拿到map
            List<HashMap> caseBrief =(List<HashMap>) hit.getSourceAsMap().get("caseBrief");
            String[] objects =caseBrief.stream().map(hm -> {
                DocVO docVO = objectMapper.convertValue(hm, DocVO.class);
                return docVO.getBrief();
            }).distinct().toArray(String[]::new);
            lawyerInfoVO.setCaseCount(caseBrief.size());
            lawyerInfoVO.setBriefArray(objects);
            if (req.getAccountId() == null)
                lawyerInfoVO.setAttentionFlag("0");
            else
                lawyerInfoVO.setAttentionFlag(collectService.isCollect(req.getAccountId(), String.valueOf(lawyerInfoVO.getId()) , CaseEnum.CollectTypeEum.LAWYER.name())? "1" : "0");
            lawyerInfoVOLis.add(lawyerInfoVO);
            if (req.getOrderBy().split(" ")[0].equals("caseCount")) {
                if (req.getOrderBy().split(" ")[1].equals("asc")) {
                    lawyerInfoVOLis.sort(Comparator.comparing(LawyerInfoVO::getCaseCount));
                } else {
                    lawyerInfoVOLis.sort((s1, s2) -> s2.getCaseCount().compareTo(s1.getCaseCount()));
                }
            }
        }
        LawyerSearchVO lawyerSearchVO = new LawyerSearchVO();
        lawyerSearchVO.setBriefList(brief);
        lawyerSearchVO.setTotal(hits.getTotalHits());
//        lawyerSearchVO.setLawFirmProvinceMap(province);
        lawyerSearchVO.setLawFirmProvinceList(provinceList);
        lawyerSearchVO.setYearsList(Arrays.asList(1,2,3,4));
        lawyerSearchVO.setLawyerInfoVOList(lawyerInfoVOLis);
        // TODO: 2019/5/13
//        lawyerSearchVO.setHasNextPage();
        return lawyerSearchVO;
    }

    private QueryBuilder queryProcess(LawyerSearchReq.SearchList req) {
        // 查询条件
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (req.getConditions().getYears_begin() != null || req.getConditions().getYears_end() != null) {
            RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(CaseEnum.lawyerSearchField.years.name());
            if (req.getConditions().getYears_begin() != null) rangeQueryBuilder = rangeQueryBuilder.gte(req.getConditions().getYears_begin());
            if (req.getConditions().getYears_end()   != null) rangeQueryBuilder = rangeQueryBuilder.lte(req.getConditions().getYears_end());
            boolQueryBuilder.must(rangeQueryBuilder);
        }
        if (StringUtils.isNotBlank(req.getConditions().getBrief())) {
            boolQueryBuilder.must(QueryBuilders.matchPhraseQuery("caseBrief.brief",req.getConditions().getBrief()));
        }
        if (StringUtils.isNotBlank(req.getConditions().getSearchWord())) {
            boolQueryBuilder.must(QueryBuilders.matchPhraseQuery(CaseEnum.lawyerSearchField.lawyerName.name(), req.getConditions().getSearchWord()));
        }
        if (StringUtils.isNotBlank(req.getConditions().getLawFirmProvince())) {
            boolQueryBuilder.must(QueryBuilders.matchPhraseQuery(CaseEnum.lawyerSearchField.lawFirmProvince.name(),req.getConditions().getLawFirmProvince()));
        }
        return boolQueryBuilder;
    }

    private Map<String, Integer> getDsrxxStatistics(List<FieldCount> fieldCounts3, Segment segment) {
        Map<String, Integer> dsrxxStatistics  = new HashMap<>();
        int personCount=0, companyCount=0;
        for (FieldCount field : fieldCounts3) {
            CaseHtmlDsrxxCondition condition = new CaseHtmlDsrxxCondition();
            condition.setDocId(field.getField1());
            // TODO: 2019/6/17 将case_html_dsrxx 放入es， 这里从es查询
            List<CaseHtmlDsrxx> caseHtmlDsrxxes = caseHtmlDsrxxDao.selectList(condition);
            for (CaseHtmlDsrxx caseHtmlDsrxx : caseHtmlDsrxxes) {
                String identity = caseHtmlDsrxx.getIdentity();
                String name = caseHtmlDsrxx.getName();
                List<Term> identitySeg = segment.seg(identity);
                List<Term> nameSeg = segment.seg(name);
                List<String> identityNatureList = identitySeg.stream().map(t -> t.nature.toString()).collect(Collectors.toList());
                List<String> nameNatureList = nameSeg.stream().map(t -> t.nature.toString()).collect(Collectors.toList());
                if (field.getField2().equals(Config.Constant.ACTIVE_LAWYER)
                        && identityNatureList.contains(Config.IDENTITY.identity_p.name())) {
                    if (nameNatureList.contains("companyKey") || nameNatureList.contains("ntc") || nameNatureList.contains("nto"))
                        companyCount++;
                    else personCount++;
                }
                if (field.getField2().equals(Config.Constant.PASSIVE_LAWYER)
                        && identityNatureList.contains(Config.IDENTITY.identity_d.name())) {
                    if (nameNatureList.contains("companyKey") || nameNatureList.contains("ntc") || nameNatureList.contains("nto"))
                        companyCount++;
                    else personCount++;
                }
            }
        }
        dsrxxStatistics.put("自然人", personCount );
        dsrxxStatistics.put("非法人组织", companyCount );
        return dsrxxStatistics;
    }

    private void conditionPreProcess(LawyerSearchReq.SearchList req)
    {
        if (req.getConditions().getYears() != null)
        {
            // 3年以下
            if (req.getConditions().getYears() == 1)
                req.getConditions().setYears_end(3);
            // 3~5年
            if (req.getConditions().getYears() == 2)
            {
                req.getConditions().setYears_begin(3);
                req.getConditions().setYears_end(5);
            }
            // 5~10年
            if (req.getConditions().getYears() == 3)
            {
                req.getConditions().setYears_begin(5);
                req.getConditions().setYears_end(10);
            }
            // 10年以上
            if (req.getConditions().getYears() == 4)
            {
                req.getConditions().setYears_begin(10);
            }
        }
        req.setOrderBy(CaseEnum.LawyerSearchOrderBy.convertKey(req.getOrderBy()));
        req.setPage(req.getPage()-1);
    }


    private  List<LawyerInfoVO> convertToLawyerInfoVO(List<TParseLawyerInfo> tParseLawyerInfos)
    {
        List<LawyerInfoVO> lawyerInfoVOList = new ArrayList<>();
        for (TParseLawyerInfo tParseLawyerInfo : tParseLawyerInfos)
        {
            LawyerInfoVO lawyerInfoVO = new LawyerInfoVO();
            BeanUtils.copyProperties(tParseLawyerInfo, lawyerInfoVO);
            if (tParseLawyerInfo.getBrief() != null)
                lawyerInfoVO.setBriefArray(tParseLawyerInfo.getBrief().split(","));
            lawyerInfoVOList.add(lawyerInfoVO);
        }
        return lawyerInfoVOList;
    }

    private LawyerSearchVO setLawyerSearchVO(List<LawyerInfoVO> lawyerInfoVOList, Integer accountId)
    {
        LawyerSearchVO lawyerSearchVO = new LawyerSearchVO();
        Set<String>    briefSet       = new LinkedHashSet<>();
        List<Integer>  yearsList      = Arrays.asList(1,2,3,4);
        Map<String, Long> lawFirmProvinceMap = new HashMap<>();
        for (LawyerInfoVO lawyerInfoVO : lawyerInfoVOList) {
            if (accountId == null)
                lawyerInfoVO.setAttentionFlag("0");
            else
                lawyerInfoVO.setAttentionFlag(collectService.isCollect(accountId, String.valueOf(lawyerInfoVO.getId()) , CaseEnum.CollectTypeEum.LAWYER.name())? "1" : "0");
            if (lawyerInfoVO.getBriefArray() != null && lawyerInfoVO.getBriefArray().length > 0)
                briefSet.addAll(Arrays.asList(lawyerInfoVO.getBriefArray()));
            lawFirmProvinceMap.merge(lawyerInfoVO.getLawFirmCity(), 1l, (oldValue, newValue) -> lawFirmProvinceMap.get(lawyerInfoVO.getLawFirmCity())+1);
        }
        lawyerSearchVO.setLawyerInfoVOList(lawyerInfoVOList);
        lawyerSearchVO.setBriefList(briefSet);
        lawyerSearchVO.setLawFirmProvinceMap(lawFirmProvinceMap);
        lawyerSearchVO.setYearsList(yearsList);
        return lawyerSearchVO;
    }

    private  LawyerSearchVO convertToLawyerSearchVO(PageInfo<TParseLawyerInfo> pageInfo, Integer accountId)
    {
        LawyerSearchVO lawyerSearchVO = new LawyerSearchVO();
        List<LawyerInfoVO> lawyerInfoVOList = new ArrayList<>();
        Set<String> briefSet = new LinkedHashSet<>();
        List<Integer> yearsList = Arrays.asList(1,2,3,4);
        Map<String, Long> lawFirmProvinceMap = new HashMap<>();
        for (TParseLawyerInfo tParseLawyerInfo : pageInfo.getList())
        {
            LawyerInfoVO lawyerInfoVO = new LawyerInfoVO();
            BeanUtils.copyProperties(tParseLawyerInfo, lawyerInfoVO);
            if (accountId == null)
                lawyerInfoVO.setAttentionFlag("0");
            else
                lawyerInfoVO.setAttentionFlag(collectService.isCollect(accountId, String.valueOf(tParseLawyerInfo.getId()) , CaseEnum.CollectTypeEum.LAWYER.name())? "1" : "0");
            if (tParseLawyerInfo.getBrief() != null)
            {
                lawyerInfoVO.setBriefArray(tParseLawyerInfo.getBrief().split(","));
                briefSet.addAll(Arrays.asList(tParseLawyerInfo.getBrief().split(",")));
            }
            lawyerInfoVOList.add(lawyerInfoVO);
            // 计算省份出现的次数l
            lawFirmProvinceMap.merge(tParseLawyerInfo.getLawFirmProvince(), 1l, (oldValue, newValue) -> lawFirmProvinceMap.get(tParseLawyerInfo.getLawFirmProvince())+1);
        }
        lawyerSearchVO.setLawyerInfoVOList(lawyerInfoVOList);
        lawyerSearchVO.setTotal(pageInfo.getTotal());
        lawyerSearchVO.setHasNextPage(pageInfo.isHasNextPage());
        lawyerSearchVO.setBriefList(briefSet);
        lawyerSearchVO.setLawFirmProvinceMap(lawFirmProvinceMap);
        lawyerSearchVO.setYearsList(yearsList);
        return lawyerSearchVO;
    }

    class DsrxxStatistics implements Callable<Map<String, Integer>>
    {
        private List<FieldCount> fieldCounts3;
        private Segment segment;
        public DsrxxStatistics(List<FieldCount> fieldCounts3, Segment segment) {
            this.fieldCounts3 = fieldCounts3;
            this.segment = segment;
        }

        @Override
        public Map<String, Integer> call() throws Exception {
            return getDsrxxStatistics(fieldCounts3, segment);
        }
    }
}
