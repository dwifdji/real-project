package com.tzCloud.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;

import java.util.*;

/**
 * @author xdrodger
 * @Title: SimpleTest
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019-04-25 13:11
 */
public class SimpleTest {


    @Test
    public void testEs() {
        try {
            RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
                    new HttpHost("127.0.0.1", 9200, "http")));

            ObjectMapper objectMapper = new ObjectMapper();

            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.matchAllQuery());
            searchSourceBuilder.aggregation(AggregationBuilders.terms("top_10_states").field("state").size(10));
            SearchRequest searchRequest = new SearchRequest();
            searchRequest.indices("test");
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            System.out.println(searchResponse.getTotalShards());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testInsertToEs() {
        try {
            RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")));
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("id", 3);
            dataMap.put("name", "abc-" + RandomStringUtils.randomAlphanumeric(6));
            dataMap.put("year", RandomUtils.nextInt(10,100));
            IndexRequest indexRequest = new IndexRequest("test", "test1", dataMap.get("id") + "").source(dataMap);
            try {
                IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);
            } catch (ElasticsearchException e) {
                e.getDetailedMessage();
            } catch (java.io.IOException ex) {
                ex.getLocalizedMessage();
            }
            System.out.println("--");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetEs() {
        try {
            RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
                    new HttpHost("127.0.0.1", 9200, "http")));
            GetRequest request = new GetRequest(
                    "test",//index name
                    "test1",  //type
                    "1");   //id
            GetResponse response = client.get(request, RequestOptions.DEFAULT);
            System.out.println(response.getSource());
            System.out.println("--");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSortEs() {
        try {
            RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
                    new HttpHost("127.0.0.1", 9200, "http")));
            SearchRequest searchRequest = new SearchRequest();
            searchRequest.indices("test");
            searchRequest.types("test1");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.matchAllQuery());
            searchSourceBuilder.sort("id", SortOrder.DESC);
            searchSourceBuilder.size(1);
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = searchResponse.getHits();
            Iterator<SearchHit> itr = hits.iterator();
            while (itr.hasNext()) {
                SearchHit hit = itr.next();
                System.out.println(hit.getId() + "--" + hit.getSourceAsMap());
            }
            System.out.println();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testBulkInsertToEs() {
        try {
            RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")));
            BulkRequest bulkRequest = new BulkRequest();
            for (int i = 0; i < 10; i ++) {
                Map<String, Object> dataMap = new HashMap<>();
                dataMap.put("id", i + 10);
                dataMap.put("name", "abc-" + RandomStringUtils.randomAlphanumeric(6));
                dataMap.put("year", RandomUtils.nextInt(10,100));
                IndexRequest indexRequest = new IndexRequest("test", "test1", dataMap.get("id") + "").source(dataMap);
                bulkRequest.add(indexRequest);
            }
            try {
                BulkResponse response = client.bulk(bulkRequest, RequestOptions.DEFAULT);
            } catch (ElasticsearchException e) {
                e.getDetailedMessage();
            } catch (java.io.IOException ex) {
                ex.getLocalizedMessage();
            }
            System.out.println("--");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testString() {
        List<String> list = new ArrayList<>();
        list.add("重庆市第五中级人民法院");
        list.add("长子中县人民法院");
        for (String str : list) {
            System.out.println(str.replaceAll("[最高|高级|中级|基层|人民|法院]", ""));
        }
    }
}
