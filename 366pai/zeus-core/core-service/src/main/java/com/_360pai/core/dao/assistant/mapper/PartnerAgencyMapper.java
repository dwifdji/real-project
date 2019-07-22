
package com._360pai.core.dao.assistant.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.assistant.PartnerAgencyCondition;
import com._360pai.core.model.assistant.PartnerAgency;
import com._360pai.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>PartnerAgency数据层的基础操作</p>
 *
 * @author Generator
 * @ClassName: PartnerAgencyMapper
 * @Description: PartnerAgency数据层的基础操作
 * @date 2018年08月10日 17时37分17秒
 */
@Mapper
public interface PartnerAgencyMapper extends BaseMapper<PartnerAgency, PartnerAgencyCondition> {

    int deletePartnerAgency(@Param("paramsId") Integer paramsId);
}
