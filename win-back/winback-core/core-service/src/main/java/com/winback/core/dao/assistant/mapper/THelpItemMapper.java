
package com.winback.core.dao.assistant.mapper;

import com.winback.core.facade.assistant.vo.HelpItem;
import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition.assistant.THelpItemCondition;
import com.winback.core.model.assistant.THelpItem;
import com.winback.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>THelpItem数据层的基础操作</p>
 * @ClassName: THelpItemMapper
 * @Description: THelpItem数据层的基础操作
 * @author Generator
 * @date 2019年01月18日 09时41分05秒
 */
@Mapper
public interface THelpItemMapper extends BaseMapper<THelpItem, THelpItemCondition>{
    List<HelpItem> getHelpItemList(@Param("display") Boolean display);
}
