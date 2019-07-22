package com.tzCloud.core.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gexin.fastjson.JSON;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.tzCloud.arch.common.constant.ElasticSearchConstant;
import com.tzCloud.arch.common.enums.ApiCallResult;
import com.tzCloud.arch.common.exception.BaseBusinessException;
import com.tzCloud.core.condition.legalEngine.TCourtCondition;
import com.tzCloud.core.constant.CaseEnum;
import com.tzCloud.core.constant.CourtEnum;
import com.tzCloud.core.constant.SysConstant;
import com.tzCloud.core.dao.legalEngine.TCourtDao;
import com.tzCloud.core.facade.caseMatching.req.CourtReq;
import com.tzCloud.core.facade.caseMatching.resp.CourtResp;
import com.tzCloud.core.facade.legalEngine.vo.Court;
import com.tzCloud.core.hanLP.HanLPFactory;
import com.tzCloud.core.model.legalEngine.TCourt;
import com.tzCloud.core.service.CourtService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author zxiao
 * @Title: CourtServiceImpl
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019/4/22 10:27
 */
@Service
@Slf4j
public class CourtServiceImpl implements CourtService {

    @Resource
    private TCourtDao courtDao;
    @Autowired
    protected RestHighLevelClient client;
    @Autowired
    protected ObjectMapper objectMapper;

    @Override
    public CourtResp findByCourtName(CourtReq req) {

        TCourtCondition courtCondition = new TCourtCondition();
        courtCondition.setId(req.getId());
        TCourt court = courtDao.selectOneResult(courtCondition);

        if (court == null) {
            log.error("当前法院数据不存在");
            throw new BaseBusinessException(ApiCallResult.FAILURE, "当前法院数据不存在");
        }

        CourtResp resp = new CourtResp();
        resp.setName(court.getName());
        resp.setAddress(court.getAddress());
        resp.setPhone(court.getPhone());
        resp.setType(court.getType());
        //统计本院案例
        Integer courtNum = getCourtNumByEs(court);
        resp.setCaseload(courtNum);


        List<Map<String, Object>> briefList = getCourtBriefByEs(court);
        if (briefList == null) return null;
        //法院所有案由
        resp.setCourtBriefs(briefList);
        //法院前5案由
        resp.setBriefs(briefList.subList(0, 5));

        //查询该法院下的法官条数
        List<Map<String, Object>> judgePerson = getJudgePersonByEs();
        if (judgePerson == null) return null;
        resp.setJudgePerson(judgePerson);

        //使用es聚合数据
        List<Map<String, Object>> judgeDate = getYearByEs(court);
        if (judgeDate == null) return null;
        resp.setJudgeDate(judgeDate);

        //使用 es查询type
        if (getTypeAndJudgeTypeAndLawFirmByEs(court, resp)) return null;

        resp.setImageUrl(court.getImgUrl());
        resp.setJudgeNum(judgePerson.size());
        resp.setRemark(court.getDescription());

        return resp;
    }

