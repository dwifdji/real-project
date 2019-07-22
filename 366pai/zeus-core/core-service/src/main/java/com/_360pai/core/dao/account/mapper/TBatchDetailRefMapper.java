
package com._360pai.core.dao.account.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.account.TBatchDetailRefCondition;
import com._360pai.core.model.account.TBatchDetailRef;
import com._360pai.arch.core.abs.BaseMapper;

/**
 * <p>TBatchDetailRef数据层的基础操作</p>
 * @ClassName: TBatchDetailRefMapper
 * @Description: TBatchDetailRef数据层的基础操作
 * @author Generator
 * @date 2018年11月29日 15时57分11秒
 */
@Mapper
public interface TBatchDetailRefMapper extends BaseMapper<TBatchDetailRef, TBatchDetailRefCondition>{

    void deleteByBatchId(Long batchId);
}
