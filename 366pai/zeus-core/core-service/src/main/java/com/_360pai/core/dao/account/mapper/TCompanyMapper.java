
package com._360pai.core.dao.account.mapper;

import com._360pai.core.model.account.TUser;
import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.account.TCompanyCondition;
import com._360pai.core.model.account.TCompany;
import com._360pai.arch.core.abs.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>TCompany数据层的基础操作</p>
 * @ClassName: TCompanyMapper
 * @Description: TCompany数据层的基础操作
 * @author Generator
 * @date 2018年08月17日 15时47分34秒
 */
@Mapper
public interface TCompanyMapper extends BaseMapper<TCompany, TCompanyCondition>{
    List<TCompany> getList(Map<String, Object> params);
}
