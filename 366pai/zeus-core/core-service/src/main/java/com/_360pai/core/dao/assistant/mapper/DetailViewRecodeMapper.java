package com._360pai.core.dao.assistant.mapper;

import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.assistant.DetailViewRecodeCondition;
import com._360pai.core.model.assistant.DetailViewRecode;
import org.apache.ibatis.annotations.Mapper;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2019/4/25 16:55
 */
@Mapper
public interface DetailViewRecodeMapper  extends BaseMapper<DetailViewRecode, DetailViewRecodeCondition> {
}
