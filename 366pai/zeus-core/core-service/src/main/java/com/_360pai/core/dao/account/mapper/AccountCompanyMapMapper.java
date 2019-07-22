
package com._360pai.core.dao.account.mapper;

import com._360pai.core.facade.account.vo.CompanyMemberVo;
import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.account.AccountCompanyMapCondition;
import com._360pai.core.model.account.AccountCompanyMap;
import com._360pai.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>AccountCompanyMap数据层的基础操作</p>
 * @ClassName: AccountCompanyMapMapper
 * @Description: AccountCompanyMap数据层的基础操作
 * @author Generator
 * @date 2018年08月10日 17时37分12秒
 */
@Mapper
public interface AccountCompanyMapMapper extends BaseMapper<AccountCompanyMap, AccountCompanyMapCondition>{

    List<CompanyMemberVo> getList(Map<String, Object> params);
}
