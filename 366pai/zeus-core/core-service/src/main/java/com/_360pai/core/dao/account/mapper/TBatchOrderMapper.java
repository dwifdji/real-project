
package com._360pai.core.dao.account.mapper;

import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.account.TBatchOrderCondition;
import com._360pai.core.model.account.TBatchOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>TBatchOrder数据层的基础操作</p>
 * @ClassName: TBatchOrderMapper
 * @Description: TBatchOrder数据层的基础操作
 * @author Generator
 * @date 2018年11月29日 15时57分11秒
 */
@Mapper
public interface TBatchOrderMapper extends BaseMapper<TBatchOrder, TBatchOrderCondition>{

    TBatchOrder selectMaxId();

    List<TBatchOrder> searchBatchOrder(@Param("batchOrderNo")Long batchOrderNo, @Param("status") String status, @Param("beginTime") String beginTime, @Param("endTime") String endTime);

}
