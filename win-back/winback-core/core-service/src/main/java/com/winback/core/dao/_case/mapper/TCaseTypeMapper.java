
package com.winback.core.dao._case.mapper;

import com.winback.core.vo.operate.CaseTypeVO;
import com.winback.core.vo.operate.QuickReleaseVO;
import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition._case.TCaseTypeCondition;
import com.winback.core.model._case.TCaseType;
import com.winback.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>TCaseType数据层的基础操作</p>
 * @ClassName: TCaseTypeMapper
 * @Description: TCaseType数据层的基础操作
 * @author Generator
 * @date 2019年01月16日 16时13分05秒
 */
@Mapper
public interface TCaseTypeMapper extends BaseMapper<TCaseType, TCaseTypeCondition>{

    List<CaseTypeVO> getAllCaseType();
 }
