package com.tzCloud.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.tzCloud.arch.common.ResponseModel;
import com.tzCloud.arch.common.constant.ElasticSearchConstant;
import com.tzCloud.arch.common.constant.RedisKeyConstant;
import com.tzCloud.arch.common.enums.ApiCallResult;
import com.tzCloud.arch.core.redis.RedisCachemanager;
import com.tzCloud.core.condition.lawyerSearch.TParseLawyerInfoCondition;
import com.tzCloud.core.constant.CaseEnum;
import com.tzCloud.core.constant.CourtEnum;
import com.tzCloud.core.constant.SysConstant;
import com.tzCloud.core.dao.caseMatching.CaseHtmlDataDao;
import com.tzCloud.core.dao.caseMatching.TLawyerDataDao;
import com.tzCloud.core.dao.caseMatching.TLawyerInfoDao;
import com.tzCloud.core.dao.caseMatching.TTreeContentDao;
import com.tzCloud.core.dao.lawyerSearch.TParseLawyerInfoDao;
import com.tzCloud.core.dao.legalEngine.TCaseDao;
import com.tzCloud.core.dao.legalEngine.TJudgePersonDao;
import com.tzCloud.core.dao.legalEngine.TLawyerFirmInfoDao;
import com.tzCloud.core.dao.view.ViewTableDao;
import com.tzCloud.core.exception.BusinessException;
import com.tzCloud.core.facade.legalEngine.req.CaseSearchCondition;
import com.tzCloud.core.facade.legalEngine.req.CaseSearchReq;
import com.tzCloud.core.facade.legalEngine.vo.*;
import com.tzCloud.core.model.caseMatching.CaseHtmlAnalysis;
import com.tzCloud.core.model.caseMatching.CaseHtmlData;
import com.tzCloud.core.model.caseMatching.TTreeContent;
import com.tzCloud.core.model.lawyerSearch.TParseLawyerInfo;
import com.tzCloud.core.model.legalEngine.TCase;
import com.tzCloud.core.model.legalEngine.TJudgePerson;
import com.tzCloud.core.model.legalEngine.TLawyerFirmInfo;
import com.tzCloud.core.service.CaseService;
import com.tzCloud.core.service.CollectService;
import com.tzCloud.core.service.ViewTableService;
import com.tzCloud.core.utils.AESUtil;
import com.tzCloud.core.utils.RespConvertUtil;
import com.tzCloud.core.utils.WenshuHtmlSaveUntil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author xdrodger
 * @Title: CaseServiceImpl
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019/4/24 16:34
 */
@Slf4j
@Service
public class CaseServiceImpl implements CaseService {

    @Autowired
    private TCaseDao caseDao;
    @Autowired
    private TTreeContentDao treeContentDao;
    @Autowired
    private RestHighLevelClient client;
    @Autowired
    private CourtServiceImpl courtService;
    @Resource
    private RedisCachemanager redisCachemanager;
    @Autowired
    private TJudgePersonDao judgePersonDao;
    @Resource
    private ViewTableDao viewTableDao;
    @Resource
    private CaseHtmlDataDao caseHtmlDataDao;
    @Autowired
    private ViewTableService viewTableService;
    @Autowired
    private TParseLawyerInfoDao parseLawyerInfoDao;
    @Autowired
    private TLawyerInfoDao lawyerInfoDao;
    @Autowired
    private TLawyerFirmInfoDao lawyerFirmInfoDao;
    @Autowired
    private CollectService collectService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TLawyerDataDao tLawyerDataDao;

