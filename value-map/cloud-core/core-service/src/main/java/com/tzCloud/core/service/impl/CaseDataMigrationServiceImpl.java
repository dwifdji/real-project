package com.tzCloud.core.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.tzCloud.arch.common.constant.ElasticSearchConstant;
import com.tzCloud.core.constant.CaseEnum;
import com.tzCloud.core.facade.assistant.req.MigrationDataReq;
import com.tzCloud.core.facade.legalEngine.vo.Case;
import com.tzCloud.core.facade.legalEngine.vo.JudgePerson;
import com.tzCloud.core.model.caseMatching.TLawyerData;
import com.tzCloud.core.model.caseMatching.TTreeContent;
import com.tzCloud.core.model.lawyerSearch.TParseLawyerInfo;
import com.tzCloud.core.model.legalEngine.TCase;
import com.tzCloud.core.model.legalEngine.TJudgePerson;
import com.tzCloud.core.service.CaseDataMigrationService;
import lombok.extern.slf4j.Slf4j;
import netscape.javascript.JSObject;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.*;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xdrodger
 * @Title: CaseDataMigrationServiceImpl
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019/5/16 18:17
 */
@Slf4j
@Service("caseDataMigrationService")
public class CaseDataMigrationServiceImpl extends DataMigrationServiceImpl implements CaseDataMigrationService {

    @Override
    public int migrationCaseFromCpwswItem(Integer limit) {
        long startTime = System.currentTimeMillis();
        if (limit == null || limit.intValue() <=0) {
            limit = 10000;
        }
        log.info("开始同步案件cpwsw_item");
        int totalRows = 0;
        Map<String, Object> params = new HashMap<>();
        params.put("limit", limit);
        params.put("maxId", caseDao.getMaxId());
        Map<String, Integer> briefMaps = getBriefMaps();
        while (true) {
            int rows = caseDao.migrationFromCpwswItem(params);
            log.info("同步案件cpwsw_item，rows={}", rows);
            repairTCase(briefMaps);
            totalRows += rows;
            if (rows <= 0) {
                break;
            }
        }
        log.info("结束同步案件cpwsw_item，耗时={}s", (System.currentTimeMillis()-startTime) / 1000);
        return totalRows;
    }

    private int repairTCase(Map<String, Integer> briefMaps) {
        int page = 1;
        int totalRows = 0;
        while (true) {
            log.info("开始同步案件，page={}", page);
            PageInfo<TCase> pageInfo = caseDao.findBriefIdIsNull(1, 2000, new HashMap<>());
            List<TCase> list = pageInfo.getList();
            Iterator<TCase> itr = list.iterator();
            while (itr.hasNext()) {
                TCase item = itr.next();
                item.setJudgementType(CaseEnum.JudgementType.getKeyBySimilarValue(item.getTitle()));
                item.setTrialRound(CaseEnum.TrialRound.getKeyByValue(item.getTrialRound()));
                Integer briefId = briefMaps.get(item.getBrief());
                if (briefId == null) {
                    briefId = -1;
                }
                item.setBriefId(briefId);
            }
            int rows = 0;
            if (list.size() > 0) {
                rows = caseDao.batchUpdateJudgementTypeBriefId(list);
            }
            log.info("同步案件，page={}, rows={}", page, rows);
            totalRows += rows;
            if (!pageInfo.isHasNextPage()) {
                break;
            }
            page ++;
        }
        log.info("同步案件，totalRows={}", totalRows);
        return totalRows;
    }

    @Override
    public int migrationCaseToElasticSearch(MigrationDataReq.CaseToElasticSearchReq req) {
        if (!isIndexExists(ElasticSearchConstant.CASE_INDEX)) {
            createCaseIndex();
        }
        int totalRows = 0;
        long startTime = System.currentTimeMillis();
        log.info("开始同步案件至elasticsearch");
        if (StringUtils.isNotBlank(req.getDocId())) {
            TCase tCase = caseDao.findBy(req.getDocId());
            Case vo = caseService.convertToCase(tCase);
            batchSyncToElasticSearch(Arrays.asList(vo));
        } else {
            Integer maxId = req.getMaxId() != null ? req.getMaxId() : getMaxCaseIdInElasticSearch();
            int page = 1;
            //int perPage = 5000;
            //int perPage = 100;
            //int perPage = 500;
            int perPage = req.getPerPage();
            while (true) {
                PageInfo<TCase> pageInfo = caseDao.findByIdGreaterThan(page, perPage, maxId);
                List<TCase> list = pageInfo.getList();
                Iterator<TCase> itr = list.iterator();
                List<Case> cases = new ArrayList<>();
//                Map<String, List<Integer>> map =  new HashMap<>(list.size());
//                List<String> collect = list.stream().map(TCase::getDocId).collect(Collectors.toList());
//                queryLawyerByDocId(collect, map);
                while (itr.hasNext()) {
                    Case vo = caseService.convertToCase(itr.next());
                    cases.add(vo);
                }
                int rows = 0;
                if (cases.size() > 0) {
                    rows = batchSyncToElasticSearch(cases);
                }
                log.info("同步案件至elasticsearch，page={}, rows={}", page, rows);
                totalRows += rows;
                if (!pageInfo.isHasNextPage()) {
                    break;
                }
                page ++;
                if (req.getPageNum() > 0 && page > req.getPageNum()) {
                    break;
                }
            }
        }
        log.info("结束同步案件至elasticsearch，totalRows={}，耗时={}s", totalRows, (System.currentTimeMillis()-startTime) / 1000);
        return totalRows;
    }

