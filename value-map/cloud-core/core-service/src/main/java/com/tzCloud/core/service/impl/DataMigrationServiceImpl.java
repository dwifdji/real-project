package com.tzCloud.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.tzCloud.arch.common.constant.ElasticSearchConstant;
import com.tzCloud.arch.common.constant.RedisKeyConstant;
import com.tzCloud.arch.common.utils.DateUtil;
import com.tzCloud.arch.core.redis.RedisCachemanager;
import com.tzCloud.core.common.SystemContent;
import com.tzCloud.core.dao.caseMatching.CaseHtmlDsrxxDao;
import com.tzCloud.core.dao.caseMatching.TLawyerDataDao;
import com.tzCloud.core.dao.caseMatching.TTreeContentDao;
import com.tzCloud.core.dao.lawyerSearch.TParseLawyerInfoDao;
import com.tzCloud.core.dao.legalEngine.*;
import com.tzCloud.core.facade.legalEngine.resp.DocVO;
import com.tzCloud.core.facade.legalEngine.resp.LawFirmESVO;
import com.tzCloud.core.facade.legalEngine.resp.LawyerStatistics;
import com.tzCloud.core.facade.legalEngine.vo.Case;
import com.tzCloud.core.hanLP.HanLPFactory;
import com.tzCloud.core.model.caseMatching.CaseHtmlDsrxx;
import com.tzCloud.core.model.caseMatching.TLawyerData;
import com.tzCloud.core.model.caseMatching.TTreeContent;
import com.tzCloud.core.model.lawyerSearch.TParseLawyerInfo;
import com.tzCloud.core.model.legalEngine.TCourt;
import com.tzCloud.core.model.legalEngine.TLawtimeCourt;
import com.tzCloud.core.model.legalEngine.TLawyerFirmInfo;
import com.tzCloud.core.service.CaseService;
import com.tzCloud.core.service.DataMigrationService;
import com.tzCloud.core.service.TParseLawyerInfoService;
import com.tzCloud.core.vo.LawyerVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xdrodger
 * @Title: DataMigrationServiceImpl
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019-04-22 09:21
 */
@Slf4j
@Service("dataMigrationService")
public class DataMigrationServiceImpl implements DataMigrationService {
    @Autowired
    protected TCaseDao caseDao;
    @Autowired
    protected TTreeContentDao treeContentDao;
    @Autowired
    protected CaseService caseService;
    @Autowired
    protected RestHighLevelClient client;
    @Autowired
    protected ObjectMapper objectMapper;
    @Resource
    protected RedisCachemanager redisCachemanager;
    @Autowired
    protected TLawtimeCourtDao lawtimeCourtDao;
    @Autowired
    protected TLawyerDataDao tLawyerDataDao;
    @Autowired
    protected TParseLawyerInfoDao tParseLawyerInfoDao;
    @Autowired
    protected TParseLawyerInfoService tParseLawyerInfoService;
    @Autowired
    protected TCourtDao courtDao;
    @Autowired
    protected TLawyerFirmInfoDao tLawyerFirmInfoDao;
    @Autowired
    protected TJudgePersonDao judgePersonDao;
    @Autowired
    protected TLawyerDataDao lawyerDataDao;
    @Autowired
    private CaseHtmlDsrxxDao caseHtmlDsrxxDao;

    @Override
    public void cacheData(String key) {
        if (RedisKeyConstant.BRIEF.equals(key)) {
            List<TTreeContent> list = treeContentDao.findAllBrief();
            for (TTreeContent item : list) {
                redisCachemanager.hSet(RedisKeyConstant.BRIEF, item.getId() + "", JSON.toJSONString(item));
            }
        }
        if (RedisKeyConstant.LAW_FIRM_SHORT.equals(key)) {
            List<String> lawFirmShortGroupBy = tParseLawyerInfoDao.getLawFirmShortGroupBy();
            lawFirmShortGroupBy.forEach(t -> {
                List<TLawyerFirmInfo> tLawyerFirmInfos = tLawyerFirmInfoDao.selectByFirmNameLike(t);
                redisCachemanager.hSet(RedisKeyConstant.LAW_FIRM_SHORT, t , JSON.toJSONString(tLawyerFirmInfos));
            });

        }
    }

    @Override
    public int migrationTableDataToElasticSearch(String table) {
        int rows = 0;
        switch (table) {
            case SystemContent.TABLE_T_LAWTIME_COURT:
                migrationLawtimeCourtTableDataToElasticSearch();
                break;
            default:
                break;
        }
        return rows;
    }

