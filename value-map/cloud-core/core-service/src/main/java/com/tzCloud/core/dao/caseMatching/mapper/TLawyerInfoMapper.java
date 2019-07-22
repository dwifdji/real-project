
package com.tzCloud.core.dao.caseMatching.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.tzCloud.core.condition.caseMatching.TLawyerInfoCondition;
import com.tzCloud.core.model.caseMatching.TLawyerInfo;
import com.tzCloud.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>TLawyerInfo数据层的基础操作</p>
 * @ClassName: TLawyerInfoMapper
 * @Description: TLawyerInfo数据层的基础操作
 * @author Generator
 * @date 2019年03月12日 11时53分17秒
 */
@Mapper
public interface TLawyerInfoMapper extends BaseMapper<TLawyerInfo, TLawyerInfoCondition>{
    List<TLawyerInfo> getLawyerByName(@Param("name") String name,@Param("firmShort") String firmShort);
}
