package com._360pai.crawler.service.alipm;

import com._360pai.crawler.model.alipm.TAliPmSf;

/**
 *
 */
public interface AliPmSfService {

    /**
     *
     *保存获取更新阿里司法拍卖
     */
    TAliPmSf saveAliPmSf(TAliPmSf model);




    /**
     *
     *修复经纬度数据
     */
    void repairData(TAliPmSf model);


    /**
     *
     *修复建筑面积字段
     */
    void repairAreaData(TAliPmSf model);



}
