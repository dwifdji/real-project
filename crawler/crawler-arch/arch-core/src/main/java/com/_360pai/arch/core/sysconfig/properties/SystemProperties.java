package com._360pai.arch.core.sysconfig.properties;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
@DisconfFile(filename = "system.properties")
public class SystemProperties {

    /**
     *
     *阿里爬虫 结束时间配置
     */
    private String crawlerAliEndDate;

    /**
     *
     *百度地图ak 配置
     */
    private String baiDuMapAk;


    @DisconfFileItem(name = "baiDu.map.ak")
    public String getBaiDuMapAk() {
        return baiDuMapAk;
    }

    public void setBaiDuMapAk(String baiDuMapAk) {
        this.baiDuMapAk = baiDuMapAk;
    }

    @DisconfFileItem(name = "crawler.ali.endDate")
    public String getCrawlerAliEndDate() {
        return crawlerAliEndDate;
    }

    public void setCrawlerAliEndDate(String crawlerAliEndDate) {
        this.crawlerAliEndDate = crawlerAliEndDate;
    }


}
