package com.tzCloud.core.provider.legalEngine;

import com.alibaba.dubbo.config.annotation.Service;
import com.tzCloud.core.facade.legalEngine.LawyerSqlDataUpdateFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author xiaolei
 * @create 2019-07-08 15:50
 */
@Slf4j
@Service(version = "1.0.0")
@Component
public class LawyerSqlDataUpdateProvider implements LawyerSqlDataUpdateFacade {

    /**
     * 先让晓亚跑t_lawyer_data表
     * 根据新的数据  律师名称 律所名称  律所所在地 查询  律师的es 存在则删除 处理 新增  不存在不处理
     * 根据新的数据  律所名称 律所所在地 查询  律所的es 存在则删除 处理 新增  不存在不处理
     * 然后再跑数据库的新增
     */
    @Override
    public void newCaseDataProcess() {
        String html = "";
        // 解析出律师名称
    }
}
