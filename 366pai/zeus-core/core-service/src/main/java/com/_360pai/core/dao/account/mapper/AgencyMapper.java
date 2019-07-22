
package com._360pai.core.dao.account.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.account.AgencyCondition;
import com._360pai.core.model.account.Agency;
import com._360pai.arch.core.abs.BaseMapper;

/**
 * <p>Agency数据层的基础操作</p>
 * @ClassName: AgencyMapper
 * @Description: Agency数据层的基础操作
 * @author Generator
 * @date 2018年08月10日 17时37分12秒
 */
@Mapper
public interface AgencyMapper extends BaseMapper<Agency, AgencyCondition>{

}
