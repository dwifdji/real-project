
package com.winback.core.dao.systemsite.mapper;

import com.winback.core.vo.systemsite.CaseSiteVO;
import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition.systemsite.TCaseSettingCondition;
import com.winback.core.model.systemsite.TCaseSetting;
import com.winback.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>TCaseSetting数据层的基础操作</p>
 * @ClassName: TCaseSettingMapper
 * @Description: TCaseSetting数据层的基础操作
 * @author Generator
 * @date 2019年01月23日 10时13分56秒
 */
@Mapper
public interface TCaseSettingMapper extends BaseMapper<TCaseSetting, TCaseSettingCondition>{

    List<CaseSiteVO> getCaseSettingList(@Param("type")String type);
}
