package com._360pai.arch.web.log;

import com._360pai.arch.common.OperatorLogModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

/**
 * 描述 日志dao 存MongoDB
 *
 * @author : whisky_vip
 * @date : 2018/9/25 15:23
 */
@Repository
public class OperatorLogMongodbDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public OperatorLogModel insertLog(OperatorLogModel model) {
        try {
            mongoTemplate.save(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }


}
