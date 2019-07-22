
package com.winback.core.dao.assistant.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition.assistant.TFileCondition;
import com.winback.core.model.assistant.TFile;
import com.winback.arch.core.abs.BaseMapper;

/**
 * <p>TFile数据层的基础操作</p>
 * @ClassName: TFileMapper
 * @Description: TFile数据层的基础操作
 * @author Generator
 * @date 2019年02月15日 17时21分00秒
 */
@Mapper
public interface TFileMapper extends BaseMapper<TFile, TFileCondition>{

}
