
package com.tzCloud.core.dao.applicant.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.tzCloud.core.condition.applicant.TMapApplicantCondition;
import com.tzCloud.core.model.applicant.TMapApplicant;
import com.tzCloud.arch.core.abs.BaseMapper;

/**
 * <p>TMapApplicant数据层的基础操作</p>
 * @ClassName: TMapApplicantMapper
 * @Description: TMapApplicant数据层的基础操作
 * @author Generator
 * @date 2019年07月05日 13时25分49秒
 */
@Mapper
public interface TMapApplicantMapper extends BaseMapper<TMapApplicant, TMapApplicantCondition>{

}
