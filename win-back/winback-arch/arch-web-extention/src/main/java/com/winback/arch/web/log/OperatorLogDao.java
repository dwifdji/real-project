package com.winback.arch.web.log;

import com.winback.arch.common.OperatorLogModel;
import com.winback.arch.common.utils.DateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;

/**
 * 描述: 日志dao
 *
 * @author : whisky_vip
 * @date : 2018/8/27 15:49
 */
@Repository
public class OperatorLogDao {

//    private final String INDEX = "winback_operator_log";
    private final String INDEX = "winback_"+DateUtil.getTodayString();
    private final String TYPE  = "operator_log";

    private RestHighLevelClient restHighLevelClient;

    private ObjectMapper objectMapper;

    public OperatorLogDao(ObjectMapper objectMapper, RestHighLevelClient restHighLevelClient) {
        this.objectMapper = objectMapper;
        this.restHighLevelClient = restHighLevelClient;
    }

    public OperatorLogModel insertLog(OperatorLogModel model) {
        model.setId(UUID.randomUUID().toString());
        Map<String, Object> dataMap = objectMapper.convertValue(model, Map.class);
        IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, model.getId())
                .source(dataMap);
        try {
            IndexResponse response = restHighLevelClient.index(indexRequest);
        } catch (ElasticsearchException e) {
            e.getDetailedMessage();
        } catch (java.io.IOException ex) {
            ex.getLocalizedMessage();
        }
        return model;
    }


}
