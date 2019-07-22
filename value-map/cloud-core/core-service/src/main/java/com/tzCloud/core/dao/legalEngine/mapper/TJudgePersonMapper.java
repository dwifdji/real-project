
package com.tzCloud.core.dao.legalEngine.mapper;

import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.core.condition.legalEngine.TJudgePersonCondition;
import com.tzCloud.core.model.legalEngine.TJudgePerson;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>TJudgePerson数据层的基础操作</p>
 * @ClassName: TJudgePersonMapper
 * @Description: TJudgePerson数据层的基础操作
 * @author Generator
 * @date 2019年04月23日 14时53分12秒
 */
@Mapper
public interface TJudgePersonMapper extends BaseMapper<TJudgePerson, TJudgePersonCondition>{
    List<TJudgePerson> findByIds(@Param("list") List<String> list);
}
