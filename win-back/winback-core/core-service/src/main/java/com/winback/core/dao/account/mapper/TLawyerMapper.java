
package com.winback.core.dao.account.mapper;

import com.winback.arch.core.abs.BaseMapper;
import com.winback.core.condition.account.TLawyerCondition;
import com.winback.core.model.account.TLawyer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>TLawyer数据层的基础操作</p>
 * @ClassName: TLawyerMapper
 * @Description: TLawyer数据层的基础操作
 * @author Generator
 * @date 2019年01月16日 15时40分02秒
 */
@Mapper
public interface TLawyerMapper extends BaseMapper<TLawyer, TLawyerCondition>{
    List<TLawyer> getList(Map<String, Object> params);
}
