
package com._360pai.core.dao.account.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.account.CompanyCondition;
import com._360pai.core.model.account.Company;
import com._360pai.arch.core.abs.BaseMapper;

/**
 * <p>Company数据层的基础操作</p>
 * @ClassName: CompanyMapper
 * @Description: Company数据层的基础操作
 * @author Generator
 * @date 2018年08月10日 17时37分13秒
 */
@Mapper
public interface CompanyMapper extends BaseMapper<Company, CompanyCondition>{

}
