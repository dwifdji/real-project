
package com._360pai.core.dao.assistant.mapper;

import com._360pai.core.facade.assistant.vo.DepositVo;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.assistant.DepositOfflineActionCondition;
import com._360pai.core.model.assistant.DepositOfflineAction;
import com._360pai.arch.core.abs.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>DepositOfflineAction数据层的基础操作</p>
 * @ClassName: DepositOfflineActionMapper
 * @Description: DepositOfflineAction数据层的基础操作
 * @author Generator
 * @date 2018年10月03日 14时24分09秒
 */
@Mapper
public interface DepositOfflineActionMapper extends BaseMapper<DepositOfflineAction, DepositOfflineActionCondition>{

    List<DepositVo> getList(Map<String, Object> params);

    List<DepositVo> getRefundList(Map<String, Object> params);
}
