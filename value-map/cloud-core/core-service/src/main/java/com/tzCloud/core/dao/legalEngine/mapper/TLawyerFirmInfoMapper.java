
package com.tzCloud.core.dao.legalEngine.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.tzCloud.core.condition.legalEngine.TLawyerFirmInfoCondition;
import com.tzCloud.core.model.legalEngine.TLawyerFirmInfo;
import com.tzCloud.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>TLawyerFirmInfo数据层的基础操作</p>
 * @ClassName: TLawyerFirmInfoMapper
 * @Description: TLawyerFirmInfo数据层的基础操作
 * @author Generator
 * @date 2019年04月28日 16时43分49秒
 */
@Mapper
public interface TLawyerFirmInfoMapper extends BaseMapper<TLawyerFirmInfo, TLawyerFirmInfoCondition>{
    List<TLawyerFirmInfo> selectByFirmNameLike(@Param("name") String name);
}