    private int migrationLawtimeCourtTableDataToElasticSearch() {
        int rows = 0;
        List<TLawtimeCourt> list = lawtimeCourtDao.selectAll();
        try {
            BulkRequest bulkRequest = new BulkRequest();
            for (TLawtimeCourt item : list) {
                Map<String, Object> dataMap = objectMapper.convertValue(item, Map.class);
                dataMap.put("simpleName", item.getName().replaceAll("[最高|高级|中级|基层|人民|法院]", ""));
                IndexRequest indexRequest = new IndexRequest(ElasticSearchConstant.LAWTIME_COURT_INDEX, ElasticSearchConstant.DEFAULT_TYPE).id(item.getId() + "").source(dataMap);
                bulkRequest.add(indexRequest);
            }
            BulkResponse response = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rows;
    }

    @Override
    public int migrationLawyerToElasticSearch() {
        Integer maxId;
        if (!isIndexExists(ElasticSearchConstant.LAWYER_INDEX)) {
            createLawyerIndex();
            maxId = -1;
        } else {
            // 判断es中最大的id
            maxId = getMaxLawyerIdInElasticSearch();
        }
        int pageNum = 1, pageSize = 1000, count = 0;
        long startTime = System.currentTimeMillis();
        log.info("开始同步律师至elasticsearch");
        while (true) {
            PageHelper.startPage(pageNum, pageSize);
            PageInfo<TParseLawyerInfo> byIdGreaterThan = tParseLawyerInfoDao.findByIdGreaterThan(pageNum, pageSize, maxId);
            List<TParseLawyerInfo> list = byIdGreaterThan.getList();
            if (list.size() <= 0) {
                break;
            }
            List<Long> idList = list.stream().map(TParseLawyerInfo::getId).collect(Collectors.toList());
            // 获取docId
//            List<TLawyerData> docIdByLawyerId = tLawyerDataDao.findLawyerByLawyerId(idList);
            // 修改为从es中获取
            List<TLawyerData> docIdByLawyerId = getCaseBriefFromEsByLawyerId(idList);
            // put map
            Map<Integer, List<DocVO>> map2 = new HashMap<>(idList.size());
            for (TLawyerData tLawyerData : docIdByLawyerId) {
                if (map2.containsKey(tLawyerData.getLawyerId())) {
                    List<DocVO> docVOS = map2.get(tLawyerData.getLawyerId());
                    docVOS.add(new DocVO(tLawyerData.getDocid(), tLawyerData.getBrief()));
                } else {
                    List<DocVO> docVOS = new ArrayList<>();
                    docVOS.add(new DocVO(tLawyerData.getDocid(), tLawyerData.getBrief()));
                    map2.put(tLawyerData.getLawyerId(), docVOS);
                }
            }
            BulkRequest bulkRequest = new BulkRequest();
            // 遍历律师
            for (TParseLawyerInfo tParseLawyerInfo : list) {
                tParseLawyerInfo.setCaseBrief(map2.get(tParseLawyerInfo.getId().toString()));
                Map<String, Object> docMap = objectMapper.convertValue(tParseLawyerInfo, Map.class);
                IndexRequest indexRequest = new IndexRequest(ElasticSearchConstant.LAWYER_INDEX, ElasticSearchConstant.DEFAULT_TYPE).id(tParseLawyerInfo.getId().toString()).source(docMap);
                bulkRequest.add(indexRequest);
            }
            try {
                int size = bulkRequest.requests().size();
                if (size == 0)
                    continue;
                count += size;
                BulkResponse bulk = client.bulk(bulkRequest, RequestOptions.DEFAULT);
                System.out.println("count: " + count);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!byIdGreaterThan.isHasNextPage()) {
                break;
            }
            pageNum++;
        }
        log.info("结束同步律师至elasticsearch，耗时={}s", (System.currentTimeMillis() - startTime) / 1000);
        return count;
    }


    private List<TLawyerData> getCaseBriefFromEsByLawyerId(List<Long> idList) {
        // searchRequest  QueryBuilders SearchSourceBuilder client
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(ElasticSearchConstant.CASE_INDEX);
        searchRequest.types(ElasticSearchConstant.DEFAULT_TYPE);
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        for (Long l : idList) {
            boolQueryBuilder.should(QueryBuilders.matchQuery("lawyerId", l));
        }
        // 构造query
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.size(idList.size());
        searchRequest.source(searchSourceBuilder);
        try {
            log.info("index: {}, query: {}",ElasticSearchConstant.CASE_INDEX, searchRequest.source().toString());
            SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
            return getLawyerDataFromSearch(search);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return new LinkedList<>();
    }

    private List<TLawyerData> getLawyerDataFromSearch(SearchResponse search) {
        SearchHits hits = search.getHits();
        SearchHit[] hits1 = hits.getHits();
        List<TLawyerData> objects = new LinkedList<>();
        for (SearchHit hit : hits1) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            Case aCase = objectMapper.convertValue(sourceAsMap, Case.class);
            //List<Integer> lawyerId = aCase.getLawyerId();
            //for (Integer id : lawyerId) {
            //    TLawyerData tLawyerData = new TLawyerData();
            //    tLawyerData.setId(id);
            //    tLawyerData.setDocid(aCase.getDocId());
            //    tLawyerData.setBrief(aCase.getBrief().getName());
            //    objects.add(tLawyerData);
            //}
        }
        return objects;
    }

    @Override
    public int migrationLawyerToElasticSearchWithoutDocId() {
        if (!isIndexExists(ElasticSearchConstant.LAWYER_INDEX)) {
            createLawyerIndex();
        }
        // 判断es中最大的id
        Integer maxId = getMaxLawyerIdInElasticSearch();
        int pageNum = 1, pageSize = 1000, count = 0;
        long startTime = System.currentTimeMillis();
        log.info("开始同步律师至elasticsearch");
        while (true) {
            PageHelper.startPage(pageNum, pageSize);
            PageInfo<LawyerVO> byIdGreaterThan = tParseLawyerInfoDao.findByIdGreaterThan2(pageNum, pageSize, maxId);
            List<LawyerVO> list = byIdGreaterThan.getList();
            if (list.size() <= 0) {
                break;
            }
            BulkRequest bulkRequest = new BulkRequest();
            // 遍历律师
            for (LawyerVO lawyerVO : list) {
                Map<String, Object> docMap = objectMapper.convertValue(lawyerVO, Map.class);
                IndexRequest indexRequest = new IndexRequest(ElasticSearchConstant.LAWYER_INDEX, ElasticSearchConstant.DEFAULT_TYPE).id(lawyerVO.getId().toString()).source(docMap);
                bulkRequest.add(indexRequest);
            }
            try {
                int size = bulkRequest.requests().size();
                count += size;
                BulkResponse bulk = client.bulk(bulkRequest, RequestOptions.DEFAULT);
                System.out.println("count: " + count);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!byIdGreaterThan.isHasNextPage()) {
                break;
            }
            pageNum++;
        }
        log.info("结束同步律师至elasticsearch，耗时={}s", (System.currentTimeMillis() - startTime) / 1000);
        return count;
    }

    @Override
    public int migrationLawFirmToElasticSearch() {
        if (!isIndexExists(ElasticSearchConstant.LAW_FIRM_INDEX)) {
            createLawFirmIndex();
        }
        Segment segment = HanLPFactory.builder().segment(true);
        String lastToEsTime = getMaxLawFirmInElasticSearch();
        BulkRequest bulkRequest = new BulkRequest();
        int count = 0;
        List<TParseLawyerInfo> all = tParseLawyerInfoDao.getLawFirmGroupBylawFirm(lastToEsTime);
        Map<Long, Map<String, String>> docIdsMap = tParseLawyerInfoDao.getDocIdsByGroupId();
        Map<Long, TParseLawyerInfo> container = new HashMap<>();
        long startTime = System.currentTimeMillis();
        log.info("开始同步律所至elasticsearch");
        Date date = new Date();
        for (int i = 0, size = all.size(); i < size; i++) {
            TParseLawyerInfo get = all.get(i);
            String[] split = get.getIds().split(",");
            List<LawyerStatistics> lawyerStatistics = new ArrayList<>(split.length);
            LawFirmESVO lawFirmESVO = new LawFirmESVO();
            for (String lawyerId : split) {
                LawyerStatistics s = new LawyerStatistics();
                s.id = Long.parseLong(lawyerId);
                long l = Long.parseLong(lawyerId);
                if (docIdsMap.containsKey(l)) {
                    String s1 = docIdsMap.get(l).get("docIds");
                    if (StringUtils.isNotBlank(s1)) {
                        s.docIds = Arrays.asList(s1.split(","));
                    }
                }
                lawyerStatistics.add(s);
            }
            lawFirmESVO.setLawFirm(get.getLawFirm());
            lawFirmESVO.setLawFirmCity(get.getLawFirmCity());
            lawFirmESVO.setLawFirmProvince(get.getLawFirmProvince());
            lawFirmESVO.setLawyerStatistics(lawyerStatistics);
            lawFirmESVO.setToESTime(DateUtil.format(date, DateUtil.NORM_DATETIME_PATTERN));
            // 获取律所的具体信息
            List<TLawyerFirmInfo> tLawyerFirmInfos = getLawyerFirmInfosByCache(get.getLawFirmShort());
            if (tLawyerFirmInfos != null) {
                TLawyerFirmInfo fitLawFirm = getFitLawFirm(get, tLawyerFirmInfos, segment);
                if (fitLawFirm != null) {
                    lawFirmESVO.setContactAddress(fitLawFirm.getContactAddress());
                    lawFirmESVO.setFoundTime(fitLawFirm.getFoundTime());
                    lawFirmESVO.setContactNumber(fitLawFirm.getContactNumber());
                    lawFirmESVO.setIntroduction(fitLawFirm.getIntroduction());
                    lawFirmESVO.setLicenseNumber(fitLawFirm.getLicenseNumber() == null ? fitLawFirm.getTyshxydm() : fitLawFirm.getLicenseNumber());
                }
            }
            Map docMap = objectMapper.convertValue(lawFirmESVO, Map.class);
            IndexRequest indexRequest = new IndexRequest(ElasticSearchConstant.LAW_FIRM_INDEX, ElasticSearchConstant.DEFAULT_TYPE).id(i + "").source(docMap);
            bulkRequest.add(indexRequest);
            int size2= bulkRequest.requests().size();
            if (size2 >= 500) {
                try {
                    count += size2;
                    BulkResponse bulk = client.bulk(bulkRequest, RequestOptions.DEFAULT);
                    bulkRequest = new BulkRequest();
                    System.out.println("count: "+count);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        log.info("结束同步律所至elasticsearch，耗时={}s", (System.currentTimeMillis() - startTime) / 1000);
        return count;
    }

    @Override
    public int migrationLawFirmToElasticSearchPage() {
        String lastToEsTime;
        if (!isIndexExists(ElasticSearchConstant.LAW_FIRM_INDEX)) {
            createLawFirmIndex();
            lastToEsTime = null;
        } else {
            lastToEsTime = getMaxLawFirmInElasticSearch();
        }
        Segment segment = HanLPFactory.builder().segment(true);
        long startTime = System.currentTimeMillis();
        log.info("开始同步律所至elasticsearch");
        int count = 0, pageNum=0, pageSize=10000;
        BulkRequest bulkRequest = new BulkRequest();
        while (true) {
            PageHelper.startPage(pageNum, pageSize);
            List<TParseLawyerInfo> all = tParseLawyerInfoDao.getLawFirmGroupBylawFirm(lastToEsTime);
            PageInfo<TParseLawyerInfo> pageInfo = new PageInfo<>(all);
            Map<Long, Map<String, String>> docIdsMap = lawyerDocMap(all);
            lawFirmProcess(all, docIdsMap, bulkRequest, segment);
            if (!pageInfo.isHasNextPage()) {
                break;
            }
            pageNum++;
        }
        log.info("结束同步律所至elasticsearch，耗时={}s", (System.currentTimeMillis() - startTime) / 1000);
        return count;
    }


    private void lawFirmProcess(List<TParseLawyerInfo> all, Map<Long, Map<String, String>> docIdsMap, BulkRequest bulkRequest, Segment segment) {
        Date date = new Date();
        for (int i = 0, size = all.size(); i < size; i++) {
            TParseLawyerInfo get = all.get(i);
            String[] split = get.getIds().split(",");
            List<LawyerStatistics> lawyerStatistics = new ArrayList<>(split.length);
            LawFirmESVO lawFirmESVO = new LawFirmESVO();
            for (String lawyerId : split) {
                LawyerStatistics s = new LawyerStatistics();
                long lawyerIdL = Long.parseLong(lawyerId);
                s.id = lawyerIdL;
                if (docIdsMap.containsKey(lawyerIdL)) {
                    String s1 = docIdsMap.get(lawyerIdL).get("docIds");
                    if (StringUtils.isNotBlank(s1)) {
                        s.docIds = Arrays.asList(s1.split(","));
                    }
                }
                lawyerStatistics.add(s);
            }
            lawFirmESVO.setLawFirm(get.getLawFirm());
            lawFirmESVO.setLawFirmCity(get.getLawFirmCity());
            lawFirmESVO.setLawFirmProvince(get.getLawFirmProvince());
            lawFirmESVO.setLawyerStatistics(lawyerStatistics);
            lawFirmESVO.setToESTime(DateUtil.format(date, DateUtil.NORM_DATETIME_PATTERN));
            // 获取律所的具体信息
            List<TLawyerFirmInfo> tLawyerFirmInfos = getLawyerFirmInfosByCache(get.getLawFirmShort());
            if (tLawyerFirmInfos != null) {
                TLawyerFirmInfo fitLawFirm = getFitLawFirm(get, tLawyerFirmInfos, segment);
                if (fitLawFirm != null) {
                    lawFirmESVO.setContactAddress(fitLawFirm.getContactAddress());
                    lawFirmESVO.setFoundTime(fitLawFirm.getFoundTime());
                    lawFirmESVO.setContactNumber(fitLawFirm.getContactNumber());
                    lawFirmESVO.setIntroduction(fitLawFirm.getIntroduction());
                    lawFirmESVO.setLicenseNumber(fitLawFirm.getLicenseNumber() == null ? fitLawFirm.getTyshxydm() : fitLawFirm.getLicenseNumber());
                }
            }
            Map docMap = objectMapper.convertValue(lawFirmESVO, Map.class);
            IndexRequest indexRequest = new IndexRequest(ElasticSearchConstant.LAW_FIRM_INDEX, ElasticSearchConstant.DEFAULT_TYPE).id(i + "").source(docMap);
            bulkRequest.add(indexRequest);
            int size2= bulkRequest.requests().size();
            if (size2 >= 500) {
                try {
                    BulkResponse bulk = client.bulk(bulkRequest, RequestOptions.DEFAULT);
                    bulkRequest = new BulkRequest();
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
        }
    }

    private Map<Long, Map<String, String>> lawyerDocMap(List<TParseLawyerInfo> all) {
        if (all.isEmpty())
            return new HashMap<>();
        List<String> collect = all.stream().map(TParseLawyerInfo::getIds).collect(Collectors.toList());
        Map<Long, Map<String, String>> result = new HashMap<>(collect.size()*5);
        for (String str : collect) {
            Map<Long, Map<String, String>> groupDocIdByLawyerIds = tLawyerDataDao.findGroupDocIdByLawyerIds(str);
            result.putAll(groupDocIdByLawyerIds);
        }
        return result;
    }

    @Override
    public int migrationLawFirmToElasticSearchWithoutDocId() {
        if (!isIndexExists(ElasticSearchConstant.LAW_FIRM_INDEX)) {
            createLawFirmIndex();
        }
        String lastToEsTime = getMaxLawFirmInElasticSearch();
        Segment segment = HanLPFactory.builder().segment(true);
        BulkRequest bulkRequest = new BulkRequest();
        int count = 0;
        List<TParseLawyerInfo> all = tParseLawyerInfoDao.getLawFirmGroupBylawFirm(lastToEsTime);
        long startTime = System.currentTimeMillis();
        log.info("开始同步律所至elasticsearch");
        Date date = new Date();
        for (int i = 0, size = all.size(); i < size; i++) {
            TParseLawyerInfo get = all.get(i);
            LawFirmESVO lawFirmESVO = new LawFirmESVO();
            lawFirmESVO.setLawFirm(get.getLawFirm());
            lawFirmESVO.setLawFirmCity(get.getLawFirmCity());
            lawFirmESVO.setLawFirmProvince(get.getLawFirmProvince());
            lawFirmESVO.setToESTime(DateUtil.format(date, DateUtil.NORM_DATETIME_PATTERN));
            // 获取律所的具体信息
            List<TLawyerFirmInfo> tLawyerFirmInfos = getLawyerFirmInfosByCache(get.getLawFirmShort());
            if (tLawyerFirmInfos != null) {
                TLawyerFirmInfo fitLawFirm = getFitLawFirm(get, tLawyerFirmInfos, segment);
                if (fitLawFirm != null) {
                    lawFirmESVO.setContactAddress(fitLawFirm.getContactAddress());
                    lawFirmESVO.setFoundTime(fitLawFirm.getFoundTime());
                    lawFirmESVO.setContactNumber(fitLawFirm.getContactNumber());
                    lawFirmESVO.setIntroduction(fitLawFirm.getIntroduction());
                    lawFirmESVO.setLicenseNumber(fitLawFirm.getLicenseNumber() == null ? fitLawFirm.getTyshxydm() : fitLawFirm.getLicenseNumber());
                }
            }
            Map docMap = objectMapper.convertValue(lawFirmESVO, Map.class);
            IndexRequest indexRequest = new IndexRequest(ElasticSearchConstant.LAW_FIRM_INDEX, ElasticSearchConstant.DEFAULT_TYPE).id(i + "").source(docMap);
            bulkRequest.add(indexRequest);
            int size2= bulkRequest.requests().size();
            if (size2 >= 500) {
                try {
                    count += size2;
                    BulkResponse bulk = client.bulk(bulkRequest, RequestOptions.DEFAULT);
                    bulkRequest = new BulkRequest();
                    System.out.println("count: "+count);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        log.info("结束同步律所至elasticsearch，耗时={}s", (System.currentTimeMillis() - startTime) / 1000);
        return count;
    }

    @Override
    public int migrationCaseDsrxxToElasticSearch() {
        Integer maxId;
        if (!isIndexExists(ElasticSearchConstant.CASE_DSRXX)) {
            createCaseDsrxxIndex();
            maxId = -1;
        } else {
            maxId = getMaxCaseDsrxxInElasticSearch();
        }
        long startTime = System.currentTimeMillis();
        log.info("开始同步案例当事人至elasticsearch");
        int pageNum = 1;
        int count ;
        while (true) {
            PageHelper.startPage(pageNum, 10000);
//            List<CaseHtmlDsrxx> caseHtmlDsrxxes = caseHtmlDsrxxDao.selectAll();
            List<CaseHtmlDsrxx> caseHtmlDsrxxes = caseHtmlDsrxxDao.findMoreThanId(maxId);
            PageInfo<CaseHtmlDsrxx> pageInfo = new PageInfo<>(caseHtmlDsrxxes);
            count = caseDsrxxToEs(pageInfo.getList());
            if (!pageInfo.isHasNextPage()) {
                break;
            }
            pageNum++;
        }
        log.info("结束同步案例当事人至elasticsearch，耗时={}s", (System.currentTimeMillis() - startTime) / 1000);
        return count;
    }

    private Integer caseDsrxxToEs(List<CaseHtmlDsrxx> list) {
        if (!list.isEmpty()) {
            BulkRequest bulkRequest = new BulkRequest();
            for (CaseHtmlDsrxx caseHtmlDsrxx : list) {
                Map docMap = objectMapper.convertValue(caseHtmlDsrxx, Map.class);
                IndexRequest indexRequest = new IndexRequest(ElasticSearchConstant.CASE_DSRXX, ElasticSearchConstant.DEFAULT_TYPE).id(caseHtmlDsrxx.getId() + "").source(docMap);
                bulkRequest.add(indexRequest);
            }
            try {
                BulkResponse bulk = client.bulk(bulkRequest, RequestOptions.DEFAULT);
                return bulkRequest.requests().size();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }


    public List<TLawyerFirmInfo> getLawyerFirmInfosByCache(String shortName) {
//        String o = null;
//        try {
//            o =(String) redisCachemanager.hGet(RedisKeyConstant.LAW_FIRM_SHORT, shortName);
//        } catch (Exception e) {
//            log.info("redis 获取律所信息异常：{}", e.getMessage());
//        }
//        if (StringUtils.isNotBlank(o)) {
//            return JSON.parseArray(o, TLawyerFirmInfo.class);
//        } else {
//            List<TLawyerFirmInfo> tLawyerFirmInfos = tLawyerFirmInfoDao.selectByFirmNameLike(shortName);
//            if (CollectionUtils.isEmpty(tLawyerFirmInfos)) {
//                return null;
//            }
//            try {
//                redisCachemanager.hSet(RedisKeyConstant.LAW_FIRM_SHORT, shortName , JSON.toJSONString(tLawyerFirmInfos));
//            } catch (Exception e) {
//                log.info("redis 存放律所信息异常，{}", e.getMessage());
//            }
//            return tLawyerFirmInfos;
//        }
        if (StringUtils.isNotBlank(shortName)) {
            List<TLawyerFirmInfo> tLawyerFirmInfos = tLawyerFirmInfoDao.selectByFirmNameLike(shortName);
            if (CollectionUtils.isEmpty(tLawyerFirmInfos)) {
                return null;
            }
            return tLawyerFirmInfos;
        }
        return null;
    }

    private static TLawyerFirmInfo getFitLawFirm(TParseLawyerInfo get0, List<TLawyerFirmInfo> tLawyerFirmInfos, Segment segment) {
        for (TLawyerFirmInfo tLawyerFirmInfo : tLawyerFirmInfos) {
            Map<String, String> stringMap = UnusualDataProcessServiceImpl.lawFirmNameProcess(tLawyerFirmInfo.getFirmName(), segment);
            // short 相同 省份相同
            // short 相同 没有省份
            if (get0.getLawFirmShort().equals(stringMap.get("lawFirm"))) {
                if (get0.getLawFirmProvince() != null && get0.getLawFirmProvince().equals(stringMap.get("province"))) {
                    return tLawyerFirmInfo;
                }
                if (get0.getLawFirmProvince() == null && stringMap.get("province") == null) {
                    return tLawyerFirmInfo;
                }
            }
        }
        return CollectionUtils.isEmpty(tLawyerFirmInfos) ? null : tLawyerFirmInfos.get(0);
    }

    @Override
    public int resetCourtName() {
        List<TCourt> list = courtDao.selectAll();
        List<JSONObject> result = new ArrayList<>();
        int i = 0;
        Segment segment = HanLPFactory.builder().segment(true);
        for (TCourt item : list) {
            //if (i > 50) {
            //    break;
            //}
            if (StringUtils.isNotBlank(item.getAddress())) {
                continue;
            }
            i++;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", item.getId());
            jsonObject.put("name", item.getName());
            jsonObject.put("type", item.getType());
            try {
                String province = "";
                String city = "";
                String area = "";
                List<Term> terms = segment.seg(item.getName());
                log.info("terms={}", JSON.toJSONString(terms));
                for (Term term : terms) {
                    String nature = term.nature.toString();
                    if (nature.startsWith("city_")) {
                        city = term.word;
                        province = nature.split("_")[1];
                    } else if (nature.startsWith("area_")) {
                        area = term.word;
                        province = nature.split("_")[2];
                        city = nature.split("_")[1];
                    }
                }
                BoolQueryBuilder q = QueryBuilders.boolQuery();
                q.must(QueryBuilders.termQuery("type", item.getType()));
                if (StringUtils.isNotBlank(province)) {
                    q.must(QueryBuilders.matchPhraseQuery("province", province));
                    jsonObject.put("province", province);
                }
                if (StringUtils.isNotBlank(city)) {
                    q.must(QueryBuilders.matchPhraseQuery("city", city));
                    jsonObject.put("city", city);
                }
                if (StringUtils.isNotBlank(area)) {
                    jsonObject.put("area", area);
                }
                q.should(QueryBuilders.matchQuery("simpleName", item.getName()));

                SearchRequest request = new SearchRequest();
                request.indices(ElasticSearchConstant.LAWTIME_COURT_INDEX);
                SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
                searchSourceBuilder.query(q);
                searchSourceBuilder.size(3);
                request.source(searchSourceBuilder);
                log.info("query={}", q.toString());
                SearchResponse response = client.search(request, RequestOptions.DEFAULT);
                SearchHits hits = response.getHits();
                Iterator<SearchHit> itr = hits.iterator();
                JSONArray matchTerms = new JSONArray();
                while (itr.hasNext()) {
                    SearchHit hit = itr.next();
                    if (hit.getScore() < 5) {
                        continue;
                    }
                    Map<String, Object> data = hit.getSourceAsMap();
                    JSONObject matchTerm = new JSONObject();
                    matchTerm.put("id", data.get("id"));
                    matchTerm.put("name", data.get("name"));
                    matchTerm.put("simpleName", data.get("simpleName"));
                    matchTerm.put("address", data.get("address"));
                    matchTerm.put("city", data.get("city"));
                    matchTerm.put("province", data.get("province"));
                    matchTerm.put("score", hit.getScore());
                    matchTerms.add(matchTerm);
                }
                jsonObject.put("matchTerms", matchTerms);
            } catch (Exception e) {
                e.printStackTrace();
            }
            result.add(jsonObject);
        }
        log.info(JSON.toJSONString(result));

        //for (JSONObject item : result) {
        //    String province = item.getString("province");
        //    String city = item.getString("city");
        //    if (StringUtils.isNotBlank(city) && StringUtils.isNotBlank(province)) {
        //        TCourt court = new TCourt();
        //        court.setId(item.getInteger("id"));
        //        court.setProvince(province);
        //        court.setCity(city);
        //        courtDao.updateById(court);
        //    }
        //}

        return 0;
    }



    private String getIndexId() {
        long r1 = Thread.currentThread().getId();
        String r2 = String.valueOf(System.nanoTime()).substring(11);
        int r3 = randInt(0, 999);
//        System.out.printf("当前线程id: %d,毫微秒末位: %s,随机数:  %d \n", r1, r2, r3);
        return r1 + r2 + r3;
    }

    private static int randInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    private Integer getMaxLawyerIdInElasticSearch() {
        try {
            SearchRequest searchRequest = new SearchRequest();
            searchRequest.indices(ElasticSearchConstant.LAWYER_INDEX);
            searchRequest.types(ElasticSearchConstant.DEFAULT_TYPE);
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

    private Integer getMaxCaseDsrxxInElasticSearch() {
        try {
            SearchRequest searchRequest = new SearchRequest();
            searchRequest.indices(ElasticSearchConstant.CASE_DSRXX);
            searchRequest.types(ElasticSearchConstant.DEFAULT_TYPE);
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

    private String getMaxLawFirmInElasticSearch() {
        try {
            SearchRequest searchRequest = new SearchRequest();
            searchRequest.indices(ElasticSearchConstant.LAW_FIRM_INDEX);
            searchRequest.types(ElasticSearchConstant.DEFAULT_TYPE);
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.matchAllQuery());
            searchSourceBuilder.sort("toESTime", SortOrder.DESC);
            searchSourceBuilder.size(1);
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = searchResponse.getHits();
            if (hits.getHits().length > 0) {
                SearchHit hit = hits.getAt(0);
                Map<String, Object> datMap = hit.getSourceAsMap();
                return (String) datMap.get("toESTime");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //判断索引是否存在
    public boolean isIndexExists(String index) {
        try {
            GetIndexRequest request = new GetIndexRequest();
            request.indices(index);
            request.local(false);
            request.humanReadable(true);
            request.includeDefaults(false);
            boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
            return exists;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    private void createLawyerIndex() {
        try {
            CreateIndexRequest request = new CreateIndexRequest(ElasticSearchConstant.LAWYER_INDEX);
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
                        builder.startObject("lawyerName");
                        {
                            builder.field("type", "text");
                        }
                        builder.endObject();
                        builder.startObject("lawFirm");
                        {
                            builder.field("type", "keyword");
                        }
                        builder.endObject();
                        builder.startObject("lawFirmShort");
                        {
                            builder.field("type", "keyword");
                        }
                        builder.endObject();
                        builder.startObject("lawFirmCity");
                        {
                            builder.field("type", "keyword");
                        }
                        builder.endObject();
                        builder.startObject("lawFirmProvince");
                        {
                            builder.field("type", "keyword");
                        }
                        builder.endObject();
                        builder.startObject("years");
                        {
                            builder.field("type", "integer");
                        }
                        builder.endObject();
                        builder.startObject("caseBrief.docId");
                        {
                            builder.field("type", "keyword");
                        }
                        builder.endObject();
                        builder.startObject("caseBrief.brief");
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
//            request.source(builder);
            request.mapping(ElasticSearchConstant.DEFAULT_TYPE, builder);
            AcknowledgedResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createLawFirmIndex() {
        try {
            CreateIndexRequest request = new CreateIndexRequest(ElasticSearchConstant.LAW_FIRM_INDEX);
            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject();
            {
                builder.startObject(ElasticSearchConstant.DEFAULT_TYPE);
                {
                    builder.startObject("properties");
                    {
                        builder.startObject("lawFirm");
                        {
                            builder.field("type", "keyword");
                        }
                        builder.endObject();
                        builder.startObject("lawFirmProvince");
                        {
                            builder.field("type", "keyword");
                        }
                        builder.endObject();
                        builder.startObject("lawyerStatistics.docIds");
                        {
                            builder.field("type", "keyword");
                        }
                        builder.endObject();
                        builder.startObject("toESTime");
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
//            request.source(builder);
            request.mapping(ElasticSearchConstant.DEFAULT_TYPE, builder);
            AcknowledgedResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createCaseDsrxxIndex() {
        try {
            CreateIndexRequest request = new CreateIndexRequest(ElasticSearchConstant.CASE_DSRXX);
            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject();
            {
                builder.startObject(ElasticSearchConstant.DEFAULT_TYPE);
                {
                    builder.startObject("properties");
                    {
                        builder.startObject("docId");
                        {
                            builder.field("type", "keyword");
                        }
                        builder.endObject();
                        builder.startObject("name");
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
//            request.source(builder);
            request.mapping(ElasticSearchConstant.DEFAULT_TYPE, builder);
            AcknowledgedResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