    @Override
    public ResponseModel searchCase(CaseSearchReq.SearchListReq req) {
        Map<String, Object> data = new HashMap<>();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        QueryBuilder q = assembleQuery(req);
        log.info("query={}", q.toString());
        searchSourceBuilder.query(q);
        searchSourceBuilder.highlighter(assembleHighlight());
        if (!req.isExcludeSidebar() && req.getPage() == 1) {
            assembleAggregation(searchSourceBuilder);
        }
        String orderBy = req.getOrderBy();
        if (StringUtils.isNotBlank(orderBy)) {
            if ("judgementDate_desc".equals(orderBy)) {
                searchSourceBuilder.sort("judgementDate", SortOrder.DESC);
            } else if ("courtType_desc".equals(orderBy)) {
                searchSourceBuilder.sort("court.type", SortOrder.DESC);
            }
        }
        searchSourceBuilder.from((req.getPage() - 1) * req.getPerPage());
        searchSourceBuilder.size(req.getPerPage());
        SearchRequest request = new SearchRequest();
        request.indices(ElasticSearchConstant.CASE_INDEX);
        request.source(searchSourceBuilder);

        //log.info("searchSourceBuilder={}", searchSourceBuilder.toString());
        //log.info("aggregations={}", searchSourceBuilder.aggregations().toString());
        SearchResponse response;
        try {
            response = client.search(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseModel.fail();
        }
        SearchHits hits = response.getHits();
        Iterator<SearchHit> itr = hits.iterator();
        List<CaseVo> list = new ArrayList<>();
        while (itr.hasNext()) {
            SearchHit hit = itr.next();
            Case _case = JSON.parseObject(JSON.toJSONString(hit.getSourceAsMap()), Case.class);
            CaseVo caseVo = RespConvertUtil.convertToCaseVo(_case);
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField highlight = highlightFields.get("title");
            if (highlight != null) {
                Text[] fragments = highlight.fragments();
                if (fragments != null && fragments.length > 0) {
                    caseVo.setTitle(fragments[0].string());
                }
            }
            highlight = highlightFields.get("courtOpinion");
            if (highlight != null) {
                Text[] fragments = highlight.fragments();
                if (fragments != null && fragments.length > 0) {
                    caseVo.setCourtOpinion(fragments[0].string());
                }
            }
            if (req.getLoginId() != null) {
                caseVo.setAttentionFlag(collectService.isCollect(req.getLoginId(), String.valueOf(caseVo.getDocId()), CaseEnum.CollectTypeEum.CASE.name()) ? "1" : "0");
            }
            list.add(caseVo);
        }

        data.put("sidebar", getSideBar(response));
        data.put("total", hits.getTotalHits());
        data.put("hasNextPage", (hits.getTotalHits() % req.getPerPage() == 0 ? hits.getTotalHits() / req.getPerPage() : (hits.getTotalHits() / req.getPerPage() + 1)) > req.getPage());
        data.put("list", list);

        return ResponseModel.succ(data);
    }

    @Override
    public ResponseModel searchCaseSidebar(CaseSearchReq.SearchListReq req) {
        Map<String, Object> data = new HashMap<>();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        QueryBuilder q = assembleQuery(req);
        log.info("query={}", q.toString());
        searchSourceBuilder.query(q);
        assembleAggregation(searchSourceBuilder);
        searchSourceBuilder.size(0);
        SearchRequest request = new SearchRequest();
        request.indices(ElasticSearchConstant.CASE_INDEX);
        request.source(searchSourceBuilder);
        log.info("aggregations={}", searchSourceBuilder.aggregations().toString());
        SearchResponse response;
        try {
            response = client.search(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseModel.fail();
        }
        data.put("sidebar", getSideBar(response));
        return ResponseModel.succ(data);
    }

    private QueryBuilder assembleQuery(CaseSearchReq.SearchListReq req) {
        BoolQueryBuilder q = QueryBuilders.boolQuery();
        Iterator<CaseSearchCondition> itr = req.getConditions().iterator();
        boolean hasSearchWord = false;
        while (itr.hasNext()) {
            CaseSearchCondition item = itr.next();
            String type = item.getType();
            String value = item.getValue();
            if (!CaseEnum.CaseSearchType.containsType(type) || StringUtils.isBlank(value)) {
                itr.remove();
            }
            if (CaseEnum.CaseSearchType.searchWord.getKey().equals(type)) {
                hasSearchWord = true;
            }
        }
        if (!hasSearchWord) {
            throw new BusinessException(ApiCallResult.EMPTY);
        }
        for (CaseSearchCondition item : req.getConditions()) {
            String type = item.getType();
            String value = item.getValue();
            String level = item.getLevel();
            if (!CaseEnum.CaseSearchType.containsType(type) || StringUtils.isBlank(value)) {
                continue;
            }
            CaseEnum.CaseSearchType searchType = CaseEnum.CaseSearchType.getByType(type);
            switch (searchType) {
                case keyword:
                    throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
                case searchWord:
                    BoolQueryBuilder subQ = QueryBuilders.boolQuery().
                            should(QueryBuilders.matchPhraseQuery("title", value)).
                            should(QueryBuilders.matchPhraseQuery("courtOpinion", value)).
                            should(QueryBuilders.matchPhraseQuery("court.name", value))
                            .should(QueryBuilders.matchPhraseQuery("brief.name", value))
                            .should(QueryBuilders.matchPhraseQuery("lawyers.lawyer", value));
                    q.must(subQ);
                    break;
                case caseBrief:
                    if (StringUtils.isBlank(level)) {
                        continue;
                    }
                    if ("1".equals(level)) {
                        q.must(QueryBuilders.termQuery("brief.firstId", value));
                    } else if ("2".equals(level)) {
                        q.must(QueryBuilders.termQuery("brief.secondId", value));
                    } else if ("3".equals(level)) {
                        q.must(QueryBuilders.termQuery("brief.thirdId", value));
                    } else if ("4".equals(level)) {
                        q.must(QueryBuilders.termQuery("brief.fourthId", value));
                    } else if ("5".equals(level)) {
                        q.must(QueryBuilders.termQuery("brief.fifthId", value));
                    }
                    break;
                case courtProvince:
                    if (StringUtils.isBlank(level)) {
                        continue;
                    }
                    if ("1".equals(level)) {
                        q.must(QueryBuilders.termQuery(CaseEnum.CaseSearchType.courtProvince.getKey(), value));
                    } else if ("2".equals(level)) {
                        q.must(QueryBuilders.termQuery(CaseEnum.CaseSearchType.courtName.getKey(), value));
                    }
                    break;
                case courtName:
                case courtType:
                case courtCity:
                    q.must(QueryBuilders.matchQuery(searchType.getKey(), value));
                    break;

                default:
                    q.must(QueryBuilders.termQuery(searchType.getKey(), value));
                    break;
            }
        }
        return q;
    }

    private HighlightBuilder assembleHighlight() {
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        HighlightBuilder.Field highlightTitle = new HighlightBuilder.Field("title");
        highlightTitle.preTags("<span style=\"color: #FD5545;\">");
        highlightTitle.postTags("</span>");
        highlightBuilder.field(highlightTitle);
        HighlightBuilder.Field highlightCourtOpinion = new HighlightBuilder.Field("courtOpinion");
        highlightBuilder.field(highlightCourtOpinion);
        highlightCourtOpinion.preTags("<span style=\"color: #FD5545;\">");
        highlightCourtOpinion.postTags("</span>");
        return highlightBuilder;
    }

    private void assembleAggregation(SearchSourceBuilder searchSourceBuilder) {
        for (CaseEnum.AggsType item : CaseEnum.AggsType.values()) {
            if (!item.isEnable()) {
                continue;
            }
            AggregationBuilder aggregationBuilder = AggregationBuilders.terms(item.getKey()).field(item.getValue());
            if (CaseEnum.AggsType.courtProvince.equals(item)) {
                aggregationBuilder.subAggregation(AggregationBuilders.terms(CaseEnum.AggsType.courtName.getKey()).field(CaseEnum.AggsType.courtName.getValue()).size(ElasticSearchConstant.AGGS_SIZE));
            } else if (CaseEnum.AggsType.briefFirst.equals(item)) {
                aggregationBuilder.subAggregation(
                        AggregationBuilders.terms(CaseEnum.AggsType.briefSecond.getKey()).field(CaseEnum.AggsType.briefSecond.getValue()).size(ElasticSearchConstant.AGGS_SIZE).
                                subAggregation(
                                        AggregationBuilders.terms(CaseEnum.AggsType.briefThird.getKey()).field(CaseEnum.AggsType.briefThird.getValue()).size(ElasticSearchConstant.AGGS_SIZE).
                                                subAggregation(
                                                        AggregationBuilders.terms(CaseEnum.AggsType.briefFourth.getKey()).field(CaseEnum.AggsType.briefFourth.getValue()).size(ElasticSearchConstant.AGGS_SIZE).
                                                                subAggregation(
                                                                        AggregationBuilders.terms(CaseEnum.AggsType.briefFifth.getKey()).field(CaseEnum.AggsType.briefFifth.getValue()).size(ElasticSearchConstant.AGGS_SIZE))
                                                )
                                )
                );
            }
            searchSourceBuilder.aggregation(aggregationBuilder);
        }
    }

    private Map<String, Object> getSideBar(SearchResponse response) {
        Map<String, Object> data = new HashMap<>();
        if (response.getAggregations() == null) {
            return data;
        }
        Iterator<Aggregation> itr = response.getAggregations().iterator();
        while (itr.hasNext()) {
            Terms terms = (Terms) itr.next();
            JSONArray array = new JSONArray();
            for (Terms.Bucket item : terms.getBuckets()) {
                JSONObject jsonObject = new JSONObject();
                String name;
                if (CaseEnum.AggsType.judgementType.getKey().equals(terms.getName())) {
                    name = CaseEnum.JudgementType.getValueByKey((String) item.getKey());
                } else if (CaseEnum.AggsType.trialRound.getKey().equals(terms.getName())) {
                    name = CaseEnum.TrialRound.getValueByKey((String) item.getKey());
                } else if (CaseEnum.AggsType.type.getKey().equals(terms.getName())) {
                    name = CaseEnum.CaseType.getValueByKey((String) item.getKey());
                } else if (CaseEnum.AggsType.courtType.getKey().equals(terms.getName())) {
                    name = CourtEnum.Type.getValueByKey((String) item.getKey());
                } else if (CaseEnum.AggsType.courtProvince.getKey().equals(terms.getName())) {
                    name = item.getKey() + "";
                    jsonObject.put("level", "1");
                    jsonObject.put("child", getCourName(item.getAggregations()));
                } else if (CaseEnum.AggsType.briefFirst.getKey().equals(terms.getName())) {
                    name = getBriefName(((Long) item.getKey()).intValue());
                    jsonObject.put("level", 1);
                    jsonObject.put("child", getBrief(item.getAggregations(), 2));
                } else {
                    name = item.getKey() + "";
                }
                jsonObject.put("key", item.getKey());
                jsonObject.put("name", name);
                jsonObject.put("count", item.getDocCount());
                array.add(jsonObject);
            }
            data.put(terms.getName(), array);
        }
        return data;
    }

    private JSONArray getCourName(Aggregations aggregations) {
        JSONArray result = new JSONArray();
        Terms terms = aggregations.get("courtName");
        for (Terms.Bucket item : terms.getBuckets()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("key", item.getKey());
            jsonObject.put("name", item.getKey());
            jsonObject.put("count", item.getDocCount());
            jsonObject.put("level", "2");
            result.add(jsonObject);
        }
        return result;
    }

    private JSONArray getCourtCity(Aggregations aggregations) {
        JSONArray result = new JSONArray();
        Terms terms = aggregations.get("courtCity");
        for (Terms.Bucket item : terms.getBuckets()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("key", item.getKey());
            jsonObject.put("name", item.getKey());
            jsonObject.put("count", item.getDocCount());
            result.add(jsonObject);
        }
        return result;
    }

    private JSONArray getBrief(Aggregations aggregations, Integer level) {
        JSONArray result = new JSONArray();
        Terms terms = aggregations.get("child");
        if (terms != null && terms.getBuckets() != null && terms.getBuckets().size() > 0) {
            for (Terms.Bucket item : terms.getBuckets()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("key", item.getKey());
                jsonObject.put("name", getBriefName(((Long) item.getKey()).intValue()));
                jsonObject.put("count", item.getDocCount());
                jsonObject.put("child", getBrief(item.getAggregations(), level + 1));
                jsonObject.put("level", level);
                result.add(jsonObject);
            }
        }
        return result;
    }


    @Override
    public Case convertToCase(TCase model) {
        Case vo = new Case();
        BeanUtils.copyProperties(model, vo);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(model.getJudgementDate());
        vo.setJudgementYear(calendar.get(Calendar.YEAR));

        vo.setCourt(courtService.convertToCourt(model.getCourtName()));
        Brief brief = (Brief) SysConstant.briefLevels.get(model.getBriefId());
        if (brief != null) {
            TTreeContent treeContent = (TTreeContent) SysConstant.briefTreeContents.get(model.getBriefId());
            if (treeContent != null) {
                brief.setName(treeContent.getKeyWord());
            }
            vo.setBrief(brief);
        }
        return vo;
    }

    @Override
    public JudgePerson getJudgePerson(String docId) {
        TJudgePerson model = judgePersonDao.findBy(docId);
        if (model != null) {
            return convertToJudgePerson(model);
        }
        return null;
    }

    @Override
    public JudgePerson convertToJudgePerson(TJudgePerson model) {
        JudgePerson judgePerson = new JudgePerson();
        String presidingJudge = "";
        if (StringUtils.isNotBlank(model.getPresidingJudge())) {
            List<String> ar = Arrays.asList(model.getPresidingJudge().split(";"));
            Set<String> presidingJudges = new HashSet<>();
            for (String item : ar) {
                presidingJudges.add(item.replace("审判长", ""));
            }
            presidingJudge = String.join(",", presidingJudges);
        }
        judgePerson.setPresidingJudge(presidingJudge);
        return judgePerson;
    }

    @Override
    public CaseDetailVo getCaseDetail(CaseSearchReq.BaseReq req) {
        String docId = AESUtil.decrypt(req.getDocId());
        TCase tCase = caseDao.findBy(docId);
        if (tCase == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        CaseDetailVo resp = RespConvertUtil.convertToCaseDetailVo(tCase);
        resp.setDocId(req.getDocId());
        resp.setCollegialPanel(viewTableDao.getCollegialPanel(req.getDocId()));
        CaseHtmlAnalysis caseHtmlAnalysis = viewTableService.getCaseHtmlAnalysis(docId);
        if (caseHtmlAnalysis != null) {
            resp.setDefendant(caseHtmlAnalysis.getDefendant());
            resp.setDefendantLawyer(caseHtmlAnalysis.getDefendantLawyer());
            resp.setProsecutor(caseHtmlAnalysis.getProsecutor());
            resp.setProsecutorLawyer(caseHtmlAnalysis.getProsecutorLawyer());
            resp.setCaseMainBody(caseHtmlAnalysis.getHtml());
            resp.setProsecutorLawyerList(getLawyerDetail(caseHtmlAnalysis.getProsecutorLawyer()));
            resp.setDefendantLawyerList(getLawyerDetail(caseHtmlAnalysis.getDefendantLawyer()));
            resp.setContainer(WenshuHtmlSaveUntil.segParse(caseHtmlAnalysis.getHtml()));
            resp.setLawFirmList(getLawFirmList(resp.getDefendantLawyerList()));
            resp.getLawFirmList().addAll(getLawFirmList(resp.getProsecutorLawyerList()));
        }
        if (req.getLoginId() != null) {
            resp.setAttentionFlag(collectService.isCollect(req.getLoginId(), String.valueOf(resp.getDocId()), CaseEnum.CollectTypeEum.CASE.name()) ? "1" : "0");
        }
        return resp;
    }

    @Override
    public Case searchESByDocId(String docId) {
        // searchRequest  QueryBuilders SearchSourceBuilder client
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(ElasticSearchConstant.CASE_INDEX);
        searchRequest.types(ElasticSearchConstant.DEFAULT_TYPE);
        // 构造query
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchPhraseQuery("docId", docId));
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
            return convertCase(search);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public CaseHtmlData getHtmlData(String docId) {
        return caseHtmlDataDao.findBy(AESUtil.decrypt(docId));
    }

    @Override
    public int getTotalCaseCount() {
        try {
            Response response = client.getLowLevelClient().performRequest("GET", ElasticSearchConstant.CASE_INDEX + "/_count");
            String responseBody = EntityUtils.toString(response.getEntity());
            if (StringUtils.isNotBlank(responseBody)) {
                JSONObject jsonObject = JSON.parseObject(responseBody);
                if (jsonObject.containsKey("count")) {
                    return jsonObject.getIntValue("count");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return caseDao.getTotalCaseCount();
    }

    private Case convertCase(SearchResponse search) {
        SearchHits hits = search.getHits();
        if (hits.getTotalHits() == 0) {
            return null;
        }
        SearchHit hit = hits.getHits()[0];
        Map<String, Object> sourceAsMap = hit.getSourceAsMap();
        return objectMapper.convertValue(sourceAsMap, Case.class);
    }

    private List<Map<String, Object>> getLawyerDetail(String lawyerNames) {
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> map;
        if (StringUtils.isNotBlank(lawyerNames)) {
            String[] splitResult = lawyerNames.split(";");
            TParseLawyerInfoCondition tLawyerCondition = new TParseLawyerInfoCondition();
            for (String lawyerName : splitResult) {
                map = Maps.newHashMap();
                map.put("lawyerName", lawyerName);
                tLawyerCondition.setLawyerName(lawyerName);
                TParseLawyerInfo parseLawyerInfo = parseLawyerInfoDao.selectFirst(tLawyerCondition);
                if (parseLawyerInfo != null) {
                    map.put("imgUrl", SysConstant.DEFAULT_LAWYER_IMG_URL);
                    map.put("lawFirm", parseLawyerInfo.getLawFirm());
                    map.put("lawyerId", parseLawyerInfo.getId());
                }
                result.add(map);
            }
        }
        return result;
    }

    private List<Map<String, Object>> getLawFirmList(List<Map<String,Object>> lawyerList) {
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> map;
        for (Map<String,Object> lawyer : lawyerList) {
            if (lawyer.containsKey("lawyerId")) {
                TParseLawyerInfo parseLawyerInfo = parseLawyerInfoDao.selectById(lawyer.get("lawyerId"));
                if (parseLawyerInfo != null) {
                    if (parseLawyerInfo.getLawyerFirmId() != null) {
                        TLawyerFirmInfo lawyerFirmInfo = lawyerFirmInfoDao.selectById(parseLawyerInfo.getLawyerFirmId());
                        if (lawyerFirmInfo != null) {
                            map = Maps.newHashMap();
                            map.put("imgUrl", lawyerFirmInfo.getImgUrl());
                            map.put("lawFirm", lawyerFirmInfo.getFirmName());
                            map.put("lawFirmId", lawyerFirmInfo.getId());
                            result.add(map);
                        }
                    }
                }
            }
        }
        return result;
    }

    @Override
    public String getBriefName(Integer briefId) {
        TTreeContent result = (TTreeContent) SysConstant.briefTreeContents.get(briefId);
        if (result == null) {
            result = treeContentDao.selectById(briefId);
            if (result != null) {
                SysConstant.briefTreeContents.put(result.getId(), result);
            }
        }
        if (result != null) {
            return result.getKeyWord();
        }
        return "";
    }


    private Brief getBriefLevel(Integer briefId) {
        if (briefId == null || briefId.equals(-1)) {
            return null;
        }
        String cache = (String) redisCachemanager.hGet(RedisKeyConstant.BRIEF_LEVEL, briefId + "");
        if (StringUtils.isNotBlank(cache)) {
            return JSON.parseObject(cache, Brief.class);
        } else {
            Brief model = parseBriefLevel(briefId);
            if (model == null) {
                return null;
            }
            redisCachemanager.hSet(RedisKeyConstant.BRIEF_LEVEL, briefId + "", JSON.toJSONString(model));
            return model;
        }
    }

    private Brief parseBriefLevel(Integer briefId) {
        String cache = (String) redisCachemanager.hGet(RedisKeyConstant.BRIEF, briefId + "");
        if (StringUtils.isNotBlank(cache)) {
            Brief brief = new Brief();
            TTreeContent b = JSON.parseObject(cache, TTreeContent.class);
            if (CaseEnum.BriefLevel.First.getValue().equals(b.getField())) {
                brief.setFirstId(b.getId());
            } else if (CaseEnum.BriefLevel.Second.getValue().equals(b.getField())) {
                brief.setSecondId(b.getId());
                brief.setFirstId(b.getParentId());
            } else if (CaseEnum.BriefLevel.Third.getValue().equals(b.getField())) {
                brief.setThirdId(b.getId());
                brief.setSecondId(b.getParentId());
                cache = (String) redisCachemanager.hGet(RedisKeyConstant.BRIEF, b.getParentId() + "");
                if (StringUtils.isNotBlank(cache)) {
                    b = JSON.parseObject(cache, TTreeContent.class);
                    brief.setFirstId(b.getParentId());
                }
            } else if (CaseEnum.BriefLevel.Fourth.getValue().equals(b.getField())) {
                brief.setFourthId(b.getId());
                brief.setThirdId(b.getParentId());
                cache = (String) redisCachemanager.hGet(RedisKeyConstant.BRIEF, b.getParentId() + "");
                if (StringUtils.isNotBlank(cache)) {
                    TTreeContent third = JSON.parseObject(cache, TTreeContent.class);
                    brief.setSecondId(third.getParentId());
                    cache = (String) redisCachemanager.hGet(RedisKeyConstant.BRIEF, third.getParentId() + "");
                    if (StringUtils.isNotBlank(cache)) {
                        TTreeContent second = JSON.parseObject(cache, TTreeContent.class);
                        brief.setFirstId(second.getParentId());
                    }
                }
            } else if (CaseEnum.BriefLevel.Fifth.getValue().equals(b.getField())) {
                brief.setFifthId(b.getId());
                brief.setFourthId(b.getParentId());
                cache = (String) redisCachemanager.hGet(RedisKeyConstant.BRIEF, b.getParentId() + "");
                if (StringUtils.isNotBlank(cache)) {
                    TTreeContent fourth = JSON.parseObject(cache, TTreeContent.class);
                    brief.setThirdId(fourth.getParentId());
                    cache = (String) redisCachemanager.hGet(RedisKeyConstant.BRIEF, fourth.getParentId() + "");
                    if (StringUtils.isNotBlank(cache)) {
                        TTreeContent third = JSON.parseObject(cache, TTreeContent.class);
                        brief.setSecondId(third.getParentId());
                        cache = (String) redisCachemanager.hGet(RedisKeyConstant.BRIEF, third.getParentId() + "");
                        if (StringUtils.isNotBlank(cache)) {
                            TTreeContent second = JSON.parseObject(cache, TTreeContent.class);
                            brief.setFirstId(second.getParentId());
                        }
                    }
                }
            }
            return brief;
        }
        return null;
    }
}
