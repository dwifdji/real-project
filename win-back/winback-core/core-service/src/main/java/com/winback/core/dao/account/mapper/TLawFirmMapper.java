
package com.winback.core.dao.account.mapper;

import com.winback.arch.core.abs.BaseMapper;
import com.winback.core.condition.account.TLawFirmCondition;
import com.winback.core.model.account.TLawFirm;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>TLawFirm数据层的基础操作</p>
 * @ClassName: TLawFirmMapper
 * @Description: TLawFirm数据层的基础操作
 * @author Generator
 * @date 2019年01月16日 15时40分02秒
 */
@Mapper
public interface TLawFirmMapper extends BaseMapper<TLawFirm, TLawFirmCondition>{
    List<TLawFirm> getList(Map<String, Object> params);
}
