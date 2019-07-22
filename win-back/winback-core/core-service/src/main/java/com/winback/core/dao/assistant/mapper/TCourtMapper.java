
package com.winback.core.dao.assistant.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition.assistant.TCourtCondition;
import com.winback.core.model.assistant.TCourt;
import com.winback.arch.core.abs.BaseMapper;

/**
 * <p>TCourt数据层的基础操作</p>
 * @ClassName: TCourtMapper
 * @Description: TCourt数据层的基础操作
 * @author Generator
 * @date 2019年01月17日 13时31分40秒
 */
@Mapper
public interface TCourtMapper extends BaseMapper<TCourt, TCourtCondition>{

}
