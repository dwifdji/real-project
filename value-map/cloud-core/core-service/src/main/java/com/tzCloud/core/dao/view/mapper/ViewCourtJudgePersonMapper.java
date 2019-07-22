
package com.tzCloud.core.dao.view.mapper;

import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.core.condition.view.ViewCourtJudgePersonCondition;
import com.tzCloud.core.model.view.ViewCourtJudgePerson;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>ViewCourtJudgePerson数据层的基础操作</p>
 * @ClassName: ViewCourtJudgePersonMapper
 * @Description: ViewCourtJudgePerson数据层的基础操作
 * @author Generator
 * @date 2019年04月24日 10时43分19秒
 */
@Mapper
public interface ViewCourtJudgePersonMapper extends BaseMapper<ViewCourtJudgePerson, ViewCourtJudgePersonCondition>{

    List<Map<String, Object>> selectJudgePersonByCourtName(@Param("courtName") String name);
}
