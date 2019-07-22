
package com.winback.core.dao.operate.mapper;

import com.winback.arch.core.abs.BaseMapper;
import com.winback.core.dto.operate.OperateIconDto;
import com.winback.core.vo.operate.*;
import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition.operate.TOperateIconInfoCondition;
import com.winback.core.model.operate.TOperateIconInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>TOperateIconInfo数据层的基础操作</p>
 * @ClassName: TOperateIconInfoMapper
 * @Description: TOperateIconInfo数据层的基础操作
 * @author Generator
 * @date 2019年01月22日 10时27分34秒
 */
@Mapper
public interface TOperateIconInfoMapper extends BaseMapper<TOperateIconInfo, TOperateIconInfoCondition> {

    List<OperateIconListVO> getReleaseAreaList(OperateIconDto params);

    List<HomeIconCategoryVO> getQualityCaseIcons(@Param("limitSize") Integer limitSize);

    List<HomeContractCategoryVO> getContractModelIcons(@Param("limitSize") Integer limitSize);

    List<QuickReleaseVO> getCaseTypeList(@Param("limitSize") Integer limitSize);
}
