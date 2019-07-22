package com.tzCloud.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.tzCloud.arch.common.constant.ElasticSearchConstant;
import com.tzCloud.arch.common.utils.DateUtil;
import com.tzCloud.core.dao.legalEngine.TCourtDao;
import com.tzCloud.core.hanLP.HanLPFactory;
import com.tzCloud.core.model.legalEngine.TCourt;
import com.tzCloud.core.service.CourtMigrationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * @author zxiao
 * @Title: CourtMigrationServiceImpl
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019/5/16 19:18
 */
@Slf4j
@Service
public class CourtMigrationServiceImpl implements CourtMigrationService {

    @Autowired
    protected RestHighLevelClient client;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected TCourtDao courtDao;


    @Override
    public int migrationCourtToElasticSearch() {
        if (!isIndexExists(ElasticSearchConstant.COURT_INDEX)) {
            createCourtIndex();
        }
        BulkRequest bulkRequest = new BulkRequest();
        List<TCourt> tCourts = courtDao.selectAll();

        for (TCourt court : tCourts) {
            Map<String, Object> map = objectMapper.convertValue(court, Map.class);
            map.remove("deleteFlag");
            map.remove("createTime");
            map.remove("updateTime");
            Object type = map.get("type");
            map.remove("type");
            map.put("courtType", type);
            IndexRequest indexRequest = new IndexRequest(ElasticSearchConstant.COURT_INDEX,ElasticSearchConstant.DEFAULT_TYPE).id(court.getId().toString()).source(map);
            bulkRequest.add(indexRequest);
        }

        long startTime = System.currentTimeMillis();
        log.info("开始同步法院数据至elasticsearch，开始时间：{}", DateUtil.date2String(new Date(), DateUtil.NORM_DATETIME_PATTERN));
        int count = 0;
        try {
            int size = bulkRequest.requests().size();
            BulkResponse bulk = client.bulk(bulkRequest, RequestOptions.DEFAULT);
            count = size;
        } catch (IOException e) {
            e.printStackTrace();
        }
        long preTime = DateUtil.spendMs(startTime);
        log.info("结束法院数据同步，耗时：{}毫秒，结束时间：{}", preTime, DateUtil.date2String(new Date(), DateUtil.NORM_DATETIME_PATTERN));
        return count;
    }

    @Override
    public Object getAggs() {
        SearchRequest request = new SearchRequest(ElasticSearchConstant.CASE_INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.aggregation(AggregationBuilders.terms("court_type").field("court.name"));

        request.source(searchSourceBuilder);
        log.info("aggregations={}", searchSourceBuilder.aggregations().toString());
        SearchResponse search = null;
        try {
            search = client.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
//      System.out.println("search = " + JSON.toJSONString(search));
        Aggregations aggregations = search.getAggregations();
        Terms terms = aggregations.get("court_type");
        List<? extends Terms.Bucket> buckets = terms.getBuckets();
        List<Map<String,Object>> list = new ArrayList<>();
        for (Terms.Bucket bucket : buckets) {
            Map<String,Object> map = new HashMap<>();
            String keyAsString = bucket.getKeyAsString();
            long docCount = bucket.getDocCount();
            map.put("courtName",keyAsString);
            map.put("courtCount",docCount);
            list.add(map);
        }

        System.out.println("aggs = " + buckets);
        return list;
    }

    @Override
    public int repairCourtProvinceCity() {
        List<TCourt> list = courtDao.selectAll();
        Segment segment = HanLPFactory.builder().segment(true);
        List<JSONObject> result = new ArrayList<>();
        for (TCourt item : list) {
            if (StringUtils.isNotBlank(item.getCity())) {
                continue;
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", item.getId());
            jsonObject.put("name", item.getName());
            List<Term> terms = segment.seg(item.getName());
            log.info("terms={}", JSON.toJSONString(terms));
            String province = "";
            String city = "";
            String area = "";
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
            jsonObject.put("srcProvince", item.getProvince());
            jsonObject.put("srcCity", item.getCity());

            jsonObject.put("province", province);
            jsonObject.put("city", city);
            result.add(jsonObject);
        }
        log.info("result={}", result.toString());

        return 0;
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


    private void createCourtIndex() {
        try {
            CreateIndexRequest request = new CreateIndexRequest(ElasticSearchConstant.COURT_INDEX);
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
                        builder.startObject("name");
                        {
                            builder.field("type", "keyword");
                        }
                        builder.endObject();
                        builder.startObject("simpleName");
                        {
                            builder.field("type", "keyword");
                        }
                        builder.endObject();
                        builder.startObject("courtType");
                        {
                            builder.field("type", "keyword");
                        }
                        builder.endObject();
                        builder.startObject("address");
                        {
                            builder.field("type", "text");
                        }
                        builder.endObject();
                        builder.startObject("phone");
                        {
                            builder.field("type", "keyword");
                        }
                        builder.endObject();
                        builder.startObject("otherPhone");
                        {
                            builder.field("type", "keyword");
                        }
                        builder.endObject();
                        builder.startObject("workTime");
                        {
                            builder.field("type", "keyword");
                        }
                        builder.endObject();
                        builder.startObject("description");
                        {
                            builder.field("type", "text");
                        }
                        builder.endObject();
                        builder.startObject("imgUrl");
                        {
                            builder.field("type", "keyword");
                        }
                        builder.endObject();
                        builder.startObject("province");
                        {
                            builder.field("type", "text");
                        }
                        builder.endObject();
                        builder.startObject("city");
                        {
                            builder.field("type", "text");
                        }
                        builder.endObject();
                    }
                    builder.endObject();
                }
                builder.endObject();
            }
            builder.endObject();
            request.mapping(ElasticSearchConstant.DEFAULT_TYPE, builder);
            client.indices().create(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
