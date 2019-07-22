package com.tzCloud.arch.web.log;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述: elasticSearch设置类
 *
 * @author : whisky_vip
 * @date : 2018/8/27 14:09
 */
@Configuration
public class ElasticSearchConfiguration extends AbstractFactoryBean<RestHighLevelClient> {

    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchConfiguration.class);

    @Value("${spring.data.elasticsearch.properties.hostname}")
    private String  hostName;
    @Value("${spring.data.elasticsearch.properties.port}")
    private Integer port;

    private RestHighLevelClient restHighLevelClient;

    @Override
    public void destroy() {
        try {
            if (restHighLevelClient != null) {
                restHighLevelClient.close();
            }
        } catch (final Exception e) {
            logger.error("Error closing ElasticSearch client: ", e);
        }
    }

    @Override
    public Class<RestHighLevelClient> getObjectType() {
        return RestHighLevelClient.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public RestHighLevelClient createInstance() {
        return buildClient();
    }

    private RestHighLevelClient buildClient() {
        try {
            restHighLevelClient = new RestHighLevelClient(
                    RestClient.builder(new HttpHost(hostName, port, "http")));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return restHighLevelClient;
    }


}