    private boolean getTypeAndJudgeTypeAndLawFirmByEs(TCourt court, CourtResp resp) {
        SearchRequest request = new SearchRequest(ElasticSearchConstant.CASE_INDEX);
        TermQueryBuilder queryBuilder = QueryBuilders.termQuery("court.name", court.getName());
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(queryBuilder);
        sourceBuilder.aggregation(AggregationBuilders.terms("types").field("type").size(20).order(BucketOrder.count(false)));
        sourceBuilder.aggregation(AggregationBuilders.terms("judgeTypes").field("judgementType").size(20).order(BucketOrder.count(false)));
        sourceBuilder.aggregation(AggregationBuilders.terms("lawFirms").field("lawyers.lawFirm").size(10).order(BucketOrder.count(false)));
        log.info("query = {}", sourceBuilder.query().toString());
        log.info("aggregation = {}", sourceBuilder.aggregations().toString());

        request.source(sourceBuilder);

        SearchResponse search = null;
        try {
            search = client.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (search == null) {
            return true;
        }

        Aggregations aggregations = search.getAggregations();
        Terms types = aggregations.get("types");
        Terms judgeTypes = aggregations.get("judgeTypes");
        Terms lawFirms = aggregations.get("lawFirms");

        List<Map<String, Object>> typeList = new LinkedList<>();
        for (Terms.Bucket bucket : types.getBuckets()) {
            Map<String, Object> map = new HashMap<>();
            long docCount = bucket.getDocCount();
            String key = (String) bucket.getKey();
            String valueByKey = CaseEnum.CaseType.getValueByKey(key);

            map.put("type", valueByKey);
            map.put("count", docCount);
            typeList.add(map);
        }

        List<Map<String, Object>> judgeTypesList = new LinkedList<>();
        for (Terms.Bucket bucket : judgeTypes.getBuckets()) {
            Map<String, Object> map = new HashMap<>();
            long docCount = bucket.getDocCount();
            String key = (String) bucket.getKey();
            String valueByKey = CaseEnum.JudgementType.getValueByKey(key);

            map.put("type", valueByKey);
            map.put("count", docCount);
            judgeTypesList.add(map);
        }

        List<Map<String, Object>> lawFirmList = new LinkedList<>();
        for (Terms.Bucket bucket : lawFirms.getBuckets()) {
            Map<String, Object> map = new HashMap<>();
            long docCount = bucket.getDocCount();
            String key = (String) bucket.getKey();

            map.put("law_firm", key);
            map.put("count", docCount);
            lawFirmList.add(map);
        }
        resp.setTypeList(typeList);
        resp.setJudgeTypeList(judgeTypesList);
        resp.setLawFirmList(lawFirmList);
        return false;
    }

    @Nullable
    private List<Map<String, Object>> getJudgePersonByEs() {
        SearchRequest request = new SearchRequest(ElasticSearchConstant.CASE_INDEX);
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().mustNot(QueryBuilders.boolQuery().filter(QueryBuilders.termQuery("judgePerson.presidingJudge", "")).must(QueryBuilders.existsQuery("judgePerson.presidingJudge")));
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(boolQueryBuilder);
        sourceBuilder.aggregation(AggregationBuilders.terms("judgePersion").field("judgePerson.presidingJudge").size(100).order(BucketOrder.count(false)));

        log.info("query={}", sourceBuilder.query().toString());
        log.info("aggregation={}", sourceBuilder.aggregations().toString());
        request.source(sourceBuilder);

        SearchResponse search = null;
        try {
            search = client.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (search == null) {
            return null;
        }
        Aggregations aggregations = search.getAggregations();
        Terms terms = aggregations.get("judgePersion");
        List<Map<String, Object>> judgePerson = new LinkedList<>();
        for (Terms.Bucket bucket : terms.getBuckets()) {
            Map<String, Object> map = new HashMap<>();
            long docCount = bucket.getDocCount();
            String key = (String) bucket.getKey();
            map.put("presiding_judge", key.replace("审判长", "").replace(";", ""));
            map.put("count", docCount);
            judgePerson.add(map);
        }
        return judgePerson;
    }

    @Nullable
    private List<Map<String, Object>> getCourtBriefByEs(TCourt court) {
        SearchRequest request = new SearchRequest(ElasticSearchConstant.CASE_INDEX);
        TermQueryBuilder queryBuilder = QueryBuilders.termQuery("court.name", court.getName());
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.size(0);
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.aggregation(AggregationBuilders.terms("courtName").field("court.name")
                .subAggregation(AggregationBuilders.terms("courtBrief").field("brief.name").size(2000).order(BucketOrder.count(false))));
        log.info("query={}", searchSourceBuilder.query().toString());
        log.info("aggs={}", searchSourceBuilder.aggregations().toString());
        request.source(searchSourceBuilder);
        SearchResponse search = null;
        try {
            search = client.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("ES通讯失败");
            e.printStackTrace();
        }
        if (search == null) {
            log.error("ES通讯失败");
            return null;
        }
        Aggregations aggregations = search.getAggregations();

        Terms terms = aggregations.get("courtName");
        List<Map<String, Object>> briefList = new LinkedList<>();
        for (Terms.Bucket bucket : terms.getBuckets()) {
            Aggregations aggregations1 = bucket.getAggregations();
            Terms terms1 = aggregations1.get("courtBrief");
            for (Terms.Bucket bucket1 : terms1.getBuckets()) {
                long docCount = bucket1.getDocCount();
                Object key = bucket1.getKey();
                Map<String, Object> map = new HashMap<>();
                map.put("brief", key);
                map.put("count", docCount);
                briefList.add(map);
            }
        }
        return briefList;
    }


    private List<Map<String, Object>> getYearByEs(TCourt court) {
        SearchRequest searchRequest = new SearchRequest(ElasticSearchConstant.CASE_INDEX);
        TermQueryBuilder boolQueryBuilder = QueryBuilders.termQuery("court.name", court.getName());
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(boolQueryBuilder);
        sourceBuilder.aggregation(AggregationBuilders.terms("yearCount").field("judgementYear").order(BucketOrder.key(true)));
        log.info("aggregations={}", sourceBuilder.aggregations().toString());
        log.info("query={}", sourceBuilder.query().toString());
        searchRequest.source(sourceBuilder);
        SearchResponse search = null;
        try {
            search = client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("ES请求通讯异常");
            e.printStackTrace();
        }
        if (search == null) {
            return null;
        }
        Aggregations yearAggregations = search.getAggregations();
        Terms terms = yearAggregations.get("yearCount");
        List<Map<String, Object>> judgeDate = new LinkedList<>();
        for (Terms.Bucket bucket : terms.getBuckets()) {
            Map<String, Object> map = new HashMap<>();
            String keyAsString = bucket.getKeyAsString();
            long docCount = bucket.getDocCount();

            map.put("jugdeDate", keyAsString);
            map.put("count", docCount);
            judgeDate.add(map);
        }
        return judgeDate;
    }

    private Integer getCourtNumByEs(TCourt court) {
        //统计本院案例
        SearchRequest searchCourtReq = new SearchRequest(ElasticSearchConstant.CASE_INDEX);
        MatchQueryBuilder courtQueryBuilder = QueryBuilders.matchQuery("court.name", court.getName());
        SearchSourceBuilder courtSourceBuilder = new SearchSourceBuilder();
        courtSourceBuilder.size(0);
        courtSourceBuilder.query(courtQueryBuilder);
        courtSourceBuilder.aggregation(AggregationBuilders.terms("courtProvince").field("court.province")
                .subAggregation(AggregationBuilders.terms("courtCity").field("court.city")
                        .subAggregation(AggregationBuilders.terms("caseNum").field("court.name").size(10))));

        log.info("aggregations={}", courtSourceBuilder.aggregations().toString());
        log.info("query={}", courtSourceBuilder.query().toString());
        searchCourtReq.source(courtSourceBuilder);
        SearchResponse courtSearch = null;
        try {
            courtSearch = client.search(searchCourtReq, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("ES请求通讯异常");
            e.printStackTrace();
        }
        if (courtSearch == null) {
            log.error("ES请求通讯异常");
            return 0;
        }
        Aggregations aggregations = courtSearch.getAggregations();
        Terms courtProvince = aggregations.get("courtProvince");
        for (Terms.Bucket bucket : courtProvince.getBuckets()) {
            Aggregations aggregations1 = bucket.getAggregations();
            Terms terms = aggregations1.get("courtCity");
            if (terms != null) {
                List<? extends Terms.Bucket> buckets1 = terms.getBuckets();
                for (Terms.Bucket bucket1 : buckets1) {
                    Aggregations aggregations2 = bucket1.getAggregations();
                    Terms terms1 = aggregations2.get("caseNum");
                    for (Terms.Bucket bucket2 : terms1.getBuckets()) {
                        long caseNum = bucket2.getDocCount();
                        return Long.valueOf(caseNum).intValue();
                    }
                }
            }
        }
        return 0;
    }

    private void covertMap(Map<String, String> typeMap, List<Map<String, String>> judgeTypeList) {
        for (String type : typeMap.keySet()) {
            Integer num = Integer.valueOf(typeMap.get(type));
            Map<String, String> map = new HashMap<>();
            map.put("type", type);
            map.put("count", num.toString());
            judgeTypeList.add(map);
        }
    }

    @Override
    public Court convertToCourt(String courtName) {
        if (StringUtils.isBlank(courtName)) {
            return null;
        }
        Court court = (Court) SysConstant.courts.get(courtName);
        if (court != null) {
            return court;
        } else {
            Court vo = new Court();
            String province = "";
            String city = "";
            String area = "";
            Segment segment = HanLPFactory.builder().segment(true);
            List<Term> terms = segment.seg(courtName);
            log.info("terms={}", JSON.toJSONString(terms));
            for (Term term : terms) {
                String nature = term.nature.toString();
                if (nature.startsWith("city_")) {
                    city = term.word;
                    province = nature.split("_")[1];
                } else if (nature.startsWith("province_")) {
                    province = nature.split("_")[1];
                } else if (nature.startsWith("area_")) {
                    area = term.word;
                    province = nature.split("_")[2];
                    city = nature.split("_")[1];
                }
            }
            vo.setName(courtName);
            if (courtName.contains("最高")) {
                vo.setType(CourtEnum.Type.Type_4.getKey());
            } else if (courtName.contains("高级")) {
                vo.setType(CourtEnum.Type.Type_3.getKey());
            } else if (courtName.contains("中级")) {
                vo.setType(CourtEnum.Type.Type_2.getKey());
            } else {
                vo.setType(CourtEnum.Type.Type_1.getKey());
            }
            String simpleName = courtName;
            if (courtName.contains("最高")) {
                simpleName = "最高人民法院";
                province = "最高人民法院";
                city = "最高人民法院";
            } else if (courtName.contains("中级") && StringUtils.isNotBlank(city)) {
                simpleName = courtName.replace(province, "").replace("省", "");
            }
            vo.setName(simpleName);
            vo.setProvince(province);
            vo.setCity(city);
            SysConstant.courts.put(vo.getName(), vo);
            return vo;
        }
    }
}