    private void queryLawyerByDocId(List<String> docIds, Map<String, List<Integer>> map) {
        int size = docIds.size();
        int pageNum = 0;
        int pageSize = 1000;
        if (size > pageSize) {
            List<String> stringList = docIds.subList(pageNum, pageSize);
            List<TLawyerData> by = tLawyerDataDao.findBy(stringList);
            Map<String, List<Integer>> collect = by.stream().collect(Collectors.groupingBy(TLawyerData::getDocid,
                    Collectors.mapping(TLawyerData::getLawyerId, Collectors.toList())));
            map.putAll(collect);
            queryLawyerByDocId(docIds.subList(pageSize, size), map);
        } else {
            queryLawyerByDocId(docIds.subList(pageSize, size), map);
        }
    }

    @Override
    public int migrationCaseExtraDataToElasticSearch(MigrationDataReq.CaseExtraDataToElasticSearchReq req) {
        int totalRows = migrationCaseJudgePersonToElasticSearch(req);
        migrationCaseLawyersToElasticSearch(req);
        return totalRows;
    }

    private int migrationCaseJudgePersonToElasticSearch(MigrationDataReq.CaseExtraDataToElasticSearchReq req) {
        long startTime = System.currentTimeMillis();
        int totalRows = 0;
        int page = 1;
        try {
            log.info("同步案件额外数据审判人至elasticsearch，开始");
            BoolQueryBuilder q = QueryBuilders.boolQuery();
            q.mustNot(QueryBuilders.existsQuery("judgePerson.presidingJudge"));
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(q);
            searchSourceBuilder.size(req.getPerPage());
            searchSourceBuilder.fetchSource(false);
            Scroll scroll = new Scroll(TimeValue.timeValueSeconds(60L));
            SearchRequest request = new SearchRequest();
            request.scroll(scroll);
            request.indices(ElasticSearchConstant.CASE_INDEX);
            request.source(searchSourceBuilder);
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            String scrollId = response.getScrollId();
            while (true) {
                SearchHits hits = response.getHits();
                if (hits == null || hits.getTotalHits() == 0) {
                    break;
                }
                List<String> docIds = new ArrayList<>();
                Iterator<SearchHit> itr = hits.iterator();
                BulkRequest bulkRequest = new BulkRequest();
                int rows = 0;
                while (itr.hasNext()) {
                    SearchHit hit = itr.next();
                    docIds.add(hit.getId());
                }
                log.info("同步案件额外数据审判人至elasticsearch，docIds size={}", docIds.size());
                if (docIds.size() == 0) {
                    break;
                }
                List<TJudgePerson> list = judgePersonDao.findBy(docIds);
                log.info("同步案件额外数据审判人至elasticsearch，TJudgePerson size={}", list.size());
                if (list.size() > 0) {
                    for (TJudgePerson item : list) {
                        JudgePerson judgePerson = caseService.convertToJudgePerson(item);
                        UpdateRequest updateRequest = new UpdateRequest(ElasticSearchConstant.CASE_INDEX, ElasticSearchConstant.DEFAULT_TYPE, item.getDocId());
                        Map<String, Object> data = new HashMap<>();
                        data.put("judgePerson", objectMapper.convertValue(judgePerson, Map.class));
                        updateRequest.doc(data);
                        bulkRequest.add(updateRequest);
                        rows ++;
                    }
                    client.bulk(bulkRequest, RequestOptions.DEFAULT);
                }
                log.info("同步案件额外数据审判人至elasticsearch，page={}, rows={}, scrollId={}", page, rows, scrollId);
                totalRows += rows;
                page ++;
                if (req.getPageNum() > 0 && page > req.getPageNum()) {
                    break;
                }
                SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
                scrollRequest.scroll(scroll);
                response = client.scroll(scrollRequest, RequestOptions.DEFAULT);
            }
            ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
            clearScrollRequest.addScrollId(scrollId);
            ClearScrollResponse clearScrollResponse = client.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("同步案件额外数据审判人至elasticsearch，结束，rows={}, 耗时={}s", totalRows, (System.currentTimeMillis()-startTime) / 1000);
        return totalRows;
    }

    private int migrationCaseLawyersToElasticSearch(MigrationDataReq.CaseExtraDataToElasticSearchReq req) {
        long startTime = System.currentTimeMillis();
        int totalRows = 0;
        int page = 1;
        try {
            log.info("同步案件额外数据律师至elasticsearch，开始");
            BoolQueryBuilder q = QueryBuilders.boolQuery();
            q.mustNot(QueryBuilders.existsQuery("lawyers"));
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(q);
            searchSourceBuilder.size(req.getPerPage());
            searchSourceBuilder.fetchSource(false);
            Scroll scroll = new Scroll(TimeValue.timeValueSeconds(60L));
            SearchRequest request = new SearchRequest();
            request.scroll(scroll);
            request.indices(ElasticSearchConstant.CASE_INDEX);
            request.source(searchSourceBuilder);
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            String scrollId = response.getScrollId();
            while (true) {
                SearchHits hits = response.getHits();
                if (hits == null || hits.getTotalHits() == 0) {
                    break;
                }
                List<String> docIds = new ArrayList<>();
                Iterator<SearchHit> itr = hits.iterator();
                BulkRequest bulkRequest = new BulkRequest();
                int rows = 0;
                while (itr.hasNext()) {
                    SearchHit hit = itr.next();
                    docIds.add(hit.getId());
                }
                log.info("同步案件额外数据律师至elasticsearch，docIds size={}", docIds.size());
                if (docIds.size() == 0) {
                    break;
                }
                List<TLawyerData> list = lawyerDataDao.findBy(docIds);
                List<Integer> collect = list.stream().map(TLawyerData::getLawyerId).distinct().collect(Collectors.toList());
                List<TParseLawyerInfo> tParseLawyerInfos = tParseLawyerInfoDao.searchByLawyerIds(collect);
                Map<Integer, TParseLawyerInfo> collect1 = tParseLawyerInfos.stream().collect(Collectors.toMap(e -> e.getId().intValue(), e -> e));
                for (TLawyerData temp : list) {
                    if (collect1.containsKey(temp.getLawyerId())) {
                        TParseLawyerInfo tParseLawyerInfo = collect1.get(temp.getLawyerId());
                        temp.setLawyer(tParseLawyerInfo.getLawyerName());
                        temp.setLawFirm(tParseLawyerInfo.getLawFirm());
                    }
                }
                log.info("同步案件额外数据审律师至elasticsearch，TLawyerData size={}", list.size());
                if (list.size() > 0) {
                    Map<String, List<TLawyerData>> maps = new HashMap<>();
                    for (TLawyerData item : list) {
                        if (maps.containsKey(item.getDocid())) {
                            List<TLawyerData> group = maps.get(item.getDocid());
                            group.add(item);
                        } else {
                            List<TLawyerData> group = new ArrayList<>();
                            group.add(item);
                            maps.put(item.getDocid(), group);
                        }
                    }
                    for (String key : maps.keySet()) {
                        List<TLawyerData> group = maps.get(key);
                        JSONArray lawyers = new JSONArray();
                        for (TLawyerData item : group) {
                            JSONObject lawyer = new JSONObject();
                            lawyer.put("lawyer", item.getLawyer());
                            lawyer.put("lawFirm", item.getLawFirm());
                            lawyers.add(lawyer);
                        }
                        UpdateRequest updateRequest = new UpdateRequest(ElasticSearchConstant.CASE_INDEX, ElasticSearchConstant.DEFAULT_TYPE, key);
                        Map<String, Object> data = new HashMap<>();
                        data.put("lawyers", objectMapper.convertValue(lawyers, JSONArray.class));
                        updateRequest.doc(data);
                        bulkRequest.add(updateRequest);
                        rows ++;
                    }
                    client.bulk(bulkRequest, RequestOptions.DEFAULT);
                }
                log.info("同步案件额外数据律师至elasticsearch，page={}, rows={}, scrollId={}", page, rows, scrollId);
                totalRows += rows;
                page ++;
                if (req.getPageNum() > 0 && page > req.getPageNum()) {
                    break;
                }
                SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
                scrollRequest.scroll(scroll);
                response = client.scroll(scrollRequest, RequestOptions.DEFAULT);
            }
            ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
            clearScrollRequest.addScrollId(scrollId);
            ClearScrollResponse clearScrollResponse = client.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("同步案件额外数据律师至elasticsearch，结束，rows={}, 耗时={}s", totalRows, (System.currentTimeMillis()-startTime) / 1000);
        return totalRows;
    }

    private Map<String, Integer> getBriefMaps() {
        Map<String, Integer> map = new HashMap<>();
        List<TTreeContent> list = treeContentDao.findAllBrief();
        for (TTreeContent item : list) {
            map.put(item.getKeyWord(), item.getId());
        }
        return map;
    }

    private Integer getMaxCaseIdInElasticSearch() {
        try {
            SearchRequest searchRequest = new SearchRequest();
            searchRequest.indices(ElasticSearchConstant.CASE_INDEX);
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.matchAllQuery());
            searchSourceBuilder.sort("id", SortOrder.DESC);
            searchSourceBuilder.size(1);
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = searchResponse.getHits();
            if (hits.getHits().length > 0) {
                SearchHit hit = hits.getAt(0);
                Map<String, Object> datMap = hit.getSourceAsMap();
                return (Integer) datMap.get("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }


    private void createCaseIndex() {
        try {
            CreateIndexRequest request = new CreateIndexRequest(ElasticSearchConstant.CASE_INDEX);
            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject();
            {
                builder.startObject(ElasticSearchConstant.DEFAULT_TYPE);
                {
                    builder.startObject("properties");
                    {
                        builder.startObject("id");
                        {
                            builder.field("type", "long");
                        }
                        builder.endObject();
                        builder.startObject("docId");
                        {
                            builder.field("type", "keyword");
                        }
                        builder.endObject();
                        builder.startObject("type");
                        {
                            builder.field("type", "keyword");
                        }
                        builder.endObject();
                        builder.startObject("judgementType");
                        {
                            builder.field("type", "keyword");
                        }
                        builder.endObject();
                        builder.startObject("judgementDate");
                        {
                            builder.field("type", "keyword");
                        }
                        builder.endObject();
                        builder.startObject("judgementYear");
                        {
                            builder.field("type", "long");
                        }
                        builder.endObject();
                        builder.startObject("trialRound");
                        {
                            builder.field("type", "keyword");
                        }
                        builder.endObject();
                        builder.startObject("court.name");
                        {
                            builder.field("type", "keyword");
                        }
                        builder.endObject();
                        builder.startObject("court.simpleName");
                        {
                            builder.field("type", "keyword");
                        }
                        builder.endObject();
                        builder.startObject("court.type");
                        {
                            builder.field("type", "keyword");
                        }
                        builder.endObject();
                        builder.startObject("court.province");
                        {
                            builder.field("type", "keyword");
                        }
                        builder.endObject();
                        builder.startObject("court.city");
                        {
                            builder.field("type", "keyword");
                        }
                        builder.endObject();
                        builder.startObject("judgePerson.presidingJudge");
                        {
                            builder.field("type", "keyword");
                        }
                        builder.endObject();
                        builder.startObject("brief.name");
                        {
                            builder.field("type", "keyword");
                        }
                        builder.endObject();

                        // 中文分词设置
                        builder.startObject("title");
                        {
                            builder.field("type", "text");
                            builder.field("analyzer", "ik_max_word");
                            builder.field("search_analyzer", "ik_smart");
                        }
                        builder.endObject();
                        builder.startObject("courtOpinion");
                        {
                            builder.field("type", "text");
                            builder.field("analyzer", "ik_max_word");
                            builder.field("search_analyzer", "ik_smart");
                        }
                        builder.endObject();
                        builder.startObject("lawyers.lawyer");
                        {
                            builder.field("type", "keyword");
                        }
                        builder.endObject();
                        builder.startObject("lawyers.lawFirm");
                        {
                            builder.field("type", "keyword");
                        }
                        builder.endObject();
                    }
                    builder.endObject();
                }
                builder.endObject();
            }
            builder.endObject();
            request.mapping(ElasticSearchConstant.DEFAULT_TYPE, builder);
            AcknowledgedResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private int batchSyncToElasticSearch(List<Case> cases) {
        if (cases.size() > 0) {
            try {
                BulkRequest bulkRequest = new BulkRequest();
                for (Case item : cases) {
                    Map<String, Object> dataMap = objectMapper.convertValue(item, Map.class);
                    IndexRequest indexRequest = new IndexRequest(ElasticSearchConstant.CASE_INDEX, ElasticSearchConstant.DEFAULT_TYPE, item.getDocId()).source(dataMap);
                    bulkRequest.add(indexRequest);
                }
                BulkResponse response = client.bulk(bulkRequest, RequestOptions.DEFAULT);
                System.out.println(response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return cases.size();
    }
}
