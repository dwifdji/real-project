
package com._360pai.core.dao.assistant.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.assistant.BannerCondition;
import com._360pai.core.model.assistant.Banner;
import com._360pai.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>Banner数据层的基础操作</p>
 * @ClassName: BannerMapper
 * @Description: Banner数据层的基础操作
 * @author Generator
 * @date 2018年08月10日 17时37分16秒
 */
@Mapper
public interface BannerMapper extends BaseMapper<Banner, BannerCondition>{

    int deleteBanner(@Param("paramsId") Integer paramsId);
}
