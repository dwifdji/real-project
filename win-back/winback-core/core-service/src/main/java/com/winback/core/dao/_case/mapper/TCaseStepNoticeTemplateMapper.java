
package com.winback.core.dao._case.mapper;

import com.winback.core.vo._case.TCaseStepNoticeTemplateVO;
import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition._case.TCaseStepNoticeTemplateCondition;
import com.winback.core.model._case.TCaseStepNoticeTemplate;
import com.winback.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>TCaseStepNoticeTemplate数据层的基础操作</p>
 * @ClassName: TCaseStepNoticeTemplateMapper
 * @Description: TCaseStepNoticeTemplate数据层的基础操作
 * @author Generator
 * @date 2019年01月16日 16时13分05秒
 */
@Mapper
public interface TCaseStepNoticeTemplateMapper extends BaseMapper<TCaseStepNoticeTemplate, TCaseStepNoticeTemplateCondition>{

    List<TCaseStepNoticeTemplateVO> getTemplateByStepId(@Param("stepId") String id);

    List<TCaseStepNoticeTemplateVO> getTemplateByBranchId(@Param("stepId") String id);
}
