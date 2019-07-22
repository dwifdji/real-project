
package com.tzCloud.core.dao.legalEngine.mapper;

import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.core.condition.legalEngine.TCourtCondition;
import com.tzCloud.core.model.legalEngine.TCourt;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>TCourt数据层的基础操作</p>
 *
 * @author Generator
 * @ClassName: TCourtMapper
 * @Description: TCourt数据层的基础操作
 * @date 2019年04月19日 15时41分11秒
 */
@Mapper
public interface TCourtMapper extends BaseMapper<TCourt, TCourtCondition> {

    List<Map<String, Object>> selectJudgeDateByCourtName(@Param("courtName") String name);

    List<TCourt> queryList();

    List<Map<String, String>> findDiffCourt();
}
