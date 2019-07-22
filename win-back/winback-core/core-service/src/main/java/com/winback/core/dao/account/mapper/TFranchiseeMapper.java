
package com.winback.core.dao.account.mapper;

import com.winback.core.model._case.TCase;
import com.winback.core.model.account.TAccount;
import com.winback.core.model.account.TLawyer;
import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition.account.TFranchiseeCondition;
import com.winback.core.model.account.TFranchisee;
import com.winback.arch.core.abs.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>TFranchisee数据层的基础操作</p>
 * @ClassName: TFranchiseeMapper
 * @Description: TFranchisee数据层的基础操作
 * @author Generator
 * @date 2019年01月16日 15时40分02秒
 */
@Mapper
public interface TFranchiseeMapper extends BaseMapper<TFranchisee, TFranchiseeCondition>{
    List<TFranchisee> getList(Map<String, Object> params);

    List<TAccount> getInviteCustomerList(Map<String, Object> params);

    List<TCase> getInviteCaseList(Map<String, Object> params);
}
