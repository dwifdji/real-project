
package com._360pai.core.dao.account.mapper;

import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.account.TAgencyCondition;
import com._360pai.core.model.account.TAgency;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>TAgency数据层的基础操作</p>
 * @ClassName: TAgencyMapper
 * @Description: TAgency数据层的基础操作
 * @author Generator
 * @date 2018年08月17日 15时47分34秒
 */
@Mapper
public interface TAgencyMapper extends BaseMapper<TAgency, TAgencyCondition>{
    List<TAgency> getList(Map<String, Object> params);

    List<Map<String, Object>> searchOnlineList(Map<String, Object> params);

    List<Map<String, Object>> getOnlineList(Map<String, Object> params);
}
